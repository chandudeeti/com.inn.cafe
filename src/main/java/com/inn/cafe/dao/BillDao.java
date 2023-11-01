package com.inn.cafe.dao;

import com.inn.cafe.POJO.Bill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BillDao extends JpaRepository<Bill, Integer> {

    /**
     * Retrieves a list of all bills.
     *
     * @return A list of all bill entities.
     */
    List<Bill> getAllBills();

    /**
     * Retrieves a list of bills associated with a specific user by their username.
     *
     * @param currentUser The username of the user for whom bills are to be retrieved.
     * @return A list of bill entities associated with the specified user.
     */

    List<Bill> getBillByUserName( @Param("username") String currentUser);
}
