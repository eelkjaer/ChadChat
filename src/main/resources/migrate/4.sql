/*
 Navicat Premium Data Transfer

 Source Server         : Localhost
 Source Server Type    : MySQL
 Source Server Version : 80019
 Source Host           : localhost:3306
 Source Schema         : ChadChat

 Target Server Type    : MySQL
 Target Server Version : 80019
 File Encoding         : 65001

 Date: 05/10/2020 10:12:26
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for Channels
-- ----------------------------
DROP TABLE IF EXISTS `Channels`;
CREATE TABLE `Channels` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `ownerId` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `channelOwner` (`ownerId`),
  CONSTRAINT `channelOwner` FOREIGN KEY (`ownerId`) REFERENCES `User` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of Channels
-- ----------------------------
BEGIN;
INSERT INTO `Channels` VALUES (1, 'Generelt', 1);
INSERT INTO `Channels` VALUES (2, 'Snak om bajere', 4);
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
  `channelId` int NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`),
  KEY `userIdMsg` (`userid`),
  KEY `channelIdMsg` (`channelId`),
  CONSTRAINT `channelIdMsg` FOREIGN KEY (`channelId`) REFERENCES `Channels` (`id`),
  CONSTRAINT `userIdMsg` FOREIGN KEY (`userid`) REFERENCES `User` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=116 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of Messages
-- ----------------------------
BEGIN;
INSERT INTO `Messages` VALUES (1, 'Hej', '2020-09-30 10:48:49', 3, 1);
INSERT INTO `Messages` VALUES (2, 'Med', '2020-09-30 10:48:54', 2, 1);
INSERT INTO `Messages` VALUES (3, 'Dig', '2020-09-30 10:48:58', 2, 1);
INSERT INTO `Messages` VALUES (4, 'hej', '2020-09-30 11:07:03', 3, 1);
INSERT INTO `Messages` VALUES (59, 'Hejsa', '2020-10-01 19:30:36', 2, 1);
INSERT INTO `Messages` VALUES (60, 'Peter was kicked by Emil', '2020-10-01 19:32:18', 1, 1);
INSERT INTO `Messages` VALUES (61, 'Hejsa', '2020-10-01 19:34:33', 3, 1);
INSERT INTO `Messages` VALUES (62, 'Hej', '2020-10-01 19:40:15', 3, 1);
INSERT INTO `Messages` VALUES (63, 'hej', '2020-10-01 19:45:23', 3, 1);
INSERT INTO `Messages` VALUES (64, 'dav', '2020-10-01 19:45:33', 3, 1);
INSERT INTO `Messages` VALUES (65, 'hejsa', '2020-10-01 19:45:39', 3, 1);
INSERT INTO `Messages` VALUES (66, 'hej', '2020-10-01 19:45:44', 2, 1);
INSERT INTO `Messages` VALUES (67, 'hvordan går det', '2020-10-01 19:45:51', 3, 1);
INSERT INTO `Messages` VALUES (68, 'hej', '2020-10-01 20:10:15', 2, 1);
INSERT INTO `Messages` VALUES (69, 'ok', '2020-10-01 20:25:06', 3, 1);
INSERT INTO `Messages` VALUES (70, 'hej', '2020-10-01 21:12:41', 4, 1);
INSERT INTO `Messages` VALUES (71, 'KurtVerner just joined. Say hi!', '2020-10-01 21:30:22', 1, 1);
INSERT INTO `Messages` VALUES (72, 'Hejsa', '2020-10-04 18:42:43', 3, 1);
INSERT INTO `Messages` VALUES (73, 'hejsa', '2020-10-04 18:48:40', 3, 1);
INSERT INTO `Messages` VALUES (74, 'Hejsa', '2020-10-04 18:53:15', 3, 2);
INSERT INTO `Messages` VALUES (75, 'hej', '2020-10-04 18:53:28', 2, 1);
INSERT INTO `Messages` VALUES (76, 'Hejsa med jer', '2020-10-04 18:56:29', 3, 2);
INSERT INTO `Messages` VALUES (77, 'hvordan gaar det', '2020-10-04 18:56:36', 3, 2);
INSERT INTO `Messages` VALUES (78, 'godt', '2020-10-04 18:56:46', 2, 1);
INSERT INTO `Messages` VALUES (79, 'hej', '2020-10-04 19:02:55', 3, 1);
INSERT INTO `Messages` VALUES (80, 'Bajersaft', '2020-10-04 19:03:03', 3, 2);
INSERT INTO `Messages` VALUES (81, 'test i gen', '2020-10-04 19:03:13', 2, 1);
INSERT INTO `Messages` VALUES (82, 'ok', '2020-10-04 19:03:19', 3, 1);
INSERT INTO `Messages` VALUES (83, 'test', '2020-10-04 19:03:26', 3, 2);
INSERT INTO `Messages` VALUES (84, 'okay', '2020-10-04 19:03:28', 2, 2);
INSERT INTO `Messages` VALUES (85, 'test', '2020-10-04 19:03:59', 2, 1);
INSERT INTO `Messages` VALUES (86, 'okay', '2020-10-04 19:04:08', 3, 2);
INSERT INTO `Messages` VALUES (87, 'fedt', '2020-10-04 19:04:11', 2, 2);
INSERT INTO `Messages` VALUES (89, 'kurtverner just joined ChadChat®. Say hi!', '2020-10-04 19:17:40', 1, 1);
INSERT INTO `Messages` VALUES (90, 'Hi', '2020-10-04 19:17:44', 5, 1);
INSERT INTO `Messages` VALUES (91, 'Hej med bajere', '2020-10-04 19:36:05', 3, 2);
INSERT INTO `Messages` VALUES (92, 'dav', '2020-10-04 19:36:12', 2, 1);
INSERT INTO `Messages` VALUES (93, 'test i generelt', '2020-10-04 19:56:39', 3, 1);
INSERT INTO `Messages` VALUES (94, 'hejsa', '2020-10-04 19:56:48', 2, 1);
INSERT INTO `Messages` VALUES (95, 'bajersaft', '2020-10-04 19:56:58', 2, 2);
INSERT INTO `Messages` VALUES (96, 'what', '2020-10-04 19:57:04', 3, 1);
INSERT INTO `Messages` VALUES (97, 'seems right', '2020-10-04 19:57:10', 3, 1);
INSERT INTO `Messages` VALUES (98, '', '2020-10-04 20:05:32', 2, 1);
INSERT INTO `Messages` VALUES (99, 'hej bajere', '2020-10-04 20:05:47', 2, 2);
INSERT INTO `Messages` VALUES (100, 'hej', '2020-10-04 20:05:53', 3, 1);
INSERT INTO `Messages` VALUES (101, 'hej peter', '2020-10-04 20:06:00', 3, 2);
INSERT INTO `Messages` VALUES (102, 'hej', '2020-10-04 20:06:04', 2, 2);
INSERT INTO `Messages` VALUES (103, 'hej', '2020-10-04 20:07:52', 2, 1);
INSERT INTO `Messages` VALUES (104, 'hej bajere', '2020-10-04 20:08:05', 3, 2);
INSERT INTO `Messages` VALUES (105, 'dav', '2020-10-04 20:08:15', 2, 1);
INSERT INTO `Messages` VALUES (106, 'hejsa gen', '2020-10-04 20:15:32', 3, 1);
INSERT INTO `Messages` VALUES (107, 'hej', '2020-10-04 20:15:38', 2, 1);
INSERT INTO `Messages` VALUES (108, 'hej bajere', '2020-10-04 20:15:45', 3, 2);
INSERT INTO `Messages` VALUES (109, 'hej gen2', '2020-10-04 20:15:51', 2, 1);
INSERT INTO `Messages` VALUES (110, 'hvrodan gC%r det', '2020-10-04 20:15:57', 3, 2);
INSERT INTO `Messages` VALUES (111, 'hej emil', '2020-10-04 20:16:05', 2, 2);
INSERT INTO `Messages` VALUES (112, 'Hej med C8', '2020-10-04 20:25:32', 3, 1);
INSERT INTO `Messages` VALUES (113, 'fra sql: æøå', '2020-10-04 20:27:08', 3, 1);
INSERT INTO `Messages` VALUES (114, 'C&C8C%', '2020-10-04 20:37:41', 3, 1);
INSERT INTO `Messages` VALUES (115, 'æøå', '2020-10-04 20:50:24', 3, 1);
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
INSERT INTO `User` VALUES (4, 'Janus', '2020-10-01 21:12:38', 0, 0xB023E79E8DE22DFDFC373C16C2AEDB3E, 0x27AD0C08CBBE43C45EDE011364DB4586E5D14F9831A7235A5B3412CE6CDF1F43);
INSERT INTO `User` VALUES (5, 'kurtverner', '2020-10-04 19:17:40', 0, 0x297D43F5CAC3E8D29B68393BF348AA4F, 0x34A789822E968DAD62E337A79757C92F5947310C5E8719D68CB82F3ECB3EB788);
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
