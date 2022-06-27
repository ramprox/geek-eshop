package ru.geekbrains.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.geekbrains.controller.dto.ProductListParams;
import ru.geekbrains.controller.dto.BrandDto;
import ru.geekbrains.controller.dto.CategoryDto;
import ru.geekbrains.controller.dto.ProductDto;
import ru.geekbrains.persist.model.Brand;
import ru.geekbrains.persist.model.Category;
import ru.geekbrains.persist.model.Picture;
import ru.geekbrains.persist.model.Product;
import ru.geekbrains.persist.repositories.ProductRepository;
import ru.geekbrains.persist.specifications.ProductSpecifications;

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
                .map(product -> new ProductDto(product.getId(),
                        product.getName(),
                        product.getCost().toString(),
                        mapToCategoryDto(product.getCategory())))
                .collect(Collectors.toList());
    }

    @Override
    public Page<ProductDto> findWithFilter(ProductListParams listParams) {
        Specification<Product> spec = Specification.where(null);
        String productNameFilter = listParams.getProductName();
        BigDecimal minCostFilter = listParams.getMinCost();
        BigDecimal maxCostFilter = listParams.getMaxCost();
        Long categoryId = listParams.getCategoryId();
        String sortBy = listParams.getSortBy();
        List<Long> brandIds = listParams.getBrandIds();
        if (productNameFilter != null && !productNameFilter.isEmpty()) {
            spec = spec.and(ProductSpecifications.productNamePrefix(productNameFilter));
        }
        if (minCostFilter != null) {
            spec = spec.and(ProductSpecifications.minCost(minCostFilter));
        }
        if (maxCostFilter != null) {
            spec = spec.and(ProductSpecifications.maxCost(maxCostFilter));
        }
        if (categoryId != null && categoryId >= 0) {
            spec = spec.and(ProductSpecifications.productCategory(categoryId));
        }
        if(brandIds != null && brandIds.size() > 0) {
            Specification<Product> specBrands = Specification.where(null);
            for(Long brandId : brandIds) {
                specBrands = specBrands.or(ProductSpecifications.productBrands(brandId));
            }
            spec = spec.and(specBrands);
        }
        Sort sortedBy = sortBy != null && !sortBy.isEmpty() ? Sort.by(sortBy) : Sort.by("id");
        sortedBy = "desc".equals(listParams.getDirection()) ? sortedBy.descending() : sortedBy.ascending();

        return productRepository.findAllForBackend(spec,
                PageRequest.of(Optional.ofNullable(listParams.getPage()).orElse(1) - 1,
                        Optional.ofNullable(listParams.getSize()).orElse(10), sortedBy))
                .map(product -> {
                    ProductDto productDto = new ProductDto(product.getId(),
                            product.getName(), product.getCost().toString());
                    productDto.setShortDescription(product.getShortDescription());
                    Picture mainPicture = product.getMainPicture();
                    if(mainPicture != null) {
                        productDto.setMainPictureId(mainPicture.getId());
                    }
                    return productDto;
                });
    }

    private static CategoryDto mapToCategoryDto(Category category) {
        return new CategoryDto(category.getId(), category.getName());
    }

    private static BrandDto mapToBrandDto(Brand brand) {
        return new BrandDto(brand.getId(), brand.getName());
    }

    private static List<Long> mapToPictureIds(List<Picture> pictures) {
        return pictures.stream()
                .map(Picture::getId)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<ProductDto> findAllInfoById(Long id) {
        return productRepository.findAllInfoById(id)
                .map(product -> {
                    BrandDto brandDto = mapToBrandDto(product.getBrand());
                    ProductDto productDto = new ProductDto(product.getId(),
                            product.getName(),
                            product.getCost().toString(),
                            mapToCategoryDto(product.getCategory()));
                    productDto.setBrandDto(brandDto);
                    productDto.setPictureIds(mapToPictureIds(product.getPictures()));
                    productDto.setShortDescription(product.getShortDescription());
                    productDto.setDescription(product.getDescription());
                    return productDto;
                });
    }

    @Override
    public Optional<ProductDto> findByIdForCart(Long id) {
        return productRepository.findByIdForCart(id)
                .map(product -> new ProductDto(product.getId(),
                        product.getName(), product.getCost().toString()));
    }
}
