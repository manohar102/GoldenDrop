CREATE TABLE `product` (
   `id` bigint NOT NULL AUTO_INCREMENT,
   `brand_name` varchar(250) DEFAULT NULL,
   `type` varchar(25) DEFAULT NULL,
   `store_id` bigint NOT NULL,
   `quantity` varchar(10) NOT NULL,
   `image_name` varchar(250) DEFAULT NULL,
   `created_at` datetime DEFAULT CURRENT_TIMESTAMP,
   `updated_at` datetime DEFAULT CURRENT_TIMESTAMP,
   PRIMARY KEY (`id`),
   KEY `store_id` (`store_id`),
   CONSTRAINT `product_ibfk_1` FOREIGN KEY (`store_id`) REFERENCES `store` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;