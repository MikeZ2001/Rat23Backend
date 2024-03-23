package com.ratatouille23.Ratatouille23Server.service;

import com.ratatouille23.Ratatouille23Server.model.StoreTable;
import com.ratatouille23.Ratatouille23Server.repository.TableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import java.util.List;

@Service
public class TableService {

    private final TableRepository tableRepository;

    @Autowired
    public TableService(TableRepository tableRepository){
        this.tableRepository = tableRepository;
    }

    @GetMapping
    public List<StoreTable> getAllTables() {
        return tableRepository.findAll();
    }

    @GetMapping
    public List<StoreTable> getAllAvailableTables(Long storeId) throws IllegalStateException{
        List<StoreTable> optionalStoreTables = tableRepository.getAllAvailableTableOfStore(storeId)
                .orElseThrow(()-> new IllegalStateException("Impossibile trovare i tavoli disponibili dell'attività con id " + storeId + "."));
        return optionalStoreTables;
    }

    @GetMapping
    public StoreTable getTableById(Long id) throws IllegalStateException{
        StoreTable optionalStoreTable = tableRepository.findById(id)
                .orElseThrow(()->new IllegalStateException("Il tavolo con id " + id + " non esiste."));

        return optionalStoreTable;
    }

    @PostMapping
    public StoreTable addTable(StoreTable storeTable) {
        return tableRepository.save(storeTable);
    }

    @DeleteMapping
    public void deleteTable(Long id) {
        tableRepository.deleteById(id);
    }

    @PutMapping
    public StoreTable updateTable(Long id, StoreTable storeTable) throws IllegalStateException{
        StoreTable updateStoreTable = tableRepository.findById(id)
                .orElseThrow(()->new IllegalStateException("Il tavolo con id " + id + " non esiste."));

        updateStoreTable.setName(storeTable.getName());
        updateStoreTable.setSeatsNumber(storeTable.getSeatsNumber());
        updateStoreTable.setAvailable(storeTable.getAvailable());
        updateStoreTable.setStore(storeTable.getStore());

       return tableRepository.save(updateStoreTable);
    }

    public List<StoreTable> getAllByStore(Long id) throws IllegalStateException{
        List<StoreTable> optionalStoreTables = tableRepository.getAllTablesOfStore(id)
                .orElseThrow(()-> new IllegalStateException("Impossibile trovare i tavoli dell'attività con id " + id + "."));
        return optionalStoreTables;
    }
}
