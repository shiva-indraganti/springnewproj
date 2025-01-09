package com.example.orderservice.controller;

import com.example.orderservice.clients.FoodItemFeign;
import com.example.orderservice.model.FoodItem;
import com.example.orderservice.entity.Order;
import com.example.orderservice.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;
    
    @Autowired
    private FoodItemFeign foodItemFeign;
    
    @GetMapping("/food-item/{orderId}")
    public FoodItem getFoodItemByOrderId(@PathVariable Long orderId) {
    	Order order = orderService.getOrderById(orderId);
    	Long foodItemId = order.getFoodItemId();
    	FoodItem foodItemById = foodItemFeign.getFoodItemById(foodItemId);
    	return foodItemById;
    }

    @GetMapping
    public List<Order> getAllOrders() {
        return orderService.getAllOrders();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable Long id) {
        Order order = orderService.getOrderById(id);
        if (order == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(order);
    }

    @PostMapping
    public Order createOrder(@RequestBody Order order) {
        return orderService.saveOrder(order);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
        orderService.deleteOrder(id);
        return ResponseEntity.noContent().build();
    }
}
