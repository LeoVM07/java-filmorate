package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.InMemoryFilmStorage;
import ru.yandex.practicum.filmorate.storage.InMemoryUserStorage;

import java.util.*;

@Slf4j
@Service
public class UserService extends BasicService {

    public UserService(InMemoryUserStorage userStorage, InMemoryFilmStorage filmStorage) {
        super(userStorage, filmStorage);
    }

    public List<User> showAllUsers() {
        return getUserStorage().showAllUsers();
    }

    public User showUser(int userId) {
        return getUserStorage().showUser(userId);
    }

    public User addUser(User user) {
        return getUserStorage().addUser(user);
    }

    public User updateUser(User user) {
        return getUserStorage().updateUser(user);
    }

    public List<User> addFriend(int userId, int friendId) {
        User user = checkUser(userId);
        User friend = checkUser(friendId);

        user.getFriendsIds().add(friendId);
        friend.getFriendsIds().add(userId);

        log.info("Пополнение списка друзей пользователей с id: {}, {}", userId, friendId);
        return List.of(user, friend);
    }

    public User deleteFriend(int userId, int friendId) {
        User user = checkUser(userId);
        User friend = checkUser(friendId);

        user.getFriendsIds().remove(friendId);
        friend.getFriendsIds().remove(userId);

        log.info("Удаление из списка друзей пользователей с id: {}, {}", userId, friendId);
        return friend;
    }

    public List<User> getAllUserFriends(int userId) {
        User user = checkUser(userId);
        Set<Integer> userFriends = user.getFriendsIds();
        log.trace("Выведен список друзей пользователя с id:{}", userId);
        return getUserStorage()
                .getAllUsers()
                .values()
                .stream()
                .filter(u -> userFriends.contains(u.getId()))
                .toList();
    }

    public List<User> getCommonFriends(int userId, int friendId) {
        checkUser(userId);
        checkUser(friendId);
        log.trace("Выведен список общих друзей пользователей с id:{} и {}", userId, friendId);
        return getUserStorage()
                .getAllUsers()
                .values()
                .stream()
                .filter(user -> user.getFriendsIds().contains(userId) && user.getFriendsIds().contains(friendId))
                .toList();
    }
}
