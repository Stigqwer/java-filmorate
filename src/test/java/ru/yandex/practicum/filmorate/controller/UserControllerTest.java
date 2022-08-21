package ru.yandex.practicum.filmorate.controller;

import org.junit.Test;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.InMemoryUserStorage;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class UserControllerTest {
    private final UserController userController = new UserController(new InMemoryUserStorage());

    @Test
    public void testOk() {
        User user1 = new User(1, "stigqwer@gmail.com", "stigqwer", "Максим",
                LocalDate.of(1990, 11, 4));

        assertTrue(userController.isValidationValues(user1));
    }

    @Test
    public void validationTest() {
        User user1 = new User(1, "stigqwer@gmail.com", "stigqwer", "",
                LocalDate.of(1990, 11, 4));
        userController.isValidationValues(user1);

        assertEquals("stigqwer", user1.getName());

        user1.setLogin("stig qwer");
        ValidationException exception = assertThrows(ValidationException.class,
                () -> userController.isValidationValues(user1));

        assertEquals("Login must be didn't have a blank char", exception.getMessage());
    }


}
