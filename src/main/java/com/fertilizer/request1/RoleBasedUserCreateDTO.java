package com.fertilizer.request1;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RoleBasedUserCreateDTO {

    @NotBlank(message = "{validation.fname.NotBlank}")
    @Size(max = 250, message = "{validation.role.fname.Size}")
    private String firstName;

    @NotBlank(message = "{validation.lname.NotBlank}")
    @Size(max = 250, message = "{validation.role.lname.Size}")
    private String lastName;

    private Long id;
    
    private String designation;

    private String role;

    private String phoneNumber;

    private String confirmPassword;

    private String state;

    private String city;

    @NotBlank(message = "{validation.email.NotBlank}")
    @Size(max = 250, message = "{validation.fname.Size}")
    private String email;

    private String clients;

    private String captcha;

    @Pattern(regexp = "^(?=.{6,40})(?=.*[a-z])(?=.*[A-Z])(?=.*?[0-9])(?=.*[*@#$%^&?!_-]).*$", message = "{validation.password.PatternMessage}")
    private String password;
}
