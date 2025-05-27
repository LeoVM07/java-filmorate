package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.dao.DataIntegrityViolationException;
import ru.yandex.practicum.filmorate.dal.FilmRepository;
import ru.yandex.practicum.filmorate.dal.MpaRepository;
import ru.yandex.practicum.filmorate.dal.mappers.FilmListResultSetExtractor;
import ru.yandex.practicum.filmorate.dal.mappers.FilmResultSetExtractor;
import ru.yandex.practicum.filmorate.dal.mappers.MpaRowMapper;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Mpa;


import java.time.LocalDate;
import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

@JdbcTest
@AutoConfigureTestDatabase
@Import({FilmRepository.class, MpaRepository.class, FilmListResultSetExtractor.class, FilmResultSetExtractor.class, MpaRowMapper.class})
public class FilmStorageTest {

    @Autowired
    private FilmRepository storage;
    private final Film testFilm1 = Film.builder()
            .name("Test_Film_1")
            .description("Test_Description_1")
            .releaseDate(LocalDate.of(2001, 1, 1))
            .duration(100)
            .mpa(new Mpa(1, "G"))
            .build();

    private final Film testFilm2 = Film.builder()
            .name("Test_Film_2")
            .description("Test_Description_2")
            .releaseDate(LocalDate.of(2002, 2, 2))
            .duration(200)
            .mpa(new Mpa(2, "PG"))
            .build();


    @Test
    void create() {
        storage.addFilm(testFilm1);

        Assertions.assertNotNull(storage.showFilm(testFilm1.getId()));
        Assertions.assertEquals(1, storage.showAllFilms().size());
    }

    @Test
    void update() {
        Film created = storage.addFilm(testFilm1);
        Film updated = Film.builder()
                .name("Updated_Name")
                .description("Updated_Description")
                .releaseDate(LocalDate.of(1992, 2, 2))
                .duration(200)
                .mpa(new Mpa(2, "PG"))
                .build();

        updated.setId(created.getId());
        updated = storage.updateFilm(updated);
        Film found = storage.showFilm(created.getId()).get();

        assertThat(updated).isNotNull();
        assertThat(found).isNotNull();
        assertThat(found.getName()).isEqualTo("Updated_Name");
        assertThat(found.getDescription()).isEqualTo("Updated_Description");
        assertThat(found.getReleaseDate()).isEqualTo(updated.getReleaseDate());
        assertThat(found.getMpa()).extracting(Mpa::getId).isEqualTo((long) 2);
    }

    @Test
    void getNonExisting() {
        long wrongId = 69;
        assertThatThrownBy(() -> storage.showFilm(wrongId))
                .isInstanceOf(DataIntegrityViolationException.class);
    }

    @Test
    void getAll() {
        storage.addFilm(testFilm1);
        storage.addFilm(testFilm2);

        Collection<Film> films = storage.showAllFilms();
        assertThat(films).hasSize(2);
    }
}