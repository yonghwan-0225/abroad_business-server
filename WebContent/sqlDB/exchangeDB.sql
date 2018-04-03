drop table EXCHANGE;

create table EXCHANGE(
	t_id varchar2(15) primary key,
	u_id varchar2(20) not null,
	t_time varchar2(50) not null,
	t_type varchar2(50) not null,
	t_status varchar2(50) not null,
	t_amount varchar2(50) not null,
	t_exchange_rate varchar2(150) not null,
	t_total varchar2(100) not null,
	CONSTRAINT u_id_fk FOREIGN KEY(u_id)
	references USERLIST(u_id)
	);

insert into EXCHANGE values(orderId.nextval, 'kpc', '2018/03/15/10:59:25', 'true','1', '200', '1200', '5555');

create sequence orderId start with 1 increment by 1;

select t_id from EXCHANGE where u_id='kpc111' and t_time='2018/03/15/10:59:25';