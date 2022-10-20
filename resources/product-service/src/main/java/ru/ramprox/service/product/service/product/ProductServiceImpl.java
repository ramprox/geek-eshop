package ru.ramprox.service.product.service.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ramprox.service.product.database.entity.Brand;
import ru.ramprox.service.product.database.entity.Category;
import ru.ramprox.service.product.database.entity.Description;
import ru.ramprox.service.product.database.entity.Product;
import ru.ramprox.service.product.database.repository.BrandRepository;
import ru.ramprox.service.product.database.repository.CategoryRepository;
import ru.ramprox.service.product.database.repository.ProductRepository;
import ru.ramprox.service.product.controller.param.Price;
import ru.ramprox.service.product.controller.param.ProductFilter;
import ru.ramprox.service.product.dto.DescriptionDto;
import ru.ramprox.service.product.dto.ListProductDto;
import ru.ramprox.service.product.dto.ProductDto;
import ru.ramprox.service.product.dto.SavingProductDto;
import ru.ramprox.service.product.util.exception.NotFoundException;
import ru.ramprox.service.product.database.specification.ProductSpecifications;

import javax.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    private final BrandRepository brandRepository;

    private final CategoryRepository categoryRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository,
                              BrandRepository brandRepository,
                              CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.brandRepository = brandRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<ListProductDto> findAll(ProductFilter params) {
        Specification<Product> spec = getSpecification(params);
        Sort sortedBy = Sort.by(params.getSortBy());
        if("desc".equals(params.getDirection())) {
            sortedBy = sortedBy.descending();
        }
        return productRepository.findAll(spec,
                        PageRequest.of(params.getPage() - 1, params.getSize(), sortedBy))
                .map(product -> {
                    ListProductDto productDto = new ListProductDto();
                    productDto.setId(product.getId());
                    productDto.setName(product.getName());
                    if(product.getPrice() != null) {
                        productDto.setPrice(product.getPrice().toString());
                    }
                    if(product.getDescription() != null) {
                        productDto.setShortDesc(product.getDescription().getSmall());
                    }
                    return productDto;
                }).stream().collect(Collectors.toList());
    }

    @Override
    public ProductDto findById(Long id) {
        return productRepository.findById(id)
                .map(product -> {
                    ProductDto productDto = new ProductDto();
                    productDto.setId(product.getId());
                    productDto.setName(product.getName());
                    if(product.getPrice() != null) {
                        productDto.setPrice(product.getPrice().toString());
                    }
                    Description description = product.getDescription();
                    if(description != null) {
                        DescriptionDto descriptionDto = new DescriptionDto();
                        descriptionDto.setSmall(product.getDescription().getSmall());
                        descriptionDto.setFull(product.getDescription().getFull());
                        productDto.setDescription(descriptionDto);
                    }
                    return productDto;
                }).orElseThrow(() -> getNotFoundException(null, id));
    }

    private Specification<Product> getSpecification(ProductFilter params) {
        Specification<Product> spec = Specification.where(null);
        if(params.getProductName() != null) {
            spec = spec.and(ProductSpecifications.productNamePrefix(params.getProductName()));
        }
        Price priceFilter = params.getPrice();
        if(priceFilter != null) {
            BigDecimal min = priceFilter.getMin();
            if(min != null) {
                spec = spec.and(ProductSpecifications.minPrice(min));
            }
            BigDecimal max = priceFilter.getMax();
            if(max != null) {
                spec = spec.and(ProductSpecifications.maxPrice(max));
            }
        }
        if(params.getCategoryId() != null) {
            spec = spec.and(ProductSpecifications.productCategory(params.getCategoryId()));
        }
        List<Long> brandIds = params.getBrandIds();
        if(brandIds.size() > 0) {
            Specification<Product> specBrands = Specification.where(null);
            for(Long brandId : brandIds) {
                specBrands = specBrands.or(ProductSpecifications.productBrands(brandId));
            }
            spec = spec.and(specBrands);
        }
        return spec;
    }

    @Override
    public SavingProductDto save(SavingProductDto productDto) {
        Brand brand = brandRepository.getById(productDto.getBrandId());
        Category category = categoryRepository.getById(productDto.getCategoryId());
        Product product = new Product(productDto.getName(), brand, category);
        String price = productDto.getPrice();
        if(price != null) {
            product.setPrice(new BigDecimal(price));
        }
        DescriptionDto descriptionDto = productDto.getDescription();
        if(descriptionDto != null) {
            Description description = new Description();
            description.setSmall(descriptionDto.getSmall());
            description.setFull(descriptionDto.getFull());
            product.setDescription(description);
        }
        product = productRepository.save(product);
        productDto.setId(product.getId());
        return productDto;
    }

    @Transactional
    @Override
    public SavingProductDto update(SavingProductDto productDto) {
        try {
            Brand brand = brandRepository.getById(productDto.getBrandId());
            Category category = categoryRepository.getById(productDto.getCategoryId());
            Product product = productRepository.getById(productDto.getId());
            product.setBrand(brand);
            product.setCategory(category);
            String price = productDto.getPrice();
            if(price != null) {
                product.setPrice(new BigDecimal(price));
            }
            DescriptionDto descriptionDto = productDto.getDescription();
            if(descriptionDto != null) {
                Description description = new Description();
                description.setSmall(descriptionDto.getSmall());
                description.setFull(descriptionDto.getFull());
                product.setDescription(description);
            }
            productDto.setId(product.getId());
            return productDto;
        } catch (EntityNotFoundException ex) {
            throw getNotFoundException(ex, productDto.getId());
        }
    }

    @Override
    public void deleteById(Long id) {
        try {
            productRepository.deleteById(id);
        } catch (EmptyResultDataAccessException ex) {
            throw getNotFoundException(ex, id);
        }
    }

    private NotFoundException getNotFoundException(Exception cause, Long brandId) {
        NotFoundException exception = new NotFoundException(String.format("Продукт с id = %d не найден", brandId));
        exception.initCause(cause);
        return exception;
    }

}
