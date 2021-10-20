package ru.geekbrains;

import com.opencsv.bean.CsvBindByPosition;

import java.math.BigDecimal;

public class ProductCsvBean {
    @CsvBindByPosition(position = 0)
    private String name;

    @CsvBindByPosition(position = 1)
    private BigDecimal cost;

    @CsvBindByPosition(position = 2)
    private String categoryName;

    @CsvBindByPosition(position = 3)
    private String brandName;

    @CsvBindByPosition(position = 4)
    private String shortDescription;

    @CsvBindByPosition(position = 5)
    private String description;

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

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
