package com.ems.repository;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.ems.dto.DepartmentDTO;

@Repository
public class DepartmentRepository {

    private final JdbcTemplate jdbcTemplate;

    public DepartmentRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // =====================================================
    // GET ALL
    // =====================================================

    public List<DepartmentDTO> findAllDepartments() {

        String sql = """
            SELECT
                id,
                department_code,
                department_name,
                description
            FROM departments
            ORDER BY id
            """;

        return jdbcTemplate.query(sql, this::mapRow);
    }

    // =====================================================
    // GET BY ID
    // =====================================================

    public DepartmentDTO findDepartmentById(Long id) {

        String sql = """
            SELECT
                id,
                department_code,
                department_name,
                description
            FROM departments
            WHERE id = ?
            """;

        List<DepartmentDTO> result =
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
    // CREATE
    // =====================================================

    public int insertDepartment(DepartmentDTO department) {

        String sql = """
            INSERT INTO departments (
                department_code,
                department_name,
                description
            )
            VALUES (?, ?, ?)
            """;

        return jdbcTemplate.update(
                sql,
                department.getDepartmentCode(),
                department.getDepartmentName(),
                department.getDescription()
        );
    }

    // =====================================================
    // UPDATE
    // =====================================================

    public int updateDepartment(
            Long id,
            DepartmentDTO department) {

        String sql = """
            UPDATE departments
            SET
                department_code = ?,
                department_name = ?,
                description = ?
            WHERE id = ?
            """;

        return jdbcTemplate.update(
                sql,
                department.getDepartmentCode(),
                department.getDepartmentName(),
                department.getDescription(),
                id
        );
    }

    // =====================================================
    // DELETE
    // =====================================================

    public int deleteDepartment(Long id) {

        String sql = """
            DELETE FROM departments
            WHERE id = ?
            """;

        return jdbcTemplate.update(sql, id);
    }

    // =====================================================
    // ROW MAPPER
    // =====================================================

    private DepartmentDTO mapRow(
            java.sql.ResultSet rs,
            int rowNum)
            throws java.sql.SQLException {

        DepartmentDTO department =
                new DepartmentDTO();

        department.setId(
                rs.getLong("id")
        );

        department.setDepartmentCode(
                rs.getString("department_code")
        );

        department.setDepartmentName(
                rs.getString("department_name")
        );

        department.setDescription(
                rs.getString("description")
        );

        return department;
    }
}