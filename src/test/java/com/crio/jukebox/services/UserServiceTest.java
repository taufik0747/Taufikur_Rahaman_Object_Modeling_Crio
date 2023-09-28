package com.crio.jukebox.services;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import com.crio.jukebox.repositories.IUserRepository;
import com.crio.jukebox.entities.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    IUserRepository userRepository;
    @InjectMocks
    UserService userService;

    @Test
    @DisplayName("When UserName is passed, it should return a user with the same name!")
    public void createUser_Test(){

        // Given
        String aNewUserName = "Joy";
        User expectedUser = new User("Joy");

        // When
        when(userRepository.save(any(User.class))).thenReturn(expectedUser);

        // Then
        User savedUser = userService.createUser(aNewUserName);
        Assertions.assertEquals("Joy", savedUser.getName());
        Assertions.assertNull(savedUser.getId());
    }

}