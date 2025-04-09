package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.IdException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {


    private final Map<Integer, User> allUsers = new HashMap<>();
    private int id = 1;

    @GetMapping
    public List<User> showAllUsers() {
        log.trace("Выведен список пользователей");
        return allUsers.values().stream().toList();
    }

    @PostMapping
    public User addUser(@Valid @RequestBody User user) {
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

    @PutMapping
    public User updateUser(@Valid @RequestBody User user) {
        if (allUsers.containsKey(user.getId())) {
            allUsers.replace(user.getId(), user);
            log.info("Обновлены данные пользователя под именем: {}, id: {}", user.getName(), user.getId());
            return user;
        }
        log.error("Некорректный id: {}", user.getId());
        throw new IdException("Пользователь с таким id не найден");
    }

    private int generateId() {
        return id++;
    }

}
