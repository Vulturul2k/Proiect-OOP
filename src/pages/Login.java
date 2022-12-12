package pages;

import input.Action;
import input.Input;
import input.User;

public class Login {
    public boolean changePage(final String page, final String newPage) {
        return page.equals("Homepage neautentificat") && (newPage.equals("login")
                || newPage.equals("register"));
    }
    public User login(final Input inputData, final Action action) {
        for (User person: inputData.getUsers()) {
            if (person.getCredentials().getName().equals(action.getCredentials().getName())) {
                if (person.getCredentials().getPassword()
                        .equals(action.getCredentials().getPassword())) {
                   return person;
                }
                return null;
            }
        }
        return null;
    }
}
