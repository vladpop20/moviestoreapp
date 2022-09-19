package com.stackroute.AuthenticationService.service;

import com.stackroute.AuthenticationService.exception.UserAlreadyExistsException;
import com.stackroute.AuthenticationService.exception.UserNotFoundException;
import com.stackroute.AuthenticationService.exception.UserTypeMismatchException;
import com.stackroute.AuthenticationService.model.UserAuth;
import com.stackroute.AuthenticationService.repository.UserAuthRepository;
import com.stackroute.AuthenticationService.utils.UserType;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserAuthServiceImpl implements UserAuthService{

	private final UserAuthRepository userRepository;

	public UserAuthServiceImpl(UserAuthRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public UserAuth findUserByEmailAndPassword(String email, String password) throws UserNotFoundException {
		Optional<UserAuth> foundUser = userRepository.findUserByEmailAndPassword(email, password);
		if (foundUser.isEmpty()) {
			throw new UserNotFoundException("Invalid user or password");
		}
		return foundUser.get();
	}

	@Override
	public boolean createUser(UserAuth user) throws UserAlreadyExistsException, UserTypeMismatchException {
		Optional<UserAuth> foundUser = userRepository.findById(user.getEmail());
		if (foundUser.isPresent()) {
			throw new UserAlreadyExistsException("A user with this username, already exists!");
		} else if (user.getType() == null || user.getType().equals("")) {
			throw new UserTypeMismatchException("User type needs to have a value");

		}
			//		else if (!UserType.ADMIN.getType().equalsIgnoreCase(user.getType())
//				&& !UserType.CUSTOMER.getType().equalsIgnoreCase(user.getType())) {
//
//			throw new UserTypeMismatchException("The user type: '" + user.getType() + "' is wrong! -- Try again with 'customer' or 'admin'");
//		}

		UserAuth savedUser = userRepository.save(user);
		if (savedUser != null) {
			return true;
		}

		return false;
	}

	@Override
	public boolean updatePassword(String email, String newPass) throws UserNotFoundException {
		Optional<UserAuth> optUser = userRepository.findById(email);

		if(optUser.isEmpty()){
			throw new UserNotFoundException("User not found.");
		}

		UserAuth userToUpdate = optUser.get();
		userToUpdate.setPassword(newPass);
		userRepository.save(userToUpdate);
		return true;
	}

	@Override
	public boolean deleteUser(String email) throws UserNotFoundException{
		Optional<UserAuth> optUser = userRepository.findById(email);
		if(optUser.isEmpty()){
			throw new UserNotFoundException("User not found.");
		}
		UserAuth userToDelete = optUser.get();
		userRepository.delete(userToDelete);
		return true;
	}

	@Override
	public String checkUserByEmail(String email){
		Optional<UserAuth> optUser = userRepository.findById(email);
		if(optUser.isPresent()){
			return email;
		}
		return "none";
	}
}
