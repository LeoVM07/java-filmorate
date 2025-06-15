package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class Review {
    private Long reviewId;
    @NotBlank(message = "Текст отзыва не может быть пустым")
    private String content;
    @NotNull(message = "Тип отзыва (положительный/отрицательный) должен быть указан")
    private Boolean isPositive;
    @NotNull(message = "ID пользователя не может быть null")
    private Long userId;
    @NotNull(message = "ID фильма не может быть null")
    private Long filmId;
    private int useful;
}
