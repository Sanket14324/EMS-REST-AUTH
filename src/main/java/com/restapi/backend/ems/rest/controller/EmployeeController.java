package com.restapi.backend.ems.rest.controller;


import com.restapi.backend.ems.rest.model.AuthRequest;
import com.restapi.backend.ems.rest.model.Employee;
import com.restapi.backend.ems.rest.repository.EmployeeRepository;
import com.restapi.backend.ems.rest.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/ems")
@CrossOrigin(value = "*")
public class EmployeeController {


    @Autowired
    private EmployeeRepository employeeRepository;


    @Autowired
    private JwtService jwtService;

    @GetMapping
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }



    @PostMapping("/login")
    public String loginEmployee(@RequestBody Employee employeeCredentials){
        List<Employee> employeeList = employeeRepository.findAll().stream().filter(employee -> Objects.equals(employee.getEmail(), employeeCredentials.getEmail()))
                .collect(Collectors.toList());

        if(employeeList.size() != 0){
            if(employeeList.get(0).getPassword().equals(employeeCredentials.getPassword())){
                return employeeList.get(0).getRole();
//                return jwtService.generateToken(employeeCredentials.getEmail());
            }
        }
        else {
            return "not found";
        }

        return "not found";

    }
    @GetMapping("{name}")
    public List<Employee> getEmployeByName(@PathVariable String name){
        return employeeRepository.findAll().stream().filter(employee -> Objects.equals(employee.getName(), name))
                .collect(Collectors.toList());

    }

    @GetMapping("/user")
    public String getEmployeeDetails(@PathVariable String token){
        String useremail = jwtService.extractUsername(token);
        String userDetails = "";

        List<Employee> employeeList = employeeRepository.findAll().stream().filter(employee -> Objects.equals(employee.getEmail(), useremail))
                .collect(Collectors.toList());

        userDetails = "{\"name\":\""+employeeList.get(0).getName()+"\", \"role\":\""+employeeList.get(0).getRole()+"\"}";

        return userDetails;
    }

    @PostMapping()
    public String addNewEmployee(@RequestBody Employee employee){
        employeeRepository.save(employee);
        return "Welcome, you are added into Iauro Humans";
    }

    @DeleteMapping("{Id}")
    public List<Employee> deleteEmployee(@PathVariable Long Id){
        Employee employee = employeeRepository.findById(Id)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        employeeRepository.delete(employee);
        return employeeRepository.findAll();
    }


    @PutMapping("{id}")
    public String updateEmployeeById(@PathVariable Long id, @RequestBody Employee employeeDetails ){
        Employee updateEmployee = employeeRepository.findById(id)
                .orElseThrow(() ->new RuntimeException("Customer not exist with id:"+id));

        updateEmployee.setName(employeeDetails.getName());
        updateEmployee.setEmail(employeeDetails.getEmail());

        employeeRepository.save(updateEmployee);
        return "Information updated successfully!!!";
    }
}
