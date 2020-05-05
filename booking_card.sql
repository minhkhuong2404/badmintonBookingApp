drop table if exists booking_card;
create table booking_card(
	card_id int auto_increment primary key,
    player_id varchar(50),
	remain_booking int ,
    time_bought datetime,
    expire_date datetime
    );
ALTER TABLE booking_card
  ADD card varchar(40) NOT NULL
    AFTER card_id;

DROP PROCEDURE IF EXISTS CreateBooking;
DELIMITER //
CREATE PROCEDURE CreateBooking(
in booking_id varchar(50),
in pdate datetime,
in cardId int,
in playerId varchar(50))
BEGIN

DECLARE EXIT HANDLER FOR SQLEXCEPTION
	BEGIN
	  GET STACKED DIAGNOSTICS CONDITION 1 
		@p1 = RETURNED_SQLSTATE, @p2 = MESSAGE_TEXT;
	  SELECT @p1, @p2;
	  ROLLBACK;
	END;
START TRANSACTION;
UPDATE booking_card
SET
	remain_booking = remain_booking - 1 
where
	cardId = card_id;
END //
DELIMITER ;

DROP PROCEDURE IF EXISTS cancelBooking;
DELIMITER //
CREATE PROCEDURE cancelBooking()
BEGIN
DECLARE EXIT HANDLER FOR SQLEXCEPTION
	BEGIN
	  GET STACKED DIAGNOSTICS CONDITION 1 
		@p1 = RETURNED_SQLSTATE, @p2 = MESSAGE_TEXT;
	  SELECT @p1, @p2;
	  ROLLBACK;
	END;
START TRANSACTION;
if exists (select booking_id from booking_card where booking_id > 0)
then
UPDATE booking_card
SET
	remain_booking = remain_booking + 1 
where
	cardId = card_id;
    end if;
END //
DELIMITER ;

INSERT INTO `booking_card` (`card_id` , `player_id` , `remain_booking` , `time_bought`, `expire_dead` ) VALUES ( '1', 'player1 ' , '8 ' , ' 2020-05-05', '2020-06-04' );  
INSERT INTO `booking_card` (`card_id` , `player_id` , `remain_booking` , `time_bought`, `expire_dead` ) VALUES ( '2', 'player2 ' , '9 ' , ' 2020-05-03', ' 2020-06-02'  );  
INSERT INTO `booking_card` (`card_id` , `player_id` , `remain_booking` , `time_bought`, `expire_dead` ) VALUES ( '3', 'player3 ' , '3 ' , ' 2020-05-02', '2020-06-01'  );  
INSERT INTO `booking_card` (`card_id` , `player_id` , `remain_booking` , `time_bought`, `expire_dead` ) VALUES ( '4' ,' player4 ' , '4 ' , ' 2020-05-01',  ' 2020-06-31' );  
INSERT INTO `booking_card` (`card_id` , `player_id` , `remain_booking` , `time_bought`, `expire_dead` ) VALUES ( '5' ,'player5 ' , '1 ' , ' 2020-04-30' , ' 2020-05-30' );  
INSERT INTO `booking_card` (`card_id` , `player_id` , `remain_booking` , `time_bought`, `expire_dead` ) VALUES ('6',  'player6 ' , '5 ' , ' 2020-04-22',  ' 2020-05-22' );  
INSERT INTO `booking_card` (`card_id` , `player_id` , `remain_booking` , `time_bought`, `expire_dead` ) VALUES ( '7','player7 ' , '6 ' , ' 2020-04-24', ' 2020-05-24' );  
INSERT INTO `booking_card` (`card_id` , `player_id` , `remain_booking` , `time_bought`, `expire_dead` ) VALUES ( '8','player8 ' , '9 ' , ' 2020-04-25' , ' 2020-05-25' );  
INSERT INTO `booking_card` (`card_id` , `player_id` , `remain_booking` , `time_bought`, `expire_dead` ) VALUES ( '9','player9 ' , '10 ' , ' 2020-04-28', ' 2020-05-28'   );  
INSERT INTO `booking_card` (`card_id` , `player_id` , `remain_booking` , `time_bought`, `expire_dead` ) VALUES ( '10','player10 ' , '2 ' , ' 2020-04-27' , ' 2020-05-27');


