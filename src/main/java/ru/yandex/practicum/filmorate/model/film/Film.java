package ru.yandex.practicum.filmorate.model.film;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
public class Film {
    public static final int MAX_SIZE_DESCRIPTION = 200;
    private int id;
    private final Set<Integer> like = new HashSet<>();
    @NotEmpty
    private String name;
    @Size(max = MAX_SIZE_DESCRIPTION)
    private String description;
    private LocalDate releaseDate;
    @Positive
    private int duration;
    private final Set<String> category = new HashSet<>();
    private String rating;
}
