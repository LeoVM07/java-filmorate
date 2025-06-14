package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.User;

import java.util.List;
import java.util.Optional;

public interface UserStorage {

    List<User> showAllUsers();

    Optional<User> showUser(long userId);

    User addUser(User user);

    User updateUser(User user);

    void deleteUser(long userId);

    void addFriendToUser(long userId, long friendId);

    void deleteFriendFromUser(long userId, long friendId);

    List<User> showAllUserFriends(long userId);

    List<User> showCommonFriends(long userId, long friendId);
}
