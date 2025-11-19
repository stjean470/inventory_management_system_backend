package com.skillstorm.inventory_management_system.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.skillstorm.inventory_management_system.exceptions.WarehouseNotFoundException;
import com.skillstorm.inventory_management_system.models.Item;
import com.skillstorm.inventory_management_system.models.Warehouse;
import com.skillstorm.inventory_management_system.repositories.WarehouseRepositiory;

@Service
public class WarehouseService {

    private final WarehouseRepositiory warehouseRepositiory;

    public WarehouseService(WarehouseRepositiory warehouseRepositiory) {
        this.warehouseRepositiory = warehouseRepositiory;
    }

    public List<Warehouse> getAllWarehouses() {
        return warehouseRepositiory.findAll();
    }

    public Warehouse createWarehouse(Warehouse warehouse) {
        return warehouseRepositiory.save(warehouse);
    }

    public Warehouse getWarehouseById(long id) {
        Warehouse warehouse = warehouseRepositiory.findById(id).orElseThrow(() -> new WarehouseNotFoundException("Warehouse not found"));
        return warehouse;
    }

    public Warehouse updateWarehouse(long id, Warehouse warehouse) {
        Warehouse updatedWarehouse = warehouseRepositiory.findById(id).orElseThrow(() -> new WarehouseNotFoundException("Warehouse not found"));
        updatedWarehouse.setName(warehouse.getName());
        updatedWarehouse.setMax_capacity(warehouse.getMax_capacity());
        updatedWarehouse.setLocation(warehouse.getLocation());
        updatedWarehouse.setItems(warehouse.getItems());
        return warehouseRepositiory.save(updatedWarehouse);
    }

    public String deleteWarehouse(long id) {
        Warehouse toBeDeleted = warehouseRepositiory.findById(id).orElseThrow(() -> new WarehouseNotFoundException("Warehouse Not found"));
        warehouseRepositiory.delete(toBeDeleted);
        return "Warehouse Deleted: \n" + toBeDeleted.toString();
      
    }

    public List<Item> getAllItemsInWarehouse(long id) {
        Warehouse w = getWarehouseById(id);
        return w.getItems();
    }

 
    
}
