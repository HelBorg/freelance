/*Мануал
    1. Устанавливаем Postgres
    2. Создаем пользователя и бд
    3. Изменяем application.properties под ваши настройки (username, password, url(только имя, как правило))
    4. Вставляем таблицы/у в бд, ниже скрипт
     profit
     postman или swagger для тестирования коннекта
     */

DROP TABLE IF EXISTS Person;
CREATE TABLE Person(id serial PRIMARY KEY, name VARCHAR(100));

Insert into Person(name) values('Dima');
