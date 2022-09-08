package ru.yandex.practicum.filmorate.storage.film;

import ru.yandex.practicum.filmorate.model.film.Film;
import ru.yandex.practicum.filmorate.model.genre.Genre;

import java.util.Set;

public interface FilmGenreStorage {

    Set<Genre> findFilmGenre(int filmId);

    void createFilmGenre(Film film);

    void updateFilmGenre(Film film);
}
