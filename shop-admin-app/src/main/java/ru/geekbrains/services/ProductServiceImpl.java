package ru.geekbrains.services;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
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
import ru.ramprox.persist.model.Brand;
import ru.ramprox.persist.model.Category;
import ru.ramprox.persist.model.Picture;
import ru.ramprox.persist.model.Product;
import ru.ramprox.persist.repositories.ProductRepository;
import ru.ramprox.persist.specifications.ProductSpecifications;
import ru.geekbrains.service.PictureService;


import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    private final PictureService pictureService;

    @Autowired
    private ModelMapper modelMapper;

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
                        product.getPrice(),
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
            spec = spec.and(ProductSpecifications.minPrice(minCostFilter));
        }
        if(maxCostFilter != null) {
            spec = spec.and(ProductSpecifications.maxPrice(maxCostFilter));
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

        return null;
//        return productRepository.findAll(spec,
//                PageRequest.of(Optional.ofNullable(listParams.getPage()).orElse(1) - 1,
//                        Optional.ofNullable(listParams.getSize()).orElse(10), sortedBy))
//                .map(product -> new ProductDto(product.getId(), product.getName(), product.getPrice(),
//                        product.getMainPicture() != null ? product.getMainPicture().getId() : null));
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
        Product product = modelMapper.map(productDto, Product.class);
        if(productDto.getMainPictureId() != null) {

        }
        List<Long> pictureIds = productDto.getPictureIds();
        List<Picture> pictures = pictureService.findByProductId(productDto.getId());
        try {
            for (Picture picture : pictures) {
                if (pictureIds.contains(picture.getId())) {

                } else {
                    pictureService.deleteById(picture.getId());
                }
            }
        } catch (IOException ex) {
            log.info("Can't delete file", ex);
            throw new RuntimeException(ex);
        }
        if(productDto.getNewPictures() != null) {
            for(MultipartFile newPicture : productDto.getNewPictures()) {
                try {
                    if (newPicture.getBytes().length > 0) {
                        Picture picture = new Picture(pictureService.createPicture(newPicture.getBytes()),
                                newPicture.getContentType(), product);
                        picture.setName(newPicture.getOriginalFilename());
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
//        return productRepository.findById(id)
//                .map(product -> {
//                    BrandDto brandDto = mapToBrandDto(product.getBrand());
//                    ProductDto productDto = new ProductDto(product.getId(),
//                            product.getName(),
//                            product.getPrice(),
//                            mapToCategoryDto(product.getCategory()));
//                    productDto.setBrandDto(brandDto);
//                    productDto.setPictureIds(mapToPictureIds(product.getPictures()));
//                    productDto.setDescription(product.getDescription());
//                    productDto.setShortDescription(product.getShortDescription());
//                    Picture mainPicture = product.getMainPicture();
//                    if(mainPicture != null) {
//                        productDto.setMainPictureId(mainPicture.getId());
//                    }
//                    return productDto;
//                });
        return null;
    }

    @Override
    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }
}
