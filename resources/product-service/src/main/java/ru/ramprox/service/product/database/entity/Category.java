package ru.ramprox.service.product.database.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.ramprox.common.entity.AbstractEntity;
import ru.ramprox.common.entity.NamedEntity;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "categories",
        indexes = @Index(columnList = "name", name = "category_name_uq", unique = true))
@SequenceGenerator(name = AbstractEntity.SEQUENCE_GEN_NAME,
        sequenceName = "categories_seq_gen", allocationSize = 1)
public class Category extends NamedEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    public Category(String name) {
        super(name);
    }

}
