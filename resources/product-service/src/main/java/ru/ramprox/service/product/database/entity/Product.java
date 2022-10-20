package ru.ramprox.service.product.database.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import ru.ramprox.common.entity.AbstractEntity;
import ru.ramprox.common.entity.NamedEntity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "products")
@SequenceGenerator(name = AbstractEntity.SEQUENCE_GEN_NAME,
        sequenceName = "products_seq_gen", allocationSize = 1)
public class Product extends NamedEntity {

    @Column(name = "price")
    private BigDecimal price;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_products_categories_id"))
    private Category category;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "brand_id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_products_brands_id"))
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Brand brand;

    private Description description;

    public Product(String name, Brand brand, Category category) {
        setName(name);
        this.brand = brand;
        this.category = category;
    }

}
