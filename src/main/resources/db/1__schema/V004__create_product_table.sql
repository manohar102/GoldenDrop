CREATE TABLE product
(
    `id`            bigint(20) NOT NULL AUTO_INCREMENT,
    `brand_name`    varchar(250) DEFAULT NULL,
    `type`     varchar(25) DEFAULT NULL,
    `quantity`         int(250) NOT NULL,
    `store_id`         int NOT NULL,
    `stock_detail_id`         int NOT NULL,

    PRIMARY KEY (`id`),
    FOREIGN KEY (store_id) REFERENCES store(id),
    FOREIGN KEY (stock_detail_id) REFERENCES stock_detail(id),

) ENGINE = InnoDB
  DEFAULT CHARSET = utf8
  COLLATE = utf8_unicode_ci;