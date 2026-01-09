-- REFERENCE: https://github.com/isa-asistent/Vezbe-2025/tree/main/vezbe4/spring-security-example

-- Addresses:
INSERT INTO Addresses (id, street, number, postal_code, place, country, latitude, longitude) 
		VALUES (1, 'Краља Драгутина', '4', '22320', 'Инђија', 'Србија', 45.043979, 20.071606);
INSERT INTO Addresses (id, street, number, postal_code, place, country, latitude, longitude) 
		VALUES (2, 'Голубиначка', '126', '22320', 'Инђија', 'Србија', 45.035361, 20.083708);

-- Users:
-- Password for this user is 'uZoric1993'.
INSERT INTO Users (id, enabled, email_address, username, password, 
		date_and_time_of_last_password_reset, first_name, last_name, address_id) VALUES (1, true, 
		'uros.zoric@gmail.com', 'UrosZoric1993', 
		'$2a$10$DWJZW/uNG0Sfeqzg.Vw9iOzcw7LstkH6oL.m1BP3KK6puIXoXHvK6', 
		'2025-11-11 09:51:47.000+01', 'Урош', 'Зорић', 1);
-- Password for this user is 'fCvetkovic2001'.
INSERT INTO Users (id, enabled, email_address, username, password, 
		date_and_time_of_last_password_reset, first_name, last_name, address_id) VALUES (2, true, 
		'filip.cvetkovic@gmail.com', 'FilipCvetkovic2001', 
		'$2a$10$k/2wBWL.TCEpS9Fi8FsIBeT2LeURUluthMTpEIS7KwBM9K6FMFeZ2', 
		'2025-11-12 12:27:34.000+01', 'Филип', 'Цветковић', 2);

-- User roles:
INSERT INTO User_roles (id, name) VALUES (1, 'ROLE_USER');

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
