package com.stackroute.AuthenticationService.service;

import com.stackroute.AuthenticationService.exception.UserAlreadyExistsException;
import com.stackroute.AuthenticationService.exception.UserNotFoundException;
import com.stackroute.AuthenticationService.exception.UserTypeMismatchException;
import com.stackroute.AuthenticationService.model.UserAuth;

public interface UserAuthService {

	UserAuth findUserByEmailAndPassword (String email, String password) throws UserNotFoundException;
	boolean createUser (UserAuth user) throws UserAlreadyExistsException, UserTypeMismatchException;

	boolean updatePassword (String email, String newPass) throws UserNotFoundException;

	boolean deleteUser(String email) throws UserNotFoundException;

	String checkUserByEmail(String email);


}
