
-- insert users table data
INSERT INTO users (user_id, username, password_hash, role, first_name, last_name) VALUES (1, 'user', '$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC','ROLE_USER', 'first', 'user');
INSERT INTO users (user_id, username, password_hash, role, first_name, last_name) VALUES (2, 'admin', '$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC','ROLE_ADMIN', 'first', 'admin');
INSERT INTO users (user_id, username, password_hash, role, first_name, last_name) VALUES (3, 'frederick', '$2a$10$bxJa9IWdnlllDQE3QMkt5.lD2z8ug3u1OhxZivZ4ifCPuVyFzlpO.','ROLE_USER', 'Ninja', 'Aung');
INSERT INTO users (user_id, username, password_hash, role, first_name, last_name) VALUES (4, 'vanessa', '$2a$10$GxLi4D/adyKmeqsXfu6L3usB4DEPtPEVJzlQYU1uwjoEfC1M7quae','ROLE_USER', 'Vanessa', 'Malabanan');
INSERT INTO users (user_id, username, password_hash, role, first_name, last_name) VALUES (5, 'jonathan', '$2a$10$0wFzUvUpO2lGmXBqnTJVTuQ2OeQA.5lyBydnpSaQ9725Hvftq5LjG','ROLE_USER', 'Jonathan', 'Thompson');
ALTER SEQUENCE seq_user_id RESTART WITH 6;


-- insert ??? table data

