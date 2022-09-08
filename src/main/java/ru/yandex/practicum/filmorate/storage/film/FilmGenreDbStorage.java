package ru.yandex.practicum.filmorate.storage.film;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.film.Film;
import ru.yandex.practicum.filmorate.model.film.FilmGenre;
import ru.yandex.practicum.filmorate.model.genre.Genre;
import ru.yandex.practicum.filmorate.storage.genre.GenreStorage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@Qualifier
public class FilmGenreDbStorage implements FilmGenreStorage{
    private final JdbcTemplate jdbcTemplate;
    private final GenreStorage genreStorage;

    public FilmGenreDbStorage(JdbcTemplate jdbcTemplate, GenreStorage genreStorage) {
        this.jdbcTemplate = jdbcTemplate;
        this.genreStorage = genreStorage;
    }


    @Override
    public Set<Genre> findFilmGenre(int filmId) {
        String sql = "SELECT * FROM film_genre WHERE film_id=?";
        List<FilmGenre> filmGenres = jdbcTemplate.query(sql,(rs, rowNum) -> makeFilmGenre(rs),filmId);
        return filmGenres.stream()
                .map(filmGenre -> genreStorage.getGenre(filmGenre.getGenreId()))
                .collect(Collectors.toSet());
    }

    @Override
    public void createFilmGenre(Film film) {
        if(film.getGenres() == null){
            return;
        }
        String sql = "INSERT INTO film_genre(film_id,genre_id) " + "values(?,?)";
        film.getGenres().forEach(genre -> jdbcTemplate.update(sql,film.getId(),genre.getId()));
    }

    @Override
    public void updateFilmGenre(Film film) {
        //дропнуть данные и добавить новые
        if(film.getGenres() == null){
            return;
        }
        String sql = "UPDATE film_genre SET film_id=?,genre_id=? WHERE film_id=?";
        film.getGenres().forEach(genre -> jdbcTemplate.update(sql,film.getId(),genre.getId()));
    }

    private FilmGenre makeFilmGenre(ResultSet rs) throws SQLException {
        int filmGenreId = rs.getInt("film_genre_id");
        int filmId = rs.getInt("film_id");
        int genreId = rs.getInt("genre_id");
        return new FilmGenre(filmGenreId,filmId,genreId);
    }
}
