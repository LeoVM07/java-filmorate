package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dal.UserRepository;
import ru.yandex.practicum.filmorate.exception.DuplicateUserIdException;
import ru.yandex.practicum.filmorate.exception.UserIdException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository repository;

    public List<User> showAllUsers() {
        return repository.showAllUsers();
    }

    public User showUser(int userId) {
        return checkUser(userId);
    }

    public User addUser(User user) {
        return repository.addUser(user);
    }

    public User updateUser(User user) {
        checkUser(user.getId());
        return repository.updateUser(user);
    }

    public Map<String, String> addFriend(long userId, long friendId) {
        checkUser(userId);
        checkUser(friendId);
        if (userId == friendId) {
            throw new DuplicateUserIdException();
        }

        log.info("Пополнение списка друзей пользователей с id: {}, {}", userId, friendId);
        repository.addFriendToUser(userId, friendId);
        return Map.of("result", String.format("user with id %d was added as friend", friendId));
    }

    public Map<String, String> deleteFriend(long userId, long friendId) {
        checkUser(userId);
        checkUser(friendId);

        log.info("Удаление из списка друзей пользователей с id: {}, {}", userId, friendId);
        repository.deleteFriendFromUser(userId, friendId);
        return Map.of("result", String.format("user with id %d was removed from friend list", friendId));
    }

    public List<User> getAllUserFriends(long userId) {
        checkUser(userId);
        log.trace("Выведен список друзей пользователя с id:{}", userId);
        return repository.showAllUserFriends(userId);
    }


    public List<User> getCommonFriends(long userId, long friendId) {
        checkUser(userId);
        checkUser(friendId);
        log.trace("Выведен список общих друзей пользователей с id:{} и {}", userId, friendId);
        return repository.showCommonFriends(userId, friendId);
    }

    private User checkUser(long userId) {
        return repository.showUser(userId)
                .stream()
                .findAny()
                .orElseThrow(() -> new UserIdException(userId));
    }
}
