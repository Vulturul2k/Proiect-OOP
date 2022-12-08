package login_register;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import input.Action;
import input.Credentials;
import input.Input;


public class Login_register {
    private static void info(Input inputData, ArrayNode output, Credentials user) {
        ObjectNode jsonNode = output.addObject();
        jsonNode.put("error" , "Error");
        ArrayNode out = jsonNode.putArray("currentMoviesList");
        /*for (Movie movie : inputData.getMovies()) {
            out.add(movie.getName());
        }*/

        if (user != null) {
            ObjectNode users = jsonNode.putObject("currentUser");
            ObjectNode info_users = users.putObject("credentials");
            info_users.put("name", user.getCredentials().getName());
            info_users.put("password", user.getCredentials().getPassword());
            info_users.put("accountType", user.getCredentials().getAccountType());
            info_users.put("country", user.getCredentials().getCountry());
            info_users.put("balance", user.getCredentials().getBalance());
            users.put("tokensCount", user.getTokensCount());
            users.put("numFreePremiumMovies", user.getNumFreePremiumMovies());
            ArrayNode movie_info = users.putArray("purchasedMovies");
            if (user.getPurchasedMovies() != null) {
                for (String movie : user.getPurchasedMovies()) {
                    movie_info.add(movie);
                }
            }

            ArrayNode movie_info_watch = users.putArray("watchedMovies");
            for (String movie : user.getWatchedMovies()) {
                movie_info_watch.add(movie);
            }
            ArrayNode movie_info_like = users.putArray("likedMovies");
            for (String movie : user.getLikedMovies()) {
                movie_info_like.add(movie);
            }
            ArrayNode movie_info_rate = users.putArray("ratedMovies");
            for (String movie : user.getRatedMovies()) {
                movie_info_rate.add(movie);
            }
        } else {
            jsonNode.put("currentUser" , (ObjectNode) null);
        }
    }
    public Login_register(Input inputData, ArrayNode output) {
        String page = "Homepage neautentificat";
        Credentials user;
        for (Action action : inputData.getActions()) {
            if (action.getType().equals("change page")) {
                if (action.getPage().equals("logout")) {
                    if (!page.equals("logged")) {
                        info(inputData, output, null);
                    }
                }
                if (page.equals("Homepage neautentificat")) {
                    if (action.getPage().equals("login") || action.getPage().equals("register")) {
                        page = action.getPage();
                    } else {

                    }
                }
            }
            if (action.getType().equals("on page")) {
                if (action.getFeature().equals("login")) {
                    if (page.equals("login")) {
                        boolean ok = false;
                        for (Credentials person: inputData.getUsers()) {
                            if (person.getCredentials().getName().equals(action.getCredentials().getName())) {
                                if (person.getCredentials().getPassword().equals(action.getCredentials().getPassword())) {
                                    user = person;
                                    page = "logged";
                                    info(inputData, output, person);
                                    ok = true;
                                }
                                break;
                            }
                        }
                        if (!ok) {
                            page = "Homepage neautentificat";
                            info(inputData, output, null);
                        }
                    } else {
                        info(inputData, output, null);
                    }
                }

            }
        }
    }
}
