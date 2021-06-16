
DROP TABLE IF EXISTS order_goods;
DROP TABLE IF EXISTS order_details;
DROP TABLE IF EXISTS courier;
DROP TABLE IF EXISTS person;
DROP TABLE IF EXISTS credentials;
DROP TABLE IF EXISTS role;
DROP TABLE IF EXISTS shopping_cart_item;
DROP TABLE IF EXISTS goods;
DROP TABLE IF EXISTS firm;
DROP TABLE IF EXISTS product;
DROP TABLE IF EXISTS category;

DROP TYPE IF EXISTS delivery_status;
DROP TYPE IF EXISTS unit_type;

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
    auth_link        VARCHAR(100),
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

CREATE TABLE IF NOT EXISTS firm
(
    id   SERIAL NOT NULL
        CONSTRAINT firm_pk PRIMARY KEY,
    name VARCHAR(50)
);

CREATE TYPE unit_type AS ENUM ('KILOGRAM', 'LITRE', 'ITEM');


CREATE TABLE IF NOT EXISTS category
(
    id   SERIAL NOT NULL
        CONSTRAINT category_pk PRIMARY KEY,
    name VARCHAR(50)
);

CREATE TABLE IF NOT EXISTS product
(
    id          SERIAL NOT NULL
        CONSTRAINT product_pk PRIMARY KEY,
    name        VARCHAR(50),
    category_id INTEGER
        CONSTRAINT fk_category
            REFERENCES category (id)
);

CREATE TABLE IF NOT EXISTS goods
(
    id            SERIAL NOT NULL
        CONSTRAINT goods_pk PRIMARY KEY,
    prod_id       INTEGER
        CONSTRAINT fk_product
            REFERENCES product (id),
    firm_id       INTEGER
        CONSTRAINT fk_firm
            REFERENCES firm (id),
    quantity      INTEGER DEFAULT 0,
    price         DECIMAL(12, 2),
    unit          unit_type,
    discount      DECIMAL(12, 2),
    shipping_date TIMESTAMP,
    in_stock      BOOLEAN,
    status        varchar(50),
    image         varchar(100),
    description   text
);

CREATE TABLE IF NOT EXISTS shopping_cart_item
(
    user_id     INTEGER NOT NULL,
    goods_id    INTEGER NOT NULL
        CONSTRAINT shopping_cart_item_goods_id_fk
            REFERENCES goods,
    quantity    INTEGER NOT NULL,
    adding_time BIGINT  NOT NULL,
    CONSTRAINT shopping_cart_item_pk
    PRIMARY KEY (user_id, goods_id)
);

--ORDER

CREATE TYPE delivery_status AS ENUM ('SUBMITTED', 'IN_DELIVERY', 'DELIVERED');

CREATE TABLE IF NOT EXISTS order_details
(
	id 				SERIAL,
	person_id 		INTEGER NOT NULL,
	courier_id 		INTEGER NOT NULL,
	delivery_time 	TIMESTAMP WITHOUT TIME ZONE NOT NULL,
	address 		VARCHAR(200),
	status 			DELIVERY_STATUS NOT NULL,
	comment 		TEXT,
	disturb 		BOOLEAN NOT NULL DEFAULT true,
	total_sum 		DECIMAL(12, 2),
	discount_sum 	DECIMAL(12, 2),
	
	CONSTRAINT order_details_pk
	PRIMARY KEY (id),
	
	CONSTRAINT person_order_details_fk
	FOREIGN KEY(person_id)
	REFERENCES person(id),
	
	CONSTRAINT person_courier_order_details_fk
	FOREIGN KEY(courier_id)
	REFERENCES person(id)
);


CREATE TABLE IF NOT EXISTS order_goods 
(
	goods_id INTEGER NOT NULL,
	order_id INTEGER NOT NULL,
	quantity INTEGER NOT NULL DEFAULT 0,
	sum 	 DECIMAL NOT NULL DEFAULT 0,
	
	CONSTRAINT goods_order_goods_fk
	FOREIGN KEY(goods_id)
	REFERENCES goods(id),
	
	CONSTRAINT order_detals_order_goods_fk
	FOREIGN KEY(order_id)
	REFERENCES order_details(id),
	
	CONSTRAINT order_goods_pk
	PRIMARY KEY (goods_id, order_id)
);

