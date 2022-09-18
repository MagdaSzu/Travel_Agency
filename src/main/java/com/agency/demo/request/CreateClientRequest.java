package com.agency.demo.request;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateClientRequest {

    @NotBlank(message = "name is required")
    private String firstName;

    @NotBlank(message = "surname is required")
    private String lastName;

    @NotBlank(message = "passport number is required")
    private String passport;
}
