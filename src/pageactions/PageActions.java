package pageactions;

import admin.Add;
import admin.Delete;
import com.fasterxml.jackson.databind.node.ArrayNode;
import input.Action;
import input.Input;
import input.Movie;
import intermediatepages.Login;
import intermediatepages.Register;
import intermediatepages.Upgrades;
import mainpages.Filter;
import mainpages.MoviePage;
import mainpages.Search;
import mainpages.Subscribe;
import mainpages.Rate;
import mainpages.Like;
import mainpages.SeeDetails;
import mainpages.Purchase;
import mainpages.Watch;

import java.util.ArrayList;


public final class PageActions {
    private static PageActions instance = new PageActions();
    private PageActions() {
    }
    public static PageActions getInstance() {
        return instance;
    }

    public String changePage(final Input inputData, final ArrayNode output,
                              final PageDetails details,
                              final boolean back) {
        if (details.getAction().getPage().equals("login")) {
            if (new Login().changePage(details)) {
                return "login";
            }
        }
        if (details.getAction().getPage().equals("register")) {
            if (Register.getInstance().changePage(details)) {
                return "register";
            }
        }
        if (details.getAction().getPage().equals("logout")) {
            if (details.getPage().equals("Homepage autentificat")
                    || details.getPage().equals("see details")
                    || details.getPage().equals("movies")) {
                return "Homepage neautentificat";
            }
        }
        if (details.getAction().getPage().equals("movies")) {
            if (MoviePage.getInstance().nextPage(inputData, details) || back) {
                new Info(output, details.getUser(), details.getMovieList());
                return "movies";
            }
        }
        if (details.getAction().getPage().equals("see details")) {
            if (details.getPage().equals("movies")) {
                if (SeeDetails.getINSTANCE().nextPage(inputData, details) || back) {
                    new Info(output, details.getUser(), details.getMovieList());
                    return "see details";
                }
            }
        }
        if (details.getAction().getPage().equals("upgrades")) {
            if (Upgrades.getInstance().changePage(details) || back) {
                return "upgrades";
            }
        }
        return details.getPage();
    }
    private boolean onPage(final Input inputData, final ArrayNode output,
                           final PageDetails details) {
        if (details.getAction().getFeature().equals("login")
                || details.getAction().getFeature().equals("register")) {
            if (details.getPage().equals("login") || details.getPage().equals("register")) {
                if (details.getAction().getFeature().equals("login")) {
                    details.setUser(new Login().login(inputData, details));
                }
                if (details.getAction().getFeature().equals("register")) {
                    details.setUser(Register.getInstance().login(inputData, details));
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
        if (details.getAction().getFeature().equals("search")) {
            Search.getInstance().action(inputData, details.getAction(), output, details);
            return true;
        }
        if (details.getAction().getFeature().equals("filter")) {
            Filter.getInstance().action(inputData, details.getAction(), output, details);
            return true;
        }
        if (details.getAction().getFeature().equals("buy tokens")) {
            if (details.getPage().equals("upgrades")) {
                if (Upgrades.getInstance().buyTokens(details)) {
                    return true;
                }
            }
        }
        if (details.getAction().getFeature().equals("buy premium account")) {
            if (details.getPage().equals("upgrades")) {
                if (Upgrades.getInstance().buyPremium(details.getUser())) {
                    return true;
                }
            }
        }
        if (details.getAction().getFeature().equals("purchase")) {
            if (details.getPage().equals("see details")) {
                if (Purchase.purchase(details.getMovie(), details)) {
                    ArrayList<Movie> listMovie = new ArrayList<>();
                    listMovie.add(details.getMovie());
                    new Info(output, details.getUser(), listMovie);
                    return true;
                }
            }
        }
        if (details.getAction().getFeature().equals("watch")) {
            if (details.getPage().equals("see details")) {
                if (Watch.watch(details.getMovie(), details)) {
                    ArrayList<Movie> listMovie = new ArrayList<>();
                    listMovie.add(details.getMovie());
                    new Info(output, details.getUser(), listMovie);
                    return true;
                }
            }
        }
        if (details.getAction().getFeature().equals("like")) {
            if (details.getPage().equals("see details")) {
                if (Like.like(details.getMovie(), details)) {
                    ArrayList<Movie> listMovie = new ArrayList<>();
                    listMovie.add(details.getMovie());
                    new Info(output, details.getUser(), listMovie);
                    return true;
                }
            }
        }
        if (details.getAction().getFeature().equals("rate")) {
            if (details.getPage().equals("see details")) {
                if (Rate.rate(details.getMovie(), details)) {
                    ArrayList<Movie> listMovie = new ArrayList<>();
                    listMovie.add(details.getMovie());
                    new Info(output, details.getUser(), listMovie);
                    return true;
                }
            }
        }
        if (details.getAction().getFeature().equals("subscribe")) {
            if (details.getPage().equals("see details")) {
                return Subscribe.subscribe(details.getMovie(), details);
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
        Pages pages = new Pages();
        for (Action action : inputData.getActions()) {
            details.setAction(action);
            String savePage = details.getPage();
            if (action.getType().equals("change page")) {
                details.setPage(changePage(inputData, output, details, false));
                if (details.getPage().equals(savePage) && !savePage.equals(action.getPage())) {
                    new Info(output, null, null);
                } else {
                    if (!savePage.equals("login")
                            && !savePage.equals("register")
                            && !savePage.equals("Homepage neautentificat")) {
                        pages.add(savePage);
                    }
                }
                if (!details.getPage().equals("movies") && details.getMovieList() != null) {
                    details.setMovieList(null);
                }
                if (details.getPage().equals("Homepage neautentificat")) {
                    details.setUser(null);
                    pages.clear();
                }
            }
            if (action.getType().equals("on page")) {
                if (!onPage(inputData, output,
                        details)) {
                    new Info(output, null, null);
                }
            }
            if (action.getType().equals("database")) {
                if (action.getFeature().equals("delete")) {
                    new Delete(action, inputData);
                }
                if (action.getFeature().equals("add")) {
                    if (!Add.getInstance().add(action, inputData, details)) {
                        new Info(output, null, null);
                    }
                }
            }
            if (action.getType().equals("back")) {
                if (details.getPage().equals("Homepage autentificat")) {
                    new Info(output, null, null);
                } else {
                   pages.undo(details, inputData, output);
                }
            }
        }
        if (details.getUser() != null
                && details.getUser().getCredentials().getAccountType().equals("premium")) {
            new Recommendation(details, inputData, output);
        }
    }
}
