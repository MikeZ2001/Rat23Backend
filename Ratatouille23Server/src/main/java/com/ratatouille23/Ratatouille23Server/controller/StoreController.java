package com.ratatouille23.Ratatouille23Server.controller;

import com.ratatouille23.Ratatouille23Server.model.Store;
import com.ratatouille23.Ratatouille23Server.service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "api/v1/store")
public class StoreController {

    private final StoreService storeService;

    @Autowired
    public StoreController(StoreService storeService) {
        this.storeService = storeService;
    }

    @PostMapping("/add")
    public ResponseEntity<Store> addStore(@RequestBody Store store){
        Store newStore = storeService.addStore(store);
        return new ResponseEntity<>(newStore, HttpStatus.CREATED);
    }

    @PutMapping("update/{id}")
    public ResponseEntity<?> updateStore(@PathVariable("id") Long id , @RequestBody Store store){
        try {
            Store updatedStore = storeService.updateStore(id, store);
            return new ResponseEntity<>(updatedStore, HttpStatus.OK);
        } catch (IllegalStateException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteStore(@PathVariable("id") Long id){
        storeService.deleteStore(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("getById/{id}")
    public ResponseEntity<?> getStoreById(@PathVariable("id") Long id){
        try {
            Store store = storeService.getStoreById(id);
            return  new ResponseEntity<>(store, HttpStatus.OK);
        } catch (IllegalStateException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
