package com.stackroute.userservice.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import com.stackroute.userservice.exception.UserAlreadyExistException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.stackroute.userservice.domain.User;

import com.stackroute.userservice.exception.UserNotFoundException;
import com.stackroute.userservice.repository.UserRepository;


public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    private User user;

    @InjectMocks
    private UserServiceImpl service;

    private Optional<User> options;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        user = new User("raghav@gmail.com", "12345", "raghav", "arora");
        options = Optional.of(user);
    }

    @Test
    public void testSaveUserSuccess() throws UserAlreadyExistException {
        when(userRepository.save(user)).thenReturn(user);
        User flag = service.saveUser(user);
        assertEquals("Cannot Register User",user,flag);
        verify(userRepository, times(1)).save(user);
    }

    @Test(expected = UserAlreadyExistException.class)
    public void testSaveUserFailure() throws UserAlreadyExistException {
        when(userRepository.findById(user.getEmailId())).thenReturn(options);
        when(userRepository.save(user)).thenReturn(user);
        User flag = service.saveUser(user);
        assertTrue("saving user failed",false);
        verify(userRepository, times(1)).findById(user.getEmailId());
    }

    @Test
    public void testValidateSuccess() throws UserNotFoundException {
        when(userRepository.findByEmailIdAndPassword(user.getEmailId(), user.getPassword())).thenReturn(user);
        User userResult = userRepository.findByEmailIdAndPassword(user.getEmailId(), user.getPassword());
        assertNotNull(userResult);
        assertEquals(user.getEmailId(), userResult.getEmailId());
        verify(userRepository, times(1)).findByEmailIdAndPassword(user.getEmailId(), user.getPassword());
    }

    @Test
    public void testValidateFailure() throws UserNotFoundException {
        when(userRepository.findById("raghav@gmail.com")).thenReturn(Optional.empty());
        userRepository.findByEmailIdAndPassword(user.getEmailId(), user.getPassword());
    }
}