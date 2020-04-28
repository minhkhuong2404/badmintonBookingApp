DROP TABLE IF EXISTS OpenHour;
CREATE TABLE OpenHour(
  id int auto_increment primary key,
  center varchar(50),
  open_hour time,
  close_hour time,
  date_of_week varchar(50),
  CONSTRAINT center_fk
	 FOREIGN KEY (center) REFERENCES center (center_id) ON DELETE CASCADE
);