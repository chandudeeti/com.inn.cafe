package com.inn.cafe.service;

import com.inn.cafe.wrapper.UserWrapper;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;
/**
 * Service interface for managing user-related operations.
 */
public interface UserService {

    /**
     * Signs up a new user based on the provided request map.
     *
     * @param requestMap A map containing data for user registration.
     * @return A ResponseEntity with a string response indicating the result of the signup process.
     */
    ResponseEntity<String> signUp(Map<String, String> requestMap);

    /**
     * Logs in a user based on the provided request map.
     *
     * @param requestMap A map containing user login credentials.
     * @return A ResponseEntity with a string response indicating the result of the login process.
     */
    ResponseEntity<String> login(Map<String, String> requestMap);

    /**
     * Retrieves a list of user data typically represented as UserWrapper objects.
     *
     * @return A ResponseEntity containing a list of UserWrapper objects.
     */
    ResponseEntity<List<UserWrapper>> getAllUsers();

    /**
     * Updates user information based on the provided request map.
     *
     * @param requestMap A map containing data for updating user details.
     * @return A ResponseEntity with a string response indicating the result of the update.
     */
    ResponseEntity<String> update(Map<String, String> requestMap);

    /**
     * Checks the validity of a user's token.
     *
     * @return A ResponseEntity with a string response indicating the result of the token check.
     */
    ResponseEntity<String> checkToken();

    /**
     * Changes a user's password based on the provided request map.
     *
     * @param requestMap A map containing data for changing the user's password.
     * @return A ResponseEntity with a string response indicating the result of the password change.
     */
    ResponseEntity<String> changePassword(Map<String, String> requestMap);

    /**
     * Initiates a password reset process based on the provided request map.
     *
     * @param requestMap A map containing data for initiating the password reset process.
     * @return A ResponseEntity with a string response indicating the result of the password reset request.
     */
    ResponseEntity<String> forgotPassword(Map<String, String> requestMap);
}
