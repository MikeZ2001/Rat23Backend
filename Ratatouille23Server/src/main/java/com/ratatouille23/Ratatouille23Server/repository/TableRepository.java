package com.ratatouille23.Ratatouille23Server.repository;

import com.ratatouille23.Ratatouille23Server.model.StoreTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TableRepository extends JpaRepository<StoreTable, Long> {

    @Query("SELECT t from StoreTable t WHERE t.store.id = ?1 AND t.available = TRUE ORDER BY t.id ASC ")
    Optional<List<StoreTable>> getAllAvailableTableOfStore(Long storeid);


    @Query(value = "SELECT t FROM StoreTable t WHERE t.store.id = ?1 ORDER BY t.id ASC")
    Optional<List<StoreTable>> getAllTablesOfStore(Long id);
}
