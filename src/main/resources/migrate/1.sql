DROP TABLE IF EXISTS Channels;
CREATE TABLE Channels (
    id int NOT NULL AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    PRIMARY KEY (id)
);

UPDATE properties
SET value = '1'
WHERE name = "version";
