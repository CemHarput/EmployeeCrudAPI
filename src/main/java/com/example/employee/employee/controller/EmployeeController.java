package com.example.employee.employee.controller;


import com.example.employee.employee.dto.AddEmployeeRequestDTO;
import com.example.employee.employee.dto.EmployeeDTO;
import com.example.employee.employee.dto.UpdateEmployeeRequestDTO;
import com.example.employee.employee.service.EmployeeService;
import com.example.employee.exception.EmployeeCreationException;
import com.example.employee.exception.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.sql.Update;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.parser.Entity;
import java.util.List;


@Slf4j
@RestController
@RequestMapping("/api/v1/")
public class EmployeeController {
    private static final Logger logger = LoggerFactory.getLogger(EmployeeController.class);

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/employee")
    public ResponseEntity<List<EmployeeDTO>> getAllStudents(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size){
        logger.debug("getAllBooks is started");
        Page<EmployeeDTO> employeeDtoPage = employeeService.getAllEmployees(page, size);
        return ResponseEntity.ok(employeeDtoPage.getContent());
    }

    @PostMapping("/employees")
    public ResponseEntity<String> createEmployee(@RequestBody AddEmployeeRequestDTO addEmployeeRequestDTO) {
        try {
            logger.debug("createEmployee is started");
            Long employeeId = employeeService.createEmployee(addEmployeeRequestDTO);
            logger.info("Employee created successfully with ID: {}", employeeId);
            return new ResponseEntity<>(employeeId.toString(), HttpStatus.CREATED);
        } catch (EmployeeCreationException e) {
            logger.error("createEmployee failed due to: {}", e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            logger.error("Unexpected error during employee creation: {}", e.getMessage());
            return new ResponseEntity<>("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/employee/{employeeId}")
    public ResponseEntity<String> updateEmployee(@PathVariable Long employeeId,
                                            @Valid @RequestBody UpdateEmployeeRequestDTO updateEmployeeRequestDTO) {
        try {
            logger.debug("updateEmployee is started for employeeId: {}", employeeId);
            Long updatedEmployeeId = employeeService.updateEmployee(employeeId, updateEmployeeRequestDTO);
            logger.info("Employee updated successfully with ID: {}", employeeId);
            return new ResponseEntity<>(updatedEmployeeId.toString(), HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            logger.error("Employee with ID {} not found: {}", employeeId, e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            logger.error("Unexpected error occurred while updating employee: {}", e.getMessage());
            return new ResponseEntity<>("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @DeleteMapping("/employee/{employeeId}")
    public ResponseEntity<String> deleteEmployee(@PathVariable Long employeeId) {
        try {
            logger.debug("deleteEmployee is started for employeeId: {}", employeeId);
            employeeService.deleteEmployee(employeeId);
            logger.info("Employee deleted successfully with ID: {}", employeeId);
            return new ResponseEntity<>(employeeId.toString(), HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            logger.error("Employee with ID {} not found: {}", employeeId, e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            logger.error("Unexpected error occurred while deleting employee: {}", e.getMessage());
            return new ResponseEntity<>("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }








}
