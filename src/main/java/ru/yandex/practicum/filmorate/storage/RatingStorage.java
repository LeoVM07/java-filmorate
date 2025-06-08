package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.Mpa;

import java.util.List;
import java.util.Optional;

public interface RatingStorage {

    public List<Mpa> showALlRatings();

    public Optional<Mpa> showRatingById(long ratingId);
}
