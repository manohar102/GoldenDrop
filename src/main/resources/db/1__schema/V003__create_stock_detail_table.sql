CREATE TABLE stock_detail
(
    `id`            bigint(20) NOT NULL AUTO_INCREMENT,
    `product_price`    varchar(250) DEFAULT NULL,
    `type`     varchar(3) DEFAULT NULL,
    `quantity`         int(250) NOT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8
  COLLATE = utf8_unicode_ci;