package ru.geekbrains.persist.model;

import javax.persistence.*;

@Entity
@Table(name = "main_pictures")
public class MainPicture {

    @Id
    @Column(name = "product_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "product_id",
            foreignKey = @ForeignKey(name = "fk_main_pictures_products_id"))
    private Product product;

    @OneToOne
    @JoinColumn(name = "picture_id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_main_pictures_pictures_id"))
    private Picture picture;

    public MainPicture() {
    }

    public MainPicture(Product product, Picture picture) {
        this.product = product;
        this.picture = picture;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Picture getPicture() {
        return picture;
    }

    public void setPicture(Picture picture) {
        this.picture = picture;
    }
}
