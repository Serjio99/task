CREATE TABLE bank
(
    id                        UUID PRIMARY KEY,
    cash                      DECIMAL(19, 2) CHECK (cash >= 0) NOT NULL,
    non_cash                  DECIMAL(19, 2) CHECK (cash >= 0) NOT NULL,
    last_operation_bonus      DECIMAL(19, 2) CHECK (cash >= 0) NOT NULL,
    total_bonus_account       DECIMAL(19, 2) CHECK (cash >= 0) NOT NULL,
    last_operation_commission DECIMAL(19, 2) CHECK (cash >= 0) NOT NULL,
    total_commission_account  DECIMAL(19, 2) CHECK (cash >= 0) NOT NULL
);

CREATE TABLE client
(
    id          UUID PRIMARY KEY,
    first_name  TEXT   NOT NULL,
    last_name   TEXT   NOT NULL,
    middle_name TEXT,
    passport_id TEXT NOT NULL UNIQUE
);

CREATE TABLE bank_client
(
    bank_id   UUID NOT NULL,
    client_id UUID NOT NULL,
    PRIMARY KEY (bank_id, client_id),
    FOREIGN KEY (bank_id) REFERENCES bank (id) ON DELETE CASCADE,
    FOREIGN KEY (client_id) REFERENCES client (id) ON DELETE CASCADE
);

CREATE INDEX idx_bank_client_bank_id ON bank_client (bank_id);
CREATE INDEX idx_bank_client_client_id ON bank_client (client_id);
