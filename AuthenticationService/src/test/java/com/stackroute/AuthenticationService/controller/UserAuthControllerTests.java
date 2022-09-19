package com.stackroute.AuthenticationService.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stackroute.AuthenticationService.exception.UserAlreadyExistsException;
import com.stackroute.AuthenticationService.exception.UserNotFoundException;
import com.stackroute.AuthenticationService.model.UserAuth;
import com.stackroute.AuthenticationService.service.UserAuthService;
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

import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UserAuthControllerTests {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private UserAuthService userAuthService;

	UserAuth user1;
	UserAuth user2;

	@InjectMocks
	UserAuthController userAuthController;

	@BeforeEach
	public void init() throws Exception {

		MockitoAnnotations.openMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(userAuthController).build();

		user1 = new UserAuth("michael@gmail.com", "pass123", "admin");
		user2 = new UserAuth("pam@gmail.com", "pampam123", "customer");
	}

	@Test
	public void testRegisterUserSuccess() throws Exception {
		Mockito.when(userAuthService.createUser(user1)).thenReturn(true);
		mockMvc.perform(MockMvcRequestBuilders.post("/movieapp/v1/auth/register")
						.contentType(MediaType.APPLICATION_JSON)
						.content(jsonToString(user1)))
				        .andExpect(MockMvcResultMatchers.status().isCreated())
				.andDo(MockMvcResultHandlers.print());
	}

	@Test
	public void testRegisterUserFailureWhenAlreadyExists() throws Exception {
		Mockito.when(userAuthService.createUser(user1)).thenThrow(UserAlreadyExistsException.class);

		mockMvc.perform(MockMvcRequestBuilders.post("/movieapp/v1/auth/register").contentType(MediaType.APPLICATION_JSON)
						.content(jsonToString(user1)))
				        .andExpect(MockMvcResultMatchers.status().isConflict()).andDo(MockMvcResultHandlers.print());

	}

	@Test
	public void testRegisterUserFailureWithInvalidEmail_thenReturnStatus400() throws Exception {
		user1.setEmail("firstgmail.com");

		Mockito.when(userAuthService.createUser(user1)).thenReturn(true);

		mockMvc.perform(MockMvcRequestBuilders.post("/movieapp/v1/auth/register").contentType(MediaType.APPLICATION_JSON)
						.content(jsonToString(user1)))
				.andExpect(MockMvcResultMatchers.status().isBadRequest()).andDo(MockMvcResultHandlers.print());
	}

	@Test
	public void testRegisterUserFailureWithInvalidType_thenReturnStatus400() throws Exception {
		user2.setType("");
		Mockito.when(userAuthService.createUser(user2)).thenReturn(true);

		mockMvc.perform(MockMvcRequestBuilders.post("/movieapp/v1/auth/register").contentType(MediaType.APPLICATION_JSON)
						.content(jsonToString(user2)))
				        .andExpect(MockMvcResultMatchers.status().isBadRequest()).andDo(MockMvcResultHandlers.print());
	}

	@Test
	public void testLoginUserSuccess() throws Exception {

		Mockito.when(userAuthService.findUserByEmailAndPassword(user2.getEmail(), user2.getPassword())).thenReturn(user2);
		mockMvc.perform(MockMvcRequestBuilders.post("/movieapp/v1/auth/login").contentType(MediaType.APPLICATION_JSON)
						.content(jsonToString(user2)))
				        .andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print());
	}

	@Test
	public void testLoginUserFailure() throws Exception {

		Mockito.when(userAuthService.findUserByEmailAndPassword(user2.getEmail(), user2.getPassword())).thenThrow(UserNotFoundException.class);
		mockMvc.perform(MockMvcRequestBuilders.post("/movieapp/v1/auth/login").contentType(MediaType.APPLICATION_JSON)
						.content(jsonToString(user2)))
				        .andExpect(MockMvcResultMatchers.status().isUnauthorized()).andDo(MockMvcResultHandlers.print());
	}

	@Test
	public void testLoginUserFailureWithEmptyEmail_thenReturnStatus400() throws Exception {
		user2.setEmail("");
		Mockito.when(userAuthService.findUserByEmailAndPassword(user2.getEmail(), user2.getPassword())).thenThrow(UserNotFoundException.class);
		mockMvc.perform(MockMvcRequestBuilders.post("/movieapp/v1/auth/login").contentType(MediaType.APPLICATION_JSON)
						.content(jsonToString(user2)))
				.andExpect(MockMvcResultMatchers.status().isBadRequest()).andDo(MockMvcResultHandlers.print());
	}

	@Test
	public void testUserUpdateSuccess_thenReturnStatus200() throws Exception {
		Mockito.when(userAuthService.updatePassword(user2.getEmail(), "test000")).thenReturn(true);
		mockMvc.perform(MockMvcRequestBuilders
						.put("/movieapp/v1/auth/updatePassword/pam@gmail.com/test000")
						.contentType(MediaType.APPLICATION_JSON))
						.andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print());
	}


	@Test
	public void testUserUpdateFailureWithIncorrectEmail_thenReturnStatus404() throws Exception {
		Mockito.when(userAuthService.updatePassword(user2.getEmail(), "google-it")).thenThrow(UserNotFoundException.class);
		String email = user2.getEmail();
		String pass = "google-it";
		mockMvc.perform(MockMvcRequestBuilders
						.put("/movieapp/v1/auth/updatePassword/{email}/{pass}", email, pass)
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isNotFound()).andDo(MockMvcResultHandlers.print());
	}

	@Test
	public void testUserDeleteSuccess_thenReturnStatus200() throws Exception {
		Mockito.when(userAuthService.deleteUser(user2.getEmail())).thenReturn(true);
		String email = user2.getEmail();
		mockMvc.perform(MockMvcRequestBuilders
						.delete("/movieapp/v1/auth/deleteUser/{email}", email)
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print());
	}


	@Test
	public void testUserDeleteFailureWithIncorrectEmail_thenReturnStatus404() throws Exception {
		Mockito.when(userAuthService.deleteUser(user2.getEmail())).thenThrow(UserNotFoundException.class);

		String email = user2.getEmail();
		mockMvc.perform(MockMvcRequestBuilders
						.delete("/movieapp/v1/auth/deleteUser/{email}", email)
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isNotFound()).andDo(MockMvcResultHandlers.print());
	}

	@Test
	public void testGetUserSuccess_thenReturnAnEmailDifferentByNone() throws Exception {
		Mockito.when(userAuthService.checkUserByEmail(user2.getEmail())).thenReturn(user1.getEmail());

		String email = user2.getEmail();
		mockMvc.perform(MockMvcRequestBuilders
						.get("/movieapp/v1/auth/getUserEmail/{email}", email)
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print());
	}

	@Test
	public void testGetUserFail_thenReturnAnStringOfNone() throws Exception {
		Mockito.when(userAuthService.checkUserByEmail(user2.getEmail())).thenReturn("none");

		String email = user2.getEmail();
		mockMvc.perform(MockMvcRequestBuilders
						.get("/movieapp/v1/auth/getUserEmail/{email}", email)
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print());
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
