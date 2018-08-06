INSERT INTO country (name) VALUES ('Belarus');
INSERT INTO country (name) VALUES ('USA');
INSERT INTO country (name) VALUES ('Britain');

INSERT INTO hotel (name, phone_number, stars, id_country) VALUES ('Plazza', '111222333', 5, 2);
INSERT INTO hotel (name, phone_number, stars, id_country) VALUES ('Renaissance', '123123123', 3, 1);
INSERT INTO hotel (name, phone_number, stars, id_country) VALUES ('The Shelbourne Hotel', '432143211', 5, 3);

INSERT INTO tour (description, cost, date, duration, id_country, id_hotel,tour_type)
VALUES ('resort', 1000, parsedatetime('12.12.2012','dd.mm.yyyy'), 7, 1, 1,'CRUISE');
INSERT INTO tour (description, cost, date, duration, id_country, id_hotel,tour_type)
VALUES ('Healing tour, best tour ever', 123, parsedatetime('12.12.2012','dd.mm.yyyy'), 12, 2, 1,'EXOTIC');

INSERT INTO `user` (login, password,role) VALUES ('Aleh', 'Savik','ADMIN');
INSERT INTO `user` (login, password,role) VALUES ('Petr', 'Ivanov','MEMBER');

INSERT INTO review (id_tour, id_user, content) VALUES (1, 1, 'Best tour ever!!!');
INSERT INTO review (id_tour, id_user, content) VALUES (1, 2, 'I like it');



