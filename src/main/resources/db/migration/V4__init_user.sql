INSERT INTO user_entity (id, created_date, update_date, name, username, email, password, user_role, is_active)
VALUES ('123e4567-e89b-12d3-a456-426614174000', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'Dido', 'dido1_2428', 'dido@gmail.com', 'password123', 'USER', true),
       ('123e4567-e89b-12d3-a456-426614174001', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'Dildora', 'dildora_04', 'dildora@gamil.com', 'password456', 'ADMIN', true)on conflict (id) do nothing;

