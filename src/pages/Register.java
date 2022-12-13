package pages;

import input.Action;
import input.Credentials;
import input.Input;
import input.User;

public final class Register extends Login {
    private static Register instance = null;
    private Register() {
        super();
    }

    /**
     * this is a getter for instance. That create a new instance if it is null
     * @return the instance
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
     * @param action give the user who want to log in
     * @return the user who was added in database
     */
    public User login(final Input inputData, final Action action) {
            if (super.login(inputData, action) != null) {
                return null;
            }
            return createUser(inputData, action);
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
