package dev.jahid.user_auth_db_config_boilerplate.user.repository;

import dev.jahid.user_auth_db_config_boilerplate.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email );
}
