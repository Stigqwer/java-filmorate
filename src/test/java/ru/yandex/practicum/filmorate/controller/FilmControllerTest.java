package ru.yandex.practicum.filmorate.controller;

import org.junit.Test;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.InMemoryFilmStorage;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class FilmControllerTest {
    private final FilmController filmController = new FilmController(new InMemoryFilmStorage());

    @Test
    public void testOk() {
        Film film1 = new Film(1, "Терминатор", "Это описание меньше 200 символов",
                LocalDate.now(), 15);

        assertTrue(filmController.isValidationValues(film1));
    }

    @Test
    public void validationTest() {
        Film film2 = new Film(1, "Терминатор", "Это описание меньше 200 символов",
                LocalDate.of(1895, 12, 28), 15);

        assertTrue(filmController.isValidationValues(film2));

        film2.setReleaseDate(LocalDate.of(1800, 12, 28));

        ValidationException exception1 = assertThrows(ValidationException.class,
                () -> filmController.isValidationValues(film2));

        assertEquals("ReleaseDate before 1895-12-28", exception1.getMessage());
    }
}
