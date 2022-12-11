package movie;

import com.fasterxml.jackson.databind.node.ArrayNode;
import input.Action;
import input.Input;
import input.Movie;
import input.User;
import login_register.Info;
import login_register.PageDetails;

import java.util.ArrayList;

public class SeeDetails {
    public String seeDetails(Input inputData, Action action,
                             ArrayNode output, PageDetails details) {
//        if (user == null) {
//            return;
//        }
        if (details.getMovieList() == null) {
            ArrayList<Movie> movieList = new MoviePage().userMovies(inputData, details.getUser());
            details.setMovieList(movieList);
        }
        ArrayList<Movie> userMovies = new ArrayList<>();
        Movie movie = findMovie(details.getMovieList(), action);
        if (movie != null) {
            details.setMovieList(null);
            userMovies.add(movie);
            new Info(inputData, output, details.getUser(), userMovies);
            details.setMovie(movie);
            return "see details";
        }
        return details.getPage();
    }
    public Movie findMovie(ArrayList<Movie> movieList, Action action) {
        for (Movie movie : movieList) {
            if (movie.getName().equals(action.getMovie())) {
                return movie;
            }
        }
        return null;
    }
    public boolean purchase(Movie movie, User user) {
        boolean ok = false;
        if (user.getCredentials().getAccountType().equals("standard")){
            if (user.getTokensCount() > 3) {
                ok = true;
                user.setTokensCount(user.getTokensCount() - 2);
            }
        } else {
            if (user.getNumFreePremiumMovies() > 0) {
                ok = true;
                user.setNumFreePremiumMovies(user.getNumFreePremiumMovies() - 1);
            } else {
                if (user.getTokensCount() > 3) {
                    ok = true;
                    user.setTokensCount(user.getTokensCount() - 2);
                }
            }
        }

        if (ok) {
            if (user.getPurchasedMovies() == null) {
                user.setPurchasedMovies(new ArrayList<>());
            }
            user.getPurchasedMovies().add(movie);

            return true;
        }
        return false;
    }
    public boolean watch(Movie movie, User user) {
        if (user.getPurchasedMovies() == null) {
            return false;
        }
        for (Movie purchase : user.getPurchasedMovies()) {
            if (purchase.equals(movie)) {
                if (user.getWatchedMovies() == null) {
                    user.setWatchedMovies(new ArrayList<>());
                }
                user.getWatchedMovies().add(movie);
                return true;
            }
        }
        return false;
    }
    public boolean like(Movie movie, User user) {
        if (user.getWatchedMovies() == null) {
            return false;
        }
        for (Movie purchase : user.getWatchedMovies()) {
            if (purchase.equals(movie)) {
                if (user.getLikedMovies() == null) {
                    user.setLikedMovies(new ArrayList<>());
                }
                movie.setNumLikes(movie.getNumLikes() + 1);
                user.getLikedMovies().add(movie);
                return true;
            }
        }
        return false;
    }
    public boolean rate(Movie movie, User user, Action action) {
        if (user.getWatchedMovies() == null) {
            return false;
        }
        for (Movie watch : user.getWatchedMovies()) {
            if (watch.equals(movie)) {
                if (user.getRatedMovies() == null) {
                    user.setRatedMovies(new ArrayList<>());
                }
                if (action.getRate() > 5) {
                    return false;
                }
                movie.setRatingSum(movie.getRatingSum() + action.getRate());
                movie.setNumRatings(movie.getNumRatings() + 1);
                movie.setRating(movie.getRatingSum()/movie.getNumRatings());
                user.getRatedMovies().add(movie);
                return true;
            }
        }
        return false;
    }
}
