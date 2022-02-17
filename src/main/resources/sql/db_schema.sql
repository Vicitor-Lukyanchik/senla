DROP SCHEMA IF EXISTS public CASCADE;
CREATE SCHEMA public AUTHORIZATION postgres;
GRANT ALL ON SCHEMA public TO PUBLIC;
GRANT ALL ON SCHEMA public TO postgres;

DROP TABLE IF EXISTS reservations;
DROP TABLE IF EXISTS service_orders;
DROP TABLE IF EXISTS lodgers;
DROP TABLE IF EXISTS rooms;
DROP TABLE IF EXISTS services;

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
                      stars INT NOT NULL,
                      repaired BOOLEAN NOT NULL
);

CREATE TABLE reservations(
                             id SERIAL PRIMARY KEY NOT NULL,
                             start_date DATE NOT NULL,
                             end_date DATE NOT NULL,
                             lodger_id BIGINT NOT NULL REFERENCES lodgers(id),
                             room_id BIGINT NOT NULL REFERENCES rooms(id)
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
