DROP TABLE courier;
DROP TABLE person;
DROP TABLE credentials;
DROP TABLE role;
DROP TABLE shopping_cart_item;

CREATE TABLE IF NOT EXISTS role
(
    id   SERIAL NOT NULL
        CONSTRAINT pk_role_id
        PRIMARY KEY,
    role VARCHAR(50)
);

CREATE UNIQUE INDEX IF NOT EXISTS ux_role_role
  ON role (role);

CREATE TABLE IF NOT EXISTS credentials
(
    id               SERIAL      NOT NULL
        CONSTRAINT pk_credentials_id
        PRIMARY KEY,
    role_id          INTEGER
        CONSTRAINT fk_credentials_role
        REFERENCES role,
    email            VARCHAR(50) NOT NULL,
    password         VARCHAR(100),
    is_enabled       BOOLEAN,
    failed_auth      INTEGER DEFAULT 0,
    last_failed_auth TIMESTAMP,
    auth_link		   VARCHAR(100),
    auth_link_date   TIMESTAMP
);


CREATE UNIQUE INDEX IF NOT EXISTS ux_credentials_email
  ON credentials (email);

CREATE TABLE IF NOT EXISTS person
(
    id             SERIAL NOT NULL
        CONSTRAINT person_pk
        PRIMARY KEY,
    credentials_id INTEGER
        CONSTRAINT fk_person_credentials
        REFERENCES credentials (id),
    name           VARCHAR(50),
    surname        VARCHAR(50),
    phone          VARCHAR(50),
    birthday       DATE
);

CREATE TABLE IF NOT EXISTS courier
(
    id        SERIAL NOT NULL
        CONSTRAINT courier_pk
        PRIMARY KEY,
    person_id INTEGER
        CONSTRAINT fk_courier_person
        REFERENCES person,
    is_active BOOLEAN
);

CREATE TABLE IF NOT EXISTS shopping_cart_item
(
    user_id     INTEGER                  NOT NULL,
    goods_id    INTEGER                  NOT NULL
        CONSTRAINT shopping_cart_item_goods_id_fk
            REFERENCES goods,
    quantity    INTEGER                  NOT NULL,
    adding_time BIGINT NOT NULL,
    CONSTRAINT shopping_cart_item_pk
        PRIMARY KEY (user_id, goods_id)
);

CREATE TABLE IF NOT EXISTS firm
(
    id SERIAL NOT NULL
        CONSTRAINT firm_pk PRIMARY KEY,
    name VARCHAR(50)
);

CREATE TYPE unit_type AS ENUM ('KILOGRAM', 'LITRE', 'ITEM');


CREATE TABLE IF NOT EXISTS category
(
    id SERIAL NOT NULL
        CONSTRAINT category_pk PRIMARY KEY,
    name VARCHAR(50)
);

CREATE TABLE IF NOT EXISTS product
(
    id SERIAL NOT NULL
        CONSTRAINT product_pk PRIMARY KEY,
    name VARCHAR(50),
    category_id INTEGER
        CONSTRAINT fk_category
        REFERENCES category(id)
);

CREATE TABLE IF NOT EXISTS goods
(
    id SERIAL NOT NULL
        CONSTRAINT goods_pk PRIMARY KEY,
    prod_id INTEGER
        CONSTRAINT fk_product
        REFERENCES product(id),
    firm_id INTEGER
        CONSTRAINT fk_firm
        REFERENCES firm(id),
    quantity INTEGER DEFAULT 0,
    price DECIMAL(12,2),
    unit unit_type,
    discount DECIMAL(12,2),
    shipping_date TIMESTAMP,
    in_stock BOOLEAN,
    status varchar(50),
    image varchar(100),
    description text
);
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
                  shipping_date, in_stock, status, image, description) values
(1, 1, 40, 'KILOGRAM', 50, null, true, null, null, 'Casual Roshen Banana'),
(2, 2, 800, 'LITRE', 50, null, true, null, null, 'Casual Roshen Banana'),
(3, 3, 4, 'ITEM', 50, null, true, null, null, 'Casual Roshen Banana'),
(4, 4, 60, 'LITRE', 50, null, true, null, null, 'Casual Roshen Banana'),
(5, 1, 500, 'KILOGRAM', 50, null, true, null, null, 'Casual Roshen Banana'),
(6, 2, 45, 'LITRE', 50, null, true, null, null, 'Casual Roshen Banana'),
(7, 3, 25, 'LITRE', 50, null, true, null, null, 'Casual Roshen Banana'),
(8, 4, 74, 'ITEM', 50, null, true, null, null, 'Casual Roshen Banana'),
(9, 1, 67, 'KILOGRAM', 50, null, true, null, null, 'Casual Roshen Banana'),
(10, 2, 21, 'LITRE', 50, null, true, null, null, 'Casual Roshen Banana'),
(11, 3, 100, 'KILOGRAM', 50, null, true, null, null, 'Casual Roshen Banana'),
(12, 4, 90, 'KILOGRAM', 50, null, true, null, null, 'Casual Roshen Banana'),
(13, 1, 200, 'ITEM', 50, null, true, null, null, 'Casual Roshen Banana'),
(14, 1, 70, 'ITEM', 50, null, true, null, null, 'Casual Roshen Banana');
