INSERT INTO main_pictures (product_id, picture_id)
SELECT products.id, (SELECT pictures.id
                       FROM pictures
                       WHERE pictures.product_id = products.id LIMIT 1) FROM products;
GO