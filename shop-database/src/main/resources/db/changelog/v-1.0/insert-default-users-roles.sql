INSERT INTO users (age, password, username) VALUES 
    (33, '$2a$12$EbWwi0ZHGv6jXqTIaX2RUOPFnRKEfyax9y/zvIKz9ZOkdbhuFu.Jy', 'admin'), -- password = admin
    (30, '$2a$12$BjnoC0fCgs3TsLbG3yepueaMiY4ufcDIfwvvY.m/lvsipxTpil0oS', 'user');  -- password = user
GO

INSERT INTO roles (name) VALUES ('ROLE_ADMIN'), ('ROLE_USER');
GO

INSERT INTO users_roles (user_id, role_id) VALUES
((SELECT id FROM users WHERE users.username = 'admin'),
(SELECT id FROM roles WHERE roles.name = 'ROLE_ADMIN'));
GO

INSERT INTO users_roles (user_id, role_id) VALUES
((SELECT id FROM users WHERE users.username = 'user'),
(SELECT id FROM roles WHERE roles.name = 'ROLE_USER'));
GO