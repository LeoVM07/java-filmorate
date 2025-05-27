package ru.yandex.practicum.filmorate.comparator;

import ru.yandex.practicum.filmorate.model.Genre;

import java.util.Comparator;

public class GenreIdComparator implements Comparator<Genre> {
    @Override
    public int compare(Genre o1, Genre o2) {
        return Long.compare(o1.getId(), o2.getId());
    }
}
