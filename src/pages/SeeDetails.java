package pages;

import input.Action;
import input.Input;
import input.Movie;
import input.User;
import page.Actions.Constants;
import page.Actions.PageDetails;

import java.util.ArrayList;

public final class SeeDetails {
    private static SeeDetails instance = null;
    private SeeDetails() {
    }

    /**
     * This is a getter
     * @return the instance
     */
    public static SeeDetails getINSTANCE() {
        if (instance == null) {
            instance = new SeeDetails();
        }
        return instance;
    }

    /**
     * This method takes us to page "see details" where we can see details about a movie
     * @param inputData give us all the movies
     * @param action give us the movie
     * @param details give info about user and page
     * @return if you could go to this page
     */
    public boolean seeDetails(final Input inputData, final Action action,
                              final PageDetails details) {
        if (details.getMovieList() == null) {
            ArrayList<Movie> movieList = MoviePage.getInstance()
                    .userMovies(inputData, details.getUser());
            details.setMovieList(movieList);
        }
        ArrayList<Movie> userMovies = new ArrayList<>();
        Movie movie = findMovie(details.getMovieList(), action);
        if (movie != null) {
            userMovies.add(movie);
            details.setMovieList(userMovies);
            details.setMovie(movie);
            return true;
        }
        return false;
    }

    /**
     * Find a movie from name
     * @param movieList all the movies
     * @param action give the movie`s name
     * @return the movie
     */
    public Movie findMovie(final ArrayList<Movie> movieList, final Action action) {
        for (Movie movie : movieList) {
            if (movie.getName().equals(action.getMovie())) {
                return movie;
            }
        }
        return null;
    }

    /**
     * This method try to buy a movie
     * @param movie the movie to be bought
     * @param user the user that will buy
     * @return if the movie can be bought
     */
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

    /**
     * This method help user to watch a movie
     * @param movie the movie that will be watched
     * @param user the current user
     * @return if the movie can be watched
     */
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

    /**
     * This method help the user to tell if he is like the movie
     * @param movie the movie that will give a like
     * @param user the current user
     * @return if the user could give a like
     */
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

    /**
     * This method help the user to tell if he is like the movie
     * @param movie the movie that will be rated
     * @param user the current user
     * @param action give the rate of the user
     * @return if the user could be rated
     */
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
