INSERT INTO product_photos (id, created_date, update_date, product_id, photo_id, order_index)
VALUES
    ('123e4567-e89b-12d3-a456-426614174033', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP,'123e4567-e89b-12d3-a456-426614174013', '123e4567-e89b-12d3-a456-426614174036', 1),
    ('123e4567-e89b-12d3-a456-426614174034', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP,'123e4567-e89b-12d3-a456-426614174013', '123e4567-e89b-12d3-a456-426614174037', 1)on conflict (id) do nothing;