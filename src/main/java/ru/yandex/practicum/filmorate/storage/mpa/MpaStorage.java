package ru.yandex.practicum.filmorate.storage.mpa;

import ru.yandex.practicum.filmorate.model.mpa.Mpa;

import java.util.List;

public interface MpaStorage {

    List<Mpa> findAll();

    Mpa getMpa(int id);
}
