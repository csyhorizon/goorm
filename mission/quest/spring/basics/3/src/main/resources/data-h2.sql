CREATE TABLE IF NOT EXISTS product (
                                       id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                       name VARCHAR(255) NOT NULL,
    price DECIMAL(10,2) NOT NULL,
    quantity INT NOT NULL
    );

INSERT INTO product (name, price, quantity) VALUES
                                                ('iPhone 14 Pro', 1299.99, 50),
                                                ('Samsung Galaxy S23', 999.99, 45),
                                                ('MacBook Pro 16"', 2499.99, 30),
                                                ('AirPods Pro', 249.99, 100),
                                                ('iPad Air', 599.99, 60),
                                                ('Sony WH-1000XM4', 349.99, 40),
                                                ('Nintendo Switch', 299.99, 75),
                                                ('PS5', 499.99, 25),
                                                ('LG OLED TV 65"', 1999.99, 20),
                                                ('Dell XPS 13', 1299.99, 35);