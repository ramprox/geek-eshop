INSERT INTO categories (id, name, category_id) VALUES
    (nextval('categories_seq_gen'), 'Комплектующие к ПК', null);
GO

UPDATE categories SET category_id = 10 WHERE id = 1;
GO

UPDATE categories SET category_id = 10 WHERE id = 2;
GO

UPDATE categories SET category_id = 10 WHERE id = 5;
GO

UPDATE categories SET category_id = 10 WHERE id = 6;
GO

UPDATE categories SET category_id = 10 WHERE id = 7;
GO