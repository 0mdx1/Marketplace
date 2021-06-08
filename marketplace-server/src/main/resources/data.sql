INSERT INTO role (role )
VALUES
('ROLE_USER'),
('ROLE_ADMIN'),
('ROLE_COURIER'),
('ROLE_PRODUCT_MANAGER');

INSERT INTO public.category (id, name) VALUES (1, 'cat1');
INSERT INTO public.category (id, name) VALUES (2, 'cat2');

INSERT INTO public.firm (id, name) VALUES (1, 'firm1');

INSERT INTO public.unit (id, name) VALUES (1, 'KILOGRAM');

INSERT INTO public.product (id, name, category_id) VALUES (1, 'prod1', 1);
INSERT INTO public.product (id, name, category_id) VALUES (2, 'prod2', 2);
INSERT INTO public.product (id, name, category_id) VALUES (3, 'prod3', 2);

INSERT INTO public.goods (id, prod_id, firm_id, quantity, price, unit_id, discount, shipping_date, in_stock, status, image, description) VALUES (1, 1, 1, 40, 20.50, 1, 0.00, '2021-06-07 21:54:18.000000', true, 'ready', 'imag1', 'descr1');
INSERT INTO public.goods (id, prod_id, firm_id, quantity, price, unit_id, discount, shipping_date, in_stock, status, image, description) VALUES (2, 2, 1, 100, 40.00, 1, 0.05, '2021-06-07 21:55:23.000000', true, 'ready', 'imag2', 'descr2');
INSERT INTO public.goods (id, prod_id, firm_id, quantity, price, unit_id, discount, shipping_date, in_stock, status, image, description) VALUES (3, 3, 1, 80, 10.00, 1, 0.10, '2021-06-07 21:55:52.000000', true, 'ready', 'imag2', 'descr3');