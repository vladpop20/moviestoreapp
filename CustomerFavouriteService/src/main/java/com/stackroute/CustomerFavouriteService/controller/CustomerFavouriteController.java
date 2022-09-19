package com.stackroute.CustomerFavouriteService.controller;

import com.stackroute.CustomerFavouriteService.model.CustomerFavourite;
import com.stackroute.CustomerFavouriteService.model.Favourite;
import com.stackroute.CustomerFavouriteService.util.exception.*;
import com.stackroute.CustomerFavouriteService.service.CustomerFavouriteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/movieapp/v1/favourite")
public class CustomerFavouriteController {

    private CustomerFavouriteService customerFavouriteService;

    public CustomerFavouriteController(CustomerFavouriteService customerFavouriteService) {
        this.customerFavouriteService = customerFavouriteService;
    }

    @PostMapping()
    public ResponseEntity<?> createFavouriteDownload(@RequestBody CustomerFavourite customerFavourite) {
        try {
            return new ResponseEntity<>(customerFavouriteService.addCustomerFavourite(customerFavourite), HttpStatus.CREATED);
        } catch (CustomerFavouriteDuplicateException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        } catch (FavouriteNotFoundException | UserNotFoundException | MovieNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    // Add a new download to an existing Favourites list
    @PostMapping("/favourite/{userId}")
    public ResponseEntity<?> registerFavouriteForUser(@RequestBody Favourite download, @PathVariable String userId) {
        try {
            return new ResponseEntity<>(customerFavouriteService.addFavouriteForCustomer(userId, download), HttpStatus.CREATED);
        } catch (FavouriteDuplicateException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        } catch (CustomerFavouriteNotFoundException | MovieNotFoundException | UserNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    //view favourite movie by favId
    @GetMapping("{userId}/{favouriteId}")
    public ResponseEntity<?> getFavouriteByMovieId(@PathVariable String userId, @PathVariable int favouriteId) {
        try {
            return new ResponseEntity<>(customerFavouriteService.getFavouriteMovieById(userId, favouriteId), HttpStatus.OK);
        } catch (FavouriteNotFoundException | UserNotFoundException | MovieNotFoundException |
                 CustomerFavouriteNotFoundException e) {
            return new ResponseEntity<>("Favourite hasn't been found", HttpStatus.NOT_FOUND);
        }
    }

    // delete a favourite movie
    @DeleteMapping("/delete/{userId}/{movieId}")
    public ResponseEntity<?> deleteFavouriteMovie(@PathVariable String userId, @PathVariable int movieId) {
        try {
            customerFavouriteService.deleteFavouriteMovie(userId, movieId);
            return new ResponseEntity<>("Favourite movie has been deleted", HttpStatus.OK);
        } catch (CustomerFavouriteNotFoundException | UserNotFoundException | MovieNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    //get all favourite movies
    @GetMapping("/all/{customerId}")
    public ResponseEntity<?> getAllFavouriteMovies(@PathVariable String customerId) {
        try {
            return new ResponseEntity<>(customerFavouriteService.getAllFavouriteMovies(customerId), HttpStatus.OK);
        } catch (CustomerFavouriteNotFoundException | UserNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("update/{customerId}")
    public ResponseEntity<?> updateFavourite(@RequestBody Favourite favourite, @PathVariable String customerId) {
        try {
            return new ResponseEntity<>(customerFavouriteService.updateFavourite(favourite, customerId), HttpStatus.OK);
        } catch (FavouriteNotFoundException | CustomerFavouriteNotFoundException | UserNotFoundException |
                 MovieNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}