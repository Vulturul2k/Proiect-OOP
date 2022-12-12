package page.Actions;

        import com.fasterxml.jackson.databind.node.ArrayNode;
        import input.Action;
        import input.Input;
        import input.Movie;
        import pages.Login;
        import pages.Register;
        import pages.MoviePage;
        import pages.SeeDetails;
        import pages.Upgrades;
        import java.util.ArrayList;


public final class PageActions {
    private static PageActions instance = new PageActions();
    private PageActions() {
    }
    public static PageActions getInstance() {
        return instance;
    }

    private String changePage(final Input inputData, final ArrayNode output, final Action action,
                              final PageDetails details, final SeeDetails seeDetails) {
        if (action.getPage().equals("login") || action.getPage().equals("register")) {
            Login login = new Login();
            if (login.changePage(details.getPage(), action.getPage())) {
                return action.getPage();
            }
        }
        if (action.getPage().equals("logout")) {
            if (details.getPage().equals("logged")  || details.getPage().equals("see details")
                    || details.getPage().equals("movies")) {
                return "Homepage neautentificat";
            }
        }
        if (action.getPage().equals("movies")) {
            boolean ok = MoviePage.getInstance().moviePage(details.getPage(),
                    inputData, details.getUser(), output);
            if (ok) {
                return "movies";
            }
        }
        if (action.getPage().equals("see details")) {
            if (details.getPage().equals("movies")) {
                return seeDetails.seeDetails(inputData, action,
                        output, details);
            }
        }
        if (action.getPage().equals("upgrades")) {
            return new Upgrades().changePage(details.getPage());
        }
        return details.getPage();
    }
    private boolean onPage(final Input inputData, final ArrayNode output, final Action action,
                           final PageDetails details, final SeeDetails seeDetails) {
        if (action.getFeature().equals("login") || action.getFeature().equals("register")) {
            if (details.getPage().equals("login") || details.getPage().equals("register")) {
                if (action.getFeature().equals("login")) {
                    Login login = new Login();
                    details.setUser(login.login(inputData, action));
                }
                if (action.getFeature().equals("register")) {
                    Register register = new Register();
                    details.setUser(register.register(inputData, action));
                }
                if (details.getUser() != null) {
                    details.setPage("logged");
                } else {
                    details.setPage("Homepage neautentificat");
                }
                new Info(output, details.getUser(), null);
                return true;
            }
        }
        if (action.getFeature().equals("search")) {
            MoviePage.getInstance().search(inputData, action,
                    details.getUser(), output, details.getPage());
            return true;
        }
        if (action.getFeature().equals("filter")) {
            MoviePage.getInstance().filter(inputData, action, output, details);
            return true;
        }
        if (action.getFeature().equals("buy tokens")) {
            if (details.getPage().equals("upgrades")) {
                if (new Upgrades().buyTokens(action, details.getUser())) {
                    return true;
                }
            }
        }
        if (action.getFeature().equals("buy premium account")) {
            if (details.getPage().equals("upgrades")) {
                if (new Upgrades().buyPremium(details.getUser())) {
                    return true;
                }
            }
        }
        if (action.getFeature().equals("purchase")) {
            if (details.getPage().equals("see details")) {
                if (seeDetails.purchase(details.getMovie(), details.getUser())) {
                    ArrayList<Movie> listMovie = new ArrayList<>();
                    listMovie.add(details.getMovie());
                    new Info(output, details.getUser(), listMovie);
                    return true;
                }
            }
        }
        if (action.getFeature().equals("watch")) {
            if (details.getPage().equals("see details")) {
                if (seeDetails.watch(details.getMovie(), details.getUser())) {
                    ArrayList<Movie> listMovie = new ArrayList<>();
                    listMovie.add(details.getMovie());
                    new Info(output, details.getUser(), listMovie);
                    return true;
                }
            }
        }
        if (action.getFeature().equals("like")) {
            if (details.getPage().equals("see details")) {
                if (seeDetails.like(details.getMovie(), details.getUser())) {
                    ArrayList<Movie> listMovie = new ArrayList<>();
                    listMovie.add(details.getMovie());
                    new Info(output, details.getUser(), listMovie);
                    return true;
                }
            }
        }
        if (action.getFeature().equals("rate")) {
            if (details.getPage().equals("see details")) {
                if (seeDetails.rate(details.getMovie(), details.getUser(), action)) {
                    ArrayList<Movie> listMovie = new ArrayList<>();
                    listMovie.add(details.getMovie());
                    new Info(output, details.getUser(), listMovie);
                    return true;
                }
            }
        }
        return false;
    }
    public void action(final Input inputData, final ArrayNode output) {
        SeeDetails seeDetails = SeeDetails.getINSTANCE();
        PageDetails details = PageDetails.getInsstance();
        details.setPage("Homepage neautentificat");
        for (Action action : inputData.getActions()) {
            if (action.getType().equals("change page")) {
                String savePage = details.getPage();
                details.setPage(changePage(inputData, output, action, details, seeDetails));
                if (details.getPage().equals(savePage) && !savePage.equals(action.getPage())) {
                    new Info(output, null, null);
                }
                if (!details.getPage().equals("movies") && details.getMovieList() != null) {
                    details.setMovieList(null);
                }
                if (details.getPage().equals("Homepage neautentificat")) {
                    details.setUser(null);
                }
            }
            if (action.getType().equals("on page")) {
                if (!onPage(inputData, output,
                        action, details, seeDetails)) {
                    new Info(output, null, null);
                }
            }
        }
    }
}
