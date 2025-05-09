package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.controller.FilmController;
import ru.yandex.practicum.filmorate.controller.UserController;


@SpringBootTest
class FilmorateApplicationTests {

    @Autowired
    private UserController userController;

    @Autowired
    private FilmController filmController;


    @Test
    void contextLoads() {
        assertNotNull(userController);
        assertNotNull(filmController);
    }

}
