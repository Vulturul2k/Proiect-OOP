package mainpages;

import input.Movie;
import pageactions.Constants;
import pageactions.PageDetails;

import java.util.ArrayList;

public abstract class Rate extends Watch {
    /**
     * This method help the user to tell if he is like the movie
     * @param movie the movie that will be rated
     * @param details give the current user and the rate
     * @return if the user could be rated
     */
    public static boolean rate(final Movie movie, final PageDetails details) {
        if (details.getUser().getWatchedMovies() == null) {
            return false;
        }
        if (details.getAction().getRate() > Constants.MAX_RATE) {
            return false;
        }
        if (verifyWatch(movie, details)) {
            setRating(movie, details);
            return true;
        }
        return false;
    }

    /**
     * this method set the rating of the movie
     * @param movie the movie
     * @param details give the user
     */
    private static void setRating(final Movie movie, final PageDetails details) {
        if (details.getUser().getRatedMovies() == null) {
            details.getUser().setRatedMovies(new ArrayList<>());
        }
        movie.setRatingSum(movie.getRatingSum() + details.getAction().getRate());
        movie.setNumRatings(movie.getNumRatings() + Constants.INCREASE);
        movie.setRating(movie.getRatingSum() / movie.getNumRatings());
        details.getUser().getRatedMovies().add(movie);
    }
}
