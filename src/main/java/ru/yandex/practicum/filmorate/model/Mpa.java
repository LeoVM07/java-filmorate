package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class Mpa {

    @NotNull(message = "Необходимо указать id рейтинга")
    private final long id;

    @NotBlank(message = "Необходимо указать возрастную категорию")
    private final String name;
}
