package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;
import ru.yandex.practicum.filmorate.storage.InMemoryUserStorage;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {

    private final InMemoryUserStorage userStorage;
    private final UserService userService;

    @Autowired
    public UserController(InMemoryUserStorage userStorage, UserService userService) {
        this.userStorage = userStorage;
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<User>> showAllUsers() {
        return new ResponseEntity<>(userStorage.showAllUsers(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<User> addUser(@Valid @RequestBody User user) {
        return new ResponseEntity<>(userStorage.addUser(user), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<User> updateUser(@Valid @RequestBody User user) {
        return new ResponseEntity<>(userStorage.updateUser(user), HttpStatus.OK);
    }

    @PutMapping("/{userId}/friends/{friendId}")
    public ResponseEntity<List<User>> addFriend(@PathVariable("userId") int userId,
                                                @PathVariable("friendId") int friendId) {
        return new ResponseEntity<>(userService.addFriend(userId, friendId), HttpStatus.OK);
    }

    @DeleteMapping("/{userId}/friends/{friendId}")
    public ResponseEntity<User> deleteFriend(@PathVariable("userId") int userId,
                                             @PathVariable("friendId") int friendId) {
        return new ResponseEntity<>(userService.deleteFriend(userId, friendId), HttpStatus.OK);
    }

    @GetMapping("/{userId}/friends")
    public ResponseEntity<List<User>> getAllUsersFriends(@PathVariable("userId") int userId) {
        return new ResponseEntity<>(userService.getAllUserFriends(userId), HttpStatus.OK);
    }

    @GetMapping("/{userId}/friends/common/{friendId}")
    public ResponseEntity<List<User>> getCommonFriends(@PathVariable("userId") int userId,
                                       @PathVariable("friendId") int friendId) {
        return new ResponseEntity<>(userService.getCommonFriends(userId, friendId), HttpStatus.OK);
    }

}
