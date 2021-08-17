package ru.geekbrains.dto;

import ru.geekbrains.persist.model.Brand;

import javax.validation.constraints.NotBlank;

public class BrandDto {
    private Long id;

    @NotBlank
    private String name;

    public BrandDto(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public BrandDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BrandDto brandDto = (BrandDto) o;
        if (id != null && !id.equals(brandDto.id)) return false;
        return name.equals(brandDto.name);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + name.hashCode();
        return result;
    }
}
