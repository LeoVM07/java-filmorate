package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.yandex.practicum.filmorate.dal.UserRepository;
import ru.yandex.practicum.filmorate.dal.mappers.UserRowMapper;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.Optional;

@JdbcTest
@AutoConfigureTestDatabase
@Import({UserRepository.class, UserRowMapper.class})
public class UserStorageTest {

    @Autowired
    private UserRepository storage;
    private final User user = new User("Test_User", "Test_Login", "test@gmail.com", LocalDate.of(2000, 1, 1));

    @Test
    public void createUser() {
        User created = storage.addUser(user);

        Assertions.assertNotNull(created.getId());
        Assertions.assertEquals(1, storage.showAllUsers().size());
    }

    @Test
    public void getUser() {
        User created = storage.addUser(user);
        User found = storage.showUser(created.getId()).get();

        Assertions.assertEquals(user.getName(), found.getName());
        Assertions.assertEquals(user.getLogin(), found.getLogin());
        Assertions.assertEquals(user.getEmail(), found.getEmail());
        Assertions.assertEquals(user.getBirthday(), found.getBirthday());
    }

    @Test
    void getNonExisting() {
        long nonExistentId = 69;
        Optional<User> userOpt = storage.showUser(nonExistentId);
        Assertions.assertTrue(userOpt.isEmpty());
    }

    @Test
    public void testUpdateUser() {
        User created = storage.addUser(user);
        LocalDate newDate = LocalDate.of(1991, 1, 1);


        User updated = new User(
                "new_email@gmail.com",
                "new_login",
                "new_name",
                newDate);
        updated.setId(created.getId());

        storage.updateUser(updated);
        User found = storage.showUser(created.getId()).get();

        Assertions.assertEquals("new_login", found.getLogin());
        Assertions.assertEquals("new_name", found.getName());
        Assertions.assertEquals("new_email@gmail.com", found.getEmail());
        Assertions.assertEquals(newDate, found.getBirthday());
    }
}