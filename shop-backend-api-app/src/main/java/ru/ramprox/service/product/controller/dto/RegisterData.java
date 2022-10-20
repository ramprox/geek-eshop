package ru.ramprox.service.product.controller.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class RegisterData {

    @NotBlank(message = "Username must not be blank")
    private String username;

    @NotBlank(message = "Password must not be blank")
    private String password;

    @NotBlank(message = "Email must not be blank")
    private String email;

    @NotBlank(message = "Firstname must not be blank")
    private String firstName;

    @NotBlank(message = "Lastname must not be blank")
    private String lastName;

    @NotNull(message = "BirthDate is mandatory")
    @Past(message = "Date must be before now")
    private Date birthDay;

}
