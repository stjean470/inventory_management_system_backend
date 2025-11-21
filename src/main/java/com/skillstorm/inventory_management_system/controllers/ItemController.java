package com.skillstorm.inventory_management_system.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.skillstorm.inventory_management_system.exceptions.ItemNotFoundException;
import com.skillstorm.inventory_management_system.exceptions.WarehouseNotFoundException;
import com.skillstorm.inventory_management_system.models.Item;
import com.skillstorm.inventory_management_system.models.Warehouse;
import com.skillstorm.inventory_management_system.services.ItemService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;





@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/items")
public class ItemController {

    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }


    @GetMapping
    public ResponseEntity<List<Item>> getAllItems() {
        try {
            return new ResponseEntity<List<Item>>(itemService.getAllItems(), HttpStatus.OK);
        }catch(Exception e) {
            return ResponseEntity.internalServerError().header("message", e.getMessage()).build();
        }
    }

    @PostMapping("/item")
    public ResponseEntity<Item> createNewItem(@RequestBody Item item) {
        try{
            return new ResponseEntity<Item>(itemService.createNewItem(item), HttpStatus.CREATED);
        }catch(Exception e) {
            return ResponseEntity.internalServerError().header("message", e.getMessage()).build();
        }
    }

    @GetMapping("/item/{id}")
    public ResponseEntity<Item> getItemById(@PathVariable long id) {
        try {
            return new ResponseEntity<Item>(itemService.getItemById(id), HttpStatus.OK);
        }catch (ItemNotFoundException infe) {
            return ResponseEntity.notFound().header("message", infe.getMessage()).build();
        }catch (Exception e) {
            return ResponseEntity.internalServerError().header("message", e.getMessage()).build();
        }
    }

    @GetMapping("/warehousesForItem/{id}")
    public ResponseEntity<List<Warehouse>> getWarehousesForItem(@PathVariable long id) {
        try {
            return new ResponseEntity<List<Warehouse>>(itemService.getAllWarehouseItemIsPresent(id), HttpStatus.OK);
        }catch (ItemNotFoundException infe) {
            return ResponseEntity.notFound().header("message", infe.getMessage()).build();
        }catch (Exception e) {
            return ResponseEntity.internalServerError().header("message", e.getMessage()).build();
        }
    }
    
    

    @PutMapping("/update-item/{id}")
    public ResponseEntity<Item> updateItem(@PathVariable long id, @RequestBody Item item) {
        try {
            return new ResponseEntity<Item>(itemService.updateItem(id, item), HttpStatus.OK);
        }catch (ItemNotFoundException infe) {
            return ResponseEntity.notFound().header("message", infe.getMessage()).build();
        }catch (Exception e) {
            return ResponseEntity.internalServerError().header("message", e.getMessage()).build();
        }  
    }

    @PutMapping("/addItemToAnWarehouse/{itemId}/{warehouseId}")
    public ResponseEntity<Item> addItemToWarehouse(@PathVariable long itemId, @PathVariable long warehouseId) {
        try {
            return new ResponseEntity<Item>(itemService.addItemToWarehouse(itemId, warehouseId), HttpStatus.OK);
        }catch (ItemNotFoundException infe) {
            return ResponseEntity.notFound().header("message", "Couldn't find the resource").build();
        }catch (WarehouseNotFoundException wnfe) {
            return ResponseEntity.notFound().header("message", "Couldn't find the resource").build();
        }catch (Exception e) {
            return ResponseEntity.internalServerError().header("message", e.getMessage()).build();
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteItem(@PathVariable long id) {
        try {
            return new ResponseEntity<String>(itemService.deleteItem(id), HttpStatus.OK);
        }catch (ItemNotFoundException infe) {
            return ResponseEntity.notFound().header("message", infe.getMessage()).build();
        }catch (Exception e) {
            return ResponseEntity.internalServerError().header("message", e.getMessage()).build();
        }
    }
    
    

    
}
