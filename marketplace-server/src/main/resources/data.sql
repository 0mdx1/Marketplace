
INSERT INTO role (role )
VALUES
('ROLE_USER'),
('ROLE_ADMIN'),
('ROLE_COURIER'),
('ROLE_PRODUCT_MANAGER');

INSERT INTO firm(name) VALUES ('roshen'),
                              ('microsoft'),
                              ('valve'),
                              ('svitoch'),
                              ('coca-cola');

INSERT INTO category(name) VALUES ('fruits'),
                                  ('vegetables'),
                                  ('meat'),
                                  ('drinks');

INSERT INTO product(name, category_id) values ('banana', 1),
                                              ('chicken', 3),
                                              ('potato', 2),
                                              ('lemon', 1),
                                              ('water', 4),
                                              ('sausage', 3),
                                              ('juice', 4),
                                              ('tomato', 2),
                                              ('beer', 4),
                                              ('carrot', 2),
                                              ('orange', 1),
                                              ('fanta', 4),
                                              ('peach', 1),
                                              ('apple', 1);

INSERT INTO goods(prod_id, firm_id, price, unit, discount,
                  shipping_date, in_stock, status, image, description, quantity) values
(1, 1, 40, 'KILOGRAM', 50, '2021-06-03 01:55:00.000000', true, true, null, 'Casual Roshen Banana', 20),
(2, 2, 800, 'LITRE', 50, '2021-02-03 01:55:00.000000', true, false, null, 'Casual Roshen Banana', 40),
(3, 3, 4, 'ITEM', 50, '2021-06-13 01:55:00.000000', true, true, null, 'Casual Roshen Banana', 40),
(4, 4, 60, 'LITRE', 50, '2021-01-03 01:55:00.000000', true, true, null, 'Casual Roshen Banana', 30),
(5, 1, 500, 'KILOGRAM', 50, '2021-02-03 01:55:00.000000', true, false, null, 'Casual Roshen Banana', 50),
(6, 2, 45, 'LITRE', 50, '2021-02-03 01:55:00.000000', true, true, null, 'Casual Roshen Banana', 40),
(7, 3, 25, 'LITRE', 50, '2021-04-03 01:55:00.000000', true, true, null, 'Casual Roshen Banana', 40),
(8, 4, 74, 'ITEM', 50, '2021-06-03 01:55:00.000000', true, true, null, 'Casual Roshen Banana', 40),
(9, 1, 67, 'KILOGRAM', 50, '2021-06-03 01:55:00.000000', true, true, null, 'Casual Roshen Banana', 40),
(10, 2, 21, 'LITRE', 50, '2020-06-03 01:55:00.000000', true, true, null, 'Casual Roshen Banana', 40),
(11, 3, 100, 'KILOGRAM', 50, '2021-06-03 01:55:00.000000', true, true, null, 'Casual Roshen Banana', 40),
(12, 4, 90, 'KILOGRAM', 50, '2021-06-03 01:55:00.000000', true, true, null, 'Casual Roshen Banana', 40),
(13, 1, 200, 'ITEM', 50, '2021-06-03 01:55:00.000000', true, true, null, 'Casual Roshen Banana', 40),
(14, 1, 70, 'ITEM', 50, '2020-06-03 01:55:00.000000', true, true, null, 'Casual Roshen Banana', 40);

