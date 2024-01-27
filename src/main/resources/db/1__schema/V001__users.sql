CREATE TABLE users
(
    `id`            bigint(20) NOT NULL AUTO_INCREMENT,
    `first_name`    varchar(250) DEFAULT NULL,
    `last_name`     varchar(250) DEFAULT NULL,
    `email`         varchar(250) NOT NULL,
    `password`      varchar(256) NOT NULL,
    `created_at`    datetime DEFAULT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY uk_email (`email`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8
  COLLATE = utf8_unicode_ci;