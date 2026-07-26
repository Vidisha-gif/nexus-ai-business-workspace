package com.nexusai.backend.controller;

import com.nexusai.backend.dto.EmployeeRequest;
import com.nexusai.backend.dto.EmployeeResponse;
import com.nexusai.backend.service.EmployeeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/employees")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    @PostMapping
    public ResponseEntity<EmployeeResponse> createEmployee(
            @Valid @RequestBody EmployeeRequest request) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(employeeService.createEmployee(request));
    }

    @GetMapping
    public ResponseEntity<Page<EmployeeResponse>> getAllEmployees(

            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String direction) {

        return ResponseEntity.ok(
                employeeService.getAllEmployees(page, size, sortBy, direction)
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeResponse> getEmployeeById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                employeeService.getEmployeeById(id)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmployeeResponse> updateEmployee(
            @PathVariable Long id,
            @Valid @RequestBody EmployeeRequest request) {

        return ResponseEntity.ok(
                employeeService.updateEmployee(id, request)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEmployee(
            @PathVariable Long id) {

        employeeService.deleteEmployee(id);

        return ResponseEntity.ok("Employee deleted successfully");
    }

    @GetMapping("/search")
    public ResponseEntity<Page<EmployeeResponse>> searchEmployees(

            @RequestParam String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String direction) {

        return ResponseEntity.ok(
                employeeService.searchEmployees(
                        keyword,
                        page,
                        size,
                        sortBy,
                        direction
                )
        );
    }
}