package com.shikhon_mate.user.repository;

import com.shikhon_mate.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email );

    @Modifying
    @Query(
            "update User " +
            "set refreshToken = ?2 " +
            "where id = ?1"
    )
    void updateRefreshTokenById( long id, String refreshToken );
}
