package com.example.clientportal.controllers;

import com.example.clientportal.dto.OrderDTO;
import com.example.clientportal.dto.OrderItemDTO;
import com.example.clientportal.mappers.ClientMapper;
import com.example.clientportal.mappers.OrderItemMapper;
import com.example.clientportal.mappers.OrderMapper;
import com.example.clientportal.model.Order;
import com.example.clientportal.model.OrderItem;
import com.example.clientportal.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    private final OrderService orderService;
    private final OrderItemMapper orderItemMapper;
    private final OrderMapper orderMapper;

    @Autowired
    public OrderController(OrderService orderService, OrderItemMapper orderItemMapper, OrderMapper orderMapper) {
        this.orderService = orderService;
        this.orderItemMapper = orderItemMapper;
        this.orderMapper = orderMapper;
    }

    @GetMapping("/client/{clientId}")
    public List<OrderDTO> getOrdersByClientId(@PathVariable Long clientId) {
        return orderService.getOrdersByClientId(clientId);
    }

    @PostMapping("/{clientId}")
    public ResponseEntity<OrderDTO> createOrder(@PathVariable Long clientId, @RequestBody OrderItemDTO orderDTO) {
        Order newOrder = orderService.createOrderWithItem(clientId, orderDTO);
        return ResponseEntity.ok(orderMapper.toDto(newOrder));
    }

    @PutMapping("/{orderId}/items")
    public OrderDTO addOrderItem(@PathVariable Long orderId, @RequestBody OrderItemDTO orderItemDTO) {

        return orderService.addOrderItem(orderId, orderItemDTO);
    }

    @PutMapping("/{orderId}/items/{itemId}")
    public ResponseEntity<OrderItemDTO> updateOrderItemQuantity(@PathVariable Long orderId, @PathVariable Long itemId, @RequestBody OrderItemDTO orderItemDTO) {
        OrderItem updatedOrderItem = orderService.updateOrderItemQuantity(orderId, itemId, orderItemDTO);
        return ResponseEntity.ok(orderItemMapper.toDto(updatedOrderItem));
    }

    @PutMapping("/{orderId}")
    public ResponseEntity<OrderDTO> updateOrder(@PathVariable Long orderId, @RequestBody OrderDTO orderDTO) {
        Order updatedOrder = orderService.updateOrder(orderId, orderDTO);
        return ResponseEntity.ok(orderMapper.toDto(updatedOrder));
    }

    @DeleteMapping("/{orderId}")
    public void deleteOrder(@PathVariable Long orderId) {
        orderService.deleteOrder(orderId);
    }
}
