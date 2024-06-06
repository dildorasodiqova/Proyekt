INSERT INTO image_date (id, created_date, update_date, name, content_type, size, bytes)
VALUES
    ('123e4567-e89b-12d3-a456-426614174036', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP,'14.jpg', 'image/jpeg', ?, LOAD_FILE('C:\MyProject\Proyekt\src\main\resources\image\14.jpg')),
    ('123e4567-e89b-12d3-a456-426614174037', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP,'14.jpg', 'image/jpeg', ?, LOAD_FILE('C:\MyProject\Proyekt\src\main\resources\image\14.jpg')) on conflict (id) do nothing;
