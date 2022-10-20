INSERT INTO users (id, username, password, age) VALUES
    (nextval('users_seq_gen'), 'admin', '$2a$12$EbWwi0ZHGv6jXqTIaX2RUOPFnRKEfyax9y/zvIKz9ZOkdbhuFu.Jy', 33), -- password = admin
    (nextval('users_seq_gen'), 'user', '$2a$12$BjnoC0fCgs3TsLbG3yepueaMiY4ufcDIfwvvY.m/lvsipxTpil0oS', 30);  -- password = user
GO

INSERT INTO roles (id, name) VALUES
    (nextval('roles_seq_gen'), 'ROLE_ADMIN'),
    (nextval('roles_seq_gen'), 'ROLE_USER');
GO

INSERT INTO users_roles (user_id, role_id) VALUES
    (1, 1),
    (2, 2);
GO
