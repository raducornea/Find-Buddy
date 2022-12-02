/*
 * pe baza tabelei se pot vedea rating-urile pe care le-am primit (public) dar si dat (privat)
 * 
 * de asemenea, parent_rating semnifica comentariul parinte in caz de e arborescenta
 */
-- reviewer_id is just the id of the user who reviewed 
create table ratings(
	rating_id int not null,
	review varchar(300) not null,
	score double not null,
	reviewed_id int not null,
	likes int not null,
	dislikes int not null,
	parent_rating int,
	primary key(rating_id),
	foreign key(reviewed_id) references users(id)
);