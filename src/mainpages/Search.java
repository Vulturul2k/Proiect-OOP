package mainpages;

import com.fasterxml.jackson.databind.node.ArrayNode;
import input.Action;
import input.Input;
import input.Movie;
import pageactions.Info;
import pageactions.PageDetails;

import java.util.ArrayList;

public final class Search implements MoviePageActions {
    private static Search instance = null;

    private Search() {
    }

    /**
     * getter
     * @return instance
     */
    public static Search getInstance() {
        if (instance == null) {
            instance = new Search();
        }
        return instance;
    }
    private ArrayList<Movie> searchMovies(final Input inputData, final Action action) {
        ArrayList<Movie> userMovie = new ArrayList<>();
        for (Movie movie : inputData.getMovies()) {
            if (movie.getName().startsWith(action.getStartsWith())) {
                userMovie.add(movie);
            }
        }
        return userMovie;
    }
    /**
     * This method search every movie who start with some characters
     * @param inputData give us the database
     * @param action give us the input
     * @param output where will be the output
     * @param details it get us the current user and page
     */
    public void action(final Input inputData, final Action action,
                       final ArrayNode output, final PageDetails details) {
        if (!details.getPage().equals("movies")) {
            new Info(output, null, null);
            return;
        }
        new Info(output, details.getUser(), searchMovies(inputData, action));
    }
}
