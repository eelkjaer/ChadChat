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
) ENGINE=InnoDB AUTO_INCREMENT=72 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of Messages
-- ----------------------------
BEGIN;
INSERT INTO `Messages` VALUES (1, 'Hej', '2020-09-30 10:48:49', 3);
INSERT INTO `Messages` VALUES (2, 'Med', '2020-09-30 10:48:54', 2);
INSERT INTO `Messages` VALUES (3, 'Dig', '2020-09-30 10:48:58', 2);
INSERT INTO `Messages` VALUES (4, 'hej', '2020-09-30 11:07:03', 3);
INSERT INTO `Messages` VALUES (59, 'Hejsa', '2020-10-01 19:30:36', 2);
INSERT INTO `Messages` VALUES (60, 'Peter was kicked by Emil', '2020-10-01 19:32:18', 1);
INSERT INTO `Messages` VALUES (61, 'Hejsa', '2020-10-01 19:34:33', 3);
INSERT INTO `Messages` VALUES (62, 'Hej', '2020-10-01 19:40:15', 3);
INSERT INTO `Messages` VALUES (63, 'hej', '2020-10-01 19:45:23', 3);
INSERT INTO `Messages` VALUES (64, 'dav', '2020-10-01 19:45:33', 3);
INSERT INTO `Messages` VALUES (65, 'hejsa', '2020-10-01 19:45:39', 3);
INSERT INTO `Messages` VALUES (66, 'hej', '2020-10-01 19:45:44', 2);
INSERT INTO `Messages` VALUES (67, 'hvordan går det', '2020-10-01 19:45:51', 3);
INSERT INTO `Messages` VALUES (68, 'hej', '2020-10-01 20:10:15', 2);
INSERT INTO `Messages` VALUES (69, 'ok', '2020-10-01 20:25:06', 3);
INSERT INTO `Messages` VALUES (70, 'hej', '2020-10-01 21:12:41', 4);
INSERT INTO `Messages` VALUES (71, 'KurtVerner just joined. Say hi!', '2020-10-01 21:30:22', 1);
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
INSERT INTO `properties` VALUES ('version', '3');
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
  `salt` blob NOT NULL,
  `secret` blob NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of User
-- ----------------------------
BEGIN;
INSERT INTO `User` VALUES (1, 'SYSTEM', '2020-09-30 21:07:51', 1, 0xB023E79E8DE22DFDFC373C16C2AEDB3E, 0x27AD0C08CBBE43C45EDE011364DB4586E5D14F9831A7235A5B3412CE6CDF1F43);
INSERT INTO `User` VALUES (2, 'Peter', '2020-09-28 18:20:03', 0, 0xB023E79E8DE22DFDFC373C16C2AEDB3E, 0x27AD0C08CBBE43C45EDE011364DB4586E5D14F9831A7235A5B3412CE6CDF1F43);
INSERT INTO `User` VALUES (3, 'Emil', '2020-09-29 13:02:03', 1, 0xB023E79E8DE22DFDFC373C16C2AEDB3E, 0x27AD0C08CBBE43C45EDE011364DB4586E5D14F9831A7235A5B3412CE6CDF1F43);
INSERT INTO `User` VALUES (4, 'johnjohn', '2020-10-01 21:12:38', 0, 0xB023E79E8DE22DFDFC373C16C2AEDB3E, 0x27AD0C08CBBE43C45EDE011364DB4586E5D14F9831A7235A5B3412CE6CDF1F43);
INSERT INTO `User` VALUES (5, 'KurtVerner', '2020-10-01 21:30:22', 0, 0x23858B38E7A4074057FB5632E1E632F6, 0x62F0CEF8E2E1FB62CD776130213B181047B7904418BCADB7DE9CAF79C8D6176A);
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
