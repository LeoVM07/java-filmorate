package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dal.FeedRecordRepository;
import ru.yandex.practicum.filmorate.dal.UserRepository;
import ru.yandex.practicum.filmorate.enums.EventType;
import ru.yandex.practicum.filmorate.enums.Operation;
import ru.yandex.practicum.filmorate.exception.DuplicateUserIdException;
import ru.yandex.practicum.filmorate.exception.UserIdException;
import ru.yandex.practicum.filmorate.model.FeedRecord;
import ru.yandex.practicum.filmorate.model.User;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final FeedRecordRepository feedRecordRepository;

    public List<User> showAllUsers() {
        return userRepository.showAllUsers();
    }

    public User showUser(int userId) {
        return checkUser(userId);
    }

    public User addUser(User user) {
        return userRepository.addUser(user);
    }

    public User updateUser(User user) {
        checkUser(user.getId());
        return userRepository.updateUser(user);
    }

    public Map<String, String> deleteUser(long userId) {
        checkUser(userId);
        userRepository.deleteUser(userId);
        log.info("Пользователь с id {} был удалён из базы данных", userId);
        return Map.of("result", String.format("user with id %d was deleted", userId));
    }

    public Map<String, String> addFriend(long userId, long friendId) {
        checkUser(userId);
        checkUser(friendId);
        if (userId == friendId) {
            throw new DuplicateUserIdException();
        }

        log.info("Пополнение списка друзей пользователей с id: {}, {}", userId, friendId);
        userRepository.addFriendToUser(userId, friendId);
        feedRecordRepository.addFeedRecord(new FeedRecord(
                Timestamp.from(Instant.now()).getTime(),
                userId,
                EventType.FRIEND,
                Operation.ADD,
                friendId));
        return Map.of("result", String.format("user with id %d was added as friend", friendId));
    }

    public Map<String, String> deleteFriend(long userId, long friendId) {
        checkUser(userId);
        checkUser(friendId);

        log.info("Удаление из списка друзей пользователей с id: {}, {}", userId, friendId);
        userRepository.deleteFriendFromUser(userId, friendId);
        feedRecordRepository.addFeedRecord(new FeedRecord(
                Timestamp.from(Instant.now()).getTime(),
                userId,
                EventType.FRIEND,
                Operation.REMOVE,
                friendId));
        return Map.of("result", String.format("user with id %d was removed from friend list", friendId));
    }

    public List<User> getAllUserFriends(long userId) {
        checkUser(userId);
        log.trace("Выведен список друзей пользователя с id:{}", userId);
        return userRepository.showAllUserFriends(userId);
    }


    public List<User> getCommonFriends(long userId, long friendId) {
        checkUser(userId);
        checkUser(friendId);
        log.trace("Выведен список общих друзей пользователей с id:{} и {}", userId, friendId);
        return userRepository.showCommonFriends(userId, friendId);
    }

    public List<FeedRecord> showFeedByUserId(long userId) {
        log.trace("Выведена лента событий пользователя с id {}", userId);
        return feedRecordRepository.showFeedByUserId(userId);
    }

    private User checkUser(long userId) {
        return userRepository.showUser(userId)
                .stream()
                .findAny()
                .orElseThrow(() -> new UserIdException(userId));
    }
}
