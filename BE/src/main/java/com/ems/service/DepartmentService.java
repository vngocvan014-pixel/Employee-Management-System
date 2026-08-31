package com.ems.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ems.dto.DepartmentDTO;
import com.ems.repository.DepartmentRepository;

@Service
public class DepartmentService {

    private final DepartmentRepository departmentRepository;

    public DepartmentService(
            DepartmentRepository departmentRepository) {

        this.departmentRepository =
                departmentRepository;
    }

    public List<DepartmentDTO> getAllDepartments() {

        return departmentRepository
                .findAllDepartments();
    }

    public DepartmentDTO getDepartmentById(Long id) {

        return departmentRepository
                .findDepartmentById(id);
    }

    public DepartmentDTO createDepartment(
            DepartmentDTO department) {

        departmentRepository
                .insertDepartment(department);

        return departmentRepository
                .findAllDepartments()
                .stream()
                .filter(d ->
                        d.getDepartmentCode()
                                .equals(
                                        department.getDepartmentCode()
                                )
                )
                .findFirst()
                .orElse(null);
    }

    public DepartmentDTO updateDepartment(
            Long id,
            DepartmentDTO department) {

        DepartmentDTO existing =
                departmentRepository
                        .findDepartmentById(id);

        if (existing == null) {
            return null;
        }

        int result =
                departmentRepository.updateDepartment(
                        id,
                        department
                );

        if (result == 0) {
            return null;
        }

        return departmentRepository
                .findDepartmentById(id);
    }

    public boolean deleteDepartment(Long id) {

        int result =
                departmentRepository
                        .deleteDepartment(id);

        return result > 0;
    }
}