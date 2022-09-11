package ru.yandex.practicum.filmorate.service.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.user.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class UserService {
    private final UserStorage userStorage;
    private int id = 1;

    @Autowired
    public UserService(@Qualifier("userDbStorage") UserStorage userStorage) {
        this.userStorage = userStorage;
    }

    public List<User> findAll() {
        return userStorage.findAll();
    }

    public User create(User user) {
        isValidationValues(user);
        user.setId(id++);
        return userStorage.create(user);
    }


    public User update(User user) {
        isValidationValues(user);
        return userStorage.update(user);
    }

    public User getUser(int id) {
        return userStorage.getUser(id);
    }

    public void addFriend(int id, int friendId) {
        userStorage.addFriend(id,friendId);
    }

    public void deleteFriend(int id, int friendId) {
        userStorage.deleteFriend(id,friendId);
    }

    public List<User> getFriends(int id) {
        return userStorage.getFriends(id);
    }


    public List<User> getCommonFriends(int id, int otherId) {
        List<User> commonFriends = new ArrayList<>();
        for (User friend : getFriends(id)) {
            for (User otherFriend : getFriends(otherId)) {
                if (otherFriend.equals(friend)) {
                    commonFriends.add(otherFriend);
                    break;
                }
            }
        }
        return commonFriends;
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
