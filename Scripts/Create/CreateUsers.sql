create table users(
	id int auto_increment,
	username varchar(30) not null unique,
	password varchar(100) not null,
	email varchar(150) not null unique,
	first_name varchar(35) not null,
	last_name varchar(35) not null,
	primary key(id)
);