package com.skillstorm.inventory_management_system.services;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


import org.springframework.stereotype.Service;

import com.skillstorm.inventory_management_system.exceptions.ItemAlreadyInWarehouseException;
import com.skillstorm.inventory_management_system.exceptions.ItemNotFoundException;
import com.skillstorm.inventory_management_system.exceptions.WarehouseHasItemException;
import com.skillstorm.inventory_management_system.exceptions.WarehouseNotFoundException;
import com.skillstorm.inventory_management_system.models.Item;
import com.skillstorm.inventory_management_system.models.Warehouse;
import com.skillstorm.inventory_management_system.repositories.ItemRepository;
import com.skillstorm.inventory_management_system.repositories.WarehouseRepositiory;

@Service
public class ItemService {

    private final ItemRepository itemRepository;
    private final WarehouseRepositiory warehouseRepositiory;

    public ItemService(ItemRepository itemRepository, WarehouseRepositiory warehouseRepositiory) {
        this.itemRepository = itemRepository;
        this.warehouseRepositiory = warehouseRepositiory;
    }

    public Item createNewItem(Item item) {
        return itemRepository.save(item);
    }

    public List<Item> getAllItems() {
        return itemRepository.findAll();
    }

    public Item getItemById(long id) {
        Item item = itemRepository.findById(id).orElseThrow(() -> new ItemNotFoundException("Item not found"));
        return item;
    }

    public Item updateItem(long id, Item item) {
        Item itemToBeUpdated = itemRepository.findById(id).orElseThrow(() -> new ItemNotFoundException("Item not found"));
        itemToBeUpdated.setItemName(item.getItemName());
        itemToBeUpdated.setSku(item.getSku());
        itemToBeUpdated.setDescription(item.getDescription());
        itemToBeUpdated.setStorage_location(item.getStorage_location());
        itemToBeUpdated.setWarehouses(item.getWarehouses());
        return itemRepository.save(itemToBeUpdated);

    }

    public String deleteItem(long id) {
        Item toBeDeleted = itemRepository.findById(id).orElseThrow(() -> new ItemNotFoundException("Item not found"));
        itemRepository.delete(toBeDeleted);
        return "Item Deleted: \n" + toBeDeleted.toString();
    }

    public List<Warehouse> getAllWarehouseItemIsPresent(long id) {
        Item item = getItemById(id);
        return item.getWarehouses();
    }

    public Item addItemToWarehouse(long itemId, long warehouseId) {
        Item item = itemRepository.findById(itemId).orElseThrow(() -> new ItemNotFoundException("Item not found"));
        Warehouse warehouse = warehouseRepositiory.findById(warehouseId).orElseThrow(() -> new WarehouseNotFoundException("Warehouse not found"));
        
        if(item.getWarehouses() == null) {
            item.setWarehouses(new ArrayList<>());
        }

        if(warehouse.getItems() == null) {
            warehouse.setItems(new ArrayList<>());
        }

        if(warehouse.getItems().contains(item) || item.getWarehouses().contains(warehouse)) {
            throw new ItemAlreadyInWarehouseException(item.getItemName() + " already in the Warehouse!");
        }

        warehouse.getItems().add(item);

        Warehouse selectedWarehouse = warehouseRepositiory.save(warehouse);
        item.getWarehouses().add(selectedWarehouse);
        return itemRepository.save(item);
    }
    
}
