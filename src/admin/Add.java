package admin;

import input.Action;
import input.Input;
import input.Movie;
import input.User;
import pageactions.PageDetails;

import java.util.ArrayList;

public final class Add {
    private static Add instance = new Add();
    private Add() {
    }
    public static Add getInstance() {
        return instance;
    }
    public boolean add(final Action action, final Input input, final PageDetails details) {
        for (Movie movies : input.getMovies()) {
            if (movies.getName().equals(action.getAddedMovie().getName())) {
                return false;
            }
        }
        input.getMovies().add(action.getAddedMovie());
        for (User user : input.getUsers()) {
            if (user.getSubscribe() != null) {
                for (String gen : user.getSubscribe()) {
                    for (String movieGen : details.getMovie().getGenres()) {
                        if (gen.equals(movieGen)) {
                            if (details.getUser().getMessage() == null) {
                                details.getUser().setMessage(new ArrayList<>());
                                details.getUser().setMovieName(new ArrayList<>());
                            }
                            user.getMessage().add("ADD");
                            user.getMovieName().add(action.getAddedMovie().getName());
                        }
                    }
                }
            }
        }
        return true;
    }
}
