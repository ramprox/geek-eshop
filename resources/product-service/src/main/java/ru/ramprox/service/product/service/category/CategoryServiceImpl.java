package ru.ramprox.service.product.service.category;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import ru.ramprox.service.product.database.entity.Category;
import ru.ramprox.service.product.database.repository.CategoryRepository;
import ru.ramprox.service.product.dto.ReadCategoryDto;
import ru.ramprox.service.product.util.exception.NotFoundException;
import ru.ramprox.service.product.dto.CategoryDto;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<ReadCategoryDto> findAllByCategoryIdOrderByName(Long categoryId) {
        if(categoryId != null) {
            return categoryRepository.findByParentCategoryId(categoryId);
        }
        return categoryRepository.findRootCategories();
    }

    @Override
    public CategoryDto findById(Long id) {
        return categoryRepository.findById(id)
                .map(category -> new CategoryDto(category.getId(), category.getName()))
                .orElseThrow(() -> getNotFoundException(null, id));
    }

    @Override
    public CategoryDto save(CategoryDto categoryDto) {
        Category category = categoryRepository.save(new Category(categoryDto.getName()));
        categoryDto.setId(category.getId());
        return categoryDto;
    }

    @Override
    @Transactional
    public CategoryDto update(CategoryDto categoryDto) {
        try {
            Category category = categoryRepository.getById(categoryDto.getId());
            category.setName(categoryDto.getName());
            return categoryDto;
        } catch (EntityNotFoundException ex) {
            throw getNotFoundException(ex, categoryDto.getId());
        }
    }

    @Override
    public void deleteById(Long id) {
        try {
            categoryRepository.deleteById(id);
        } catch (EmptyResultDataAccessException ex) {
            throw getNotFoundException(ex, id);
        }
    }

    private NotFoundException getNotFoundException(Exception cause, Long brandId) {
        NotFoundException exception = new NotFoundException(String.format("Категория с id = %d не найдена", brandId));
        exception.initCause(cause);
        return exception;
    }
}
