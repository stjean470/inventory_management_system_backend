package com.skillstorm.inventory_management_system.services;

import java.util.Iterator;
import java.util.List;


import org.springframework.stereotype.Service;

import com.skillstorm.inventory_management_system.exceptions.ItemNotFoundException;
import com.skillstorm.inventory_management_system.models.Item;
import com.skillstorm.inventory_management_system.models.Warehouse;
import com.skillstorm.inventory_management_system.repositories.ItemRepository;

@Service
public class ItemService {

    private final ItemRepository itemRepository;

    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public Item createNewItem(Item item) {
        return itemRepository.save(item);
    }

    public List<Item> getAllItems() {
        return itemRepository.findAll();
    }

    public Item getItemById(long id) {
        Item item = itemRepository.findById(id).orElseThrow(() -> new RuntimeException("Item not found"));
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
    
}
