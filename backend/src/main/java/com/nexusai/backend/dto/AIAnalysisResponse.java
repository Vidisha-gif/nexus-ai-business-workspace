package com.nexusai.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AIAnalysisResponse {

    private Long employeeId;
    private String employeeName;
    private String department;
    private String designation;
    private Double salary;

    private int performanceScore;
    private String riskLevel;
    private boolean promotionEligible;
    private String salaryCategory;

    private List<String> recommendations;
}