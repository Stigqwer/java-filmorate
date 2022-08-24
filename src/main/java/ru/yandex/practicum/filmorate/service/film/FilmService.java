package ru.yandex.practicum.filmorate.service.film;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.film.Film;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FilmService {

    private final FilmStorage filmStorage;
    private static final LocalDate MIN_RELEASE_DATE = LocalDate.parse("1895-12-28");
    private int id = 1;

    @Autowired
    public FilmService(FilmStorage filmStorage) {
        this.filmStorage = filmStorage;
    }

    public List<Film> findAll() {
        return filmStorage.findAll();
    }

    public Film create(Film film) {
        isValidationValues(film);
        film.setId(id++);
        return filmStorage.create(film);
    }

    public Film update(Film film) {
        isValidationValues(film);
        return filmStorage.update(film);
    }

    public Film getFilm(int id) {
        Film film = filmStorage.getFilm(id);
        if (film == null) {
            throw new NotFoundException("Film not found");
        } else {
            return film;
        }
    }

    public void addLike(int id, int userId) {
        Film film = getFilm(id);
        if (film == null) {
            throw new NotFoundException("Film not found");
        } else if (userId < 1) {
            throw new ValidationException("Id cannot be less than 1");
        } else {
            film.getLike().add(userId);
        }
    }

    public void deleteLike(int id, int userId) {
        Film film = getFilm(id);
        if (film == null || userId < 1) {
            throw new NotFoundException("Film not found");
        } else {
            film.getLike().remove(userId);
        }
    }

    public List<Film> getPopularFilm(int count) {
        return filmStorage.findAll().stream()
                .sorted((film1, film2) -> film2.getLike().size() - film1.getLike().size())
                .limit(count)
                .collect(Collectors.toList());
    }

    public boolean isValidationValues(Film film) {
        if (film.getReleaseDate() != null
                && film.getReleaseDate().isBefore(MIN_RELEASE_DATE)) {
            throw new ValidationException("ReleaseDate before 1895-12-28");
        }
        return true;
    }
}
