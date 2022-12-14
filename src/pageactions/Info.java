package pageactions;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import input.Credentials;
import input.Movie;
import input.User;

import java.util.ArrayList;

public final class Info {
    private static void showMovie(final ArrayNode output, final Movie movie) {
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
        jsonNode.put("rating", movie.getRating());
        jsonNode.put("numRatings", movie.getNumRatings());

    }

    private void showCredentials(final Credentials credentials, final ObjectNode users) {
        ObjectNode infoUsers = users.putObject("credentials");
        infoUsers.put("name", credentials.getName());
        infoUsers.put("password", credentials.getPassword());
        infoUsers.put("accountType", credentials.getAccountType());
        infoUsers.put("country", credentials.getCountry());
        infoUsers.put("balance", credentials.getBalance());
    }

    public Info(final ArrayNode output, final User user, final ArrayList<Movie> userMovie) {
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
                    showMovie(out, movie);
                }
            }
        }

        if (user != null) {
            ObjectNode users = jsonNode.putObject("currentUser");
            showCredentials(user.getCredentials(), users);
            users.put("tokensCount", user.getTokensCount());
            users.put("numFreePremiumMovies", user.getNumFreePremiumMovies());
            ArrayNode movieInfo = users.putArray("purchasedMovies");
            if (user.getPurchasedMovies() != null) {
                for (Movie movie : user.getPurchasedMovies()) {
                    showMovie(movieInfo, movie);
                }
            }

            ArrayNode movieInfoWatch = users.putArray("watchedMovies");
            if (user.getWatchedMovies() != null) {
                for (Movie movie : user.getWatchedMovies()) {
                    showMovie(movieInfoWatch, movie);
                }
            }

            ArrayNode movieInfoLike = users.putArray("likedMovies");
            if (user.getLikedMovies() != null) {
                for (Movie movie : user.getLikedMovies()) {
                    showMovie(movieInfoLike, movie);
                }
            }
            ArrayNode movieInfoRate = users.putArray("ratedMovies");
            if (user.getRatedMovies() != null) {
                for (Movie movie : user.getRatedMovies()) {
                    showMovie(movieInfoRate, movie);
                }
            }
        } else {
            jsonNode.put("currentUser", (ObjectNode) null);
        }
    }
}
