package movie;

import com.fasterxml.jackson.databind.node.ArrayNode;
import input.Action;
import input.Input;
import input.Movie;
import input.User;
import login_register.Info;

import java.util.ArrayList;

public class SeeDetails {
    public SeeDetails(Input inputData, Action action, User user,
                ArrayNode output, String page) {
        if (user == null) {
            return;
        }
        ArrayList<Movie> movieList = new MoviePage().userMovies(inputData, user);
        ArrayList<Movie> userMovies = new ArrayList<>();
        Movie movie = findMovie(movieList, action);
        if (movie != null) {
            userMovies.add(movie);
            new Info(inputData, output, user, userMovies);
        } else {
            new Info(inputData, output, null, null);
        }
    }
    private Movie findMovie(ArrayList<Movie> movieList, Action action) {
        for (Movie movie : movieList) {
            if (movie.getName().equals(action.getMovie())) {
                return movie;
            }
        }
        return null;
    }
}
