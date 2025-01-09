package com.fertilizer.request1;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
public class RoleBasedUpdateUserDTO {

    private String firstName;

    private String lastName;

    private String phoneNumber;

    private String billingState;

    private String billingCity;

    private String billingAddress;

    private String billingPinCode;

    private String role;

    private String designation;

    private String clients;
}
