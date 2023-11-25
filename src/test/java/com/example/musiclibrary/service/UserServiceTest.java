package com.example.musiclibrary.service;

import com.example.musiclibrary.model.User;
import com.example.musiclibrary.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.stream.Stream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    UserRepository userRepository;

    @InjectMocks
    UserService userService;


    @Test
    void testSwitchProfile() {

        User user = User.builder()
                        .id(2L)
                        .userName("Razvan")
                        .age(20)
                        .build();

        when(userRepository.findUserByUserName("Razvan")).thenReturn(user);
        userService.switchProfile("Razvan");
        assertEquals(userService.getMyProfile(), user);
        verify(userRepository).findUserByUserName("Razvan");

        when(userRepository.findUserByUserName("Raz")).thenReturn(null);
        User fakeUser = userService.switchProfile("Raz");
        assertNull(fakeUser);
    }


    @ParameterizedTest(name = "Case: {0}")
    @MethodSource("retrieveArgumentsTestAddUser")
    public void testAddUser(String testCase, String userName, String password, String firstName) {

        User user = User.builder()
                        .id(2L)
                        .userName(userName)
                        .password(password)
                        .firstName(firstName)
                        .age(20)
                        .build();



        if( testCase.contains("password already used") ) {
            when(userRepository.findByPassword(user.getPassword())).thenReturn(user);
        }
        if( testCase.contains("password wrong") || testCase.contains("password correct") ) {
            when(userRepository.findByPassword(user.getPassword())).thenReturn(null);
        }
        if( testCase.contains("username already used") ) {
            when(userRepository.findUserByUserName("razvan")).thenReturn(user);
        }
        if( testCase.contains("user correctly registered") ) {
            when(userRepository.findUserByUserName("razvan123")).thenReturn(null);
        }

        User profileToAdd = userService.addUser(user);
        assertNull(profileToAdd);

        if( testCase.contains("user correctly registered") ) {
            verify(userRepository).save(user);
        }
    }

    private static Stream<? extends Arguments> retrieveArgumentsTestAddUser() {
        return Stream.of(
                Arguments.arguments("user firstName null",
                                    "razvan123",
                                    "password",
                                    null
                ),
                Arguments.arguments("user password null",
                                    "razvan123",
                                    null,
                                    "Razvan"
                ),
                Arguments.arguments("user password already used",
                                    "razvan123",
                                    "passwordUsed",
                                    "Razvan"
                ),
                Arguments.arguments("user password wrong because it does not contain any special character",
                                    "razvan123",
                                    "gcfdytrtf2345",
                                    "Razvan"
                ),
                Arguments.arguments("user password wrong because it contains first name",
                                    "razvan123",
                                    "!Razvan31542",
                                    "Razvan"
                ),
                Arguments.arguments("user password wrong because it does not contain at least 8 digits",
                                    "razvan123",
                                    "!Raz32",
                                    "Razvan"
                ),
                Arguments.arguments("user password correct but null username",
                                    null,
                                    "!Raz323247",
                                    "Razvan"
                ),
                Arguments.arguments("user password correct but username already used",
                                    "usernameUsed",
                                    "!Raz323247",
                                    "Razvan"
                ),
                Arguments.arguments("user password correct and user correctly registered",
                                    "razvan123",
                                    "!Raz323247",
                                    "Razvan"
                )
        );
    }


    @ParameterizedTest(name = "Case: {0}")
    @MethodSource("retrieveArgumentsTestUpdateMyProfile")
    public void testUpdateMyProfile(String testCase, String firstName, String lastName, String userName, String password, String address) {

        userService.setMyProfile(User.builder()
                                        .userName("current userName")
                                        .password("current password")
                                        .oldPassword("old password")
                                        .firstName("current firstName")
                                        .lastName("current firstName")
                                        .address("current address")
                                        .build());

        User newProfile = User.builder()
                                .userName(userName)
                                .password(password)
                                .firstName(firstName)
                                .lastName(lastName)
                                .address(address)
                                .build();

        when(userRepository.save(userService.getMyProfile())).thenReturn(newProfile);

        if( testCase.contains("password is already used") ) {
            when(userRepository.findByPassword(newProfile.getPassword())).thenReturn(newProfile);
        }
        if( testCase.contains("no special character") ) {
            when(userRepository.findByPassword(newProfile.getPassword())).thenReturn(null);
        }

        newProfile = userService.updateMyProfile(newProfile);

        if( testCase.contains("no new password") ) {
            assertThat(userService.getMyProfile())
                    .usingRecursiveComparison()
                    .ignoringFields("password")
                    .ignoringFields("oldPassword")
                    .isEqualTo(newProfile);
        }

        verify(userRepository).save(userService.getMyProfile());

        if( testCase.contains("password correctly updated") ) {
            assertEquals(userService.getMyProfile().getPassword(), newProfile.getPassword());
            assertEquals(userService.getMyProfile().getOldPassword(), "current password");
        } else {
            assertEquals(userService.getMyProfile().getPassword(), "current password");
        }
    }


    private static Stream<? extends Arguments> retrieveArgumentsTestUpdateMyProfile() {
        return Stream.of(
                Arguments.arguments("no new password",
                                    "new firstName",
                                    "new lastName",
                                    "new userName",
                                    null,
                                    "new address"
                ),
                Arguments.arguments("password update not valid: new password = old password",
                                    null,
                                    null,
                                    null,
                                    "old password",
                                    null
                ),
                Arguments.arguments("password update not valid: password is already used",
                                    null,
                                    null,
                                    null,
                                    "password already existent",
                                    null
                ),
                Arguments.arguments("password update not valid: no special character",
                                    null,
                                    null,
                                    null,
                                    "no special character",
                                    null
                ),
                Arguments.arguments("password update not valid: password contains your name",
                                    "Razvan",
                                    null,
                                    null,
                                    "!Razvan123",
                                    null
                ),
                Arguments.arguments("password update not valid: minimum digits 8",
                                    null,
                                    null,
                                    null,
                                    "!raz",
                                    null
                ),
                Arguments.arguments("password correctly updated",
                                    null,
                                    null,
                                    null,
                                    "!Razvan123",
                                    null
                )
        );
    }


    @Test
    void testAddUser() {

        // 1st case: firstName null
        User user = User.builder()
                        .id(2L)
                        .userName("razvan123")
                        .age(20)
                        .build();

        // 5th case: password contains first name
        user.setPassword("!Razvan31542");

        User profileToAdd = userService.addUser(user);
        assertNull(profileToAdd);
        System.out.println("\n");

        // 6th case: password not contains at least 8 digits
        user.setPassword("!Raz32");

        profileToAdd = userService.addUser(user);
        assertNull(profileToAdd);
        System.out.println("\n");

        // 7th case: username null
        user.setUserName(null);
        user.setPassword("!Raz323247");

        profileToAdd = userService.addUser(user);
        assertNull(profileToAdd);
        System.out.println("\n");

        // 8th case: userName already used
        user.setUserName("razvan123");
        user.setPassword("!Raz323247");

        when(userRepository.findUserByUserName("razvan123")).thenReturn(user);
        profileToAdd = userService.addUser(user);
        assertNull(profileToAdd);
        System.out.println("\n");

        // 9th case: user correctly registered
        user.setUserName("razvan123");
        user.setPassword("!Raz323247");
        user.setLastName("Bogdan");
        user.setBirthdate(LocalDate.now());

        when(userRepository.findUserByUserName("razvan123")).thenReturn(null);

        userService.addUser(user);

        verify(userRepository).save(user);
    }

}
