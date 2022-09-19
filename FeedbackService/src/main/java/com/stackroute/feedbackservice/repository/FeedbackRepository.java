package com.stackroute.feedbackservice.repository;

import com.stackroute.feedbackservice.model.Feedback;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FeedbackRepository extends MongoRepository<Feedback, Integer> {

	List<Feedback> findAllByCustomerId (String customerId);

	List<Feedback> findAllByMovieId (int movieId);

	boolean existsFeedbackByMovieIdAndCustomerId (int movieId, String customerId);

	@Aggregation("{ '$group':  { '_id': 'movieId', 'averageRating':  { $avg:  '$rating'}}}")
	Double getAverageRatingByAllFeedbacksForMovie(int movieId);

}
