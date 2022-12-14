package main.Pages;

import com.fasterxml.jackson.databind.node.ArrayNode;
import input.Action;
import input.Input;
import input.Movie;
import page.Actions.Info;
import page.Actions.PageDetails;

import java.util.ArrayList;

public final class Filter implements MoviePageActions {
    private static Filter instance = null;

    private Filter() {
    }

    /**
     * getter for instance
     * @return instance
     */
    public static Filter getInstance() {
        if (instance == null) {
            instance = new Filter();
        }
        return instance;
    }
    /**
     * it gives us the movies that only contain certain criteria
     * @param action give us the input
     * @param userMovie it"s the main list with movies
     * @return the new list with movies
     */
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

    /**
     * This method sort the movies with some criteria
     * @param action tell us how to sort them
     * @param userMovie this is the list with movie that will be sorted
     * @return the sorted list
     */
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

    /**
     * this method filters movies according to certain criteria
     * @param inputData give us the database
     * @param action tell us the criteria
     * @param output it is where will be the output
     * @param details give us details about the page like
     *                the current user or the current page
     */
    public void action(final Input inputData, final Action action,
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
            userMovie = sort(action,
                    MoviePage.getInstance().userMovies(inputData, details.getUser()));
        } else {
            userMovie = MoviePage.getInstance().userMovies(inputData, details.getUser());
        }
        if (action.getFilters().getContains() != null) {
            details.setMovieList(contains(action, userMovie));
        } else {
            details.setMovieList(userMovie);
        }
        new Info(output, details.getUser(), details.getMovieList());
    }
}
