package com.deepanshu.FullstackApp.controller;


import com.deepanshu.FullstackApp.model.Nonprofit;
import com.deepanshu.FullstackApp.repo.NonprofitRepository;
import com.deepanshu.FullstackApp.service.NonprofitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin("http://localhost:3000")
@RequestMapping("/nonprofits")
public class NonprofitController {

    @Autowired
    private NonprofitRepository nonprofitRepository;

    @Autowired
    private NonprofitService nonprofitService;

    @GetMapping
    public ResponseEntity<List<Nonprofit>> getAllNonprofits() {
        try {
            List<Nonprofit> nonprofitList = new ArrayList<>(nonprofitRepository.findAll());

            if (nonprofitList.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(nonprofitList, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Nonprofit> getNonprofitById(@PathVariable Long id) {
        Optional<Nonprofit> nonprofit =  nonprofitRepository.findById(id);
        if (nonprofit.isPresent()) {
            return new ResponseEntity<>(nonprofit.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<Nonprofit> createNonprofit(@RequestBody Nonprofit nonprofit) {
        try {
            Nonprofit nonprofitObj= nonprofitRepository.save(nonprofit);
            return new ResponseEntity<>(nonprofitObj, HttpStatus.CREATED);
        } catch(Exception ex){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Nonprofit> updateNonprofit(@PathVariable Long id, @RequestBody Nonprofit updatedNonprofit) {

        Nonprofit updated = nonprofitService.updateNonprofit(id, updatedNonprofit);
        if (updated == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteNonprofit(@PathVariable Long id) {
        nonprofitRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
