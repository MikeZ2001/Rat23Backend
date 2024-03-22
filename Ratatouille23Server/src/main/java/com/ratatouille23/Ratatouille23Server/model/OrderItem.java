package com.ratatouille23.Ratatouille23Server.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.ratatouille23.Ratatouille23Server.model.enumeration.OrderItemStatus;
import jakarta.annotation.Nonnull;
import jakarta.persistence.*;
import jakarta.persistence.Table;

import java.util.Objects;

@Entity
@Table
public class OrderItem{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Nonnull
    private Integer quantity;

    private String particularRequests;

    @ManyToOne(optional = false)
    private Product product; //Product used in this order item

    @ManyToOne
    private Order order; //Order of this order item

    private OrderItemStatus orderItemStatus;

    public OrderItemStatus getOrderItemStatus() {
        return orderItemStatus;
    }

    public void setOrderItemStatus(OrderItemStatus orderItemStatus) {
        this.orderItemStatus = orderItemStatus;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getParticularRequests() {
        return particularRequests;
    }

    public void setParticularRequests(String particularRequests) {
        this.particularRequests = particularRequests;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OrderItem orderItem)) return false;
        return id.equals(orderItem.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "OrderItem{" +
                "id=" + id +
                ", quantity=" + quantity +
                ", particularRequests='" + particularRequests + '\'' +
                ", product=" + product +
                ", order=" + order +
                ", orderItemStatus=" + orderItemStatus +
                '}';
    }
}
