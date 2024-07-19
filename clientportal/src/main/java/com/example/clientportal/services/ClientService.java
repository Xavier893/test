package com.example.clientportal.services;

import com.example.clientportal.dto.ClientDTO;
import com.example.clientportal.mappers.ClientMapper;
import com.example.clientportal.model.Client;
import com.example.clientportal.repositories.ClientRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ClientService {
    private final ClientRepository clientRepository;
    private final ClientMapper clientMapper;

    @Autowired
    public ClientService(ClientRepository clientRepository, ClientMapper clientMapper) {
        this.clientRepository = clientRepository;
        this.clientMapper = clientMapper;
    }

    public Client gerOrCreateClient(String email, String name) {
        List<Client> clients = clientRepository.findByEmail(email);
        if (clients.isEmpty()) {
            Client client = new Client();
            client.setEmail(email);
            client.setName(name);
            return clientRepository.save(client);
        } else {
            return clients.getFirst();
        }
    }
}
