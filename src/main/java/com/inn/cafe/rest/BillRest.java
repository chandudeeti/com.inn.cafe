package com.inn.cafe.rest;

import com.inn.cafe.POJO.Bill;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * REST API interface for managing bills.
 */
@RequestMapping(path = "/bill")
public interface BillRest {

    /**
     * Generates a bill report based on the provided request map.
     *
     * @param requestMap A map containing data needed for report generation.
     * @return A ResponseEntity with a string response.
     */
    @PostMapping(path = "/generateReport")
    ResponseEntity<String> generateReport(@RequestBody Map<String, Object> requestMap);

    /**
     * Retrieves a list of bills.
     *
     * @return A ResponseEntity containing a list of Bill objects.
     */
    @GetMapping(path = "/getBills")
    ResponseEntity<List<Bill>> getBills();

    /**
     * Generates a PDF based on the provided request map.
     *
     * @param requestMap A map containing data needed for PDF generation.
     * @return A ResponseEntity with a byte array containing the PDF data.
     */
    @PostMapping(path = "/getPdf")
    ResponseEntity<byte[]> getPdf(@RequestBody Map<String, Object> requestMap);

    /**
     * Deletes a bill by its ID.
     *
     * @param id The ID of the bill to delete.
     * @return A ResponseEntity with a string response.
     */
    @PostMapping(path = "/delete/{id}")
    ResponseEntity<String> deleteBill(@PathVariable Integer id);
}

