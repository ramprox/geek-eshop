package ru.ramprox.service.product.database.entity;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.ramprox.common.entity.AbstractEntity;
import ru.ramprox.common.entity.NamedEntity;

import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "brands",
        indexes = @Index(columnList = "name", name = "brand_name_uq", unique = true))
@SequenceGenerator(name = AbstractEntity.SEQUENCE_GEN_NAME,
        sequenceName = "brands_seq_gen", allocationSize = 1)
public class Brand extends NamedEntity {

    public Brand(String name) {
        super(name);
    }

}
