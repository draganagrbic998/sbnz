------------------------------AUTHORITIES------------------------------
insert into authority_table (name) values ('ADMIN');
insert into authority_table (name) values ('SLUZBENIK');
insert into authority_table (name) values ('KLIJENT');

------------------------------USERS------------------------------
insert into user_table (email, password, first_name, last_name, enabled)
values ('admin@gmail.com', '$2a$10$aL2cRpbMvSsvTcIGxUoauO4RMefDmYtEEARsmKJpwJ7T585HfBsra', 'Pera', 'Peric', true);
insert into user_table (email, password, first_name, last_name, enabled)
values ('sluzbenik@gmail.com', '$2a$10$aL2cRpbMvSsvTcIGxUoauO4RMefDmYtEEARsmKJpwJ7T585HfBsra', 'Djoka', 'Djokic', true);
insert into user_table (email, password, first_name, last_name, enabled)
values ('klijent@gmail.com', '$2a$10$aL2cRpbMvSsvTcIGxUoauO4RMefDmYtEEARsmKJpwJ7T585HfBsra', 'Mika', 'Mikic', true);

------------------------------USER AUTHORITY------------------------------
insert into user_authority (user_id, authority_id) values (1, 1);
insert into user_authority (user_id, authority_id) values (2, 2);
insert into user_authority (user_id, authority_id) values (3, 3);

------------------------------ACCOUNTS------------------------------
insert into account_table (jmbg, birth_date, address, city, zip_code, date, balance, user_id)
values ('2805998805053', '1998-05-28', 'Lasla Gala 23', 'Novi Sad', '21000', '2020-02-02', 1000000, 3);

------------------------------USER ACCOUNT------------------------------
update user_table set account_id = 1 where id = 3;