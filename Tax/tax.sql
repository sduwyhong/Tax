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
create table tax_message (
	id int unsigned not null primary key auto_increment,
	sender_id varchar(32) not null,
	receiver_id varchar(32) not null,
	content varchar(1000) not null,
	status tinyInt unsigned default 0,
	constraint fk_sender_id foreign key (sender_id) references tax_user(id),
	constraint fk_receiver_id foreign key (receiver_id) references tax_user(id)
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




