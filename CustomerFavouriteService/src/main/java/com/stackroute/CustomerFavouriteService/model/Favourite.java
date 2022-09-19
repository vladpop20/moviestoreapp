package com.stackroute.CustomerFavouriteService.model;

import java.time.LocalDateTime;

public class Favourite {

    private int favId;
    private int movieId;
    private LocalDateTime createdAt;

    public Favourite(int favId, int movieId, LocalDateTime createdAt) {
        this.favId = favId;
        this.movieId = movieId;
        this.createdAt = createdAt;
    }
    public Favourite(){}

    public int getFavId() {
        return favId;
    }

    public void setFavId(int favId) {
        this.favId = favId;
    }

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "Favourite{" +
                "favId=" + favId +
                ", movieId=" + movieId +
                ", createdAt=" + createdAt +
                '}';
    }
}
