package admin;

import input.Action;
import input.Input;
import input.Movie;

public class Delete {
    public Delete(final Action action, final Input input) {
        for (Movie movie : input.getMovies()) {
            if (movie.getName().equals(action.getDeletedMovie())) {
                input.getMovies().remove(movie);
            }
            break;
        }
    }
}
