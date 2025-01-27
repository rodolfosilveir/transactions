CREATE TABLE transaction (
    id SERIAL PRIMARY KEY,
    account_id INTEGER NOT NULL,
    operation_type_id INTEGER NOT NULL,
    amount NUMERIC(15, 2) NOT NULL,
    event_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    CONSTRAINT fk_account FOREIGN KEY (account_id) REFERENCES account (id) ON DELETE CASCADE,
    CONSTRAINT fk_operation_type FOREIGN KEY (operation_type_id) REFERENCES operation_type (id) ON DELETE CASCADE
);
