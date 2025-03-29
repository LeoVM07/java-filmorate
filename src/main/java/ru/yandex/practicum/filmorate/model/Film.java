package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;

@Data
public class Film {

    private Integer id; //значение полю будет задаваться контроллером, остальные поля должны быть заполнены заранее

    @NotBlank(message = "Необходимо указать название фильма")
    private final String name;

    @NotBlank(message = "Необходимо заполнить описание")
    @Size(max = 200)
    private final String description;

    @NotNull(message = "Необходимо указать дату выхода")
    private final LocalDate releaseDate;

    @NotNull(message = "Необходимо указать продолжительность")
    private final long duration;

}
