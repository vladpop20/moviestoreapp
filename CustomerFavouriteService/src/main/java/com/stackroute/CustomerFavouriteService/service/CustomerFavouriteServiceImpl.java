package com.stackroute.CustomerFavouriteService.service;

import com.netflix.discovery.converters.Auto;
import com.stackroute.CustomerFavouriteService.model.CustomerFavourite;
import com.stackroute.CustomerFavouriteService.model.Favourite;
import com.stackroute.CustomerFavouriteService.repository.FavouriteRepository;
import com.stackroute.CustomerFavouriteService.util.exception.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerFavouriteServiceImpl implements CustomerFavouriteService {

    @Autowired
    private FavouriteRepository favouriteRepository;

    @Autowired
    private CustomerFavUserConnect userConnect;

    @Autowired
    private CustomerFavMovieConnect movieConnect;

    @Override
    public CustomerFavourite addCustomerFavourite(CustomerFavourite customerFavourite)
            throws CustomerFavouriteDuplicateException, FavouriteNotFoundException, UserNotFoundException,
            MovieNotFoundException {

        if (userConnect.getUserById(customerFavourite.getCustomerId()).equals("none")) {
            throw new UserNotFoundException("Customer with ID: '" + customerFavourite.getCustomerId() + "' doesn't exists!");
        }

        Optional<CustomerFavourite> customerFavouriteFound = favouriteRepository.findById(customerFavourite.getCustomerFavId());
        if (customerFavouriteFound.isPresent()) {
            throw new CustomerFavouriteDuplicateException("Customer favourite already exists!");
        }

        Optional<CustomerFavourite> customerFavFoundWithCustId = favouriteRepository.findByCustomerId(customerFavourite.getCustomerId());

        if (customerFavFoundWithCustId.isPresent()) {
            throw new CustomerFavouriteDuplicateException("This customer download for '" + customerFavourite.getCustomerId()
                    + "', is a duplicate! - simple make a new download");
        }
        List<Favourite> favourites = customerFavourite.getFavourites();

        if (favourites == null || favourites.isEmpty()) {
            throw new FavouriteNotFoundException("Customer favourite list can't be null or empty!!");
        }

        for (Favourite fav : favourites) {
            if (movieConnect.getMovieById(fav.getMovieId()) == -1) {
                throw new MovieNotFoundException("Movie with ID: '" + fav.getMovieId() + "' doesn't exists!");
            }
        }

        favouriteRepository.save(customerFavourite);
        return customerFavourite;
    }

    @Override
    public Favourite addFavouriteForCustomer(String userId, Favourite favourite)
            throws UserNotFoundException, CustomerFavouriteNotFoundException, MovieNotFoundException,
            FavouriteDuplicateException {

        if (userConnect.getUserById(userId).equals("none")) {
            throw new UserNotFoundException("Customer with ID: '" + userId + "' doesn't exists!");
        }

        if (movieConnect.getMovieById(favourite.getMovieId()) == -1) {
            throw new MovieNotFoundException("Movie with ID: '" + favourite.getMovieId() + "' doesn't exists!");
        }

        Optional<CustomerFavourite> foundCustomerDown = favouriteRepository.findByCustomerId(userId);
        if (foundCustomerDown.isEmpty()) {
            throw new CustomerFavouriteNotFoundException("Customer with ID: '" + userId + "' first needs to be created!");
        }

        List<Favourite> favourites = foundCustomerDown.get().getFavourites();

        boolean duplicateDownload = favourites.stream().anyMatch(fav -> fav.getFavId() == favourite.getFavId());
        if (duplicateDownload) {
            throw new FavouriteDuplicateException("This download '" + favourite.getFavId() + "' already exists! Can't be duplicate!");
        }
        favourites.add(favourite);
        foundCustomerDown.get().setFavourites(favourites);
        favouriteRepository.save(foundCustomerDown.get());
        return favourite;
    }

    @Override
    public boolean deleteFavouriteMovie(String userId, int movieId)
            throws CustomerFavouriteNotFoundException, UserNotFoundException, MovieNotFoundException {
        Optional<CustomerFavourite> customerFavourite = favouriteRepository.findByCustomerId(userId);
        if (customerFavourite.isEmpty()) {
            throw new CustomerFavouriteNotFoundException("Customer with ID: '" + userId + "' doesn't have any favourites yet!");
        }

        if (userConnect.getUserById(userId).equals("none")) {
            throw new UserNotFoundException("Customer with ID: '" + userId + "' doesn't exists!");
        }

        if (movieConnect.getMovieById(movieId) == -1) {
            throw new MovieNotFoundException("Movie with ID: '" + movieId + "' doesn't exists!");
        }

        List<Favourite> favouriteList = customerFavourite.get().getFavourites();

        boolean movieStatus = favouriteList.stream().anyMatch(obj -> obj.getMovieId() == movieId);
        if (movieStatus == false) {
            throw new MovieNotFoundException("Movie with ID: '" + movieId + "' haven't been yet downloaded!");
        }

        favouriteList.removeIf(obj -> obj.getMovieId() == movieId);
        customerFavourite.get().setFavourites(favouriteList);
        favouriteRepository.save(customerFavourite.get());
        return true;
    }

    @Override
    public Favourite getFavouriteMovieById(String userId, int movieId)
            throws FavouriteNotFoundException, UserNotFoundException, MovieNotFoundException,
            CustomerFavouriteNotFoundException {

        Optional<CustomerFavourite> customerFavourite = favouriteRepository.findByCustomerId(userId);
        if (customerFavourite.isEmpty()) {
            throw new CustomerFavouriteNotFoundException("Customer with ID: '" + userId + "' doesn't have any favourites yet!");
        }

        if (userConnect.getUserById(userId).equals("none")) {
            throw new UserNotFoundException("Customer with ID: '" + userId + "' doesn't exists!");
        }

        if (movieConnect.getMovieById(movieId) == -1) {
            throw new MovieNotFoundException("Movie with ID: '" + movieId + "' doesn't exists!");
        }

        List<Favourite> favouriteList = customerFavourite.get().getFavourites();

        Optional<Favourite> foundFavourite = favouriteList.stream()
                .filter(favourite -> favourite.getMovieId() == movieId)
                .findFirst();

        if (foundFavourite.isEmpty()) {
            throw new FavouriteNotFoundException("Favourite movie can't be found!");
        }

        return foundFavourite.get();
    }

    @Override
    public List<Favourite> getAllFavouriteMovies(String userId)
            throws UserNotFoundException, CustomerFavouriteNotFoundException {

        if (userConnect.getUserById(userId).equals("none")) {
            throw new UserNotFoundException("Customer with ID: '" + userId + "' doesn't exists!");
        }

        Optional<CustomerFavourite> customerFavourite = favouriteRepository.findByCustomerId(userId);
        if (customerFavourite.isEmpty()) {
            throw new CustomerFavouriteNotFoundException("Customer with ID: '" + userId + "' doesn't have any favourites yet!");
        }

        if (customerFavourite.get().getFavourites().isEmpty()) {
            throw new CustomerFavouriteNotFoundException("Customer with ID: '" + userId + "' doesn't have any favourites!");
        }
        return customerFavourite.get().getFavourites();
    }

    @Override
    public Favourite updateFavourite(Favourite favourite, String customerId)
            throws FavouriteNotFoundException, CustomerFavouriteNotFoundException, UserNotFoundException,
            MovieNotFoundException {

        if (userConnect.getUserById(customerId).equals("none")) {
            throw new UserNotFoundException("Customer with ID: '" + customerId + "' doesn't exists!");
        }

        Optional<CustomerFavourite> favouriteToUpdate = this.favouriteRepository.findByCustomerId(customerId);
        if (favouriteToUpdate.isEmpty()) {
            throw new CustomerFavouriteNotFoundException("Customer favourite hasn't been found");
        }

        if (movieConnect.getMovieById(favourite.getMovieId()) == -1) {
            throw new MovieNotFoundException("Movie with ID: '" + favourite.getMovieId() + "' doesn't exists!");
        }

        List<Favourite> favList = favouriteToUpdate.get().getFavourites();
        //see if any favourites with favId exists in this CustomerFavourite before deleting
        boolean found = favList.stream().anyMatch(fav -> fav.getFavId() == favourite.getFavId());
        if (found) {
            favList.removeIf(favourite1 -> favourite1.getFavId() == favourite.getFavId());
            favList.add(favourite);
            favouriteToUpdate.get().setFavourites(favList);
            favouriteRepository.save(favouriteToUpdate.get());
            return favourite;
        }
        throw new FavouriteNotFoundException("Favourite movie can't be found");

    }
}
