package login_register;

import input.Action;
import input.Credentials;
import input.Input;
import input.User;

public class Register extends Login{


    public User register (String page, Input inputData, Action action) {
        if (page.equals("register")) {
            if (login("login", inputData, action) != null) {
                return null;
            }
            return createUser(inputData, action);
        } else {
            return null;
        }
    }
    public User createUser (Input inputData, Action action) {
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
