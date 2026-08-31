package com.ems.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ems.model.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    // =========================
    // GET ALL - Native SQL
    // =========================

    @Query(value = """
        SELECT
            e.id,
            e.employee_code,
            e.name,
            e.email,
            e.phone,
            e.department_id,
            e.position_id,
            e.employment_type,
            e.status,
            e.hire_date,
            e.resignation_date,
            e.manager_id,
            e.salary,
            e.created_at,
            e.updated_at
        FROM employees e
        ORDER BY e.id DESC
        """, nativeQuery = true)
    List<Employee> findAllEmployees();


    // =========================
    // GET BY ID - Native SQL
    // =========================

    @Query(value = """
        SELECT
            e.id,
            e.employee_code,
            e.name,
            e.email,
            e.phone,
            e.department_id,
            e.position_id,
            e.employment_type,
            e.status,
            e.hire_date,
            e.resignation_date,
            e.manager_id,
            e.salary,
            e.created_at,
            e.updated_at
        FROM employees e
        WHERE e.id = :id
        """, nativeQuery = true)
    Employee findEmployeeById(Long id);


    // =========================
    // CHECK EMAIL - Native SQL
    // =========================

    @Query(value = """
        SELECT COUNT(*)
        FROM employees
        WHERE email = :email
        """, nativeQuery = true)
    int countByEmail(String email);


    // =========================
    // INSERT - Native SQL
    // =========================

    @Modifying
    @Transactional
    @Query(value = """
        INSERT INTO employees (
            employee_code,
            name,
            email,
            phone,
            department_id,
            position_id,
            employment_type,
            status,
            hire_date,
            resignation_date,
            manager_id,
            salary,
            created_at,
            updated_at
        )
        VALUES (
            :employeeCode,
            :name,
            :email,
            :phone,
            :departmentId,
            :positionId,
            :employmentType,
            :status,
            :hireDate,
            :resignationDate,
            :managerId,
            :salary,
            CURRENT_TIMESTAMP,
            CURRENT_TIMESTAMP
        )
        """, nativeQuery = true)
    int insertEmployee(
        String employeeCode,
        String name,
        String email,
        String phone,
        Long departmentId,
        Long positionId,
        String employmentType,
        String status,
        java.time.LocalDate hireDate,
        java.time.LocalDate resignationDate,
        Long managerId,
        java.math.BigDecimal salary
    );
}

