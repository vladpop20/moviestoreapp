package com.stackroute.feedbackservice.service;

import com.stackroute.feedbackservice.exception.*;
import com.stackroute.feedbackservice.model.Feedback;
import com.stackroute.feedbackservice.repository.FeedbackRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FeedbackServiceImpl implements FeedbackService{


	private FeedbackMovieConnect feedbackMovieConnect;
	private FeedbackUserConnect feedbackUserConnect;
	private FeedbackRepository feedbackRepository;

	public FeedbackServiceImpl(FeedbackRepository feedbackRepository, FeedbackUserConnect feedbackUserConnect,
			FeedbackMovieConnect feedbackMovieConnect) {

		this.feedbackRepository = feedbackRepository;
		this.feedbackUserConnect = feedbackUserConnect;
		this.feedbackMovieConnect = feedbackMovieConnect;
	}

	@Override
	public Feedback createFeedback(Feedback feedback)
			throws FeedbackAlreadyExistsException, FeedbackRatingOutOfBoundsException, MovieNotFoundException,
			UserNotFoundException {

		Optional<Feedback> foundFeedback = feedbackRepository.findById(feedback.getFeedbackId());
		if (foundFeedback.isPresent()) {
			throw new FeedbackAlreadyExistsException("Feedback with ID: '" + feedback.getFeedbackId() + "' already exists!");
		}

		if (feedback.getRating() < 0.0 || feedback.getRating() > 5.0) {
			throw new FeedbackRatingOutOfBoundsException("Feedback rating must be in the range of 0 - 5 !");
		}

		if (feedbackRepository.existsFeedbackByMovieIdAndCustomerId(feedback.getMovieId(), feedback.getCustomerId())) {
			throw new FeedbackAlreadyExistsException("Duplicate feedback for this user: '" + feedback.getCustomerId() + "' and movie: '" +
					feedback.getMovieId() + "' !");
		}

		if (feedbackMovieConnect.getMovieById(feedback.getMovieId()) == -1) {
			throw new MovieNotFoundException("Movie with ID: '" + feedback.getMovieId() + "' doesn't exists!");
		}

		if (feedbackUserConnect.getUserById(feedback.getCustomerId()).equals("none")) {
			throw new UserNotFoundException("Customer with ID: '" + feedback.getCustomerId() + "' doesn't exists!");
		}

		Feedback savedOne = feedbackRepository.save(feedback);
		if (savedOne != null) {
			return savedOne;
		}

		return null;
	}

	@Override
	public boolean deleteFeedback(int feedbackId) throws FeedbackNotFoundException {
		Optional<Feedback> foundFeedback = feedbackRepository.findById(feedbackId);
		if (foundFeedback.isEmpty()) {
			throw new FeedbackNotFoundException("Feedback with ID: '" + feedbackId + "' doesn't exists!");
		}

		feedbackRepository.delete(foundFeedback.get());
		return true;
	}

	@Override
	public Feedback updateFeedback(Feedback feedback, int feedbackId)
			throws FeedbackNotFoundException, FeedbackRatingOutOfBoundsException, MovieNotFoundException,
			UserNotFoundException {
		Optional<Feedback> foundFeedback = feedbackRepository.findById(feedbackId);
		if (foundFeedback.isEmpty()) {
			throw new FeedbackNotFoundException("Feedback with ID: '" + feedbackId + "' doesn't exists!");
		}

		if (feedback.getRating() < 0.0 || feedback.getRating() > 5.0) {
			throw new FeedbackRatingOutOfBoundsException("Feedback rating must be in the range of 0 - 5 !");
		}

		if (feedbackMovieConnect.getMovieById(feedback.getMovieId()) == -1) {
			throw new MovieNotFoundException("Movie with ID: '" + feedback.getMovieId() + "' doesn't exists!");
		}

		if (feedbackUserConnect.getUserById(feedback.getCustomerId()).equals("none")) {
			throw new UserNotFoundException("Customer with ID: '" + feedback.getCustomerId() + "' doesn't exists!");
		}

		feedbackRepository.save(feedback);
		return feedback;
	}

	@Override
	public List<Feedback> viewAllFeedbackByCustomerId(String customerID)
			throws FeedbackNotFoundException, CustomerNotFoundException, UserNotFoundException {

		if (feedbackUserConnect.getUserById(customerID).equals("none")) {
			throw new UserNotFoundException("Customer with ID: '" + customerID + "' doesn't exists!");
		}
		return feedbackRepository.findAllByCustomerId(customerID);
	}

	@Override
	public List<Feedback> viewAllFeedbackByMovieId(int movieID)	throws FeedbackNotFoundException, MovieNotFoundException {
		if (feedbackMovieConnect.getMovieById(movieID) == -1) {
			throw new MovieNotFoundException("Movie with ID: '" + movieID + "' doesn't exists!");
		}
		return feedbackRepository.findAllByMovieId(movieID);
	}

	@Override
	public Double getRatingByMovieId(int movieId) throws MovieNotFoundException {
		return feedbackRepository.getAverageRatingByAllFeedbacksForMovie(movieId);
	}


}
