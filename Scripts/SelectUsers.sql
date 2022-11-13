-- pentru a lua fix toti utilizatorii si a face prelucrari masive mai departe
select * from users;

-- pentru prelucrari pe acel user
select * from users where id = 3;

-- pentru cauteri in functie de nume - o sa fie cel mai probabil in stilul de jos, in cazul numelui 
select * from users where username like '%sla%';