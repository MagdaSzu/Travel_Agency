package com.agency.demo.controller;


import com.agency.demo.request.CreateClientRequest;
import com.agency.demo.request.UpdateClientRequest;
import com.agency.demo.response.ClientResponse;
import com.agency.demo.service.ClientService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class OfficeApplicationController {

    private final ClientService clientService;

    public OfficeApplicationController(ClientService clientService) {
        this.clientService = clientService;

    }

    @GetMapping
    public ResponseEntity<List<ClientResponse>> getAllClient() {
        return new ResponseEntity<>(clientService.getAllClient(), HttpStatus.OK);
    }

    @GetMapping("/findByLastName/{lastName}")
    public ResponseEntity<ClientResponse> findByLastName(@PathVariable String lastName) {
        return clientService.findByLastName(lastName).map(client -> new ResponseEntity<>(client, HttpStatus.OK)).
                orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/findByPassport/{passport}")
    public ResponseEntity<ClientResponse> findByPassport(@PathVariable String passport) {
        return clientService.findByPassport(passport).map(customer -> new ResponseEntity<>(customer, HttpStatus.OK)).
                orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<ClientResponse> addClient(@Valid @RequestBody CreateClientRequest createClientRequest) {
      return new ResponseEntity<>(clientService.addClient(createClientRequest), HttpStatus.CREATED);
    }

    @PutMapping("/updateClient/{passport}")
    public ResponseEntity<ClientResponse> updatePassport(@Valid @RequestBody UpdateClientRequest updateClientRequest, @PathVariable String passport) {
        return clientService.updateClient(updateClientRequest, passport)
                .map(client -> new ResponseEntity<>(new ClientResponse(client), HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/deleteClient/{id}")
    public ResponseEntity<ClientResponse> deleteClient(@PathVariable Long id) {
        return clientService.deleteClient(id) ?
                new ResponseEntity<>(HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
