package com.ratatouille23.Ratatouille23Server.repository;

import com.ratatouille23.Ratatouille23Server.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order,Long> {

    @Query("SELECT order FROM Order order where order.status = 0 AND order.storeTable.store.id = ?1 order by order.id ASC")
    Optional<List<Order>> getAllAcceptedOrders(Long storeId);

    @Query("SELECT order FROM Order order where order.status = 1 AND order.storeTable.store.id = ?1 order by order.id ASC ")
    Optional<List<Order>>  getAllInProgressOrders(Long storeId);

    @Query("SELECT order FROM Order order where order.status = 2 AND order.storeTable.store.id = ?1 order by order.id ASC")
    Optional<List<Order>> getAllReadyOrders(Long storeId);

    @Query("SELECT order FROM Order order where order.status = 3 AND order.storeTable.store.id = ?1 order by order.id ASC")
    Optional<List<Order>> getAllCompletedOrders(Long storeId);

    @Query("SELECT order FROM Order order where order.status = 4 AND order.storeTable.store.id = ?1 order by order.id ASC")
    Optional<List<Order>> getAllDeletedOrders(Long storeId);

    @Query("SELECT order FROM Order order where order.status = 5 AND order.storeTable.store.id = ?1 order by order.id ASC ")
    Optional<List<Order>> getAllPayedOrders(Long storeId);

    @Query("SELECT COUNT(ord) FROM Order ord WHERE ?1 IN (SELECT emp.id FROM ord.employeesOfTheOrder emp) AND ord.date=?2 AND ord.status = 5 AND ord.storeTable.store.id = ?3")
    Optional<Integer> getNumberOfOrdersOfEmployee(Long employeeId, String date, Long storeId);

    @Query("SELECT order FROM Order order WHERE order.status < 4 AND order.storeTable.id = ?1")
    Optional<Order> getCurrentOrderOfTable(Long id);

    @Query("SELECT order FROM Order order WHERE order.storeTable.id = ?1")
    Optional<List<Order>> getAllOrdersOfTable(Long tableId);
}
