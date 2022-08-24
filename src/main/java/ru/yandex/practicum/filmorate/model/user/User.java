package ru.yandex.practicum.filmorate.model.user;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Past;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
public class User {
    private int id;
    private final Set<Integer> friends = new HashSet<>();
    @Email
    @NotEmpty
    private String email;
    @NotEmpty
    private String login;
    private String name;
    @Past
    private LocalDate birthday;
}
