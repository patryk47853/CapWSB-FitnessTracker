package pl.wsb.fitnesstracker.user.internal;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.wsb.fitnesstracker.user.api.User;

import java.util.List;

/**
 * Repository for User entity.
 * Provides basic CRUD operations and custom search helpers.
 */
interface UserRepository extends JpaRepository<User, Long> {

    List<User> findByEmailContainingIgnoreCase(String email);

}
