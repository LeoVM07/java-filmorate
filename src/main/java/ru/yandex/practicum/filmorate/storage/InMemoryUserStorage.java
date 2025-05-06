package ru.yandex.practicum.filmorate.storage;

import jakarta.validation.Valid;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.UserIdException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class InMemoryUserStorage implements UserStorage {

    @Getter
    private final Map<Integer, User> allUsers = new HashMap<>();
    private int id = 1;

    @Override
    public List<User> showAllUsers() {
        log.trace("Выведен список пользователей");
        return allUsers.values().stream().toList();
    }

    @Override
    public User showUser(@Valid int userId) {
        if (allUsers.containsKey(userId)) {
            log.trace("Пользователь с id: {} найден", userId);
            return allUsers.get(userId);
        } else {
            log.error("Некорректный id: {}", userId);
            throw new UserIdException(userId);
        }
    }

    @Override
    public User addUser(@Valid User user) {
        user.setId(generateId());
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
            log.info("Пользователь не указал имя, для заполнения поля был использован его логин: {}",
                    user.getLogin());
        }
        allUsers.put(user.getId(), user);
        log.info("Добавлен новый пользователь под именем: {}, id: {}", user.getName(), user.getId());
        return user;
    }

    @Override
    public User updateUser(@Valid User user) {
        if (allUsers.containsKey(user.getId())) {
            allUsers.replace(user.getId(), user);
            log.info("Обновлены данные пользователя под именем: {}, id: {}", user.getName(), user.getId());
            return user;
        }
        log.error("Некорректный id: {}", user.getId());
        throw new UserIdException(user.getId());
    }

    private int generateId() {
        return id++;
    }
}
