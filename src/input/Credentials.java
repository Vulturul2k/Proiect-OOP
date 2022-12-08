package input;

import java.util.ArrayList;

public class Credentials {
    private User credentials;

    private double tokensCount;
    private double numFreePremiumMovies;
    private ArrayList<String> purchasedMovies;
    private ArrayList<String> watchedMovies;
    private ArrayList<String> likedMovies;
    private ArrayList<String> ratedMovies;

    public double getTokensCount() {
        return tokensCount;
    }

    public void setTokensCount(double tokensCount) {
        this.tokensCount = tokensCount;
    }

    public double getNumFreePremiumMovies() {
        return numFreePremiumMovies;
    }

    public void setNumFreePremiumMovies(double numFreePremiumMovies) {
        this.numFreePremiumMovies = numFreePremiumMovies;
    }

    public ArrayList<String> getPurchasedMovies() {
        return purchasedMovies;
    }

    public void setPurchasedMovies(ArrayList<String> purchasedMovies) {
        this.purchasedMovies = purchasedMovies;
    }

    public ArrayList<String> getWatchedMovies() {
        return watchedMovies;
    }

    public void setWatchedMovies(ArrayList<String> watchedMovies) {
        this.watchedMovies = watchedMovies;
    }

    public ArrayList<String> getLikedMovies() {
        return likedMovies;
    }

    public void setLikedMovies(ArrayList<String> likedMovies) {
        this.likedMovies = likedMovies;
    }

    public ArrayList<String> getRatedMovies() {
        return ratedMovies;
    }

    public void setRatedMovies(ArrayList<String> ratedMovies) {
        this.ratedMovies = ratedMovies;
    }

    public User getCredentials() {
        return credentials;
    }

    public void setCredentials(User credentials) {
        this.credentials = credentials;
    }
}
