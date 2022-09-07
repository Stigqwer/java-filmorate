package ru.yandex.practicum.filmorate.model.like;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Like {
    private int id;
    private int filmId;
    private int userId;
}
