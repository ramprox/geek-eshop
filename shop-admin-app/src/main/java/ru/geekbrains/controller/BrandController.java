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
import ru.geekbrains.dto.BrandDto;
import ru.geekbrains.exceptions.NotFoundException;
import ru.geekbrains.interfaces.BrandService;

import javax.validation.Valid;

@Controller
@RequestMapping("/brand")
public class BrandController {

    private static final Logger logger = LoggerFactory.getLogger(BrandController.class);
    private final BrandService brandService;

    @Autowired
    public BrandController(BrandService brandService) {
        this.brandService = brandService;
    }

    @GetMapping
    public String listBrands(Model model) {
        model.addAttribute("brands", brandService.findAll());
        return "brands";
    }

    @GetMapping("/new")
    public String newBrandForm(Model model) {
        model.addAttribute("brand", new BrandDto());
        return "brand_form";
    }

    @PostMapping
    public String update(@Valid @ModelAttribute("brand") BrandDto brandDto, BindingResult result, Model model) {
        logger.info("Saving brand " + brandDto.getId());
        if (result.hasErrors()) {
            return "brand_form";
        }
        brandService.save(brandDto);
        return "redirect:/brand";
    }

    @GetMapping("/{id}")
    public String editBrand(@PathVariable("id") Long id, Model model) {
        model.addAttribute("brand", brandService.findById(id)
                .orElseThrow(() -> new NotFoundException("Brand not found")));
        return "brand_form";
    }

    @DeleteMapping("/{id}")
    public String deleteBrand(@PathVariable("id") Long id) {
        brandService.deleteById(id);
        return "redirect:/brand";
    }

    @ExceptionHandler
    public ModelAndView notFoundExceptionHandler(NotFoundException ex) {
        ModelAndView modelAndView = new ModelAndView("not_found");
        modelAndView.addObject("message", ex.getMessage());
        modelAndView.setStatus(HttpStatus.NOT_FOUND);
        return modelAndView;
    }
}
