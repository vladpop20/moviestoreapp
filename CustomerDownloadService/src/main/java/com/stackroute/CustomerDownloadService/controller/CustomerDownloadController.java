package com.stackroute.CustomerDownloadService.controller;

import com.stackroute.CustomerDownloadService.model.CustomerDownload;
import com.stackroute.CustomerDownloadService.model.Download;
import com.stackroute.CustomerDownloadService.service.CustomerDownloadService;
import com.stackroute.CustomerDownloadService.util.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/movieapp/v1/download")
public class CustomerDownloadController {

    private CustomerDownloadService customerDownloadService;

    public CustomerDownloadController(CustomerDownloadService customerDownloadService) {
        this.customerDownloadService = customerDownloadService;
    }

    // Add a new customer download
    @PostMapping()
    public ResponseEntity<?> createCustomerDownload(@RequestBody CustomerDownload customerDownload) {
        try {
            customerDownloadService.addCustomerDownload(customerDownload);
            return new ResponseEntity<>("Download registered!", HttpStatus.CREATED);
        } catch (CustomerDownloadDuplicateException | MovieNotFoundException | DownloadNotFoundException
                 | UserNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
    }

    // Add a new download to an existing Downloads list
    @PostMapping("/download/{userId}")
    public ResponseEntity<?> registerDownloadForUser(@RequestBody Download download, @PathVariable String userId) {
        try {
            return new ResponseEntity<>(customerDownloadService.addDownloadForCustomer(userId, download), HttpStatus.CREATED);
        } catch (DownloadDuplicateException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        } catch (CustomerDownloadNotFoundException | MovieNotFoundException | UserNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    //view downloaded movie by movieId
    @GetMapping("{userId}/{movieId}")
    public ResponseEntity<?> getDownloadByMovieId(@PathVariable String userId, @PathVariable int movieId) {
        try {
            return new ResponseEntity<>(customerDownloadService.getDownloadByMovieId(userId, movieId), HttpStatus.OK);
        } catch (DownloadNotFoundException | CustomerDownloadNotFoundException | UserNotFoundException |
                 MovieNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    //get all downloaded movies
    @GetMapping("/all/{userId}")
    public ResponseEntity<?> getAllDownloadedMovies(@PathVariable String userId) {
        try {
            return new ResponseEntity<>(customerDownloadService.getAllDownloadedMovies(userId), HttpStatus.OK);
        } catch (UserNotFoundException | CustomerDownloadNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    // delete a downloaded movie
    @DeleteMapping("{userId}/{movieId}")
    public ResponseEntity<?> deleteDownloadedMovie(@PathVariable String userId, @PathVariable int movieId) {
        try {
            customerDownloadService.deleteDownloadedMovie(userId, movieId);
            return new ResponseEntity<>("Download deleted with success!", HttpStatus.OK);
        } catch (CustomerDownloadNotFoundException | MovieNotFoundException | UserNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("update/{customerId}")
    public ResponseEntity<?> updateDownload(@RequestBody Download download, @PathVariable String customerId) {
        try {
            return new ResponseEntity<>(customerDownloadService.updateDownload(download, customerId), HttpStatus.OK);
        } catch (DownloadNotFoundException | CustomerDownloadNotFoundException | UserNotFoundException |
                 MovieNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}