DROP DATABASE IF exists chadchat;
DROP USER if exists 'chadchat'@'locaohost';
CREATE DATABASE chadchat;

grant all privileges on chadchat.* to 'chadchat'@'locaohost';
use chadchat;