
-- insert users table data
INSERT INTO users (user_id, username, password_hash, role, first_name, last_name) VALUES (1, 'user', '$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC','ROLE_USER', 'first', 'user');
INSERT INTO users (user_id, username, password_hash, role, first_name, last_name) VALUES (3, 'admin', '$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC','ROLE_ADMIN', 'first', 'admin');
ALTER SEQUENCE seq_user_id RESTART WITH 3;

-- insert ??? table data

