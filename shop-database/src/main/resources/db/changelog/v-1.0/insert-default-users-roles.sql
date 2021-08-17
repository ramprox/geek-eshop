INSERT INTO users (age, password, username) VALUES 
    (33, '$2a$12$MRgkzgogA1Nv05pAL9ViSu2MyDcY2qoqSl5786dy1QBniwZHT00d6', 'admin'),
    (30, '$2a$12$2grU5FhzknaIcKK.MLIYNudRR2RBCJIZJcI9RCV.s03NyapAnti2O', 'user1');
GO

INSERT INTO roles (name) VALUES ('ROLE_ADMIN'), ('ROLE_USER');
GO

INSERT INTO users_roles (user_id, role_id) VALUES
((SELECT id FROM users WHERE users.username = 'admin'),
(SELECT id FROM roles WHERE roles.name = 'ROLE_ADMIN'));
GO

INSERT INTO users_roles (user_id, role_id) VALUES
((SELECT id FROM users WHERE users.username = 'user1'),
(SELECT id FROM roles WHERE roles.name = 'ROLE_USER'));
GO