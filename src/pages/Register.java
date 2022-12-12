package pages;

import input.Action;
import input.Credentials;
import input.Input;
import input.User;

public class Register {


    public User register(final Input inputData, final Action action) {
            if (new Login().login(inputData, action) != null) {
                return null;
            }
            return createUser(inputData, action);
    }
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
