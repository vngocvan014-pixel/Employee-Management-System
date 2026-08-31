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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ems.dto.EmployeeDTO;
import com.ems.dto.PageResponseDTO;
import com.ems.service.EmployeeService;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    // =====================================================
    // GET ALL
    // GET /api/employees
    // =====================================================

    @GetMapping
    public ResponseEntity<List<EmployeeDTO>> getAllEmployees() {

        return ResponseEntity.ok(
                employeeService.getAllEmployees()
        );
    }

    // =====================================================
    // GET BY ID
    // GET /api/employees/{id}
    // =====================================================

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDTO> getEmployeeById(
            @PathVariable Long id) {

        EmployeeDTO employee =
                employeeService.getEmployeeById(id);

        if (employee == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(employee);
    }

    // =====================================================
    // SEARCH + FILTER + PAGINATION
    //
    // GET /api/employees/search
    //
    // Examples:
    //
    // /api/employees/search
    //
    // /api/employees/search?keyword=nguyen
    //
    // /api/employees/search?departmentId=1
    //
    // /api/employees/search?status=ACTIVE
    //
    // /api/employees/search
    // ?keyword=nguyen
    // &departmentId=1
    // &status=ACTIVE
    // &page=0
    // &size=10
    // =====================================================

    @GetMapping("/search")
    public ResponseEntity<PageResponseDTO<EmployeeDTO>>
            searchEmployees(

                    @RequestParam(
                            required = false)
                    String keyword,

                    @RequestParam(
                            required = false)
                    Long departmentId,

                    @RequestParam(
                            required = false)
                    String status,

                    @RequestParam(
                            defaultValue = "0")
                    int page,

                    @RequestParam(
                            defaultValue = "10")
                    int size) {

        PageResponseDTO<EmployeeDTO> response =
                employeeService.searchEmployees(
                        keyword,
                        departmentId,
                        status,
                        page,
                        size
                );

        return ResponseEntity.ok(response);
    }

    // =====================================================
    // CREATE
    // POST /api/employees
    // =====================================================

    @PostMapping
    public ResponseEntity<EmployeeDTO> createEmployee(
            @RequestBody EmployeeDTO employee) {

        EmployeeDTO created =
                employeeService.createEmployee(employee);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(created);
    }

    // =====================================================
    // UPDATE
    // PUT /api/employees/{id}
    // =====================================================

    @PutMapping("/{id}")
    public ResponseEntity<EmployeeDTO> updateEmployee(
            @PathVariable Long id,
            @RequestBody EmployeeDTO employee) {

        EmployeeDTO updated =
                employeeService.updateEmployee(
                        id,
                        employee
                );

        if (updated == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(updated);
    }

    // =====================================================
    // DELETE
    // DELETE /api/employees/{id}
    // =====================================================

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(
            @PathVariable Long id) {

        boolean deleted =
                employeeService.deleteEmployee(id);

        if (!deleted) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }
}

