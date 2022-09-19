package com.stackroute.feedbackservice.service;

import com.stackroute.feedbackservice.exception.*;
import com.stackroute.feedbackservice.model.Feedback;

import java.util.List;


public interface FeedbackService {

	Feedback createFeedback (Feedback feedback)
			throws FeedbackAlreadyExistsException, FeedbackRatingOutOfBoundsException, MovieNotFoundException,
			UserNotFoundException;

	boolean deleteFeedback (int feedbackId) throws FeedbackNotFoundException;

	Feedback updateFeedback (Feedback feedback, int feedbackId)
			throws FeedbackNotFoundException, FeedbackRatingOutOfBoundsException, MovieNotFoundException,
			UserNotFoundException;

	List<Feedback> viewAllFeedbackByCustomerId (String customerID)
			throws FeedbackNotFoundException, CustomerNotFoundException, UserNotFoundException;

	List<Feedback> viewAllFeedbackByMovieId (int movieID) throws FeedbackNotFoundException, MovieNotFoundException;

	Double getRatingByMovieId (int movieId) throws MovieNotFoundException;


}
