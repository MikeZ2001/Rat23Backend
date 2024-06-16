package com.ratatouille23.Ratatouille23Server.controller;

import com.ratatouille23.Ratatouille23Server.model.Product;
import com.ratatouille23.Ratatouille23Server.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "api/v1/product")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService){
        this.productService = productService;
    }

    @PostMapping("/add")
    public ResponseEntity<Product>addProduct(@RequestBody Product product){
        Product newProduct = productService.addProduct(product);
        return new ResponseEntity<>(newProduct,HttpStatus.CREATED);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<Product>> getAllProducts(){
        List<Product> products = productService.getAllProducts();
        return new ResponseEntity<>(products,HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable("id") Long id , @RequestBody Product product){
        try {
            Product updateProduct = productService.updateProduct(id,product);
            return new ResponseEntity<>(updateProduct,HttpStatus.OK);
        } catch (IllegalStateException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteProductById(@PathVariable("id") Long id){
        productService.deleteProduct(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{storeId}/getAllByCategoryId/{id}")
    public ResponseEntity<?> getAllProductByCategoryId(@PathVariable("id") Long id, @PathVariable("storeId") Long storeId){
        try {
            List<Product> products = productService.getAllProductByCategoryId(id, storeId);
            return new ResponseEntity<>(products, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getAllByStore/{storeId}")
    public ResponseEntity<?> getAllProductsOfStore(@PathVariable("storeId")Long id){
        try {
            List<Product> products = productService.getAllProductsOfStore(id);
            return new ResponseEntity<>(products, HttpStatus.OK);
        } catch (IllegalStateException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
