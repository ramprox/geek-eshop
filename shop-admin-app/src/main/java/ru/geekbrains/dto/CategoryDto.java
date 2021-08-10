package ru.geekbrains.dto;

import javax.validation.constraints.NotBlank;

public class CategoryDto {

    private Long id;

    @NotBlank
    private String name;

    public CategoryDto(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public CategoryDto() {
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
        CategoryDto categoryDto = (CategoryDto) o;
        if (id != null && !id.equals(categoryDto.id)) return false;
        return name.equals(categoryDto.name);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + name.hashCode();
        return result;
    }
}
