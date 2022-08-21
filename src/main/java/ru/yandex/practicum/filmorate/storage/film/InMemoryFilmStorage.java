package ru.yandex.practicum.filmorate.storage.film;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class InMemoryFilmStorage implements FilmStorage{
    private final Map<Integer, Film> films = new HashMap<>();
    private int id = 1;
    private static final LocalDate MIN_RELEASE_DATE = LocalDate.parse("1895-12-28");

    @Override
    public List<Film> findAll() {
        return new ArrayList<>(films.values());
    }

    @Override
    public Film create(Film film) {
        isValidationValues(film);
        film.setId(id++);
        films.put(film.getId(), film);
        log.info("film created");
        return film;
    }

    @Override
    public Film update(Film film) {
        isValidationValues(film);
        if (films.containsKey(film.getId())) {
            films.put(film.getId(), film);
            log.info("film updated");
            return film;
        } else {
            throw new ValidationException("Not have this id on server");
        }
    }

    public boolean isValidationValues(Film film) {
        if (film.getReleaseDate() != null
                && film.getReleaseDate().isBefore(MIN_RELEASE_DATE)) {
            throw new ValidationException("ReleaseDate before 1895-12-28");
        }
        return true;
    }
}
