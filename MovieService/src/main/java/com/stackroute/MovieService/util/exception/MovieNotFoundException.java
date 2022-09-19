package com.stackroute.MovieService.util.exception;

public class MovieNotFoundException extends Exception{

    private static final long serialVersionUID = 1L;

    public MovieNotFoundException(String mess){
        super(mess);
    }
}
