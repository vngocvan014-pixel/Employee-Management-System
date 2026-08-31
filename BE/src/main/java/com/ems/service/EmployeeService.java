package com.ems.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ems.dto.EmployeeDTO;
import com.ems.dto.PageResponseDTO;
import com.ems.repository.EmployeeRepository;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    // =====================================================
    // GET ALL
    // =====================================================

    public List<EmployeeDTO> getAllEmployees() {
        return employeeRepository.findAllEmployees();
    }

    // =====================================================
    // GET BY ID
    // =====================================================

    public EmployeeDTO getEmployeeById(Long id) {
        return employeeRepository.findEmployeeById(id);
    }

    // =====================================================
    // CREATE
    // =====================================================

    public EmployeeDTO createEmployee(EmployeeDTO employee) {

        if (employeeRepository.existsByEmail(employee.getEmail())) {
            throw new IllegalArgumentException(
                    "Email already exists"
            );
        }

        Long id = employeeRepository.insertEmployee(employee);

        return employeeRepository.findEmployeeById(id);
    }

    // =====================================================
    // UPDATE
    // =====================================================

    public EmployeeDTO updateEmployee(
            Long id,
            EmployeeDTO employee) {

        EmployeeDTO existingEmployee =
                employeeRepository.findEmployeeById(id);

        if (existingEmployee == null) {
            return null;
        }

        if (!existingEmployee.getEmail().equals(employee.getEmail())
                && employeeRepository.existsByEmail(employee.getEmail())) {

            throw new IllegalArgumentException(
                    "Email already exists"
            );
        }

        int result =
                employeeRepository.updateEmployee(
                        id,
                        employee
                );

        if (result == 0) {
            return null;
        }

        return employeeRepository.findEmployeeById(id);
    }

    // =====================================================
    // DELETE
    // =====================================================

    public boolean deleteEmployee(Long id) {

        int result =
                employeeRepository.deleteEmployee(id);

        return result > 0;
    }

    // =====================================================
    // SEARCH + FILTER + PAGINATION
    // =====================================================

    public PageResponseDTO<EmployeeDTO> searchEmployees(
            String keyword,
            Long departmentId,
            String status,
            int page,
            int size) {

        // Không cho page âm
        if (page < 0) {
            page = 0;
        }

        // Giới hạn size để tránh query quá lớn
        if (size <= 0) {
            size = 10;
        }

        if (size > 100) {
            size = 100;
        }

        int offset = page * size;

        List<EmployeeDTO> employees =
                employeeRepository.searchEmployees(
                        keyword,
                        departmentId,
                        status,
                        size,
                        offset
                );

        int totalElements =
                employeeRepository.countEmployees(
                        keyword,
                        departmentId,
                        status
                );

        return new PageResponseDTO<>(
                employees,
                page,
                size,
                totalElements
        );
    }
}

