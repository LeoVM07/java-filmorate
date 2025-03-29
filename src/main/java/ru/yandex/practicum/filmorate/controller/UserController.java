package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.FieldValidationException;
import ru.yandex.practicum.filmorate.exception.IdException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.Year;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {


    private final Map<Integer, User> allUsers = new HashMap<>();

    //Хотел назвать эту константу по правилам (капсом), но чекстайл не пропустил =D
    private final String incorrectUserId = "Пользователь с таким id не найден";

    @GetMapping
    public List<User> showAllUsers() {
        log.trace("Выведен список пользователей");
        return allUsers.values().stream().toList();
    }

    @PostMapping
    public User addUser(@Valid @RequestBody User user) {
        validateUser(user);
        user.setId(IDGenerator.getNextId(allUsers));
        allUsers.put(user.getId(), user);
        log.debug("Добавлен новый пользователь под именем: {}, id: {}", user.getName(), user.getId());
        return user;
    }

    @PutMapping
    public User updateUser(@Valid @RequestBody User user) {
        if (allUsers.containsKey(user.getId())) {
            validateUser(user);
            allUsers.replace(user.getId(), user);
            log.debug("Обновлены данные пользователя под именем: {}, id: {}", user.getName(), user.getId());
            return user;
        }
        log.info("Некорректный id: {}", user.getId());
        throw new IdException(incorrectUserId);
    }

    private void validateUser(User user) {
        int currentYear = Year.now().getValue();
        if (user.getLogin().contains(" ")) {
            log.info("Некорректный логин: {}", user.getLogin());
            throw new FieldValidationException("Логин не должен содержать пробелы");
        }
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
            log.debug("Пользователь не указал имя, для заполнения поля был использован его логин: {}",
                    user.getLogin());
        }
        if (user.getBirthday().getYear() > currentYear) {
            log.info("Недопустимая дата рождения: {}", user.getBirthday().getYear());
            throw new FieldValidationException("Год рождения не может быть позднее текущего года");
        }
    }
}
