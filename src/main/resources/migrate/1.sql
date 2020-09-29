DROP TABLE IF EXISTS Channels;
CREATE TABLE Channels (
    id int AUTO_INCREMENT,
    channelName VARCHAR(255) NOT NULL,
    timestamp TIMESTAMP NOT NULL default(NOW()),
    PRIMARY KEY (id)
);

INSERT INTO Channels (channelName) VALUES ("Janu");

DROP TABLE IF EXISTS User;
CREATE TABLE User (
    id int AUTO_INCREMENT,
    userName VARCHAR(255) NOT NULL,
    timestamp TIMESTAMP NOT NULL default(NOW()),
    PRIMARY KEY (id)
);

INSERT INTO user (userName) VALUES ("Peter");

DROP TABLE IF EXISTS Messages;
CREATE TABLE Messages (
    id int AUTO_INCREMENT,
    messageText VARCHAR(255) NOT NULL,
    timestamp TIMESTAMP NOT NULL default(NOW()),
    PRIMARY KEY (id)
);

INSERT INTO messages (messageText) VALUES ("Test chat-text");

DROP TABLE IF EXISTS Subcriptions;
CREATE TABLE Subcriptions (
    id int AUTO_INCREMENT,
    channelName VARCHAR(255) NOT NULL,
    subscribe boolean,
    timestamp TIMESTAMP NOT NULL default(NOW()),
    PRIMARY KEY (subscribe)
    FOREIGN KEY (id) REFERENCES User(id)
    FOREIGN KEY (channelName) REFERENCES Channels(channelName)
);

INSERT INTO messages (messageText) VALUES ("Test chat-text");

UPDATE properties
SET value = '1'
WHERE name = "version";
