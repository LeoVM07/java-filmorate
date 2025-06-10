package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dal.DirectorRepository;
import ru.yandex.practicum.filmorate.exception.DirectorIdException;
import ru.yandex.practicum.filmorate.model.Director;

import java.util.List;

@Service
public class DirectorService {

    private final DirectorRepository directorRepository;

    @Autowired
    public DirectorService(DirectorRepository directorRepository) {
        this.directorRepository = directorRepository;
    }

    public Director showDirector(long directorId) {
        return directorRepository.findById(directorId)
                .stream()
                .findAny()
                .orElseThrow(() -> new DirectorIdException(directorId));
    }

    public List<Director> showAllDirectors() {
        return directorRepository.findAll();
    }

    public Director addDirector(Director director) {
        return directorRepository.addDirector(director);
    }

    public Director updateDirector(Director director) {
        if (directorRepository.findById(director.getId()).isEmpty()) {
            throw new DirectorIdException(director.getId());
        }
        return directorRepository.updateDirector(director);
    }

    public void deleteDirector(long directorId) {
        directorRepository.deleteDirector(directorId);
    }
}
