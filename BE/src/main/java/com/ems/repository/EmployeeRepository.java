package com.ems.repository;

import com.ems.model.Employee;
import org.springframework.stereotype.Repository;

@Repository
public class EmployeeRepository {

    public Employee findEmployee() {
        return new Employee(
            1L,
            "Ngoc",
            "ngoc@example.com"
        );
    }
}