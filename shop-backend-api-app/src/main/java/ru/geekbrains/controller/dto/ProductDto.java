package ru.geekbrains.controller.dto;

import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.util.ArrayList;
import java.util.List;

@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS)
public class ProductDto {
    private Long id;

    private String name;

    private String description;

    private String shortDescription;

    private String cost;

    private CategoryDto categoryDto;

    private BrandDto brandDto;

    private List<Long> pictureIds = new ArrayList<>();

    private Long mainPictureId;

    public ProductDto() {
    }

    public ProductDto(String name, String cost) {
        this.name = name;
        this.cost = cost;
    }

    public ProductDto(Long id, String name, String cost) {
        this(name, cost);
        this.id = id;
    }

    public ProductDto(Long id, String name, String cost, CategoryDto categoryDto) {
        this(id, name, cost);
        this.categoryDto = categoryDto;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public CategoryDto getCategoryDto() {
        return categoryDto;
    }

    public void setCategoryDto(CategoryDto categoryDto) {
        this.categoryDto = categoryDto;
    }

    public BrandDto getBrandDto() {
        return brandDto;
    }

    public void setBrandDto(BrandDto brandDto) {
        this.brandDto = brandDto;
    }

    public List<Long> getPictureIds() {
        return pictureIds;
    }

    public void setPictureIds(List<Long> pictureIds) {
        this.pictureIds = pictureIds;
    }

    public Long getMainPictureId() {
        return mainPictureId;
    }

    public void setMainPictureId(Long mainPictureId) {
        this.mainPictureId = mainPictureId;
    }
}
