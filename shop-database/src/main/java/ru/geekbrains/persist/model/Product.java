package ru.geekbrains.persist.model;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "cost", nullable = false)
    private BigDecimal cost;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false, foreignKey = @ForeignKey(name = "fk_products_categories_id"))
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "brand_id", nullable = false, foreignKey = @ForeignKey(name = "fk_products_brands_id"))
    private Brand brand;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<Picture> pictures = new ArrayList<>();

    @OneToOne
    @JoinTable(name = "main_pictures",
            joinColumns = {@JoinColumn(name = "product_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "picture_id", referencedColumnName = "id")})
    private Picture mainPicture;

    @Column(length = 2048)
    private String description;

    @Column(name = "short_description")
    private String shortDescription;

    public Product() {
    }

    public Product(Long id, String name, BigDecimal cost, Picture mainPicture) {
        this.id = id;
        this.name = name;
        this.cost = cost;
        this.mainPicture = mainPicture;
    }

    public Product(Long id, String name, BigDecimal cost, String shortDescription) {
        this.id = id;
        this.name = name;
        this.cost = cost;
        this.shortDescription = shortDescription;
    }

    public Product(Long id, String name, BigDecimal cost, Category category, Brand brand) {
        this.id = id;
        this.name = name;
        this.cost = cost;
        this.category = category;
        this.brand = brand;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Brand getBrand() {
        return brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }

    public List<Picture> getPictures() {
        return pictures;
    }

    public void setPictures(List<Picture> pictures) {
        this.pictures = pictures;
    }

    public Picture getMainPicture() {
        return mainPicture;
    }

    public void setMainPicture(Picture mainPicture) {
        this.mainPicture = mainPicture;
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
}
