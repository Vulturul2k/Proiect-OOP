package intermediat.Pages;

import input.Input;
import input.User;
import page.Actions.PageDetails;

public class Login implements IntermediatePages {
    private static Login instance = null;
    protected Login() {
    }

    /**
     * this is a getter
     * @return instance
     */
    public static Login getInstance() {
        if (instance == null) {
            instance = new Login();
        }
        return instance;
    }

    /**
     * this is a method which takes us out of the home page
     * @param details where we have the main page
     * @return if we are on the homepage
     */
    public final boolean changePage(final PageDetails details) {
        return details.getPage().equals("Homepage neautentificat");
    }

    /**
     * This method try to log in a user
     * @param inputData database with users
     * @param details give the user who want to log in
     * @return the user if it is logged, else null
     */
    public User login(final Input inputData, final PageDetails details) {
        for (User person: inputData.getUsers()) {
            if (person.getCredentials().getName().equals(details.getAction()
                    .getCredentials().getName())) {
                if (person.getCredentials().getPassword()
                        .equals(details.getAction().getCredentials().getPassword())) {
                   return person;
                }
                return null;
            }
        }
        return null;
    }
}
