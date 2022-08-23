package ru.yandex.practicum.filmorate.storage.film;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.ConflictException;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class InMemoryFilmStorage implements FilmStorage {
    private final Map<Integer, Film> films = new HashMap<>();


    @Override
    public List<Film> findAll() {
        return new ArrayList<>(films.values());
    }

    @Override
    public Film create(Film film) {
        if (films.containsKey(film.getId())) {
            throw new ConflictException("You can't use this id");
        } else {
            films.put(film.getId(), film);
            log.info("film created");
            return film;
        }
    }

    @Override
    public Film update(Film film) {
        if (films.containsKey(film.getId())) {
            films.put(film.getId(), film);
            log.info("film updated");
            return film;
        } else {
            throw new NotFoundException("Not have this id on server");
        }
    }

    @Override
    public Film getFilm(int id) {
        return films.get(id);
    }


}
