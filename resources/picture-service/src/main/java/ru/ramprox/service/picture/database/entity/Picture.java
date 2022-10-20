package ru.ramprox.service.picture.database.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.NaturalId;
import ru.ramprox.common.entity.AbstractEntity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "pictures",
        indexes = @Index(name = "storageUUID_uq_idx", columnList = "storage_uuid", unique = true))
@SequenceGenerator(name = AbstractEntity.SEQUENCE_GEN_NAME,
        sequenceName = "pictures_seq_gen", allocationSize = 1)
public class Picture extends AbstractEntity {

    @NotNull
    @NaturalId
    @Column(name = "storage_uuid", nullable = false)
    private String storageUUID;

    @Column(name = "name")
    private String name;

    @NotNull
    @Column(name = "content_type", nullable = false)
    private String contentType;

    @NotNull
    @Column(name = "product_id", nullable = false)
    private Long productId;

    public Picture(String storageUUID, String contentType) {
        this.storageUUID = storageUUID;
        this.contentType = contentType;
    }

    public Picture(String storageUUID, String contentType, Long productId) {
        this(storageUUID, contentType);
        this.productId = productId;
    }

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(!(o instanceof Picture)) return false;
        Picture other = (Picture) o;
        return Objects.equals(storageUUID, other.storageUUID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(storageUUID);
    }

}
