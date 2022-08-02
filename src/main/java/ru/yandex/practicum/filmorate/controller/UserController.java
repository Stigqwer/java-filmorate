package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Slf4j
@RequestMapping("/users")
public class UserController {

    private final Map<Integer, User> users = new HashMap<>();

    @GetMapping
    public List<User> findAll() {
        return new ArrayList<>(users.values());
    }

    @PostMapping
    public User create(@Valid @RequestBody User user) {
        isValidationValues(user);
        user.setId(users.size()+1);
        users.put(user.getId(),user);
        log.info("User успешно добавлен");
        return user;
    }

    @PutMapping
    public User update(@Valid @RequestBody User user) {
        isValidationValues(user);
        if(users.containsKey(user.getId())){
            users.put(user.getId(),user);
            log.info("User успешно обновлен");
            return user;
        } else {
            log.error("Передан не верный id");
            throw new ValidationException("Передан не верный id");
        }
    }

    boolean isValidationValues(User user){
        if(user.getLogin().contains(" ")){
            throw new ValidationException("Логин не может содержать пробелы");
        }
        if(user.getName() == null || user.getName().isBlank()){
            log.info("Имя не задано. Присвоено имя в соответствии с логином");
            user.setName(user.getLogin());
        }
        return true;
    }
}
