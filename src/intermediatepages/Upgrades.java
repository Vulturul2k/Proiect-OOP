package intermediatepages;

import input.User;
import pageactions.Constants;
import pageactions.PageDetails;

public final class Upgrades implements IntermediatePages {
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
    private int convert(final User user) {
        int balance = Constants.EMPTY;
        for (int i = Constants.EMPTY; i < user.getCredentials().getBalance().length(); i++) {
            balance = balance * Constants.NEXT_DECIMAL
                    + (user.getCredentials().getBalance().charAt(i) - '0');
        }
        return balance;
    }
    /**
     * This method convert balance in tokens
     * @param details give how many balance to convert and the user
     * @return if it would be converted
     */
    public boolean buyTokens(final PageDetails details) {
        int balance = convert(details.getUser());
        if (balance >= details.getAction().getCount()) {
            balance -= details.getAction().getCount();
            details.getUser().setTokensCount(details.getUser()
                    .getTokensCount() + details.getAction().getCount());
        } else {
            return false;
        }
        details.getUser().getCredentials().setBalance(Integer.toString(balance));
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
