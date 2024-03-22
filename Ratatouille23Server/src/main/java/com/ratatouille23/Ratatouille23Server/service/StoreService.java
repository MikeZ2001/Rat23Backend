package com.ratatouille23.Ratatouille23Server.service;

import com.ratatouille23.Ratatouille23Server.model.Store;
import com.ratatouille23.Ratatouille23Server.repository.StoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

@Service
public class StoreService {
    private final StoreRepository storeRepository;

    @Autowired
    public StoreService(StoreRepository storeRepository){
        this.storeRepository = storeRepository;
    }

    @PostMapping
    public Store addStore(Store newStore){
        return storeRepository.save(newStore);
    }

    @PutMapping
    public Store updateStore(Long id , Store store) throws IllegalStateException{

        Store storeUpdate = storeRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("L'attività con id " + id + " non esiste."));

        storeUpdate.setName(store.getName());
        storeUpdate.setAddress(store.getAddress());
        storeUpdate.setEmail(store.getEmail());
        storeUpdate.setPhone(store.getPhone());

        return storeRepository.save(storeUpdate);
    }

    @DeleteMapping
    public void deleteStore(Long id){
        storeRepository.deleteById(id);
    }

    @GetMapping
    public Store getStoreById(Long id) throws IllegalStateException{
        Store optionalStore = storeRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("L'attività con id " + id + " non esiste."));

        return optionalStore;
    }
}
