package ru.yandex.practicum.filmorate.controller;

import org.junit.Test;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class FilmControllerTest {
    private FilmController filmController;

    @Test
    public void validationTest() {
        filmController = new FilmController();
        Film film1 = new Film(1, "Терминатор", "Это описание меньше 200 символов",
                LocalDate.now(), 15);

        assertTrue(filmController.isValidationValues(film1));

        film1.setDescription("Это описание ровно 200 символов!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!" +
                "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!" +
                "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");

        assertTrue(filmController.isValidationValues(film1));

        film1.setDescription("Это описание ровно 201 символ!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!" +
                "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!" +
                "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        ValidationException exception = assertThrows(ValidationException.class,
                () -> filmController.isValidationValues(film1));

        assertEquals("Длина описания больше 200 символов", exception.getMessage());

        Film film2 = new Film(1, "Терминатор", "Это описание меньше 200 символов",
                LocalDate.of(1895,12,28), 15);

        assertTrue(filmController.isValidationValues(film2));

        film2.setReleaseDate(LocalDate.of(1800,12,28));

        ValidationException exception1 = assertThrows(ValidationException.class,
                () -> filmController.isValidationValues(film2));

        assertEquals("Дата релиза раньше 28 декабря 1895 года", exception1.getMessage());
    }
}
