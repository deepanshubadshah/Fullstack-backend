package com.deepanshu.FullstackApp.controller;

import com.deepanshu.FullstackApp.model.EmailTemplate;
import com.deepanshu.FullstackApp.repo.EmailTemplateRepository;
import com.deepanshu.FullstackApp.service.EmailTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin("http://localhost:3000")
@RequestMapping("/email-templates")
public class EmailTemplateController {

    @Autowired
    private EmailTemplateRepository emailTemplateRepository;

    @Autowired
    private EmailTemplateService emailTemplateService;

    @GetMapping
    public ResponseEntity<List<EmailTemplate>> getAllEmailTemplates() {
        try {
            List<EmailTemplate> templateList = new ArrayList<>(emailTemplateRepository.findAll());

            if (templateList.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(templateList, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmailTemplate> getEmailTemplateById(@PathVariable Long id) {
        Optional<EmailTemplate> emailTemplate = emailTemplateRepository.findById(id);
        if (emailTemplate.isPresent()) {
            return new ResponseEntity<>(emailTemplate.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<EmailTemplate> createEmailTemplate(@RequestBody EmailTemplate emailTemplate) {
        try {
            EmailTemplate savedTemplate = emailTemplateRepository.save(emailTemplate);
            return new ResponseEntity<>(savedTemplate, HttpStatus.CREATED);
        } catch (Exception error) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmailTemplate> updateEmailTemplate(@PathVariable Long id, @RequestBody EmailTemplate updatedTemplate) {
        try {
            EmailTemplate updated = emailTemplateService.updateEmailTemplate(id, updatedTemplate);
            if (updated == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(updated, HttpStatus.OK);
        }catch (Exception error) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteEmailTemplate(@PathVariable Long id) {
        emailTemplateRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
