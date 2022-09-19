package com.stackroute.MovieService.util.exception;

public class GenreNotExistentException extends Exception{

    public GenreNotExistentException(String mess){
        super(mess);
    }
}
