package ru.ramprox.service.product.database.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class Description {

    @Size(max = 256)
    @Column(name = "short_description")
    private String small;

    @Size(max = 2048)
    @Column(name = "full_description", length = 2048)
    private String full;

}
