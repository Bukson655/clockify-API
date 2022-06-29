CREATE SEQUENCE IF NOT EXISTS
    hibernate_sequence;

CREATE EXTENSION IF NOT EXISTS
    "uuid-ossp";

CREATE TABLE IF NOT EXISTS "user" (
    id BIGINT PRIMARY KEY,
    uuid uuid NOT NULL UNIQUE DEFAULT uuid_generate_v4(),
    login VARCHAR (50) UNIQUE NOT NULL,
    first_name VARCHAR (50) NOT NULL,
    last_name VARCHAR (50) NOT NULL,
    user_role VARCHAR (7) NOT NULL CHECK (user_role IN ('ADMIN', 'USER', 'MANAGER')),
    password VARCHAR (100) NOT NULL,
    email VARCHAR (150) UNIQUE NOT NULL,
    cost_per_hour NUMERIC(10,2) NOT NULL);

CREATE TABLE IF NOT EXISTS "project" (
    id BIGINT PRIMARY KEY,
    uuid uuid NOT NULL UNIQUE DEFAULT uuid_generate_v4(),
    title VARCHAR (50) UNIQUE NOT NULL,
    description VARCHAR (255),
    start_date DATE NOT NULL,
    end_date DATE NOT NULL,
    current_spending NUMERIC(10,2) NOT NULL,
    budget NUMERIC(10,2) NOT NULL);

CREATE TABLE IF NOT EXISTS "project_user" (
    project_id BIGINT REFERENCES project,
    user_id BIGINT REFERENCES "user",
    PRIMARY KEY (project_id, user_id));