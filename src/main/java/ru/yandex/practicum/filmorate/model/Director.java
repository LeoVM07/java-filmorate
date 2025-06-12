package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class Director {
    private long id;
    @NotBlank
    private String name;
}
