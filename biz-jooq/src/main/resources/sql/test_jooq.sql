/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 80021
Source Host           : localhost:3306
Source Database       : test_jooq

Target Server Type    : MYSQL
Target Server Version : 80021
File Encoding         : 65001

Date: 2021-04-27 17:37:37
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for jooq_test_table
-- ----------------------------
DROP TABLE IF EXISTS `jooq_test_table`;
CREATE TABLE `jooq_test_table` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=81 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- ----------------------------
-- Records of jooq_test_table
-- ----------------------------
INSERT INTO `jooq_test_table` VALUES ('1', '李四1');
INSERT INTO `jooq_test_table` VALUES ('2', '张三1');
INSERT INTO `jooq_test_table` VALUES ('3', '张三');
INSERT INTO `jooq_test_table` VALUES ('4', '李四');

-- ----------------------------
-- Table structure for sample
-- ----------------------------
DROP TABLE IF EXISTS `sample`;
CREATE TABLE `sample` (
  `id` int NOT NULL AUTO_INCREMENT,
  `c1` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `c2` bigint DEFAULT NULL,
  `c3` tinyint DEFAULT NULL,
  `c4` bit(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- ----------------------------
-- Records of sample
-- ----------------------------
INSERT INTO `sample` VALUES ('1', 'test', '123', '5', '1');
INSERT INTO `sample` VALUES ('2', 'test', '123', '1', '1');

