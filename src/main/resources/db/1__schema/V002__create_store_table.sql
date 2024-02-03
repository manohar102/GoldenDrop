CREATE TABLE store
(
    `id`            bigint(20) NOT NULL AUTO_INCREMENT,
    `license_id`    varchar(250) DEFAULT NULL,
    `name`     varchar(250) DEFAULT NULL,
    `email`         varchar(250) NOT NULL,
    `address`    varchar(256) NOT NULL,
    `created_at`    datetime DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`),
    UNIQUE KEY uk_license_id (`license_id`)
);