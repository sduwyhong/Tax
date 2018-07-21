create database tax;
/*--the beginning of creating tables--*/
create table tax_user(
	id varchar(32) not null primary key,
	username varchar(20) not null,
	password varchar(32) not null,
	email varchar(100) not null,
	telephone varchar(11) not null,
	score int unsigned default 0,
	last_visit date not null,
	pro_list varchar(100) default '',
	image varchar(255) not null,
	privilege tinyInt unsigned default 0
);
create table tax_pro(
	id int unsigned not null primary key auto_increment,
	name varchar(100) not null
);
insert into tax_pro(name) values('发票系统故障及操作指导');
insert into tax_pro(name) values('发票领购');
insert into tax_pro(name) values('发票代开');
insert into tax_pro(name) values('发票开具');
insert into tax_pro(name) values('红字增值税发票开具');
insert into tax_pro(name) values('申报有误能否作废');
insert into tax_pro(name) values('一般纳税人资格');
insert into tax_pro(name) values('辅导期一般纳税人');
insert into tax_pro(name) values('专用发票认证抵扣');
insert into tax_pro(name) values('小规模纳税人增值税起征点是否有调整');
insert into tax_pro(name) values('认定为一般纳税人之后是否可以转为小规模纳税人');
insert into tax_pro(name) values('小规模纳税人自行开具的增值税专用发票如何填写申报表');
insert into tax_pro(name) values('注销登记');
insert into tax_pro(name) values('发票检查');
insert into tax_pro(name) values('专用发票丢失');
insert into tax_pro(name) values('征收率');
insert into tax_pro(name) values('非国税业务');
insert into tax_pro(name) values('信用等级');
insert into tax_pro(name) values('营改增');
insert into tax_pro(name) values('金税管理');
insert into tax_pro(name) values('进出口税收');
insert into tax_pro(name) values('征收管理');
insert into tax_pro(name) values('发票保管');
create table tax_question(
	id int unsigned not null primary key auto_increment,
	author_id varchar(32) not null,
	type varchar(100) not null,
	prize int unsigned not null default 0,
	title varchar(100) not null,
	content varchar(1000) not null,
	publish_date date not null,
	click int unsigned default 0,
	likes int unsigned default 0,
	favourite int unsigned default 0,
	status int unsigned default 0,
	constraint fk_author_id_question foreign key (author_id) references tax_user(id),
	fulltext(content)
)engine=MyISAM;
create table tax_invitation(
	id int unsigned not null primary key auto_increment,
	question_id int unsigned not null,
	user_id varchar(32) not null,
 	constraint fk_user_id_invitation foreign key (user_id) references tax_user(id)
);
create table tax_answer(
	id int unsigned not null primary key auto_increment,
	question_id int unsigned not null,
	author_id varchar(32) not null,
	publish_date date not null,
	content varchar(1000) not null,
	favourite int unsigned default 0,
	likes int unsigned default 0,
	status tinyint unsigned default 0,
 	constraint fk_author_id_answer foreign key (author_id) references tax_user(id)
);
create table tax_share(
	id int unsigned not null primary key auto_increment,
	author_id varchar(32) not null,
	title varchar(100) not null,
	content varchar(1000) not null,
	publish_date date not null,
	click int unsigned default 0,
	likes int unsigned default 0,
	favourite int unsigned default 0,
	constraint fk_author_id_share foreign key (author_id) references tax_user(id)
);
create table tax_expert(
	id int unsigned not null primary key auto_increment,
	author_id varchar(32) not null,
	title varchar(100) not null,
	content varchar(1000) not null,
	publish_date date not null,
	click int unsigned default 0,
	likes int unsigned default 0,
	favourite int unsigned default 0,
	constraint fk_author_id_expert foreign key (author_id) references tax_user(id)
);
create table tax_favourite (
	id int unsigned not null primary key auto_increment,
	question_id int unsigned not null,
	user_id varchar(32) not null,
 	constraint fk_user_id_favourite foreign key (user_id) references tax_user(id)
);
create table tax_favourite_answer (
	id int unsigned not null primary key auto_increment,
	question_id int unsigned not null,
	answer_id int unsigned not null,
	user_id varchar(32) not null,
 	constraint fk_user_id_favourite_answer foreign key (user_id) references tax_user(id)
);
create table tax_message (
	id int unsigned not null primary key auto_increment,
	sender_id varchar(32) not null,
	receiver_id varchar(32) not null,
	content varchar(1000) not null,
	status tinyInt unsigned default 0,
	constraint fk_sender_id foreign key (sender_id) references tax_user(id),
	constraint fk_receiver_id foreign key (receiver_id) references tax_user(id)
);
create table tax_message_reply (
	id int unsigned not null primary key auto_increment,
	message_id int unsigned not null,
	content varchar(1000) not null,
	constraint fk_message_id foreign key (message_id) references tax_message(id)
);
create table tax_user_pro (
	id int unsigned not null primary key auto_increment,
	pro_id int unsigned not null,
	user_id varchar(32) not null
);
create table tax_question_pro (
	id int unsigned primary key auto_increment,
	question_id int unsigned not null,
	pro_id int unsigned not null
);
/*--the end of creating tables--*/

/*--the beginning of creating views--*/
create view questionAuthorView as
	select q.*,u.username,u.email,u.telephone,u.score,u.last_visit,u.pro_list,u.image
	from tax_question q,tax_user u
	where q.author_id = u.id;

create view invitationView as
	select i.question_id,q.title,i.user_id,u.username,u.image
	from tax_question q,tax_user u,tax_invitation i
	where i.question_id = q.id and i.user_id = u.id;
/*--the end of creating views--*/




