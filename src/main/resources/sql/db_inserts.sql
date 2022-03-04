--LODGERS
INSERT INTO lodgers (first_name, last_name, phone_number) VALUES
('Victor','Lukyanchik','2345673'),
('Igor','Voytenko','2999967'),
('Marina','Voytsehova','9292278'),
('Lionel','Messi','6344224'),
('Martin','Basile','3218715'),
('Andrey','Pedigryu','2567849'),
('Renatu','Sanchesh','9074532'),
('Andrey', 'Avdeev', '2764431');

--ROOMS
INSERT INTO rooms (number, cost, capacity, stars) VALUES
(101, 20.00, 1, 3),
(102, 25.00, 1, 4),
(103, 35.00, 1, 5),
(201, 30.00, 2, 3),
(202, 45.00, 3, 5),
(303, 23.00, 2, 3);

--RESERVATIONS
INSERT INTO reservations (start_date, end_date, lodger_id, room_id, reserved) VALUES
('05.02.2022', '10.02.2022', 1, 2, false),
('03.02.2022', '07.02.2022', 2, 1, false),
('01.02.2022', '15.02.2022', 3, 4, false),
('07.02.2022', '25.02.2022', 4, 5, false),
('17.03.2022', '23.03.2022', 5, 5, false),
('27.02.2022', '03.03.2022', 6, 1, false);

--SERVICE
INSERT INTO services (name, cost) VALUES ('Clean', 5.00),
('Wash clothes', 3.50),
('Wash dishes', 2.50),
('Make breakfast', 5.00),
('Make diner', 5.00);

--SERVICE-ORDER
INSERT INTO service_orders (date, lodger_id, service_id) VALUES
('03.03.2022', 1, 4),
('05.02.2022', 2, 1),
('13.02.2022', 3, 4),
('07.02.2022', 3, 4),
('09.02.2022', 3, 4),
('18.03.2022', 4, 5),
('01.03.2022', 6, 2);
