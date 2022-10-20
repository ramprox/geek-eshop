package ru.ramprox.common.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@MappedSuperclass
public abstract class NamedEntity extends AbstractEntity {

    @NotBlank
    @Column(nullable = false)
    private String name;

    protected NamedEntity(String name) {
        this.name = name;
    }

}
