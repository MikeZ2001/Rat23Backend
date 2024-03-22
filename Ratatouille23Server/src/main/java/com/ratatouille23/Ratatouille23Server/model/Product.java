package com.ratatouille23.Ratatouille23Server.model;


import io.hypersistence.utils.hibernate.type.array.ListArrayType;
import jakarta.annotation.Nonnull;
import jakarta.persistence.*;
import jakarta.persistence.Table;
import org.hibernate.annotations.Type;

import java.util.List;
import java.util.Objects;

@Entity
@Table
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Nonnull
    private String name;
    private String description;
    @Nonnull
    private double price;

    @Type(ListArrayType.class)
    @Column(name = "Allergens", columnDefinition = "VARCHAR(255)")
    private List<String> allergens;

    @ManyToOne
    private Category categoryOfTheProduct; //Category of this product

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Category getCategoryOfTheProduct() {
        return categoryOfTheProduct;
    }

    public void setCategoryOfTheProduct(Category categoryOfTheProduct) {
        this.categoryOfTheProduct = categoryOfTheProduct;
    }

    public List<String> getAllergens() {
        return allergens;
    }

    public void setAllergens(List<String> allergens) {
        this.allergens = allergens;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Product product)) return false;
        return id.equals(product.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", allergens=" + allergens +
                ", categoryOfTheProduct=" + categoryOfTheProduct +
                '}';
    }
}
