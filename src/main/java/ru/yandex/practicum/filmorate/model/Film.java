package ru.yandex.practicum.filmorate.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Data
@AllArgsConstructor
public class Film {
    public final static int MAX_SIZE_DESCRIPTION = 200;
    private int id;
    @NotEmpty
    private String name;
    @Size(max = MAX_SIZE_DESCRIPTION)
    private String description;
    private LocalDate releaseDate;
    @Positive
    private int duration;
}
