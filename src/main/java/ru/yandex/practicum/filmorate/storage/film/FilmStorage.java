package ru.yandex.practicum.filmorate.storage.film;

import ru.yandex.practicum.filmorate.model.film.Film;

import java.util.List;


public interface FilmStorage {
    List<Film> findAll();

    Film create(Film film);

    Film update(Film film);

    Film getFilm(int id);

    boolean addLike(int filmId, int userId);

    boolean deleteLike(int filmId, int userId);

    List<Film> getPopularFilm(int count);
}
