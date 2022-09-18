package com.agency.demo.service;


import com.agency.demo.entity.Client;
import com.agency.demo.repository.ClientRepository;
import com.agency.demo.request.CreateClientRequest;
import com.agency.demo.request.UpdateClientRequest;
import com.agency.demo.response.ClientResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ClientService {

    private final ClientRepository clientRepository;

    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public List<ClientResponse> getAllClient() {
        return clientRepository.findAll().stream()
                .map(ClientResponse::new)
                .collect(Collectors.toList());
    }

    public Optional<ClientResponse> findByLastName(String lastName) {
        return clientRepository.findByLastName(lastName).map(ClientResponse::new);
    }

    public Optional<ClientResponse> findByPassport(String passport) {
        return clientRepository.findByPassport(passport).map(ClientResponse::new);
    }

    public ClientResponse addClient(final CreateClientRequest createClientRequest) {
        return new ClientResponse(clientRepository.save(Client.builder()
                .firstName(createClientRequest.getFirstName())
                .lastName(createClientRequest.getLastName())
                .passport(createClientRequest.getPassport())
                .build()));
    }


    public Optional<Client> updateClient(UpdateClientRequest updateClientRequest, String passport) {
        return clientRepository.findByPassport(passport)
                .map(client -> updateClient(client, updateClientRequest));
    }

    public boolean deleteClient(Long id) {
        return clientRepository.findById(id).map(client -> {
            clientRepository.delete(client);
            return true;
        }).orElse(false);
    }

    private Client updateClient(Client client, UpdateClientRequest updateClientRequest) {
        client.setFirstName(updateClientRequest.getFirstName());
        client.setLastName(updateClientRequest.getLastName());
        client.setPassport(updateClientRequest.getPassport());
        return saveClient(client);
    }

    private Client saveClient(Client client) {
        return clientRepository.save(client);
    }
}
