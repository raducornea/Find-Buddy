-- trebuie avut in vedere cazul in care utilizatorul incearca sa puna aceeasi preferinta la like SI dislike 
-- de aceea am pus campul like in tabela intermediara
create table preferences(
	preference_id int auto_increment,
	preference varchar(30) not null unique,
	primary key(preference_id)
);