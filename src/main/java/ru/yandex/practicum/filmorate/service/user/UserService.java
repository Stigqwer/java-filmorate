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

/*    public void addFriend(int id, int friendId) {
        User user = getUser(id);
        User friendUser = getUser(friendId);
        if (user == null || friendUser == null) {
            throw new NotFoundException("User not found");
        } else {
            user.getFriends().add(friendId);
            friendUser.getFriends().add(id);
        }
    }*/

/*    public void deleteFriend(int id, int friendId) {
        User user = getUser(id);
        User friendUser = getUser(friendId);
        if (user == null || friendUser == null) {
            throw new NotFoundException("User not found");
        } else {
            user.getFriends().remove(id);
            friendUser.getFriends().remove(friendId);
        }
    }*/

 /*   public List<User> getFriends(int id) {
        User user = getUser(id);
        if (user == null) {
            throw new NotFoundException("User not found");
        } else {
            List<User> friends = new ArrayList<>();
            for (Integer idFriends : getUser(id).getFriends()) {
                friends.add(getUser(idFriends));
            }
            return friends;
        }
    }*/

    public List<User> getCommonFriends(int id, int otherId) {
        User user = getUser(id);
        User otherUser = getUser(otherId);
        if (user == null || otherUser == null) {
            throw new NotFoundException("User not found");
        } else {
            List<User> commonFriends = new ArrayList<>();
            for (Integer idFriends : getUser(id).getFriends()) {
                for (Integer otherFriendsId : getUser(otherId).getFriends()) {
                    if (otherFriendsId.equals(idFriends)) {
                        commonFriends.add(getUser(otherFriendsId));
                        break;
                    }
                }
            }
            return commonFriends;
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
