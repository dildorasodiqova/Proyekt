INSERT INTO feedback (id, created_date, update_date,product_id, user_id, rate, text)
VALUES
    ('123e4567-e89b-12d3-a456-426614174015', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP,'<123e4567-e89b-12d3-a456-426614174013>', '<123e4567-e89b-12d3-a456-426614174000>', 4, 'Hammasi juda yaxshi mahsulotlar ekan'),
    ('123e4567-e89b-12d3-a456-426614174016', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP,'<123e4567-e89b-12d3-a456-426614174013>', '<123e4567-e89b-12d3-a456-426614174000>', 5, 'Har doim harid qilaman'),
    ('123e4567-e89b-12d3-a456-426614174017', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP,'<123e4567-e89b-12d3-a456-426614174014>', '<123e4567-e89b-12d3-a456-426614174001>', 4, 'Umuman yoqmadi qimmat ekan'),
    ('123e4567-e89b-12d3-a456-426614174018', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP,'<123e4567-e89b-12d3-a456-426614174014>', '<123e4567-e89b-12d3-a456-426614174001>', 5, 'Afsuslandim')on conflict (id) do nothing;
