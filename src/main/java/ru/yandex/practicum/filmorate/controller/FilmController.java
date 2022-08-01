package ru.yandex.practicum.filmorate.controller;

import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/films")
public class FilmController {

    private final Map<Integer, Film> films = new HashMap<>();

    @GetMapping
    public List<Film> findAll() {
        return new ArrayList<>(films.values());
    }

    @PostMapping
    public Film create(@RequestBody Film film) {
        film.setId(films.size()+1);
        films.put(film.getId(),film);
        return film;
    }

    @PutMapping
    public Film update(@RequestBody Film film) {
        if(films.containsKey(film.getId())){
            films.put(film.getId(),film);
            return film;
        } else {
            throw new ValidationException("Передан не верный id");
        }
    }
}
