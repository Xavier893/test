package com.example.clientportal.controllers;

import com.example.clientportal.dto.ClientDTO;
import com.example.clientportal.mappers.ClientMapper;
import com.example.clientportal.model.Client;
import com.example.clientportal.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clients")
public class ClientController {
    private final ClientService clientService;
    private final ClientMapper clientMapper;

    @Autowired
    public ClientController(ClientService clientService, ClientMapper clientMapper) {
        this.clientService = clientService;
        this.clientMapper = clientMapper;
    }

    @PostMapping
    public ResponseEntity<ClientDTO> createClient(@RequestBody ClientDTO clientDTO) {
        Client client = clientService.gerOrCreateClient(clientDTO.getEmail(), clientDTO.getName());
        return ResponseEntity.ok(clientMapper.toDto(client));
    }

}
