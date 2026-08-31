package com.ems.repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.ems.dto.EmployeeDTO;

@Repository
public class EmployeeRepository {

    private final JdbcTemplate jdbcTemplate;

    public EmployeeRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // =====================================================
    // GET ALL
    // =====================================================

    public List<EmployeeDTO> findAllEmployees() {

        String sql = """
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
            """;

        return jdbcTemplate.query(sql, this::mapRow);
    }

    // =====================================================
    // GET BY ID
    // =====================================================

    public EmployeeDTO findEmployeeById(Long id) {

        String sql = """
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
            WHERE e.id = ?
            """;

        List<EmployeeDTO> result =
                jdbcTemplate.query(
                        sql,
                        this::mapRow,
                        id
                );

        return result.isEmpty()
                ? null
                : result.get(0);
    }

    // =====================================================
    // CHECK EMAIL
    // =====================================================

    public boolean existsByEmail(String email) {

        String sql = """
            SELECT COUNT(*)
            FROM employees
            WHERE email = ?
            """;

        Integer count =
                jdbcTemplate.queryForObject(
                        sql,
                        Integer.class,
                        email
                );

        return count != null && count > 0;
    }

    // =====================================================
    // INSERT
    // =====================================================

    public Long insertEmployee(EmployeeDTO employee) {

        String sql = """
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
                ?, ?, ?, ?, ?, ?, ?, ?,
                ?, ?, ?, ?,
                CURRENT_TIMESTAMP,
                CURRENT_TIMESTAMP
            )
            """;

        KeyHolder keyHolder =
                new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {

            PreparedStatement ps =
                    connection.prepareStatement(
                            sql,
                            Statement.RETURN_GENERATED_KEYS
                    );

            ps.setString(
                    1,
                    employee.getEmployeeCode()
            );

            ps.setString(
                    2,
                    employee.getName()
            );

            ps.setString(
                    3,
                    employee.getEmail()
            );

            ps.setString(
                    4,
                    employee.getPhone()
            );

            ps.setObject(
                    5,
                    employee.getDepartmentId()
            );

            ps.setObject(
                    6,
                    employee.getPositionId()
            );

            ps.setString(
                    7,
                    employee.getEmploymentType()
            );

            ps.setString(
                    8,
                    employee.getStatus()
            );

            ps.setObject(
                    9,
                    employee.getHireDate()
            );

            ps.setObject(
                    10,
                    employee.getResignationDate()
            );

            ps.setObject(
                    11,
                    employee.getManagerId()
            );

            ps.setBigDecimal(
                    12,
                    employee.getSalary()
            );

            return ps;

        }, keyHolder);

        if (keyHolder.getKey() == null) {

            throw new IllegalStateException(
                    "Failed to create employee"
            );
        }

        return keyHolder
                .getKey()
                .longValue();
    }

    // =====================================================
    // UPDATE
    // =====================================================

    public int updateEmployee(
            Long id,
            EmployeeDTO employee) {

        String sql = """
            UPDATE employees
            SET
                employee_code = ?,
                name = ?,
                email = ?,
                phone = ?,
                department_id = ?,
                position_id = ?,
                employment_type = ?,
                status = ?,
                hire_date = ?,
                resignation_date = ?,
                manager_id = ?,
                salary = ?,
                updated_at = CURRENT_TIMESTAMP
            WHERE id = ?
            """;

        return jdbcTemplate.update(
                sql,

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
                employee.getSalary(),

                id
        );
    }

    // =====================================================
    // DELETE
    // =====================================================

    public int deleteEmployee(Long id) {

        String sql = """
            DELETE FROM employees
            WHERE id = ?
            """;

        return jdbcTemplate.update(
                sql,
                id
        );
    }

    // =====================================================
    // SEARCH + FILTER + PAGINATION
    // =====================================================

    public List<EmployeeDTO> searchEmployees(
            String keyword,
            Long departmentId,
            String status,
            int size,
            int offset) {

        StringBuilder sql =
                new StringBuilder("""
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
                    WHERE 1 = 1
                    """);

        List<Object> params =
                new java.util.ArrayList<>();

        // -------------------------------------------------
        // SEARCH KEYWORD
        // -------------------------------------------------

        if (keyword != null
                && !keyword.trim().isEmpty()) {

            sql.append("""
                AND (
                    LOWER(e.name) LIKE LOWER(?)
                    OR LOWER(e.email) LIKE LOWER(?)
                    OR LOWER(e.employee_code) LIKE LOWER(?)
                )
                """);

            String searchKeyword =
                    "%" + keyword.trim() + "%";

            params.add(searchKeyword);
            params.add(searchKeyword);
            params.add(searchKeyword);
        }

        // -------------------------------------------------
        // FILTER DEPARTMENT
        // -------------------------------------------------

        if (departmentId != null) {

            sql.append("""
                AND e.department_id = ?
                """);

            params.add(departmentId);
        }

        // -------------------------------------------------
        // FILTER STATUS
        // -------------------------------------------------

        if (status != null
                && !status.trim().isEmpty()) {

            sql.append("""
                AND e.status = ?
                """);

            params.add(status);
        }

        // -------------------------------------------------
        // ORDER + PAGINATION
        // -------------------------------------------------

        sql.append("""
            ORDER BY e.id DESC
            LIMIT ?
            OFFSET ?
            """);

        params.add(size);
        params.add(offset);

        return jdbcTemplate.query(
                sql.toString(),
                this::mapRow,
                params.toArray()
        );
    }

    // =====================================================
    // COUNT
    // =====================================================

    public int countEmployees(
            String keyword,
            Long departmentId,
            String status) {

        StringBuilder sql =
                new StringBuilder("""
                    SELECT COUNT(*)
                    FROM employees e
                    WHERE 1 = 1
                    """);

        List<Object> params =
                new java.util.ArrayList<>();

        // -------------------------------------------------
        // SEARCH KEYWORD
        // -------------------------------------------------

        if (keyword != null
                && !keyword.trim().isEmpty()) {

            sql.append("""
                AND (
                    LOWER(e.name) LIKE LOWER(?)
                    OR LOWER(e.email) LIKE LOWER(?)
                    OR LOWER(e.employee_code) LIKE LOWER(?)
                )
                """);

            String searchKeyword =
                    "%" + keyword.trim() + "%";

            params.add(searchKeyword);
            params.add(searchKeyword);
            params.add(searchKeyword);
        }

        // -------------------------------------------------
        // FILTER DEPARTMENT
        // -------------------------------------------------

        if (departmentId != null) {

            sql.append("""
                AND e.department_id = ?
                """);

            params.add(departmentId);
        }

        // -------------------------------------------------
        // FILTER STATUS
        // -------------------------------------------------

        if (status != null
                && !status.trim().isEmpty()) {

            sql.append("""
                AND e.status = ?
                """);

            params.add(status);
        }

        Integer count =
                jdbcTemplate.queryForObject(
                        sql.toString(),
                        Integer.class,
                        params.toArray()
                );

        return count != null
                ? count
                : 0;
    }

    // =====================================================
    // ROW MAPPER
    // =====================================================

    private EmployeeDTO mapRow(
            java.sql.ResultSet rs,
            int rowNum)
            throws java.sql.SQLException {

        EmployeeDTO employee =
                new EmployeeDTO();

        employee.setId(
                rs.getLong("id")
        );

        employee.setEmployeeCode(
                rs.getString("employee_code")
        );

        employee.setName(
                rs.getString("name")
        );

        employee.setEmail(
                rs.getString("email")
        );

        employee.setPhone(
                rs.getString("phone")
        );

        employee.setDepartmentId(
                rs.getObject(
                        "department_id",
                        Long.class
                )
        );

        employee.setPositionId(
                rs.getObject(
                        "position_id",
                        Long.class
                )
        );

        employee.setEmploymentType(
                rs.getString(
                        "employment_type"
                )
        );

        employee.setStatus(
                rs.getString("status")
        );

        employee.setHireDate(
                rs.getObject(
                        "hire_date",
                        java.time.LocalDate.class
                )
        );

        employee.setResignationDate(
                rs.getObject(
                        "resignation_date",
                        java.time.LocalDate.class
                )
        );

        employee.setManagerId(
                rs.getObject(
                        "manager_id",
                        Long.class
                )
        );

        employee.setSalary(
                rs.getBigDecimal("salary")
        );

        employee.setCreatedAt(
                rs.getObject(
                        "created_at",
                        java.time.LocalDateTime.class
                )
        );

        employee.setUpdatedAt(
                rs.getObject(
                        "updated_at",
                        java.time.LocalDateTime.class
                )
        );

        return employee;
    }
}