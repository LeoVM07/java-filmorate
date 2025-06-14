package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.FeedRecord;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<User>> showAllUsers() {
        return new ResponseEntity<>(userService.showAllUsers(), HttpStatus.OK);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<User> showUser(@PathVariable("userId") int userId) {
        return new ResponseEntity<>(userService.showUser(userId), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<User> addUser(@Valid @RequestBody User user) {
        return new ResponseEntity<>(userService.addUser(user), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<User> updateUser(@Valid @RequestBody User user) {
        return new ResponseEntity<>(userService.updateUser(user), HttpStatus.OK);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Map<String, String>> deleteUser(@PathVariable("userId") long userId) {
        return new ResponseEntity<>(userService.deleteUser(userId), HttpStatus.OK);
    }

    @PutMapping("/{userId}/friends/{friendId}")
    public ResponseEntity<Map<String, String>> addFriend(@PathVariable("userId") long userId,
                                                         @PathVariable("friendId") long friendId) {
        return new ResponseEntity<>(userService.addFriend(userId, friendId), HttpStatus.OK);
    }

    @DeleteMapping("/{userId}/friends/{friendId}")
    public ResponseEntity<Map<String, String>> deleteFriend(@PathVariable("userId") int userId,
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

    @GetMapping("/{userId}/feed")
    public ResponseEntity<List<FeedRecord>> getFeedByUserId(@PathVariable("userId") int userId) {
        return new ResponseEntity<>(userService.showFeedByUserId(userId), HttpStatus.OK);
    }

}
