INSERT INTO orderProduct (id, created_date, update_date, order_id, product_id, count, price)
VALUES
    ('123e4567-e89b-12d3-a456-426614174031', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, '123e4567-e89b-12d3-a456-426614174026', '<123e4567-e89b-12d3-a456-426614174013>', 2, 41.00),
    ('123e4567-e89b-12d3-a456-426614174032', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, '123e4567-e89b-12d3-a456-426614174026', '<123e4567-e89b-12d3-a456-426614174014', 3, 62.25)on conflict (id) do nothing;
