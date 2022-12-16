package mainpages;

import input.Movie;
import pageactions.Constants;
import pageactions.PageDetails;

import java.util.ArrayList;

public abstract class Like extends Watch {
    /**
     * This method help the user to tell if he is like the movie
     * @param movie the movie that will give a like
     * @param details the current user
     * @return if the user could give a like
     */
    public static boolean like(final Movie movie, final PageDetails details) {
        if (details.getUser().getWatchedMovies() == null) {
            return false;
        }
        if (verifyWatch(movie, details)) {
            setLike(movie, details);
            return true;
        }
        return false;
    }

    /**
     * this method set like to a movie
     * @param movie the movie
     * @param details give the user
     */
    private static void setLike(final Movie movie, final PageDetails details) {
        if (details.getUser().getLikedMovies() == null) {
            details.getUser().setLikedMovies(new ArrayList<>());
        }
        movie.setNumLikes(movie.getNumLikes() + Constants.INCREASE);
        details.getUser().getLikedMovies().add(movie);
    }
}
