package ru.yandex.practicum.filmorate.service.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    private final UserStorage userStorage;

    @Autowired
    public UserService(UserStorage userStorage) {
        this.userStorage = userStorage;
    }

    public List<User> findAll() {
        return userStorage.findAll();
    }

    public User create(User user) {
        return userStorage.create(user);
    }


    public User update(User user) {
        return userStorage.update(user);
    }

    public User getUser(int id){
        return userStorage.getUser(id);
    }

    public void addFriend(int id, int friendId){
        getUser(id).getFriends().add(friendId);
        getUser(friendId).getFriends().add(id);
    }

    public void deleteFriend(int id, int friendId){
        getUser(id).getFriends().remove(id);
        getUser(friendId).getFriends().remove(friendId);
    }

    public List<User> getFriends(int id){
        List<User> friends = new ArrayList<>();
        for(Integer idFriends:getUser(id).getFriends()){
            friends.add(getUser(idFriends));
        }
        return friends;
    }

    public List<User> getCommonFriends(int id, int otherId){
        List<User> commonFriends = new ArrayList<>();
        for(Integer idFriends: getUser(id).getFriends()){
            for(Integer otherFriendsId: getUser(otherId).getFriends()){
                if(otherFriendsId.equals(idFriends)){
                    commonFriends.add(getUser(otherFriendsId));
                    break;
                }
            }
        }
        return commonFriends;
    }
}
