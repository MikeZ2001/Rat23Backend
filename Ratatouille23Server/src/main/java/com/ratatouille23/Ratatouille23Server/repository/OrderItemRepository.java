package com.ratatouille23.Ratatouille23Server.repository;

import com.ratatouille23.Ratatouille23Server.model.Order;
import com.ratatouille23.Ratatouille23Server.model.OrderItem;
import com.ratatouille23.Ratatouille23Server.model.enumeration.OrderItemStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem,Long> {

    @Query("SELECT item FROM OrderItem item where item.order.id=?1 order by item.id asc")
    Optional<List<OrderItem>> getAllOrderItemByOrderId(Long id);

    @Query("SELECT item FROM OrderItem item where item.id=?1")
    Optional<OrderItem> getOrderItemById(Long id);

    @Query("update OrderItem orderItem SET orderItem.quantity =?2 where orderItem.id = ?1")
    Optional<?> updateQuantity(Long id,Long quantity);

    @Modifying(clearAutomatically = true)
    @Query("update OrderItem orderItem SET orderItem.orderItemStatus =?1 where orderItem.id = ?2")
    void updateOrderItemStatus(OrderItemStatus orderItemStatus, Long id);
}
