-- similar to Blocks table
create table friends(
	user_id1 int not null,
	user_id2 int not null,
	primary key(user_id1, user_id2),
	foreign key(user_id1) references users(id),
	constraint friend_self_ck check (user_id1 != user_id2)
);
