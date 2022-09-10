package ru.yandex.practicum.filmorate.storage.film;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.film.Film;
import ru.yandex.practicum.filmorate.model.genre.Genre;
import ru.yandex.practicum.filmorate.model.mpa.Mpa;
import ru.yandex.practicum.filmorate.storage.genre.GenreStorage;
import ru.yandex.practicum.filmorate.storage.mpa.MpaStorage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

@Component("filmDbStorage")
public class FilmDbStorage implements FilmStorage {
    private final GenreStorage genreStorage;
    private final MpaStorage mpaStorage;
    private final JdbcTemplate jdbcTemplate;

    public FilmDbStorage(GenreStorage genreStorage, MpaStorage mpaStorage, JdbcTemplate jdbcTemplate) {
        this.genreStorage = genreStorage;
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
        createFilmGenre(film);
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
        updateFilmGenre(film);
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

    @Override
    public void addLike(int filmId, int userId) {
        getFilm(filmId);
        String sql = "INSERT INTO \"like\" (film_id,user_id) values (?,?)";
        jdbcTemplate.update(sql,
                filmId,
                userId);
    }

    @Override
    public boolean deleteLike(int filmId, int userId) {
        getFilm(filmId);
        String sql = "DELETE FROM \"like\" WHERE film_id=? AND user_id=?";
        return jdbcTemplate.update(sql,
                filmId,
                userId) > 0;
    }

    @Override
    public List<Film> getPopularFilm(int count) {
        String sql = "SELECT film.film_id FROM film " +
               "LEFT JOIN \"like\" l on film.film_id = l.film_id " +
                "GROUP BY film.film_id ORDER BY COUNT(l.USER_ID) DESC limit ?";

        return jdbcTemplate.query(sql,(rs, rowNum) -> getFilm(rs.getInt("film_id")),count);
    }

    private Film makeFilm(ResultSet rs) throws SQLException {
        int id = rs.getInt("film_id");
        String name = rs.getString("name");
        String description = rs.getString("description");
        LocalDate releaseDate = rs.getDate("release_date").toLocalDate();
        int duration = rs.getInt("duration");
        Mpa mpa = mpaStorage.getMpa(rs.getInt("mpa_id"));
        List<Genre> genres = findFilmGenre(id);
        return new Film(id, name, description, releaseDate, duration, mpa, genres);
    }

    private void createFilmGenre(Film film) {
        if (film.getGenres() == null) {
            return;
        }
        String sql = "INSERT INTO film_genre(film_id,genre_id) " + "values(?,?)";
        film.getGenres().stream().distinct()
                .forEach(genre -> jdbcTemplate.update(sql, film.getId(), genre.getId()));
    }

    private boolean deleteFilmGenre(Film film) {
        String sql = "DELETE FROM film_genre WHERE film_id=?";
        return jdbcTemplate.update(sql, film.getId()) > 0;
    }

    private void updateFilmGenre(Film film) {
        deleteFilmGenre(film);
        if (film.getGenres() == null) {
            return;
        }
        createFilmGenre(film);
    }

    private List<Genre> findFilmGenre(int filmId) {
        String sql = "SELECT * FROM film_genre WHERE film_id=?";
        return jdbcTemplate.query(sql, (rs, rowNum) -> genreStorage.getGenre(rs.getInt("genre_id")), filmId);
    }
}
