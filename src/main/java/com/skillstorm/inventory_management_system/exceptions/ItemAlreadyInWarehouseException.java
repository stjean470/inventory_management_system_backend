package com.skillstorm.inventory_management_system.exceptions;

public class ItemAlreadyInWarehouseException extends IllegalArgumentException{
    public ItemAlreadyInWarehouseException(String message) {
        super(message);
    }
}
