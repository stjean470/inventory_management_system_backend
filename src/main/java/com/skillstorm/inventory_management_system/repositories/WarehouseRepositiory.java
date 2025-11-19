package com.skillstorm.inventory_management_system.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.skillstorm.inventory_management_system.models.Warehouse;

@Repository
public interface WarehouseRepositiory extends JpaRepository<Warehouse, Long> {
    
}
