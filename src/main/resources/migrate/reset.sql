DROP DATABASE IF exists chadchat;
DROP USER if exists 'chadchat'@'localhost';
CREATE DATABASE chadchat;
CREATE USER 'chadchat'@'localhost';

grant all privileges on chadchat.* to 'chadchat'@'localhost';
use chadchat;