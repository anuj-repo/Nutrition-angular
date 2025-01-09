package com.fertilizer.DTO;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class EditProfileRequestDTO {
    @NotBlank(message = "First name is required")
    private String fname;
    
    @NotBlank(message = "Last name is required")
    private String lname;
    
    @NotBlank(message = "Mobile number is required")
    private String mobileNumber;
    
    private String address;
    private String city;
    private String state;
    private String country;
    private String pincode;
}
