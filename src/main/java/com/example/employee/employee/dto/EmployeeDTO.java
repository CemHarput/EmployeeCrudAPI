package com.example.employee.employee.dto;

import com.example.employee.employee.model.Employee;

import java.math.BigDecimal;

public record EmployeeDTO(String name, String surname, int age, BigDecimal salary, int workYears, String title) {
    public static EmployeeDTO convertFromEmployee(Employee employee) {
        return new EmployeeDTO(employee.getName(), employee.getSurname(), employee.getAge(), employee.getSalary(), employee.getWorkYears(), employee.getTitle());
    }
}

