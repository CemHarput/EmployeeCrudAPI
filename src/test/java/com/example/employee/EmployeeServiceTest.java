package com.example.employee;

import com.example.employee.employee.dto.AddEmployeeRequestDTO;
import com.example.employee.employee.dto.EmployeeDTO;
import com.example.employee.employee.dto.UpdateEmployeeRequestDTO;
import com.example.employee.employee.model.Employee;
import com.example.employee.employee.repository.EmployeeRepository;
import com.example.employee.employee.service.EmployeeService;
import com.example.employee.exception.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class EmployeeServiceTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeService employeeService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);  // Initialize mocks
    }

    @Test
    void testGetAllEmployees() {
        Employee employee1 = new Employee("John", "Doe", 30, new BigDecimal("5000"), 5, "Developer");
        Employee employee2 = new Employee("Jane", "Doe", 25, new BigDecimal("4500"), 3, "Tester");

        List<Employee> employees = Arrays.asList(employee1, employee2);
        Page<Employee> employeePage = new PageImpl<>(employees, PageRequest.of(0, 10), employees.size());

        when(employeeRepository.findAll(any(Pageable.class))).thenReturn(employeePage);

        Page<EmployeeDTO> result = employeeService.getAllEmployees(0, 10);

        assertEquals(2, result.getContent().size());
        assertEquals("John", result.getContent().get(0).name());
        assertEquals("Jane", result.getContent().get(1).name());

        verify(employeeRepository, times(1)).findAll(any(Pageable.class));
    }


    @Test
    void testCreateEmployee() {
        AddEmployeeRequestDTO requestDTO = new AddEmployeeRequestDTO("John", "Doe", 30, new BigDecimal("5000"), 5, "Developer");
        Employee employee = new Employee("John", "Doe", 30, new BigDecimal("5000"), 5, "Developer");

        when(employeeRepository.save(any(Employee.class))).thenReturn(employee);

        Long createdId = employeeService.createEmployee(requestDTO);

        assertNotNull(createdId);
        verify(employeeRepository, times(1)).save(any(Employee.class));
    }
    @Test
    void testUpdateEmployee() {
        Long employeeId = 1L;
        UpdateEmployeeRequestDTO updateRequestDTO = new UpdateEmployeeRequestDTO("John", "Doe", 35, new BigDecimal("6000"), 6, "Lead Developer");
        Employee existingEmployee = new Employee("John", "Doe", 30, new BigDecimal("5000"), 5, "Developer");

        when(employeeRepository.findById(employeeId)).thenReturn(Optional.of(existingEmployee));
        when(employeeRepository.save(any(Employee.class))).thenReturn(existingEmployee);

        Long updatedId = employeeService.updateEmployee(employeeId, updateRequestDTO);

        assertEquals(employeeId, updatedId);
        assertEquals("Lead Developer", existingEmployee.getTitle());
        verify(employeeRepository, times(1)).findById(employeeId);
        verify(employeeRepository, times(1)).save(existingEmployee);
    }

    @Test
    void testUpdateEmployee_NotFound() {
        Long employeeId = 1L;
        UpdateEmployeeRequestDTO updateRequestDTO = new UpdateEmployeeRequestDTO("John", "Doe", 35, new BigDecimal("6000"), 6, "Lead Developer");

        when(employeeRepository.findById(employeeId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> employeeService.updateEmployee(employeeId, updateRequestDTO));
        verify(employeeRepository, times(1)).findById(employeeId);
        verify(employeeRepository, times(0)).save(any(Employee.class));
    }

    @Test
    void testDeleteEmployee() {
        Long employeeId = 1L;
        Employee existingEmployee = new Employee("John", "Doe", 30, new BigDecimal("5000"), 5, "Developer");

        when(employeeRepository.findById(employeeId)).thenReturn(Optional.of(existingEmployee));

        employeeService.deleteEmployee(employeeId);

        verify(employeeRepository, times(1)).findById(employeeId);
        verify(employeeRepository, times(1)).delete(existingEmployee);
    }

    @Test
    void testDeleteEmployee_NotFound() {
        Long employeeId = 1L;

        when(employeeRepository.findById(employeeId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> employeeService.deleteEmployee(employeeId));
        verify(employeeRepository, times(1)).findById(employeeId);
        verify(employeeRepository, times(0)).delete(any(Employee.class));
    }
}
