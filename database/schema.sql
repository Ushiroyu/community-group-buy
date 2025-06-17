-- 切换到项目库
CREATE DATABASE IF NOT EXISTS community
  DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_unicode_ci;
USE community;

-- 1. 用户表
CREATE TABLE `user` (
  `id` BIGINT AUTO_INCREMENT PRIMARY KEY,
  `username` VARCHAR(50) NOT NULL UNIQUE,
  `password` VARCHAR(255) NOT NULL,
  `email` VARCHAR(100),
  `phone` VARCHAR(20),
  `role` VARCHAR(20) DEFAULT 'user',
  `deleted` TINYINT(1) DEFAULT 0,
  `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB;

-- 2. 收货地址
CREATE TABLE `address` (
  `id` BIGINT AUTO_INCREMENT PRIMARY KEY,
  `user_id` BIGINT NOT NULL,
  `receiver_name` VARCHAR(50) NOT NULL,
  `receiver_phone` VARCHAR(20) NOT NULL,
  `province` VARCHAR(50), `city` VARCHAR(50), `district` VARCHAR(50),
  `detail` VARCHAR(255) NOT NULL,
  `is_default` TINYINT(1) DEFAULT 0,
  FOREIGN KEY (`user_id`) REFERENCES `user`(`id`)
) ENGINE=InnoDB;

-- 3. 社区
CREATE TABLE `community` (
  `id` BIGINT AUTO_INCREMENT PRIMARY KEY,
  `name` VARCHAR(100) NOT NULL,
  `location` VARCHAR(255),
  UNIQUE KEY(`name`)
) ENGINE=InnoDB;

-- 4. 团长
CREATE TABLE `leader` (
  `id` BIGINT AUTO_INCREMENT PRIMARY KEY,
  `user_id` BIGINT NOT NULL,
  `community_id` BIGINT NOT NULL,
  `status` ENUM('PENDING','APPROVED','REJECTED') DEFAULT 'PENDING',
  `apply_time` DATETIME DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (`user_id`) REFERENCES `user`(`id`),
  FOREIGN KEY (`community_id`) REFERENCES `community`(`id`)
) ENGINE=InnoDB;

-- 5. 供应商
CREATE TABLE `supplier` (
  `id` BIGINT AUTO_INCREMENT PRIMARY KEY,
  `name` VARCHAR(100) NOT NULL,
  `contact_phone` VARCHAR(20),
  `email` VARCHAR(100),
  `approved` TINYINT(1) DEFAULT 0
) ENGINE=InnoDB;

-- 6. 商品分类
CREATE TABLE `category` (
  `id` BIGINT AUTO_INCREMENT PRIMARY KEY,
  `name` VARCHAR(100) NOT NULL,
  `parent_id` BIGINT DEFAULT NULL,
  FOREIGN KEY (`parent_id`) REFERENCES `category`(`id`)
) ENGINE=InnoDB;

-- 7. 商品
CREATE TABLE `product` (
  `id` BIGINT AUTO_INCREMENT PRIMARY KEY,
  `supplier_id` BIGINT NOT NULL,
  `category_id` BIGINT NOT NULL,
  `name` VARCHAR(200) NOT NULL,
  `description` TEXT,
  `price` DECIMAL(10,2) NOT NULL,
  `stock` INT NOT NULL DEFAULT 0,
  `image_url` VARCHAR(255),
  `approved` TINYINT(1) DEFAULT 0,
  `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (`supplier_id`) REFERENCES `supplier`(`id`),
  FOREIGN KEY (`category_id`) REFERENCES `category`(`id`),
  INDEX(`price`), INDEX(`stock`)
) ENGINE=InnoDB;

-- 8. 订单
CREATE TABLE `order` (
  `id` BIGINT AUTO_INCREMENT PRIMARY KEY,
  `user_id` BIGINT NOT NULL,
  `leader_id` BIGINT,
  `supplier_id` BIGINT,
  `total_amount` DECIMAL(10,2) NOT NULL,
  `status` ENUM('CREATED','PAID','SHIPPED','DELIVERED','CANCELLED') DEFAULT 'CREATED',
  `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (`user_id`) REFERENCES `user`(`id`),
  FOREIGN KEY (`leader_id`) REFERENCES `leader`(`id`),
  FOREIGN KEY (`supplier_id`) REFERENCES `supplier`(`id`),
  INDEX(`status`), INDEX(`created_at`)
) ENGINE=InnoDB;

-- 9. 订单项
CREATE TABLE `order_item` (
  `id` BIGINT AUTO_INCREMENT PRIMARY KEY,
  `order_id` BIGINT NOT NULL,
  `product_id` BIGINT NOT NULL,
  `quantity` INT NOT NULL,
  `unit_price` DECIMAL(10,2) NOT NULL,
  FOREIGN KEY (`order_id`) REFERENCES `order`(`id`),
  FOREIGN KEY (`product_id`) REFERENCES `product`(`id`)
) ENGINE=InnoDB;
