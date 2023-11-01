package com.inn.cafe.serviceImpl;

import com.inn.cafe.JWT.JwtFilter;
import com.inn.cafe.POJO.Bill;
import com.inn.cafe.constents.CafeConstants;
import com.inn.cafe.dao.BillDao;
import com.inn.cafe.service.BillService;
import com.inn.cafe.utils.CafeUtils;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import lombok.extern.slf4j.Slf4j;
import org.apache.pdfbox.io.IOUtils;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.List;
import java.util.stream.Stream;

@Slf4j
@Service
public class BillServiceImpl implements BillService {

    @Autowired
    JwtFilter jwtFilter;

    @Autowired
    BillDao billDao;

    /**
     * Generates a PDF report based on the provided request data and saves it to a file.
     *
     * @param requestMap A Map containing request data, including "name," "contactNumber," "email," "paymentMethod," "productDetails," "totalAmount," and optionally "uuid" and "isGenerate."
     * @return A ResponseEntity with a success message and the generated PDF's UUID if the report is generated and saved successfully,
     *         a BAD_REQUEST response if the request data is incomplete or invalid,
     *         or an error response if an exception occurs during the process.
     */
    @Override
    public ResponseEntity<String> generateReport(Map<String, Object> requestMap) {
        log.info("Inside GenerateReport{}");
        System.out.println(requestMap.get("uuid"));
        try {
            String fileName;
            if (validateRequestMap(requestMap)) {
                if (requestMap.containsKey("isGenerate") && !(Boolean) requestMap.get("isGenerate")) {
                    fileName = (String) requestMap.get("uuid");
                    System.out.println("File Name===="+fileName);
                } else {
                    System.out.println("uuid from else block"+requestMap.get("uuid"));
                    fileName = CafeUtils.getUUID();
                    requestMap.put("uuid", fileName);
                    insertBill(requestMap);
                }
                String data = "Name :" + requestMap.get("name") + "\n" + "Contact Number :" + requestMap.get("contactNumber") +
                        "\n" + "Email :" + requestMap.get("email") + "\n" + "Payment Method :" + requestMap.get("paymentMethod");

                Document document = new Document();
                log.info("Pdf Name--- : " + fileName);
                PdfWriter.getInstance(document, new FileOutputStream(CafeConstants.STORE_LOCATION + "\\" + fileName + ".pdf"));

                document.open();
                setRectangularInPdf(document);

                Paragraph chunk = new Paragraph("Cafe Management System", getFont("Header"));
                chunk.setAlignment(Element.ALIGN_CENTER);
                document.add(chunk);

                Paragraph paragraph = new Paragraph(data + "\n \n", getFont("Data"));
                document.add(paragraph);

                PdfPTable table = new PdfPTable(5);
                table.setWidthPercentage(100);
                addTableHeader(table);

                JSONArray jsonArray = CafeUtils.getJSONArrayFromString((String) requestMap.get("productDetails"));
                for (int i = 0; i < jsonArray.length(); i++) {
                    addRows(table, CafeUtils.getMapFromJSON(jsonArray.getString(i)));
                }
                document.add(table);

                Paragraph footer = new Paragraph("Total : " + requestMap.get("totalAmount") + "\n"
                        + "Thank you for visiting. Please visit again!!", getFont("Data"));

                document.add(footer);
                document.close();
                return new ResponseEntity<>("{\"uuId\":\"" + fileName + "\"}", HttpStatus.OK);


            }
            return CafeUtils.getResponseEntity("Required Data Not found", HttpStatus.BAD_REQUEST);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return CafeUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }


    /**
     * Adds a row of data to a PdfPTable in a PDF document.
     *
     * @param table The PdfPTable to which the data row will be added.
     * @param data A Map containing data to be added to the row, including "name," "category," "quantity," "price," and "total."
     */
    private void addRows(PdfPTable table, Map<String, Object> data) {
        log.info("Inside addRows");
        table.addCell((String) data.get("name"));
        table.addCell((String) data.get("category"));
        table.addCell((String) data.get("quantity"));
        table.addCell(Double.toString((Double) data.get("price")));
        table.addCell(Double.toString((Double) data.get("total")));
    }

    /**
     * Adds a header row to a PdfPTable in a PDF document.
     *
     * @param table The PdfPTable to which the header row will be added.
     */
    private void addTableHeader(PdfPTable table) {

        log.info("Inside addTableHeader{}");

        Stream.of("Name", "Category", "Quantity", "Price", "Sub Total")
                .forEach(columnTitle -> {
                    PdfPCell header = new PdfPCell();
                    header.setBackgroundColor(BaseColor.LIGHT_GRAY);
                    header.setBorderWidth(2);
                    header.setPhrase(new Phrase(columnTitle));
                    header.setBackgroundColor(BaseColor.YELLOW);
                    header.setHorizontalAlignment(Element.ALIGN_CENTER);
                    header.setVerticalAlignment(Element.ALIGN_CENTER);
                    table.addCell(header);
                });

    }

    /**
     * Retrieves a Font based on the specified font type.
     *
     * @param type A string indicating the desired font type, which can be "Header" or "Data."
     * @return A Font object with the specified font style and size, or a default Font if the type is not recognized.
     */
    private Font getFont(String type) {
        log.info("Inside getFont{}");
        switch (type) {
            case "Header":
                Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLDOBLIQUE, 18, BaseColor.BLACK);
                headerFont.setStyle(Font.BOLD);
                return headerFont;
            case "Data":
                Font dataFont = FontFactory.getFont(FontFactory.TIMES_ROMAN, 11, BaseColor.BLACK);
                dataFont.setStyle(Font.BOLD);
                return dataFont;
            default:
                return new Font();
        }

    }

    /**
     * Sets a rectangular border in the PDF document.
     *
     * @param document The PDF document to which the rectangular border will be added.
     * @throws DocumentException if there is an issue while setting the rectangular border.
     */
    private void setRectangularInPdf(Document document) throws DocumentException {
        log.info("set setRectangularInPdf");
        Rectangle rectangle = new Rectangle(577, 825, 18, 15);
        rectangle.enableBorderSide(1);
        rectangle.enableBorderSide(2);
        rectangle.enableBorderSide(4);
        rectangle.enableBorderSide(8);
        rectangle.setBorderColor(BaseColor.BLACK);
        rectangle.setBorderWidth(1);
        document.add(rectangle);

    }

    /**
     * Validates a request map to ensure it contains the required fields for processing.
     *
     * @param requestMap A Map containing request data with the following keys:
     *                  - "name" (Name of the customer)
     *                  - "contactNumber" (Contact number of the customer)
     *                  - "email" (Email address of the customer)
     *                  - "paymentMethod" (Payment method used)
     *                  - "productDetails" (Details of products ordered)
     *                  - "totalAmount" (Total amount of the bill)
     * @return true if the requestMap contains all the required keys, false otherwise.
     */
    private boolean validateRequestMap(Map<String, Object> requestMap) {
        return requestMap.containsKey("name") && requestMap.containsKey("contactNumber")
                && requestMap.containsKey("email") && requestMap.containsKey("paymentMethod")
                && requestMap.containsKey("productDetails") && requestMap.containsKey("totalAmount");
    }

    /**
     * Inserts a new bill record into the database based on the provided request data.
     *
     * @param requestMap A Map containing request data, including "uuid," "name," "email," "contactNumber," "paymentMethod," "totalAmount," and "productDetails."
     * @throws Exception if an error occurs during the insertion process.
     */
    private void insertBill(Map<String, Object> requestMap) {
        try {
            Bill bill = new Bill();
            bill.setUuid((String) requestMap.get("uuid"));
            bill.setName((String) requestMap.get("name"));
            bill.setEmail((String) requestMap.get("email"));
            bill.setContactNumber((String) requestMap.get("contactNumber"));
            bill.setPaymentMethod((String) requestMap.get("paymentMethod"));
            bill.setTotal(Integer.parseInt((String) requestMap.get("totalAmount")));
            bill.setProductDetails((String) requestMap.get("productDetails"));
            bill.setCreatedBy(jwtFilter.getCurrentUser());
            billDao.save(bill);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Retrieves a list of bills based on the user's role (admin or non-admin).
     *
     * @return A ResponseEntity containing a list of Bill objects if the user is an admin,
     *         or a list of bills associated with the current user if not an admin.
     */
    @Override
    public ResponseEntity<List<Bill>> getBills() {
        List<Bill> list = new ArrayList<>();
        if (jwtFilter.isAdmin()) {
            list = billDao.getAllBills();
        } else {
            list = billDao.getBillByUserName(jwtFilter.getCurrentUser());
        }
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    /**
     * Retrieves a PDF file based on a UUID or generates it if it doesn't exist.
     *
     * @param requestMap A Map containing request data, including the "uuid" for the PDF.
     * @return A ResponseEntity with the PDF file's content if found or generated successfully,
     *         a BAD_REQUEST response if the UUID is missing or the requestMap is not valid,
     *         or an error response if an exception occurs during the process.
     */
    @Override
    public ResponseEntity<byte[]> getPdf(Map<String, Object> requestMap) {
        log.info("Inside getPdf : requestMap{}" + requestMap);
        System.out.println(requestMap.get("uuid"));
        try {
            byte[] byteArray = new byte[0];
            if (!requestMap.containsKey("uuid") && validateRequestMap(requestMap))
                return new ResponseEntity<>(byteArray, HttpStatus.BAD_REQUEST);
            String filePath = CafeConstants.STORE_LOCATION + "\\" + requestMap.get("uuid") + ".pdf";
            System.out.println("File path from get pdf"+filePath);
            if (CafeUtils.isFileExists(filePath)) {
                byteArray = getByteArray(filePath);
                return new ResponseEntity<>(byteArray, HttpStatus.OK);
            } else {
                requestMap.put("isGenerate", false);
                generateReport(requestMap);
                byteArray = getByteArray(filePath);
                return new ResponseEntity<>(byteArray, HttpStatus.OK);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }


    /**
     * Reads the contents of a file and returns it as a byte array.
     *
     * @param filePath The path to the file to be read.
     * @return A byte array containing the file's content.
     * @throws Exception if there are any errors during file reading.
     */
    private byte[] getByteArray(String filePath) throws Exception {
        Path path = Paths.get(filePath);
        return Files.readAllBytes(path);
    }

    /**
     * Deletes a bill by its unique identifier (ID).
     *
     * @param id The ID of the bill to be deleted.
     * @return A ResponseEntity with a success message if the bill is deleted successfully,
     *         a message indicating that the bill ID doesn't exist, or an error message if
     *         an exception occurs during the process.
     */
    @Override
    public ResponseEntity<String> deleteBill(Integer id) {
        try {
            Optional optional = billDao.findById(id);
            if (!optional.isEmpty()) {
                billDao.deleteById(id);
                return CafeUtils.getResponseEntity("Bill deleted successfully", HttpStatus.OK);
            }
            return CafeUtils.getResponseEntity("Bill id doesn't exists", HttpStatus.OK);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return CafeUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
