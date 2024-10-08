CREATE TABLE IF NOT EXISTS public.users
(
    id           SERIAL PRIMARY KEY,
    email        TEXT NOT NULL UNIQUE,
    password     TEXT NOT NULL,
    phone_number TEXT UNIQUE,
    role         TEXT NOT NULL
);

CREATE TABLE IF NOT EXISTS public.user_info
(
    id      SERIAL PRIMARY KEY,
    user_id INTEGER NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS public.address
(
    id          SERIAL PRIMARY KEY,
    city        TEXT,
    country     TEXT,
    line1       TEXT,
    line2       TEXT,
    postal_code TEXT,
    state       TEXT
);

CREATE TABLE IF NOT EXISTS public.user_info_address
(
    user_info_id INTEGER NOT NULL,
    address_id   INTEGER NOT NULL
);