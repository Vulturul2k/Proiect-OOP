package login_register;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import input.Credentials;
import input.Input;
import input.Movie;
import input.User;

import java.util.ArrayList;

public class Info {
    private static void showMovie(Input inputData, ArrayNode output, Movie movie) {
        ObjectNode jsonNode = output.addObject();
        jsonNode.put("name", movie.getName());
        jsonNode.put("year", movie.getYear());
        jsonNode.put("duration", movie.getDuration());
        ArrayNode movieGenresNod = jsonNode.putArray("genres");
        if (movie.getGenres() != null) {
            for (String movieGenres : movie.getGenres()) {
                movieGenresNod.add(movieGenres);
            }
        }
        ArrayNode actor = jsonNode.putArray("actors");
        if (movie.getActors() != null) {
            for (String movieActors : movie.getActors()) {
                actor.add(movieActors);
            }
        }
        ArrayNode countriesBanned = jsonNode.putArray("countriesBanned");
        if (movie.getActors() != null) {
            for (String country : movie.getCountriesBanned()) {
                countriesBanned.add(country);
            }
        }
        jsonNode.put("numLikes", movie.getNumLikes());
        jsonNode.put("rating",movie.getRating());
        jsonNode.put("numRatings", movie.getNumRatings());

    }

    private void showCredentials(Credentials credentials, ObjectNode users) {
        ObjectNode info_users = users.putObject("credentials");
        info_users.put("name", credentials.getName());
        info_users.put("password", credentials.getPassword());
        info_users.put("accountType", credentials.getAccountType());
        info_users.put("country", credentials.getCountry());
        info_users.put("balance", credentials.getBalance());
    }

    public Info(Input inputData, ArrayNode output, User user, ArrayList<Movie> userMovie) {
        ObjectNode jsonNode = output.addObject();
        if (user == null) {
            jsonNode.put("error", "Error");
        } else {
            jsonNode.put("error", (ObjectNode) null);
        }

        ArrayNode out = jsonNode.putArray("currentMoviesList");
        if (userMovie != null) {
            if (user != null) {
                for (Movie movie : userMovie) {
                    showMovie(inputData, out, movie);
                }
            }
        }

        if (user != null) {
            ObjectNode users = jsonNode.putObject("currentUser");
            showCredentials (user.getCredentials(), users);
            users.put("tokensCount", user.getTokensCount());
            users.put("numFreePremiumMovies", user.getNumFreePremiumMovies());
            ArrayNode movieInfo = users.putArray("purchasedMovies");
            if (user.getPurchasedMovies() != null) {
                for (Movie movie : user.getPurchasedMovies()) {
                    showMovie(inputData, movieInfo, movie);
                }
            }

            ArrayNode movieInfoWatch = users.putArray("watchedMovies");
            if (user.getWatchedMovies() != null) {
                for (Movie movie : user.getWatchedMovies()) {
                    showMovie(inputData, movieInfoWatch, movie);
                }
            }

            ArrayNode movieInfoLike = users.putArray("likedMovies");
            if (user.getLikedMovies() != null) {
                for (Movie movie : user.getLikedMovies()) {
                    showMovie(inputData, movieInfoLike, movie);
                }
            }
            ArrayNode movieInfoRate = users.putArray("ratedMovies");
            if (user.getRatedMovies() != null) {
                for (Movie movie : user.getRatedMovies()) {
                    showMovie(inputData, movieInfoRate, movie);
                }
            }
        } else {
            jsonNode.put("currentUser", (ObjectNode) null);
        }
    }
}
