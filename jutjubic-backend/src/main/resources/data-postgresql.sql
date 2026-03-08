-- REFERENCE: https://github.com/isa-asistent/Vezbe-2025/tree/main/vezbe4/spring-security-example

-- Addresses:
INSERT INTO Addresses (id, street, number, postal_code, place, country, latitude, longitude, 
		version) VALUES (1, 'Краља Драгутина', '4', '22320', 'Инђија', 'Србија', 
		45.043979, 20.071606, 1);
INSERT INTO Addresses (id, street, number, postal_code, place, country, latitude, longitude, 
		version) VALUES (2, 'Голубиначка', '126', '22320', 'Инђија', 'Србија', 
		45.035361, 20.083708, 1);

-- Users:
-- Password for this user is 'uZoric1993'.
INSERT INTO Users (id, enabled, email_address, username, password, 
		date_and_time_of_last_password_change, digested_identificator, first_name, last_name, 
		address_id, version) VALUES (1, true, 'uros.zoric@gmail.com', 'UrosZoric1993', 
		'$2a$10$DWJZW/uNG0Sfeqzg.Vw9iOzcw7LstkH6oL.m1BP3KK6puIXoXHvK6', 
		-- REFERENCE: https://stackoverflow.com/questions/25456465/postgres-timestamp-with-timezone
		'2025-11-11 09:51:47.000000+01', 
		'f9137c69ac51d5347ce1c8ca21a7d3e43e3be13cd9683424cde97b2efb80e311', 
		'Урош', 'Зорић', 1, 1);
-- Password for this user is 'fCvetkovic2001'.
INSERT INTO Users (id, enabled, email_address, username, password, 
		date_and_time_of_last_password_change, digested_identificator, first_name, last_name, 
		address_id, version) VALUES (2, true, 'filip.cvetkovic@gmail.com', 'FilipCvetkovic2001', 
		'$2a$10$k/2wBWL.TCEpS9Fi8FsIBeT2LeURUluthMTpEIS7KwBM9K6FMFeZ2', 
		-- REFERENCE: https://stackoverflow.com/questions/25456465/postgres-timestamp-with-timezone
		'2025-11-12 12:27:34.000000+01', 
		'92c44f56ef7be622b672366f90df48291a4d32614bba728545c57336bb37ca4c', 
		'Филип', 'Цветковић', 2, 1);

-- User roles:
INSERT INTO User_roles (id, name, version) VALUES (1, 'ROLE_USER', 1);

-- Joining table of users and user roles:
INSERT INTO Joining_table_of_users_and_roles(user_id, role_id) VALUES (1, 1);
INSERT INTO Joining_table_of_users_and_roles(user_id, role_id) VALUES (2, 1);

-- REFERENCE: https://dba.stackexchange.com/questions/46125/why-does-postgres-generate-an-already-used-pk-value
-- REFERENCE: https://dba.stackexchange.com/a/90522
-- REFERENCE: https://commandprompt.com/education/is-nvl-function-same-as-coalesce-in-postgresql/
SELECT SETVAL('addresses_id_seq', (SELECT MAX(id) FROM Addresses));
SELECT SETVAL('users_id_seq', (SELECT MAX(id) FROM Users));
SELECT SETVAL('user_roles_id_seq', (SELECT MAX(id) FROM User_roles));
SELECT SETVAL('joining_table_of_users_and_roles_user_id_seq', 
		(SELECT MAX(user_id) FROM Joining_table_of_users_and_roles));
SELECT SETVAL('joining_table_of_users_and_roles_role_id_seq', 
		(SELECT MAX(role_id) FROM Joining_table_of_users_and_roles));
