package pl.wsb.fitnesstracker.user.api;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repository for User entity.
 * Provides basic CRUD operations and custom search helpers.
 */
public interface UserRepository extends JpaRepository<User, Long> {

    List<User> findByEmailContainingIgnoreCase(String email);

}
