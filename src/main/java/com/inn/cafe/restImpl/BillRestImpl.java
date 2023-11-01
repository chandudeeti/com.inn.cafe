package com.inn.cafe.restImpl;

import com.inn.cafe.POJO.Bill;
import com.inn.cafe.constents.CafeConstants;
import com.inn.cafe.rest.BillRest;
import com.inn.cafe.service.BillService;
import com.inn.cafe.utils.CafeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * Implementation of the BillRest interface for managing bills via REST endpoints.
 */
@RestController
public class BillRestImpl implements BillRest {

    @Autowired
    BillService billService;

    /**
     * Generates a bill report based on the provided request map.
     *
     * @param requestMap A map containing data needed for report generation.
     * @return A ResponseEntity with a string response indicating the result.
     */
    @Override
    public ResponseEntity<String> generateReport(Map<String, Object> requestMap) {
        try {
            return billService.generateReport(requestMap);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return CafeUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Retrieves a list of bills.
     *
     * @return A ResponseEntity containing a list of Bill objects.
     */
    @Override
    public ResponseEntity<List<Bill>> getBills() {
        try {
            return billService.getBills();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    /**
     * Generates a PDF based on the provided request map.
     *
     * @param requestMap A map containing data needed for PDF generation.
     * @return A ResponseEntity with a byte array containing the PDF data.
     */
    @Override
    public ResponseEntity<byte[]> getPdf(Map<String, Object> requestMap) {
        try {
            billService.getPdf(requestMap);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    /**
     * Deletes a bill by its ID.
     *
     * @param id The ID of the bill to delete.
     * @return A ResponseEntity with a string response indicating the result.
     */
    @Override
    public ResponseEntity<String> deleteBill(Integer id) {
        try {
            return billService.deleteBill(id);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return CafeUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
