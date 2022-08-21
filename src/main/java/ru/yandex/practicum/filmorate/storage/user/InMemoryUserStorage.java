package ru.yandex.practicum.filmorate.storage.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class InMemoryUserStorage implements UserStorage {
    private final Map<Integer, User> users = new HashMap<>();
    private int id = 1;

    @Override
    public List<User> findAll() {
        return new ArrayList<>(users.values());
    }

    @Override
    public User create(User user) {
        isValidationValues(user);
        user.setId(id++);
        users.put(user.getId(), user);
        log.info("film created");
        return user;
    }

    @Override
    public User update(User user) {
        isValidationValues(user);
        if (users.containsKey(user.getId())) {
            users.put(user.getId(), user);
            log.info("User updated");
            return user;
        } else {
            throw new ValidationException("Not have this id on server");
        }
    }

    public boolean isValidationValues(User user) {
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
