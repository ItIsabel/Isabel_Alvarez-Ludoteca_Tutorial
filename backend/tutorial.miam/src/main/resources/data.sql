INSERT INTO category(name) VALUES ('Eurogames');
INSERT INTO category(name) VALUES ('Ameritrash');
INSERT INTO category(name) VALUES ('Familiar');

INSERT INTO author(name, nationality) VALUES ('Alan R. Moon', 'US');
INSERT INTO author(name, nationality) VALUES ('Vital Lacerda', 'PT');
INSERT INTO author(name, nationality) VALUES ('Simone Luciani', 'IT');
INSERT INTO author(name, nationality) VALUES ('Perepau Llistosella', 'ES');
INSERT INTO author(name, nationality) VALUES ('Michael Kiesling', 'DE');
INSERT INTO author(name, nationality) VALUES ('Phil Walker-Harding', 'US');

INSERT INTO game(title, age, category_id, author_id) VALUES ('On Mars', '14', 1, 2);
INSERT INTO game(title, age, category_id, author_id) VALUES ('Aventureros al tren', '8', 3, 1);
INSERT INTO game(title, age, category_id, author_id) VALUES ('1920: Wall Street', '12', 1, 4);
INSERT INTO game(title, age, category_id, author_id) VALUES ('Barrage', '14', 1, 3);
INSERT INTO game(title, age, category_id, author_id) VALUES ('Los viajes de Marco Polo', '12', 1, 3);
INSERT INTO game(title, age, category_id, author_id) VALUES ('Azul', '8', 3, 5);

INSERT INTO customer(name) VALUES ('Isabel Alvarez');
INSERT INTO customer(name) VALUES ('Katherine Johnson');
INSERT INTO customer(name) VALUES ('Ada Lovelace');

INSERT INTO loan(game_id, customer_id, start_date, finish_date) VALUES (1, 1, '2025-03-15', '2025-04-01');
INSERT INTO loan(game_id, customer_id, start_date, finish_date) VALUES (2, 1, '2025-03-31', '2025-04-15');
INSERT INTO loan(game_id, customer_id, start_date, finish_date) VALUES (3, 2, '2025-05-01', '2025-05-15');
INSERT INTO loan(game_id, customer_id, start_date, finish_date) VALUES (2, 1, '2025-06-01', '2025-06-15');
INSERT INTO loan(game_id, customer_id, start_date, finish_date) VALUES (3, 2, '2025-06-01', '2025-06-15');
INSERT INTO loan(game_id, customer_id, start_date, finish_date) VALUES (2, 1, '2025-07-01', '2025-07-15');
