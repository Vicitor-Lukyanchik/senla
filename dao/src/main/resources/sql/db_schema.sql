DROP SCHEMA IF EXISTS public CASCADE;
CREATE SCHEMA public AUTHORIZATION postgres;
GRANT ALL ON SCHEMA public TO PUBLIC;
GRANT ALL ON SCHEMA public TO postgres;

DROP TABLE IF EXISTS reservations;
DROP TABLE IF EXISTS service_orders;
DROP TABLE IF EXISTS lodgers;
DROP TABLE IF EXISTS rooms;
DROP TABLE IF EXISTS services;
DROP TABLE IF EXISTS user_roles;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS roles;

CREATE TABLE lodgers(
                        id SERIAL PRIMARY KEY NOT NULL,
                        first_name VARCHAR(25) NOT NULL,
                        last_name VARCHAR (25) NOT NULL,
                        phone_number VARCHAR (7) NOT NULL
);

CREATE TABLE rooms(
                      id SERIAL PRIMARY KEY NOT NULL,
                      number INT NOT NULL,
                      cost DECIMAL NOT NULL,
                      capacity INT NOT NULL,
                      stars INT NOT NULL
);

CREATE TABLE reservations(
                             id SERIAL PRIMARY KEY NOT NULL,
                             start_date DATE NOT NULL,
                             end_date DATE NOT NULL,
                             lodger_id BIGINT NOT NULL REFERENCES lodgers(id),
                             room_id BIGINT NOT NULL REFERENCES rooms(id),
                             reserved BOOLEAN NOT NULL
);

CREATE TABLE services(
                         id SERIAL PRIMARY KEY NOT NULL,
                         name VARCHAR(25) NOT NULL,
                         cost DECIMAL NOT NULL
);

CREATE TABLE service_orders(
                               id SERIAL PRIMARY KEY NOT NULL,
                               date DATE NOT NULL,
                               lodger_id BIGINT NOT NULL REFERENCES lodgers(id),
                               service_id BIGINT NOT NULL REFERENCES services(id)
);

CREATE TABLE users(
    id SERIAL PRIMARY KEY NOT NULL,
    username VARCHAR(100) NOT NULL,
    email VARCHAR(255) NOT NULL,
    firstname VARCHAR(100) NOT NULL,
    lastname VARCHAR(100) NOT NULL,
    password VARCHAR (255) NOT NULL,
    status VARCHAR (25) NOT NULL DEFAULT 'ACTIVE'
);

CREATE TABLE roles(
    id SERIAL PRIMARY KEY NOT NULL,
    name VARCHAR (100),
    status VARCHAR (25) NOT NULL DEFAULT 'ACTIVE'
);

CREATE TABLE user_roles(
    user_id BIGINT NOT NULL REFERENCES users(id),
    role_id BIGINT NOT NULL REFERENCES roles(id)
);
