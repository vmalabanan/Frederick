
-- insert users table data
INSERT INTO users (user_id, username, password_hash, role, first_name, last_name) VALUES (1, 'user', '$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC','ROLE_USER', 'first', 'user');
INSERT INTO users (user_id, username, password_hash, role, first_name, last_name) VALUES (2, 'admin', '$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC','ROLE_ADMIN', 'first', 'admin');
INSERT INTO users (user_id, username, password_hash, role, first_name, last_name) VALUES (3, 'frederick', '$2a$10$bxJa9IWdnlllDQE3QMkt5.lD2z8ug3u1OhxZivZ4ifCPuVyFzlpO.','ROLE_USER', 'Ninja', 'Aung');
INSERT INTO users (user_id, username, password_hash, role, first_name, last_name) VALUES (4, 'vanessa', '$2a$10$GxLi4D/adyKmeqsXfu6L3usB4DEPtPEVJzlQYU1uwjoEfC1M7quae','ROLE_USER', 'Vanessa', 'Malabanan');
INSERT INTO users (user_id, username, password_hash, role, first_name, last_name) VALUES (5, 'jonathan', '$2a$10$0wFzUvUpO2lGmXBqnTJVTuQ2OeQA.5lyBydnpSaQ9725Hvftq5LjG','ROLE_USER', 'Jonathan', 'Thompson');
ALTER SEQUENCE seq_user_id RESTART WITH 6;


-- insert games table data
INSERT INTO games (game_id, game_name, organizer_id, start_date, end_date, game_length_days) VALUES (1, 'TE to the moon', 3, '2023-01-31 12:00:00', '2023-02-07 12:00:00', 7);
INSERT INTO games (game_id, game_name, organizer_id, start_date, end_date, game_length_days) VALUES (2, 'TEBets', 4, '2023-01-31 12:00:00', '2023-02-14 12:00:00', 14);
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

-- insert trade_type table data
INSERT INTO trade_type (trade_type_id, trade_type_desc) VALUES (1, 'Buy');
INSERT INTO trade_type (trade_type_id, trade_type_desc) VALUES (2, 'Sell');

-- create an ended game without trades (#777)
INSERT INTO games (game_id, game_name, organizer_id, start_date, end_date, game_length_days)
VALUES (777, 'Vanessa Game Ended', 4, CURRENT_TIMESTAMP - INTERVAL '8 day', CURRENT_TIMESTAMP - INTERVAL '1 day', 7);

INSERT INTO games_users (game_id, user_id, invitation_status_id) VALUES (777, 4, 2);
INSERT INTO games_users (game_id, user_id, invitation_status_id) VALUES (777, 5, 2);
INSERT INTO games_users (game_id, user_id, invitation_status_id) VALUES (777, 3, 2);
INSERT INTO games_users (game_id, user_id, invitation_status_id) VALUES (777, 1, 2);


INSERT INTO cash (game_id, user_id, amount, effective_date) VALUES (777, 4, 105239.32, CURRENT_TIMESTAMP - INTERVAL '5 day');
INSERT INTO cash (game_id, user_id, amount, effective_date) VALUES (777, 5, 98233.21, CURRENT_TIMESTAMP - INTERVAL '5 day');
INSERT INTO cash (game_id, user_id, amount, effective_date) VALUES (777, 3, 110238.12, CURRENT_TIMESTAMP - INTERVAL '5 day');
INSERT INTO cash (game_id, user_id, amount, effective_date) VALUES (777, 1, 67938.99, CURRENT_TIMESTAMP - INTERVAL '5 day');

-- create a current game with trades (#555)
INSERT INTO games (game_id, game_name, organizer_id, start_date, end_date, game_length_days)
VALUES (555, 'Vanessa Test', 4, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP + INTERVAL '7 day', 7);

INSERT INTO games_users (game_id, user_id, invitation_status_id) VALUES (555, 4, 2);
INSERT INTO games_users (game_id, user_id, invitation_status_id) VALUES (555, 5, 2);
INSERT INTO games_users (game_id, user_id, invitation_status_id) VALUES (555, 3, 2);
INSERT INTO games_users (game_id, user_id, invitation_status_id) VALUES (555, 1, 2);




INSERT INTO cash (game_id, user_id, amount, effective_date) VALUES (555, 4, 100000, CURRENT_TIMESTAMP);
INSERT INTO cash (game_id, user_id, amount, effective_date) VALUES (555, 4, 75239.48, CURRENT_TIMESTAMP + INTERVAL '1 day');
INSERT INTO cash (game_id, user_id, amount, effective_date) VALUES (555, 4, 0.19, CURRENT_TIMESTAMP + INTERVAL '2 day');
INSERT INTO cash (game_id, user_id, amount, effective_date) VALUES (555, 4, 15938.30, CURRENT_TIMESTAMP + INTERVAL '4 day');
INSERT INTO cash (game_id, user_id, amount, effective_date) VALUES (555, 4, 123.23, CURRENT_TIMESTAMP + INTERVAL '6 day');
INSERT INTO cash (game_id, user_id, amount, effective_date) VALUES (555, 4, 1000.55, CURRENT_TIMESTAMP + INTERVAL '7 day');



INSERT INTO cash (game_id, user_id, amount, effective_date) VALUES (555, 5, 100000, CURRENT_TIMESTAMP);
INSERT INTO cash (game_id, user_id, amount, effective_date) VALUES (555, 5, 50231.32, CURRENT_TIMESTAMP + INTERVAL '3 hour');
INSERT INTO cash (game_id, user_id, amount, effective_date) VALUES (555, 5, 4028.19, CURRENT_TIMESTAMP + INTERVAL '1 day');
INSERT INTO cash (game_id, user_id, amount, effective_date) VALUES (555, 5, 193.60, CURRENT_TIMESTAMP + INTERVAL '3 day');
INSERT INTO cash (game_id, user_id, amount, effective_date) VALUES (555, 5, 10439.93, CURRENT_TIMESTAMP + INTERVAL '4 day');
INSERT INTO cash (game_id, user_id, amount, effective_date) VALUES (555, 5, 43.23, CURRENT_TIMESTAMP + INTERVAL '7 day');


INSERT INTO cash (game_id, user_id, amount, effective_date) VALUES (555, 3, 100000, CURRENT_TIMESTAMP);
INSERT INTO cash (game_id, user_id, amount, effective_date) VALUES (555, 3, 40631.72, CURRENT_TIMESTAMP + INTERVAL '3 hour');
INSERT INTO cash (game_id, user_id, amount, effective_date) VALUES (555, 3, 345.34, CURRENT_TIMESTAMP + INTERVAL '1 day');
INSERT INTO cash (game_id, user_id, amount, effective_date) VALUES (555, 3, 3045.49, CURRENT_TIMESTAMP + INTERVAL '3 day');
INSERT INTO cash (game_id, user_id, amount, effective_date) VALUES (555, 3, 490.22, CURRENT_TIMESTAMP + INTERVAL '4 day');
INSERT INTO cash (game_id, user_id, amount, effective_date) VALUES (555, 3, 10.22, CURRENT_TIMESTAMP + INTERVAL '7 day');


INSERT INTO cash (game_id, user_id, amount, effective_date) VALUES (555, 1, 100000, CURRENT_TIMESTAMP);
INSERT INTO cash (game_id, user_id, amount, effective_date) VALUES (555, 1, 2049.12, CURRENT_TIMESTAMP + INTERVAL '3 hour');
INSERT INTO cash (game_id, user_id, amount, effective_date) VALUES (555, 1, 340.12, CURRENT_TIMESTAMP + INTERVAL '1 day');
INSERT INTO cash (game_id, user_id, amount, effective_date) VALUES (555, 1, 59.14, CURRENT_TIMESTAMP + INTERVAL '3 day');
INSERT INTO cash (game_id, user_id, amount, effective_date) VALUES (555, 1, 29437.13, CURRENT_TIMESTAMP + INTERVAL '4 day');
INSERT INTO cash (game_id, user_id, amount, effective_date) VALUES (555, 1, 49.34, CURRENT_TIMESTAMP + INTERVAL '7 day');

INSERT INTO stocks (ticker_symbol)
VALUES ('AAPL')
ON CONFLICT DO NOTHING;

INSERT INTO stocks (ticker_symbol)
VALUES ('META')
ON CONFLICT DO NOTHING;

INSERT INTO stocks (ticker_symbol)
VALUES ('NFLX')
ON CONFLICT DO NOTHING;

INSERT INTO stocks (ticker_symbol)
VALUES ('GOOG')
ON CONFLICT DO NOTHING;

INSERT INTO stocks (ticker_symbol)
VALUES ('AMZN')
ON CONFLICT DO NOTHING;

INSERT INTO stocks (ticker_symbol)
VALUES ('HBI')
ON CONFLICT DO NOTHING;

INSERT INTO stocks (ticker_symbol)
VALUES ('JZ')
ON CONFLICT DO NOTHING;

INSERT INTO stocks (ticker_symbol)
VALUES ('VR')
ON CONFLICT DO NOTHING;

INSERT INTO stocks (ticker_symbol)
VALUES ('BL')
ON CONFLICT DO NOTHING;

INSERT INTO stocks (ticker_symbol)
VALUES ('GH')
ON CONFLICT DO NOTHING;

INSERT INTO trades (game_id, user_id, stock_id, trade_type_id, number_of_shares, share_price, trade_date)
VALUES (555, 4, 1, 1, 100, 105.10, CURRENT_TIMESTAMP);

INSERT INTO trades (game_id, user_id, stock_id, trade_type_id, number_of_shares, share_price, trade_date)
VALUES (555, 4, 2, 1, 90, 50.27, CURRENT_TIMESTAMP + INTERVAL '1 hour');

INSERT INTO trades (game_id, user_id, stock_id, trade_type_id, number_of_shares, share_price, trade_date)
VALUES (555, 4, 3, 1, 50, 35.34, CURRENT_TIMESTAMP + INTERVAL '2 hours');

INSERT INTO trades (game_id, user_id, stock_id, trade_type_id, number_of_shares, share_price, trade_date)
VALUES (555, 4, 2, 2, 28, 60.00, CURRENT_TIMESTAMP + INTERVAL '3 hours');

INSERT INTO trades (game_id, user_id, stock_id, trade_type_id, number_of_shares, share_price, trade_date)
VALUES (555, 4, 4, 1, 67, 26.76, CURRENT_TIMESTAMP + INTERVAL '1 day');

INSERT INTO trades (game_id, user_id, stock_id, trade_type_id, number_of_shares, share_price, trade_date)
VALUES (555, 4, 1, 1, 50, 100.54, CURRENT_TIMESTAMP + INTERVAL '28 hours');

INSERT INTO trades (game_id, user_id, stock_id, trade_type_id, number_of_shares, share_price, trade_date)
VALUES (555, 4, 1, 2, 150, 120.56, CURRENT_TIMESTAMP + INTERVAL '2 days');

INSERT INTO trades (game_id, user_id, stock_id, trade_type_id, number_of_shares, share_price, trade_date)
VALUES (555, 4, 4, 1, 23, 30.56, CURRENT_TIMESTAMP + INTERVAL '3 days');

INSERT INTO trades (game_id, user_id, stock_id, trade_type_id, number_of_shares, share_price, trade_date)
VALUES (555, 4, 5, 1, 24, 15.75, CURRENT_TIMESTAMP + INTERVAL '90 hours');

INSERT INTO trades (game_id, user_id, stock_id, trade_type_id, number_of_shares, share_price, trade_date)
VALUES (555, 4, 5, 2, 12, 20.34, CURRENT_TIMESTAMP + INTERVAL '5 days');

INSERT INTO trades (game_id, user_id, stock_id, trade_type_id, number_of_shares, share_price, trade_date)
VALUES (555, 4, 2, 1, 23, 62.43, CURRENT_TIMESTAMP + INTERVAL '125 hours');

INSERT INTO trades (game_id, user_id, stock_id, trade_type_id, number_of_shares, share_price, trade_date)
VALUES (555, 4, 2, 2, 28, 75.34, CURRENT_TIMESTAMP + INTERVAL '6 days');

INSERT INTO trades (game_id, user_id, stock_id, trade_type_id, number_of_shares, share_price, trade_date)
VALUES (555, 4, 1, 1, 123, 110.34, CURRENT_TIMESTAMP + INTERVAL '147 hours');

INSERT INTO trades (game_id, user_id, stock_id, trade_type_id, number_of_shares, share_price, trade_date)
VALUES (555, 4, 1, 2, 50, 115.23, CURRENT_TIMESTAMP + INTERVAL '160 hours');

INSERT INTO trades (game_id, user_id, stock_id, trade_type_id, number_of_shares, share_price, trade_date)
VALUES (555, 4, 6, 1, 134, 59.43, CURRENT_TIMESTAMP + INTERVAL '162 hours');



INSERT INTO trades (game_id, user_id, stock_id, trade_type_id, number_of_shares, share_price, trade_date)
VALUES (555, 5, 1, 1, 34, 105.10, CURRENT_TIMESTAMP);

INSERT INTO trades (game_id, user_id, stock_id, trade_type_id, number_of_shares, share_price, trade_date)
VALUES (555, 5, 2, 1, 59, 50.27, CURRENT_TIMESTAMP + INTERVAL '1 hour');

INSERT INTO trades (game_id, user_id, stock_id, trade_type_id, number_of_shares, share_price, trade_date)
VALUES (555, 5, 3, 1, 100, 35.34, CURRENT_TIMESTAMP + INTERVAL '2 hours');

INSERT INTO trades (game_id, user_id, stock_id, trade_type_id, number_of_shares, share_price, trade_date)
VALUES (555, 5, 2, 2, 24, 60.00, CURRENT_TIMESTAMP + INTERVAL '3 hours');

INSERT INTO trades (game_id, user_id, stock_id, trade_type_id, number_of_shares, share_price, trade_date)
VALUES (555, 5, 4, 1, 84, 26.76, CURRENT_TIMESTAMP + INTERVAL '1 day');

INSERT INTO trades (game_id, user_id, stock_id, trade_type_id, number_of_shares, share_price, trade_date)
VALUES (555, 5, 1, 1, 23, 100.54, CURRENT_TIMESTAMP + INTERVAL '28 hours');

INSERT INTO trades (game_id, user_id, stock_id, trade_type_id, number_of_shares, share_price, trade_date)
VALUES (555, 5, 1, 2, 15, 120.56, CURRENT_TIMESTAMP + INTERVAL '2 days');

INSERT INTO trades (game_id, user_id, stock_id, trade_type_id, number_of_shares, share_price, trade_date)
VALUES (555, 5, 6, 1, 89, 30.56, CURRENT_TIMESTAMP + INTERVAL '3 days');

INSERT INTO trades (game_id, user_id, stock_id, trade_type_id, number_of_shares, share_price, trade_date)
VALUES (555, 5, 7, 1, 23, 15.75, CURRENT_TIMESTAMP + INTERVAL '90 hours');

INSERT INTO trades (game_id, user_id, stock_id, trade_type_id, number_of_shares, share_price, trade_date)
VALUES (555, 5, 6, 2, 12, 20.34, CURRENT_TIMESTAMP + INTERVAL '5 days');

INSERT INTO trades (game_id, user_id, stock_id, trade_type_id, number_of_shares, share_price, trade_date)
VALUES (555, 5, 8, 1, 23, 62.43, CURRENT_TIMESTAMP + INTERVAL '125 hours');

INSERT INTO trades (game_id, user_id, stock_id, trade_type_id, number_of_shares, share_price, trade_date)
VALUES (555, 5, 8, 2, 8, 75.34, CURRENT_TIMESTAMP + INTERVAL '6 days');

INSERT INTO trades (game_id, user_id, stock_id, trade_type_id, number_of_shares, share_price, trade_date)
VALUES (555, 5, 9, 1, 123, 110.34, CURRENT_TIMESTAMP + INTERVAL '147 hours');

INSERT INTO trades (game_id, user_id, stock_id, trade_type_id, number_of_shares, share_price, trade_date)
VALUES (555, 5, 9, 2, 50, 115.23, CURRENT_TIMESTAMP + INTERVAL '160 hours');

INSERT INTO trades (game_id, user_id, stock_id, trade_type_id, number_of_shares, share_price, trade_date)
VALUES (555, 5, 8, 1, 134, 59.43, CURRENT_TIMESTAMP + INTERVAL '162 hours');



INSERT INTO trades (game_id, user_id, stock_id, trade_type_id, number_of_shares, share_price, trade_date)
VALUES (555, 3, 1, 1, 340, 105.10, CURRENT_TIMESTAMP);

INSERT INTO trades (game_id, user_id, stock_id, trade_type_id, number_of_shares, share_price, trade_date)
VALUES (555, 1, 2, 1, 543, 105.10, CURRENT_TIMESTAMP);

