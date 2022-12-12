package pages;

import input.Action;
import input.User;
import page.Actions.Constants;

public final class Upgrades {
    public String changePage(final String page) {
        if (page.equals("logged") || page.equals("see details")) {
            return "upgrades";
        }
        return page;
    }
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
    public boolean buyPremium(final User user) {
        if (user.getTokensCount() >= Constants.PRICE_OF_PREMIUM) {
            user.getCredentials().setAccountType("premium");
            user.setTokensCount(user.getTokensCount() - Constants.PRICE_OF_PREMIUM);
            return true;
        }
        return false;
    }
}
