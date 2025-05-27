package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class Genre {

    @NotNull(message = "Необходимо указать id жанра")
    private final long id;

    @NotBlank(message = "Необходимо указать название жанра")
    private final String name;
}
