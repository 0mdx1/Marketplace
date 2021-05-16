-- Find credentials record by email
SELECT id,
  role_id,
  email,
  password,
  is_enabled,
  failed_auth,
  last_failed_auth
FROM credentials
WHERE email = :email;

-- Find person record by id
SELECT id,
  credentials_id,
  name,
  surname,
  phone
FROM person
WHERE id = :id;

-- Find courier record by id
SELECT id, person_id, is_active
FROM courier
WHERE id = :id;
