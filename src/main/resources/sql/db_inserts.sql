INSERT INTO product (maker, model, type)
VALUES ('Asus', 'PC1', 'pc');
INSERT INTO product (maker, model, type)
VALUES ('Samsung', 'PC2', 'pc');
INSERT INTO product (maker, model, type)
VALUES ('Lenovo', 'PC3', 'pc');
INSERT INTO product (maker, model, type)
VALUES ('Lenovo', 'PC4', 'pc');
INSERT INTO product (maker, model, type)
VALUES ('Huawei', 'PC5', 'pc');
INSERT INTO product (maker, model, type)
VALUES ('Lenovo', 'PC6', 'pc');
INSERT INTO product (maker, model, type)
VALUES ('Samsung', 'PC7', 'pc');

INSERT INTO product (maker, model, type)
VALUES ('Samsung', 'PR1', 'printer');
INSERT INTO product (maker, model, type)
VALUES ('Xiaomi', 'PR2', 'printer');
INSERT INTO product (maker, model, type)
VALUES ('Huawei', 'PR3', 'printer');

INSERT INTO product (maker, model, type)
VALUES ('Samsung', 'LT1', 'laptop');
INSERT INTO product (maker, model, type)
VALUES ('Redmi', 'LT3', 'laptop');
INSERT INTO product (maker, model, type)
VALUES ('Mac', 'LT2', 'laptop');
INSERT INTO product (maker, model, type)
VALUES ('Mac', 'LT4', 'laptop');

INSERT INTO pc (code, model, speed, ram, hd, cd, price)
VALUES (1, 'PC1', 2660, 6000, 128, '4x', 1000);
INSERT INTO pc (code, model, speed, ram, hd, cd, price)
VALUES (2, 'PC2', 2440, 4000, 64, '4x', 800);
INSERT INTO pc (code, model, speed, ram, hd, cd, price)
VALUES (3, 'PC3', 2880, 8000, 256, '4x', 1300);
INSERT INTO pc (code, model, speed, ram, hd, cd, price)
VALUES (4, 'PC4', 880, 1400, 32, '4x', 400);
INSERT INTO pc (code, model, speed, ram, hd, cd, price)
VALUES (5, 'PC5', 880, 1400, 32, '12x', 550);
INSERT INTO pc (code, model, speed, ram, hd, cd, price)
VALUES (6, 'PC6', 1440, 1800, 64, '24x', 930);
INSERT INTO pc (code, model, speed, ram, hd, cd, price)
VALUES (7, 'PC7', 1440, 1400, 64, '24x', 870);

INSERT INTO printer (code, model, color, type, price)
VALUES (1, 'PR1', 'n', 'Laser', 500);
INSERT INTO printer (code, model, color, type, price)
VALUES (2, 'PR2', 'y', 'Jet', 700);
INSERT INTO printer (code, model, color, type, price)
VALUES (3, 'PR3', 'y', 'Matrix', 900);

INSERT INTO laptop (code, model, speed, ram, hd, price, screen)
VALUES (1, 'LT1', 1440, 6000, 64, 400, 5);
INSERT INTO laptop (code, model, speed, ram, hd, price, screen)
VALUES (2, 'LT2', 840, 4000, 32, 200, 3);
INSERT INTO laptop (code, model, speed, ram, hd, price, screen)
VALUES (3, 'LT3', 1640, 8000, 128, 800, 6);
INSERT INTO laptop (code, model, speed, ram, hd, price, screen)
VALUES (4, 'LT4', 1640, 12000, 128, 1400, 12);
