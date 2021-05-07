CREATE TABLE IF NOT EXISTS products
(
    id SERIAL PRIMARY KEY ,
    types_id INTEGER NOT NULL,
    name TEXT NOT NULL ,
    price INTEGER ,
    weight INTEGER ,
    description TEXT ,
    countries_id INTEGER ,
    FOREIGN KEY (types_id) REFERENCES types (id) ON DELETE CASCADE ,
    FOREIGN KEY (countries_id) REFERENCES countries (id) ON DELETE CASCADE
    );
CREATE TABLE IF NOT EXISTS types
(
    id SERIAL PRIMARY KEY ,
    name TEXT NOT NULL
    );
CREATE TABLE IF NOT EXISTS countries
(
    id SERIAL PRIMARY KEY ,
    name TEXT NOT NULL
    );
CREATE TABLE IF NOT EXISTS users
(
    id    SERIAL PRIMARY KEY ,
    login  VARCHAR(200) NOT NULL ,
    password VARCHAR(200) NOT NULL
);