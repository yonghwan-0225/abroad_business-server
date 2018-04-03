drop table TRAVEL;

create table TRAVEL(
	u_id varchar2(20) not null,
	date_depart varchar2(50) not null,
	date_arrive varchar2(50) not null,
	destination varchar2(50) not null,
	CONSTRAINT u_id_fk2 FOREIGN KEY(u_id)
	references USERLIST(u_id)
	);