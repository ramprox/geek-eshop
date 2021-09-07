package ru.geekbrains.dto;

import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ProductDto {
    private Long id;

    @NotBlank
    private String name;

    @PositiveOrZero
    @NotNull
    private BigDecimal cost;

    @NotNull
    private CategoryDto categoryDto;

    @NotNull
    private BrandDto brandDto;

    private List<Long> pictureIds = new ArrayList<>();

    private Long mainPictureId;

    private MultipartFile[] newPictures;

    private String description;

    private String shortDescription;

    public ProductDto() {
    }

    public ProductDto(Long id, String name, BigDecimal cost, Long mainPictureId) {
        this.id = id;
        this.name = name;
        this.cost = cost;
        this.mainPictureId = mainPictureId;
    }

    public ProductDto(Long id, String name, BigDecimal cost, CategoryDto categoryDto) {
        this.id = id;
        this.name = name;
        this.cost = cost;
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

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
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

    public MultipartFile[] getNewPictures() {
        return newPictures;
    }

    public void setNewPictures(MultipartFile[] newPictures) {
        this.newPictures = newPictures;
    }

    public List<Long> getPictureIds() {
        return pictureIds;
    }

    public void setPictureIds(List<Long> pictureIds) {
        this.pictureIds = pictureIds;
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

    public Long getMainPictureId() {
        return mainPictureId;
    }

    public void setMainPictureId(Long mainPictureId) {
        this.mainPictureId = mainPictureId;
    }
}
