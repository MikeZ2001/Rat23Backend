package com.ratatouille23.Ratatouille23Server.controller;

import com.ratatouille23.Ratatouille23Server.model.Table;
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
    public ResponseEntity<List<Table>> getAllTables(){
        List<Table> tables = tableService.getAllTables();

        return new ResponseEntity<>(tables, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<Table>addTable(@RequestBody Table table){
        Table newTable = tableService.addTable(table);

        return new ResponseEntity<>(newTable,HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{tableId}")
    public ResponseEntity<?> deleteTable(@PathVariable("tableId") Long id){
        tableService.deleteTable(id);

        return new ResponseEntity<>(HttpStatus.OK);

    }

    @PutMapping("/update/{tableId}")
    public ResponseEntity<?> updateTable(@PathVariable("tableId") Long id,@RequestBody Table table){
        try {
            Table updatedTable = tableService.updateTable(id,table);
            return new ResponseEntity<>(updatedTable,HttpStatus.OK);
        } catch (IllegalStateException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getAllAvailableTables/{storeId}")
    public ResponseEntity<?> getTables(@PathVariable("storeId") Long id){
        try {
            List<Table> tables = tableService.getAllAvailableTables(id);
            return new ResponseEntity<>(tables, HttpStatus.OK);
        } catch (IllegalStateException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getById/{tableId}")
    public ResponseEntity<?> getTableById(@PathVariable("tableId") Long id){
        try {
            Table table = tableService.getTableById(id);
            return new ResponseEntity<>(table,HttpStatus.OK);
        } catch (IllegalStateException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getAllByStore/{storeId}")
    public ResponseEntity<?> getAllByStoreId(@PathVariable("storeId") Long id){
        try {
            List<Table> tables = tableService.getAllByStore(id);
            return new ResponseEntity<>(tables, HttpStatus.OK);
        } catch (IllegalStateException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

