drop table if exists ITEMS;
drop table if exists WAREHOUSE;

create table WAREHOUSE (
	id INT auto_increment primary key,
    name varchar(100) NOT NULL,
    max_capacity INT NOT NULL,
    warehouse_location varchar(100) NOT NULL
);

create table ITEMS (
    id INT auto_increment primary key,
    item_name varchar(100) NOT NULL,
    sku varchar(255) NOT NULL, 
    description varchar(255) NOT NULL,
    quantity INT NOT NULL,
    storage_location varchar(255) NOT NULL,
    warehouse_id INT NOT NULL,
    FOREIGN KEY (warehouse_id) REFERENCES WAREHOUSE(id)
);

insert into warehouse (name, max_capacity, warehouse_location) values ('Amazon', 20, 'Dallas, TX');
insert into warehouse (name, max_capacity, warehouse_location) values ('Walmart', 15, 'Atlanta, GA');
insert into warehouse (name, max_capacity, warehouse_location) values ('Target', 10, 'Richmond, VA');

INSERT INTO ITEMS (item_name, sku, description, quantity, storage_location, warehouse_id)
VALUES
('Wireless Keyboard', 'SKU-WKB-001', 'Compact wireless keyboard with Bluetooth support', 120, 'Aisle 4, Shelf B', 1),

('LED Monitor 27-inch', 'SKU-MON-027', '27-inch 1080p LED display ideal for office use', 75, 'Aisle 2, Shelf A', 2),

('USB-C Charging Cable', 'SKU-CBL-USBC-010', 'Durable 10ft USB-C fast charging cable', 300, 'Aisle 1, Shelf C', 3);