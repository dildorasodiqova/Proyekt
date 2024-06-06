INSERT INTO image_date (id, created_date, is_active, update_date, bytes, content_type, name, size)
VALUES ('123e4567-e89b-12d3-a456-426614174036', '2024-06-06 09:55:18.186695', true, '2024-06-06 09:55:18.186695',
        216121, 'image/jpeg', 'photo_2023-02-21_23-10-01.jpg', 72502),
       ('123e4567-e89b-12d3-a456-426614174037', '2024-06-06 09:55:18.186695', true, '2024-06-06 09:55:18.186695',
        216121, 'image/jpeg', 'photo_2023-02-21_23-10-01.jpg', 72502)
on conflict (id) do nothing;

