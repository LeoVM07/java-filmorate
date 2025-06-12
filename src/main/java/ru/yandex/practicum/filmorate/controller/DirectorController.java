package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.model.Director;
import ru.yandex.practicum.filmorate.service.DirectorService;

import java.util.List;

@RestController
@RequestMapping("/directors")
public class DirectorController {

    private final DirectorService directorService;

    @Autowired
    public DirectorController(DirectorService directorService) {
        this.directorService = directorService;
    }

    @GetMapping("/{directorId}")
    public ResponseEntity<Director> showDirector(@PathVariable("directorId") long directorId) {
        return new ResponseEntity<>(directorService.showDirector(directorId), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Director>> showAllDirectors() {
        return new ResponseEntity<>(directorService.showAllDirectors(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Director> addDirector(@RequestBody @Valid Director director) {
        return new ResponseEntity<>(directorService.addDirector(director), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<Director> updateDirector(@RequestBody @Valid Director director) {
        return new ResponseEntity<>(directorService.updateDirector(director), HttpStatus.OK);
    }

    @DeleteMapping("/{directorId}")
    public void deleteDirector(@PathVariable("directorId") long directorId) {
        directorService.deleteDirector(directorId);
    }

}
