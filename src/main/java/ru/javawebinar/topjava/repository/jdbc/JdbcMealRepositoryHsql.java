package ru.javawebinar.topjava.repository.jdbc;

import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.Profiles;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Repository
@Profile(value = {Profiles.HSQL_DB})
public class JdbcMealRepositoryHsql extends JdbcMealRepository <Timestamp> {
    public JdbcMealRepositoryHsql(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        super(jdbcTemplate, namedParameterJdbcTemplate);
    }

    @Override
    public Timestamp getCorrectDateTime(LocalDateTime dateTime) {
        return Timestamp.valueOf(dateTime);
    }
}