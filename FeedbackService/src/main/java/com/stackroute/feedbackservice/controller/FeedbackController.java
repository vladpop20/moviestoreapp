package com.stackroute.feedbackservice.controller;

import com.stackroute.feedbackservice.exception.*;
import com.stackroute.feedbackservice.model.Feedback;
import com.stackroute.feedbackservice.service.FeedbackService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "movieapp/v1/feedback")
@Validated
public class FeedbackController {

	private FeedbackService feedbackService;

	public FeedbackController(FeedbackService feedbackService) {
		this.feedbackService = feedbackService;
	}

	@PostMapping
	public ResponseEntity<?> createFeedback (@Valid @RequestBody Feedback feedback) {
		try {
			return new ResponseEntity<>(feedbackService.createFeedback(feedback), HttpStatus.CREATED);
		} catch (FeedbackAlreadyExistsException | FeedbackRatingOutOfBoundsException | MovieNotFoundException |
				 UserNotFoundException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
		}
	}

	@DeleteMapping("/{feedbackId}")
	public ResponseEntity<?> deleteFeedback (@PathVariable @Min(value = 0, message = "value should be greater than 0") int feedbackId) {

		ResponseEntity<?> response = null;
		try {
			if (feedbackService.deleteFeedback(feedbackId)) {
				response = new ResponseEntity<>("Feedback deleted with success!", HttpStatus.CREATED);
			}
		} catch (FeedbackNotFoundException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
		return response;
	}

	@PutMapping("{feedbackId}")
	public ResponseEntity<?> updateFeedback(@PathVariable int feedbackId, @RequestBody Feedback updated) {
		try {
			return new ResponseEntity<>(feedbackService.updateFeedback(updated, feedbackId), HttpStatus.OK);
		} catch (FeedbackRatingOutOfBoundsException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_ACCEPTABLE);
		} catch (FeedbackNotFoundException | MovieNotFoundException | UserNotFoundException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping("/customer/{customerId}")
	public ResponseEntity<?> getAllByCustomerId(@PathVariable String customerId) {
		try {
			return new ResponseEntity<>(feedbackService.viewAllFeedbackByCustomerId(customerId), HttpStatus.OK);
		} catch (FeedbackNotFoundException | CustomerNotFoundException | UserNotFoundException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping("/movie/{movieId}")
	public ResponseEntity<?> getAllByMovieId(@PathVariable int movieId) {
		try {
			return new ResponseEntity<>(feedbackService.viewAllFeedbackByMovieId(movieId), HttpStatus.OK);
		} catch (FeedbackNotFoundException | MovieNotFoundException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping("/rating/{movieId}")
	public ResponseEntity<?> getRatingForMovie(@PathVariable int movieId) {
		try {
			return new ResponseEntity<>("Average rating: '" + feedbackService.getRatingByMovieId(movieId) + "'", HttpStatus.OK);
		} catch (MovieNotFoundException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
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

}
