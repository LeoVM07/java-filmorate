package ru.yandex.practicum.filmorate.controller;


import java.util.Map;

/*Класс со статическим методом для генерации id. Я бы предпочёл сделать его общим абстрактным классом для контроллеров
без всяких статических классов, чтобы у пользователя было меньше шансов сломать код, но, пока тут только один метод,
не вижу в этом смысла*/
public class IDGenerator {

    public static Integer getNextId(Map<Integer, ?> map) {
        int currentMaxId = map.keySet()
                .stream()
                .mapToInt(id -> id)
                .max()
                .orElse(0);
        return ++currentMaxId;
    }
}
