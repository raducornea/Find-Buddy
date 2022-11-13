-- tinyint(1) e boolean in situatia asta
create table accounts_preferences(
	user_id int,
	preference_id int,
	likes tinyint(1),
	primary key(user_id, preference_id),
	foreign key(user_id) references users(id),
	foreign key(preference_id) references preferences(preference_id)
);
