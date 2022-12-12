package pages;

import com.fasterxml.jackson.databind.node.ArrayNode;
import input.Action;
import input.Input;
import input.Movie;
import input.User;
import page.Actions.Info;
import page.Actions.PageDetails;

import java.util.ArrayList;

public final class MoviePage {
    private static MoviePage instance = null;
    private MoviePage() {
    }

    public static MoviePage getInstance() {
        if (instance == null) {
            instance = new MoviePage();
        }
        return instance;
    }

    public void search(final Input inputData, final Action action, final User user,
                       final ArrayNode output, final String page) {
        if (!page.equals("movies")) {
            new Info(output, null, null);
            return;
        }
        ArrayList<Movie> userMovie = new ArrayList<>();
        for (Movie movie : inputData.getMovies()) {
            if (movie.getName().startsWith(action.getStartsWith())) {
                userMovie.add(movie);
                break;
            }
        }
        new Info(output, user, userMovie);
    }
    private ArrayList<Movie> contains(final Action action, final ArrayList<Movie> userMovie) {
            ArrayList<Movie> newUserMovie = new ArrayList<>();
            for (Movie movie : userMovie) {
                boolean containsActors = true;
                boolean containsGenres = true;
                if (action.getFilters().getContains().getActors() != null) {
                    containsActors = false;
                    for (String actor : action.getFilters().getContains().getActors()) {
                        if (movie.getActors().contains(actor)) {
                            containsActors = true;
                            break;
                        }
                    }
                }
                if (action.getFilters().getContains().getGenre() != null) {
                    containsGenres = false;
                    for (String genre : action.getFilters().getContains().getGenre()) {
                        if (movie.getGenres().contains(genre)) {
                            containsGenres = true;
                            break;
                        }
                    }
                }
                if (containsActors && containsGenres) {
                    newUserMovie.add(movie);
                }
            }
        return newUserMovie;
    }
    private ArrayList<Movie> sort(final Action action, final ArrayList<Movie> userMovie) {
        userMovie.sort((o1, o2) -> {

            if (action.getFilters().getSort().getDuration() == null
                    || o1.getDuration() == o2.getDuration()) {
                if (action.getFilters().getSort().getRating() == null) {
                    return 0;
                }
                if (action.getFilters().getSort().getRating().equals("decreasing")) {
                    return (int) ((o2.getRating()) - o1.getRating());
                } else {
                    return (int) (o1.getRating() - o2.getRating());
                }
            }
            if (action.getFilters().getSort().getDuration().equals("decreasing")) {
                return o2.getDuration() - o1.getDuration();
            } else {
                return o1.getDuration() - o2.getDuration();
            }
        });
        return userMovie;
    }
    public void filter(final Input inputData, final Action action,
                       final ArrayNode output, final PageDetails details) {
        if (!details.getPage().equals("movies")) {
            new Info(output, null, null);
            return;
        }
        if (details.getUser() == null) {
            return;
        }
        ArrayList<Movie> userMovie;
        if (action.getFilters().getSort() != null) {
            userMovie = sort(action, userMovies(inputData, details.getUser()));
        } else {
            userMovie = userMovies(inputData, details.getUser());
        }
        if (action.getFilters().getContains() != null) {
            details.setMovieList(contains(action, userMovie));
        } else {
            details.setMovieList(userMovie);
        }
        new Info(output, details.getUser(), details.getMovieList());
    }
    public ArrayList<Movie> userMovies(final Input inputData, final User user) {
        ArrayList<Movie> userMovie = new ArrayList<>();
        for (Movie movie : inputData.getMovies()) {
            boolean ok = true;
            for (String country : movie.getCountriesBanned()) {
                if (user.getCredentials().getCountry().equals(country)) {
                    ok = false;
                    break;
                }
            }
            if (ok) {
                userMovie.add(movie);
            }
        }
        return userMovie;
    }
    public boolean moviePage(final String page, final Input inputData, final User user,
                             final ArrayNode output) {
        if (user == null) {
            return false;
        }
        if (!page.equals("logged") && !page.equals("see details")
                && !page.equals("upgrades") && !page.equals("movies")) {
            return false;
        }
        ArrayList<Movie> userMovie = userMovies(inputData, user);
        new Info(output, user, userMovie);
        return true;
    }
}
