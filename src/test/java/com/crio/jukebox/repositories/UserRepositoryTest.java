package com.crio.jukebox.repositories;

import com.crio.jukebox.entities.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class UserRepositoryTest {
    
    @InjectMocks
    UserRepository userRepository;

    @Test
    @DisplayName("Funciton should save a user from given userName and return that user along with its ID")
    public void save_test(){
        // Given
        String userName = "Joy";

        // When

        // Then
        User outputUser = userRepository.save(new User(userName));
        Assertions.assertEquals("Joy", outputUser.getName());
        Assertions.assertNotNull(outputUser.getId());
    }
}