package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import javax.validation.Valid;

@RestController
@Slf4j
@RequestMapping("/users")
public class UserController extends Controller<User> {

    @PostMapping
    @Override
    public User create(@Valid @RequestBody User user) {
        super.create(user);
        user.setId(id++);
        data.put(user.getId(), user);
        log.info("User created");
        return user;
    }

    @PutMapping
    @Override
    public User update(@Valid @RequestBody User user) {
        super.create(user);
        if (data.containsKey(user.getId())) {
            data.put(user.getId(), user);
            log.info("User updated");
            return user;
        } else {
            throw new ValidationException("Not have this id on server");
        }
    }

    @Override
    boolean isValidationValues(User user) {
        if (user.getLogin().contains(" ")) {
            throw new ValidationException("Login must be didn't have a blank char");
        }
        if (user.getName() == null || user.getName().isBlank()) {
            log.info("Name isEmpty. Now Name = Login");
            user.setName(user.getLogin());
        }
        return true;
    }
}
