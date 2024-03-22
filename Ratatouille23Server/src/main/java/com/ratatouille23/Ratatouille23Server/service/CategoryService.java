package com.ratatouille23.Ratatouille23Server.service;

import com.ratatouille23.Ratatouille23Server.model.Category;
import com.ratatouille23.Ratatouille23Server.model.Employee;
import com.ratatouille23.Ratatouille23Server.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import java.util.List;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository){
        this.categoryRepository = categoryRepository;
    }

    @GetMapping
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @PostMapping
    public Category addCategory(Category category) {
        return categoryRepository.save(category);
    }

    @DeleteMapping
    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }

    @PutMapping
    public void updateCategory(Long id, Category category) throws IllegalStateException {
        Category updateCategory = categoryRepository.findById(id).
                orElseThrow(() -> new IllegalStateException("Impossibile trovare la categoria con id " + id));

        updateCategory.setName(category.getName());
        updateCategory.setDescription(category.getDescription());
        updateCategory.setProductsOfTheCategory(category.getProductsOfTheCategory());

        categoryRepository.save(updateCategory);
    }


    @GetMapping
    public Category getCategoryById(Long id) throws IllegalStateException{
        Category optionalCategory = categoryRepository.findById(id)
                .orElseThrow(()->new IllegalStateException("Impossibile trovare la categoria con id " + id));

        return optionalCategory;
    }

    @GetMapping
    public List<Category> getCategoriesOfStore(Long storeId) throws IllegalStateException{
        List<Category> optionalCategories = categoryRepository.getCategoriesOfStore(storeId)
                .orElseThrow(()->new IllegalStateException("Impossibile trovare le categorie dell'attività con id "+storeId));

        return optionalCategories;
    }
}
