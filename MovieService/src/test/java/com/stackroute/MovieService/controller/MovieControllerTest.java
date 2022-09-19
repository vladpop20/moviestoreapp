package com.stackroute.MovieService.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.stackroute.MovieService.model.Actor;
import com.stackroute.MovieService.model.Movie;
import com.stackroute.MovieService.service.MovieService;
import com.stackroute.MovieService.util.exception.GenreNotExistentException;
import com.stackroute.MovieService.util.exception.MovieAlreadyExistsException;
import com.stackroute.MovieService.util.exception.MovieNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class MovieControllerTest {

    private MockMvc mockMvc;

    @MockBean
    private Movie movie;
    @MockBean
    private Actor actor;

    @MockBean
    private MovieService movieService;

    @MockBean
    private List<Actor> actors;

    @InjectMocks
    private MovieController movieController;

    private List<Movie> movieList;

    @BeforeEach
    public void setUp(){

        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(movieController).build();

        movie = new Movie();
        movie.setMovieId(1);
        movie.setTitle("Gone with the wind");
        actor = new Actor("Emma", "James", "american");
        actors = List.of(actor, new Actor("John", "Peterson", "american"),
                new Actor("Gina", "Harris", "canadian"),
                new Actor("Stephanie", "Guillot", "french"));
        movie.setActors(actors);
        movie.setRating(3.7);
        movie.setYearOfrelease(1996);
        movie.setGenre("romance");
        movie.setDirector("Jim Morrison");
        Movie movie2 = new Movie();
        movie2.setActors(List.of(actor, new Actor("Jason", "Momoa", "american")));

        movieList = new ArrayList<>();
        movieList.add(movie);
        movieList.add(movie2);

    }

    @Test
    public void addMovieSuccess() throws Exception {
//        when(movieService.addMovie(movie)).thenReturn(true);
//        mockMvc.perform(MockMvcRequestBuilders.post("/movieapp/v1/movie/addMovie")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(asJsonString(movie)))
//                .andExpect(MockMvcResultMatchers.status().isCreated())
//                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void addMovieFailure() throws Exception{
//        when(movieService.addMovie(any())).thenThrow(MovieAlreadyExistsException.class);
//        mockMvc.perform(MockMvcRequestBuilders.post("/movieapp/v1/movie/addMovie").contentType(MediaType.APPLICATION_JSON)
//                        .content(asJsonString(movie)))
//                .andExpect(MockMvcResultMatchers.status().isConflict())
//                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void deleteMovieSuccess() throws Exception{
//        when(movieService.deleteMovie(movie.getMovieId())).thenReturn(true);
//        mockMvc.perform(MockMvcRequestBuilders.delete("/movieapp/v1/movie/deleteMovie/1")
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void deleteMovieFailure() throws Exception{
        when(movieService.deleteMovie(movie.getMovieId())).thenReturn(false);
        mockMvc.perform(MockMvcRequestBuilders.delete("/movieapp/v1/movie/deleteMovie/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void updateMovieSuccess() throws Exception {
//        when(movieService.updateMovieRating(movie.getMovieId(), 4.5)).thenReturn(movie);
//        mockMvc.perform(MockMvcRequestBuilders.put("/movieapp/v1/movie/updateMovieRating/1/4.5")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(asJsonString(movie)))
//                        .andExpect(MockMvcResultMatchers.status().isOk())
//                        .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void updateMovieFailure() throws Exception {
        when(movieService.updateMovieRating(movie.getMovieId(), 4.5)).thenThrow(MovieNotFoundException.class);
        mockMvc.perform(MockMvcRequestBuilders.put("/movieapp/v1/movie/updateMovieRating/1/4.5")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(movie)))
                        .andExpect(MockMvcResultMatchers.status().isNotFound())
                        .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void getMovieByIdSuccess() throws Exception{
//        when(movieService.getMovieByMovieId(1)).thenReturn(1);
//        mockMvc.perform(MockMvcRequestBuilders.get("/movieapp/v1/movie/getMovieById/1")
//                        .contentType(MediaType.APPLICATION_JSON))
//                        .andExpect(MockMvcResultMatchers.status().isOk())
//                        .andDo(MockMvcResultHandlers.print());
    }

//    @Test
//    public void getMovieByIdFailure() throws Exception{
//        when(movieService.getMovieByMovieId(1)).thenReturn(-1);
//        mockMvc.perform(MockMvcRequestBuilders.get("/movieapp/v1/movie/getMovieById/1")
//                        .contentType(MediaType.APPLICATION_JSON))
//                        .andExpect(MockMvcResultMatchers.status().isNotFound())
//                        .andDo(MockMvcResultHandlers.print());
//    }

//    @Test
//    public void getMovieByTitleSuccess()throws Exception{
//        when(movieService.getMovieByTitle("Gone with the wind")).thenReturn(movie);
//        mockMvc.perform(MockMvcRequestBuilders.get("/movieapp/v1/movie/getMovieByTitle/Gone with the wind")
//                        .contentType(MediaType.APPLICATION_JSON))
//                        .andExpect(MockMvcResultMatchers.status().isOk())
//                        .andDo(MockMvcResultHandlers.print());
//    }

    @Test
    public void getMovieByTitleFailure()throws Exception{
        when(movieService.getMovieByTitle("Gone with the wind")).thenThrow(MovieNotFoundException.class);
        mockMvc.perform(MockMvcRequestBuilders.get("/movieapp/v1/movie/getMovieByTitle/Gone with the wind")
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(MockMvcResultMatchers.status().isNotFound())
                        .andDo(MockMvcResultHandlers.print());
    }

//    @Test
//    public void getAllMoviesByRatingSuccess() throws Exception{
//        when(movieService.getAllMoviesByRating(3.7)).thenReturn(movieList);
//        mockMvc.perform(MockMvcRequestBuilders.get("/movieapp/v1/movie/getAllMoviesByRating/3.7")
//                        .contentType(MediaType.APPLICATION_JSON))
//                        .andExpect(MockMvcResultMatchers.status().isOk())
//                        .andDo(MockMvcResultHandlers.print());
//    }

    @Test
    public void getAllMoviesByRatingFailure() throws Exception{
        when(movieService.getAllMoviesByRating(3.7)).thenThrow(MovieNotFoundException.class);
        mockMvc.perform(MockMvcRequestBuilders.get("/movieapp/v1/movie/getAllMoviesByRating/3.7")
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(MockMvcResultMatchers.status().isNotFound())
                        .andDo(MockMvcResultHandlers.print());
    }

//    @Test
//    public void getAllMoviesByGenreSuccess() throws Exception{
//        when(movieService.getAllMoviesByGenre("romance")).thenReturn(movieList);
//        mockMvc.perform(MockMvcRequestBuilders.get("/movieapp/v1/movie/getAllMoviesByGenre/romance")
//                        .contentType(MediaType.APPLICATION_JSON))
//                        .andExpect(MockMvcResultMatchers.status().isOk())
//                        .andDo(MockMvcResultHandlers.print());
//    }

    @Test
    public void getAllMoviesByGenreSFailure() throws Exception{
        when(movieService.getAllMoviesByGenre("romance")).thenThrow(GenreNotExistentException.class);
        mockMvc.perform(MockMvcRequestBuilders.get("/movieapp/v1/movie/getAllMoviesByGenre/romance")
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(MockMvcResultMatchers.status().isNotFound())
                        .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void getAllMoviesByActorSuccess() throws Exception{
//        when(movieService.getAllMoviesByActor(actor.getFirstName(), actor.getLastName())).thenReturn(movieList);
//        mockMvc.perform(MockMvcRequestBuilders.get("/movieapp/v1/movie/getAllMovieByActor/actor?firstName=Emma&lastName=James")
//                        .contentType(MediaType.APPLICATION_JSON))
//                        .andExpect(MockMvcResultMatchers.status().isOk())
//                        .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void getAllMoviesByActorFailure() throws Exception{
        when(movieService.getAllMoviesByActor(actor.getFirstName(), actor.getLastName())).thenThrow(MovieNotFoundException.class);
        mockMvc.perform(MockMvcRequestBuilders.get("/movieapp/v1/movie/getAllMovieByActor/actor?firstName=Emma&lastName=James")
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(MockMvcResultMatchers.status().isNotFound())
                        .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void getAllMoviesSuccess()throws Exception{
//        when(movieService.getAllMovies()).thenReturn(movieList);
//        mockMvc.perform(MockMvcRequestBuilders.get("/movieapp/v1/movie/getAllMovies")
//                        .contentType(MediaType.APPLICATION_JSON))
//                        .andExpect(MockMvcResultMatchers.status().isOk())
//                        .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void getAllMoviesFailure()throws Exception{
        when(movieService.getAllMovies()).thenThrow(MovieNotFoundException.class);
        mockMvc.perform(MockMvcRequestBuilders.get("/movieapp/v1/movie/getAllMovies")
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(MockMvcResultMatchers.status().isNotFound())
                        .andDo(MockMvcResultHandlers.print());
    }




    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
