package com.example.clientportal.mappers;

import com.example.clientportal.dto.ClientDTO;
import com.example.clientportal.model.Client;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ClientMapper {
    @Mapping(target = "orders", ignore = true)
    ClientDTO toDto(Client client);
    Client toEntity(ClientDTO clientDTO);
}
