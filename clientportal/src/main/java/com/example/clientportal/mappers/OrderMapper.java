package com.example.clientportal.mappers;

import com.example.clientportal.dto.OrderDTO;
import com.example.clientportal.model.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    @Mapping(target = "client.orders", ignore = true) // Prevent circular reference
    OrderDTO toDto(Order order);
    Order toEntity(OrderDTO orderDTO);
}