package input;

import pageactions.Constants;

import java.util.ArrayList;

public final class User {

    private Credentials credentials;

    private int tokensCount;
    private int numFreePremiumMovies = Constants.NUMBER_OF_FREE_MOVIES;
    private ArrayList<Movie> purchasedMovies;
    private ArrayList<Movie> watchedMovies;
    private ArrayList<Movie> likedMovies;
    private ArrayList<Movie> ratedMovies;
    private ArrayList<String> subscribe;

    public ArrayList<String> getSubscribe() {
        return subscribe;
    }

    public void setSubscribe(final ArrayList<String> subscribe) {
        this.subscribe = subscribe;
    }

    public ArrayList<Double> getRates() {
        return rates;
    }

    public void setRates(final ArrayList<Double> rates) {
        this.rates = rates;
    }

    private ArrayList<Double> rates;


    private ArrayList<String> message;
    private ArrayList<String> movieName;

    public ArrayList<String> getMessage() {
        return message;
    }

    public void setMessage(final ArrayList<String> message) {
        this.message = message;
    }

    public ArrayList<String> getMovieName() {
        return movieName;
    }

    public void setMovieName(final ArrayList<String> movieName) {
        this.movieName = movieName;
    }

    public Credentials getCredentials() {
        return credentials;
    }

    public void setCredentials(final Credentials credentials) {
        this.credentials = credentials;
    }

    public int getTokensCount() {
        return tokensCount;
    }

    public void setTokensCount(final int tokensCount) {
        this.tokensCount = tokensCount;
    }

    public int getNumFreePremiumMovies() {
        return numFreePremiumMovies;
    }

    public void setNumFreePremiumMovies(final int numFreePremiumMovies) {
        this.numFreePremiumMovies = numFreePremiumMovies;
    }

    public ArrayList<Movie> getPurchasedMovies() {
        return purchasedMovies;
    }

    public void setPurchasedMovies(final ArrayList<Movie> purchasedMovies) {
        this.purchasedMovies = purchasedMovies;
    }

    public ArrayList<Movie> getWatchedMovies() {
        return watchedMovies;
    }

    public void setWatchedMovies(final ArrayList<Movie> watchedMovies) {
        this.watchedMovies = watchedMovies;
    }

    public ArrayList<Movie> getLikedMovies() {
        return likedMovies;
    }

    public void setLikedMovies(final ArrayList<Movie> likedMovies) {
        this.likedMovies = likedMovies;
    }

    public ArrayList<Movie> getRatedMovies() {
        return ratedMovies;
    }

    public void setRatedMovies(final ArrayList<Movie> ratedMovies) {
        this.ratedMovies = ratedMovies;
    }
}
