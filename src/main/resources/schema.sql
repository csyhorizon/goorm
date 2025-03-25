CREATE TABLE IF NOT EXISTS products (
    id BIGINT PRIMARY KEY,
    item_name VARCHAR(100),
    price INT,
    item_count INT
);

INSERT INTO products (id, item_name, price, item_count) VALUES (1, '감자', 1000, 10);
INSERT INTO products (id, item_name, price, item_count) VALUES (2, '고기', 5000, 5);