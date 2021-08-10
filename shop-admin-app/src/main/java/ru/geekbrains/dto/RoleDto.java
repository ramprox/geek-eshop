package ru.geekbrains.dto;

public class RoleDto {

    private Long id;

    private String name;

    public RoleDto(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public RoleDto() {
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

        RoleDto roleDto = (RoleDto) o;

        if (!id.equals(roleDto.id)) return false;
        return name.equals(roleDto.name);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + name.hashCode();
        return result;
    }
}
