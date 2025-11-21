package com.skillstorm.inventory_management_system.models;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "ITEMS")
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "item_name")
    private String itemName;
    
    @Column(name = "sku")
    private String sku;

    @Column(name = "description")
    private String description;

    @Column(name = "quantity")
    private int quantity;

    @Column(name = "storage_location")
    private String storage_location;

    @ManyToMany
    @JoinTable(
        name = "warehouse_items",
        joinColumns = @JoinColumn(name = "item_id"),
        inverseJoinColumns = @JoinColumn(name = "warehouse_id"))
    @JsonIgnore
    private List<Warehouse> warehouses;

    public Item() {}

    public Item(String itemName, String sku, String description, int quantity, String storage_location,
            List<Warehouse> warehouses) {
        this.itemName = itemName;
        this.sku = sku;
        this.description = description;
        this.quantity = quantity;
        this.storage_location = storage_location;
        this.warehouses = warehouses;
    }



    public Item(long id, String itemName, String sku, String description, int quantity, String storage_location,
            List<Warehouse> warehouses) {
        this.id = id;
        this.itemName = itemName;
        this.sku = sku;
        this.description = description;
        this.quantity = quantity;
        this.storage_location = storage_location;
        this.warehouses = warehouses;
    }



    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getStorage_location() {
        return storage_location;
    }

    public void setStorage_location(String storage_location) {
        this.storage_location = storage_location;
    }


    public List<Warehouse> getWarehouses() {
        return warehouses;
    }


    public void setWarehouses(List<Warehouse> warehouses) {
        this.warehouses = warehouses;
    }



    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (int) (id ^ (id >>> 32));
        result = prime * result + ((itemName == null) ? 0 : itemName.hashCode());
        result = prime * result + ((sku == null) ? 0 : sku.hashCode());
        result = prime * result + ((description == null) ? 0 : description.hashCode());
        result = prime * result + quantity;
        result = prime * result + ((storage_location == null) ? 0 : storage_location.hashCode());
        result = prime * result + ((warehouses == null) ? 0 : warehouses.hashCode());
        return result;
    }



    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Item other = (Item) obj;
        if (id != other.id)
            return false;
        if (itemName == null) {
            if (other.itemName != null)
                return false;
        } else if (!itemName.equals(other.itemName))
            return false;
        if (sku == null) {
            if (other.sku != null)
                return false;
        } else if (!sku.equals(other.sku))
            return false;
        if (description == null) {
            if (other.description != null)
                return false;
        } else if (!description.equals(other.description))
            return false;
        if (quantity != other.quantity)
            return false;
        if (storage_location == null) {
            if (other.storage_location != null)
                return false;
        } else if (!storage_location.equals(other.storage_location))
            return false;
        if (warehouses == null) {
            if (other.warehouses != null)
                return false;
        } else if (!warehouses.equals(other.warehouses))
            return false;
        return true;
    }



    @Override
    public String toString() {
        return "Item [id=" + id + ", itemName=" + itemName + ", sku=" + sku + ", description=" + description
                + ", quantity=" + quantity + ", storage_location=" + storage_location + "]";
    }

    

    

    

    
}
