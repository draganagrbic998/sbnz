------------------------------EXCHANGE RATES------------------------------
insert into exchange_rate (type, rate) values ('RSD', 1);
insert into exchange_rate (type, rate) values ('EUR', 117.48);
insert into exchange_rate (type, rate) values ('USD', 98.77);
insert into exchange_rate (type, rate) values ('CHF', 106.14);
insert into exchange_rate (type, rate) values ('GBP', 137.26);

------------------------------AUTHORITIES------------------------------
insert into authority (name) values ('ADMIN');
insert into authority (name) values ('SLUZBENIK');
insert into authority (name) values ('KLIJENT');

------------------------------USERS------------------------------
insert into user_table (email, password, first_name, last_name, enabled)
values ('admin@gmail.com', '$2a$10$aL2cRpbMvSsvTcIGxUoauO4RMefDmYtEEARsmKJpwJ7T585HfBsra', 'Pera', 'Peric', true);
insert into user_table (email, password, first_name, last_name, enabled)
values ('sluzbenik@gmail.com', '$2a$10$aL2cRpbMvSsvTcIGxUoauO4RMefDmYtEEARsmKJpwJ7T585HfBsra', 'Pera', 'Peric', true);
insert into user_table (email, password, first_name, last_name, enabled)
values ('klijent@gmail.com', '$2a$10$aL2cRpbMvSsvTcIGxUoauO4RMefDmYtEEARsmKJpwJ7T585HfBsra', 'Pera', 'Peric', true);

------------------------------USER AUTHORITY------------------------------
insert into user_authority (user_id, authority_id) values (1, 1);
insert into user_authority (user_id, authority_id) values (2, 2);
insert into user_authority (user_id, authority_id) values (3, 3);

------------------------------ACCOUNTS------------------------------
insert into account (user_id, date, birth_date, jmbg, address, city, zip_code, balance)
values (3, '2019-10-13', '1998-05-28', '2805998805053', 'Lasla Gala 23', 'Novi Sad', '21000', 30000000);
update user_table set account_id = 1 where id = 3;