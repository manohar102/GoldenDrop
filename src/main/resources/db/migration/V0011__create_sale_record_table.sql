CREATE TABLE sale_record
(
    `id`            bigint(20) NOT NULL AUTO_INCREMENT,
    `sale_amount`    double DEFAULT 0,
    `digital_amount`    double DEFAULT 0,
    `online_amount`    double DEFAULT 0,
    `expenses`    double DEFAULT 0,
    `store_id`         bigint(20) NOT NULL,
    `user_id`         bigint(20) NOT NULL,
    `comments`         varchar(255),
    `created_at`    datetime DEFAULT CURRENT_TIMESTAMP,
    `updated_at`    datetime DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`),
    FOREIGN KEY (store_id) REFERENCES store(id),
    FOREIGN KEY (user_id) REFERENCES users(id)

);