package main.Pages;

import input.Input;
import input.Movie;
import input.User;
import page.Actions.PageDetails;

import java.util.ArrayList;

public final class MoviePage implements MainPages {
    private static MoviePage instance = null;
    private MoviePage() {
    }

    /**
     * this is a getter
     * @return the instance
     */
    public static MoviePage getInstance() {
        if (instance == null) {
            instance = new MoviePage();
        }
        return instance;
    }

    /**
     * it gives us the movies that a user can watch
     * @param inputData give the list with all the movies
     * @param user give us the current user
     * @return the list with movies
     */
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

    /**
     * this method take us to a page where we can see all the movies
     * @param inputData this is the database with all the movies
     * @param details give us the current user and the current page
     * @return if the page could be changed
     */
    public boolean nextPage(final Input inputData,
                             final PageDetails details) {
        if (!details.getPage().equals("Homepage autentificat")
                && !details.getPage().equals("see details")
                && !details.getPage().equals("upgrades") && !details.getPage().equals("movies")) {
            return false;
        }
        ArrayList<Movie> userMovie = userMovies(inputData, details.getUser());
        details.setMovieList(userMovie);
        return true;
    }
}
