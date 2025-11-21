package com.skillstorm.inventory_management_system.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.skillstorm.inventory_management_system.exceptions.ItemAlreadyInWarehouseException;
import com.skillstorm.inventory_management_system.exceptions.ItemNotFoundException;
import com.skillstorm.inventory_management_system.exceptions.WarehouseNotFoundException;
import com.skillstorm.inventory_management_system.models.Item;
import com.skillstorm.inventory_management_system.models.Warehouse;
import com.skillstorm.inventory_management_system.repositories.ItemRepository;
import com.skillstorm.inventory_management_system.repositories.WarehouseRepositiory;

@Service
public class WarehouseService {

    private final WarehouseRepositiory warehouseRepositiory;
    private final ItemRepository itemRepository;

    public WarehouseService(WarehouseRepositiory warehouseRepositiory, ItemRepository itemRepository) {
        this.warehouseRepositiory = warehouseRepositiory;
        this.itemRepository = itemRepository;
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

    public Warehouse addItemToWarehouse(long id, Item item) {
        //Item addedItem = itemRepository.save(item);
        Warehouse w = warehouseRepositiory.findById(id).orElseThrow(() -> new WarehouseNotFoundException("Warehouse was not found"));
        if (w.getItems() == null) {
            w.setItems(new ArrayList<>());
        }

        if(item.getWarehouses() == null) {
            item.setWarehouses(new ArrayList<>());
        }
        item.getWarehouses().add(w);
        Item addedItem = itemRepository.save(item);
        w.getItems().add(addedItem);
        return warehouseRepositiory.save(w);
    }

    public Warehouse deleteItemByWarehouse(long warehouseId, long itemId) {
        Warehouse warehouse = warehouseRepositiory.findById(warehouseId).orElseThrow(() -> new WarehouseNotFoundException("Warehouse not found"));
        Item item = itemRepository.findById(itemId).orElseThrow(() -> new ItemNotFoundException("Item not found"));

        item.getWarehouses().remove(warehouse);
        warehouse.getItems().remove(item);
        
        itemRepository.save(item);

        return warehouseRepositiory.save(warehouse);
        
    }

    public Warehouse transferItem(long presentWarehouseId, long itemId, long newWarehouseId) {
        Warehouse presentWarehouse = getWarehouseById(presentWarehouseId);
        Item itemToBeTransferred = itemRepository.findById(itemId).orElseThrow(() -> new ItemNotFoundException("Item not found"));
        Warehouse newWarehouse = getWarehouseById(newWarehouseId);

        if(!(newWarehouse.getItems().contains(itemToBeTransferred))) {
            itemToBeTransferred.getWarehouses().remove(presentWarehouse);
            presentWarehouse.getItems().remove(itemToBeTransferred);
            warehouseRepositiory.save(presentWarehouse);
            itemToBeTransferred.getWarehouses().add(newWarehouse);
            newWarehouse.getItems().add(itemToBeTransferred);
            itemRepository.save(itemToBeTransferred);
        }else {
            throw new ItemAlreadyInWarehouseException(itemToBeTransferred + " already exist in Warehouse!");
        }
        return warehouseRepositiory.save(newWarehouse);
    }

 
    
}
