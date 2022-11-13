-- cand utilizatorul vrea sa-si modifice parola
update users set password = "QWERTYUIOPLKJHGFDSA" where id = 1;

-- sau mail-ul
update users set mail = "mariangeorgian@yahoo.com" where id = 1;

-- prenumele
update users set first_name = "Mariann" where id = 1;

-- numele
update users set last_name = "Georgia" where id = 1;
