package com.agency.demo;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.agency.demo.repository.ClientRepository;
import com.agency.demo.request.CreateClientRequest;
import com.agency.demo.request.UpdateClientRequest;
import com.agency.demo.response.ClientResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
public class ControllerIntegrationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ClientRepository clientRepository;

    private final ObjectMapper objectMapper = new ObjectMapper();


    @Test
    public void shouldPostAndGetAllClients() throws Exception {

        mockMvc.perform(post("http://localhost:8080")
                        .content(objectMapper.writeValueAsBytes(CreateClientRequest.builder()
                                .firstName("Adam")
                                .lastName("Nowak")
                                .passport("AD1234567")
                                .build()))
                        .contentType("application/json"))
                .andExpect(status().isCreated());

        mockMvc.perform(post("http://localhost:8080")
                        .content(objectMapper.writeValueAsBytes(CreateClientRequest.builder()
                                .firstName("Jan")
                                .lastName("Nowakowski")
                                .passport("AD1254357")
                                .build()))
                        .contentType("application/json"))
                .andExpect(status().isCreated());


        var mvcResult = mockMvc.perform(get("http://localhost:8080"))
                .andExpect(status().isOk())
                .andReturn();

        var actualClient = Arrays.asList(objectMapper
                .readValue(mvcResult
                                .getResponse()
                                .getContentAsString(),
                        ClientResponse[].class));

        var expectedClient = List.of(ClientResponse.builder()
                        .id(1L)
                        .firstName("Adam")
                        .lastName("Nowak")
                        .passport("AD1234567")
                        .build(),
                ClientResponse.builder()
                        .id(2L)
                        .firstName("Jan")
                        .lastName("Nowakowski")
                        .passport("AD1254357")
                        .build());

        Assertions.assertEquals(expectedClient, actualClient);
    }

    @Test
    public void shouldEditClientsAndFindByPassportAndFindBySurname() throws Exception {

        mockMvc.perform(post("http://localhost:8080")
                        .content(objectMapper.writeValueAsBytes(CreateClientRequest.builder()
                                .firstName("Adam")
                                .lastName("Nowak")
                                .passport("AD1234567")
                                .build()))
                        .contentType("application/json"))
                .andExpect(status().isCreated());

        mockMvc.perform(put("/updateClient/AD1234567")
                        .content(objectMapper.writeValueAsBytes(UpdateClientRequest.builder()
                                .firstName("Adam2")
                                .lastName("Nowak2")
                                .passport("99999")
                                .build()))
                        .contentType("application/json"))
                .andExpect(status().isOk());

        var mvcResult = mockMvc.perform(get("/findByLastName/Nowak2"))
                .andExpect(status().isOk())
                .andReturn();

        var actualClient = objectMapper
                .readValue(mvcResult
                                .getResponse()
                                .getContentAsString(),
                        ClientResponse.class);

        var expectedClient = ClientResponse.builder()
                .id(1L)
                .firstName("Adam2")
                .lastName("Nowak2")
                .passport("99999")
                .build();

        Assertions.assertEquals(expectedClient, actualClient);
    }

    @Test
    public void shouldDeleteClientById() throws Exception {

        mockMvc.perform(post("http://localhost:8080")
                        .content(objectMapper.writeValueAsBytes(CreateClientRequest.builder()
                                .firstName("Adam")
                                .lastName("Nowak")
                                .passport("AD1234567")
                                .build()))
                        .contentType("application/json"))
                .andExpect(status().isCreated());

        mockMvc.perform(delete("/deleteClient/1"));

        Assertions.assertEquals(0, clientRepository.findAll().size());
    }

}
