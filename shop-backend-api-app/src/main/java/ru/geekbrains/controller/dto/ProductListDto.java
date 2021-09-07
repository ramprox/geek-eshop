package ru.geekbrains.controller.dto;

import org.springframework.data.domain.Page;

import java.util.List;

public class ProductListDto {
    private List<BrandDto> brands;
    private List<CategoryDto> categories;
    private Page<ProductDto> page;

    public ProductListDto() {
    }

    public List<BrandDto> getBrands() {
        return brands;
    }

    public void setBrands(List<BrandDto> brands) {
        this.brands = brands;
    }

    public Page<ProductDto> getPage() {
        return page;
    }

    public void setPage(Page<ProductDto> page) {
        this.page = page;
    }

    public List<CategoryDto> getCategories() {
        return categories;
    }

    public void setCategories(List<CategoryDto> categories) {
        this.categories = categories;
    }
}
