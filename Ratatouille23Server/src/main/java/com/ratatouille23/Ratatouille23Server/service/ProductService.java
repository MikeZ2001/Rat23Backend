package com.ratatouille23.Ratatouille23Server.service;

import com.ratatouille23.Ratatouille23Server.model.Product;
import com.ratatouille23.Ratatouille23Server.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository){
        this.productRepository = productRepository;
    }

    @PostMapping
    public Product addProduct(Product newProduct){
        return productRepository.save(newProduct);
    }

    @GetMapping
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @PutMapping
    public Product updateProduct(Long id , Product product) throws IllegalStateException{
        Product productUpdate = productRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("Il prodotto con id " + id + " non esiste."));

        productUpdate.setName(product.getName());
        productUpdate.setDescription(product.getDescription());
        productUpdate.setPrice(product.getPrice());
        productUpdate.setCategoryOfTheProduct(product.getCategory());
        productUpdate.setAllergens(product.getAllergens());

        return productRepository.save(productUpdate);
    }

    @DeleteMapping
    public void deleteProduct(Long id){
        productRepository.deleteById(id);
    }

    @GetMapping
    public List<Product> getAllProductByCategoryId(Long id, Long storeId) throws IllegalStateException{
        List<Product> optionalProducts = productRepository.getAllProductByCategoryId(id, storeId)
                .orElseThrow(()-> new IllegalStateException("Impossibile trovare prodotti della categoria con id "+ id + "."));
        return optionalProducts;
    }

    @GetMapping
    public List<Product> getAllProductsOfStore(Long id) throws IllegalStateException{
        List<Product> optionalProducts = productRepository.getAllProductsOfStore(id)
                .orElseThrow(()-> new IllegalStateException("Impossibile trovare prodotti dell'attività con id "+id+"."));
        return optionalProducts;
    }
}
