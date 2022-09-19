package com.stackroute.MovieService.repository;

import com.stackroute.MovieService.model.Actor;
import com.stackroute.MovieService.model.Movie;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRepository extends MongoRepository<Movie, Integer> {

    Movie findByTitleIgnoreCase(String title);

    List<Movie> findByRating(Double rating);

    List<Movie> findByGenreIgnoreCase(String genre);

    List<Movie> findAllByGenreAndRating(String genre, double rating);





}
