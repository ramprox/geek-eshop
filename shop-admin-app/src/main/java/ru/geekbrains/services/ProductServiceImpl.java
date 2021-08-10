package ru.geekbrains.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.geekbrains.dto.CategoryDto;
import ru.geekbrains.dto.ProductDto;
import ru.geekbrains.dto.ProductListParams;
import ru.geekbrains.persist.*;

import javax.persistence.criteria.CriteriaBuilder;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<ProductDto> findAll() {
        return productRepository.findAll().stream()
                .map(product -> new ProductDto(product.getId(), product.getName(), product.getCost(), mapToCategoryDto(product.getCategory())))
                .collect(Collectors.toList());
    }

    @Override
    public Page<ProductDto> findWithFilter(ProductListParams listParams) {
        Specification<Product> spec = ProductSpecifications.fetchCategory();
        String productNameFilter = listParams.getProductNameFilter();
        if(productNameFilter != null && !productNameFilter.isBlank()) {
            spec = spec.and(ProductSpecifications.productNamePrefix(productNameFilter));
        }
        BigDecimal minCostFilter = listParams.getMinCostFilter();
        if(minCostFilter != null) {
            spec = spec.and(ProductSpecifications.minCost(minCostFilter));
        }
        BigDecimal maxCostFilter = listParams.getMaxCostFilter();
        if(maxCostFilter != null) {
            spec = spec.and(ProductSpecifications.maxCost(maxCostFilter));
        }
        String sortBy = listParams.getSortBy();
        Sort sortedBy = sortBy != null && !sortBy.isBlank() ? Sort.by(sortBy) : Sort.by("id");
        sortedBy = "desc".equals(listParams.getDirection()) ? sortedBy.descending() : sortedBy.ascending();

        return productRepository.findAll(spec,
                PageRequest.of(Optional.ofNullable(listParams.getPage()).orElse(1) - 1,
                        Optional.ofNullable(listParams.getSize()).orElse(10), sortedBy))
                .map(product -> new ProductDto(product.getId(), product.getName(), product.getCost(), mapToCategoryDto(product.getCategory())));
    }

    private static CategoryDto mapToCategoryDto(Category category) {
        return new CategoryDto(category.getId(), category.getName());
    }

    @Override
    public void save(ProductDto productDto) {
        Product product = new Product(
                productDto.getId(),
                productDto.getName(),
                productDto.getCost(),
                new Category(productDto.getCategoryDto().getId(), productDto.getCategoryDto().getName()));
        productRepository.save(product);
    }

    @Override
    public Optional<ProductDto> findById(Long id) {
        return productRepository.findById(id)
                .map(product -> new ProductDto(product.getId(), product.getName(), product.getCost(), mapToCategoryDto(product.getCategory())));
    }

    @Override
    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }

}
