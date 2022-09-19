package com.stackroute.MovieService.service;

import com.stackroute.MovieService.model.Actor;
import com.stackroute.MovieService.model.Movie;
import com.stackroute.MovieService.repository.MovieRepository;
import com.stackroute.MovieService.service.MovieServiceImpl;
import com.stackroute.MovieService.util.exception.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class MovieServiceImplServiceTest {


    private Movie movie;
    private Actor actor;

    @Mock
    private MovieRepository movieRepo;

    @InjectMocks
    private MovieServiceImpl movieServiceImpl;
    private List<Movie> movies;
    private List<Actor> actors;

    private List<Movie> moviesByActor;


    Optional<Movie> options;


    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);

        movie = new Movie();
        movie.setMovieId(1);
        movie.setTitle("Gone with the wind");
        actor = new Actor("Emma", "James", "american");
        actors = List.of(actor,
                new Actor("John", "Peterson", "american"),
                new Actor("Gina", "Harris", "canadian"),
                new Actor("Stephanie", "Guillot", "french"));
        movie.setActors(actors);
        movie.setRating(3.7);
        movie.setYearOfrelease(1996);
        movie.setGenre("romance");
        movie.setDirector("Jim Morrison");

        movies = new ArrayList<>();
        movies.add(movie);

        options = Optional.of(movie);

        moviesByActor = new ArrayList<>();
        Movie movie2 = new Movie();
        Movie movie3 = new Movie();
        movie2.setActors(List.of(actor, new Actor("Jason", "Momoa", "american")));
        movie3.setActors(List.of(new Actor("Gabriel", "Peterson", "jamaican"),actor));
        moviesByActor.add(movie);
        moviesByActor.add(movie2);
        moviesByActor.add(movie3);
    }

    @Test
    public void addMovieSuccess() throws FieldNotInitializedException, GenreNotExistentException, MovieAlreadyExistsException,RatingOutOfBoundsException {
        when(movieRepo.save(any())).thenReturn(movie);
        boolean status = movieServiceImpl.addMovie(movie);
        assertEquals(true, status);
    }

    @Test
    public void addMovieFailure() throws FieldNotInitializedException, GenreNotExistentException, MovieAlreadyExistsException ,RatingOutOfBoundsException{
        when(movieRepo.insert((Movie) any())).thenReturn(null);
        boolean status = movieServiceImpl.addMovie(movie);
        assertEquals(false, status);
    }

    @Test
    public void deleteMovieSuccess(){
        when(movieRepo.findById(movie.getMovieId())).thenReturn(options);
        when(movieRepo.save(movie)).thenReturn(movie);
        boolean flag = movieServiceImpl.deleteMovie(movie.getMovieId());
        assertEquals(true,flag);
    }

    @Test
    public void deleteMovieFailure(){
        when(movieRepo.findById(movie.getMovieId())).thenReturn(null);
        when(movieRepo.save(movie)).thenReturn(movie);

        assertThrows(
                NullPointerException.class,
                ()->{movieServiceImpl.deleteMovie(movie.getMovieId());}
        );
    }

    @Test
    public void updateMovieRatingSuccess() throws MovieNotFoundException, RatingOutOfBoundsException {
        when(movieRepo.findById(movie.getMovieId())).thenReturn(options);
        movie.setRating(4.5);
        movies.add(movie);
        Movie fetchedMovie = movieServiceImpl.updateMovieRating(movie.getMovieId(), 4.8);
        assertEquals(movie, fetchedMovie);
    }

    @Test
    public void updateMovieRatingFailure() throws MovieNotFoundException{
        when(movieRepo.findById(movie.getMovieId())).thenThrow(NoSuchElementException.class);
        movie.setRating(3.7);
        movies.add(movie);
        assertThrows(
                MovieNotFoundException.class,
                ()->{movieServiceImpl.updateMovieRating(movie.getMovieId(), 2.7);}
        );
    }

//    @Test
//    public void getMovieByMovieIdSuccess() throws MovieNotFoundException{
//        when(movieRepo.findById(movie.getMovieId())).thenReturn(options);
//        Movie fetchedMovie = movieServiceImpl.getMovieByMovieId(movie.getMovieId());
//        assertEquals(movie, fetchedMovie);
//    }

//    @Test
//    public void getMovieByMovieIdFailure() throws MovieNotFoundException{
//        when(movieRepo.findById(movie.getMovieId())).thenThrow(NoSuchElementException.class);
//        assertThrows(
//                MovieNotFoundException.class,
//                ()-> {movieServiceImpl.getMovieByMovieId(movie.getMovieId());}
//        );
//    }

    @Test
    public void getMovieByMovieTitleSuccess() throws MovieNotFoundException{
        when(movieRepo.findByTitleIgnoreCase(movie.getTitle())).thenReturn(movie);
        Movie fetchedMovie = movieServiceImpl.getMovieByTitle(movie.getTitle());
        assertEquals(movie, fetchedMovie);
    }

    @Test
    public void getMovieByMovieTitleFailure() throws MovieNotFoundException{
        when(movieRepo.findByTitleIgnoreCase(movie.getTitle())).thenThrow(NoSuchElementException.class);
        assertThrows(
                MovieNotFoundException.class,
                ()->{movieServiceImpl.getMovieByTitle(movie.getTitle());}
        );
    }

    @Test
    public void getAllMoviesByRatingSuccess() throws MovieNotFoundException, RatingOutOfBoundsException{
        when(movieRepo.findByRating(movie.getRating())).thenReturn(movies);
        List<Movie> fetchedMovies = movieServiceImpl.getAllMoviesByRating(movie.getRating());
        assertEquals(movies, fetchedMovies);
    }

    @Test
    public void getAllMoviesByRatingFailure() throws MovieNotFoundException {
        when(movieRepo.findByRating(movie.getRating())).thenThrow(NoSuchElementException.class);
        assertThrows(
                MovieNotFoundException.class,
                () -> {
                    movieServiceImpl.getAllMoviesByRating(movie.getRating());
                }
        );
    }

    @Test
    public void getAllMoviesByGenreSuccess() throws GenreNotExistentException{
        when(movieRepo.findByGenreIgnoreCase(movie.getGenre())).thenReturn(movies);
        List<Movie> fetchedMovies = movieServiceImpl.getAllMoviesByGenre(movie.getGenre());
        assertEquals(movies, fetchedMovies);
    }

    @Test
    public void getAllMoviesByGenreFailure() throws GenreNotExistentException{
        when(movieRepo.findByGenreIgnoreCase(movie.getGenre())).thenThrow(NoSuchElementException.class);
        assertThrows(
                GenreNotExistentException.class,
                ()->{movieServiceImpl.getAllMoviesByGenre(movie.getGenre());}
        );
    }


    @Test
    public void getAllMoviesByActorSuccess() throws MovieNotFoundException{
        when(movieRepo.findAll()).thenReturn(moviesByActor);
        List<Movie> fetchedMovies = movieServiceImpl.getAllMoviesByActor(actor.getFirstName(), actor.getLastName());
        assertEquals(moviesByActor, fetchedMovies);
    }

    @Test
    public void getAllMoviesByActorFailure() throws MovieNotFoundException{
        when(movieRepo.findAll()).thenThrow(NoSuchElementException.class);
        assertThrows(MovieNotFoundException.class,()-> movieServiceImpl.getAllMoviesByActor(actor.getFirstName(), actor.getLastName()));
    }



    @Test
    public void getAllMoviesSuccess()throws MovieNotFoundException{
        when(movieRepo.findAll()).thenReturn(movies);
        List<Movie> fetchedMovies = movieServiceImpl.getAllMovies();
        assertEquals(movies,fetchedMovies);
    }

    @Test
    public void getAllMoviesFailure() throws MovieNotFoundException{
        when(movieRepo.findAll()).thenThrow(NoSuchElementException.class);
        assertThrows(
                MovieNotFoundException.class,
                ()->{
                    movieServiceImpl.getAllMovies();
                }
        );
    }














}
