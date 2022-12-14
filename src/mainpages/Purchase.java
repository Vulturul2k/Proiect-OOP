package mainpages;

import input.Movie;
import pageactions.Constants;
import pageactions.PageDetails;

import java.util.ArrayList;

public class Purchase {
    private boolean pay(final PageDetails details) {
        boolean ok = false;
        if (details.getUser().getCredentials().getAccountType().equals("standard")
                || details.getUser().getNumFreePremiumMovies() == Constants.EMPTY) {
            if (details.getUser().getTokensCount() >= Constants.PRICE_OF_A_MOVIE) {
                ok = true;
                details.getUser().setTokensCount(details.getUser()
                        .getTokensCount() - Constants.PRICE_OF_A_MOVIE);
            }
        } else {
            if (details.getUser().getNumFreePremiumMovies() > Constants.EMPTY) {
                ok = true;
                details.getUser().setNumFreePremiumMovies(details.getUser()
                        .getNumFreePremiumMovies() - Constants.INCREASE);
            }
        }
        return ok;
    }
    /**
     * This method try to buy a movie
     * @param movie the movie to be bought
     * @param details the user that will buy
     * @return if the movie can be bought
     */
    public boolean purchase(final Movie movie, final PageDetails details) {

        if (pay(details)) {
            if (details.getUser().getPurchasedMovies() == null) {
                details.getUser().setPurchasedMovies(new ArrayList<>());
            }
            details.getUser().getPurchasedMovies().add(movie);

            return true;
        }
        return false;
    }

    /**
     * verify is a movie is already purchased
     * @param movie the movie
     * @param details give the user
     * @return if is already purchased
     */
    public boolean verify(final Movie movie, final PageDetails details) {
        for (Movie purchase : details.getUser().getPurchasedMovies()) {
            if (purchase.equals(movie)) {
                if (details.getUser().getWatchedMovies() == null) {
                    details.getUser().setWatchedMovies(new ArrayList<>());
                }
                details.getUser().getWatchedMovies().add(movie);
                return true;
            }
        }
        return false;
    }
}
