CREATE TABLE role
(
  id   SERIAL NOT NULL
    CONSTRAINT pk_role_id
    PRIMARY KEY,
  role VARCHAR(50)
);

INSERT INTO role (role )
VALUES
		('ROLE_USER'),
		('ROLE_ADMIN'),
		('ROLE_COURIER'),
		('ROLE_PRODUCT_MANAGER');

ALTER TABLE role
  OWNER TO ivan;

CREATE UNIQUE INDEX ux_role_role
  ON role (role);

CREATE TABLE credentials
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

ALTER TABLE credentials
  OWNER TO ivan;

CREATE UNIQUE INDEX ux_credentials_email
  ON credentials (email);

CREATE TABLE person
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

ALTER TABLE person
  OWNER TO ivan;

CREATE TABLE courier
(
    person_id INTEGER NOT NULL
    CONSTRAINT courier_pk
    PRIMARY KEY
    CONSTRAINT fk_courier_person
    REFERENCES person,
    is_active BOOLEAN
);

ALTER TABLE courier
  OWNER TO ivan;