package pl.wsb.fitnesstracker.user.api;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.annotation.Nullable;

import java.time.LocalDate;

/**
 * Transfer object (DTO) containing full user information exposed by API.
 *
 *
 * @param id        database identifier (nullable for create requests)
 * @param firstName first name
 * @param lastName  last name
 * @param birthdate birthdate in ISO format (yyyy-MM-dd)
 * @param email     unique email address
 */
public record UserDto(
        @Nullable Long id,
        String firstName,
        String lastName,
        @JsonFormat(pattern = "yyyy-MM-dd") LocalDate birthdate,
        String email
) {}
