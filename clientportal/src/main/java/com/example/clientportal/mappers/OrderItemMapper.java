package com.example.clientportal.mappers;

import com.example.clientportal.dto.OrderItemDTO;
import com.example.clientportal.model.OrderItem;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderItemMapper {
    OrderItemDTO toDto(OrderItem orderItem);
    OrderItem toEntity(OrderItemDTO orderItemDTO);
}
