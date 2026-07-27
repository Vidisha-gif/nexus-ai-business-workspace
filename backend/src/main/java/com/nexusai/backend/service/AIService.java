package com.nexusai.backend.service;

import com.nexusai.backend.dto.AIAnalysisResponse;
import com.nexusai.backend.entity.Employee;
import com.nexusai.backend.repository.EmployeeRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AIService {

    private final EmployeeRepository employeeRepository;

    public AIAnalysisResponse analyzeEmployee(Long employeeId) {

        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() ->
                        new EntityNotFoundException("Employee not found with id: " + employeeId));

        int salaryScore;

        if (employee.getSalary() >= 100000)
            salaryScore = 100;
        else if (employee.getSalary() >= 80000)
            salaryScore = 85;
        else if (employee.getSalary() >= 60000)
            salaryScore = 70;
        else if (employee.getSalary() >= 40000)
            salaryScore = 55;
        else
            salaryScore = 40;

        int departmentBonus = switch (employee.getDepartment().toUpperCase()) {
            case "IT" -> 10;
            case "FINANCE" -> 8;
            case "HR" -> 6;
            default -> 5;
        };

        int designationBonus = switch (employee.getDesignation().toUpperCase()) {
            case "MANAGER" -> 15;
            case "SENIOR SOFTWARE ENGINEER" -> 12;
            case "SOFTWARE ENGINEER" -> 10;
            case "EMPLOYEE" -> 8;
            case "INTERN" -> 3;
            default -> 5;
        };

        int performanceScore = Math.min(100,
                salaryScore + departmentBonus + designationBonus);

        String riskLevel;

        if (performanceScore >= 85)
            riskLevel = "LOW";
        else if (performanceScore >= 70)
            riskLevel = "MEDIUM";
        else
            riskLevel = "HIGH";

        boolean promotionEligible = performanceScore >= 85;

        String salaryCategory;

        if (employee.getSalary() >= 100000)
            salaryCategory = "EXCELLENT";
        else if (employee.getSalary() >= 70000)
            salaryCategory = "GOOD";
        else if (employee.getSalary() >= 50000)
            salaryCategory = "AVERAGE";
        else
            salaryCategory = "NEEDS IMPROVEMENT";

        List<String> recommendations = new ArrayList<>();

        if (promotionEligible) {
            recommendations.add("Eligible for Promotion");
            recommendations.add("Consider Salary Increment");
            recommendations.add("Assign Leadership Responsibilities");
        } else if (performanceScore >= 70) {
            recommendations.add("Skill Improvement Recommended");
            recommendations.add("Attend Technical Training");
            recommendations.add("Quarterly Performance Review");
        } else {
            recommendations.add("Immediate Mentorship Required");
            recommendations.add("Weekly Performance Monitoring");
            recommendations.add("Mandatory Training Program");
        }

        return AIAnalysisResponse.builder()
                .employeeId(employee.getId())
                .employeeName(employee.getFirstName() + " " + employee.getLastName())
                .department(employee.getDepartment())
                .designation(employee.getDesignation())
                .salary(employee.getSalary())
                .performanceScore(performanceScore)
                .riskLevel(riskLevel)
                .promotionEligible(promotionEligible)
                .salaryCategory(salaryCategory)
                .recommendations(recommendations)
                .build();
    }
}