package ru.yandex.practicum.filmorate.service;

import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.enums.EventType;
import ru.yandex.practicum.filmorate.enums.Operation;
import ru.yandex.practicum.filmorate.exception.*;
import ru.yandex.practicum.filmorate.model.FeedRecord;
import ru.yandex.practicum.filmorate.model.Review;
import ru.yandex.practicum.filmorate.storage.FeedRecordStorage;
import ru.yandex.practicum.filmorate.storage.FilmStorage;
import ru.yandex.practicum.filmorate.storage.ReviewStorage;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewStorage reviewStorage;
    private final UserStorage userStorage;
    private final FilmStorage filmStorage;
    private final FeedRecordStorage feedStorage;

    public Review addReview(Review review) {
        checkUserAndFilm(review.getUserId(), review.getFilmId());
        Review createdReview = reviewStorage.addReview(review);
        feedStorage.addFeedRecord(new FeedRecord(
                Timestamp.from(Instant.now()).getTime(),
                review.getUserId(),
                EventType.REVIEW,
                Operation.ADD,
                review.getReviewId()));
        log.info("Добавлен новый отзыв ID: {}", createdReview.getReviewId());
        return createdReview;
    }

    public Review updateReview(Review review) {
        checkReviewExists(review.getReviewId());

        Review updatedReview = reviewStorage.updateReview(review);
        feedStorage.addFeedRecord(new FeedRecord(
                Timestamp.from(Instant.now()).getTime(),
                review.getUserId(),
                EventType.REVIEW,
                Operation.UPDATE,
                review.getReviewId()));
        log.info("Обновлен отзыв ID: {}", updatedReview.getReviewId());
        return updatedReview;
    }

    public void deleteReview(Long reviewId) {
        checkReviewExists(reviewId);
        Review review = showReviewById(reviewId);
        reviewStorage.deleteReview(reviewId);
        feedStorage.addFeedRecord(new FeedRecord(
                Timestamp.from(Instant.now()).getTime(),
                review.getUserId(),
                EventType.REVIEW,
                Operation.REMOVE,
                reviewId));
        log.info("Удален отзыв ID: {}", reviewId);
    }

    public Review showReviewById(Long reviewId) {
        Review review = reviewStorage.showReviewById(reviewId)
                .orElseThrow(() -> new ReviewNotFoundException(reviewId));
        log.debug("Получен отзыв ID: {}", reviewId);
        return review;
    }

    public List<Review> showReviews(Long filmId, int count) {
        if (count <= 0) {
            throw new IllegalArgumentException("Count must be positive");
        }
        if (filmId != null && filmId <= 0) {
            throw new ValidationException("Film ID must be positive");
        }

        if (filmId == null) {
            return reviewStorage.showAllReviews(count); // <- без filmId
        } else {
            return reviewStorage.showReviewsByFilmId(filmId, count);
        }

    }

    public void addLikeToReview(Long reviewId, Long userId) {
        checkReviewAndUser(reviewId, userId);

        try {
            reviewStorage.addLikeToReview(reviewId, userId);
            log.info("Пользователь ID: {} поставил лайк отзыву ID: {}", userId, reviewId);
        } catch (DuplicateKeyException e) {
            throw new DuplicateReviewLikeException(reviewId, userId);
        }
    }

    public void addDislikeToReview(Long reviewId, Long userId) {
        checkReviewAndUser(reviewId, userId);

        try {
            reviewStorage.addDislikeToReview(reviewId, userId);
            log.info("Пользователь ID: {} поставил дизлайк отзыву ID: {}", userId, reviewId);
        } catch (DuplicateKeyException e) {
            throw new DuplicateReviewLikeException(reviewId, userId);
        }
    }

    public void removeLikeOrDislike(Long reviewId, Long userId) {
        checkReviewAndUser(reviewId, userId);
        reviewStorage.removeLikeOrDislike(reviewId, userId);
        log.info("Пользователь ID: {} удалил оценку отзыва ID: {}", userId, reviewId);
    }

    private void checkReviewExists(Long reviewId) {
        if (!reviewStorage.showReviewById(reviewId).isPresent()) {
            throw new ReviewNotFoundException(reviewId);
        }
    }

    private void checkUserAndFilm(Long userId, Long filmId) {
        if (filmId == null || userId == null) {
            throw new ValidationException();
        }
        if (filmId <= 0) {
            throw new FilmIdException(filmId);
        }
        if (userId <= 0) {
            throw new UserIdException(userId);
        }

        userStorage.showUser(userId)
                .orElseThrow(() -> new UserIdException(userId));
        filmStorage.showFilm(filmId)
                .orElseThrow(() -> new FilmIdException(filmId));
    }

    private void checkReviewAndUser(Long reviewId, Long userId) {
        checkReviewExists(reviewId);
        userStorage.showUser(userId)
                .orElseThrow(() -> new UserIdException(userId));
    }
}
