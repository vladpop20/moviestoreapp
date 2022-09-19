package com.stackroute.feedbackservice.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Document
@Setter
@Getter
@NoArgsConstructor
public class Feedback {

	@Id
	@NotNull
	@Positive
	private Integer feedbackId;

	@NotBlank(message = "Title is mandatory")
	private String title;

	@NotBlank(message = "Description is mandatory")
	private String description;

	@NotNull(message = "Rating is mandatory")
	@DecimalMin(value = "0.0", message = "The value should be greater than 0!")
	@DecimalMax(value = "5.1", message = "Maximum value should be 5.0!")
	private Double rating;

	@NotNull(message = "MovieId is mandatory")
	@Positive(message = "The movie ID should be greater than 0")
	private Integer movieId;

	@NotBlank(message = "The customer ID is mandatory")
	private String customerId;

	private LocalDateTime createdAt;

	public Feedback(int feedbackId, String title, String description, Double rating, Integer movieId, String customerId) {
		this.feedbackId = feedbackId;
		this.title = title;
		this.description = description;
		this.rating = rating;
		this.movieId = movieId;
		this.customerId = customerId;
		this.createdAt = LocalDateTime.now();
	}

	public void setCreatedAt() {
		this.createdAt = LocalDateTime.now();
	}

	@Override public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof Feedback))
			return false;
		Feedback feedback = (Feedback) o;
		return feedbackId.equals(feedback.feedbackId) && title.equals(feedback.title) && description.equals(
				feedback.description) && rating.equals(feedback.rating) && movieId.equals(
				feedback.movieId) && customerId.equals(feedback.customerId) && createdAt.equals(feedback.createdAt);
	}

	@Override public int hashCode() {
		return Objects.hash(feedbackId, title, description, rating, movieId, customerId, createdAt);
	}
}
