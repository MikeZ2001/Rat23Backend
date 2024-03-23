package com.ratatouille23.Ratatouille23Server.repository;

import com.ratatouille23.Ratatouille23Server.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product,Long> {

    @Query("SELECT prod FROM Product prod JOIN Category cat WHERE prod.category.id=?1 AND cat.store.id = ?2")
    Optional<List<Product>> getAllProductByCategoryId(Long id, Long storeId);

    @Query("SELECT prod FROM Product prod JOIN Category cat ON cat.id = prod.category.id WHERE cat.store.id=?1")
    Optional<List<Product>> getAllProductsOfStore(Long id);
}
