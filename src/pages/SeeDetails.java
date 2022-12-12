package pages;

import com.fasterxml.jackson.databind.node.ArrayNode;
import input.Action;
import input.Input;
import input.Movie;
import input.User;
import page.Actions.Constants;
import page.Actions.Info;
import page.Actions.PageDetails;
import java.util.ArrayList;

public final class SeeDetails {
    private static SeeDetails instance = null;
    private SeeDetails() {
    }

    public static SeeDetails getINSTANCE() {
        if (instance == null) {
            instance = new SeeDetails();
        }
        return instance;
    }

    public String seeDetails(final Input inputData, final Action action,
                             final ArrayNode output, final PageDetails details) {
        if (details.getMovieList() == null) {
            ArrayList<Movie> movieList = MoviePage.getInstance()
                    .userMovies(inputData, details.getUser());
            details.setMovieList(movieList);
        }
        ArrayList<Movie> userMovies = new ArrayList<>();
        Movie movie = findMovie(details.getMovieList(), action);
        if (movie != null) {
            details.setMovieList(null);
            userMovies.add(movie);
            new Info(output, details.getUser(), userMovies);
            details.setMovie(movie);
            return "see details";
        }
        return details.getPage();
    }
    public Movie findMovie(final ArrayList<Movie> movieList, final Action action) {
        for (Movie movie : movieList) {
            if (movie.getName().equals(action.getMovie())) {
                return movie;
            }
        }
        return null;
    }
    public boolean purchase(final Movie movie, final User user) {
        boolean ok = false;
        if (user.getCredentials().getAccountType().equals("standard")) {
            if (user.getTokensCount() >= Constants.PRICE_OF_A_MOVIE) {
                ok = true;
                user.setTokensCount(user.getTokensCount() - Constants.PRICE_OF_A_MOVIE);
            }
        } else {
            if (user.getNumFreePremiumMovies() > Constants.EMPTY) {
                ok = true;
                user.setNumFreePremiumMovies(user.getNumFreePremiumMovies() - Constants.INCREASE);
            } else {
                if (user.getTokensCount() >= Constants.PRICE_OF_A_MOVIE) {
                    ok = true;
                    user.setTokensCount(user.getTokensCount() - Constants.PRICE_OF_A_MOVIE);
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
    public boolean watch(final Movie movie, final User user) {
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
    public boolean like(final Movie movie, final User user) {
        if (user.getWatchedMovies() == null) {
            return false;
        }
        for (Movie purchase : user.getWatchedMovies()) {
            if (purchase.equals(movie)) {
                if (user.getLikedMovies() == null) {
                    user.setLikedMovies(new ArrayList<>());
                }
                movie.setNumLikes(movie.getNumLikes() + Constants.INCREASE);
                user.getLikedMovies().add(movie);
                return true;
            }
        }
        return false;
    }
    public boolean rate(final Movie movie, final User user, final Action action) {
        if (user.getWatchedMovies() == null) {
            return false;
        }
        for (Movie watch : user.getWatchedMovies()) {
            if (watch.equals(movie)) {
                if (user.getRatedMovies() == null) {
                    user.setRatedMovies(new ArrayList<>());
                }
                if (action.getRate() > Constants.MAX_RATE) {
                    return false;
                }
                movie.setRatingSum(movie.getRatingSum() + action.getRate());
                movie.setNumRatings(movie.getNumRatings() + Constants.INCREASE);
                movie.setRating(movie.getRatingSum() / movie.getNumRatings());
                user.getRatedMovies().add(movie);
                return true;
            }
        }
        return false;
    }
}
