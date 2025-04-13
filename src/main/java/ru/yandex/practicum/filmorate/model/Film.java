package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.*;
import lombok.Data;
import ru.yandex.practicum.filmorate.validator.ReleaseDate;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
public class Film {

    private Integer id; //значение полю будет задаваться контроллером, остальные поля должны быть заполнены заранее

    private Set<Integer> likes = new HashSet<>();

    @NotBlank(message = "Необходимо указать название фильма")
    private final String name;

    @NotBlank(message = "Необходимо заполнить описание")
    @Size(max = 200)
    private final String description;

    @NotNull(message = "Необходимо указать дату выхода")
    @ReleaseDate
    private final LocalDate releaseDate;

    @NotNull(message = "Необходимо указать продолжительность")
    @Positive(message = "Продолжительность фильма должна быть положительным числом")
    private final long duration;

}
