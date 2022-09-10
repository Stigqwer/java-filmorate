package ru.yandex.practicum.filmorate.storage.user;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.user.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

@Component("userDbStorage")
public class UserDbStorage implements UserStorage{
    private final JdbcTemplate jdbcTemplate;

    public UserDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<User> findAll() {
        String sql = "SELECT * FROM \"user\"";
        return jdbcTemplate.query(sql,(rs, rowNum) -> makeUser(rs));
    }

    @Override
    public User create(User user) {
        String sql = "INSERT INTO \"user\" (user_id, email, name, login, birthday) " +
                "values(?,?,?,?,?)";
        jdbcTemplate.update(sql,
                user.getId(),
                user.getEmail(),
                user.getName(),
                user.getLogin(),
                user.getBirthday());
        return getUser(user.getId());
    }

    @Override
    public User update(User user) {
        String sql = "UPDATE \"user\" SET email=?,name=?,login=?,birthday=? WHERE user_id=?";
        jdbcTemplate.update(sql,
                user.getEmail(),
                user.getName(),
                user.getLogin(),
                user.getBirthday(),
                user.getId());
        return getUser(user.getId());
    }

    @Override
    public User getUser(int id) {
        String sql = "SELECT * FROM \"user\" WHERE user_id=?";
        try{
           return jdbcTemplate.queryForObject(sql,(rs, rowNum) -> makeUser(rs), id);
        } catch (EmptyResultDataAccessException e){
            throw new NotFoundException("User not found");
        }
    }

    @Override
    public List<User> getCommonFriends(int id, int otherId) {
        getUser(id);
        getUser(otherId);
        String sql
        return null;
    }

    private User makeUser(ResultSet rs) throws SQLException {
        int id = rs.getInt("user_id");
        String email = rs.getString("email");
        String name = rs.getString("name");
        String login = rs.getString("login");
        LocalDate birthday = rs.getDate("birthday").toLocalDate();
        return new User(id,email,name,login,birthday);
    }
}
