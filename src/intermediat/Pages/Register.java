package intermediat.Pages;

import input.Action;
import input.Credentials;
import input.Input;
import input.User;
import page.Actions.PageDetails;

public final class Register extends Login {
    private static Register instance = null;
    private Register() {
        super();
    }

    /**
     * this is a getter for instanc
     * @return
     */
    public static Register getInstance() {
        if (instance == null) {
            instance = new Register();
        }
        return instance;
    }
    /**
     * This method register a user who isn't in the database
     * @param inputData database with users
     * @param details give the user who want to log in
     * @return the user who was added in database
     */
    public User login(final Input inputData, final PageDetails details) {
            if (super.login(inputData, details) != null) {
                return null;
            }
            return createUser(inputData, details.getAction());
    }

    /**
     * This method create a new user
     * @param inputData database
     * @param action give the user which will be created
     * @return the user
     */
    public User createUser(final Input inputData, final Action action) {
        Credentials newUser = new Credentials();
        newUser.setName(action.getCredentials().getName());
        newUser.setPassword(action.getCredentials().getPassword());
        newUser.setAccountType(action.getCredentials().getAccountType());
        newUser.setCountry(action.getCredentials().getCountry());
        newUser.setBalance(action.getCredentials().getBalance());
        User credentialsUser = new User();
        credentialsUser.setCredentials(newUser);
        inputData.getUsers().add(credentialsUser);
        return credentialsUser;
    }
}
