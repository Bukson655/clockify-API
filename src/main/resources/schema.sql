CREATE SEQUENCE IF NOT EXISTS
    hibernate_sequence;

CREATE EXTENSION IF NOT EXISTS
    "uuid-ossp";

CREATE TABLE IF NOT EXISTS "user" (
    id  BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    uuid uuid NOT NULL DEFAULT uuid_generate_v4(),
    login VARCHAR (50) UNIQUE NOT NULL,
    first_name VARCHAR (50) NOT NULL,
    last_name VARCHAR (50) NOT NULL,
    user_role VARCHAR (15) NOT NULL,
    password VARCHAR (100) NOT NULL,
    email VARCHAR (150) UNIQUE NOT NULL,
    cost_per_hour NUMERIC(10,2) NOT NULL);