package page.Actions;

import input.Action;
import input.Movie;
import input.User;

import java.util.ArrayList;

public final class PageDetails {

    private  static PageDetails instance = new PageDetails();

    private PageDetails() {
    }

    public static PageDetails getInsstance() {
        return instance;
    }

    private String page;
    private User user;
    private Movie movie;
    private ArrayList<Movie> movieList;
    private Action action;

    public Action getAction() {
        return action;
    }

    public void setAction(final Action action) {
        this.action = action;
    }

    public ArrayList<Movie> getMovieList() {
        return movieList;
    }

    public void setMovieList(final ArrayList<Movie> movieList) {
        this.movieList = movieList;
    }

    public String getPage() {
        return page;
    }

    public void setPage(final String page) {
        this.page = page;
    }

    public User getUser() {
        return user;
    }

    public void setUser(final User user) {
        this.user = user;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(final Movie movie) {
        this.movie = movie;
    }
}
