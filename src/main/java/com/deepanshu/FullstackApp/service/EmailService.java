package com.deepanshu.FullstackApp.service;

import com.deepanshu.FullstackApp.model.EmailTemplate;
import com.deepanshu.FullstackApp.model.Nonprofit;
import com.deepanshu.FullstackApp.model.SentEmail;
import com.deepanshu.FullstackApp.repo.EmailTemplateRepository;
import com.deepanshu.FullstackApp.repo.NonprofitRepository;
import com.deepanshu.FullstackApp.repo.SentEmailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@Service
public class EmailService {
    @Autowired
    private EmailTemplateRepository emailTemplateRepository;

    @Autowired
    private NonprofitRepository nonprofitRepository;

    @Autowired
    private SentEmailRepository sentEmailRepository;


    public SentEmail sendEmails(List<Long> nonprofitIds, String foundationEmail, Long emailTemplateId, String ccTo, String bccTo) {
        try {
            Optional<EmailTemplate> emailTemplateOptional = emailTemplateRepository.findById(emailTemplateId);
            if (!emailTemplateOptional.isPresent()) {
                throw new RuntimeException("Email template with ID " + emailTemplateId + " not found");
            }

            EmailTemplate emailTemplate = emailTemplateOptional.get();
            String baseTemplate = emailTemplate.getContent();
            String templateSubject = emailTemplate.getSubject();

            List<SentEmail> sentEmails = new ArrayList<>();
            for (Long nonprofitId : nonprofitIds) {
                Optional<Nonprofit> nonprofitOptional = nonprofitRepository.findById(nonprofitId);
                if (nonprofitOptional.isPresent()) {
                    Nonprofit nonprofit = nonprofitOptional.get();

                    String name = String.valueOf(nonprofit.getName());
                    String address = String.valueOf(nonprofit.getAddress());
                    Map<String, String> valuesMap = Map.of("name", name, "address", address);
                    String message = replacePlaceholders(baseTemplate, valuesMap);

                    SentEmail sentEmail = new SentEmail();
                    sentEmail.setRecipientEmail(nonprofit.getEmail());
                    sentEmail.setFoundationEmail(foundationEmail);
                    sentEmail.setCcTo(ccTo);
                    sentEmail.setBccTo(bccTo);
                    sentEmail.setSubject(templateSubject);
                    sentEmail.setMessage(message);
                    sentEmail.setSentDateTime(LocalDateTime.now());
                    sentEmails.add(sentEmail);
                } else {
                    System.out.println("Nonprofit with ID " + nonprofitId + " not found. Skipping email.");
                }
            }

            sentEmailRepository.saveAll(sentEmails);
            return sentEmails.isEmpty() ? null : sentEmails.get(0);
        } catch (DataAccessException ex) {
            throw new RuntimeException("An error occurred while saving sent emails", ex);
        } catch (Exception ex) {
            throw new RuntimeException("An unexpected error occurred while sending emails", ex);
        }
    }



    public static String replacePlaceholders(String template, Map<String, String> values) {
        StringBuilder messageBuilder = new StringBuilder();
        int startIndex = 0;
        int placeholderEndIndex;

        while ((placeholderEndIndex = template.indexOf("%{", startIndex)) != -1) {
            messageBuilder.append(template.substring(startIndex, placeholderEndIndex));
            startIndex = placeholderEndIndex + 2;

            int placeholderNameEndIndex = template.indexOf("}", startIndex);
            String placeholderName = template.substring(startIndex, placeholderNameEndIndex);
            String replacementValue = values.get(placeholderName);

            if (replacementValue != null) {
                messageBuilder.append(replacementValue);
            } else {
                messageBuilder.append("%{");
                messageBuilder.append(placeholderName);
                messageBuilder.append("}");
            }

            startIndex = placeholderNameEndIndex + 1;
        }

        messageBuilder.append(template.substring(startIndex));
        return messageBuilder.toString();
    }
}
