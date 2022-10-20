package ru.ramprox.service.product.controller.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
public class ProductDto {

    private Long id;

    private String name;

    private String price;

    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private Map<String, String> productDetails = new HashMap<>();

    public String addProductDetail(String key, String value) {
        return productDetails.put(key, value);
    }

    public String removeProductDetail(String key) {
        return productDetails.remove(key);
    }

    public Map<String, String> getProductDetails() {
        return Collections.unmodifiableMap(productDetails);
    }

}
