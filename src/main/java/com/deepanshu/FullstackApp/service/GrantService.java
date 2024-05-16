package com.deepanshu.FullstackApp.service;

import com.deepanshu.FullstackApp.model.Grant;
import com.deepanshu.FullstackApp.repo.GrantRepository;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import com.opencsv.exceptions.CsvValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import com.amazonaws.services.s3.AmazonS3;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import org.springframework.data.domain.Pageable;


@Service
public class GrantService {

    @Autowired
    private GrantRepository grantRepository;

//    @Autowired
//    private AmazonS3 s3Client;

    public void loadGrantsFromCSV(String filePath, InputStream inputStream) throws IOException {
        List<Grant> grants = parseGrantsFromCSV(filePath, inputStream);
        grantRepository.saveAll(grants);  // Save grants to H2 database

        // Upload CSV file to S3 (after successful processing)
//        uploadCsvToS3(csvFile);
    }

    private List<Grant> parseGrantsFromCSV(String filePath, InputStream inputStream) throws IOException {
        List<Grant> grants = new ArrayList<>();
        try (CSVReader reader = new CSVReader(new InputStreamReader(inputStream))) {
            String[] header = reader.readNext(); // Skip header row
            List<String[]> rows = reader.readAll();
            for (String[] row : rows) {
                Grant grant = new Grant();

                grant.setNonprofitLegalName(row[0] != null ? row[0] : "");
                grant.setGrantSubmissionName(row[1] != null ? row[1] : "");
                grant.setStage(row[2] != null ? row[2] : "");
                grant.setFoundationOwner(row[3] != null ? row[3] : "");
                grant.setRequestedAmount(parseAmount(row[4]));

                String awardedAmount = row[5] != null ? row[5] : null;
                grant.setAwardedAmount(awardedAmount != null ? parseAmount(awardedAmount) : null);

                grant.setGrantType(row[6] != null ? Grant.GrantType.valueOf(row[6]) : null);
                grant.setTags(row[7] != null ? Arrays.asList(row[7].split(",")) : Collections.emptyList());
                grant.setDurationStart(parseDate(row[8]));
                grant.setDurationEnd(parseDate(row[9]));
                grant.setAdditionalFileFolderPath(row[10] != null ? row[10] : null);  // Assuming date, adjust if text field
                grant.setGrantSubmissionId(row[11] != null ? row[11] : "");

                grants.add(grant);
            }
        } catch (ParseException | CsvException err) {
            throw new RuntimeException("An error occurred while reading CSV", err);
        }
        return grants;
    }

//    private void uploadCsvToS3(MultipartFile csvFile) throws IOException {
//        String fileName = csvFile.getOriginalFilename();
//        byte[] content = csvFile.getBytes();
//        PutObjectRequest request = new PutObjectRequest("grants-bucket", fileName, new ByteArrayInputStream(content));
//        s3Client.putObject(request);
//    }

    public Page<Grant> getGrants(int pageNumber, String sortField, String sortOrder) {
        Sort sort;
        if (sortOrder.equalsIgnoreCase("asc")) {
            sort = Sort.by(sortField).ascending();
        } else {
            sort = Sort.by(sortField).descending();
        }
        Pageable pageable = PageRequest.of(pageNumber - 1, 10, sort.and(Sort.by("id").ascending()));
        Page<Grant> grants = grantRepository.findAll(pageable);
        int totalPages = grants.getTotalPages();
        return grants;
    }

    private BigDecimal parseAmount(String value) {
        return new BigDecimal(value.substring(1).replaceAll(",", ""));
    }

    private String formatDate(Date date) {
        SimpleDateFormat desiredFormat = new SimpleDateFormat("yyyy-MM-dd");  // Adjust format as needed
        return desiredFormat.format(date);
    }

    private Date parseDate(String value) throws ParseException {

        SimpleDateFormat format1 = new SimpleDateFormat("dd-MM-yyyy"); // Day-Month-Year
        SimpleDateFormat format2 = new SimpleDateFormat("MM/dd/yyyy"); // Month-Day-Year

        try {
            return format1.parse(value);
        } catch (ParseException e) {
            try {
                return format2.parse(value);
            } catch (ParseException e2) {
                throw new ParseException("Unrecognized date format: " + value, 0);
            }
        }
    }

}
