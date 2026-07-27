package com.nexusai.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DashboardResponse {

    private long totalEmployees;
    private long totalDepartments;

    private Double averageSalary;
    private Double highestSalary;
    private Double lowestSalary;

    private long promotionEligibleEmployees;
    private long highRiskEmployees;
}