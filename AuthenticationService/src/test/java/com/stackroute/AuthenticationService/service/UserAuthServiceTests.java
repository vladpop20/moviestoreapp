package com.stackroute.AuthenticationService.service;

import com.stackroute.AuthenticationService.exception.UserAlreadyExistsException;
import com.stackroute.AuthenticationService.exception.UserNotFoundException;
import com.stackroute.AuthenticationService.exception.UserTypeMismatchException;
import com.stackroute.AuthenticationService.model.UserAuth;
import com.stackroute.AuthenticationService.repository.UserAuthRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class UserAuthServiceTests {

	@Mock
	private UserAuthRepository userRepository;

	private UserAuth user;

	@InjectMocks
	UserAuthServiceImpl userAuthService;

	Optional<UserAuth> optionalUser;

	@BeforeEach
	public void init() throws Exception {
		MockitoAnnotations.openMocks(this);

		user = new UserAuth("Tudor_23@yahoo.com", "mars123", "admin");
		optionalUser = Optional.of(user);
	}

	@Test
	public void testSaveUserSuccess() throws UserAlreadyExistsException, UserTypeMismatchException {
		Mockito.when(userRepository.findById("Tudor_23@yahoo.com")).thenReturn(Optional.empty());
		Mockito.when(userRepository.save(user)).thenReturn(user);
		boolean flag = userAuthService.createUser(user);
		assertEquals(true, flag);
	}

	@Test
	public void testSaveUserFailure() {
		Mockito.when(userRepository.findById("Tudor_23@yahoo.com")).thenReturn(optionalUser);
		Mockito.when(userRepository.save(user)).thenReturn(user);
		assertThrows(UserAlreadyExistsException.class, () -> userAuthService.createUser(user));
	}

	@Test
	public void testFindByUserIdAndPasswordSuccess() throws UserNotFoundException {
		Mockito.when(userRepository.findUserByEmailAndPassword("Tudor_23@yahoo.com", "mars123")).thenReturn(optionalUser);
		UserAuth fetchedUser = userAuthService.findUserByEmailAndPassword("Tudor_23@yahoo.com", "mars123");
		assertEquals("Tudor_23@yahoo.com", fetchedUser.getEmail());
	}

	@Test
	public void testFindByUserIdAndPasswordFailure() throws UserNotFoundException {
		Mockito.when(userRepository.findUserByEmailAndPassword("Tudor_23@yahoo.com", "mars123")).thenReturn(Optional.empty());
		assertThrows(UserNotFoundException.class, () -> userAuthService.findUserByEmailAndPassword("Tudor_23@yahoo.com", "mars123"));
	}

	@Test
	public void testDeleteUserSuccess() throws UserNotFoundException {
		Mockito.when(userRepository.findById("Tudor_23@yahoo.com")).thenReturn(optionalUser);
		boolean flag = userAuthService.deleteUser(user.getEmail());
		assertTrue(flag);
	}

	@Test
	public void testDeleteUserFailure() throws UserNotFoundException {
		Mockito.when(userRepository.findById("Tudor_23@yahoo.com")).thenReturn(Optional.empty());
		assertThrows(UserNotFoundException.class, () -> userAuthService.deleteUser("Tudor_23@yahoo.com"));
	}

	@Test
	public void testCheckIfUserExistsSuccess() {
		Mockito.when(userRepository.findById("Tudor_23@yahoo.com")).thenReturn(optionalUser);
		String userEmail= userAuthService.checkUserByEmail(user.getEmail());
		assertEquals(user.getEmail(), userEmail);
	}

	@Test
	public void testCheckIfUserExistsFailure_ThenReturnNone() {
		Mockito.when(userRepository.findById("Tudor_23@yahoo.com")).thenReturn(Optional.empty());
		String userEmail= userAuthService.checkUserByEmail(user.getEmail());
		assertEquals("none", userEmail);
	}

}
