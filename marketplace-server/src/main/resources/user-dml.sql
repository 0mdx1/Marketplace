-- Create credentials record
INSERT INTO credentials (role_id, email, password, is_enabled)
VALUES (:roleId, :email, :password, :isEnabled)
RETURNING id;

-- Create credentials record
INSERT INTO person(credentials_id, name, surname, phone)
VALUES (:credentials_id, :name, :surname, :phone)
RETURNING id;

-- Create courier record
INSERT INTO courier(person_id, is_active)
VALUES (:personId, :isActive)
RETURNING id;

-- Create Authorized user/Admin/Product manager
WITH cred_ins AS (
  INSERT INTO credentials (role_id, email, password, is_enabled)
  VALUES (:roleId, :email, :password, :isEnabled)
  RETURNING id AS cred_id
)
INSERT
INTO person(credentials_id, name, surname, phone)
  SELECT cred_id, :name, :surname, :phone
  FROM cred_ins
RETURNING id;

-- Create Courier
WITH cred_ins AS (
  INSERT INTO credentials (role_id, email, password, is_enabled)
  VALUES (:roleId, :email, :password, :isEnabled)
  RETURNING id AS cred_id
),
    pers_ins AS (
    INSERT INTO person (credentials_id, name, surname, phone)
      SELECT cred_id, :name, :surname, :phone FROM cred_ins
    RETURNING id AS pers_id
  )
INSERT
INTO courier(person_id, is_active)
  SELECT pers_id, :isActive
  FROM pers_ins
RETURNING id;

-- Update credentials record
UPDATE credentials
SET role_id = :roleId,
  email = :email,
  password = :password,
  is_enabled = :isEnabled
WHERE id = :id;

-- Enable/disable user account
UPDATE credentials
SET is_enabled = :isEnabled
WHERE id = :id;

-- Register unsuccessful auth attempt
WITH auth AS (
    SELECT COALESCE(failed_auth, 0) AS failed_auth FROM credentials WHERE id = :id
)
UPDATE credentials
SET (failed_auth, last_failed_auth) = (SELECT failed_auth + 1, CURRENT_TIMESTAMP FROM auth);

-- Update person record
UPDATE person
SET credentials_id = :credentialsId,
  name = :name,
  surname = :surname,
  phone = :phone
WHERE id = :id;

-- Update courier record
UPDATE courier
SET person_id = :personId,
  is_active = :isActive
WHERE id = :id;

-- Delete credentials record
DELETE FROM credentials WHERE id=:id;

-- Delete person record
DELETE FROM person WHERE id=:id;

-- Delete courier record
DELETE FROM courier WHERE id=:id;