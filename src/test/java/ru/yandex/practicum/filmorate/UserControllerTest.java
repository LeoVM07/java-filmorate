package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.controller.UserController;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserControllerTest {

    UserController userController;

    @BeforeEach
    void createUserController() {
        this.userController = new UserController();
    }

    @Test
    void usersShouldBeEqual() {
        User user1 = new User("testemail@yandex.ru", "Test_Login", "TestName",
                LocalDate.of(1999, 11, 8));
        User user2 = userController.addUser(user1);
        assertEquals(user1, user2, "Пользователи должны быть одинаковыми");
    }

    @Test
    void userListShouldBeEqual() {
        User user1 = new User("testemail@yandex.ru", "Test_Login", "TestName",
                LocalDate.of(1999, 11, 8));
        User user2 = new User("anotheremail@yandex.ru", "Another_Login", "TestName",
                LocalDate.of(1999, 11, 8));
        List<User> userList1 = List.of(user1, user2);

        userController.addUser(user1);
        userController.addUser(user2);
        List<User> userList2 = userController.showAllUsers();

        assertEquals(userList1, userList2, "Списки пользователей должны быть одинаковыми");
    }

    @Test
    void userNameShouldBeEqualUserLogin() {
        User user = new User("testemail@yandex.ru", "Test_Login",
                LocalDate.of(1999, 11, 8));
        assertEquals(user.getName(), user.getLogin(), "Имя и логин пользователя должны совпадать");
    }
}
