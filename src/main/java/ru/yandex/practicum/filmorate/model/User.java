package ru.yandex.practicum.filmorate.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@Data
public class User {

    private Integer id; //значение полю будет задаваться контроллером, остальные поля должны быть заполнены заранее

    @Email(message = "Некорректно заполненный электронный адрес")
    @NotNull(message = "Электронный адрес необходимо заполнить")
    private final String email;

    @NotNull(message = "Необходимо указать логин")
    @NotBlank(message = "Необходимо указать логин")
    private final String login;

    private String name;

    @NotNull(message = "Необходимо указать дату рождения")
    private final LocalDate birthday;


    public User(String email,
                String login,
                LocalDate birthday) {
        this.email = email;
        this.login = login;
        this.name = login;
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
