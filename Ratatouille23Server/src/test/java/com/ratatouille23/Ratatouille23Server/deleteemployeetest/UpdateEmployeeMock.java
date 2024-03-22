package com.ratatouille23.Ratatouille23Server.deleteemployeetest;

import com.ratatouille23.Ratatouille23Server.EmployeeDBMock;
import com.ratatouille23.Ratatouille23Server.model.Employee;
import com.ratatouille23.Ratatouille23Server.model.enumeration.Role;
import org.springframework.web.bind.annotation.PutMapping;

import java.util.List;

public class UpdateEmployeeMock {

    private EmployeeDBMock dbMock;

    public UpdateEmployeeMock() {
        this.dbMock = new EmployeeDBMock();
    }

    //Metodo da testare
    public void updateEmployee(Long id, Employee employee) throws IllegalStateException{
        Employee updateEmployee = dbMock.findById(id); //Equivalente di repository.findById().orElse(null);

        if (updateEmployee == null)
            throw new IllegalStateException("Utente inesistente.");

        if (updateEmployee.getRole().equals(Role.ADMINISTRATOR) && !employee.getRole().equals(Role.ADMINISTRATOR)) {
            List<Employee> admins = dbMock.getAdminsOfStore(employee.getStore().getId()); //Equivalente di repository.getAdminsOfStore().orElse(null);
            if (admins != null && admins.size() > 1) {
                dbMock.updateEmployee(id,employee.getName(),employee.getSurname(),employee.getEmail(), employee.getRole()); //Equivalente di employeeRepository.updateEmployee(id,employee.getName(),employee.getSurname(),employee.getEmail(), employee.getRole());
            } else {
                throw new IllegalStateException("Impossibile avere 0 amministratori di questa attività.");
            }
        }else{
            dbMock.updateEmployee(id,employee.getName(),employee.getSurname(),employee.getEmail(), employee.getRole()); //Equivalente di employeeRepository.updateEmployee(id,employee.getName(),employee.getSurname(),employee.getEmail(), employee.getRole());
        }
    }

    public Employee getEmployee(Employee e){
       return dbMock.findById(e.getId());
    }
}
