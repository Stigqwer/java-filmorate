package ru.yandex.practicum.filmorate.controller;

import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/users")
public class UserController {

    private final Map<Integer, User> users = new HashMap<>();

    @GetMapping
    public List<User> findAll() {
        return new ArrayList<>(users.values());
    }

    @PostMapping
    public User create(@RequestBody User user) {
        user.setId(users.size()+1);
        users.put(user.getId(),user);
        return user;
    }

    @PutMapping
    public User update(@RequestBody User user) {
        if(users.containsKey(user.getId())){
            users.put(user.getId(),user);
            return user;
        } else {
            throw new ValidationException("Передан не верный id");
        }
    }
}
