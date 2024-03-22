package com.ratatouille23.Ratatouille23Server.controller;

import com.ratatouille23.Ratatouille23Server.model.Category;
import com.ratatouille23.Ratatouille23Server.service.CategoryService;
import com.ratatouille23.Ratatouille23Server.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/category")
public class CategoryController {

    @Autowired
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService, ProductService productService){
        this.categoryService = categoryService;
    }


    @GetMapping("/getAll")
    public ResponseEntity<List<Category>> getAllCategories(){

        List<Category> categories = categoryService.getAllCategories();

        return new ResponseEntity<>(categories, HttpStatus.OK);
    }

    @GetMapping("/getById/{id}")
    public ResponseEntity<?> getCategoryById(@PathVariable("id") Long id){
        try {
            Category category = categoryService.getCategoryById(id);
            return new ResponseEntity<>(category,HttpStatus.OK);
        }catch (IllegalStateException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PostMapping("/add")
    public ResponseEntity<Category>addCategory(@RequestBody Category category){
        Category newCategory = categoryService.addCategory(category);

        return new ResponseEntity<>(newCategory,HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable("id") Long id){
        categoryService.deleteCategory(id);

        return new ResponseEntity<>(HttpStatus.OK);

    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateCategory(@PathVariable("id") Long id,@RequestBody Category category){
        try{
            categoryService.updateCategory(id,category);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (IllegalStateException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping("/getByStore/{storeId}")
    public ResponseEntity<?> getCategoriesOfStore(@PathVariable("storeId") Long id){
        try{
            List<Category> categories = categoryService.getCategoriesOfStore(id);
            return new ResponseEntity<>(categories, HttpStatus.OK);
        } catch (IllegalStateException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
