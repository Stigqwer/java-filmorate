package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.user.UserService;

import javax.validation.Valid;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<User> findAll() {
        return userService.findAll();
    }

    @PostMapping
    public User create(@Valid @RequestBody User user) {
        return userService.create(user);
    }

    @PutMapping
    public User update(@Valid @RequestBody User user) {
        if(user.getId() < 1){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return userService.update(user);
    }

    @GetMapping("/{id}")
    public User getUser(@PathVariable int id) {
        User user = userService.getUser(id);
        if (user == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        } else {
            return user;
        }
    }

    @PutMapping("/{id}/friends/{friendId}")
    public void addFriends(@PathVariable int id, @PathVariable int friendId){
        if(userService.getUser(id) == null || userService.getUser(friendId) == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        } else{
            userService.addFriend(id,friendId);
        }
    }

    @DeleteMapping("/{id}/friends/{friendId}")
    public void deleteFriends(@PathVariable int id, @PathVariable int friendId){
        if(userService.getUser(id) == null || userService.getUser(friendId) == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        } else{
            userService.deleteFriend(id,friendId);
        }
    }

    @GetMapping("/{id}/friends")
    public List<User> getFriends(@PathVariable int id){
        if(userService.getUser(id) == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        } else{
           return userService.getFriends(id);
        }
    }

    @GetMapping("/{id}/friends/common/{otherId}")
    public List<User> getCommonFriends(@PathVariable int id, @PathVariable int otherId){
        if(userService.getUser(id) == null || userService.getUser(otherId) == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        } else {
            return userService.getCommonFriends(id,otherId);
        }
    }
}
