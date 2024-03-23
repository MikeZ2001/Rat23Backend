package com.ratatouille23.Ratatouille23Server.controller;

import com.ratatouille23.Ratatouille23Server.model.StoreTable;
import com.ratatouille23.Ratatouille23Server.service.TableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/table")
public class TableController {

    @Autowired
    private final TableService tableService;

    public TableController(TableService tableService){
        this.tableService = tableService;
    }


    @GetMapping("/getAll")
    public ResponseEntity<List<StoreTable>> getAllTables(){
        List<StoreTable> storeTables = tableService.getAllTables();

        return new ResponseEntity<>(storeTables, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<StoreTable>addTable(@RequestBody StoreTable storeTable){
        StoreTable newStoreTable = tableService.addTable(storeTable);

        return new ResponseEntity<>(newStoreTable,HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{tableId}")
    public ResponseEntity<?> deleteTable(@PathVariable("tableId") Long id){
        tableService.deleteTable(id);

        return new ResponseEntity<>(HttpStatus.OK);

    }

    @PutMapping("/update/{tableId}")
    public ResponseEntity<?> updateTable(@PathVariable("tableId") Long id,@RequestBody StoreTable storeTable){
        try {
            StoreTable updatedStoreTable = tableService.updateTable(id, storeTable);
            return new ResponseEntity<>(updatedStoreTable,HttpStatus.OK);
        } catch (IllegalStateException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getAllAvailableTables/{storeId}")
    public ResponseEntity<?> getTables(@PathVariable("storeId") Long id){
        try {
            List<StoreTable> storeTables = tableService.getAllAvailableTables(id);
            return new ResponseEntity<>(storeTables, HttpStatus.OK);
        } catch (IllegalStateException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getById/{tableId}")
    public ResponseEntity<?> getTableById(@PathVariable("tableId") Long id){
        try {
            StoreTable storeTable = tableService.getTableById(id);
            return new ResponseEntity<>(storeTable,HttpStatus.OK);
        } catch (IllegalStateException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getAllByStore/{storeId}")
    public ResponseEntity<?> getAllByStoreId(@PathVariable("storeId") Long id){
        try {
            List<StoreTable> storeTables = tableService.getAllByStore(id);
            return new ResponseEntity<>(storeTables, HttpStatus.OK);
        } catch (IllegalStateException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

