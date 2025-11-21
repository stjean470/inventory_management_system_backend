package com.skillstorm.inventory_management_system.exceptions;

public class WarehouseHasItemException extends IllegalArgumentException{
    public WarehouseHasItemException(String message) {
        super(message);
    }
    
}
