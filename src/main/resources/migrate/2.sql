SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for Channels
-- ----------------------------
DROP TABLE IF EXISTS `Channels`;
CREATE TABLE `Channels` (
                            `id` int NOT NULL AUTO_INCREMENT,
                            `name` varchar(255) NOT NULL,
                            PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of Channels
-- ----------------------------
BEGIN;
INSERT INTO `Channels` VALUES (1, 'Janus');
COMMIT;

-- ----------------------------
-- Table structure for Messages
-- ----------------------------
DROP TABLE IF EXISTS `Messages`;
CREATE TABLE `Messages` (
                            `id` int NOT NULL AUTO_INCREMENT,
                            `messageText` varchar(255) NOT NULL,
                            `timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
                            `userid` int NOT NULL,
                            PRIMARY KEY (`id`),
                            KEY `userIdMsg` (`userid`),
                            CONSTRAINT `userIdMsg` FOREIGN KEY (`userid`) REFERENCES `User` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=59 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of Messages
-- ----------------------------
BEGIN;
INSERT INTO `Messages` VALUES (1, 'Hej', '2020-09-30 10:48:49', 3);
INSERT INTO `Messages` VALUES (2, 'Med', '2020-09-30 10:48:54', 2);
INSERT INTO `Messages` VALUES (3, 'Dig', '2020-09-30 10:48:58', 2);
INSERT INTO `Messages` VALUES (4, 'hej', '2020-09-30 11:07:03', 3);
COMMIT;

-- ----------------------------
-- Table structure for properties
-- ----------------------------
DROP TABLE IF EXISTS `properties`;
CREATE TABLE `properties` (
                              `name` varchar(255) NOT NULL,
                              `value` varchar(255) NOT NULL,
                              PRIMARY KEY (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of properties
-- ----------------------------
BEGIN;
INSERT INTO `properties` VALUES ('version', '2');
COMMIT;

-- ----------------------------
-- Table structure for User
-- ----------------------------
DROP TABLE IF EXISTS `User`;
CREATE TABLE `User` (
                        `id` int NOT NULL AUTO_INCREMENT,
                        `userName` varchar(255) NOT NULL,
                        `timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
                        `admin` tinyint(1) NOT NULL DEFAULT '0',
                        `salt` BINARY(16) NOT NULL,
                        `secret`  BINARY(32) NOT NULL,
                        PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of User
-- ----------------------------
BEGIN;
INSERT INTO `User` VALUES (1, 'SYSTEM', '2020-09-30 21:07:51', 1, 0, 0);
INSERT INTO `User` VALUES (2, 'Peter', '2020-09-28 18:20:03', 0, 0, 0);
INSERT INTO `User` VALUES (3, 'Emil', '2020-09-29 13:02:03', 1, 0, 0);
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
