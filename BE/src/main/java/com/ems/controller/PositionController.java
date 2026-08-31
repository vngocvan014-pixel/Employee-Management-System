package com.ems.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ems.dto.PositionDTO;
import com.ems.service.PositionService;

@RestController
@RequestMapping("/api/positions")
public class PositionController {

    private final PositionService positionService;

    public PositionController(
            PositionService positionService) {

        this.positionService =
                positionService;
    }

    // =====================================================
    // GET ALL
    // =====================================================

    @GetMapping
    public ResponseEntity<List<PositionDTO>>
            getAllPositions() {

        return ResponseEntity.ok(
                positionService
                        .getAllPositions()
        );
    }

    // =====================================================
    // GET BY ID
    // =====================================================

    @GetMapping("/{id}")
    public ResponseEntity<PositionDTO>
            getPositionById(
                    @PathVariable Long id) {

        PositionDTO position =
                positionService
                        .getPositionById(id);

        if (position == null) {
            return ResponseEntity
                    .notFound()
                    .build();
        }

        return ResponseEntity.ok(position);
    }

    // =====================================================
    // CREATE
    // =====================================================

    @PostMapping
    public ResponseEntity<PositionDTO>
            createPosition(
                    @RequestBody PositionDTO position) {

        PositionDTO created =
                positionService
                        .createPosition(position);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(created);
    }

    // =====================================================
    // UPDATE
    // =====================================================

    @PutMapping("/{id}")
    public ResponseEntity<PositionDTO>
            updatePosition(
                    @PathVariable Long id,
                    @RequestBody PositionDTO position) {

        PositionDTO updated =
                positionService
                        .updatePosition(
                                id,
                                position
                        );

        if (updated == null) {
            return ResponseEntity
                    .notFound()
                    .build();
        }

        return ResponseEntity.ok(updated);
    }

    // =====================================================
    // DELETE
    // =====================================================

    @DeleteMapping("/{id}")
    public ResponseEntity<Void>
            deletePosition(
                    @PathVariable Long id) {

        boolean deleted =
                positionService
                        .deletePosition(id);

        if (!deleted) {
            return ResponseEntity
                    .notFound()
                    .build();
        }

        return ResponseEntity
                .noContent()
                .build();
    }
}

