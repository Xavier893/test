package com.example.clientportal.services;

import com.example.clientportal.dto.OrderDTO;
import com.example.clientportal.dto.OrderItemDTO;
import com.example.clientportal.mappers.OrderItemMapper;
import com.example.clientportal.mappers.OrderMapper;
import com.example.clientportal.model.Client;
import com.example.clientportal.model.Order;
import com.example.clientportal.model.OrderItem;
import com.example.clientportal.model.Product;
import com.example.clientportal.repositories.ClientRepository;
import com.example.clientportal.repositories.OrderItemRepository;
import com.example.clientportal.repositories.OrderRepository;
import com.example.clientportal.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final OrderItemMapper orderItemMapper;
    private final ClientRepository clientRepository;
    private final ProductRepository productRepository;
    private final OrderItemRepository orderItemRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository, OrderMapper orderMapper, OrderItemMapper orderItemMapper,
                        ClientRepository clientRepository, ProductRepository productRepository, OrderItemRepository orderItemRepository) {
        this.orderRepository = orderRepository;
        this.orderMapper = orderMapper;
        this.orderItemMapper = orderItemMapper;
        this.clientRepository = clientRepository;
        this.productRepository = productRepository;
        this.orderItemRepository = orderItemRepository;
    }

    public List<OrderDTO> getOrdersByClientId(Long clientId) {
        return orderRepository.findByClientId(clientId).stream()
                .map(orderMapper::toDto)
                .collect(Collectors.toList());
    }

    public OrderItem updateOrderItemQuantity(Long orderId, Long itemId, OrderItemDTO orderItemDTO) {
        OrderItem orderItem = orderItemRepository.findById(itemId).orElse(null);
        orderItem.setQuantity(orderItemDTO.getQuantity());
        return orderItemRepository.save(orderItem);
    }

    public Order createOrderWithItem(Long clientId, OrderItemDTO orderItemDTO) {
        Order order = new Order();
        order.setClient(clientRepository.findById(clientId).orElse(null));
        OrderItem orderItem = new OrderItem();
        OrderItem newOrderItem = orderItemMapper.toEntity(orderItemDTO);
        orderItem.setProduct(newOrderItem.getProduct());
        orderItem.setQuantity(orderItemDTO.getQuantity());
        orderItem.setOrder(order);
        order.getOrderItems().add(newOrderItem);
        return orderRepository.save(order);
    }

    public OrderDTO addOrderItem(Long orderId, OrderItemDTO orderItemDTO) {
        Order order = orderRepository.findById(orderId).orElse(null);
        if (order == null) {
            return null;
        }
        Product product = productRepository.findById(orderItemDTO.getProduct().getId()).orElse(null);
        if (product == null) {
            return null;
        }
        OrderItem orderItem = orderItemMapper.toEntity(orderItemDTO);
        orderItem.setOrder(order);
        orderItem.setProduct(product);
        order.getOrderItems().add(orderItem);
        return orderMapper.toDto(orderRepository.save(order));
    }

    public Order updateOrder(Long orderId, OrderDTO orderDTO) {
        Order order = orderRepository.findById(orderId)
                .orElse(null);

        order.getOrderItems().clear();
        for (OrderItemDTO itemDTO : orderDTO.getOrderItems()) {
            OrderItem orderItem = new OrderItem();
            orderItem.setProduct(productRepository.findById(itemDTO.getProduct().getId())
                    .orElse(null));
            orderItem.setQuantity(itemDTO.getQuantity());
            orderItem.setOrder(order);
            order.getOrderItems().add(orderItem);
        }

        return orderRepository.save(order);
    }

    public void deleteOrder(Long orderId) {
        orderRepository.deleteById(orderId);
    }
}
