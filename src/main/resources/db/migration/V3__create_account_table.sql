CREATE TABLE account (
    id SERIAL PRIMARY KEY,
    id_user UUID NOT NULL,
    account_number TEXT NOT NULL UNIQUE,
    account_digit TEXT NOT NULL,
    level TEXT NOT NULL,
    CONSTRAINT fk_user FOREIGN KEY (id_user) REFERENCES users(id) ON DELETE CASCADE
);


