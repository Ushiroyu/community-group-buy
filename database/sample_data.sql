-- Sample data for community group-buy system

-- Users
INSERT INTO `user` (id, username, password, email, phone, role) VALUES
  (1, 'admin', '$2b$12$pcL/0e6i/nuuk3YQFpMDOeEHj0toLF24TubN0YaPD2vxff3a9gW1q', 'admin@example.com', '13800000000', 'admin'),
  (2, 'leader1', '$2b$12$Hkj9SNNxe4ZfZP6G96SbrOiuMkksJ2SeLurDNBnTHoQMX.11J37yy', 'leader1@example.com', '13800000001', 'leader'),
  (3, 'supplier1', '$2b$12$QPV6ZvqHIc2kOvp7/cEBaumHyRwIMlYN24IzrMI6btSA3HRTFMb9y', 'supplier1@example.com', '13800000002', 'supplier'),
  (4, 'user1', '$2b$12$7IdbAypFUtAzQJURztXMROWqeDHgyPVmsxm6ddDx6jLHQbgwbfoty', 'user1@example.com', '13800000003', 'user');

-- Communities
INSERT INTO `community` (id, name, location) VALUES
  (1, 'Central Community', '123 Main St'),
  (2, 'West Side', '456 West St');

-- Leaders
INSERT INTO `leader` (id, user_id, community_id, status, apply_time) VALUES
  (1, 2, 1, 'APPROVED', NOW());

-- Suppliers
INSERT INTO `supplier` (id, name, contact_phone, email, approved) VALUES
  (1, 'FreshFruits Inc', '13800001000', 'contact@freshfruits.com', 1);

-- Categories
INSERT INTO `category` (id, name, parent_id) VALUES
  (1, 'Fruits', NULL),
  (2, 'Vegetables', NULL);

-- Products
INSERT INTO `product` (id, supplier_id, category_id, name, description, price, stock, image_url, approved, created_at) VALUES
  (1, 1, 1, 'Apple', 'Fresh red apples', 2.99, 100, '/images/apple.jpg', 1, NOW()),
  (2, 1, 2, 'Carrot', 'Organic carrots', 1.49, 200, '/images/carrot.jpg', 1, NOW());

-- Addresses
INSERT INTO `address` (id, user_id, receiver_name, receiver_phone, province, city, district, detail, is_default) VALUES
  (1, 4, 'User One', '13800000003', 'SomeProvince', 'SomeCity', 'District1', '123 Street', 1);

-- Orders
INSERT INTO `order` (id, user_id, leader_id, supplier_id, total_amount, status, created_at) VALUES
  (1, 4, 1, 1, 5.97, 'CREATED', NOW());

-- Order Items
INSERT INTO `order_item` (id, order_id, product_id, quantity, unit_price) VALUES
  (1, 1, 1, 1, 2.99),
  (2, 1, 2, 2, 1.49);
