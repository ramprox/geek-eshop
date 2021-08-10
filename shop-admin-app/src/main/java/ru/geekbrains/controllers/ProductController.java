package ru.geekbrains.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.geekbrains.dto.CategoryDto;
import ru.geekbrains.dto.ProductDto;
import ru.geekbrains.dto.ProductListParams;
import ru.geekbrains.exceptions.NotFoundException;
import ru.geekbrains.persist.CategoryRepository;
import ru.geekbrains.persist.Product;
import ru.geekbrains.services.CategoryService;
import ru.geekbrains.services.ProductService;

import javax.validation.Valid;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;
    private final CategoryService categoryService;

    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    public ProductController(ProductService productService, CategoryService categoryService) {
        this.productService = productService;
        this.categoryService = categoryService;
    }

    @GetMapping
    public String listPage(Model model, ProductListParams listParams) {
        logger.info("Product list page requested");
        model.addAttribute("products", productService.findWithFilter(listParams));
        return "products";
    }

    @GetMapping("/new")
    public String newProduct(Model model) {
        logger.info("New product page requested");
        model.addAttribute("product", new ProductDto());
        model.addAttribute("categories", categoryService.findAllOrderByName().stream()
                .map(category -> new CategoryDto(category.getId(), category.getName()))
                .collect(Collectors.toList()));
        return "product_form";
    }

    @PostMapping
    public String update(@Valid @ModelAttribute("product") ProductDto product, BindingResult result, Model model) {
        logger.info("Saving product");
        if(result.hasErrors()) {
            model.addAttribute("categories", categoryService.findAllOrderByName().stream()
                    .map(category -> new CategoryDto(category.getId(), category.getName()))
                    .collect(Collectors.toList()));
            return "product_form";
        }
        productService.save(product);
        return "redirect:/product";
    }

    @GetMapping("/{id}")
    public String editProduct(@PathVariable("id") Long id, Model model) {
        logger.info("Product editing page requested");
        model.addAttribute("product", productService.findById(id)
                .orElseThrow(() -> new NotFoundException("Product not found")));
        model.addAttribute("categories", categoryService.findAllOrderByName().stream()
                .map(category -> new CategoryDto(category.getId(), category.getName()))
                .collect(Collectors.toList()));
        return "product_form";
    }

    @DeleteMapping("/{id}")
    public String deleteProduct(@PathVariable("id") Long id) {
        logger.info("Delete product requested");
        productService.deleteById(id);
        return "redirect:/product";
    }

    @ExceptionHandler
    public ModelAndView notFoundExceptionHandler(NotFoundException ex) {
        ModelAndView modelAndView = new ModelAndView("not_found");
        modelAndView.addObject("message", ex.getMessage());
        modelAndView.setStatus(HttpStatus.NOT_FOUND);
        return modelAndView;
    }
}
