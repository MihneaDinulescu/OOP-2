CREATE TABLE IF NOT EXISTS bank_clients (
    id BIGSERIAL PRIMARY KEY,
    name varchar(50) NOT NULL,
    address varchar(100) NOT NULL,
    active_status boolean NOT NULL
);

CREATE TABLE bank_accounts (
                               account_id BIGINT PRIMARY KEY,
                               client_id BIGINT,
                               iban VARCHAR(34) UNIQUE NOT NULL,
                               balance DECIMAL(15, 2),
                               currency VARCHAR(10),
                               is_active BOOLEAN,
                               FOREIGN KEY (client_id) REFERENCES bank_clients(id) ON DELETE CASCADE
);