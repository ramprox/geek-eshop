INSERT INTO categories (id, name) VALUES
    (nextval('categories_seq_gen'), 'Оперативная память'), -- 1
    (nextval('categories_seq_gen'), 'Жесткие диски'),      -- 2
    (nextval('categories_seq_gen'), 'Мониторы'),           -- 3
    (nextval('categories_seq_gen'), 'Принтеры'),           -- 4
    (nextval('categories_seq_gen'), 'Процессоры'),         -- 5
    (nextval('categories_seq_gen'), 'Видеокарты'),         -- 6
    (nextval('categories_seq_gen'), 'Материнские платы'),  -- 7
    (nextval('categories_seq_gen'), 'Смартфоны'),          -- 8
    (nextval('categories_seq_gen'), 'Планшеты');           -- 9
GO

INSERT INTO brands (id, name) VALUES
    (nextval('brands_seq_gen'), 'AMD'),          -- 1
    (nextval('brands_seq_gen'), 'Intel'),        -- 2
    (nextval('brands_seq_gen'), 'Kingston'),     -- 3
    (nextval('brands_seq_gen'), 'Apple'),        -- 4
    (nextval('brands_seq_gen'), 'Huawei'),       -- 5
    (nextval('brands_seq_gen'), 'Samsung'),      -- 6
    (nextval('brands_seq_gen'), 'Acer'),         -- 7
    (nextval('brands_seq_gen'), 'MSI'),          -- 8
    (nextval('brands_seq_gen'), 'Palit'),        -- 9
    (nextval('brands_seq_gen'), 'Kyocera'),      -- 10
    (nextval('brands_seq_gen'), 'HP'),           -- 11
    (nextval('brands_seq_gen'), 'WD'),           -- 12
    (nextval('brands_seq_gen'), 'Seagate'),      -- 13
    (nextval('brands_seq_gen'), 'Xiaomi'),       -- 14
    (nextval('brands_seq_gen'), 'GIGABYTE');     -- 15
GO

INSERT INTO products (id, name, price, category_id, brand_id) VALUES
    (nextval('products_seq_gen'), 'Процессор AMD Ryzen 5 3600 OEM', 14299, 5, 1),
    (nextval('products_seq_gen'), 'Оперативная память AMD Radeon R5 Entertainment Series [R538G1601U2S-U] 8 ГБ', 2999, 1, 1),
    (nextval('products_seq_gen'), '10.2" Планшет Apple iPad 2020 Wi-Fi 128 ГБ серый', 38999, 9, 4),
    (nextval('products_seq_gen'), '23.6" Монитор Acer K242HYLBBD черный', 9199, 3, 7),
    (nextval('products_seq_gen'), 'Процессор Intel Core i5-10400F OEM', 11799, 5, 2),
    (nextval('products_seq_gen'), 'Видеокарта GIGABYTE GeForce RTX 3060 GAMING OC (LHR) [GV-N3060GAMING OC-12GD Rev2.0]', 58999, 6, 15),
    (nextval('products_seq_gen'), 'Принтер лазерный Kyocera ECOSYS P2040dn', 22299, 4, 10),
    (nextval('products_seq_gen'), '1 ТБ Жесткий диск WD Blue [WD10EZEX]', 2999, 2, 12),
    (nextval('products_seq_gen'), '6.1" Смартфон Apple iPhone 12 128 ГБ фиолетовый', 67999, 8, 4),
    (nextval('products_seq_gen'), 'Материнская плата MSI Z490-A PRO', 11899, 7, 8),
    (nextval('products_seq_gen'), 'Принтер лазерный HP Laser 107r', 7999, 4, 11),
    (nextval('products_seq_gen'), 'Процессор AMD Ryzen 5 3500X OEM', 11799, 5, 1),
    (nextval('products_seq_gen'), 'Видеокарта Palit GeForce GTX 1660 DUAL [NE51660018J9-1161C]', 39999, 6, 9),
    (nextval('products_seq_gen'), '23.5" Монитор Samsung S24F354FHI черный', 9399, 3, 6),
    (nextval('products_seq_gen'), '1 ТБ Жесткий диск Seagate 7200 BarraCuda [ST1000DM010]', 2899, 2, 13),
    (nextval('products_seq_gen'), 'Оперативная память AMD Radeon R9 Gamer Series [R9S416G3206U2K] 16 ГБ', 6599, 1, 1),
    (nextval('products_seq_gen'), '10.4" Планшет Huawei Matepad 128 ГБ серый', 23999, 9, 5),
    (nextval('products_seq_gen'), '6.53" Смартфон Xiaomi Redmi 9 32 ГБ серый', 10499, 8, 14),
    (nextval('products_seq_gen'), 'Принтер лазерный HP LaserJet Pro M15a', 8499, 4, 11),
    (nextval('products_seq_gen'), '2 ТБ Жесткий диск WD Purple [WD20PURZ]', 5999, 2, 12),
    (nextval('products_seq_gen'), 'Материнская плата MSI B450M MORTAR MAX', 6999, 7, 8),
    (nextval('products_seq_gen'), 'Процессор AMD Ryzen 9 5900X BOX', 49299, 5, 1),
    (nextval('products_seq_gen'), '24" Монитор Samsung Odyssey G3 F24G35TFWI черный', 12999, 3, 6),
    (nextval('products_seq_gen'), '10.4" Планшет Samsung Galaxy Tab S6 Lite 64 ГБ серый', 24699, 9, 6),
    (nextval('products_seq_gen'), 'Видеокарта MSI GeForce RTX 3080 Ti VENTUS 3X 12G [RTX 3080 Ti VENTUS 3X 12G]', 157999, 6, 8),
    (nextval('products_seq_gen'), 'Материнская плата GIGABYTE B550 AORUS ELITE V2', 10499, 7, 15),
    (nextval('products_seq_gen'), '6.1" Смартфон Apple iPhone 11 128 ГБ красный', 46049, 8, 4),
    (nextval('products_seq_gen'), '4 ТБ Жесткий диск Seagate 5900 SkyHawk [ST4000VX007]', 10499, 2, 13),
    (nextval('products_seq_gen'), 'Оперативная память Kingston HyperX FURY Black [HX437C19FB3K2/32] 32 ГБ', 17799, 1, 3),
    (nextval('products_seq_gen'), 'Процессор Intel Core i5-10400F BOX', 14799, 5, 2);
GO