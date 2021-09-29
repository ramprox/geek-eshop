INSERT INTO users (age, password, username) VALUES 
    (33, '$2a$12$MRgkzgogA1Nv05pAL9ViSu2MyDcY2qoqSl5786dy1QBniwZHT00d6', 'admin'),
    (30, '$2a$10$BNGJaAxeTh1k9SN/62ObyON/DqmYRweJywBcnEW2K/ioqlGY8a9BK', 'user');
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