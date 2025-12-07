package pl.wsb.fitnesstracker.user.internal;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.wsb.fitnesstracker.user.api.User;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Implementation of {@link UserService} containing all business logic.
 */
@Service
@RequiredArgsConstructor
@Slf4j
class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public User createUser(User user) {
        log.info("Creating user {}", user);
        if (user.getId() != null) {
            throw new IllegalArgumentException("User already has ID!");
        }
        return userRepository.save(user);
    }

    @Override
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public List<User> findOlderThan(LocalDate date) {
        return userRepository.findAll().stream()
                .filter(u -> u.getBirthdate().isBefore(date))
                .collect(Collectors.toList());
    }

    @Override
    public List<User> findByEmailFragment(String fragment) {
        String lowered = fragment.toLowerCase();
        return userRepository.findAll().stream()
                .filter(u -> u.getEmail().toLowerCase().contains(lowered))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<User> getUser(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public User updateUser(Long id, User user) {
        User existing = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        User updated = new User(
                user.getFirstName(),
                user.getLastName(),
                user.getBirthdate(),
                user.getEmail()
        );
        updated.setId(existing.getId());
        return userRepository.save(updated);
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
