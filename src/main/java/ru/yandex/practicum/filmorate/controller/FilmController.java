package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.film.FilmService;

import javax.validation.Valid;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/films")
public class FilmController {
    private final FilmService filmService;

    @Autowired
    public FilmController(FilmService filmService) {
        this.filmService = filmService;
    }

    @GetMapping
    public List<Film> findAll() {
        return filmService.findAll();
    }

    @PostMapping
    public Film create(@Valid @RequestBody Film film) {
        return filmService.create(film);
    }

    @PutMapping
    public Film update(@Valid @RequestBody Film film) {
        if(film.getId() < 1){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return filmService.update(film);
    }

    @GetMapping("/{id}")
    public Film getFilm(@PathVariable int id) {
        Film film = filmService.getFilm(id);
        if (film == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        } else {
            return film;
        }
    }

    @PutMapping("/{id}/like/{userId}")
    public void addLike(@PathVariable int id, @PathVariable int userId){
        if(filmService.getFilm(id) == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        } else{
            filmService.addLike(id,userId);
        }
    }

    @DeleteMapping("/{id}/like/{userId}")
    public void deleteLike(@PathVariable int id, @PathVariable int userId){
        if(filmService.getFilm(id) == null || userId < 1){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        } else{
            filmService.deleteLike(id,userId);
        }
    }

    @GetMapping("/popular")
    public List<Film> getPopularFilm(@RequestParam(defaultValue = "10") int count){
        return filmService.getPopularFilm(count);
    }
}
