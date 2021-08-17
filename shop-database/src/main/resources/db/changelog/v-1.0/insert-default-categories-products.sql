INSERT INTO categories (name) VALUES
    ('Оперативная память'),
    ('Жесткие диски'),
    ('Мониторы'),
    ('Принтеры'),
    ('Процессоры'),
    ('Видеокарты'),
    ('Материнские платы'),
    ('Смартфоны'),
    ('Планшеты');
GO

INSERT INTO products (name, cost, category_id) VALUES
    ('Ryzen 5 3600 OEM', 14299, (SELECT id FROM categories WHERE categories.name = 'Процессоры')),
    ('Radeon R5 R538G1601U2S-U', 2999, (SELECT id FROM categories WHERE categories.name = 'Оперативная память')),
    ('iPad 2020 10.2"', 38999, (SELECT id FROM categories WHERE categories.name = 'Планшеты')),
    ('K242HYLBBD 23.6"', 9199, (SELECT id FROM categories WHERE categories.name = 'Мониторы')),
    ('Core i5-10400F OEM', 11799, (SELECT id FROM categories WHERE categories.name = 'Процессоры')),
    ('GeForce RTX 3060 GAMING', 58999, (SELECT id FROM categories WHERE categories.name = 'Видеокарты')),
    ('ECOSYS P2040dn', 22299, (SELECT id FROM categories WHERE categories.name = 'Принтеры')),
    ('WD10EZEX 1TБ', 2999, (SELECT id FROM categories WHERE categories.name = 'Жесткие диски')),
    ('iPhone 12', 67999, (SELECT id FROM categories WHERE categories.name = 'Смартфоны')),
    ('Z490-A PRO', 11899, (SELECT id FROM categories WHERE categories.name = 'Материнские платы')),
    ('Laser 107r', 7999, (SELECT id FROM categories WHERE categories.name = 'Принтеры')),
    ('Ryzen 5 3500X OEM', 11799, (SELECT id FROM categories WHERE categories.name = 'Процессоры')),
    ('GeForce GTX 1660 DUAL', 39999, (SELECT id FROM categories WHERE categories.name = 'Видеокарты')),
    ('S24F354FHI 23.5"', 9399, (SELECT id FROM categories WHERE categories.name = 'Мониторы')),
    ('ST1000DM010 1ТБ', 2899, (SELECT id FROM categories WHERE categories.name = 'Жесткие диски')),
    ('Radeon R9 R9S416G3206U2K', 6599, (SELECT id FROM categories WHERE categories.name = 'Оперативная память')),
    ('Matepad 10.4"', 23999, (SELECT id FROM categories WHERE categories.name = 'Планшеты')),
    ('Redmi 9', 10499, (SELECT id FROM categories WHERE categories.name = 'Смартфоны')),
    ('LaserJet Pro M15a', 8499, (SELECT id FROM categories WHERE categories.name = 'Принтеры')),
    ('WD20PURZ 2ТБ', 5999, (SELECT id FROM categories WHERE categories.name = 'Жесткие диски')),
    ('B450M MORTAR MAX', 6999, (SELECT id FROM categories WHERE categories.name = 'Материнские платы')),
    ('Ryzen 9 5900X BOX', 49299, (SELECT id FROM categories WHERE categories.name = 'Процессоры')),
    ('F24G35TFWI 24"', 12999, (SELECT id FROM categories WHERE categories.name = 'Мониторы')),
    ('Galaxy Tab S6 Lite 10.4"', 24699, (SELECT id FROM categories WHERE categories.name = 'Планшеты')),
    ('GeForce RTX 3080 Ti', 157999, (SELECT id FROM categories WHERE categories.name = 'Видеокарты')),
    ('B550 AORUS ELITE V2', 10499, (SELECT id FROM categories WHERE categories.name = 'Материнские платы')),
    ('iPhone 11', 46049, (SELECT id FROM categories WHERE categories.name = 'Смартфоны')),
    ('ST4000VX007 4ТБ', 10499, (SELECT id FROM categories WHERE categories.name = 'Жесткие диски')),
    ('HyperX HX437C19FB3K2/32', 17799, (SELECT id FROM categories WHERE categories.name = 'Оперативная память')),
    ('Core i5-10400 BOX', 14799, (SELECT id FROM categories WHERE categories.name = 'Процессоры'));
GO