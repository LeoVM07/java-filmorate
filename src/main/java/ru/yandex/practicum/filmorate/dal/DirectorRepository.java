package ru.yandex.practicum.filmorate.dal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.exception.DirectorIdException;
import ru.yandex.practicum.filmorate.model.Director;
import ru.yandex.practicum.filmorate.storage.DirectorStorage;

import java.util.List;
import java.util.Optional;

@Repository
public class DirectorRepository extends BaseRepository<Director> implements DirectorStorage {

    private static final String FIND_BY_ID_QUERY = """
            SELECT d.director_id,
            d.director_name
            FROM directors d
            WHERE d.director_id = ?
            """;

    private static final String FIND_ALL_QUERY = """
            SELECT d.director_id,
            d.director_name
            FROM directors d
            """;

    private static final String UPDATE_QUERY = """
            UPDATE directors
            SET director_name = ?
            WHERE director_id = ?
            """;

    private static final String INSERT_QUERY = """
            INSERT INTO directors (director_name)
            VALUES (?)
            """;

    private static final String DELETE_QUERY = """
            DELETE FROM directors
            WHERE director_id = ?
            """;


    @Autowired
    public DirectorRepository(JdbcTemplate jdbc, RowMapper<Director> mapper) {
        super(jdbc);
        this.mapper = mapper;

    }


    @Override
    public List<Director> findAll() {
        return findMany(FIND_ALL_QUERY);
    }

    @Override
    public Optional<Director> findById(long id) {
        return findOne(FIND_BY_ID_QUERY, id);
    }

    @Override
    public Director addDirector(Director director) {
        long id = insert(INSERT_QUERY, director.getName());
        director.setId(id);
        return director;
    }

    @Override
    public Director updateDirector(Director director) {
        update(UPDATE_QUERY, director.getName(), director.getId());
        return director;
    }

    @Override
    public void deleteDirector(long directorId) {
        if (!delete(DELETE_QUERY, directorId)) {
            throw new DirectorIdException(directorId);
        }
    }


}
