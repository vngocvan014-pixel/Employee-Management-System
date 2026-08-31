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


    // =========================
    // GET ALL
    // =========================

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAllEmployees();
    }


    // =========================
    // GET BY ID
    // =========================

    public Employee getEmployeeById(Long id) {
        return employeeRepository.findEmployeeById(id);
    }


    // =========================
    // CREATE
    // =========================

    public Employee createEmployee(Employee employee) {

        // Kiểm tra email đã tồn tại
        if (employeeRepository.countByEmail(employee.getEmail()) > 0) {
            throw new IllegalArgumentException("Email already exists");
        }

        int result = employeeRepository.insertEmployee(
            employee.getEmployeeCode(),
            employee.getName(),
            employee.getEmail(),
            employee.getPhone(),
            employee.getDepartmentId(),
            employee.getPositionId(),
            employee.getEmploymentType(),
            employee.getStatus(),
            employee.getHireDate(),
            employee.getResignationDate(),
            employee.getManagerId(),
            employee.getSalary()
        );

        if (result == 0) {
            throw new IllegalStateException("Failed to create employee");
        }

        // Lấy lại employee vừa insert
        return employeeRepository.findEmployeeById(
            employee.getId()
        );
    }


    // =========================
    // UPDATE
    // =========================

    public Employee updateEmployee(Long id, Employee employee) {

        Employee existingEmployee =
                employeeRepository.findEmployeeById(id);

        if (existingEmployee == null) {
            return null;
        }

        existingEmployee.setEmployeeCode(employee.getEmployeeCode());
        existingEmployee.setName(employee.getName());
        existingEmployee.setEmail(employee.getEmail());
        existingEmployee.setPhone(employee.getPhone());
        existingEmployee.setDepartmentId(employee.getDepartmentId());
        existingEmployee.setPositionId(employee.getPositionId());
        existingEmployee.setEmploymentType(employee.getEmploymentType());
        existingEmployee.setStatus(employee.getStatus());
        existingEmployee.setHireDate(employee.getHireDate());
        existingEmployee.setResignationDate(employee.getResignationDate());
        existingEmployee.setManagerId(employee.getManagerId());
        existingEmployee.setSalary(employee.getSalary());

        return employeeRepository.save(existingEmployee);
    }


    // =========================
    // DELETE
    // =========================

    public void deleteEmployee(Long id) {
        employeeRepository.deleteById(id);
    }
}

