USE lukyanchik_task10;
INSERT INTO product (maker, model, type)
VALUES ('Asus', 'PC1', 'pc'),
       ('Samsung', 'PC2', 'pc'),
       ('Lenovo', 'PC3', 'pc'),
       ('Lenovo', 'PC4', 'pc'),
       ('Huawei', 'PC5', 'pc'),
       ('Lenovo', 'PC6', 'pc'),
       ('Samsung', 'PC7', 'pc');

INSERT INTO product (maker, model, type)
VALUES ('Samsung', 'PR1', 'printer'),
       ('Xiaomi', 'PR2', 'printer'),
       ('Huawei', 'PR3', 'printer');

INSERT INTO product (maker, model, type)
VALUES ('Samsung', 'LT1', 'laptop'),
       ('Redmi', 'LT3', 'laptop'),
       ('Mac', 'LT2', 'laptop'),
       ('Mac', 'LT4', 'laptop');

INSERT INTO pc (code, model, speed, ram, hd, cd, price)
VALUES (1, 'PC1', 2660, 6000, 128, '4x', 1000),
       (2, 'PC2', 2440, 4000, 64, '4x', 800),
       (3, 'PC3', 2880, 8000, 256, '4x', 1300),
       (4, 'PC4', 880, 1400, 32, '4x', 400),
       (5, 'PC5', 880, 1400, 32, '12x', 550),
       (6, 'PC6', 1440, 1800, 64, '24x', 930),
       (7, 'PC7', 1440, 1400, 64, '24x', 870);

INSERT INTO printer (code, model, color, type, price)
VALUES (1, 'PR1', 'n', 'Laser', 500),
       (2, 'PR2', 'y', 'Jet', 700),
       (3, 'PR3', 'y', 'Matrix', 900);

INSERT INTO laptop (code, model, speed, ram, hd, price, screen)
VALUES (1, 'LT1', 1440, 6000, 64, 400, 5),
       (2, 'LT2', 840, 4000, 32, 200, 3),
       (3, 'LT3', 1640, 8000, 128, 800, 6),
       (4, 'LT4', 1640, 12000, 128, 1400, 12);
