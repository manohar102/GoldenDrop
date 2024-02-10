CREATE TABLE stock_detail
(
    `id`            bigint(20) NOT NULL AUTO_INCREMENT,
    `product_price`    varchar(250) DEFAULT NULL,
    `type`     varchar(3) DEFAULT NULL,
    `quantity`         int(250) NOT NULL,
    `product_id`         bigint(20) NOT NULL,
    `created_at`    datetime DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`),
    FOREIGN KEY (product_id) REFERENCES product(id)
);