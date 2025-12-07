package pl.wsb.fitnesstracker.user.api;

import jakarta.annotation.Nullable;

/**
 * DTO representing basic information about a user (ID, first name, last name, email).
 */
public record UserSimpleDto(
        @Nullable Long id,
        String firstName,
        String lastName,
        String email
) {}
