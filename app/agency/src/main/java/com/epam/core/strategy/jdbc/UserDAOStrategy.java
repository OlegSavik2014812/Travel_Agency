package com.epam.core.strategy.jdbc;

import com.epam.core.entity.User;
import com.epam.core.strategy.Strategy;
import com.epam.core.util.SearchCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * The type User dao strategy.
 */
@Repository
public class UserDAOStrategy extends BaseDaoStrategy<User> implements Strategy<User> {
    private static final String SQL_CREATE = "INSERT INTO user(login, password,role) VALUES(?,?,?)";
    private static final String SQL_UPDATE = "UPDATE user SET login = ?, password = ? WHERE id = ?";
    private static final String SQL_DELETE_BY_ID = "DELETE FROM user WHERE user.id =? ";
    private static final String SQL_GET_BY_ID = "SELECT user.id AS user_id, user.login AS user_login, user.password AS user_password FROM user " + "WHERE user.id = ?";
    private static final String SQL_GET_ALL = "SELECT user.id AS user_id, user.login AS user_login, user.password AS user_password FROM user";
    private final JdbcTemplate jdbcTemplate;
    private RowMapper<User> rowMapper = (resultSet, i) -> {
        User user = new User(resultSet.getString("user_login"), resultSet.getString("user_password"));
        user.setId(resultSet.getLong("user_id"));
        return user;
    };

    /**
     * Instantiates a new User dao strategy.
     *
     * @param jdbcTemplate the jdbc template
     */
    @Autowired(required = false)
    public UserDAOStrategy(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
        setTableName(User.class.getSimpleName().toLowerCase());
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void save(User entity) {
        jdbcTemplate.update(SQL_CREATE, entity.getLogin(), entity.getPassword(), entity.getRole().toString());
    }

    @Override
    public User findById(Long id) {
        return jdbcTemplate.query(SQL_GET_BY_ID, rowMapper, id).get(0);
    }

    @Override
    public User update(User entity) {
        jdbcTemplate.update(SQL_UPDATE, entity.getLogin(), entity.getPassword(), entity.getId());
        return findById(entity.getId());
    }

    @Override
    public boolean deleteById(Long id) {
        return jdbcTemplate.update(SQL_DELETE_BY_ID, id) == 1;
    }

    @Override
    public List<User> findAll() {
        return jdbcTemplate.query(SQL_GET_ALL, rowMapper);
    }

    @Override
    public List<User> searchEntity(List<SearchCriteria> params) {
        return null;
    }

    @Override
    public void setParametrizedClass(Class<User> c) {

    }
}
