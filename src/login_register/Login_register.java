package login_register;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import input.*;
import movie.MoviePage;
import movie.SeeDetails;
import movie.Upgrades;

import java.util.ArrayList;
import java.util.Objects;


public class Login_register {

    private final static Login_register log = new Login_register();

    public static Login_register getLog() {
        return log;
    }

    private Login_register() {

    }
    private String changePage(Input inputData, ArrayNode output,
                              Action action, PageDetails details) {
        if (action.getPage().equals("login") || action.getPage().equals("register")) {
            Login login = new Login();
            if (login.changePage(details.getPage(), action.getPage())) {
                return action.getPage();
            }
        }
        if (action.getPage().equals("logout")) {
            if (details.getPage().equals("logged")  || details.getPage().equals("see details") || details.getPage().equals("movies")) {
                return "Homepage neautentificat";
            }
        }
        if (action.getPage().equals("movies")) {
            boolean ok = new MoviePage().moviePage(details.getPage(), inputData, action, details.getUser(), output);
            if (ok) {
                return "movies";
            }
        }
        if (action.getPage().equals("see details")) {
            if (details.getPage().equals("movies")) {
                return new SeeDetails().seeDetails(inputData, action,
                        output, details);
            }
        }
        if (action.getPage().equals("upgrades")) {
            return new Upgrades().changePage(details.getPage());
        }
        return details.getPage();
    }
    private void onPage(Input inputData, ArrayNode output,
                        Action action, PageDetails details) {
        if (action.getFeature().equals("login") || action.getFeature().equals("register")) {
            if (details.getPage().equals("login") || details.getPage().equals("register")) {
                if (action.getFeature().equals("login")) {
                    Login login = new Login();
                    details.setUser(login.login(details.getPage(), inputData, action));
                }
                if (action.getFeature().equals("register")) {
                    Register register = new Register();
                    details.setUser(register.register(details.getPage(), inputData, action));
                }
                if (details.getUser() != null) {
                    details.setPage("logged");
                } else {
                    details.setPage("Homepage neautentificat");
                }
                new Info(inputData, output, details.getUser(), null);
            } else {
                new Info(inputData, output, null, null);
            }
        }
        if (action.getFeature().equals("search")) {
            new MoviePage().search(inputData, action, details.getUser(), output, details.getPage());
        }
        if (action.getFeature().equals("filter")) {
            new MoviePage().filter(inputData, action, details.getUser(), output, details.getPage());
        }
        if (action.getFeature().equals("buy tokens")) {
            if (details.getPage().equals("upgrades")) {
                if (!new Upgrades().buyTokens(inputData, action, details.getUser())) {
                    new Info(inputData, output, null, null);
                }
            }
        }
        if (action.getFeature().equals("buy premium account")) {
            if (details.getPage().equals("upgrades")) {
                if (!new Upgrades().buyPremium(details.getUser())) {
                    new Info(inputData, output, null, null);
                }
            }
        }
        if (action.getFeature().equals("purchase")) {
            if (details.getPage().equals("see details") && details.getPage().equals("see details")) {
                if (!new SeeDetails().purchase(details.getMovie(), details.getUser())) {
                    new Info(inputData, output, null, null);
                } else {
                    ArrayList<Movie> listMovie = new ArrayList<>();
                    listMovie.add(details.getMovie());
                    new Info(inputData, output, details.getUser(), listMovie);
                }
            } else {
                new Info(inputData, output, null, null);
            }
        }
        if (action.getFeature().equals("watch")) {
            if (details.getPage().equals("see details") && details.getPage().equals("see details")) {
                if (!new SeeDetails().watch(details.getMovie(), details.getUser())) {
                    new Info(inputData, output, null, null);
                } else {
                    ArrayList<Movie> listMovie = new ArrayList<>();
                    listMovie.add(details.getMovie());
                    new Info(inputData, output, details.getUser(), listMovie);
                }
            } else {
                new Info(inputData, output, null, null);
            }
        }
        if (action.getFeature().equals("like")) {
            if (details.getPage().equals("see details") && details.getPage().equals("see details")) {
                if (!new SeeDetails().like(details.getMovie(), details.getUser())) {
                    new Info(inputData, output, null, null);
                } else {
                    ArrayList<Movie> listMovie = new ArrayList<>();
                    listMovie.add(details.getMovie());
                    new Info(inputData, output, details.getUser(), listMovie);
                }
            } else {
                new Info(inputData, output, null, null);
            }
        }
        if (action.getFeature().equals("rate")) {
            if (details.getPage().equals("see details") && details.getPage().equals("see details")) {
                if (!new SeeDetails().rate(details.getMovie(), details.getUser(), action)) {
                    new Info(inputData, output, null, null);
                } else {
                    ArrayList<Movie> listMovie = new ArrayList<>();
                    listMovie.add(details.getMovie());
                    new Info(inputData, output, details.getUser(), listMovie);
                }
            }  else {
                new Info(inputData, output, null, null);
            }
        }
    }
    public void action(Input inputData, ArrayNode output) {
        PageDetails details = new PageDetails();
        details.setPage("Homepage neautentificat");
        for (Action action : inputData.getActions()) {
            if (action.getType().equals("change page")) {
                String savePage = details.getPage();
                details.setPage(changePage(inputData, output, action, details));
                if (details.getPage().equals(savePage) && !savePage.equals(action.getPage())) {
                    new Info(inputData, output, null, null);
                }
                if (details.getPage().equals("Homepage neautentificat")) {
                    details.setUser(null);
                }
            }
            if (action.getType().equals("on page")) {
                onPage(inputData, output,
                        action, details);
            }
        }
    }
}
