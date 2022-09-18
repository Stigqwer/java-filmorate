package ru.yandex.practicum.filmorate.storage.user;

import ru.yandex.practicum.filmorate.model.user.User;

import java.util.List;

public interface UserStorage {
    List<User> findAll();

    User create(User user);

    User update(User user);

    User getUser(int id);

    List<User> getFriends(int id);

    boolean addFriend(int id, int friendId);

    boolean deleteFriend(int id, int friendId);

}
