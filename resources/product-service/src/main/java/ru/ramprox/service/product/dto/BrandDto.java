package ru.ramprox.service.product.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import java.util.Objects;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BrandDto {

    private Long id;

    @NotBlank
    private String name;

    public BrandDto(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BrandDto brandDto = (BrandDto) o;
        return Objects.equals(id, brandDto.id) && Objects.equals(name, brandDto.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
