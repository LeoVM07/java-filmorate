package ru.yandex.practicum.filmorate.dal;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.dal.mappers.UserRowMapper;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.List;
import java.util.Optional;

@Repository
public class UserRepository extends BaseRepository<User> implements UserStorage {

    private static final String SHOW_ALL_USERS_QUERY = "SELECT * FROM users";
    private static final String SHOW_USER_BY_ID_QUERY = "SELECT * FROM users WHERE user_id = ?";
    private static final String ADD_USER_QUERY = "INSERT INTO users(email, login, name, birthday)" +
            "VALUES(?, ?, ?, ?)";
    private static final String UPDATE_USER_QUERY = "UPDATE users SET email = ?, login = ?, name = ?, " +
            "birthday = ? WHERE user_id = ?";
    private static final String ADD_FRIEND_TO_USER_QUERY = "INSERT INTO friendship (user_id, friend_id) VALUES(?, ?)";
    private static final String DELETE_FRIEND_FROM_USER_QUERY =
            "DELETE friendship WHERE user_id = ? AND friend_id =?";
    private static final String SHOW_ALL_USER_FRIENDS_QUERY = """
            SELECT users.*
            FROM users
            LEFT JOIN friendship ON users.user_id = friendship.friend_id
            WHERE friendship.user_id = ?;
            """;
    private static final String SHOW_COMMON_FRIEND_QUERY = """
            SELECT users.*
            FROM friendship f1
            LEFT JOIN friendship f2 ON f1.friend_id = f2.friend_id
            LEFT JOIN users ON f1.friend_id = users.user_id
            WHERE f1.user_id = ? AND f2.user_id = ?;
            """;


    public UserRepository(JdbcTemplate jdbc, UserRowMapper mapper) {
        super(jdbc);
        this.mapper = mapper;
    }

    @Override
    public List<User> showAllUsers() {
        return findMany(SHOW_ALL_USERS_QUERY);
    }

    @Override
    public Optional<User> showUser(long userId) {
        return findOne(SHOW_USER_BY_ID_QUERY, userId);
    }

    @Override
    public User addUser(User user) {
        long userId = insert(ADD_USER_QUERY,
                user.getEmail(),
                user.getLogin(),
                user.getName(),
                user.getBirthday());

        userNameCheck(user);
        user.setId(userId);
        return user;
    }

    @Override
    public User updateUser(User user) {
        userNameCheck(user);
        update(
                UPDATE_USER_QUERY,
                user.getEmail(),
                user.getLogin(),
                user.getName(),
                user.getBirthday(),
                user.getId()
        );

        return user;
    }

    @Override
    public void addFriendToUser(long userId, long friendId) {
        insert(ADD_FRIEND_TO_USER_QUERY, userId, friendId);
    }

    @Override
    public void deleteFriendFromUser(long userId, long friendId) {
        delete(DELETE_FRIEND_FROM_USER_QUERY, userId, friendId);
    }

    @Override
    public List<User> showAllUserFriends(long userId) {
        return findMany(SHOW_ALL_USER_FRIENDS_QUERY, userId);
    }

    @Override
    public List<User> showCommonFriends(long userId, long friendId) {
        return findMany(SHOW_COMMON_FRIEND_QUERY, userId, friendId);
    }

    private void userNameCheck(User user) { //проверяется наличие настоящего имени пользователя
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
    }
}
