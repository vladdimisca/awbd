INSERT INTO users (id, email, password) VALUES (1, 'random@gmail.com', 'pass-hash');
INSERT INTO users (id, email, password) VALUES (2, 'abc@gmail.com', 'hash');

INSERT INTO cars (id, type, license_plate, created_at, fk_user) VALUES (1, 'VAN', 'random', '2022-04-24 10:30:28.181601', 1);
INSERT INTO cars (id, type, license_plate, created_at, fk_user) VALUES (2, 'REGULAR', 'abc', '2022-04-24 11:35:33.385621', 1);
INSERT INTO cars (id, type, license_plate, created_at, fk_user) VALUES (3, 'MINIBUS', 'bus', '2022-05-23 12:40:33.383331', 2);