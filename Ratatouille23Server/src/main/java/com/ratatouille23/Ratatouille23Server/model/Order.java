package com.ratatouille23.Ratatouille23Server.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.ratatouille23.Ratatouille23Server.model.enumeration.Status;
import jakarta.annotation.Nonnull;
import jakarta.persistence.*;
import jakarta.persistence.Table;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "RestaurantOrder")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Nonnull
    private Status status;
    @Nonnull
    private String date;
    @Nonnull
    private String time;
    @Nonnull
    private Double total;
    private String notes;

    @ManyToOne(cascade = CascadeType.MERGE)
    private StoreTable storeTable;

    @ManyToMany(fetch = FetchType.LAZY)
    private List<Employee> employeesOfTheOrder;

    @JsonManagedReference
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<OrderItem> items = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public StoreTable getTable() {
        return storeTable;
    }

    public void setTable(StoreTable storeTable) {
        this.storeTable = storeTable;
    }

    public List<Employee> getEmployeesOfTheOrder() {
        return employeesOfTheOrder;
    }

    public void setEmployeesOfTheOrder(List<Employee> employeesOfTheOrder) {
        this.employeesOfTheOrder = employeesOfTheOrder;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public void setItems(List<OrderItem> items) {
        this.items = items;
    }
}
