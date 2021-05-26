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
  phone          VARCHAR(50)
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
    id          SERIAL NOT NULL
        CONSTRAINT shopping_cart_item_pk
        PRIMARY KEY,
    user_id     INTEGER   NOT NULL,
    goods_id    INTEGER   NOT NULL,
    quantity    INTEGER   NOT NULL,
    adding_time TIMESTAMP NOT NULL
);