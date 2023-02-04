DROP TABLE IF EXISTS users;
DROP SEQUENCE IF EXISTS seq_user_id;

CREATE SEQUENCE seq_user_id
  INCREMENT BY 1
  NO MAXVALUE
  NO MINVALUE
  CACHE 1;


CREATE TABLE users (
	user_id int DEFAULT nextval('seq_user_id'::regclass) NOT NULL,
	username varchar(50) NOT NULL,
	password_hash varchar(200) NOT NULL,
	role varchar(50) NOT NULL,
	first_name varchar(50) NOT NULL,
	last_name varchar(50) NOT NULL,
	CONSTRAINT PK_user PRIMARY KEY (user_id)
);


-- create game table
DROP TABLE IF EXISTS games;
DROP SEQUENCE IF EXISTS seq_game_id;

CREATE SEQUENCE seq_game_id
  INCREMENT BY 1
  NO MAXVALUE
  NO MINVALUE
  CACHE 1;

CREATE TABLE games (
	game_id int DEFAULT nextval('seq_game_id'::regclass) NOT NULL,
	game_name varchar(50) NOT NULL,
	organizer_id int NOT NULL,
	end_date TIMESTAMP NOT NULL,
	game_length_days int NOT NULL,
	CONSTRAINT PK_game PRIMARY KEY (game_id)
);

-- create invitation_status table
DROP TABLE IF EXISTS invitation_status;
DROP SEQUENCE IF EXISTS seq_invitation_status_id;

CREATE SEQUENCE seq_invitation_status_id
   INCREMENT BY 1
   NO MAXVALUE
   NO MINVALUE
   CACHE 1;

 CREATE TABLE invitation_status (
 	invitation_status_id int DEFAULT nextval('seq_invitation_status_id'::regclass) NOT NULL,
 	invitation_status_desc varchar(10) NOT NULL,
 	CONSTRAINT PK_invitation_status PRIMARY KEY (invitation_status_id)
 );

 -- create games_users table
 DROP TABLE IF EXISTS games_users;
 DROP SEQUENCE IF EXISTS seq_games_users_id;

 CREATE SEQUENCE seq_games_users_id
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;

 CREATE TABLE games_users (
    game_id int NOT NULL,
    user_id int NOT NULL,
 	invitation_status_id int NOT NULL,
 	CONSTRAINT PK_games_users PRIMARY KEY(game_id, user_id),
    CONSTRAINT FK_games_users_games FOREIGN KEY(game_id) REFERENCES games(game_id),
    CONSTRAINT FK_games_users_users FOREIGN KEY(user_id) REFERENCES users(user_id),
 	CONSTRAINT FK_games_users_invitation_status FOREIGN KEY(invitation_status_id) REFERENCES invitation_status(invitation_status_id)
 );

CREATE TABLE cash (
    game_id int NOT NULL,
    user_id int NOT NULL,
	cash decimal(15, 2) NOT NULL,
 	CONSTRAINT PK_cash PRIMARY KEY(game_id, user_id),
    CONSTRAINT FK_cash_games FOREIGN KEY(game_id) REFERENCES games(game_id),
    CONSTRAINT FK_cash_users FOREIGN KEY(user_id) REFERENCES users(user_id)
 );

-- create games_users table




-- Add seed data in the 03_data.sql script file

-- Add Foreign key constraints in the 04_constraints.sql script file
