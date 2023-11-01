package com.inn.cafe.rest;

import com.inn.cafe.wrapper.UserWrapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
/**
 * REST API interface for managing users.
 */
@RequestMapping(path = "/user")
@CrossOrigin(origins = "http://localhost:4200")
public interface UserRest {

    /**
     * Registers a new user based on the provided request map.
     *
     * @param requestMap A map containing user registration data.
     * @return A ResponseEntity with a string response indicating the result of the registration.
     */
    @PostMapping(path = "/signup")
    ResponseEntity<String> signUp(@RequestBody(required = true) Map<String, String> requestMap);

    /**
     * Handles user login based on the provided request map.
     *
     * @param requestMap A map containing user login credentials.
     * @return A ResponseEntity with a string response indicating the result of the login.
     */
    @PostMapping(path = "/login")
    ResponseEntity<String> login(@RequestBody(required = true) Map<String, String> requestMap);

    /**
     * Retrieves a list of user data, typically represented as UserWrapper objects.
     *
     * @return A ResponseEntity containing a list of UserWrapper objects.
     */
    @GetMapping(path = "/get")
    ResponseEntity<List<UserWrapper>> getAllUsers();

    /**
     * Updates user information based on the provided request map.
     *
     * @param requestMap A map containing data for updating user information.
     * @return A ResponseEntity with a string response indicating the result of the update.
     */
    @PostMapping(path = "/update")
    ResponseEntity<String> update(@RequestBody(required = true) Map<String, String> requestMap);

    /**
     * Checks the validity of a user token.
     *
     * @return A ResponseEntity with a string response indicating the result of the token check.
     */
    @GetMapping(path = "/checkToken")
    ResponseEntity<String> checkToken();

    /**
     * Handles user password change based on the provided request map.
     *
     * @param requestMap A map containing data for changing the user's password.
     * @return A ResponseEntity with a string response indicating the result of the password change.
     */
    @PostMapping(path = "/changePassword")
    ResponseEntity<String> changePassword(@RequestBody(required = true) Map<String, String> requestMap);

    /**
     * Handles the process of resetting a forgotten password based on the provided request map.
     *
     * @param requestMap A map containing data for the password reset.
     * @return A ResponseEntity with a string response indicating the result of the password reset process.
     */
    @PostMapping(path = "/forgotPassword")
    ResponseEntity<String> forgotPassword(@RequestBody(required = true) Map<String, String> requestMap);
}
