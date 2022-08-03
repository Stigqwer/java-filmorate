package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import javax.validation.Valid;
import java.time.LocalDate;

@RestController
@Slf4j
@RequestMapping("/films")
public class FilmController extends Controller<Film> {

    public static LocalDate MIN_RELEASE_DATE = LocalDate.parse(System.getenv("MIN_RELEASE_DATE"));


    @PostMapping
    @Override
    public Film create(@Valid @RequestBody Film film) {
        super.create(film);
        film.setId(id++);
        data.put(film.getId(), film);
        log.info("film created");
        return film;
    }

    @PutMapping
    @Override
    public Film update(@Valid @RequestBody Film film) {
        super.update(film);
        if (data.containsKey(film.getId())) {
            data.put(film.getId(), film);
            log.info("film updated");
            return film;
        } else {
            throw new ValidationException("Not have this id on server");
        }
    }

    @Override
    boolean isValidationValues(Film film) {
        if (film.getReleaseDate() != null
                && film.getReleaseDate().isBefore(MIN_RELEASE_DATE)) {
            throw new ValidationException("ReleaseDate before 1895-12-28");
        }
        return true;
    }
}
