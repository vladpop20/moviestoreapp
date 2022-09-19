package com.stackroute.CustomerFavouriteService.service;

import com.stackroute.CustomerFavouriteService.model.CustomerFavourite;
import com.stackroute.CustomerFavouriteService.model.Favourite;
import com.stackroute.CustomerFavouriteService.util.exception.*;

import java.util.List;

public interface CustomerFavouriteService {

    CustomerFavourite addCustomerFavourite(CustomerFavourite customerFavourite)
            throws CustomerFavouriteDuplicateException, FavouriteNotFoundException, UserNotFoundException,
            MovieNotFoundException;

    Favourite addFavouriteForCustomer(String userId, Favourite favourite)
            throws UserNotFoundException, CustomerFavouriteNotFoundException, MovieNotFoundException,
            FavouriteDuplicateException;

    boolean deleteFavouriteMovie(String userId, int movieId)
            throws CustomerFavouriteNotFoundException, UserNotFoundException, MovieNotFoundException;

    Favourite getFavouriteMovieById(String userId, int movieId)
			throws FavouriteNotFoundException, UserNotFoundException,
			MovieNotFoundException, CustomerFavouriteNotFoundException;

    List<Favourite> getAllFavouriteMovies(String userId)
			throws UserNotFoundException, CustomerFavouriteNotFoundException;

    Favourite updateFavourite(Favourite favourite, String customerFavId)
			throws FavouriteNotFoundException, CustomerFavouriteNotFoundException, UserNotFoundException,
			MovieNotFoundException;
}
