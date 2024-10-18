package com.example.employee.employee.service;


import com.example.employee.employee.dto.AddEmployeeRequestDTO;
import com.example.employee.employee.dto.EmployeeDTO;
import com.example.employee.employee.dto.EmployeeRequestDTO;
import com.example.employee.employee.dto.UpdateEmployeeRequestDTO;
import com.example.employee.employee.model.Employee;
import com.example.employee.employee.repository.EmployeeRepository;
import com.example.employee.exception.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public Page<EmployeeDTO> getAllEmployees(int page, int size){
        Pageable pageable = PageRequest.of(page, size);

        Page<Employee> employeesPage = employeeRepository.findAll(pageable);

        List<EmployeeDTO> studentDtos = employeesPage.getContent().stream()
                .map(EmployeeDTO::convertFromEmployee)
                .toList();

        return new PageImpl<>(studentDtos, pageable, employeesPage.getTotalElements());
    }


    public Long createEmployee(AddEmployeeRequestDTO addEmployeeRequestDTO) {
        Employee employee = new Employee();
        mapDtoToEmployee(addEmployeeRequestDTO,employee);
        employeeRepository.save(employee);
        return employee.getId();
    }

    private Employee findEmployeeById(Long employeeId)  {
        return employeeRepository.findById(employeeId)
                .orElseThrow(() -> new EntityNotFoundException("Employee not found with ID: " + employeeId));
    }

    public Long updateEmployee(Long employeeId, UpdateEmployeeRequestDTO updateEmployeeDTO) {
        Employee existingEmployee = findEmployeeById(employeeId);
        mapDtoToEmployee(updateEmployeeDTO, existingEmployee);
        employeeRepository.save(existingEmployee);
        return existingEmployee.getId();
    }

    private void mapDtoToEmployee(EmployeeRequestDTO dto, Employee employee) {
        employee.setName(dto.name());
        employee.setSurname(dto.surname());
        employee.setAge(dto.age());
        employee.setSalary(dto.salary());
        employee.setWorkYears(dto.workYears());
        employee.setTitle(dto.title());
    }
    public void deleteEmployee(Long employeeId) {
        Employee existingEmployee = findEmployeeById(employeeId);
        employeeRepository.delete(existingEmployee);

    }


}
