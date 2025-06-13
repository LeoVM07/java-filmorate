package ru.yandex.practicum.filmorate.exception;

public class DuplicateReviewLikeException extends RuntimeException {
    public DuplicateReviewLikeException(long reviewId, long userId) {
        super(String.format("Пользователь %d уже оценивал отзыв %d", userId, reviewId));
    }
}
