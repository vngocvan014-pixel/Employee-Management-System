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

        if (employeeRepository.existsByEmail(
                employee.getEmail())) {

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

        EmployeeDTO existing =
                employeeRepository.findEmployeeById(id);

        if (existing == null) {
            return null;
        }

        // Kiểm tra email mới có bị trùng không
        if (!existing.getEmail().equals(employee.getEmail())
                && employeeRepository.existsByEmail(
                        employee.getEmail())) {

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

        // page = 0 → offset = 0
        // page = 1 → offset = size
        // page = 2 → offset = size * 2

        int offset = page * size;

        // Lấy danh sách employee
        List<EmployeeDTO> employees =
                employeeRepository.searchEmployees(
                        keyword,
                        departmentId,
                        status,
                        size,
                        offset
                );

        // Tổng số employee phù hợp điều kiện search
        int totalElements =
                employeeRepository.countEmployees(
                        keyword,
                        departmentId,
                        status
                );

        // Tính tổng số page
        int totalPages =
                (int) Math.ceil(
                        (double) totalElements / size
                );

        // Tạo response
        PageResponseDTO<EmployeeDTO> response =
                new PageResponseDTO<>();

        response.setData(employees);
        response.setPage(page);
        response.setSize(size);
        response.setTotalElements(totalElements);
        response.setTotalPages(totalPages);

        return response;
    }

    // =====================================================
    // COUNT
    // =====================================================

    public int countEmployees(
            String keyword,
            Long departmentId,
            String status) {

        return employeeRepository.countEmployees(
                keyword,
                departmentId,
                status
        );
    }
}