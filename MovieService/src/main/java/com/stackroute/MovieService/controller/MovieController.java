package com.stackroute.MovieService.controller;


import com.stackroute.MovieService.model.Actor;
import com.stackroute.MovieService.model.Movie;
import com.stackroute.MovieService.service.MovieService;
import com.stackroute.MovieService.util.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/movieapp/v1/movie")
public class MovieController {

    private MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @PostMapping("/admin/addMovie")
    public ResponseEntity<?> addMovie(@RequestBody Movie movieObj) {
        try{
            movieService.addMovie(movieObj);
            return new ResponseEntity<>("New movie added", HttpStatus.CREATED);
        }catch (FieldNotInitializedException  | GenreNotExistentException | MovieAlreadyExistsException | RatingOutOfBoundsException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
    }

    @DeleteMapping("/admin/deleteMovie/{movieId}")
    public ResponseEntity<?> deleteMovie(@PathVariable("movieId") int movieId) {

        boolean result = movieService.deleteMovie(movieId);
        if (result) {
            return new ResponseEntity<>("Movie successfully deleted", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Movie not found", HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/admin/updateMovieRating/{movieId}/{newRating}")
    public ResponseEntity<?> updateMovieRating(@PathVariable("movieId") int movieId, @PathVariable("newRating") double newRating) {
        try {
            movieService.updateMovieRating(movieId, newRating);
            return new ResponseEntity<>("Updated succesfully", HttpStatus.OK);
        } catch (RatingOutOfBoundsException e){
            return new ResponseEntity<>("Rating out of bounds.Rating should be from 0.0 to 5.0.", HttpStatus.NOT_FOUND);
        } catch (MovieNotFoundException e) {
            return new ResponseEntity<>("Movie not found", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("customer/getMovieById/{movieId}")
    public Integer getMovieById(@PathVariable("movieId") int movieId) {
        return movieService.getMovieByMovieId(movieId);
    }

    @GetMapping("customer/getMovieByTitle/title/{title}")
    public ResponseEntity<?> getMovieByTitle(@PathVariable("title") String title) {
        try {
            Movie movieToReturn = movieService.getMovieByTitle(title);
            return new ResponseEntity<>(movieToReturn, HttpStatus.OK);
        } catch (MovieNotFoundException e) {
            return new ResponseEntity<String>("Movie not found", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("customer/getAllMoviesByRating/rating/{rating}")
    public ResponseEntity<?> getAllMoviesByRating(@PathVariable("rating") double rating) {
        try {
            List<Movie> moviesToReturn = movieService.getAllMoviesByRating(rating);
            return new ResponseEntity<>(moviesToReturn, HttpStatus.OK);
        } catch (RatingOutOfBoundsException e) {
            return new ResponseEntity<String>("Rating out of bounds.Rating should be from 0.0 to 5.0.", HttpStatus.NOT_FOUND);
        } catch (MovieNotFoundException e) {
            return new ResponseEntity<String>("Movie not found", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("customer/getAllMoviesByGenre/genre/{genre}")
    public ResponseEntity<?> getAllMoviesByGenre(@PathVariable("genre") String genre) {
        try {
            List<Movie> moviesToReturn = movieService.getAllMoviesByGenre(genre);
            return new ResponseEntity<>(moviesToReturn, HttpStatus.OK);
        } catch (GenreNotExistentException e) {
            return new ResponseEntity<>("Genre not found in our dataBase", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("customer/getAllMovieByActor/actor")
    public ResponseEntity<?> getAllMoviesByActor(@RequestParam String firstName, @RequestParam String lastName){
        try{
            List<Movie> moviesToReturn = movieService.getAllMoviesByActor(firstName,lastName);
            return new ResponseEntity<>(moviesToReturn, HttpStatus.OK);
        }catch(MovieNotFoundException e){
            return new ResponseEntity<>("Movie not found", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("customer/getAllMovies")
    public ResponseEntity<?> getAllMovies(){
        try{
            List<Movie> moviesToReturn = movieService.getAllMovies();
            return new ResponseEntity<>(moviesToReturn, HttpStatus.OK);
        }catch(MovieNotFoundException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("customer/getMoviesByActorAndDirector/actor/{director}")
    public ResponseEntity<?> getMoviesByActorAndDirector(@RequestParam String firstName, @RequestParam String lastName,
                                                         @PathVariable("director") String director){
        try{
            List<Movie> moviesToReturn = movieService.getMoviesByActorAndDirector(firstName, lastName, director);
            return new ResponseEntity<>(moviesToReturn, HttpStatus.OK);
        }catch(MovieNotFoundException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("customer/getMoviesByGenreAndRating/{genre}/{rating}")
    public ResponseEntity<?> getMoviesByGenreAndRating(@PathVariable("genre") String genre, @PathVariable("rating") double rating){
        try{
            List<Movie> moviesToReturn = movieService.getMoviesByGenreAndRating(genre, rating);
            return new ResponseEntity<>(moviesToReturn, HttpStatus.OK);
        }catch(MovieNotFoundException | RatingOutOfBoundsException | GenreNotExistentException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

}
