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

import com.ems.dto.DepartmentDTO;
import com.ems.service.DepartmentService;

@RestController
@RequestMapping("/api/departments")
public class DepartmentController {

    private final DepartmentService departmentService;

    public DepartmentController(
            DepartmentService departmentService) {

        this.departmentService =
                departmentService;
    }

    // GET /api/departments
    @GetMapping
    public ResponseEntity<List<DepartmentDTO>>
            getAllDepartments() {

        return ResponseEntity.ok(
                departmentService
                        .getAllDepartments()
        );
    }

    // GET /api/departments/{id}
    @GetMapping("/{id}")
    public ResponseEntity<DepartmentDTO>
            getDepartmentById(
                    @PathVariable Long id) {

        DepartmentDTO department =
                departmentService
                        .getDepartmentById(id);

        if (department == null) {
            return ResponseEntity
                    .notFound()
                    .build();
        }

        return ResponseEntity.ok(department);
    }

    // POST /api/departments
    @PostMapping
    public ResponseEntity<DepartmentDTO>
            createDepartment(
                    @RequestBody DepartmentDTO department) {

        DepartmentDTO created =
                departmentService
                        .createDepartment(department);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(created);
    }

    // PUT /api/departments/{id}
    @PutMapping("/{id}")
    public ResponseEntity<DepartmentDTO>
            updateDepartment(
                    @PathVariable Long id,
                    @RequestBody DepartmentDTO department) {

        DepartmentDTO updated =
                departmentService
                        .updateDepartment(
                                id,
                                department
                        );

        if (updated == null) {
            return ResponseEntity
                    .notFound()
                    .build();
        }

        return ResponseEntity.ok(updated);
    }

    // DELETE /api/departments/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void>
            deleteDepartment(
                    @PathVariable Long id) {

        boolean deleted =
                departmentService
                        .deleteDepartment(id);

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