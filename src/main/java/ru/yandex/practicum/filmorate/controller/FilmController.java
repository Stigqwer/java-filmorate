package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Slf4j
@RequestMapping("/films")
public class FilmController {

    private final Map<Integer, Film> films = new HashMap<>();

    @GetMapping
    public List<Film> findAll() {
        return new ArrayList<>(films.values());
    }

    @PostMapping
    public Film create(@Valid @RequestBody Film film) {
        isValidationValues(film);
        film.setId(films.size()+1);
        films.put(film.getId(),film);
        log.info("Фильм добавлен успешно");
        return film;
    }

    @PutMapping
    public Film update(@Valid @RequestBody Film film) {
        isValidationValues(film);
        if(films.containsKey(film.getId())){
            films.put(film.getId(),film);
            log.info("Фильм успешно обновлен");
            return film;
        } else {
            log.error("Передан не верный id");
            throw new ValidationException("Передан не верный id");
        }
    }

    boolean isValidationValues(Film film){
        if(film.getDescription() != null && film.getDescription().length() > 200){
            throw new ValidationException("Длина описания больше 200 символов");
        } else if(film.getReleaseDate() != null
                && film.getReleaseDate().isBefore(LocalDate.of(1895,12,28))){
            throw new ValidationException("Дата релиза раньше 28 декабря 1895 года");
        }
        return true;
    }
}
