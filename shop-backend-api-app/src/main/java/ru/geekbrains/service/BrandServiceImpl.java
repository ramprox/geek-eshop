package ru.geekbrains.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.geekbrains.controller.dto.BrandDto;
import ru.geekbrains.persist.repositories.BrandRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BrandServiceImpl implements BrandService {

    private BrandRepository brandRepository;

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
}
