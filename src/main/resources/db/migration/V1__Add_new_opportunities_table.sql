CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE opportunities (
    id serial PRIMARY KEY,
    uuid uuid DEFAULT uuid_generate_v4 (),
    name varchar(100) NOT NULL
);