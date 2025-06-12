package ru.yandex.practicum.filmorate.dal;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.dal.mappers.ReviewRowMapper;
import ru.yandex.practicum.filmorate.exception.ReviewNotFoundException;
import ru.yandex.practicum.filmorate.model.Review;
import ru.yandex.practicum.filmorate.storage.ReviewStorage;

import java.util.List;
import java.util.Optional;

@Repository
public class ReviewRepository extends BaseRepository<Review> implements ReviewStorage {
    private static final String ADD_REVIEW_QUERY = """
            INSERT INTO reviews (content, is_positive, user_id, film_id)
            VALUES (?, ?, ?, ?)
            """;
    private static final String UPDATE_REVIEW_QUERY = """
            UPDATE reviews
            SET content = ?, is_positive = ?
            WHERE review_id = ?
            """;
    private static final String DELETE_REVIEW_QUERY = "DELETE FROM reviews WHERE review_id = ?";
    private static final String SHOW_REVIEW_BY_ID_QUERY = "SELECT * FROM reviews WHERE review_id = ?";
    private static final String SHOW_REVIEWS_BY_FILM_QUERY = """
            SELECT * FROM reviews
            WHERE film_id = ?
            ORDER BY useful DESC
            LIMIT ?
            """;
    private static final String SHOW_ALL_REVIEWS_QUERY = """
            SELECT * FROM reviews
            ORDER BY useful DESC
            LIMIT ?
            """;
    private static final String ADD_LIKE_QUERY = """
            INSERT INTO review_likes (review_id, user_id, is_like)
            VALUES (?, ?, true)
            """;
    private static final String ADD_DISLIKE_QUERY = """
            INSERT INTO review_likes (review_id, user_id, is_like)
            VALUES (?, ?, false)
            """;
    private static final String GET_LIKE_STATUS_QUERY = """
            SELECT is_like FROM review_likes
            WHERE review_id = ? AND user_id = ?
            """;
    private static final String REMOVE_LIKE_QUERY = """
            DELETE FROM review_likes
            WHERE review_id = ? AND user_id = ?
            """;
    private static final String UPDATE_USEFUL_QUERY = """
            UPDATE reviews
            SET useful = useful + ?
            WHERE review_id = ?
            """;

    private final RowMapper<Review> reviewRowMapper;

    public ReviewRepository(JdbcTemplate jdbc, ReviewRowMapper reviewRowMapper) {
        super(jdbc);
        this.reviewRowMapper = reviewRowMapper;
        this.mapper = reviewRowMapper;
    }

    @Override
    public Review addReview(Review review) {
        Long reviewId = insert(ADD_REVIEW_QUERY,
                review.getContent(), review.getIsPositive(), review.getUserId(), review.getFilmId());
        review.setReviewId(reviewId);
        return review;
    }

    @Override
    public Review updateReview(Review review) {
        Optional<Review> existing = showReviewById(review.getReviewId());
        if (existing.isEmpty()) {
            throw new ReviewNotFoundException(review.getReviewId());
        }

        update(
                UPDATE_REVIEW_QUERY,
                review.getContent(),
                review.getIsPositive(),
                review.getReviewId()
        );
        return showReviewById(review.getReviewId()).orElseThrow();
    }

    @Override
    public void deleteReview(Long reviewId) {
        delete(DELETE_REVIEW_QUERY, reviewId);
    }

    @Override
    public Optional<Review> showReviewById(Long reviewId) {
        return findOne(SHOW_REVIEW_BY_ID_QUERY, reviewId);
    }

    @Override
    public List<Review> showReviewsByFilmId(Long filmId, int count) {
        return findMany(SHOW_REVIEWS_BY_FILM_QUERY, filmId, count);
    }

    public List<Review> showAllReviews(int count) {
        return findMany(SHOW_ALL_REVIEWS_QUERY, count);
    }

    @Override
    public void addLikeToReview(Long reviewId, Long userId) {
        try {
            updateReviewUseful(reviewId, 1);
            insert(ADD_LIKE_QUERY, reviewId, userId);
        } catch (DuplicateKeyException ignored) {
            // Пользователь уже ставил оценку
        }
    }

    @Override
    public void addDislikeToReview(Long reviewId, Long userId) {
        try {
            updateReviewUseful(reviewId, -1);
            insert(ADD_DISLIKE_QUERY, reviewId, userId);
        } catch (DuplicateKeyException ignored) {
            // Пользователь уже ставил оценку
        }
    }

    @Override
    public void removeLikeOrDislike(Long reviewId, Long userId) {
        Boolean isLike = jdbc.queryForObject(
                GET_LIKE_STATUS_QUERY,
                Boolean.class,
                reviewId,
                userId
        );

        if (isLike != null) {
            int usefulDelta = isLike ? -1 : 1;
            updateReviewUseful(reviewId, usefulDelta);
            delete(REMOVE_LIKE_QUERY, reviewId, userId);
        }
    }

    private void updateReviewUseful(Long reviewId, int delta) {
        update(UPDATE_USEFUL_QUERY, delta, reviewId);
    }
}
