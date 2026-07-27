package com.nexusai.backend.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.nexusai.backend.entity.Department;

public interface DepartmentRepository extends JpaRepository<Department, Long> {

    Optional<Department> findByDepartmentCode(String departmentCode);

    Optional<Department> findByName(String name);

    boolean existsByDepartmentCode(String departmentCode);

    boolean existsByName(String name);

    Page<Department> findByNameContainingIgnoreCaseOrDepartmentCodeContainingIgnoreCase(
            String name,
            String departmentCode,
            Pageable pageable
    );
}
