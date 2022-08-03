package ru.yandex.practicum.filmorate.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public abstract class Controller<T> {
    protected final Map<Integer, T> data = new HashMap<>();

    protected int id = 1;

    @GetMapping
    public List<T> findAll() {
        return new ArrayList<>(data.values());
    }

    public T create(T t) {
        isValidationValues(t);
        return t;
    }

    public T update(T t) {
        isValidationValues(t);
        return t;
    }

    abstract boolean isValidationValues(T t);
}
