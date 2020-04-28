DROP table if exists holiday;
create table holiday(
	id int auto_increment not null primary key,
	city_id varchar(50) not null,
    holiday_day int not null,
    holiday_month int not null,
    constraint city_holiday
     foreign key (city_id) references city(city_id) on delete cascade
)