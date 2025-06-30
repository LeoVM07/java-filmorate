package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.Review;

import java.util.List;
import java.util.Optional;

public interface ReviewStorage {
    Review addReview(Review review);

    Review updateReview(Review review);

    void deleteReview(Long id);

    Optional<Review> showReviewById(Long id);

    List<Review> showReviewsByFilmId(Long filmId, int count);

    List<Review> showAllReviews(int count);

    void addLikeToReview(Long reviewId, Long userId);

    void addDislikeToReview(Long reviewId, Long userId);

    void removeLikeOrDislike(Long reviewId, Long userId);
}
