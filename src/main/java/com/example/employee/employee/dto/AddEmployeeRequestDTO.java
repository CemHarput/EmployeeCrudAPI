package com.example.employee.employee.dto;

import java.math.BigDecimal;

public record AddEmployeeRequestDTO(String name, String surname, int age, BigDecimal salary, int workYears,String title) implements EmployeeRequestDTO{
}
