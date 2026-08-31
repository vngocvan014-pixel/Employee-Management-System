package com.ems.repository;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.ems.dto.PositionDTO;

@Repository
public class PositionRepository {

    private final JdbcTemplate jdbcTemplate;

    public PositionRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // =====================================================
    // GET ALL
    // =====================================================

    public List<PositionDTO> findAllPositions() {

        String sql = """
            SELECT
                id,
                position_code,
                position_name,
                description
            FROM positions
            ORDER BY id
            """;

        return jdbcTemplate.query(sql, this::mapRow);
    }

    // =====================================================
    // GET BY ID
    // =====================================================

    public PositionDTO findPositionById(Long id) {

        String sql = """
            SELECT
                id,
                position_code,
                position_name,
                description
            FROM positions
            WHERE id = ?
            """;

        List<PositionDTO> result =
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

    public int insertPosition(PositionDTO position) {

        String sql = """
            INSERT INTO positions (
                position_code,
                position_name,
                description
            )
            VALUES (?, ?, ?)
            """;

        return jdbcTemplate.update(
                sql,
                position.getPositionCode(),
                position.getPositionName(),
                position.getDescription()
        );
    }

    // =====================================================
    // UPDATE
    // =====================================================

    public int updatePosition(
            Long id,
            PositionDTO position) {

        String sql = """
            UPDATE positions
            SET
                position_code = ?,
                position_name = ?,
                description = ?
            WHERE id = ?
            """;

        return jdbcTemplate.update(
                sql,
                position.getPositionCode(),
                position.getPositionName(),
                position.getDescription(),
                id
        );
    }

    // =====================================================
    // DELETE
    // =====================================================

    public int deletePosition(Long id) {

        String sql = """
            DELETE FROM positions
            WHERE id = ?
            """;

        return jdbcTemplate.update(
                sql,
                id
        );
    }

    // =====================================================
    // ROW MAPPER
    // =====================================================

    private PositionDTO mapRow(
            java.sql.ResultSet rs,
            int rowNum)
            throws java.sql.SQLException {

        PositionDTO position =
                new PositionDTO();

        position.setId(
                rs.getLong("id")
        );

        position.setPositionCode(
                rs.getString("position_code")
        );

        position.setPositionName(
                rs.getString("position_name")
        );

        position.setDescription(
                rs.getString("description")
        );

        return position;
    }
}

