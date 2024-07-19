package com.example.clientportal.mappers;

import com.example.clientportal.dto.ProductDTO;
import com.example.clientportal.model.Product;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    ProductDTO toDto(Product product);
    Product toEntity(ProductDTO productDTO);
}
