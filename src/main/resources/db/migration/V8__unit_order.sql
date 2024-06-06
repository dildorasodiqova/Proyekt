INSERT INTO orders (id, created_date, update_date, user_id, price, status, delivery)
VALUES
    ('123e4567-e89b-12d3-a456-426614174026', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, '123e4567-e89b-12d3-a456-426614174000', 150.75, 'NEW', true),
    ('123e4567-e89b-12d3-a456-426614174027', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, '123e4567-e89b-12d3-a456-426614174001', 275.50, 'NEW', false)on conflict (id) do nothing;
