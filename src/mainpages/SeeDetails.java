package mainpages;

import input.Action;
import input.Input;
import input.Movie;
import pageactions.PageDetails;

import java.util.ArrayList;

public final class SeeDetails implements MainPages {
    private static SeeDetails instance = null;
    private SeeDetails() {
    }

    /**
     * This is a getter
     * @return the instance
     */
    public static SeeDetails getINSTANCE() {
        if (instance == null) {
            instance = new SeeDetails();
        }
        return instance;
    }

    /**
     * This method takes us to page "see details" where we can see details about a movie
     * @param inputData give us all the movies
     * @param details give info about user and page
     * @return if you could go to this page
     */
    public boolean nextPage(final Input inputData,
                              final PageDetails details) {
        if (details.getMovieList() == null) {
            ArrayList<Movie> movieList = MoviePage.getInstance()
                    .userMovies(inputData, details.getUser());
            details.setMovieList(movieList);
        }
        ArrayList<Movie> userMovies = new ArrayList<>();
        Movie movie = findMovie(details.getMovieList(), details.getAction());
        if (movie != null) {
            userMovies.add(movie);
            details.setMovieList(userMovies);
            details.setMovie(movie);
            return true;
        }
        return false;
    }

    /**
     * Find a movie from name
     * @param movieList all the movies
     * @param action give the movie`s name
     * @return the movie
     */
    public Movie findMovie(final ArrayList<Movie> movieList, final Action action) {
        for (Movie movie : movieList) {
            if (movie.getName().equals(action.getMovie())) {
                return movie;
            }
        }
        return null;
    }
}
