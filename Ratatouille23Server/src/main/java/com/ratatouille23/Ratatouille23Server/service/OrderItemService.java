package com.ratatouille23.Ratatouille23Server.service;

import com.ratatouille23.Ratatouille23Server.model.OrderItem;
import com.ratatouille23.Ratatouille23Server.repository.OrderItemRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import java.util.List;

@Service
public class OrderItemService {

    private final OrderItemRepository orderItemRepository;


    @Autowired
    public OrderItemService(OrderItemRepository orderItemRepository){
        this.orderItemRepository = orderItemRepository;
    }

    @GetMapping
    public List<OrderItem> getAllOrderItem(){
        return orderItemRepository.findAll();
    }

    @GetMapping
    public List<OrderItem> getAllOrderItemByOrderId(Long id) throws IllegalStateException{
        List<OrderItem> optionalOrderItem = orderItemRepository.getAllOrderItemByOrderId(id)
                .orElseThrow((()-> new IllegalStateException("Non esistono ordini con id "+id+".")));
        return optionalOrderItem;
    }

    @PostMapping
    public OrderItem addOrderItem(OrderItem orderItem){
        return orderItemRepository.save(orderItem);
    }

    @DeleteMapping
    public void deleteAllOrder(){
        orderItemRepository.deleteAll();
    }

    @DeleteMapping
    public void deleteOrderItemById(Long id){
        orderItemRepository.deleteById(id);
    }

    @PutMapping
    public void updateOrderItem(Long id, OrderItem orderItem) {
        orderItemRepository.updateOrderItem(Long.valueOf(orderItem.getQuantity()),id,orderItem.getOrderItemStatus());
    }

    @PutMapping
    public void updateOrderItemStatus(Long id,OrderItem orderItem){
        orderItemRepository.updateOrderItemStatus(orderItem.getOrderItemStatus(),id);
    }
}
