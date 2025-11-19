package com.skillstorm.inventory_management_system.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.skillstorm.inventory_management_system.exceptions.WarehouseNotFoundException;
import com.skillstorm.inventory_management_system.models.Item;
import com.skillstorm.inventory_management_system.models.Warehouse;
import com.skillstorm.inventory_management_system.services.WarehouseService;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;



@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/warehouses")
public class WarehouseController {

    private final WarehouseService warehouseService;

    public WarehouseController(WarehouseService warehouseService) {
        this.warehouseService = warehouseService;
    }

    @GetMapping
    public ResponseEntity<List<Warehouse>> getAllWarehouses() {
        try {
            return new ResponseEntity<List<Warehouse>>(warehouseService.getAllWarehouses(), HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().header("message", e.getMessage()).build();
        }
    }

    @PostMapping("/new_warehouse")
    public ResponseEntity<Warehouse> createWarehouse(@RequestBody Warehouse warehouse) {
        try {
            return new ResponseEntity<Warehouse>(warehouseService.createWarehouse(warehouse), HttpStatus.CREATED);
        }catch (Exception e) {
            return ResponseEntity.internalServerError().header("message", e.getMessage()).build();
        }
    }

    @GetMapping("/warehouse/{id}")
    public ResponseEntity<Warehouse> getWarehouseById(@PathVariable long id) {
        try {
            return new ResponseEntity<Warehouse>(warehouseService.getWarehouseById(id), HttpStatus.OK);
        }catch (WarehouseNotFoundException wnfe) {
            return ResponseEntity.notFound().header("message", "Couldn't find the resource").build();
        }catch (Exception e) {
            return ResponseEntity.internalServerError().header("message", e.getMessage()).build();
        }
    }

    @GetMapping("/warehouseItems/{id}")
    public ResponseEntity<List<Item>> getAllItemsInWarehouse(@PathVariable long id) {
        try {
            return new ResponseEntity<List<Item>>(warehouseService.getAllItemsInWarehouse(id), HttpStatus.OK);
            
        }catch (Exception e) {
            return ResponseEntity.internalServerError().header("message", e.getMessage()).build();
        }
    }
    

    @PutMapping("/update/{id}")
    public ResponseEntity<Warehouse> updateWarehouse(@PathVariable long id, @RequestBody Warehouse warehouse) {
        try {
            return new ResponseEntity<Warehouse>(warehouseService.updateWarehouse(id, warehouse), HttpStatus.OK);         
        }catch (WarehouseNotFoundException wnfe) {
            return ResponseEntity.notFound().header("message", wnfe.getMessage()).build();
        }catch (Exception e) {
            return ResponseEntity.internalServerError().header("message", e.getMessage()).build();
        }
        
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteWarehouse(@PathVariable long id) {
        try {
            return new ResponseEntity<String>(warehouseService.deleteWarehouse(id), HttpStatus.OK);
        }catch (WarehouseNotFoundException wnfe) {
            return ResponseEntity.notFound().header("message", wnfe.getMessage()).build();
        }catch (Exception e) {
            return ResponseEntity.internalServerError().header("message", e.getMessage()).build();
        }
    }

    
    
}
