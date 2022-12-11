package login_register;

import input.Movie;
import input.User;

public class PageDetails {
    String page;
    User user;
    Movie movie;

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }
}
