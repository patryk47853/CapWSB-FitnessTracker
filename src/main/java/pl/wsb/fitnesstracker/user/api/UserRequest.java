package pl.wsb.fitnesstracker.user.api;

import java.time.LocalDate;

/**
 * DTO representing input request to create or update a user.
 *
 * @param firstName user's first name
 * @param lastName  user's last name
 * @param birthdate user's birthdate
 * @param email     user's email address
 */
public record UserRequest(String firstName, String lastName, LocalDate birthdate, String email) {}
