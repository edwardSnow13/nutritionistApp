package com.stackroute.userservice.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.stackroute.userservice.domain.User;
import com.stackroute.userservice.domain.UserCredentialsDTO;
import com.stackroute.userservice.exception.UserAlreadyExistException;
import com.stackroute.userservice.service.UserServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest(controllers = UserController.class)
@ExtendWith(SpringExtension.class)
class UserControllerTest {

    @MockBean
    private UserServiceImpl userService;

    @Autowired
    MockMvc mockMvc;

    @InjectMocks
    private UserController userController;

    @Autowired
    private ObjectMapper mapper;

    private User user;
    private UserCredentialsDTO userCredDTO;



    @BeforeEach
    void setUp() {
        user = new User("a@a.com","pass","ayush", "sharma");
    }

    @AfterEach
    void tearDown() {
        user = null;
    }

    @Test
    void whenGivenProfileToRegisterThenShouldReturnRegisteredUser() throws Exception {
        when(userService.saveUser(any())).thenReturn(user);

        mockMvc.perform(post("/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(user)))
                .andExpect(status().is(201));
        verify(userService,times(1)).saveUser(any());
    }

    @Test
    void whenGivenProfileToLoginThenShouldTokenAndEmailId() throws Exception {
        when(userService.authenticateUser(any())).thenReturn(Map.of());
        UserCredentialsDTO userDto = new UserCredentialsDTO("a@a.com","pass");
        mockMvc.perform(post("/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(userDto)))
                .andExpect(status().is(200));
        verify(userService,times(1)).authenticateUser(any());
    }
}