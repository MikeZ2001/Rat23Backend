package com.ratatouille23.Ratatouille23Server.repository;

import com.ratatouille23.Ratatouille23Server.model.Employee;
import com.ratatouille23.Ratatouille23Server.model.enumeration.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee,Long> {
    @Query("SELECT searchedEmployee FROM Employee searchedEmployee WHERE searchedEmployee.email=?1")
    Optional<Employee> getEmployeeByEmail(String email);

    @Query("SELECT employees FROM Employee employees WHERE employees.store.id =?1")
    Optional<List<Employee>> getEmployeesOfStore(Long storeId);

    @Query("SELECT employees FROM Employee employees WHERE employees.store.id =?1 AND employees.role = 3")
    Optional<List<Employee>> getAdminsOfStore(Long storeId);

    @Modifying
    @Transactional
    @Query("UPDATE Employee e SET " +
            "e.name = :employeeName," +
            "e.surname = :employeeSurname," +
            "e.email = :employeeEmail," +
            "e.role = :employeeRole " +
            "WHERE e.id = :employeeId")
    void updateEmployee(@Param("employeeId") Long id, @Param("employeeName") String name,
                                      @Param("employeeSurname") String surname, @Param("employeeEmail") String email, @Param("employeeRole") Role role);
}
