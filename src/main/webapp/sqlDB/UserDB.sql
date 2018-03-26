drop table USERLIST;

create table USERLIST(
	u_id varchar2(20) primary key,
	u_pw varchar2(50) not null,
	u_name varchar2(50),
	u_phone varchar2(50) not null,
	u_email varchar2(100) not null,
	u_address varchar2(150)
	);

insert into USERLIST values('kpc', '1', '김용환', '890225', '01090790089',  '2a5@naver.com', '서울시 마포구', '하나은행', '01090790089607');

rollback;