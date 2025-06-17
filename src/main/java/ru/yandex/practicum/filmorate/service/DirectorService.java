package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dal.DirectorRepository;
import ru.yandex.practicum.filmorate.exception.DirectorIdException;
import ru.yandex.practicum.filmorate.model.Director;

import java.util.List;

@Service
@Slf4j
public class DirectorService {

    private final DirectorRepository directorRepository;

    @Autowired
    public DirectorService(DirectorRepository directorRepository) {
        this.directorRepository = directorRepository;
    }

    public Director showDirector(long directorId) {
        log.info("Показан режиссёр с id {}", directorId);
        return directorRepository.findById(directorId)
                .stream()
                .findAny()
                .orElseThrow(() -> new DirectorIdException(directorId));
    }

    public List<Director> showAllDirectors() {
        log.trace("Выведен список режиссёров");
        return directorRepository.findAll();
    }

    public Director addDirector(Director director) {
        log.info("Добавлен режиссёр с id {}", director.getId());
        return directorRepository.addDirector(director);
    }

    public Director updateDirector(Director director) {
        if (directorRepository.findById(director.getId()).isEmpty()) {
            throw new DirectorIdException(director.getId());
        }
        log.info("Обновлён режиссёр с id {}", director.getId());
        return directorRepository.updateDirector(director);
    }

    public void deleteDirector(long directorId) {
        directorRepository.deleteDirector(directorId);
    }
}
