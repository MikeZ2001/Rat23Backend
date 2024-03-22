package com.ratatouille23.Ratatouille23Server.model;

import jakarta.annotation.Nonnull;
import jakarta.persistence.*;

import java.util.Objects;

@Entity
@jakarta.persistence.Table(name = "RestaurantTable")
public class Table {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Nonnull
    private String name;
    @Nonnull
    private Integer seatsNumber;
    @Nonnull
    private Boolean available;

    @ManyToOne(cascade = CascadeType.PERSIST)
    private Store store;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSeatsNumber() {
        return seatsNumber;
    }

    public void setSeatsNumber(Integer seatsNumber) {
        this.seatsNumber = seatsNumber;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }

//    public Collection<Order> getOrdersOfTheTable() {
//        return ordersOfTheTable;
//    }
//
//    public void setOrdersOfTheTable(Collection<Order> ordersOfTheTable) {
//        this.ordersOfTheTable = ordersOfTheTable;
//    }

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Table table)) return false;
        return id.equals(table.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
