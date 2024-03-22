package com.ratatouille23.Ratatouille23Server.service;

import com.ratatouille23.Ratatouille23Server.model.Table;
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
    public List<Table> getAllTables() {
        return tableRepository.findAll();
    }

    @GetMapping
    public List<Table> getAllAvailableTables(Long storeId) throws IllegalStateException{
        List<Table> optionalTables = tableRepository.getAllAvailableTableOfStore(storeId)
                .orElseThrow(()-> new IllegalStateException("Impossibile trovare i tavoli disponibili dell'attività con id " + storeId + "."));
        return optionalTables;
    }

    @GetMapping
    public Table getTableById(Long id) throws IllegalStateException{
        Table optionalTable = tableRepository.findById(id)
                .orElseThrow(()->new IllegalStateException("Il tavolo con id " + id + " non esiste."));

        return optionalTable;
    }

    @PostMapping
    public Table addTable(Table table) {
        return tableRepository.save(table);
    }

    @DeleteMapping
    public void deleteTable(Long id) {
        tableRepository.deleteById(id);
    }

    @PutMapping
    public Table updateTable(Long id, Table table) throws IllegalStateException{
        Table updateTable = tableRepository.findById(id)
                .orElseThrow(()->new IllegalStateException("Il tavolo con id " + id + " non esiste."));

        updateTable.setName(table.getName());
        updateTable.setSeatsNumber(table.getSeatsNumber());
        updateTable.setAvailable(table.getAvailable());

       return tableRepository.save(updateTable);
    }

    public List<Table> getAllByStore(Long id) throws IllegalStateException{
        List<Table> optionalTables = tableRepository.getAllTablesOfStore(id)
                .orElseThrow(()-> new IllegalStateException("Impossibile trovare i tavoli dell'attività con id " + id + "."));
        return optionalTables;
    }
}
