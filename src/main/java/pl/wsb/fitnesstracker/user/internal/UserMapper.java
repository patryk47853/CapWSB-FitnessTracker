package pl.wsb.fitnesstracker.user.internal;

import org.springframework.stereotype.Component;
import pl.wsb.fitnesstracker.user.api.User;
import pl.wsb.fitnesstracker.user.api.UserDto;
import pl.wsb.fitnesstracker.user.api.UserSimpleDto;

/**
 * Mapper for converting between User entity and various DTOs.
 */
@Component
public class UserMapper {

    /**
     * Converts a User entity to full UserDto.
     */
    public UserDto toDto(User user) {
        return new UserDto(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getBirthdate(),
                user.getEmail()
        );
    }

    /**
     * Converts a User entity to a simple DTO (ID, first name, last name, email).
     */
    public UserSimpleDto toSimpleDto(User user) {
        return new UserSimpleDto(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail()
        );
    }

    /**
     * Creates a User entity from a full UserDto.
     */
    public User fromDto(UserDto dto) {
        return new User(
                dto.firstName(),
                dto.lastName(),
                dto.birthdate(),
                dto.email()
        );
    }
}
