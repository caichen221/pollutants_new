/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 80022
 Source Host           : localhost:3306
 Source Schema         : rocketmq-transaction

 Target Server Type    : MySQL
 Target Server Version : 80022
 File Encoding         : 65001

 Date: 01/06/2021 16:12:50
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for good_order
-- ----------------------------
DROP TABLE IF EXISTS `good_order`;
CREATE TABLE `good_order`  (
  `id` int(0) NOT NULL,
  `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `num` int(0) NOT NULL,
  `price` float(10, 2) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of good_order
-- ----------------------------
INSERT INTO `good_order` VALUES (2975, '牛奶', 2, 5.20);
INSERT INTO `good_order` VALUES (40220, '牛奶', 2, 5.20);

-- ----------------------------
-- Table structure for good_store
-- ----------------------------
DROP TABLE IF EXISTS `good_store`;
CREATE TABLE `good_store`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `stock` int(0) NOT NULL,
  `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of good_store
-- ----------------------------
INSERT INTO `good_store` VALUES (1, 96, '牛奶');
INSERT INTO `good_store` VALUES (2, 20, '面包');
INSERT INTO `good_store` VALUES (3, 150, '苹果');

-- ----------------------------
-- Table structure for order_mark
-- ----------------------------
DROP TABLE IF EXISTS `order_mark`;
CREATE TABLE `order_mark`  (
  `order_id` int(0) NOT NULL,
  `time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of order_mark
-- ----------------------------
INSERT INTO `order_mark` VALUES (40220, '2021-06-01 16:11:57');
INSERT INTO `order_mark` VALUES (2975, '2021-06-01 16:11:58');

-- ----------------------------
-- Table structure for transaction_log
-- ----------------------------
DROP TABLE IF EXISTS `transaction_log`;
CREATE TABLE `transaction_log`  (
  `id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '事务ID',
  `business` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '业务标识',
  `foreign_key` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '对应业务表中的主键',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of transaction_log
-- ----------------------------
INSERT INTO `transaction_log` VALUES ('7F000001503018B4AAC203732CEB0000', 'Order', '40220');
INSERT INTO `transaction_log` VALUES ('7F000001613C18B4AAC2036BD59C0000', 'Order', '2975');

SET FOREIGN_KEY_CHECKS = 1;
