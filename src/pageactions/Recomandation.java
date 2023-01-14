package pageactions;

import admin.GensLikes;
import com.fasterxml.jackson.databind.node.ArrayNode;
import input.Input;
import input.Movie;
import mainpages.MoviePage;

import java.util.ArrayList;

public class Recomandation {
    Recomandation(final PageDetails details, final Input inputData, final ArrayNode output) {
        if (details.getUser().getMessage() == null) {
            details.getUser().setMessage(new ArrayList<>());
            details.getUser().setMovieName(new ArrayList<>());
            System.out.println("ceva");
        }
        details.getUser().getMessage().add("Recommendation");
        ArrayList<Integer> gensLike = new ArrayList<>();
        ArrayList<String> gensName = new ArrayList<>();
        if (details.getUser().getLikedMovies() != null) {
            for (Movie movie : details.getUser().getLikedMovies()) {
                for (String gen : movie.getGenres()) {
                    if (gensName.contains(gen)) {
                        gensLike.set(gensName.indexOf(gen),
                                gensLike.get(gensName.indexOf(gen)) + 1);
                    } else {
                        gensName.add(gen);
                        int one = 1;
                        gensLike.add(one);
                    }
                }
            }
            ArrayList<GensLikes> gensObj = new ArrayList<>();
            for (int i = 0; i < gensLike.size(); i++) {
                GensLikes newGen = new GensLikes();
                newGen.setNames(gensName.get(i));
                newGen.setLikes(gensLike.get(i));
                gensObj.add(newGen);
            }
            ArrayList<GensLikes> movies = new ArrayList<>();
            ArrayList<Movie> userMovies =  MoviePage.getInstance()
                    .userMovies(inputData, details.getUser());
            for (Movie movie : userMovies) {
                boolean ok = true;
                for (Movie likedMovie : details.getUser().getLikedMovies()) {
                    if (movie.getName().equals(likedMovie.getName())) {
                        ok = false;
                        break;
                    }
                }
                if (ok) {
                    GensLikes movieLikes = new GensLikes();
                    movieLikes.setNames(movie.getName());
                    for (GensLikes obj : gensObj) {
                        if (movie.getGenres().contains(obj.getNames())) {
                            movieLikes.setLikes(obj.getLikes() + movieLikes.getLikes());
                        }
                    }
                    movies.add(movieLikes);
                }
            }
            movies.sort((o1, o2) -> {

                if (o1.getLikes() == o2.getLikes()) {
                    return -o1.getNames().compareTo(o2.getNames());
                }
                return o2.getLikes() - o1.getLikes();
            });
            details.getUser().getMovieName().add(movies.get(0).getNames());
        } else {
            details.getUser().getMovieName().add("No recommendation");
        }
        new Info(output, details.getUser(), null);
    }
}
