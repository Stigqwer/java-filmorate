package ru.yandex.practicum.filmorate.model.friend;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.yandex.practicum.filmorate.model.user.User;

import java.util.Set;
@Data
@AllArgsConstructor
public class Friend {
   private final Set<User> friends;
}
