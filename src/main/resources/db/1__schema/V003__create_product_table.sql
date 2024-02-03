CREATE TABLE product
(
    `id`            bigint(20) NOT NULL AUTO_INCREMENT,
    `brand_name`    varchar(250) DEFAULT NULL,
    `type`     varchar(25) DEFAULT NULL,
    `store_id`         bigint(20) NOT NULL,
    `created_at`    datetime DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`),
    FOREIGN KEY (store_id) REFERENCES store(id)

);