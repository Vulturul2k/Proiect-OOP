package mainpages;

import input.Movie;
import pageactions.PageDetails;

import java.util.ArrayList;

public final class Subscribe {
    /**
     * This method try to buy a movie
     * @param movie the movie to be bought
     * @param details the user that will buy
     * @return if the movie can be bought
     */
    public static boolean subscribe(final Movie movie, final PageDetails details) {
        if (details.getUser().getSubscribe() != null) {
            for (String gen : details.getUser().getSubscribe()) {
                if (gen.equals(details.getAction().getSubscribedGenre())) {
                    return false;
                }
            }
        } else {
            details.getUser().setSubscribe(new ArrayList<>());
        }
        boolean ok = false;
        for (String movieGen : movie.getGenres()) {
            if (movieGen.equals(details.getAction().getSubscribedGenre())) {
                ok = true;
                break;
            }
        }
        if (!ok) {
            return false;
        }
        details.getUser().getSubscribe().add(details.getAction().getSubscribedGenre());
        return true;
    }
}
