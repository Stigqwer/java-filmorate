package ru.yandex.practicum.filmorate.controller;

import org.junit.Test;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class UserControllerTest {
    UserController userController;

    @Test
    public void validationTest(){
        userController = new UserController();
        User user1 = new User(1,"stigqwer@gmail.com", "stigqwer", "Максим",
                LocalDate.of(1990,11,4));

        assertTrue(userController.isValidationValues(user1));

        user1.setName("");
        userController.isValidationValues(user1);

        assertEquals("stigqwer", user1.getName());

        user1.setLogin("stig qwer");
        ValidationException exception = assertThrows(ValidationException.class,
                () -> userController.isValidationValues(user1));

        assertEquals("Логин не может содержать пробелы", exception.getMessage());
    }


}
