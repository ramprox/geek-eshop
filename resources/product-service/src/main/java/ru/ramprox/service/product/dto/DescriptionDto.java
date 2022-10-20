package ru.ramprox.service.product.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Size;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class DescriptionDto {

    @Size(max = 256)
    private String small;

    @Size(max = 2048)
    private String full;

}
