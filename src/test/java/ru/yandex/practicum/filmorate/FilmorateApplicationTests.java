package ru.yandex.practicum.filmorate;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import ru.yandex.practicum.filmorate.model.film.Film;
import ru.yandex.practicum.filmorate.model.genre.Genre;
import ru.yandex.practicum.filmorate.model.mpa.Mpa;
import ru.yandex.practicum.filmorate.model.user.User;
import ru.yandex.practicum.filmorate.storage.film.FilmDbStorage;
import ru.yandex.practicum.filmorate.storage.genre.GenreDbStorage;
import ru.yandex.practicum.filmorate.storage.genre.GenreStorage;
import ru.yandex.practicum.filmorate.storage.mpa.MpaDbStorage;
import ru.yandex.practicum.filmorate.storage.mpa.MpaStorage;
import ru.yandex.practicum.filmorate.storage.user.UserDbStorage;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Sql(scripts = "classpath:schema.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "classpath:data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class FilmorateApplicationTests {
    private final MpaDbStorage mpaStorage;
    private final GenreDbStorage genreStorage;
    private final UserDbStorage userStorage;
    private final FilmDbStorage filmStorage;

    @Test
    public void testGetMpa(){
        Mpa mpa = mpaStorage.getMpa(1);

        assertEquals(mpa, new Mpa(1,"G"));
    }

    @Test
    public void testFindAllMpa(){
        List<Mpa> mpaList = mpaStorage.findAll();

        assertEquals(mpaList.size(), 5);
    }

    @Test
    public void testGetGenre(){
        Genre genre = genreStorage.getGenre(1);

        assertEquals(genre, new Genre(1, "Комедия"));
    }

    @Test
    public void testFindAllGenre(){
        List<Genre> genreList = genreStorage.findAll();

        assertEquals(genreList.size(),6);
    }

    @Test
    public void testCreateFilm(){
        Film film = new Film(1, "Терминатор", "Это описание меньше 200 символов",
                LocalDate.of(2020,11,10), 15,
                new Mpa(1,"G"),new ArrayList<>());

        Film film1 = filmStorage.create(film);

        assertEquals(film1,film);
    }

    @Test
    public void testUpdateFilm(){
        Film film = new Film(1, "Терминатор", "Это описание меньше 200 символов",
                LocalDate.of(2020,11,10), 15,
                new Mpa(1,"G"),new ArrayList<>());
        filmStorage.create(film);
        Film film2 = new Film(1, "Терминатор2", "Это описание меньше 200 символов",
                LocalDate.of(2020,11,10), 15,
                new Mpa(1,"G"),new ArrayList<>());

        Film film3 = filmStorage.update(film2);

        assertEquals(film3,film2);
    }

    @Test
    public void testGetFilm(){
        Film film = new Film(1, "Терминатор", "Это описание меньше 200 символов",
                LocalDate.of(2020,11,10), 15,
                new Mpa(1,"G"),new ArrayList<>());
        filmStorage.create(film);

        Film film1 = filmStorage.getFilm(1);

        assertEquals(film1,film);

    }

    @Test
    public void testFindAllFilm(){
        Film film = new Film(1, "Терминатор", "Это описание меньше 200 символов",
                LocalDate.of(2020,11,10), 15,
                new Mpa(1,"G"),new ArrayList<>());
        filmStorage.create(film);
        Film film1 = new Film(2, "Терминатор2", "Это описание меньше 200 символов",
                LocalDate.of(2020,11,10), 15,
                new Mpa(1,"G"),new ArrayList<>());
        filmStorage.create(film1);

        List<Film> films = filmStorage.findAll();

        assertEquals(films.size(),2);
    }

    @Test
    public void testAddLike(){
        Film film = new Film(1, "Терминатор", "Это описание меньше 200 символов",
                LocalDate.of(2020,11,10), 15,
                new Mpa(1,"G"),new ArrayList<>());
        filmStorage.create(film);
        User user = new User(1, "stigqwer@gmail.com", "stigqwer", "Максим",
                LocalDate.of(1990, 11, 4));
        userStorage.create(user);

        assertTrue(filmStorage.addLike(1,1));
    }

    @Test
    public void testGetPopularFilm(){
        Film film = new Film(1, "Терминатор", "Это описание меньше 200 символов",
                LocalDate.of(2020,11,10), 15,
                new Mpa(1,"G"),new ArrayList<>());
        filmStorage.create(film);
        Film film1 = new Film(2, "Терминатор2", "Это описание меньше 200 символов",
                LocalDate.of(2020,11,10), 15,
                new Mpa(1,"G"),new ArrayList<>());
        filmStorage.create(film1);
        User user = new User(1, "stigqwer@gmail.com", "stigqwer", "Максим",
                LocalDate.of(1990, 11, 4));
        userStorage.create(user);
        filmStorage.addLike(2,1);

        assertEquals(filmStorage.getPopularFilm(1).get(0),film1);
    }

    @Test
    public void testDeleteLike(){
        Film film = new Film(1, "Терминатор", "Это описание меньше 200 символов",
                LocalDate.of(2020,11,10), 15,
                new Mpa(1,"G"),new ArrayList<>());
        filmStorage.create(film);
        User user = new User(1, "stigqwer@gmail.com", "stigqwer", "Максим",
                LocalDate.of(1990, 11, 4));
        userStorage.create(user);
        filmStorage.addLike(1,1);

        assertTrue(filmStorage.deleteLike(1,1));
    }

    @Test
    public void testCreateUser(){
        User user = new User(1, "stigqwer@gmail.com", "stigqwer", "Максим",
                LocalDate.of(1990, 11, 4));

        User user1 = userStorage.create(user);

        assertEquals(user1,user);
    }

    @Test
    public void testUpdateUser(){
        User user = new User(1, "stigqwer@gmail.com", "stigqwer", "Максим",
                LocalDate.of(1990, 11, 4));
        userStorage.create(user);
        User user1 = new User(1, "viper@gmail.com", "viper", "Максим",
                LocalDate.of(1990, 11, 4));


        assertEquals(userStorage.update(user1),user1);
    }

    @Test
    public void testGetUser(){
        User user = new User(1, "stigqwer@gmail.com", "stigqwer", "Максим",
                LocalDate.of(1990, 11, 4));
        userStorage.create(user);

        assertEquals(userStorage.getUser(1),user);
    }

    @Test
    public void testFindAllUser(){
        User user = new User(1, "stigqwer@gmail.com", "stigqwer", "Максим",
                LocalDate.of(1990, 11, 4));
        userStorage.create(user);
        User user1 = new User(2, "viper@gmail.com", "viper", "Максим",
                LocalDate.of(1990, 11, 4));
        userStorage.create(user1);

        assertEquals(userStorage.findAll().size(),2);
    }

    @Test
    public void testAddFriend(){
        User user = new User(1, "stigqwer@gmail.com", "stigqwer", "Максим",
                LocalDate.of(1990, 11, 4));
        userStorage.create(user);
        User user1 = new User(2, "viper@gmail.com", "viper", "Максим",
                LocalDate.of(1990, 11, 4));
        userStorage.create(user1);

        assertTrue(userStorage.addFriend(1,2));
    }

    @Test
    public void testGetFriends(){
        User user = new User(1, "stigqwer@gmail.com", "stigqwer", "Максим",
                LocalDate.of(1990, 11, 4));
        userStorage.create(user);
        User user1 = new User(2, "viper@gmail.com", "viper", "Максим",
                LocalDate.of(1990, 11, 4));
        userStorage.create(user1);
        User user2 = new User(3, "1@gmail.com", "1", "Максим",
                LocalDate.of(1990, 11, 4));
        userStorage.create(user2);
        userStorage.addFriend(1,2);
        userStorage.addFriend(1,3);

        assertEquals(userStorage.getFriends(1).size(),2);
    }

    @Test
    public void testDeleteFriend(){
        User user = new User(1, "stigqwer@gmail.com", "stigqwer", "Максим",
                LocalDate.of(1990, 11, 4));
        userStorage.create(user);
        User user1 = new User(2, "viper@gmail.com", "viper", "Максим",
                LocalDate.of(1990, 11, 4));
        userStorage.create(user1);
        userStorage.addFriend(1,2);

        assertTrue(userStorage.deleteFriend(1,2));
    }

}
