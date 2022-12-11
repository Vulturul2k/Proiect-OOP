package movie;

import input.Action;
import input.Input;
import input.User;
import login_register.Info;

public class Upgrades {
    public String changePage(String page) {
        if (page.equals("logged") || page.equals("see details")) {
            return "upgrades";
        }
        return page;
    }
    public boolean buyTokens(Input inputData, Action action, User user) {
        if (user == null) {
            return false;
        }
        int balance = 0;
        for (int i = 0; i < user.getCredentials().getBalance().length(); i++) {
            balance = balance * 10 + (user.getCredentials().getBalance().charAt(i) - '0');
        }
        if (balance >= action.getCount()) {
            balance -= action.getCount();
            user.setTokensCount(user.getTokensCount() + action.getCount());
        } else {
            return false;
        }
//        StringBuilder newBalance = new StringBuilder(new String());
//        for (int i = 0; i < user.getCredentials().getBalance().length(); i++) {
//            newBalance.append((char) (balance % 10) + '0');
//            balance /= 10;
//        }
        user.getCredentials().setBalance(Integer.toString(balance));
        return true;
    }
    public boolean buyPremium(User user) {
        if (user.getTokensCount() > 11) {
            user.getCredentials().setAccountType("premium");
            user.setTokensCount(user.getTokensCount() - 10);
            return true;
        }
        return false;
    }
}
