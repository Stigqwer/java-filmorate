package ru.yandex.practicum.filmorate.model.film;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.yandex.practicum.filmorate.model.genre.Genre;
import ru.yandex.practicum.filmorate.model.mpa.Mpa;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.Set;

@Data
@AllArgsConstructor
public class Film {
    public static final int MAX_SIZE_DESCRIPTION = 200;
    private int id;
    @NotEmpty
    private String name;
    @Size(max = MAX_SIZE_DESCRIPTION)
    private String description;
    private LocalDate releaseDate;
    @Positive
    private int duration;
    private Mpa mpa;
    private final Set<Genre> genres;
}
