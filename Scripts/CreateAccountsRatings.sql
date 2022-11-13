-- e mai mult o tabela pentru a putea sterge usor posibilele rating-uri (cont sters, banned) 
create table accounts_ratings(
	user_id int,
	rating_id int,
	primary key(user_id, rating_id),
	foreign key(user_id) references users(id),
	foreign key(rating_id) references ratings(rating_id)
);
