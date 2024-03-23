package com.ratatouille23.Ratatouille23Server.controller;
import com.ratatouille23.Ratatouille23Server.model.Order;
import com.ratatouille23.Ratatouille23Server.model.OrderItem;
import com.ratatouille23.Ratatouille23Server.service.OrderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "api/v1/orderItem")
public class OrderItemController {

    @Autowired
    private final OrderItemService orderItemService;

    public OrderItemController(OrderItemService orderItemService){
        this.orderItemService = orderItemService;
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<OrderItem>> getAllOrderItems(){
        List<OrderItem> orderItems = orderItemService.getAllOrderItem();
        return new ResponseEntity<>(orderItems, HttpStatus.OK);
    }

    @GetMapping("/getAllByOrderId/{id}")
    public ResponseEntity<?> getAllOrderItemByOrderId(@PathVariable("id")Long id){
        try {
            List<OrderItem> orderItems = orderItemService.getAllOrderItemByOrderId(id);
            return new ResponseEntity<>(orderItems,HttpStatus.OK);
        } catch (IllegalStateException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/add")
    public ResponseEntity<OrderItem>addOrderItem(@RequestBody OrderItem orderItem){
        OrderItem newOrderItem = orderItemService.addOrderItem(orderItem);
        return new ResponseEntity<>(newOrderItem,HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteOrderItemById(@PathVariable("id") Long id){
        orderItemService.deleteOrderItemById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @DeleteMapping("/deleteAll")
    public ResponseEntity<?> deleteAllOrderItem(){
        orderItemService.deleteAllOrder();
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateOrderItem(@PathVariable("id") Long id,@RequestBody OrderItem orderItem){
        OrderItem updatedOrderItem = orderItemService.updateOrderItem(id,orderItem);
        return new ResponseEntity<>(updatedOrderItem, HttpStatus.OK);
    }


    @PutMapping("/updateOrderItemStatus/{id}")
    public ResponseEntity<Void> updateOrderItemStatus(@PathVariable("id") Long id,@RequestBody OrderItem orderItem){
        orderItemService.updateOrderItemStatus(id,orderItem);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
