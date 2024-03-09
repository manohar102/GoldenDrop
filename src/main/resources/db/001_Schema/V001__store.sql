CREATE TABLE `store` (
     `id` bigint NOT NULL AUTO_INCREMENT,
     `license_id` varchar(250) DEFAULT NULL,
     `name` varchar(250) DEFAULT NULL,
     `address` varchar(256) NOT NULL,
     `created_at` datetime DEFAULT CURRENT_TIMESTAMP,
     `updated_at` datetime DEFAULT CURRENT_TIMESTAMP,
     PRIMARY KEY (`id`),
     UNIQUE KEY `uk_license_id` (`license_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;