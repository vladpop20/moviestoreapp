package com.stackroute.MovieService.service;

import com.stackroute.MovieService.model.Actor;
import com.stackroute.MovieService.model.Movie;
import com.stackroute.MovieService.repository.MovieRepository;
import com.stackroute.MovieService.util.exception.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class MovieServiceImpl implements MovieService {

    @Autowired
    MovieRepository movieRepo;


    @Override
    public boolean addMovie(Movie movie) throws FieldNotInitializedException, GenreNotExistentException, MovieAlreadyExistsException, RatingOutOfBoundsException{
        List<String> availableGenres = Arrays.asList("comedy", "action", "adventure", "SF", "documentary", "romance");
        Optional<Movie> optMovie = movieRepo.findById(movie.getMovieId());
        if (optMovie.isEmpty()) {
            if (movie.getMovieId() == 0 || movie.getActors().isEmpty() || movie.getDirector().equals("") || movie.getDirector().equals("string")||
                    movie.getGenre().equals("") || movie.getGenre().equals("string") || movie.getRating() == 0.0 || movie.getTitle().equals("") ||
                    movie.getTitle().equals("string") || movie.getYearOfrelease() == 0) {
                throw new FieldNotInitializedException("Please fill out all of the fields!");
            }
            if (!availableGenres.contains(movie.getGenre())) {
                throw new GenreNotExistentException("The specified genre is invalid.");
            }
            if (movie.getRating() < 0.0 || movie.getRating() > 5.0) {
                throw new RatingOutOfBoundsException("Rating out of bounds exception. The rating value should be from 0.0 to 5.0.");
            }
            Movie movieNullCheck = movieRepo.save(movie);
            return movieNullCheck != null;
        }
        throw new MovieAlreadyExistsException("Movie already exists in database!");
    }

    @Override
    public boolean deleteMovie(int movieId) {
        Optional<Movie> optMovie = movieRepo.findById(movieId);
        if (optMovie.isPresent()) {
            Movie movieToDelete = optMovie.get();
            movieRepo.delete(movieToDelete);
            return true;
        }
        return false;
    }

    @Override
    public Movie updateMovieRating(int movieId, double newRating) throws MovieNotFoundException, RatingOutOfBoundsException {
        if (newRating < 0.0 || newRating > 5.0) {
            throw new RatingOutOfBoundsException("Rating out of bounds exception. The rating value should be from 0.0 to 5.0.");
        }
        Movie movie = null;
        try {
            Optional<Movie> optMovie = movieRepo.findById(movieId);
            if (optMovie.isPresent()) {
                optMovie.get().setRating(newRating);
                movieRepo.save(optMovie.get());
                movie = optMovie.get();
            }
        } catch (NoSuchElementException e) {
            throw new MovieNotFoundException("The movie doesn't exist");
        }
        return movie;
    }

    @Override
    public Integer getMovieByMovieId(int movieId) {
        Optional<Movie> optMovie = movieRepo.findById(movieId);
        if (optMovie.isPresent()) {
            return optMovie.get().getMovieId();
        }

        return -1;
    }

    @Override
    public Movie getMovieByTitle(String title) throws MovieNotFoundException {
        Movie movieToReturn;
        try {
            movieToReturn = movieRepo.findByTitleIgnoreCase(title);
        } catch (NoSuchElementException e) {
            throw new MovieNotFoundException("Movie not found");
        }
        if (movieToReturn == null) {
            throw new MovieNotFoundException("Movie not found");
        }
        return movieToReturn;
    }

    @Override
    public List<Movie> getAllMoviesByRating(double rating) throws RatingOutOfBoundsException, MovieNotFoundException {
        if (rating < 0.0 || rating > 5.0) {
            throw new RatingOutOfBoundsException("Rating out of bounds exception. The rating value should be from 0.0 to 5.0.");
        }
        List<Movie> moviesToReturn;
        try {
            moviesToReturn = movieRepo.findByRating(rating);
        } catch (NoSuchElementException e) {
            throw new MovieNotFoundException("There are no movies with that rating");
        }
        if (moviesToReturn == null || moviesToReturn.isEmpty()) {
            throw new MovieNotFoundException("There are no movies with that rating");
        }
        return moviesToReturn;
    }

    @Override
    public List<Movie> getAllMoviesByGenre(String genre) throws GenreNotExistentException {


        List<Movie> moviesToReturn;
        try {
            moviesToReturn = movieRepo.findByGenreIgnoreCase(genre);
        } catch (NoSuchElementException e) {
            throw new GenreNotExistentException("The specified genre does not exist in our database.");
        }
        if (moviesToReturn == null || moviesToReturn.isEmpty()) {
            throw new GenreNotExistentException("The specified genre does not exist in our database.");
        }
        return moviesToReturn;
    }

    @Override
    public List<Movie> getAllMoviesByActor(String firstName, String lastName) throws MovieNotFoundException {
        List<Movie> movieToReturn = new ArrayList<>();
        try {
            List<Movie> listOfMovies = movieRepo.findAll();
            for (Movie m : listOfMovies) {
                for (Actor a : m.getActors()) {
                    if (a.getFirstName().equals(firstName) && a.getLastName().equals(lastName)) {
                        movieToReturn.add(m);
                    }
                }
            }
        } catch (NoSuchElementException e) {
            throw new MovieNotFoundException("No movies with specified actor");
        }
        if (movieToReturn == null || movieToReturn.isEmpty()) {
            throw new MovieNotFoundException("No movies with specified actor");
        }

        return movieToReturn;
    }

    public List<Movie> getAllMovies() throws MovieNotFoundException {
        List<Movie> listToReturn;
        try {
            listToReturn = movieRepo.findAll();
        } catch (NoSuchElementException e) {
            throw new MovieNotFoundException("No movies to return");
        }
        if (listToReturn == null || listToReturn.isEmpty()) {
            throw new MovieNotFoundException("No movies to return");
        }
        return listToReturn;
    }

    public List<Movie> getMoviesByActorAndDirector(String firstName, String lastName, String director)
            throws MovieNotFoundException {
        List<Movie> moviesToReturnFinally = new ArrayList<>();
        try {
            List<Movie> moviesByActor;
            moviesByActor = getAllMoviesByActor(firstName, lastName);
            for (Movie m : moviesByActor) {
                if (m.getDirector().equals(director)) {
                    moviesToReturnFinally.add(m);
                }
            }
        } catch (NoSuchElementException e) {
            throw new MovieNotFoundException("No movies with specified actor and director");
        }
        if (moviesToReturnFinally == null || moviesToReturnFinally.isEmpty()) {
            throw new MovieNotFoundException("No movies with specified actor and director");
        }
        return moviesToReturnFinally;
    }

    //    public List<Movie> getMoviesByGenreAndRating(String genre, double rating) throws RatingOutOfBoundsException,
//                                                              GenreNotExistentException,MovieNotFoundException {
//        if(rating < 0.0 || rating > 5.0){
//            throw new RatingOutOfBoundsException("Rating out of bounds exception. The rating value should be from 0.0 to 5.0.");
//        }
//        List<Movie> listByGenreAndRating = new ArrayList<>();
//       List<Movie> moviesByGenre = getAllMoviesByGenre(genre);
//       for(Movie m:moviesByGenre){
//           if(m.getRating()==rating){
//               listByGenreAndRating.add(m);
//           }
//       }
//       if(listByGenreAndRating==null || listByGenreAndRating.isEmpty()){
//           throw new MovieNotFoundException("No movie to return with specified genre and rating");
//       }
//       return listByGenreAndRating;
//    }


    public List<Movie> getMoviesByGenreAndRating(String genre, double rating) throws RatingOutOfBoundsException,
            GenreNotExistentException, MovieNotFoundException {
        if (rating < 0.0 || rating > 5.0) {
            throw new RatingOutOfBoundsException("Rating out of bounds exception. The rating value should be from 0.0 to 5.0.");
        }
        boolean flag = movieRepo.findByGenreIgnoreCase(genre).stream().anyMatch(g->g.getGenre().equals(genre));
        if(flag == false){
            throw new GenreNotExistentException("The specified genre is not present in our database");
        }
        List<Movie> moiesByGenreAndRating = movieRepo.findAllByGenreAndRating(genre, rating);
        if(moiesByGenreAndRating == null || moiesByGenreAndRating.isEmpty()){
           throw new MovieNotFoundException("No movie to return with specified genre and rating");
       }
       return moiesByGenreAndRating;
    }

}
