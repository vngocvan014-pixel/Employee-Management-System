package com.ems.service;

import com.ems.model.Employee;
import com.ems.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    // Lấy tất cả Employee
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    // Tìm Employee theo ID
    public Employee getEmployeeById(Long id) {
        return employeeRepository.findById(id).orElse(null);
    }

    // Thêm Employee
    public Employee createEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    // Cập nhật Employee
    public Employee updateEmployee(Long id, Employee employee) {
        Employee existingEmployee = employeeRepository.findById(id).orElse(null);

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