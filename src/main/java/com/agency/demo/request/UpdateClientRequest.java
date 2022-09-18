package com.agency.demo.request;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateClientRequest {

    private Long id;
    private String firstName;
    private String lastName;
    private String passport;

}
