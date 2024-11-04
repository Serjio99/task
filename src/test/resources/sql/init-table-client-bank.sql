INSERT INTO client (id, first_name, last_name, middle_name, passport_id)
VALUES ('123e4567-e89b-12d3-a456-426614174000'::uuid, 'David', 'Martinez', null,
        '8-800-555-35-35');

INSERT INTO bank (id, cash, non_cash, last_operation_bonus, total_bonus_account, last_operation_commission,
                  total_commission_account)
VALUES ('223e4567-e89b-12d3-a456-426614174001'::uuid, 5000.00, 5000.00,
        0.00, 0.00, 0.00, 0.00);

INSERT INTO bank_client (bank_id, client_id)
VALUES ('223e4567-e89b-12d3-a456-426614174001'::uuid, '123e4567-e89b-12d3-a456-426614174000'::uuid);


