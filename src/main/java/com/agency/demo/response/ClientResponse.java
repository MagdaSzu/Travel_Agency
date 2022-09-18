package com.agency.demo.response;

import com.agency.demo.entity.Client;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode
public class ClientResponse {
    private Long id;
    private String firstName;
    private String lastName;
    private String passport;


    public ClientResponse(Client client) {
        this.id = client.getId();
        this.firstName = client.getFirstName();
        this.lastName = client.getLastName();
        this.passport = client.getPassport();
    }
}
