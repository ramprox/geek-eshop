package ru.ramprox.service.product.util;


import ru.ramprox.service.product.database.entity.Category;
import ru.ramprox.util.itest.EntityBuilder;

public class CategoryBuilder implements EntityBuilder<Category> {

    private String name = "Category";

    private EntityBuilder<Category> category;

    public static CategoryBuilder category() {
        return new CategoryBuilder();
    }

    private CategoryBuilder() { }

    private CategoryBuilder(CategoryBuilder categoryBuilder) {
        this.name = categoryBuilder.name;
        this.category = categoryBuilder.category;
    }

    public CategoryBuilder withName(String name) {
        CategoryBuilder builder = new CategoryBuilder(this);
        builder.name = name;
        return builder;
    }

    public CategoryBuilder withParentCategory(EntityBuilder<Category> category) {
        CategoryBuilder builder = new CategoryBuilder(this);
        builder.category = category;
        return builder;
    }

    @Override
    public Category build() {
        Category category = new Category(name);
        if(this.category != null) {
            category.setCategory(this.category.build());
        }
        return category;
    }
}
