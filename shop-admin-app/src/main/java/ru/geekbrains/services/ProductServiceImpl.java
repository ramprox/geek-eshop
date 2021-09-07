package ru.geekbrains.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.geekbrains.dto.BrandDto;
import ru.geekbrains.dto.CategoryDto;
import ru.geekbrains.dto.ProductDto;
import ru.geekbrains.dto.ProductListParams;
import ru.geekbrains.interfaces.ProductService;
import ru.geekbrains.persist.model.Brand;
import ru.geekbrains.persist.model.Category;
import ru.geekbrains.persist.model.Picture;
import ru.geekbrains.persist.model.Product;
import ru.geekbrains.persist.repositories.ProductRepository;
import ru.geekbrains.persist.specifications.ProductSpecifications;
import ru.geekbrains.service.PictureService;


import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final PictureService pictureService;

    private static final Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, PictureService pictureService) {
        this.productRepository = productRepository;
        this.pictureService = pictureService;
    }

    @Override
    public List<ProductDto> findAll() {
        return productRepository.findAll().stream()
                .map(product -> new ProductDto(product.getId(),
                        product.getName(),
                        product.getCost(),
                        mapToCategoryDto(product.getCategory())))
                .collect(Collectors.toList());
    }

    @Override
    public Page<ProductDto> findWithFilter(ProductListParams listParams) {
        Specification<Product> spec = Specification.where(null);
        String productNameFilter = listParams.getProductName();
        BigDecimal minCostFilter = listParams.getMinCost();
        BigDecimal maxCostFilter = listParams.getMaxCost();
        String sortBy = listParams.getSortBy();
        Long categoryId = listParams.getCategoryId();
        List<Long> brandIds = listParams.getBrandIds();
        if(productNameFilter != null && !productNameFilter.isEmpty()) {
            spec = spec.and(ProductSpecifications.productNamePrefix(productNameFilter));
        }
        if(minCostFilter != null) {
            spec = spec.and(ProductSpecifications.minCost(minCostFilter));
        }
        if(maxCostFilter != null) {
            spec = spec.and(ProductSpecifications.maxCost(maxCostFilter));
        }
        if(categoryId != null && categoryId >= 0) {
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

        return productRepository.findAllForAdmin(spec,
                PageRequest.of(Optional.ofNullable(listParams.getPage()).orElse(1) - 1,
                        Optional.ofNullable(listParams.getSize()).orElse(10), sortedBy))
                .map(product -> new ProductDto(product.getId(), product.getName(), product.getCost(), product.getMainPicture().getId()));
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
    @Transactional
    public void save(ProductDto productDto) {
        CategoryDto categoryDto = productDto.getCategoryDto();
        BrandDto brandDto = productDto.getBrandDto();
        Product product = new Product(
                productDto.getId(),
                productDto.getName(),
                productDto.getCost(),
                new Category(categoryDto.getId(), categoryDto.getName()),
                new Brand(brandDto.getId(), brandDto.getName()));
        product.setBrand(new Brand(brandDto.getId(), brandDto.getName()));
        product.setDescription(productDto.getDescription());
        product.setShortDescription(productDto.getShortDescription());
        if(productDto.getMainPictureId() != null) {
            product.setMainPicture(pictureService.findById(productDto.getMainPictureId()).get());
        }

        List<Long> pictureIds = productDto.getPictureIds();
        List<Picture> pictures = pictureService.findByProductId(productDto.getId());

        try {
            for (Picture picture : pictures) {
                if (pictureIds.contains(picture.getId())) {
                    product.getPictures().add(picture);
                } else {
                    pictureService.deleteById(picture.getId());
                }
            }
        } catch (IOException ex) {
            logger.info("Can't delete file", ex);
            throw new RuntimeException(ex);
        }

        if(productDto.getNewPictures() != null) {
            for(MultipartFile newPicture : productDto.getNewPictures()) {
                try {
                    if (newPicture.getBytes().length > 0) {
                        product.getPictures().add(new Picture(null,
                                newPicture.getOriginalFilename(),
                                newPicture.getContentType(),
                                pictureService.createPicture(newPicture.getBytes()),
                                product));
                    }
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }
        productRepository.save(product);
    }

    @Override
    public Optional<ProductDto> findById(Long id) {
        return productRepository.findAllInfoById(id)
                .map(product -> {
                    BrandDto brandDto = mapToBrandDto(product.getBrand());
                    ProductDto productDto = new ProductDto(product.getId(),
                            product.getName(),
                            product.getCost(),
                            mapToCategoryDto(product.getCategory()));
                    productDto.setBrandDto(brandDto);
                    productDto.setPictureIds(mapToPictureIds(product.getPictures()));
                    productDto.setDescription(product.getDescription());
                    productDto.setShortDescription(product.getShortDescription());
                    Picture mainPicture = product.getMainPicture();
                    if(mainPicture != null) {
                        productDto.setMainPictureId(mainPicture.getId());
                    }
                    return productDto;
                });
    }

    @Override
    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }
}
