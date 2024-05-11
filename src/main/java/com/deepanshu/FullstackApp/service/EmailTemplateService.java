package com.deepanshu.FullstackApp.service;

import com.deepanshu.FullstackApp.model.EmailTemplate;
import com.deepanshu.FullstackApp.repo.EmailTemplateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EmailTemplateService {

    @Autowired
    private EmailTemplateRepository emailTemplateRepository;

    public EmailTemplate updateEmailTemplate(Long id, EmailTemplate updatedTemplate) {
        Optional<EmailTemplate> existingTemplate = emailTemplateRepository.findById(id);
        if (existingTemplate.isPresent()) {
            EmailTemplate changedTemplate = existingTemplate.get();
            changedTemplate.setSubject(updatedTemplate.getSubject());
            changedTemplate.setContent(updatedTemplate.getContent());

            return emailTemplateRepository.save(changedTemplate);
        }
        return null;
    }
}