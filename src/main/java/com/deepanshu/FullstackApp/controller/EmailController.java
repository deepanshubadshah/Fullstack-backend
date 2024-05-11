package com.deepanshu.FullstackApp.controller;


import com.deepanshu.FullstackApp.model.EmailTemplate;
import com.deepanshu.FullstackApp.model.SendEmailRequest;
import com.deepanshu.FullstackApp.model.SentEmail;
import com.deepanshu.FullstackApp.repo.SentEmailRepository;
import com.deepanshu.FullstackApp.service.EmailService;
import com.deepanshu.FullstackApp.service.NonprofitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@CrossOrigin("http://localhost:3000")
@RequestMapping("/emails")
public class EmailController {

    @Autowired
    private NonprofitService nonprofitService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private SentEmailRepository sentEmailRepository;

    @PostMapping("/send-to-nonprofits")
    public ResponseEntity<Long> sendEmailsToNonprofits(@RequestBody SendEmailRequest request) {
        try {
            List<Long> nonprofitIds = request.getNonprofitIds();
            String foundationEmail = request.getFoundationEmail();
            Long emailTemplateId = request.getEmailTemplateId();
            SentEmail sentEmail = emailService.sendEmails(nonprofitIds, foundationEmail, emailTemplateId);
            return new ResponseEntity<>(sentEmail != null ? sentEmail.getId() : null, HttpStatus.CREATED);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public ResponseEntity<List<SentEmail>> getAllSentEmails() {
        try {
            List<SentEmail> sentEmails = sentEmailRepository.findAll();
            return new ResponseEntity<>(sentEmails, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<SentEmail> getEmailById(@PathVariable Long id) {
        Optional<SentEmail> sentEmail = sentEmailRepository.findById(id);
        if (sentEmail.isPresent()) {
            return new ResponseEntity<>(sentEmail.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
