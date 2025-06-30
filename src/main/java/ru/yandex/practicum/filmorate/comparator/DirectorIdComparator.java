package ru.yandex.practicum.filmorate.comparator;

import ru.yandex.practicum.filmorate.model.Director;


import java.util.Comparator;

public class DirectorIdComparator implements Comparator<Director> {
    public int compare(Director o1, Director o2) {
        return Long.compare(o1.getId(), o2.getId());
    }
}
