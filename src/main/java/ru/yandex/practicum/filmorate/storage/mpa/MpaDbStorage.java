package ru.yandex.practicum.filmorate.storage.mpa;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.mpa.Mpa;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
@Component
public class MpaDbStorage implements MpaStorage{

    private final JdbcTemplate jdbcTemplate;

    public MpaDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Mpa> findAll() {
        String sql = "SELECT * FROM mpa";
        return jdbcTemplate.query(sql, (rs, rowNum) -> makeMpa(rs));
    }

    @Override
    public Mpa getMpa(int id) {
        String sql = "SELECT * FROM mpa WHERE mpa_id=?";
        try {
            return jdbcTemplate.queryForObject(sql, ((rs, rowNum )-> makeMpa(rs)), id);
        } catch(EmptyResultDataAccessException e){
            throw new NotFoundException("Mpa not found");
        }
    }

    private Mpa makeMpa(ResultSet rs) throws SQLException{
        int id = rs.getInt("mpa_id");
        String name = rs.getString("name");
        return new Mpa(id,name);
    }
}
