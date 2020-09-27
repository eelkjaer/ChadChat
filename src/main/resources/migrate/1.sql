DROP TABLE IF EXISTS Channels;
CREATE TABLE Channels (
    id int AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    PRIMARY KEY (id)
);

INSERT INTO Channels (name) VALUES ("Janus");

DROP TABLE IF EXISTS User;
CREATE TABLE User (
    id int AUTO_INCREMENT,
    userName VARCHAR(255) NOT NULL,
    timestamp TIMESTAMP NOT NULL default(NOW()),
    PRIMARY KEY (id)
);

INSERT INTO user (userName) VALUES ("Peter");

UPDATE properties
SET value = '1'
WHERE name = "version";
