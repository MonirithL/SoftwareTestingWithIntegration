package com.finalproj.amr.service;
import com.finalproj.amr.entity.User;
import com.finalproj.amr.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.swing.text.html.Option;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private User user1;
    private User user2;

    @BeforeEach
    void setUp() {
        // Sample user
        user1 = new User(1, "Ankim", "ankim@gmail.com", "pass123");
        user2 = new User(2, "Rith", "rith@gmail.com", "pass123");
    }

    @Test
    void testAddUser() {
        when(userRepository.save(user1)).thenReturn(user1);
        User savedUser = userService.addUser(user1);

        assertNotNull(savedUser);
        assertEquals("Ankim", savedUser.getUsername());
        verify(userRepository, times(1)).save(user1);
    }

    @Test
    void testGetAllUsers(){
        when(userRepository.findAll()).thenReturn(Arrays.asList(user1,user2));
        List<User> users= userService.getAllUsers();

        assertEquals(2,users.size());
        verify(userRepository,times(1)).findAll();
    }

    @Test
    void testGetUserById(){
        when(userRepository.findById(1)).thenReturn(Optional.of(user1));
        Optional<User> user = userService.getUserById(1);
        assertTrue(user.isPresent());
        assertEquals("Ankim",user.get().getUsername());
//        Fail result below if want to test
//        assertEquals("Rith",user.get().getUsername());
        verify(userRepository, times(1)).findById(1);
    }

    @Test
    void checkUserLogin_Success(){
        when(userRepository.findByEmail("ankim@gmail.com")).thenReturn(Optional.of(user1));
        Optional<User> acc = userService.checkUserLogin("ankim@gmail.com","pass123");

        assertTrue(acc.isPresent());
        assertEquals("Ankim",acc.get().getUsername());
    }

    @Test
    void checkUserLogin_NotFound(){
        when(userRepository.findByEmail("lmao@gmail.com")).thenReturn(Optional.empty());
        Optional<User> acc = userService.checkUserLogin("lmao@gmail.com","pass123");

        assertTrue(acc.isEmpty());
    }

    @Test
    void checkUserLogin_Wrong(){
        when(userRepository.findByEmail("ankim@gmail.com")).thenReturn(Optional.of(user1));
        Optional<User> acc = userService.checkUserLogin("ankim@gmail.com","123456");

        assertTrue(acc.isEmpty());
    }

}
