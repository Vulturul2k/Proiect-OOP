package login_register;

import input.Action;
import input.Input;
import input.User;

public class Login {
    public boolean changePage (String page, String newPage) {
        return page.equals("Homepage neautentificat") && (newPage.equals("login")
                || newPage.equals("register"));
    }
    public User login (String page, Input inputData, Action action) {
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
