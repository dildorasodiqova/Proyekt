INSERT INTO product (id, created_date, update_date,name, old_count, now_count, price, description, shop_id, category_id)
VALUES
    ('123e4567-e89b-12d3-a456-426614174013', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP,'Product1', 10, 8, 20.50, 'Product 1 description', '123e4567-e89b-12d3-a456-426614174000', '123e4567-e89b-12d3-a456-426614174005'),
    ('123e4567-e89b-12d3-a456-426614174014', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP,'Product2', 20, 15, 15.75, 'Product 2 description', '123e4567-e89b-12d3-a456-426614174001', '123e4567-e89b-12d3-a456-426614174005')on conflict (id) do nothing;

