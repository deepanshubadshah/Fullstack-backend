package com.deepanshu.FullstackApp.model;


import jakarta.persistence.Entity;
import lombok.*;

import java.util.List;


@Setter
@Getter
public class SendEmailRequest {

    private List<Long> nonprofitIds;
    private String foundationEmail;
    private Long emailTemplateId;
    private String bccTo;
    private String ccTo;
}
