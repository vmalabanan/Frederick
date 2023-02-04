
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

-- insert invitation_status table data
INSERT INTO invitation_status (invitation_status_id, invitation_status_desc) VALUES (1, 'Invited');
INSERT INTO invitation_status (invitation_status_id, invitation_status_desc) VALUES (2, 'Accepted');
INSERT INTO invitation_status (invitation_status_id, invitation_status_desc) VALUES (3, 'Rejected');
ALTER SEQUENCE seq_game_id RESTART WITH 4;

-- insert games_users table data
INSERT INTO games_users (game_id, user_id, invitation_status_id) VALUES (1, 1, 1);
INSERT INTO games_users (game_id, user_id, invitation_status_id) VALUES (1, 3, 1);
INSERT INTO games_users (game_id, user_id, invitation_status_id) VALUES (1, 4, 1);
INSERT INTO games_users (game_id, user_id, invitation_status_id) VALUES (1, 5, 1);
INSERT INTO games_users (game_id, user_id, invitation_status_id) VALUES (2, 1, 1);
INSERT INTO games_users (game_id, user_id, invitation_status_id) VALUES (2, 3, 1);
INSERT INTO games_users (game_id, user_id, invitation_status_id) VALUES (2, 4, 1);
INSERT INTO games_users (game_id, user_id, invitation_status_id) VALUES (2, 5, 1);

