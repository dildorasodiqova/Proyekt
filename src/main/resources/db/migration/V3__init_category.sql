INSERT INTO category_entity (id, created_date, update_date, name, parent_id)
VALUES ('123e4567-e89b-12d3-a456-426614174004', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'mevalar', NULL);
('123e4567-e89b-12d3-a456-426614174005', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP,'olma', '123e4567-e89b-12d3-a456-426614174004')on conflict (id) do nothing;


