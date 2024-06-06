INSERT INTO shop_entity (id, created_date, update_date, name, about_us, is_active)
VALUES
    ('123e4567-e89b-12d3-a456-426614174000', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'Shop1', 'mevalar dokoni', true),
    ('123e4567-e89b-12d3-a456-426614174001', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'Shop2', 'kiyimlar dokoni', true) on conflict (id) do nothing;