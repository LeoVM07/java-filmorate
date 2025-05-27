package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dal.MpaRepository;
import ru.yandex.practicum.filmorate.exception.MpaIdException;
import ru.yandex.practicum.filmorate.model.Mpa;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class MpaService {

    private final MpaRepository repository;

    public List<Mpa> showAllRatings() {
        return repository.showALlRatings();
    }

    public Mpa showRatingById(long ratingId) {
        return checkMpa(ratingId);
    }

    private Mpa checkMpa(long ratingId) {
        return repository.showRatingById(ratingId)
                .stream()
                .findAny()
                .orElseThrow(() -> new MpaIdException(ratingId));
    }
}
