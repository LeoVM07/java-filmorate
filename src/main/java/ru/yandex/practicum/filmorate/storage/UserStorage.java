package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.User;

import java.util.List;
import java.util.Optional;

public interface UserStorage {

    public List<User> showAllUsers();

    public Optional<User> showUser(long userId);

    public User addUser(User user);

    public User updateUser(User user);

    public void deleteUser(long userId);

    public void addFriendToUser(long userId, long friendId);

    public void deleteFriendFromUser(long userId, long friendId);

    public List<User> showAllUserFriends(long userId);

    public List<User> showCommonFriends(long userId, long friendId);
}
