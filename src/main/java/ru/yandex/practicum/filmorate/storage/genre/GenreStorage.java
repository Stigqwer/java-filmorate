package ru.yandex.practicum.filmorate.storage.genre;

import ru.yandex.practicum.filmorate.model.genre.Genre;

import java.util.List;

public interface GenreStorage {

    List<Genre> findAll();

    Genre getGenre(int id);
}
