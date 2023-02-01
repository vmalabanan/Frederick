
-- insert users table data
INSERT INTO users (user_id, username, password_hash, role, first_name, last_name) VALUES (1, 'user', '$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC','ROLE_USER', 'first', 'user');
INSERT INTO users (user_id, username, password_hash, role, first_name, last_name) VALUES (2, 'admin', '$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC','ROLE_ADMIN', 'first', 'admin');
INSERT INTO users (user_id, username, password_hash, role, first_name, last_name) VALUES (3, 'frederick', '$2a$10$bxJa9IWdnlllDQE3QMkt5.lD2z8ug3u1OhxZivZ4ifCPuVyFzlpO.','ROLE_USER', 'Ninja', 'Aung');
INSERT INTO users (user_id, username, password_hash, role, first_name, last_name) VALUES (4, 'vanessa', '$2a$10$GxLi4D/adyKmeqsXfu6L3usB4DEPtPEVJzlQYU1uwjoEfC1M7quae','ROLE_USER', 'Vanessa', 'Malabanan');
INSERT INTO users (user_id, username, password_hash, role, first_name, last_name) VALUES (5, 'jonathan', '$2a$10$0wFzUvUpO2lGmXBqnTJVTuQ2OeQA.5lyBydnpSaQ9725Hvftq5LjG','ROLE_USER', 'Jonathan', 'Thompson');
ALTER SEQUENCE seq_user_id RESTART WITH 6;


-- insert games table data
INSERT INTO games (game_id, game_name, organizer_id, end_date, game_length_days) VALUES (1, 'TE to the moon', 3, '2023-02-07 12:00:00', 7);
INSERT INTO games (game_id, game_name, organizer_id, end_date, game_length_days) VALUES (2, 'TEBets', 4, '2023-02-14 12:00:00', 14);
ALTER SEQUENCE seq_game_id RESTART WITH 3;

-- insert stocks table data
INSERT INTO stocks (stock_id, name, ticker, price, change) VALUES (1, 'Apple Inc', 'AAPL', 145.93, 1.37);
ALTER SEQUENCE seq_game_id RESTART WITH 2;
