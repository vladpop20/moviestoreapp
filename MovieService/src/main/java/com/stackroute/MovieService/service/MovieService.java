package com.stackroute.MovieService.service;

import com.stackroute.MovieService.model.Actor;
import com.stackroute.MovieService.model.Movie;
import com.stackroute.MovieService.util.exception.*;

import java.util.List;

public interface MovieService {

    boolean addMovie(Movie movie) throws FieldNotInitializedException, GenreNotExistentException, MovieAlreadyExistsException,RatingOutOfBoundsException;

    boolean deleteMovie(int movieId);

    Movie updateMovieRating(int movieId, double newRating) throws MovieNotFoundException, RatingOutOfBoundsException;

    Integer getMovieByMovieId(int movieId) ;

    Movie getMovieByTitle(String title) throws MovieNotFoundException;

    List<Movie> getAllMoviesByRating(double rating) throws RatingOutOfBoundsException, MovieNotFoundException;

    List<Movie> getAllMoviesByGenre(String genre) throws GenreNotExistentException;

    List<Movie> getAllMoviesByActor(String firstName, String lastName) throws MovieNotFoundException;

//    List<Movie> getAllMoviesByActor(Actor actor) throws MovieNotFoundException;

    List<Movie> getAllMovies() throws MovieNotFoundException;

    List<Movie> getMoviesByActorAndDirector(String firstName, String lastName, String director) throws MovieNotFoundException;

    List<Movie> getMoviesByGenreAndRating(String genre, double rating) throws RatingOutOfBoundsException,GenreNotExistentException, MovieNotFoundException;




}
