-- DROP table if exists users;

create table if not exists users(
    id      BIGSERIAL primary key,
    name    VARCHAR(255) NOT NULL,
    email   VARCHAR(255) NOT NULL UNIQUE,
    role    VARCHAR(100) NOT NULL DEFAULT 'USER',
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP NOT NULL DEFAULT NOW()

);

-- as there are no mappings in r2dbc, thus we gotta do it manually
create table if not exists profiles(
    id      BIGSERIAL primary key,
    user_id BIGINT NOT NULL REFERENCES users(id) on DELETE CASCADE,
    bio     TEXT,
    avatar_url    VARCHAR(500),
    created_at TIMESTAMP NOT NULL DEFAULT NOW()
    );
