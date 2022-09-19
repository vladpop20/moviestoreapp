package com.stackroute.MovieService.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;


@Document
public class Movie {

    @Id
    private int movieId;
    private String title;
    private List<Actor> actors;
    private double rating;
    private int yearOfRelease;
    private String genre;
    private String director;

    public Movie(){

    }

    public Movie(int movieId, String title, List<Actor> actors, double rating, int yearOfRelease, String genre, String director) {
        this.movieId = movieId;
        this.title = title;
        this.actors = actors;
        this.rating = rating;
        this.yearOfRelease = yearOfRelease;
        this.genre = genre;
        this.director = director;
    }

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Actor> getActors() {
        return actors;
    }

    public void setActors(List<Actor> actors) {
        this.actors = actors;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public int getYearOfrelease() {
        return yearOfRelease;
    }

    public void setYearOfrelease(int yearOfrelease) {
        this.yearOfRelease = yearOfrelease;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "movieId=" + movieId +
                ", title='" + title + '\'' +
                ", actors=" + actors +
                ", rating=" + rating +
                ", yearOfrelease=" + yearOfRelease +
                ", genre='" + genre + '\'' +
                ", director='" + director + '\'' +
                '}';
    }
}
