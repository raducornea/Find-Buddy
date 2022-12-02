-- se salveaza link-urile pozelor
create table profile_pictures(
	user_id int not null,
	link varchar(100) not null unique,
	primary key(user_id),
	foreign key(user_id) references users(id)
);
