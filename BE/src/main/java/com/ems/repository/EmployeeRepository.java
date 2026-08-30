package com.ems.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ems.model.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    // =========================
    // 1. JPA - Method Query
    // =========================

    // Kiểm tra email đã tồn tại chưa
    boolean existsByEmail(String email);


    // Tìm Employee theo tên
    // Spring Data JPA tự tạo query
    List<Employee> findByName(String name);


    // Tìm Employee theo email
    // Spring Data JPA tự tạo query
    Employee findByEmail(String email);


    // =========================
    // 2. JPQL - Tự viết Query
    // =========================

    // Lấy tất cả Employee
    @Query(value = """
    SELECT e.*
    FROM employees e
    INNER JOIN departments d
        ON e.department_id = d.id
    """, nativeQuery = true)
    List<Employee> findAllEmployees();
}