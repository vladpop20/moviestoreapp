package com.stackroute.feedbackservice.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stackroute.feedbackservice.exception.FeedbackAlreadyExistsException;
import com.stackroute.feedbackservice.model.Feedback;
import com.stackroute.feedbackservice.service.FeedbackService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class FeedbackControllerTests {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	FeedbackService feedbackService;

	Feedback feedback1;
	Feedback feedback2;

	@InjectMocks
	FeedbackController feedbackController;


	@BeforeEach
	public void init() throws Exception {

		MockitoAnnotations.openMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(feedbackController).build();

		feedback1 = new Feedback(1,
				"Fantastic movie",
				"I haven't seen a better movie",
				3.4,
				2,
				"bullet@gmail.com");

		feedback2 = new Feedback(2,
				"Amazing",
				"Very different movie by others",
				4.1,
				1,
				"bullet@gmail.com");
	}


	@Test
	public void testCreateFeedbackSuccess() throws Exception {
//		Mockito.when(feedbackService.createFeedback(any())).thenReturn(feedback2);
//
//		mockMvc.perform(MockMvcRequestBuilders.post("/movieapp/v1/feedback")
//						.contentType(MediaType.APPLICATION_JSON)
//						.content(jsonToString(feedback2)))
//				.andExpect(MockMvcResultMatchers.status().isCreated())
//				.andDo(MockMvcResultHandlers.print());
	}

	@Test
	public void testCreateFeedbackFailure() throws Exception {
//		Mockito.when(feedbackService.createFeedback(any())).thenThrow(FeedbackAlreadyExistsException.class);
//
//		mockMvc.perform(MockMvcRequestBuilders.post("/movieapp/v1/feedback")
//						.contentType(MediaType.APPLICATION_JSON)
//						.content(jsonToString(feedback2)))
//				.andExpect(MockMvcResultMatchers.status().isConflict())
//				.andDo(MockMvcResultHandlers.print());
	}


	// Parsing String format data into JSON format
	private static String jsonToString(final Object obj) throws JsonProcessingException {
		String result;
		try {
			final ObjectMapper mapper = new ObjectMapper();
			final String jsonContent = mapper.writeValueAsString(obj);
			result = jsonContent;
		} catch (JsonProcessingException e) {
			result = "Json processing error";
		}
		return result;
	}

}
