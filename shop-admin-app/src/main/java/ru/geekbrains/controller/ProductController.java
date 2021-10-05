package ru.geekbrains.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.geekbrains.dto.ProductDto;
import ru.geekbrains.dto.ProductListParams;
import ru.geekbrains.exceptions.NotFoundException;
import ru.geekbrains.interfaces.BrandService;
import ru.geekbrains.interfaces.CategoryService;
import ru.geekbrains.interfaces.ProductService;

import javax.validation.Valid;

@Controller
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;
    private final CategoryService categoryService;
    private final BrandService brandService;

    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    public ProductController(ProductService productService,
                             CategoryService categoryService,
                             BrandService brandService) {
        this.productService = productService;
        this.categoryService = categoryService;
        this.brandService = brandService;
    }

    @GetMapping
    public String listPage(Model model, ProductListParams listParams) {
        logger.info("Product list page requested");
        model.addAttribute("products", productService.findWithFilter(listParams));
        model.addAttribute("categories", categoryService.findAllOrderByName());
        model.addAttribute("brands", brandService.findAllOrderByName());
        return "products";
    }

    @GetMapping("/new")
    public String newProduct(Model model) {
        logger.info("New product page requested");
        model.addAttribute("product", new ProductDto());
        model.addAttribute("categories", categoryService.findAllOrderByName());
        model.addAttribute("brands", brandService.findAllOrderByName());
        return "product_form";
    }

    @PostMapping
    public String update(@Valid @ModelAttribute("product") ProductDto product,
                         BindingResult result,
                         Model model,
                         @RequestParam(value = "deleting", required = false) Long pictureId) {
        if (pictureId != null) {
            logger.info("Delete picture from list");
            product.getPictureIds().remove(pictureId);
            if (pictureId.equals(product.getMainPictureId())) {
                product.setMainPictureId(null);
            }
            model.addAttribute("product", product);
            model.addAttribute("categories", categoryService.findAllOrderByName());
            model.addAttribute("brands", brandService.findAllOrderByName());
            return "product_form";
        } else {
            logger.info("Saving product");
            if (result.hasErrors()) {
                model.addAttribute("categories", categoryService.findAllOrderByName());
                model.addAttribute("brands", brandService.findAllOrderByName());
                return "product_form";
            }
            productService.save(product);
            return "redirect:/product";
        }
    }

    @GetMapping("/{id}")
    public String editProduct(@PathVariable("id") Long id, Model model) {
        logger.info("Product editing page requested");
        model.addAttribute("product", productService.findById(id)
                .orElseThrow(() -> new NotFoundException("Product not found")));
        model.addAttribute("categories", categoryService.findAllOrderByName());
        model.addAttribute("brands", brandService.findAllOrderByName());
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
