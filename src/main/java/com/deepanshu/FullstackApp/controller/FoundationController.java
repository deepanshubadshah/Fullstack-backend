package com.deepanshu.FullstackApp.controller;

import com.deepanshu.FullstackApp.model.Foundation;
import com.deepanshu.FullstackApp.repo.FoundationRepository;
import com.deepanshu.FullstackApp.service.FoundationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin("http://localhost:3000")
@RequestMapping("/foundations")
public class FoundationController {

    @Autowired
    private FoundationRepository foundationRepository;

    @Autowired
    private FoundationService foundationService;

    @GetMapping
    public ResponseEntity<List<Foundation>> getAllFoundations() {
        try {
            List<Foundation> foundationList = new ArrayList<>(foundationRepository.findAll());

            if (foundationList.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(foundationList, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Foundation> getFoundationById(@PathVariable Long id) {
        Optional<Foundation> foundation = foundationRepository.findById(id);
        if (foundation.isPresent()) {
            return new ResponseEntity<>(foundation.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<Foundation> createFoundation(@RequestBody Foundation foundation) {
        try {
            Foundation foundationObj = foundationRepository.save(foundation);
            return new ResponseEntity<>(foundationObj, HttpStatus.CREATED);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Foundation> updateFoundation(@PathVariable Long id, @RequestBody Foundation updatedFoundation) {

        Foundation updated = foundationService.updateFoundation(id, updatedFoundation);
        if (updated == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteFoundation(@PathVariable Long id) {
        foundationRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
