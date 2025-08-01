package com.postech.challenge_01.repositories.user_type;

import com.postech.challenge_01.entities.UserTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserTypeJpaRepository extends JpaRepository<UserTypeEntity, Long> {
    @Query("""
        SELECT u FROM UserTypeEntity u
        WHERE u.name = :name
    """)
    Optional<UserTypeEntity> findByName(String name);
}
