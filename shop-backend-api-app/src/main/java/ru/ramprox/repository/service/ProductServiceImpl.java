package ru.ramprox.repository.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.ramprox.service.product.controller.dto.ProductListParams;
import ru.ramprox.service.product.controller.dto.ProductDto;
import ru.ramprox.persist.model.Product;
import ru.ramprox.persist.repositories.ProductRepository;
import ru.ramprox.persist.specifications.ProductSpecifications;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Page<ProductDto> findAll(ProductListParams listParams) {
        Specification<Product> spec = getSpecification(listParams);
        Sort sortedBy = Sort.by(listParams.getSortBy());
        if("desc".equals(listParams.getDirection())) {
            sortedBy = sortedBy.descending();
        }
        return productRepository.findAll(spec,
                PageRequest.of(listParams.getPage() - 1, listParams.getSize(), sortedBy))
                .map(product -> {
                    ProductDto productDto = new ProductDto();
                    productDto.setId(product.getId());
                    productDto.setName(product.getName());
                    productDto.setPrice(product.getPrice().toString());
                    return productDto;
                });
    }

    @Override
    public Optional<ProductDto> findById(Long id) {
        return productRepository.findById(id)
                .map(product -> {
                    ProductDto productDto = new ProductDto();
                    productDto.setId(product.getId());
                    productDto.setName(product.getName());
                    productDto.setPrice(product.getPrice().toString());
                    return productDto;
                });
    }

    @Override
    public Optional<ProductDto> findByIdForCart(Long id) {
        return null;
//        return productRepository.findIdNameCostById(id)
//                .map(product -> new ProductDto(product.getId(),
//                        product.getName(), product.getPrice().toString()));
    }

    private Specification<Product> getSpecification(ProductListParams params) {
        Specification<Product> spec = Specification.where(null);
        String name = params.getProductName();
        BigDecimal minPrice = params.getMinPrice();
        BigDecimal maxPrice = params.getMaxPrice();
        Long categoryId = params.getCategoryId();
        List<Long> brandIds = params.getBrandIds();
        if(name != null && !name.isBlank()) {
            spec = spec.and(ProductSpecifications.productNamePrefix(name));
        }
        if(minPrice != null) {
            spec = spec.and(ProductSpecifications.minPrice(minPrice));
        }
        if(maxPrice != null) {
            spec = spec.and(ProductSpecifications.maxPrice(maxPrice));
        }
        if(categoryId != null) {
            spec = spec.and(ProductSpecifications.productCategory(categoryId));
        }
        if(brandIds.size() > 0) {
            Specification<Product> specBrands = Specification.where(null);
            for(Long brandId : brandIds) {
                specBrands = specBrands.or(ProductSpecifications.productBrands(brandId));
            }
            spec = spec.and(specBrands);
        }
        return spec;
    }
}
