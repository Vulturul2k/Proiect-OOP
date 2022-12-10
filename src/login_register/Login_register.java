package login_register;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import input.*;
import movie.MoviePage;
import movie.SeeDetails;

import java.util.ArrayList;


public class Login_register {

    private final static Login_register log = new Login_register();

    public static Login_register getLog() {
        return log;
    }

    private Login_register() {

    }

    public void action(Input inputData, ArrayNode output) {
        String page = "Homepage neautentificat";
        User user = null;
        for (Action action : inputData.getActions()) {
            if (action.getType().equals("change page")) {
                if (action.getPage().equals("see details")) {
                    if (page.equals("movies")) {
                        new SeeDetails(inputData, action, user,
                                output, page);
                    }
                }
                if (action.getPage().equals("logout")) {
                    if (!page.equals("logged")) {
                        new Info(inputData, output, null, null);
                    } else {
                        page = "Homepage neautentificat";
                    }
                }
                if (action.getPage().equals("login") || action.getPage().equals("register")) {
                    Login login = new Login();
                    if (login.changePage(page, action.getPage())) {
                        page = action.getPage();
                    } else {
                        new Info(inputData, output, null, null);
                    }
                }
                if (action.getPage().equals("movies")) {
                    boolean ok = new MoviePage().movie_page(page, inputData, action, user, output);
                    if (ok) {
                        page = action.getPage();
                    }
                }
            }
            if (action.getType().equals("on page")) {
                if (action.getFeature().equals("login") || action.getFeature().equals("register")) {
                    if (action.getFeature().equals("login")) {
                        Login login = new Login();
                        user = login.login(page, inputData, action);
                    }
                    if (action.getFeature().equals("register")) {
                        Register register = new Register();
                        user = register.register(page, inputData, action);
                    }
                        if (user != null) {
                            page = "logged";
                        } else {
                            if (page.equals("login") || page.equals("register")) {
                                page = "Homepage neautentificat";
                            }
                        }
                    new Info(inputData, output, user, null);
                }
                if (action.getFeature().equals("search")) {
                    new MoviePage().search(inputData, action, user, output, page);
                }
                if (action.getFeature().equals("filter")) {
                    new MoviePage().filter(inputData, action, user, output, page);
                }
            }
        }
    }
}
