package com.stackroute.AuthenticationService.controller;

import com.stackroute.AuthenticationService.exception.UserAlreadyExistsException;
import com.stackroute.AuthenticationService.exception.UserNotFoundException;
import com.stackroute.AuthenticationService.exception.UserTypeMismatchException;
import com.stackroute.AuthenticationService.model.UserAuth;
import com.stackroute.AuthenticationService.service.UserAuthService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/movieapp/v1/auth")
@Validated
public class UserAuthController {

	private UserAuthService userService;

	public UserAuthController(UserAuthService userService) {
		this.userService = userService;
	}

	private static final long EXPIRATION_TIME = 864000000;  // 10 days

	@PostMapping("/register")
	public ResponseEntity<?> createUser(@Valid @RequestBody UserAuth user) {
		try {
			userService.createUser(user);
			return new ResponseEntity<>("The user with ID: " + user.getEmail() + " was added", HttpStatus.CREATED);
		} catch (UserAlreadyExistsException | UserTypeMismatchException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
		}
	}

	@PostMapping("/login")
	public ResponseEntity<?> loginUser(@Valid @RequestBody UserAuth user) {
		try {
			userService.findUserByEmailAndPassword(user.getEmail(), user.getPassword());
		} catch (UserNotFoundException e) {
			Map<String, String> faultMap = new HashMap<>();
			faultMap.put("UNAUTHORIZED", e.getMessage());
			return new ResponseEntity<>(faultMap, HttpStatus.UNAUTHORIZED);
		}


		String generatedToken = generateToken(user);
		Map<String, String> tokenMap = new HashMap<>();
		tokenMap.put("token", generatedToken);

		return new ResponseEntity<>(tokenMap, HttpStatus.OK);
	}

	@PutMapping("/updatePassword/{email}/{newPassword}")
//	@PutMapping("/updatePassword)
	public ResponseEntity<?> updatePassword(@PathVariable String email, @PathVariable String newPassword) {

		try{
			userService.updatePassword(email, newPassword);
			return new ResponseEntity<>("Password updated successfully", HttpStatus.OK);
		}catch (UserNotFoundException e){
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}

	@DeleteMapping("/deleteUser/{email}")
	public ResponseEntity<?> deleteUser(@PathVariable("email") String email) {
		try {
			userService.deleteUser(email);
			return new ResponseEntity<>("User deleted successfully", HttpStatus.OK);
		} catch (UserNotFoundException e) {
			return new ResponseEntity<>("User not found in our database.", HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping("/getUserEmail/{email}")
	public String checkUser(@PathVariable String email){
		return userService.checkUserByEmail(email);
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public Map<String, String> handleValidationExceptions (MethodArgumentNotValidException ex) {

		Map<String, String> errors = new HashMap<>();

		ex.getBindingResult().getAllErrors().forEach((error) -> {
			String fieldName = ((FieldError) error).getField();
			String errorMessage = error.getDefaultMessage();
			errors.put(fieldName, errorMessage);
		});

		return errors;
	}

	public static String generateToken(UserAuth user) {
		return Jwts.builder()
				.setSubject(user.getType())
				.setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
				.signWith(SignatureAlgorithm.HS256, "oscarmovie")
				.compact();

	}
}
