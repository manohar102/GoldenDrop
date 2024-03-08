ALTER TABLE users
ADD COLUMN store_id bigint(20) DEFAULT NULL,
ADD CONSTRAINT FOREIGN KEY (store_id) REFERENCES store(id);

ALTER TABLE users DROP CONSTRAINT fk_worker_shopkeeper;
ALTER TABLE users
DROP COLUMN shopkeeper_id;