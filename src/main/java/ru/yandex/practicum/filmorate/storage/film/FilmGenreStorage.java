package ru.yandex.practicum.filmorate.storage.film;

import ru.yandex.practicum.filmorate.model.film.Film;
import ru.yandex.practicum.filmorate.model.genre.Genre;

import java.util.List;

interface FilmGenreStorage {

    List<Genre> findFilmGenre(int filmId);

    void createFilmGenre(Film film);

    void updateFilmGenre(Film film);

    boolean deleteFilmGenre(Film film);
}
