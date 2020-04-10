DROP PROCEDURE IF EXISTS getAllCityCenterCourts;
DELIMITER //
CREATE PROCEDURE getAllCityCenterCourts(in cityId varchar(50), in centerId varchar(50))
BEGIN
-- select court based on city and center -- 
select * from  court where cityId = city_id and centerId = center_id;
END //
DELIMITER ;

call getAllCityCenterCourts('HCM','HCM_quan1');