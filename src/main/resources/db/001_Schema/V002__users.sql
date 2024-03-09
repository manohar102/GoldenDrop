CREATE TABLE `users` (
     `id` bigint NOT NULL AUTO_INCREMENT,
     `first_name` varchar(250) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
     `last_name` varchar(250) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
     `user_name` varchar(250) COLLATE utf8mb3_unicode_ci NOT NULL,
     `phone_number` varchar(100) COLLATE utf8mb3_unicode_ci NOT NULL,
     `password` varchar(256) COLLATE utf8mb3_unicode_ci NOT NULL,
     `role` varchar(100) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
     `status` varchar(100) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
     `created_at` datetime DEFAULT CURRENT_TIMESTAMP,
     `updated_at` datetime DEFAULT CURRENT_TIMESTAMP,
     `store_id` bigint DEFAULT NULL,
     PRIMARY KEY (`id`),
     UNIQUE KEY `uk_username` (`user_name`),
     KEY `store_id` (`store_id`),
     CONSTRAINT `users_ibfk_1` FOREIGN KEY (`store_id`) REFERENCES `store` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;