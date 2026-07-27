package com.nexusai.backend.service;

import com.nexusai.backend.dto.DepartmentRequest;
import com.nexusai.backend.dto.DepartmentResponse;
import com.nexusai.backend.entity.Department;
import com.nexusai.backend.exception.DuplicateResourceException;
import com.nexusai.backend.exception.ResourceNotFoundException;
import com.nexusai.backend.repository.DepartmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DepartmentService {

    private final DepartmentRepository departmentRepository;

    // ==========================
    // Create Department
    // ==========================
    public DepartmentResponse createDepartment(DepartmentRequest request) {

        if (departmentRepository.existsByDepartmentCode(request.getDepartmentCode())) {
            throw new DuplicateResourceException("Department code already exists");
        }

        if (departmentRepository.existsByName(request.getName())) {
            throw new DuplicateResourceException("Department name already exists");
        }

        Department department = Department.builder()
                .departmentCode(request.getDepartmentCode())
                .name(request.getName())
                .description(request.getDescription())
                .location(request.getLocation())
                .build();

        return mapToResponse(departmentRepository.save(department));
    }

    // ==========================
    // Get All Departments
    // ==========================
    public Page<DepartmentResponse> getAllDepartments(
            int page,
            int size,
            String sortBy,
            String direction) {

        Sort sort = direction.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);

        return departmentRepository.findAll(pageable)
                .map(this::mapToResponse);
    }

    // ==========================
    // Get Department By Id
    // ==========================
    public DepartmentResponse getDepartmentById(Long id) {

        Department department = departmentRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Department not found with id: " + id));

        return mapToResponse(department);
    }

    // ==========================
    // Update Department
    // ==========================
    public DepartmentResponse updateDepartment(Long id, DepartmentRequest request) {

        Department department = departmentRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Department not found with id: " + id));

        department.setDepartmentCode(request.getDepartmentCode());
        department.setName(request.getName());
        department.setDescription(request.getDescription());
        department.setLocation(request.getLocation());

        return mapToResponse(departmentRepository.save(department));
    }

    // ==========================
    // Delete Department
    // ==========================
    public void deleteDepartment(Long id) {

        Department department = departmentRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Department not found with id: " + id));

        departmentRepository.delete(department);
    }

    // ==========================
    // Search Departments
    // ==========================
    public Page<DepartmentResponse> searchDepartments(
            String keyword,
            int page,
            int size,
            String sortBy,
            String direction) {

        Sort sort = direction.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);

        return departmentRepository
                .findByNameContainingIgnoreCaseOrDepartmentCodeContainingIgnoreCase(
                        keyword,
                        keyword,
                        pageable
                )
                .map(this::mapToResponse);
    }

    // ==========================
    // Mapper
    // ==========================
    private DepartmentResponse mapToResponse(Department department) {

        return DepartmentResponse.builder()
                .id(department.getId())
                .departmentCode(department.getDepartmentCode())
                .name(department.getName())
                .description(department.getDescription())
                .location(department.getLocation())
                .createdAt(department.getCreatedAt())
                .updatedAt(department.getUpdatedAt())
                .build();
    }
}