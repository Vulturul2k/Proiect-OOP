package admin;

import input.Action;
import input.Input;
import input.Movie;
import pageactions.PageDetails;

public class Delete {
    public Delete(Action action, Input input) {
        for (Movie movie : input.getMovies()) {
            if (movie.getName().equals(action.getDeletedMovie())) {
                input.getMovies().remove(movie);
            }
            break;
        }
    }
}
