package page.Actions;

        import com.fasterxml.jackson.databind.node.ArrayNode;
        import input.Action;
        import input.Input;
        import input.Movie;
        import pages.MoviePage;
        import pages.Register;
        import pages.SeeDetails;
        import pages.Upgrades;
        import pages.Login;
        import pages.Search;
        import pages.Filter;


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
        if (action.getPage().equals("login")) {
            if (Login.getInstance().changePage(details)) {
                return "login";
            }
        }
        if (action.getPage().equals("register")) {
            if (Register.getInstance().changePage(details)) {
                return "register";
            }
        }
        if (action.getPage().equals("logout")) {
            if (details.getPage().equals("Homepage autentificat")
                    || details.getPage().equals("see details")
                    || details.getPage().equals("movies")) {
                return "Homepage neautentificat";
            }
        }
        if (action.getPage().equals("movies")) {
            if (MoviePage.getInstance().moviePage(inputData, details)) {
                new Info(output, details.getUser(), details.getMovieList());
                return "movies";
            }
        }
        if (action.getPage().equals("see details")) {
            if (details.getPage().equals("movies")) {
                if (seeDetails.seeDetails(inputData, action, details)) {
                    new Info(output, details.getUser(), details.getMovieList());
                    return "see details";
                }
            }
        }
        if (action.getPage().equals("upgrades")) {
            if (Upgrades.getInstance().changePage(details)) {
                return "upgrades";
            }
        }
        return details.getPage();
    }
    private boolean onPage(final Input inputData, final ArrayNode output, final Action action,
                           final PageDetails details, final SeeDetails seeDetails) {
        if (action.getFeature().equals("login") || action.getFeature().equals("register")) {
            if (details.getPage().equals("login") || details.getPage().equals("register")) {
                if (action.getFeature().equals("login")) {
                    details.setUser(Login.getInstance().login(inputData, action));
                }
                if (action.getFeature().equals("register")) {
                    details.setUser(Register.getInstance().login(inputData, action));
                }
                if (details.getUser() != null) {
                    details.setPage("Homepage autentificat");
                } else {
                    details.setPage("Homepage neautentificat");
                }
                new Info(output, details.getUser(), null);
                return true;
            }
        }
        if (action.getFeature().equals("search")) {
            Search.getInstance().action(inputData, action, output, details);
            return true;
        }
        if (action.getFeature().equals("filter")) {
            Filter.getInstance().action(inputData, action, output, details);
            return true;
        }
        if (action.getFeature().equals("buy tokens")) {
            if (details.getPage().equals("upgrades")) {
                if (Upgrades.getInstance().buyTokens(action, details.getUser())) {
                    return true;
                }
            }
        }
        if (action.getFeature().equals("buy premium account")) {
            if (details.getPage().equals("upgrades")) {
                if (Upgrades.getInstance().buyPremium(details.getUser())) {
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

    /**
     * This method iterate through actions
     * @param inputData this is the input
     * @param output where will be the output
     */
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
