package com.stackroute.CustomerFavouriteService.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document
public class CustomerFavourite {

    @Id
    private String customerFavId;
    private String customerId;
    private List<Favourite> favourites;

    public CustomerFavourite(String customerId, String customerFavId, List<Favourite> favourites) {
        this.customerId = customerId;
        this.customerFavId = customerFavId;
        this.favourites = favourites;
    }

    public CustomerFavourite(){}

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getCustomerFavId() {
        return customerFavId;
    }

    public void setCustomerFavId(String customerFavId) {
        this.customerFavId = customerFavId;
    }

    public List<Favourite> getFavourites() {
        return favourites;
    }

    public void setFavourites(List<Favourite> favourites) {
        this.favourites = favourites;
    }

    @Override
    public String toString() {
        return "CustomerFavourite{" +
                "customerId=" + customerId +
                ", customerFavId=" + customerFavId +
                ", favourites=" + favourites +
                '}';
    }
}