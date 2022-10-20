package ru.ramprox.service.product.service.brand;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ramprox.service.product.database.entity.Brand;
import ru.ramprox.service.product.database.repository.BrandRepository;
import ru.ramprox.service.product.util.exception.NotFoundException;
import ru.ramprox.service.product.dto.BrandDto;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BrandServiceImpl implements BrandService {

    private final BrandRepository brandRepository;

    @Autowired
    public BrandServiceImpl(BrandRepository brandRepository) {
        this.brandRepository = brandRepository;
    }

    @Override
    public List<BrandDto> findAllOrderByName() {
        return brandRepository.findAllByOrderByName()
                .stream()
                .map(brand -> new BrandDto(brand.getId(), brand.getName()))
                .collect(Collectors.toList());
    }

    @Override
    public BrandDto findById(Long id) {
        return brandRepository.findById(id)
                .map(brand -> new BrandDto(brand.getId(), brand.getName()))
                .orElseThrow(() -> getNotFoundException(null, id));
    }

    @Override
    public BrandDto save(BrandDto brandDto) {
        Brand brand = brandRepository.save(new Brand(brandDto.getName()));
        brandDto.setId(brand.getId());
        return brandDto;
    }

    @Override
    @Transactional
    public BrandDto update(BrandDto brandDto) {
        try {
            Brand brand = brandRepository.getById(brandDto.getId());
            brand.setName(brandDto.getName());
            return brandDto;
        } catch (EntityNotFoundException ex) {
            throw getNotFoundException(ex, brandDto.getId());
        }
    }

    @Override
    public void deleteById(Long id) {
        try {
            brandRepository.deleteById(id);
        } catch (EmptyResultDataAccessException ex) {
            throw getNotFoundException(ex, id);
        }
    }

    private NotFoundException getNotFoundException(Exception cause, Long brandId) {
        NotFoundException exception = new NotFoundException(String.format("Брэнд с id = %d не найден", brandId));
        exception.initCause(cause);
        return exception;
    }

}
