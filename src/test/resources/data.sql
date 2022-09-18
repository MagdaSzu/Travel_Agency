DROP TABLE IF EXISTS client;

CREATE TABLE client (
id int NOT NULL AUTO_INCREMENT,
first_name varchar(50) NOT NULL,
last_name varchar(50) NOT NULL,
passport varchar(50) NOT NULL,
PRIMARY KEY (id)
);

