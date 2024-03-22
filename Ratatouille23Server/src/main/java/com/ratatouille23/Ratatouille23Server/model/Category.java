package com.ratatouille23.Ratatouille23Server.model;



import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.annotation.Nonnull;
import jakarta.persistence.*;
import jakarta.persistence.Table;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Entity
@Table
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String description;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<Product> productsOfTheCategory; //Products in this category

    @ManyToOne(cascade = CascadeType.PERSIST)
    private Store store; //Store where the category is

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

    public List<Product> getProductsOfTheCategory() {
        return productsOfTheCategory;
    }

    public void setProductsOfTheCategory(List<Product> productsOfTheCategory) {
        this.productsOfTheCategory = productsOfTheCategory;
    }

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Category category)) return false;
        return id.equals(category.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Category{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", productsOfTheCategory=" + productsOfTheCategory +
                ", store=" + store +
                '}';
    }
}

