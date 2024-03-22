package com.ratatouille23.Ratatouille23Server.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.ratatouille23.Ratatouille23Server.model.enumeration.Role;
import jakarta.annotation.Nonnull;
import jakarta.persistence.*;
import jakarta.persistence.Table;

import java.util.Collection;
import java.util.Objects;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = "email"))
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Nonnull
    private String email;
    @Nonnull
    private String name;
    @Nonnull
    private String surname;
    @Nonnull
    private Role role;

    @ManyToOne
    private Store store; //Store where this works

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }

//    public Collection<Order> getAllOrdersTakenByEmployee() {
//        return allOrdersTakenByEmployee;
//    }
//
//    public void setAllOrdersTakenByEmployee(Collection<Order> allOrdersTakenByEmployee) {
//        this.allOrdersTakenByEmployee = allOrdersTakenByEmployee;
//    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Employee employee)) return false;
        return id.equals(employee.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
