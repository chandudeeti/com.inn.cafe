package com.inn.cafe.dao;

import com.inn.cafe.POJO.User;
import com.inn.cafe.wrapper.UserWrapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

public interface UserDao extends JpaRepository<User, Integer> {

    /**
     * Retrieves a user by their email ID.
     *
     * @param email The email ID of the user to retrieve.
     * @return The user associated with the specified email ID, or null if not found.
     */
    User findByEmailId(@Param("email") String email);

    /**
     * Retrieves a list of user wrapper objects representing all users.
     *
     * @return A list of user wrapper objects representing all users.
     */
    List<UserWrapper> getAllUsers();

    /**
     * Retrieves a list of email addresses of all admin users.
     *
     * @return A list of email addresses of admin users.
     */
    List<String> getAllAdmin();

    /**
     * Updates the status of a user with the specified ID.
     *
     * @param status The new status to set for the user.
     * @param id The ID of the user to update.
     * @return The number of updated records (typically 1 if the update was successful).
     */
    @Transactional
    @Modifying
    Integer updateStatus(@Param("status") String status, @Param("id") Integer id);

    /**
     * Retrieves a user by their email address.
     *
     * @param email The email address of the user to retrieve.
     * @return The user associated with the specified email address, or null if not found.
     */
    User findByEmail(String email);
}