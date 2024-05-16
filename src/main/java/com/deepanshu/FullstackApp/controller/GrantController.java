package com.deepanshu.FullstackApp.controller;


import com.amazonaws.util.IOUtils;
import com.deepanshu.FullstackApp.model.Grant;
import com.deepanshu.FullstackApp.service.GrantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin("http://localhost:3000")
@RequestMapping("/grants")
public class GrantController {

    @Autowired
    private GrantService grantService;

    @PostMapping("/upload")
    public ResponseEntity<Void> uploadGrants(@RequestParam("file") MultipartFile csvFile) throws IOException {
        String fileName = csvFile.getOriginalFilename();
        grantService.loadGrantsFromCSV(fileName, csvFile.getInputStream());
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> getGrants(
            @RequestParam(value = "pageNumber", defaultValue = "1") int pageNumber,
            @RequestParam(value = "sortField", defaultValue = "id") String sortField,
            @RequestParam(value = "sortOrder", defaultValue = "asc") String sortOrder) {

        Page<Grant> grants = grantService.getGrants(pageNumber, sortField, sortOrder);
        List<Grant> grantList = grants.getContent();

        int totalPages = grants.getTotalPages();
        Map<String, Object> response = new HashMap<>();
        response.put("grants", grantList);
        response.put("totalPages", totalPages);

        return ResponseEntity.ok(response);
    }

}
