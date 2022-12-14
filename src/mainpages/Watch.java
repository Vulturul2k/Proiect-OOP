package mainpages;

import input.Movie;
import pageactions.PageDetails;

public class Watch extends Purchase {
    /**
     * This method help user to watch a movie
     * @param movie the movie that will be watched
     * @param details the current user
     * @return if the movie can be watched
     */
    public boolean watch(final Movie movie, final PageDetails details) {
        if (details.getUser().getPurchasedMovies() == null) {
            return false;
        }
        return super.verify(movie, details);
    }

    /**
     * this method verify if a movie is already watched
     * @param movie the movie
     * @param details give the user
     * @return if the movie is watched
     */
    public boolean verify(final Movie movie, final PageDetails details) {
        for (Movie watch : details.getUser().getWatchedMovies()) {
            if (watch.equals(movie)) {
                return true;
            }
        }
        return false;
    }
}
