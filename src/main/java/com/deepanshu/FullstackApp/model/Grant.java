package com.deepanshu.FullstackApp.model;


import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class Grant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nonprofitLegalName;
    private String grantSubmissionName;
    private String stage;
    private String foundationOwner;
    private BigDecimal requestedAmount;
    private BigDecimal awardedAmount;
    @Enumerated(EnumType.STRING)  // Store enum as string
    private GrantType grantType;
    private List<String> tags;
    private Date durationStart;
    private Date durationEnd;
    private String additionalFileFolderPath;  // Assuming a date here, adjust if text field
    private String grantSubmissionId;

    public enum GrantType {
        OPERATING_GRANT,
        PROJECT_GRANT
    }
}
