package com.example.employee;

import com.example.employee.employee.model.Employee;
import com.example.employee.employee.repository.EmployeeRepository;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;

@SpringBootApplication
public class EmployeeApplication implements CommandLineRunner {


	private final EmployeeRepository employeeRepository;

    public EmployeeApplication(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public static void main(String[] args) {
		SpringApplication.run(EmployeeApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Employee jimEmployee = new Employee("Jim","Halpert",33,new BigDecimal("10000"),5,"Sales Junior");
		Employee dwightEmployee = new Employee("Dwight","Schrute",32,new BigDecimal("20000"),5,"Assistant to the Regional Manager");
		Employee michaelEmployee = new Employee("Michael","Scott",42,new BigDecimal("10000"),10,"Regional Manager");
		Employee stanleyEmployee = new Employee("Stanley","Hudson",50,new BigDecimal("14000"),10,"Sales Executive");
		employeeRepository.saveAll(Arrays.asList(jimEmployee,dwightEmployee,michaelEmployee,stanleyEmployee));
	}
	@Bean
	public OpenAPI customOpenAPI(){
		return new OpenAPI().info(new Info());

	}
}
