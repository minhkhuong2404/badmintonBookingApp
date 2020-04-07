DROP DATABASE IF EXISTS booking_app;
CREATE DATABASE booking_app;
USE booking_app;

DROP TABLE IF EXISTS city;
CREATE TABLE city(
  city_id varchar(50) NOT NULL PRIMARY KEY
);

DROP TABLE IF EXISTS center;
CREATE TABLE center(
  center_id varchar(50) NOT NULL PRIMARY KEY,
  city_id varchar(50) NOT NULL,
  CONSTRAINT city_fk
	 FOREIGN KEY (city_id) REFERENCES city (city_id) ON DELETE CASCADE
);

DROP TABLE IF EXISTS court;
CREATE TABLE court(
  court_id varchar(50) NOT NULL PRIMARY KEY,
  city_id varchar(50) NOT NULL,
  center_id varchar(50) NOT NULL,
  CONSTRAINT court_center_fk
	 FOREIGN KEY (center_id) REFERENCES center (center_id) ON DELETE CASCADE,
  CONSTRAINT court_city_fk   
     FOREIGN KEY (city_id) REFERENCES city (city_id) ON DELETE CASCADE
     
);

DROP TABLE IF EXISTS staff;
CREATE TABLE staff(
  staff_id varchar(50) NOT NULl PRIMARY KEY,
  city_id varchar(50) NOT NULL,
  center_id varchar(50) NOT NULL,
  CONSTRAINT staff_center_fk
	 FOREIGN KEY (center_id) REFERENCES center (center_id) ON DELETE CASCADE,
  CONSTRAINT staff_city_fk
     FOREIGN KEY (city_id) REFERENCES city (city_id) ON DELETE CASCADE
);

DROP TABLE IF EXISTS player;
CREATE TABLE player(
  player_id varchar(50) NOT NULL PRIMARY KEY
);

DROP TABLE IF EXISTS booking;
CREATE TABLE booking(
  booking_id varchar(50) NOT NULL PRIMARY KEY,
  timestamp datetime NOT NULL,
  date date NOT NULL,
  startTime TIME NOT NULL,
  endTime TIME NOT NULL,
  city_id varchar(50) NOT NULL,
  center_id varchar(50) NOT NULL,
  court_id varchar(50),
  player_id varchar(50),
  status int default 0,
  CONSTRAINT court_fk
     FOREIGN KEY (court_id) REFERENCES court (court_id) ON DELETE CASCADE,
  CONSTRAINT player_fk
     FOREIGN KEY (player_id) REFERENCES player (player_id) ON DELETE CASCADE	 
);