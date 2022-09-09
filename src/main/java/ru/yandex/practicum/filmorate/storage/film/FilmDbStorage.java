package ru.yandex.practicum.filmorate.storage.film;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.film.Film;
import ru.yandex.practicum.filmorate.model.genre.Genre;
import ru.yandex.practicum.filmorate.model.mpa.Mpa;
import ru.yandex.practicum.filmorate.storage.mpa.MpaStorage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Component("filmDbStorage")
public class FilmDbStorage implements FilmStorage {
    private final FilmGenreStorage filmGenreStorage;
    private final MpaStorage mpaStorage;
    private final JdbcTemplate jdbcTemplate;

    public FilmDbStorage(FilmGenreStorage filmGenreStorage, MpaStorage mpaStorage, JdbcTemplate jdbcTemplate) {
        this.filmGenreStorage = filmGenreStorage;
        this.mpaStorage = mpaStorage;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Film> findAll() {
        String sql = "SELECT * FROM film";
        return jdbcTemplate.query(sql, (rs, rowNum) -> makeFilm(rs));
    }

    @Override
    public Film create(Film film) {
        String sql = "INSERT INTO film (film_id,name, description, release_date, duration,mpa_id) " +
                "values (?,?,?,?,?,?)";
        jdbcTemplate.update(sql,
                film.getId(),
                film.getName(),
                film.getDescription(),
                film.getReleaseDate(),
                film.getDuration(),
                film.getMpa().getId());
        filmGenreStorage.createFilmGenre(film);
        return getFilm(film.getId());
    }

    @Override
    public Film update(Film film) {
        String sql = "UPDATE film SET name=?, description=?, release_date=?,duration=?,mpa_id=? WHERE film_id=?";
        jdbcTemplate.update(sql,
                film.getName(),
                film.getDescription(),
                film.getReleaseDate(),
                film.getDuration(),
                film.getMpa().getId(),
                film.getId());
        filmGenreStorage.updateFilmGenre(film);
        return getFilm(film.getId());
    }

    @Override
    public Film getFilm(int id) {
        String sql = "SELECT * FROM film WHERE film_id=?";
        try {
            return jdbcTemplate.queryForObject(sql, ((rs, rowNum) -> makeFilm(rs)), id);
        } catch (EmptyResultDataAccessException e) {
            throw new NotFoundException("Film not found");
        }
    }

    private Film makeFilm(ResultSet rs) throws SQLException {
        int id = rs.getInt("film_id");
        String name = rs.getString("name");
        String description = rs.getString("description");
        LocalDate releaseDate = rs.getDate("release_date").toLocalDate();
        int duration = rs.getInt("duration");
        Mpa mpa = mpaStorage.getMpa(rs.getInt("mpa_id"));
        List<Genre> genres = filmGenreStorage.findFilmGenre(id);
        return new Film(id, name, description, releaseDate, duration, mpa, genres);
    }
}
