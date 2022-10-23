DELETE
FROM user_roles;
DELETE
FROM users;
DELETE
FROM meals;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password)
VALUES ('User', 'user@yandex.ru', 'password'),
       ('Admin', 'admin@gmail.com', 'admin'),
       ('Guest', 'guest@gmail.com', 'guest');

INSERT INTO user_roles (role, user_id)
VALUES ('USER', 100000),
       ('ADMIN', 100001);

INSERT INTO meals (date_time, description, calories, user_id)
VALUES ('2022-10-21 10:00:00.000000', 'Завтрак', 500, 100000),
       ('2022-10-21 13:00:00.000000', 'Обед', 1000, 100000),
       ('2022-10-21 20:00:00.000000', 'Ужин', 500, 100000),
       ('2022-10-22 00:00:00.000000', 'Еда на граничное значение', 500, 100000),
       ('2022-10-22 10:00:00.000000', 'Завтрак', 500, 100000),
       ('2022-10-22 13:00:00.000000', 'Обед', 1000, 100000),
       ('2022-10-22 20:00:00.000000', 'Ужин', 400, 100000),
       ('2022-10-21 10:00:00.000000', 'ADMIN Завтрак', 500, 100001),
       ('2022-10-21 13:00:00.000000', 'ADMIN Обед', 1000, 100001),
       ('2022-10-21 20:00:00.000000', 'ADMIN Ужин', 500, 100001);
