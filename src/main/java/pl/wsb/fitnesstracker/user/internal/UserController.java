package pl.wsb.fitnesstracker.user.internal;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.wsb.fitnesstracker.user.api.User;
import pl.wsb.fitnesstracker.user.api.UserDto;
import pl.wsb.fitnesstracker.user.api.UserSimpleDto;

import java.time.LocalDate;
import java.util.List;

/**
 * REST controller for user operations in the FitnessTracker system.
 * Provides endpoints for creating, updating, retrieving, and deleting users.
 */
@RestController
@RequestMapping("/v1/users")
@RequiredArgsConstructor
class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    /**
     * Retrieves all users with full details.
     *
     * @return List of UserDto containing full user information
     */
    @GetMapping
    public List<UserDto> getAllUsers() {
        return userService.findAllUsers().stream()
                .map(userMapper::toDto)
                .toList();
    }

    /**
     * Retrieves all users with simplified information (first name and last name).
     *
     * @return List of UserSimpleDto containing only ID, first name, and last name
     */
    @GetMapping("/simple")
    public List<UserSimpleDto> getAllUsersSimple() {
        return userService.findAllUsers().stream()
                .map(userMapper::toSimpleDto)
                .toList();
    }

    /**
     * Retrieves a specific user by ID.
     *
     * @param id User ID
     * @return UserDto containing full user information
     * @throws IllegalArgumentException if user not found
     */
    @GetMapping("/{id}")
    public UserDto getUserById(@PathVariable Long id) {
        return userService.getUser(id)
                .map(userMapper::toDto)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
    }

    /**
     * Retrieves users whose email contains the given fragment, case-insensitive.
     *
     * @param email Fragment of email to search for
     * @return List of UserSimpleDto containing only ID and email
     */
    @GetMapping("/email")
    public List<UserSimpleDto> getUsersByEmailFragment(@RequestParam String email) {
        return userService.findByEmailFragment(email).stream()
                .map(userMapper::toSimpleDto)
                .toList();
    }

    /**
     * Retrieves users older than a specified date.
     *
     * @param date Users born before this date will be returned
     * @return List of UserDto containing full user information
     */
    @GetMapping("/older/{date}")
    public List<UserDto> getUsersOlderThan(@PathVariable LocalDate date) {
        return userService.findOlderThan(date).stream()
                .map(userMapper::toDto)
                .toList();
    }

    /**
     * Creates a new user.
     *
     * @param dto UserDto containing information of the user to create
     * @return UserDto containing the created user's information
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto createUser(@RequestBody UserDto dto) {
        User created = userService.createUser(userMapper.fromDto(dto));
        return userMapper.toDto(created);
    }

    /**
     * Updates an existing user by ID.
     *
     * @param id  User ID to update
     * @param dto UserDto containing updated information
     * @return UserDto containing the updated user's information
     */
    @PutMapping("/{id}")
    public UserDto updateUser(@PathVariable Long id, @RequestBody UserDto dto) {
        User updated = userService.updateUser(id, userMapper.fromDto(dto));
        return userMapper.toDto(updated);
    }

    /**
     * Deletes a user by ID.
     *
     * @param id User ID to delete
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }
}
