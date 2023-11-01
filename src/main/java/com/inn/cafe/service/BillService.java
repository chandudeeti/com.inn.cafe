package com.inn.cafe.service;

import com.inn.cafe.POJO.Bill;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;
/**
 * Service interface for managing bills.
 */
public interface BillService {

    /**
     * Generates a bill report based on the provided request map.
     *
     * @param requestMap A map containing data needed for report generation.
     * @return A ResponseEntity with a string response indicating the result.
     */
    ResponseEntity<String> generateReport(Map<String, Object> requestMap);

    /**
     * Retrieves a list of bills.
     *
     * @return A ResponseEntity containing a list of Bill objects.
     */
    ResponseEntity<List<Bill>> getBills();

    /**
     * Generates a PDF based on the provided request map.
     *
     * @param requestMap A map containing data needed for PDF generation.
     * @return A ResponseEntity with a byte array containing the PDF data.
     */
    ResponseEntity<byte[]> getPdf(Map<String, Object> requestMap);

    /**
     * Deletes a bill by its ID.
     *
     * @param id The ID of the bill to delete.
     * @return A ResponseEntity with a string response indicating the result.
     */
    ResponseEntity<String> deleteBill(Integer id);
}
