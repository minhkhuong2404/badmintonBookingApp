INSERT INTO `city` (`city_id`) VALUES ('1');
INSERT INTO `city` (`city_id`) VALUES ('A');
INSERT INTO `city` (`city_id`) VALUES ('B');
INSERT INTO `city` (`city_id`) VALUES ('C');
INSERT INTO `city` (`city_id`) VALUES ('city1');


INSERT INTO `center` (`center_id`,`city_id`) VALUES ('2','1');
INSERT INTO `center` (`center_id`,`city_id`) VALUES ('A1','A');
INSERT INTO `center` (`center_id`,`city_id`) VALUES ('B1','B');
INSERT INTO `center` (`center_id`,`city_id`) VALUES ('B2','B');
INSERT INTO `center` (`center_id`,`city_id`) VALUES ('C1','C');
INSERT INTO `center` (`center_id`,`city_id`) VALUES ('C2','C');
INSERT INTO `center` (`center_id`,`city_id`) VALUES ('C3','C');
INSERT INTO `center` (`center_id`,`city_id`) VALUES ('center1','city1');


INSERT INTO `court` (`court_id`,`city_id`,`center_id`) VALUES ('A','1','2');
INSERT INTO `court` (`court_id`,`city_id`,`center_id`) VALUES ('A1C','A','A1');
INSERT INTO `court` (`court_id`,`city_id`,`center_id`) VALUES ('A1C1','A','A1');
INSERT INTO `court` (`court_id`,`city_id`,`center_id`) VALUES ('A1C2','A','A1');
INSERT INTO `court` (`court_id`,`city_id`,`center_id`) VALUES ('B10','B','B1');
INSERT INTO `court` (`court_id`,`city_id`,`center_id`) VALUES ('B12','B','B1');
INSERT INTO `court` (`court_id`,`city_id`,`center_id`) VALUES ('B13','B','B1');
INSERT INTO `court` (`court_id`,`city_id`,`center_id`) VALUES ('C1C','C','C1');
INSERT INTO `court` (`court_id`,`city_id`,`center_id`) VALUES ('C1C2','C','C1');
INSERT INTO `court` (`court_id`,`city_id`,`center_id`) VALUES ('C3C','C','C3');
INSERT INTO `court` (`court_id`,`city_id`,`center_id`) VALUES ('court1','city1','center1');


INSERT INTO `booking` (`booking_id`,`timestamp`,`date`,`startTime`,`endTime`,`city_id`,`center_id`,`court_id`,`player_id`,`status`) VALUES (1,'2020-04-22 18:23:18','2020-05-10','10:30:00','11:30:00','A','A1','A1C','player1',0);
INSERT INTO `booking` (`booking_id`,`timestamp`,`date`,`startTime`,`endTime`,`city_id`,`center_id`,`court_id`,`player_id`,`status`) VALUES (2,'2020-04-22 19:09:13','2020-05-10','13:31:00','14:31:00','C','C1','C1C','player4',0);
INSERT INTO `booking` (`booking_id`,`timestamp`,`date`,`startTime`,`endTime`,`city_id`,`center_id`,`court_id`,`player_id`,`status`) VALUES (3,'2020-04-22 19:09:13','2020-05-10','13:31:00','14:31:00','C','C1','C1C2','player4',0);
INSERT INTO `booking` (`booking_id`,`timestamp`,`date`,`startTime`,`endTime`,`city_id`,`center_id`,`court_id`,`player_id`,`status`) VALUES (8,'2020-04-22 19:08:48','2020-05-10','12:31:00','13:31:00','A','A1','A1C','player3',0);
INSERT INTO `booking` (`booking_id`,`timestamp`,`date`,`startTime`,`endTime`,`city_id`,`center_id`,`court_id`,`player_id`,`status`) VALUES (9,'2020-04-22 19:09:13','2020-05-10','13:31:00','14:31:00','A','A1','A1C1','player1',0);
INSERT INTO `booking` (`booking_id`,`timestamp`,`date`,`startTime`,`endTime`,`city_id`,`center_id`,`court_id`,`player_id`,`status`) VALUES (10,'2020-04-22 19:09:13','2020-05-10','08:31:00','09:31:00','A','A1','A1C2','player1',0);
INSERT INTO `booking` (`booking_id`,`timestamp`,`date`,`startTime`,`endTime`,`city_id`,`center_id`,`court_id`,`player_id`,`status`) VALUES (11,'2020-04-22 19:09:13','2020-06-10','08:31:00','09:31:00','A','A1','A1C','player2',0);
