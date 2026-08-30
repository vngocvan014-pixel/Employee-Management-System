package com.ems.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ems.model.Employee;
import com.ems.repository.EmployeeRepository;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    // Lấy tất cả Employee
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAllEmployees();
    }

    // Tìm Employee theo ID
    public Employee getEmployeeById(Long id) {
        return employeeRepository.findById(id).orElse(null);
    }

    // Thêm Employee
    public Employee createEmployee(Employee employee) {

        if (employeeRepository.existsByEmail(employee.getEmail())) {
            throw new IllegalArgumentException("Email already exists");
        }

        return employeeRepository.save(employee);
    }

    // Cập nhật Employee
    public Employee updateEmployee(Long id, Employee employee) {

        Employee existingEmployee =
                employeeRepository.findById(id).orElse(null);

        if (existingEmployee == null) {
            return null;
        }

        existingEmployee.setName(employee.getName());
        existingEmployee.setEmail(employee.getEmail());

        return employeeRepository.save(existingEmployee);
    }

    // Xóa Employee
    public void deleteEmployee(Long id) {
        employeeRepository.deleteById(id);
    }
}

