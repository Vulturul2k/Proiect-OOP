package movie;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import input.Action;
import input.Input;
import input.Movie;
import input.User;
import login_register.Info;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class MoviePage {

    public void search(Input inputData, Action action, User user,
                        ArrayNode output, String page) {
        if (!page.equals("movies")) {
            new Info(inputData, output, null, null);
            return;
        }
        ArrayList<Movie> userMovie = new ArrayList<>();
        for (Movie movie : inputData.getMovies()) {
            if (movie.getName().startsWith(action.getStartsWith())) {
                userMovie.add(movie);
                break;
            }
        }
        new Info(inputData, output, user, userMovie);
    }


    public void filter(Input inputData, Action action, User user,
                        ArrayNode output, String page) {
        if (!page.equals("movies")) {
            new Info(inputData, output, null, null);
            return;
        }
        if (user == null) {
            return;
        }
        ArrayList<Movie> userMovie = userMovies(inputData, user);
        if (action.getFilters().getSort() != null) {
            userMovie.sort((o1, o2) -> {

                if (action.getFilters().getSort().getDuration() == null || o1.getDuration() == o2.getDuration()) {
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
        }
        new Info(inputData, output, user, userMovie);
    }
    public ArrayList<Movie> userMovies(Input inputData, User user) {
        ArrayList<Movie> userMovie = new ArrayList<>();
        for (Movie movie : inputData.getMovies()) {
            boolean ok = true;
            for (String country : movie.getCountriesBanned()){
                if (user.getCredentials().getCountry().equals(country)){
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
    public boolean moviePage(String page, Input inputData, Action action, User user,
                               ArrayNode output) {
        if (user == null) {
            return false;
        }
        if (!page.equals("logged") && !page.equals("see details") && !page.equals("upgrades") && !page.equals("movies")) {
            return false;
        }
        ArrayList<Movie> userMovie = userMovies(inputData, user);
//        Info info = Info.getInfo();
//        info.show(inputData, output, user, userMovie);
        new Info(inputData, output, user, userMovie);
        return true;
    }
}