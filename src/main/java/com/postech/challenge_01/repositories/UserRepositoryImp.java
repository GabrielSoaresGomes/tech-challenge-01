package com.postech.challenge_01.repositories;

import com.postech.challenge_01.entities.User;
import com.postech.challenge_01.exceptions.IdNotReturnedException;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Repository
public class UserRepositoryImp implements UserRepository {
    private final JdbcClient jdbcClient;

    public UserRepositoryImp(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    @Override
    public Optional<User> findById(Long id) {
        String sql = "SELECT * FROM users WHERE id = :id";

        return jdbcClient
                .sql(sql)
                .param("id", id)
                .query(User.class)
                .optional();
    }

    @Override
    public Optional<User> findByLogin(String login) {
        String sql = "SELECT id, name, email, login, password, address, lastModifiedDateTime FROM users WHERE login = :login";

        return jdbcClient
                .sql(sql)
                .param("login", login)
                .query(User.class)
                .optional();
    }

    @Override
    public List<User> findAll(int size, int offset) {
        String sql = "SELECT * FROM users LIMIT :size OFFSET :offset";

        return jdbcClient
                .sql(sql)
                .param("size", size)
                .param("offset", offset)
                .query(User.class)
                .list();
    }

    @Override
    public User save(User user) {
        String sql = """
            INSERT INTO users (name, email, login, password, address, lastModifiedDateTime)
            VALUES (:name, :email, :login, :password, :address, :lastModifiedDateTime)
        """;

        var keyHolder = new GeneratedKeyHolder();
        Integer result = this.jdbcClient
                .sql(sql)
                .param("name", user.getName())
                .param("email", user.getEmail())
                .param("login", user.getLogin())
                .param("password", user.getPassword())
                .param("address", user.getAddress())
                .param("lastModifiedDateTime", user.getLastModifiedDateTime())
                .update(keyHolder);
        if (result == 0) {
            return null;
        }
        var generatedId = this.getIdFromKeyHolder(keyHolder);

        return new User(
                generatedId,
                user.getName(),
                user.getEmail(),
                user.getLogin(),
                user.getPassword(),
                user.getAddress(),
                user.getLastModifiedDateTime()
        );
    }

    @Override
    public User update(User user, Long id) {
        String sql = """
            UPDATE users
            SET name = :name, email = :email, login = :login, password = :password, address = :address, lastModifiedDateTime = :lastModifiedDateTime
            WHERE id = :id
        """;

        Integer result = this.jdbcClient
                .sql(sql)
                .param("name", user.getName())
                .param("email", user.getEmail())
                .param("login", user.getLogin())
                .param("password", user.getPassword())
                .param("address", user.getAddress())
                .param("lastModifiedDateTime", user.getLastModifiedDateTime())
                .param("id", id)
                .update();
        if (result == 0) {
            return null;
        }
        return user;
    }

    @Override
    public Integer delete(Long id) {
        String sql = "DELETE FROM users WHERE id = :id";

        return this.jdbcClient
                .sql(sql)
                .param("id", id)
                .update();
    }

    private Long getIdFromKeyHolder(GeneratedKeyHolder keyHolder) {
        Map<String, Object> keys = keyHolder.getKeys();

        if (Objects.isNull(keys) || !keys.containsKey("id")) {
            throw new IdNotReturnedException();
        }

        return ((Number) keys.get("id")).longValue();
    }
}
