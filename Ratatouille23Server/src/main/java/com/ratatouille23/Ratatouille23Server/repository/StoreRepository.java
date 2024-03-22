package com.ratatouille23.Ratatouille23Server.repository;

import com.ratatouille23.Ratatouille23Server.model.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface StoreRepository extends JpaRepository<Store,Long> {
//    @Query("SELECT * FROM Store s JOIN Employee e ON s.id = e.store_id WHERE e.id = ?1")
//    Optional<Store> getStoreByEmployeeId(Long id);
}
