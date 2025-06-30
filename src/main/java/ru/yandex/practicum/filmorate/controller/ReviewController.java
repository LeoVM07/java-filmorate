package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Review;
import ru.yandex.practicum.filmorate.service.ReviewService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/reviews")
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Review addReview(@RequestBody @Valid Review review) {
        return reviewService.addReview(review);
    }

    @PutMapping
    public Review updateReview(@RequestBody @Valid Review review) {
        return reviewService.updateReview(review);
    }

    @DeleteMapping("/{reviewId}")
    public void deleteReview(@PathVariable Long reviewId) {
        reviewService.deleteReview(reviewId);
    }

    @GetMapping("/{reviewId}")
    public Review showReviewById(@PathVariable Long reviewId) {
        return reviewService.showReviewById(reviewId);
    }

    @GetMapping
    public List<Review> showReviews(
            @RequestParam(required = false) Long filmId,
            @RequestParam(defaultValue = "10") int count) {
        return reviewService.showReviews(filmId, count);
    }

    @PutMapping("/{reviewId}/like/{userId}")
    public void addLike(@PathVariable Long reviewId, @PathVariable Long userId) {
        reviewService.addLikeToReview(reviewId, userId);
    }

    @PutMapping("/{reviewId}/dislike/{userId}")
    public void addDislike(@PathVariable Long reviewId, @PathVariable Long userId) {
        reviewService.addDislikeToReview(reviewId, userId);
    }

    @DeleteMapping("/{reviewId}/like/{userId}")
    public void removeLike(@PathVariable Long reviewId, @PathVariable Long userId) {
        reviewService.removeLikeOrDislike(reviewId, userId);
    }

    @DeleteMapping("/{reviewId}/dislike/{userId}")
    public void removeDislike(@PathVariable Long reviewId, @PathVariable Long userId) {
        reviewService.removeLikeOrDislike(reviewId, userId);
    }
}