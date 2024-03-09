CREATE TABLE `sale_record` (
       `id` bigint NOT NULL AUTO_INCREMENT,
       `sale_amount` double DEFAULT '0',
       `digital_amount` double DEFAULT '0',
       `online_amount` double DEFAULT '0',
       `expenses` double DEFAULT '0',
       `store_id` bigint NOT NULL,
       `user_id` bigint NOT NULL,
       `comments` varchar(255) DEFAULT NULL,
       `created_at` datetime DEFAULT CURRENT_TIMESTAMP,
       `updated_at` datetime DEFAULT CURRENT_TIMESTAMP,
       PRIMARY KEY (`id`),
       KEY `store_id` (`store_id`),
       KEY `user_id` (`user_id`),
       CONSTRAINT `sale_record_ibfk_1` FOREIGN KEY (`store_id`) REFERENCES `store` (`id`),
       CONSTRAINT `sale_record_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;