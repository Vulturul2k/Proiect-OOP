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
        if (details.getUser().getRatedMovies() != null) {
            for (Movie rated : details.getUser().getRatedMovies()) {
                if (movie.getName().equals(rated.getName())) {
                    movie.setRatingSum(movie.getRatingSum() - details.getUser()
                            .getRates().get(details.getUser().getRatedMovies().indexOf(movie)));
                    details.getUser().getRates().set(details.getUser().getRatedMovies()
                            .indexOf(movie), (double) details.getAction().getRate());
                    movie.setRatingSum(movie.getRatingSum() + details.getAction().getRate());
                    movie.setRating(movie.getRatingSum() / movie.getNumRatings());
                    return true;
                }
            }
        }
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
        if (details.getUser().getRates() == null) {
            details.getUser().setRates(new ArrayList<>());
        }
        details.getUser().getRatedMovies().add(movie);
        details.getUser().getRates().add((double) details.getAction().getRate());
        movie.setRatingSum(movie.getRatingSum() + details.getAction().getRate());
        movie.setNumRatings(movie.getNumRatings() + Constants.INCREASE);
        movie.setRating(movie.getRatingSum() / movie.getNumRatings());
    }
}
