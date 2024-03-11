CREATE TABLE `stock_detail` (
    `id` bigint NOT NULL AUTO_INCREMENT,
    `product_price` double DEFAULT NULL,
    `type` varchar(3) DEFAULT NULL,
    `quantity` int NOT NULL,
    `product_id` bigint NOT NULL,
    `created_by`  bigint NOT NULL,
    `created_at` datetime DEFAULT CURRENT_TIMESTAMP,
    `updated_at` datetime DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`),
    KEY `product_id` (`product_id`),
    CONSTRAINT `stock_detail_ibfk_1` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;