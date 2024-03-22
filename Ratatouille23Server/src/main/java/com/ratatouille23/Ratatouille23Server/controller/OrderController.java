package com.ratatouille23.Ratatouille23Server.controller;

import com.ratatouille23.Ratatouille23Server.model.Order;
import com.ratatouille23.Ratatouille23Server.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(path = "api/v1/order")
public class OrderController {

    @Autowired
    private final OrderService orderService;

    public OrderController(OrderService orderService){
        this.orderService = orderService;
    }


    @GetMapping("/getAll")
    public ResponseEntity<List<Order>> getAllOrder(){

        List<Order> orders = orderService.getAllOrder();

        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @GetMapping("/getById/{id}")
    public ResponseEntity<?> getOrderById(@PathVariable("id") Long id){
        try{
            Order order = orderService.getOrderById(id);
            return new ResponseEntity<>(order,HttpStatus.OK);
        } catch (IllegalStateException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PostMapping("/add")
    public ResponseEntity<Order>addOrder(@RequestBody Order order){
        Order newOrder = orderService.addOrder(order);

        return new ResponseEntity<>(newOrder,HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{orderId}")
    public ResponseEntity<?> deleteOrder(@PathVariable("orderId") Long id){
        orderService.deleteOrder(id);

        return new ResponseEntity<>(HttpStatus.OK);

    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateOrder(@PathVariable("id") Long id,@RequestBody Order order){
        try{
            Order updatedOrder = orderService.updateOrder(id,order);
            return new ResponseEntity<>(updatedOrder,HttpStatus.OK);
        }catch (IllegalStateException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{storeId}/getAllAcceptedOrders")
    public ResponseEntity<?> getAllAcceptedOrders(@PathVariable("storeId") Long storeId){
        try {
            List<Order> acceptedOrders = orderService.getAllAcceptedOrders(storeId);
            return new ResponseEntity<>(acceptedOrders,HttpStatus.OK);
        }catch (IllegalStateException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{storeId}/getAllInProgressOrders")
    public ResponseEntity<?> getAllInProgressOrders(@PathVariable("storeId") Long storeId){
        try {
            List<Order> inProgressOrders = orderService.getAllInProgressOrders(storeId);
            return new ResponseEntity<>(inProgressOrders,HttpStatus.OK);
        } catch (IllegalStateException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{storeId}/getAllReadyOrders")
    public ResponseEntity<?> getAllReadyOrders(@PathVariable("storeId") Long storeId){
        try {
            List<Order> readyOrders = orderService.getAllReadyOrders(storeId);
            return new ResponseEntity<>(readyOrders,HttpStatus.OK);
        } catch (IllegalStateException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{storeId}/getAllCompletedOrders")
    public ResponseEntity<?> getAllCompletedOrders(@PathVariable("storeId") Long storeId){
        try {
            List<Order> completedOrders = orderService.getAllCompletedOrders(storeId);
            return new ResponseEntity<>(completedOrders,HttpStatus.OK);
        } catch (IllegalStateException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{storeId}/getAllDeletedOrders")
    public ResponseEntity<?> getAllDeletedOrders(@PathVariable("storeId") Long storeId){
        try {
            List<Order> deletedOrders = orderService.getAllDeletedOrders(storeId);
            return new ResponseEntity<>(deletedOrders,HttpStatus.OK);
        } catch (IllegalStateException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{storeId}/getAllPayedOrders")
    public ResponseEntity<?> getAllPayedOrders(@PathVariable("storeId") Long storeId){
        try {
            List<Order> deletedOrders = orderService.getAllPayedOrders(storeId);
            return new ResponseEntity<>(deletedOrders,HttpStatus.OK);
        } catch (IllegalStateException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{storeId}/getOrdersOfEmployee/{employeeId}/{date}")
    public ResponseEntity<Integer> getNumberOfOrdersOfEmployee(@PathVariable("storeId") Long storeId, @PathVariable("employeeId") Long employeeId, @PathVariable("date") LocalDate date){
        Integer count = orderService.countOrdersOfEmployee(storeId, employeeId, date);
        return new ResponseEntity<>(count, HttpStatus.OK);
    }

    @GetMapping("/getCurrentOrderOfTable/{id}")
    public ResponseEntity<Order> getCurrentOrderOfTable(@PathVariable("id") Long id){
        Order order = orderService.getCurrentOrderOfTable(id);
        return new ResponseEntity<>(order, HttpStatus.OK);
    }
}
