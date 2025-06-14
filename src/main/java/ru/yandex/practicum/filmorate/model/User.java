package ru.yandex.practicum.filmorate.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;

@Data
public class User {

    private Long id; //значение полю будет задаваться контроллером, остальные поля должны быть заполнены заранее

    @Email(message = "Некорректно заполненный электронный адрес")
    @NotBlank(message = "Электронный адрес необходимо заполнить")
    private final String email;

    @NotBlank(message = "Необходимо указать логин")
    @Pattern(regexp = "\\S+", message = "Логин не должен содержать пробелов")
    private final String login;

    @Setter
    private String name;

    @NotNull(message = "Необходимо указать дату рождения")
    @Past
    private final LocalDate birthday;

    public User(String email, String login, LocalDate birthday) {
        this.email = email;
        this.login = login;
        this.birthday = birthday;
    }

    @JsonCreator
    public User(@JsonProperty("email") String email,
                @JsonProperty("login") String login,
                @JsonProperty("name") String name,
                @JsonProperty("birthday") LocalDate birthday) {
        this.email = email;
        this.login = login;
        this.name = name;
        this.birthday = birthday;
    }
}
