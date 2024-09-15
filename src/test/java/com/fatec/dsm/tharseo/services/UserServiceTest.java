package com.fatec.dsm.tharseo.services;

import com.fatec.dsm.tharseo.models.Role;
import com.fatec.dsm.tharseo.models.User;
import com.fatec.dsm.tharseo.repositories.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private Temporary temporary;

    @Mock
    private RoleService roleService;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    @Test
    @DisplayName("Should insert new user in Database")
    void insertOneUserCase1() {
        User user = new User(null, "Richard", "Guedes", "11966066684", "guedes@gmail.com", "1234", 1);
        Role roleBasic = new Role();
        when(roleService.findByName("BASIC")).thenReturn(roleBasic);

        user.setPassword("password");
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");

        userService.insertOne(user);

        verify(userRepository, times(1)).save(user);

        //temporary
        User newUser = userService.findByEmail(user.getEmail());
        temporary.insertDollar(newUser);
        verify(temporary, times(2)).insertDollar(newUser);
    }

    @Test
    @DisplayName("Should not insert new user in Database")
    void insertOneUserCase2() {
        userService.insertOne(null);
        verify(userRepository, never()).save(any());
    }

    @Test
    @DisplayName("Should get all users from Database")
    void findAllUsersCase1() {
        List<User> users = Arrays.asList(new User(), new User());
        when(userRepository.findAll()).thenReturn(users);
        List<User> userList = userService.findAll();
        assertEquals(2, userList.size());
    }

    @Test
    @DisplayName("Should not get all users when not exists")
    void findAllUsersCase2() {
        when(userRepository.findAll()).thenReturn(Arrays.asList());
        List<User> userList = userService.findAll();
        assertEquals(0, userList.size());
    }

    @Test
    @DisplayName("Should get user by attribute long id from Database")
    void findByIdCase1() {
        User user = new User();
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        User foundUser = userService.findById(1L);
        assertEquals(user, foundUser);
    }

    @Test
    @DisplayName("Should not get user by attribute long id when not exists")
    void findByIdCase2() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());
        User foundUser = userService.findById(1L);
        assertNull(foundUser);
    }

    @Test
    @DisplayName("Should get user by attribute email from Database")
    void findByEmailCase1() {
        User user = new User();
        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(user));
        User foundUser = userService.findByEmail("test@example.com");
        assertEquals(user, foundUser);
    }

    @Test
    @DisplayName("Should not get user by attribute email when not exists")
    void findByEmailCase2() {
        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.empty());
        User foundUser = userService.findByEmail("test@example.com");
        assertNull(foundUser);
    }

    @Test
    @DisplayName("Should get user by attribute phone number from Database")
    void findByPhoneNumberCase1() {
        User user = new User();
        when(userRepository.findByPhoneNumber("123456789")).thenReturn(Optional.of(user));
        User foundUser = userService.findByPhoneNumber("123456789");
        assertEquals(user, foundUser);
    }

    @Test
    @DisplayName("Should not get user by attribute phone number when not exists")
    void findByPhoneNumberCase2() {
        when(userRepository.findByPhoneNumber("123456789")).thenReturn(Optional.empty());
        User foundUser = userService.findByPhoneNumber("123456789");
        assertNull(foundUser);
    }


    @Test
    @DisplayName("Should updated user in Database")
    void updateUserCase1() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());
        User updatedUser = new User();
        updatedUser.setName("John");
        User result = userService.updateUser(1L, updatedUser);
        assertNull(result);
    }

    @Test
    @DisplayName("Should deleted user by attribute id from Database")
    void deleteUserByIdCase1() {
        User existingUser = new User();
        existingUser.setId(1L);
        when(userRepository.findById(1L)).thenReturn(Optional.of(existingUser));

        userService.DeleteUserById(1L);
        assertEquals(0, existingUser.getIsactive());
        verify(userRepository, times(1)).save(existingUser);
    }

    @Test
    @DisplayName("Should not deleted user by attribute id when not exists")
    void deleteUserByIdCase2() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());
        userService.DeleteUserById(1L);
        verify(userRepository, never()).save(any());
    }
}

