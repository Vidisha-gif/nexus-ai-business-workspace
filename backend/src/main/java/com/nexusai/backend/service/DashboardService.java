package com.nexusai.backend.service;

import com.nexusai.backend.dto.DashboardResponse;
import com.nexusai.backend.entity.Employee;
import com.nexusai.backend.repository.DepartmentRepository;
import com.nexusai.backend.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;

    public DashboardResponse getDashboardStats() {

        List<Employee> employees = employeeRepository.findAll();

        long totalEmployees = employees.size();
        long totalDepartments = departmentRepository.count();

        double averageSalary = employees.stream()
                .mapToDouble(Employee::getSalary)
                .average()
                .orElse(0);

        double highestSalary = employees.stream()
                .map(Employee::getSalary)
                .max(Comparator.naturalOrder())
                .orElse(0.0);

        double lowestSalary = employees.stream()
                .map(Employee::getSalary)
                .min(Comparator.naturalOrder())
                .orElse(0.0);

        long promotionEligible = employees.stream()
                .filter(e -> e.getSalary() >= 80000)
                .count();

        long highRisk = employees.stream()
                .filter(e -> e.getSalary() < 50000)
                .count();

        return DashboardResponse.builder()
                .totalEmployees(totalEmployees)
                .totalDepartments(totalDepartments)
                .averageSalary(averageSalary)
                .highestSalary(highestSalary)
                .lowestSalary(lowestSalary)
                .promotionEligibleEmployees(promotionEligible)
                .highRiskEmployees(highRisk)
                .build();
    }
}