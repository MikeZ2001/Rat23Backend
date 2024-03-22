package com.ratatouille23.Ratatouille23Server.service;

import com.ratatouille23.Ratatouille23Server.model.Order;
import com.ratatouille23.Ratatouille23Server.repository.OrderRepository;
import com.ratatouille23.Ratatouille23Server.repository.TableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository){
        this.orderRepository = orderRepository;
    }

    @GetMapping
    public List<Order> getAllOrder() {
        return orderRepository.findAll();
    }

    @GetMapping
    public Order getOrderById(Long id) throws IllegalStateException{
        Order optionalOrder = orderRepository.findById(id)
                .orElseThrow(()->new IllegalStateException("L'ordine con id "+id+" non esiste."));

        return optionalOrder;
    }

    @PostMapping
    public Order addOrder(Order order) {
        return orderRepository.save(order);
    }

    @DeleteMapping
    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }

    @PutMapping
    public Order updateOrder(Long id, Order order) throws IllegalStateException{
        Order updateOrder = orderRepository.findById(id)
                .orElseThrow(()->new IllegalStateException("L'ordine con id "+id+" non esiste."));

        updateOrder.setNotes(order.getNotes());
        updateOrder.setDate(order.getDate());
        updateOrder.setTime(order.getTime());
        updateOrder.setStatus(order.getStatus());
        updateOrder.setTotal(order.getTotal());
        updateOrder.setItems(order.getItems());
        updateOrder.setEmployeesOfTheOrder(order.getEmployeesOfTheOrder());
        updateOrder.setTable(order.getTable());

        return orderRepository.save(updateOrder);
    }

    @GetMapping
    public List<Order> getAllAcceptedOrders(Long storeId) throws IllegalStateException{
        List<Order> orders = orderRepository.getAllAcceptedOrders(storeId)
                .orElseThrow(() -> new IllegalStateException("Impossibile trovare gli ordini accettati dell'attività con id "+storeId +"."));
        return orders;
    }

    @GetMapping
    public List<Order> getAllInProgressOrders(Long storeId) throws IllegalStateException{
        return orderRepository.getAllInProgressOrders(storeId)
                .orElseThrow(() -> new IllegalStateException("Impossibile trovare gli ordini in corso dell'attività con id "+storeId +"."));
    }

    @GetMapping
    public List<Order> getAllReadyOrders(Long storeId) throws IllegalStateException{
        return orderRepository.getAllReadyOrders(storeId)
                .orElseThrow(() -> new IllegalStateException("Impossibile trovare gli ordini pronti dell'attività con id "+storeId +"."));
    }

    @GetMapping
    public List<Order> getAllCompletedOrders(Long storeId) throws IllegalStateException{
        return orderRepository.getAllCompletedOrders(storeId)
                .orElseThrow(() -> new IllegalStateException("Impossibile trovare gli ordini completati dell'attività con id "+storeId +"."));
    }

    @GetMapping
    public List<Order> getAllDeletedOrders(Long storeId) throws IllegalStateException{
        return orderRepository.getAllDeletedOrders(storeId)
                .orElseThrow(() -> new IllegalStateException("Impossibile trovare gli ordini annullati dell'attività con id "+storeId +"."));
    }

    @GetMapping
    public List<Order> getAllPayedOrders(Long storeId) throws IllegalStateException {
        return orderRepository.getAllPayedOrders(storeId)
                .orElseThrow(() -> new IllegalStateException("Impossibile trovare gli ordini pagati dell'attività con id "+storeId +"."));
    }

    @GetMapping
    public Integer countOrdersOfEmployee(Long storeId, Long employeeId, LocalDate date){
        ZoneId zoneId = ZoneId.of("Europe/Rome");
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy").withZone(zoneId);
        return orderRepository.getNumberOfOrdersOfEmployee(employeeId, date.format(dateFormat), storeId).orElse(0);
    }

    @GetMapping
    public Order getCurrentOrderOfTable(Long id) {
        return orderRepository.getCurrentOrderOfTable(id).orElse(null);
    }
}
