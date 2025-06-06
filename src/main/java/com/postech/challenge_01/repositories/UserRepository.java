package com.postech.challenge_01.repositories;
import com.postech.challenge_01.domains.User;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> findByLogin(String login);
    boolean updatePassword(Long id, String password);
}