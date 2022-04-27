INSERT INTO user_details (id, first_name, last_name, phone_number, sex) VALUES (1, 'aaa', 'bbb', '0755221133', 'MALE');
INSERT INTO user_details (id, first_name, last_name, phone_number, sex) VALUES (2, 'ccc', 'ddd', '0772232255', 'FEMALE');

INSERT INTO users (id, email, password, fk_user_details) VALUES (1, 'random@gmail.com', 'pass-hash', 1);
INSERT INTO users (id, email, password, fk_user_details) VALUES (2, 'abc@gmail.com', 'hash', 2);

INSERT INTO cars (id, type, license_plate, created_at, fk_user) VALUES (1, 'VAN', 'random', '2022-04-24 10:30:28.181601', 1);
INSERT INTO cars (id, type, license_plate, created_at, fk_user) VALUES (2, 'REGULAR', 'abc', '2022-04-24 11:35:33.385621', 1);
INSERT INTO cars (id, type, license_plate, created_at, fk_user) VALUES (3, 'MINIBUS', 'bus', '2022-05-23 12:40:33.383331', 2);

INSERT INTO jobs (id, type, price, duration_minutes, car_type, number_of_employees) VALUES (1, 'INTERIOR', 50.0, 25, 'VAN', 1);
INSERT INTO jobs (id, type, price, duration_minutes, car_type, number_of_employees) VALUES (2, 'EXTERIOR', 25.0, 15, 'REGULAR', 1);
INSERT INTO jobs (id, type, price, duration_minutes, car_type, number_of_employees) VALUES (3, 'INTERIOR_AND_EXTERIOR', 70.0, 40, 'VAN', 2);

INSERT INTO employees (id, first_name, last_name, email, phone_number, salary, hire_date) VALUES (1, 'abc', 'bcd', 'random@yahoo.com', '0755221171', 2000.0, '2021-05-14');
INSERT INTO employees (id, first_name, last_name, email, phone_number, salary, hire_date) VALUES (2, 'first', 'last', 'random@abc.com', '0765332211', 2000.0, '2021-06-15');

INSERT INTO appointments (id, start_time, fk_job, fk_car, fk_user) VALUES (1, '2022-06-22 15:08:25.152701', 1, 1, 1);
INSERT INTO appointments (id, start_time, fk_job, fk_car, fk_user) VALUES (2, '2022-07-21 13:13:30.131201', 2, 2, 2);
INSERT INTO appointments (id, start_time, fk_job, fk_car, fk_user) VALUES (3, '2022-01-18 07:22:34.182601', 1, 3, 2);
INSERT INTO appointments (id, start_time, fk_job, fk_car, fk_user) VALUES (4, '2022-08-23 10:33:12.142201', 1, 1, 1);
INSERT INTO appointments (id, start_time, fk_job, fk_car, fk_user) VALUES (5, '2022-09-25 09:53:22.172621', 2, 2, 1);

