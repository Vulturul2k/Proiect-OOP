package pages;

import input.Action;
import input.User;
import page.Actions.Constants;
import page.Actions.PageDetails;

public final class Upgrades implements ChangePage {
    private static Upgrades instance = null;

    private Upgrades() {
    }

    /**
     * this is a getter for the lazy instance
     * @return the instance
     */
    public static Upgrades getInstance() {
        if (instance == null) {
            instance = new Upgrades();
        }
        return instance;
    }

    /**
     * this method verify if the page could be changed to upgrades
     * @param details is the database where is the page
     * @return if it is a success
     */
    public boolean changePage(final PageDetails details) {
        return details.getPage().equals("Homepage autentificat")
                || details.getPage().equals("see details");
    }

    /**
     * This method convert balance in tokens
     * @param action give how many balance to convert
     * @param user is the curent user
     * @return if it would be converted
     */
    public boolean buyTokens(final Action action, final User user) {
        int balance = Constants.EMPTY;
        for (int i = Constants.EMPTY; i < user.getCredentials().getBalance().length(); i++) {
            balance = balance * Constants.NEXT_DECIMAL
                    + (user.getCredentials().getBalance().charAt(i) - '0');
        }
        if (balance >= action.getCount()) {
            balance -= action.getCount();
            user.setTokensCount(user.getTokensCount() + action.getCount());
        } else {
            return false;
        }
        user.getCredentials().setBalance(Integer.toString(balance));
        return true;
    }

    /**
     * This method transform a user in a premium one
     * @param user the curent user
     * @return if the user has tokens to buy premium
     */
    public boolean buyPremium(final User user) {
        if (user.getTokensCount() >= Constants.PRICE_OF_PREMIUM) {
            user.getCredentials().setAccountType("premium");
            user.setTokensCount(user.getTokensCount() - Constants.PRICE_OF_PREMIUM);
            return true;
        }
        return false;
    }
}
