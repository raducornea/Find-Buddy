/*
 * Block Scenario:
 * A. User1 blocks User2, but User2 doesn't block User1
 * 	- user1 can see their block list and unblock user2
 *  - user2 can block user1, but they won't be able to see user1's profile
 * B. They block each other
 *  - they can only unblock them, not see the other's profile
 * THEY MUST NOT BLOCK THEIR OWN SELVES -> WEIRD BEHAVIOR 
*/
create table blocks(
	user_id1 int not null,
	user_id2 int not null,
	primary key(user_id1, user_id2),
	foreign key(user_id1) references users(id),
	constraint block_self_ck check (user_id1 != user_id2)
);
