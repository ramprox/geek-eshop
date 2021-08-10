package ru.geekbrains.dto;

import java.math.BigDecimal;

public class ProductListParams {
    private String productNameFilter;
    private BigDecimal minCostFilter;
    private BigDecimal maxCostFilter;
    private Integer page;
    private Integer size;
    private String sortBy;
    private String direction;

    public String getProductNameFilter() {
        return productNameFilter;
    }

    public void setProductNameFilter(String productNameFilter) {
        this.productNameFilter = productNameFilter;
    }

    public BigDecimal getMinCostFilter() {
        return minCostFilter;
    }

    public void setMinCostFilter(BigDecimal minCostFilter) {
        this.minCostFilter = minCostFilter;
    }

    public BigDecimal getMaxCostFilter() {
        return maxCostFilter;
    }

    public void setMaxCostFilter(BigDecimal maxCostFilter) {
        this.maxCostFilter = maxCostFilter;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public String getSortBy() {
        return sortBy;
    }

    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }
}
