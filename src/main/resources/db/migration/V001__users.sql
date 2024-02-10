CREATE TABLE users
(
    `id`            bigint(20) NOT NULL AUTO_INCREMENT,
    `first_name`    varchar(250) DEFAULT NULL,
    `last_name`     varchar(250) DEFAULT NULL,
    `user_name`     varchar(250) NOT NULL,
    `phone_number`  varchar(100) NOT NULL,
    `password`      varchar(256) NOT NULL,
    `shopkeeper_id` bigint(20)   DEFAULT NULL,
    `created_at`    datetime DEFAULT NULL,
    `role`          varchar(100) DEFAULT NULL,
    `status`        varchar(100) DEFAULT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY uk_email (`user_name`),
    CONSTRAINT `fk_worker_shopkeeper` FOREIGN KEY(`shopkeeper_id`) REFERENCES `users`(`id`) ON DELETE CASCADE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8
  COLLATE = utf8_unicode_ci;