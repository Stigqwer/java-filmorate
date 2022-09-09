package ru.yandex.practicum.filmorate.model.mpa;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotEmpty;
@Data
@AllArgsConstructor
public class Mpa {
    private int id;
    private String name;
}
