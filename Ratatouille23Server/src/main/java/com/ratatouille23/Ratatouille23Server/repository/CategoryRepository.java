package com.ratatouille23.Ratatouille23Server.repository;

import com.ratatouille23.Ratatouille23Server.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Long> {
    @Query(value = "SELECT cat FROM Category cat WHERE cat.store.id = ?1")
    Optional<List<Category>> getCategoriesOfStore(Long storeId);
}
