package com.stackroute.CustomerFavouriteService.repository;

import com.stackroute.CustomerFavouriteService.model.CustomerFavourite;
import com.stackroute.CustomerFavouriteService.model.Favourite;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface FavouriteRepository extends MongoRepository<CustomerFavourite, String> {

    Optional<CustomerFavourite> findByCustomerId(String id);

}
