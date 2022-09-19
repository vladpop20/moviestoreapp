package com.stackroute.feedbackservice.service;

import com.stackroute.feedbackservice.exception.*;
import com.stackroute.feedbackservice.model.Feedback;
import com.stackroute.feedbackservice.repository.FeedbackRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class FeedbackServiceTests {

	@Mock
	private FeedbackRepository feedbackRepository;

	@Mock
	private FeedbackUserConnect feedbackUserConnect;

	@Mock
	private FeedbackMovieConnect feedbackMovieConnect;

//	FeedbackMovieConnect feedbackMovieConnect = new FeedbackMovieConnect() {
//		@Override
//		public Integer getMovieById(int movieId) {
//			return 2;
//		}
//	};

	@MockBean
	private Feedback feedback;

	@MockBean
	private Feedback feedbackInvalid;

	@InjectMocks
	FeedbackServiceImpl feedbackService;

	Optional<Feedback> optionalFeedback;

	@BeforeEach
	public void init() throws Exception {
		MockitoAnnotations.openMocks(this);

		feedback = new Feedback(1,
				"Fantastic movie",
				"I haven't seen a better movie",
				4.5,
				2,
				"bullet@gmail.com");

		feedbackInvalid = new Feedback(1,
				"Fantastic movie",
				"I haven't seen a better movie",
				6.0,
				2,
				"bullet@gmail.com");

		optionalFeedback = Optional.of(feedback);
	}

	@Test
	public void testSaveFeedbackSuccess()
			throws FeedbackAlreadyExistsException, FeedbackRatingOutOfBoundsException, MovieNotFoundException,
			UserNotFoundException {

		Mockito.when(feedbackRepository.findById(1)).thenReturn(Optional.empty());

		Mockito.when(feedbackMovieConnect.getMovieById(feedback.getMovieId())).thenReturn(2);
		Mockito.when(feedbackUserConnect.getUserById(feedback.getCustomerId())).thenReturn("bullet@gmail.com");

		Mockito.when(feedbackRepository.save(feedback)).thenReturn(feedback);
		assertEquals(feedback, feedbackService.createFeedback(feedback));
	}

	@Test
	public void testSaveFeedbackFailureAlreadyExists()
			throws FeedbackAlreadyExistsException, FeedbackRatingOutOfBoundsException, MovieNotFoundException,
			UserNotFoundException {

		Mockito.when(feedbackRepository.findById(1)).thenReturn(optionalFeedback);

		Mockito.when(feedbackMovieConnect.getMovieById(feedback.getMovieId())).thenReturn(2);
		Mockito.when(feedbackUserConnect.getUserById(feedback.getCustomerId())).thenReturn("bullet@gmail.com");

		Mockito.when(feedbackRepository.save(feedback)).thenReturn(feedback);
		assertThrows(FeedbackAlreadyExistsException.class, () -> feedbackService.createFeedback(feedback));
	}

	@Test
	public void testSaveFeedbackFailureRatingOutOfBounds()
			throws FeedbackAlreadyExistsException, FeedbackRatingOutOfBoundsException, MovieNotFoundException,
			UserNotFoundException {

		Mockito.when(feedbackRepository.findById(1)).thenReturn(Optional.empty());

		Mockito.when(feedbackMovieConnect.getMovieById(feedbackInvalid.getMovieId())).thenReturn(2);
		Mockito.when(feedbackUserConnect.getUserById(feedbackInvalid.getCustomerId())).thenReturn("bullet@gmail.com");

		Mockito.when(feedbackRepository.save(feedbackInvalid)).thenReturn(feedbackInvalid);
		assertThrows(FeedbackRatingOutOfBoundsException.class, () -> feedbackService.createFeedback(feedbackInvalid));
	}

	@Test
	public void testSaveFeedbackFailureMovieNotExistent()
			throws FeedbackAlreadyExistsException, FeedbackRatingOutOfBoundsException, MovieNotFoundException,
			UserNotFoundException {

		Mockito.when(feedbackRepository.findById(1)).thenReturn(Optional.empty());

		Mockito.when(feedbackMovieConnect.getMovieById(feedback.getMovieId())).thenReturn(-1);
		Mockito.when(feedbackUserConnect.getUserById(feedback.getCustomerId())).thenReturn("bullet@gmail.com");

		Mockito.when(feedbackRepository.save(feedback)).thenReturn(feedback);
		assertThrows(MovieNotFoundException.class, () -> feedbackService.createFeedback(feedback));
	}

	@Test
	public void testSaveFeedbackFailureUserNotExistent()
			throws FeedbackAlreadyExistsException, FeedbackRatingOutOfBoundsException, MovieNotFoundException,
			UserNotFoundException {

		Mockito.when(feedbackRepository.findById(1)).thenReturn(Optional.empty());

		Mockito.when(feedbackMovieConnect.getMovieById(feedback.getMovieId())).thenReturn(2);
		Mockito.when(feedbackUserConnect.getUserById(feedback.getCustomerId())).thenReturn("none");

		Mockito.when(feedbackRepository.save(feedback)).thenReturn(feedback);
		assertThrows(UserNotFoundException.class, () -> feedbackService.createFeedback(feedback));
	}


	@Test
	public void deleteFeedbackSuccess() throws FeedbackNotFoundException {
		when(feedbackRepository.findById(feedback.getFeedbackId())).thenReturn(optionalFeedback);
		boolean flag = feedbackService.deleteFeedback(feedback.getFeedbackId());
		assertTrue(flag);
	}

	@Test
	public void deleteFeedbackFailure() throws FeedbackNotFoundException {
		when(feedbackRepository.findById(feedback.getFeedbackId())).thenReturn(Optional.empty());
		assertThrows(FeedbackNotFoundException.class, () -> feedbackService.deleteFeedback(feedback.getFeedbackId()));
	}



}
