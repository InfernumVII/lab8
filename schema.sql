CREATE TABLE auth (
    id SERIAL PRIMARY KEY,
    login varchar(20) UNIQUE CHECK (
        length(login) >= 4 AND 
        length(login) <= 20
    ), 
    password varchar(100) CHECK (
        length(password) >= 4 AND 
        length(password) <= 100
    ),
    salt BYTEA CHECK (octet_length(salt) = 16)
);

CREATE SEQUENCE dragon_id_seq START 1 INCREMENT 1;

CREATE TYPE color_enum AS ENUM ('YELLOW', 'ORANGE', 'WHITE', 'BROWN');
CREATE TYPE dragon_character_enum AS ENUM ('WISE', 'EVIL', 'CHAOTIC_EVIL', 'FICKLE');
CREATE TYPE dragon_type_enum AS ENUM ('WATER', 'UNDERGROUND', 'AIR', 'FIRE');

CREATE TABLE dragon_heads (
    head_id SERIAL PRIMARY KEY,
    eyes_count FLOAT
);

CREATE TABLE coordinates (
    coord_id SERIAL PRIMARY KEY,
    x BIGINT NOT NULL CHECK (x > -420),
    y BIGINT NOT NULL CHECK (y <= 699)
);

CREATE TABLE dragons (
    id INTEGER PRIMARY KEY DEFAULT nextval('dragon_id_seq'),
    name VARCHAR(255) NOT NULL CHECK (name <> ''),
    creation_date DATE NOT NULL DEFAULT CURRENT_DATE,
    age BIGINT NOT NULL CHECK (age > 0),
    color color_enum NOT NULL,
    type dragon_type_enum NOT NULL,
    character dragon_character_enum NOT NULL,
    coord_id INTEGER REFERENCES coordinates(coord_id) ON DELETE CASCADE,
    head_id INTEGER REFERENCES dragon_heads(head_id) ON DELETE CASCADE
);

CREATE TABLE owner_table (
    auth_id INTEGER REFERENCES auth(id),
    dragon_id INTEGER REFERENCES dragons(id) ON DELETE CASCADE,
    PRIMARY KEY (auth_id, dragon_id)
);