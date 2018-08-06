CREATE TABLE `user`
(
  id       BIGINT      NOT NULL AUTO_INCREMENT
  CONSTRAINT "User_pkey"
  PRIMARY KEY ,
  login    VARCHAR(256) NOT NULL,
  password VARCHAR(256) NOT NULL,
  version INTEGER,
role varchar(8) not null
);

CREATE SEQUENCE client_seq START WITH 1 INCREMENT BY 1;

CREATE TABLE country
(
  id   BIGINT      NOT NULL AUTO_INCREMENT
    CONSTRAINT "Country_pkey"
    PRIMARY KEY,
  name VARCHAR(256) NOT NULL,
  version INTEGER default 0
);

CREATE TABLE hotel
(
  id           BIGINT      NOT NULL AUTO_INCREMENT
    CONSTRAINT "Hotel_pkey"
    PRIMARY KEY,
  name         VARCHAR(256) NOT NULL,
  phone_number VARCHAR(256),
  stars        INTEGER,
  version INTEGER default 0,
  id_country   BIGINT      NOT NULL
    CONSTRAINT id_country
    REFERENCES country on delete cascade on update cascade
);

CREATE INDEX fki_id_country
  ON hotel (id_country);

CREATE TABLE tour
(
  id          BIGINT NOT NULL AUTO_INCREMENT
    CONSTRAINT "Tour_pkey"
    PRIMARY KEY,
  description TEXT,
  cost        DOUBLE  NOT NULL,
  date        TIMESTAMP(6),
  duration    INTEGER NOT NULL,
  tour_type varchar(256),
  version INTEGER default 0,
  id_country  BIGINT null
    CONSTRAINT tour_country
    REFERENCES country on delete cascade  on update cascade  ,
  id_hotel    BIGINT null
    CONSTRAINT tour_hotel
    REFERENCES hotel on delete cascade on update cascade ,
  image_uri   VARCHAR(256)

);


CREATE INDEX fki_tour_country
  ON tour (id_country);

CREATE INDEX fki_tour_hotel
  ON tour (id_hotel);

CREATE TABLE review
(
  id      BIGINT NOT NULL
  AUTO_INCREMENT
    CONSTRAINT "Review_pkey"
    PRIMARY KEY,
    version INTEGER default 0,
  id_tour BIGINT null
    CONSTRAINT review_tour
    REFERENCES tour on delete cascade  on update cascade ,
  id_user BIGINT null
    CONSTRAINT review_user
    REFERENCES `user` on delete cascade on update cascade ,
  content TEXT    NOT NULL
);

CREATE INDEX fki_review_tour
  ON review (id_tour);

CREATE INDEX fki_review_user
  ON review (id_user);
