package ru.geekbrains.persist.model;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
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

    @OneToOne(mappedBy = "product", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private MainPicture mainPicture;

    @Column(length = 2048)
    private String description;

    @Column(name = "short_description")
    private String shortDescription;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<OrderDetail> orderDetails = new ArrayList<>();

    public Product() {
    }

    public Product(Long id) {
        this.id = id;
    }

    public Product(Long id, BigDecimal cost) {
        this(id);
        this.cost = cost;
    }

    public Product(Long id, String name, BigDecimal cost) {
        this(id, cost);
        this.name = name;
    }

    public Product(Long id, String name, BigDecimal cost, Picture mainPicture) {
        this(id, name, cost);
        this.mainPicture = new MainPicture(this, mainPicture);
    }

    public Product(Long id, String name, BigDecimal cost, String shortDescription) {
        this(id, name, cost);
        this.shortDescription = shortDescription;
    }

    public Product(Long id, String name, BigDecimal cost, Category category, Brand brand) {
        this(id, name, cost);
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
        return mainPicture != null ? mainPicture.getPicture() : null;
    }

    public void setMainPicture(Picture mainPicture) {
        this.mainPicture = new MainPicture(this, mainPicture);
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

    public List<OrderDetail> getOrderProducts() {
        return orderDetails;
    }

    public void setOrderProducts(List<OrderDetail> orderDetails) {
        this.orderDetails = orderDetails;
    }
}
