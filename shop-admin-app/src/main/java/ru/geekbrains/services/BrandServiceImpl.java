package ru.geekbrains.services;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.geekbrains.dto.BrandDto;
import ru.geekbrains.interfaces.BrandService;
import ru.ramprox.persist.model.Brand;
import ru.ramprox.persist.repositories.BrandRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BrandServiceImpl implements BrandService {

    private final BrandRepository brandRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    public BrandServiceImpl(BrandRepository brandRepository) {
        this.brandRepository = brandRepository;
    }

    @Override
    public List<BrandDto> findAll() {
        return brandRepository.findAll().stream()
                .map(brand -> new BrandDto(brand.getId(), brand.getName()))
                .collect(Collectors.toList());
    }

    @Override
    public void save(BrandDto brandDto) {
        Brand brand = modelMapper.map(brandDto, Brand.class);
        brandRepository.save(brand);
    }

    @Override
    public Optional<BrandDto> findById(Long id) {
        return brandRepository.findById(id)
                .map(brand -> new BrandDto(brand.getId(), brand.getName()));
    }

    @Override
    public void deleteById(Long id) {
        brandRepository.deleteById(id);
    }

    @Override
    public List<BrandDto> findAllOrderByName() {
        return brandRepository.findAllByOrderByName().stream()
                .map(brand -> new BrandDto(brand.getId(), brand.getName()))
                .collect(Collectors.toList());
    }
}
