DROP DATABASE IF exists chadchat;
DROP USER if exists 'chadchat'@'localhost';
CREATE DATABASE chadchat;

grant all privileges on chadchat.* to 'chadchat'@'localhost';
use chadchat;