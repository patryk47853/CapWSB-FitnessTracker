package pl.wsb.fitnesstracker.user.internal;

import pl.wsb.fitnesstracker.user.api.User;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Service interface for managing users.
 */
public interface UserService {

    /**
     * Creates a new user in the system.
     */
    User createUser(User user);

    /**
     * Returns all users in the system.
     */
    List<User> findAllUsers();

    /**
     * Returns users older than a specific date.
     */
    List<User> findOlderThan(LocalDate date);

    /**
     * Returns users whose email contains the given fragment (case-insensitive).
     */
    List<User> findByEmailFragment(String fragment);

    /**
     * Returns a user by ID.
     */
    Optional<User> getUser(Long id);

    /**
     * Updates user data.
     */
    User updateUser(Long id, User user);

    /**
     * Deletes a user from the system.
     */
    void deleteUser(Long id);
}
