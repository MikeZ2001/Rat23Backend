package com.ratatouille23.Ratatouille23Server.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ratatouille23.Ratatouille23Server.model.Employee;
import com.ratatouille23.Ratatouille23Server.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping(path = "api/v1/employee")
public class EmployeeController {

    @Autowired
    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService){
        this.employeeService = employeeService;
    }


    @GetMapping("/getAll")
    public ResponseEntity<List<Employee>> getAllEmployee(){

        List<Employee> employees = employeeService.getAllEmployee();

        return new ResponseEntity<>(employees, HttpStatus.OK);
    }

    @GetMapping("/getById/{employeeId}")
    public ResponseEntity<?> getEmployeeById(@PathVariable("employeeId") Long id){
        try {
            Employee employee = employeeService.getEmployeeById(id);
            return new ResponseEntity<>(employee,HttpStatus.OK);
        } catch (IllegalStateException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PostMapping("/add")
    public ResponseEntity<Employee>addEmployee(@RequestBody Employee employee){
        Employee newEmployee = employeeService.addEmployee(employee);

        return new ResponseEntity<>(newEmployee,HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{employeeId}")
    public ResponseEntity<?> deleteEmployee(@PathVariable("employeeId") Long id){
        try{
            employeeService.deleteEmployee(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (IllegalStateException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/update/{employeeId}")
    public ResponseEntity<?> updateEmployee(@PathVariable("employeeId") Long id,@RequestBody Employee employee){
        try {
            employeeService.updateEmployee(id,employee);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (IllegalStateException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PostMapping("/getByEmail")
    public ResponseEntity<?> getEmployeeByEmail(@RequestBody String email){
        try{
            Employee employee = employeeService.getEmployeeByEmail(email.replace("\"", ""));
            return new ResponseEntity<>(employee, HttpStatus.OK);
        } catch (IllegalStateException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getByStore/{id}")
    public ResponseEntity<?> getEmployeesOfStore(@PathVariable("id") Long storeId){
        try{
            List<Employee> employees = employeeService.getEmployeesOfStore(storeId);
            return new ResponseEntity<>(employees, HttpStatus.OK);
        } catch (IllegalStateException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
