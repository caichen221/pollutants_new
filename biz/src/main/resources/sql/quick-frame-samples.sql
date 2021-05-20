/*
 Navicat Premium Data Transfer

 Source Server         : 192.168.100.88
 Source Server Type    : MySQL
 Source Server Version : 80022
 Source Host           : 192.168.100.88:3306
 Source Schema         : quick-frame-samples

 Target Server Type    : MySQL
 Target Server Version : 80022
 File Encoding         : 65001

 Date: 21/03/2021 10:48:17
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for dict_data
-- ----------------------------
DROP TABLE IF EXISTS `dict_data`;
CREATE TABLE `dict_data`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `dict_type` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '业务类',
  `dict_data_type` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `dict_label` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `dict_desc` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `create_time` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `create_by` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `update_time` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `update_by` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of dict_data
-- ----------------------------
INSERT INTO `dict_data` VALUES (1, '系统类', 'enum_test', '枚举测试', '测试枚举更新测试', '2021-03-01 17:29:29', 'unknown', '2021-03-01 17:31:06', 'unknown');
INSERT INTO `dict_data` VALUES (2, '业务类', 'user_status', '用户状态', '', '2021-03-05 12:12:03', 'admin', '2021-03-05 14:55:11', 'unknown');

-- ----------------------------
-- Table structure for dict_data_type
-- ----------------------------
DROP TABLE IF EXISTS `dict_data_type`;
CREATE TABLE `dict_data_type`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `dict_data_type` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `dict_data_key` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `dict_data_value` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `dict_data_desc` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `create_time` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `create_by` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `update_time` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `update_by` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 12 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of dict_data_type
-- ----------------------------
INSERT INTO `dict_data_type` VALUES (1, 'enum_test', '0', '无效', '无效状态11', '2021-03-01 17:40:24', 'unknown', '2021-03-05 11:09:53', 'unknown');
INSERT INTO `dict_data_type` VALUES (2, 'enum_test', '1', '有效', '有效状态', '2021-03-01 17:40:24', 'unknown', '', '');
INSERT INTO `dict_data_type` VALUES (4, 'user_status', '0', '无效', '无效用户', NULL, NULL, '2021-03-05 14:55:26', 'unknown');
INSERT INTO `dict_data_type` VALUES (5, 'user_status', '1', '有效', '有效用户', NULL, NULL, NULL, NULL);
INSERT INTO `dict_data_type` VALUES (6, 'enum_test', '12', '1', '1', '2021-03-05 14:21:39', 'unknown', '2021-03-05 14:26:23', 'unknown');
INSERT INTO `dict_data_type` VALUES (7, 'enum_test', '1', '1', '1', '2021-03-05 14:23:58', 'unknown', '2021-03-05 16:02:07', 'unknown');
INSERT INTO `dict_data_type` VALUES (9, 'enum_test', '3', '1', '3', '2021-03-05 15:29:40', 'unknown', '2021-03-05 16:01:59', 'unknown');
INSERT INTO `dict_data_type` VALUES (10, 'enum_test', '4', '4', '4', '2021-03-05 16:07:26', 'unknown', NULL, NULL);
INSERT INTO `dict_data_type` VALUES (11, 'enum_test', '5', '5', '5', '2021-03-05 16:07:40', 'unknown', NULL, NULL);

-- ----------------------------
-- Table structure for log_info
-- ----------------------------
DROP TABLE IF EXISTS `log_info`;
CREATE TABLE `log_info`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `log_type` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `request_url` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `operate_type` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `operate_user` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `log_desc` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `operate_time` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `request_took_time` int(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 36 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for menu
-- ----------------------------
DROP TABLE IF EXISTS `menu`;
CREATE TABLE `menu`  (
  `menu_id` int(0) NOT NULL AUTO_INCREMENT,
  `menu_pid` int(0) NULL DEFAULT NULL,
  `menu_page` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '前端的路径',
  `menu_create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `menu_update_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP COMMENT '最后修改时间',
  `menu_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`menu_id`) USING BTREE,
  UNIQUE INDEX `menu_name`(`menu_name`) USING BTREE,
  INDEX `menu_ibfk_1`(`menu_pid`) USING BTREE,
  CONSTRAINT `menu_ibfk_1` FOREIGN KEY (`menu_pid`) REFERENCES `menu` (`menu_id`) ON DELETE CASCADE ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 49 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of menu
-- ----------------------------
INSERT INTO `menu` VALUES (27, NULL, '/pages/index', NULL, '2021-03-11 12:25:01', '首页');
INSERT INTO `menu` VALUES (28, NULL, '/pages/systemmanage', NULL, '2021-03-11 13:47:14', '系统管理');
INSERT INTO `menu` VALUES (29, NULL, '/pages/systemmanagegateway', '2021-03-10 11:07:19', '2021-03-10 11:07:19', '原系统管理');
INSERT INTO `menu` VALUES (30, 28, '/pages/systemmanage/usermanage', NULL, '2021-03-11 13:47:26', '用户管理');
INSERT INTO `menu` VALUES (31, 28, '/pages/systemmanage/organizationmanage', NULL, '2021-03-11 13:26:58', '组织机构管理');
INSERT INTO `menu` VALUES (32, 28, '/pages/systemmanage/menumanage', NULL, '2021-03-11 13:27:35', '菜单管理');
INSERT INTO `menu` VALUES (33, 28, '/pages/systemmanage/rolemanage', NULL, '2021-03-11 13:28:05', '角色管理');
INSERT INTO `menu` VALUES (34, 28, '/pages/systemmanage/paramset', NULL, '2021-03-11 13:28:26', '参数设置');
INSERT INTO `menu` VALUES (35, 28, '/pages/systemmanage/dictionarymanage', NULL, '2021-03-11 13:28:43', '字典管理');
INSERT INTO `menu` VALUES (36, 28, '/pages/systemmanage/systemMonitor', NULL, '2021-03-11 13:29:01', '系统监控');
INSERT INTO `menu` VALUES (37, 36, '/pages/systemmanage/systemMonitor/physicalsource', NULL, '2021-03-11 13:29:25', '物理资源监控');
INSERT INTO `menu` VALUES (38, 36, '/pages/systemmanage/systemMonitor/jvmmonitor', '2021-03-10 11:30:11', '2021-03-10 11:30:11', '虚拟机监控');
INSERT INTO `menu` VALUES (39, 36, '/pages/systemmanage/systemMonitor/portDocument', NULL, '2021-03-11 13:31:02', '接口文档');
INSERT INTO `menu` VALUES (40, 36, '/pages/systemmanage/systemMonitor/DataMonitoring', '2021-03-10 11:30:50', '2021-03-10 11:30:50', '数据监控');
INSERT INTO `menu` VALUES (41, 36, '/pages/systemmanage/systemMonitor/systemLog', '2021-03-10 11:31:10', '2021-03-10 11:31:10', '系统日志');
INSERT INTO `menu` VALUES (42, 36, '/pages/systemmanage/systemMonitor/logManagement', '2021-03-10 11:31:28', '2021-03-10 11:31:28', '访问日志');
INSERT INTO `menu` VALUES (43, 36, '/pages/systemmanage/systemMonitor/WSController', '2021-03-10 11:31:46', '2021-03-10 11:31:46', '消息管理');
INSERT INTO `menu` VALUES (44, 29, '/pages/systemmanagegateway/usermanagegateway', NULL, '2021-03-10 11:33:21', '用户管理-网关');
INSERT INTO `menu` VALUES (45, 29, '/pages/systemmanagegateway/serviceconfiggateway', '2021-03-10 11:33:38', '2021-03-10 11:33:38', '服务配置-网关');
INSERT INTO `menu` VALUES (46, 29, '/pages/systemmanagegateway/menumanagegateway', '2021-03-10 11:34:01', '2021-03-10 11:34:01', '菜单管理-网关');
INSERT INTO `menu` VALUES (47, 29, '/pages/systemmanagegateway/rolemanagegateway', '2021-03-10 11:34:18', '2021-03-10 11:34:18', '角色管理-网关');
INSERT INTO `menu` VALUES (48, 28, '/pages/systemmanage/monitoring/1', NULL, '2021-03-12 09:53:42', '纤维生产企业监测');

-- ----------------------------
-- Table structure for menu_opration
-- ----------------------------
DROP TABLE IF EXISTS `menu_opration`;
CREATE TABLE `menu_opration`  (
  `menu_id` int(0) NOT NULL,
  `op_id` int(0) NOT NULL,
  PRIMARY KEY (`menu_id`, `op_id`) USING BTREE,
  INDEX `menu_opration_ibfk_2`(`op_id`) USING BTREE,
  CONSTRAINT `menu_opration_ibfk_1` FOREIGN KEY (`menu_id`) REFERENCES `menu` (`menu_id`) ON DELETE CASCADE ON UPDATE RESTRICT,
  CONSTRAINT `menu_opration_ibfk_2` FOREIGN KEY (`op_id`) REFERENCES `opration` (`op_id`) ON DELETE CASCADE ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of menu_opration
-- ----------------------------
INSERT INTO `menu_opration` VALUES (28, 1);
INSERT INTO `menu_opration` VALUES (30, 1);
INSERT INTO `menu_opration` VALUES (31, 1);
INSERT INTO `menu_opration` VALUES (32, 1);
INSERT INTO `menu_opration` VALUES (33, 1);
INSERT INTO `menu_opration` VALUES (34, 1);
INSERT INTO `menu_opration` VALUES (35, 1);
INSERT INTO `menu_opration` VALUES (36, 1);
INSERT INTO `menu_opration` VALUES (37, 1);
INSERT INTO `menu_opration` VALUES (39, 1);
INSERT INTO `menu_opration` VALUES (48, 1);
INSERT INTO `menu_opration` VALUES (28, 2);
INSERT INTO `menu_opration` VALUES (30, 2);
INSERT INTO `menu_opration` VALUES (31, 2);
INSERT INTO `menu_opration` VALUES (32, 2);
INSERT INTO `menu_opration` VALUES (33, 2);
INSERT INTO `menu_opration` VALUES (34, 2);
INSERT INTO `menu_opration` VALUES (35, 2);
INSERT INTO `menu_opration` VALUES (36, 2);
INSERT INTO `menu_opration` VALUES (39, 2);
INSERT INTO `menu_opration` VALUES (48, 2);
INSERT INTO `menu_opration` VALUES (28, 3);
INSERT INTO `menu_opration` VALUES (30, 3);
INSERT INTO `menu_opration` VALUES (31, 3);
INSERT INTO `menu_opration` VALUES (32, 3);
INSERT INTO `menu_opration` VALUES (33, 3);
INSERT INTO `menu_opration` VALUES (34, 3);
INSERT INTO `menu_opration` VALUES (35, 3);
INSERT INTO `menu_opration` VALUES (36, 3);
INSERT INTO `menu_opration` VALUES (37, 3);
INSERT INTO `menu_opration` VALUES (39, 3);
INSERT INTO `menu_opration` VALUES (28, 4);
INSERT INTO `menu_opration` VALUES (30, 4);
INSERT INTO `menu_opration` VALUES (31, 4);
INSERT INTO `menu_opration` VALUES (32, 4);
INSERT INTO `menu_opration` VALUES (33, 4);
INSERT INTO `menu_opration` VALUES (34, 4);
INSERT INTO `menu_opration` VALUES (35, 4);
INSERT INTO `menu_opration` VALUES (36, 4);
INSERT INTO `menu_opration` VALUES (39, 4);
INSERT INTO `menu_opration` VALUES (28, 5);
INSERT INTO `menu_opration` VALUES (30, 5);
INSERT INTO `menu_opration` VALUES (31, 5);
INSERT INTO `menu_opration` VALUES (32, 5);
INSERT INTO `menu_opration` VALUES (33, 5);
INSERT INTO `menu_opration` VALUES (34, 5);
INSERT INTO `menu_opration` VALUES (35, 5);
INSERT INTO `menu_opration` VALUES (36, 5);
INSERT INTO `menu_opration` VALUES (37, 5);
INSERT INTO `menu_opration` VALUES (39, 5);
INSERT INTO `menu_opration` VALUES (28, 6);
INSERT INTO `menu_opration` VALUES (30, 6);
INSERT INTO `menu_opration` VALUES (31, 6);
INSERT INTO `menu_opration` VALUES (32, 6);
INSERT INTO `menu_opration` VALUES (33, 6);
INSERT INTO `menu_opration` VALUES (34, 6);
INSERT INTO `menu_opration` VALUES (35, 6);
INSERT INTO `menu_opration` VALUES (36, 6);
INSERT INTO `menu_opration` VALUES (39, 6);
INSERT INTO `menu_opration` VALUES (28, 7);
INSERT INTO `menu_opration` VALUES (30, 7);
INSERT INTO `menu_opration` VALUES (31, 7);
INSERT INTO `menu_opration` VALUES (32, 7);
INSERT INTO `menu_opration` VALUES (33, 7);
INSERT INTO `menu_opration` VALUES (34, 7);
INSERT INTO `menu_opration` VALUES (35, 7);
INSERT INTO `menu_opration` VALUES (36, 7);
INSERT INTO `menu_opration` VALUES (37, 7);
INSERT INTO `menu_opration` VALUES (39, 7);
INSERT INTO `menu_opration` VALUES (28, 8);
INSERT INTO `menu_opration` VALUES (30, 8);
INSERT INTO `menu_opration` VALUES (31, 8);
INSERT INTO `menu_opration` VALUES (32, 8);
INSERT INTO `menu_opration` VALUES (33, 8);
INSERT INTO `menu_opration` VALUES (34, 8);
INSERT INTO `menu_opration` VALUES (35, 8);
INSERT INTO `menu_opration` VALUES (36, 8);
INSERT INTO `menu_opration` VALUES (39, 8);
INSERT INTO `menu_opration` VALUES (28, 9);
INSERT INTO `menu_opration` VALUES (30, 9);
INSERT INTO `menu_opration` VALUES (31, 9);
INSERT INTO `menu_opration` VALUES (32, 9);
INSERT INTO `menu_opration` VALUES (33, 9);
INSERT INTO `menu_opration` VALUES (34, 9);
INSERT INTO `menu_opration` VALUES (35, 9);
INSERT INTO `menu_opration` VALUES (36, 9);
INSERT INTO `menu_opration` VALUES (39, 9);
INSERT INTO `menu_opration` VALUES (28, 10);
INSERT INTO `menu_opration` VALUES (30, 10);
INSERT INTO `menu_opration` VALUES (31, 10);
INSERT INTO `menu_opration` VALUES (32, 10);
INSERT INTO `menu_opration` VALUES (33, 10);
INSERT INTO `menu_opration` VALUES (34, 10);
INSERT INTO `menu_opration` VALUES (35, 10);
INSERT INTO `menu_opration` VALUES (36, 10);
INSERT INTO `menu_opration` VALUES (37, 10);
INSERT INTO `menu_opration` VALUES (39, 10);
INSERT INTO `menu_opration` VALUES (28, 11);
INSERT INTO `menu_opration` VALUES (30, 11);
INSERT INTO `menu_opration` VALUES (31, 11);
INSERT INTO `menu_opration` VALUES (32, 11);
INSERT INTO `menu_opration` VALUES (33, 11);
INSERT INTO `menu_opration` VALUES (34, 11);
INSERT INTO `menu_opration` VALUES (35, 11);
INSERT INTO `menu_opration` VALUES (36, 11);
INSERT INTO `menu_opration` VALUES (37, 11);
INSERT INTO `menu_opration` VALUES (39, 11);
INSERT INTO `menu_opration` VALUES (28, 12);
INSERT INTO `menu_opration` VALUES (30, 12);
INSERT INTO `menu_opration` VALUES (31, 12);
INSERT INTO `menu_opration` VALUES (32, 12);
INSERT INTO `menu_opration` VALUES (33, 12);
INSERT INTO `menu_opration` VALUES (34, 12);
INSERT INTO `menu_opration` VALUES (35, 12);
INSERT INTO `menu_opration` VALUES (36, 12);
INSERT INTO `menu_opration` VALUES (39, 12);
INSERT INTO `menu_opration` VALUES (28, 13);
INSERT INTO `menu_opration` VALUES (30, 13);
INSERT INTO `menu_opration` VALUES (31, 13);
INSERT INTO `menu_opration` VALUES (32, 13);
INSERT INTO `menu_opration` VALUES (33, 13);
INSERT INTO `menu_opration` VALUES (34, 13);
INSERT INTO `menu_opration` VALUES (35, 13);
INSERT INTO `menu_opration` VALUES (36, 13);
INSERT INTO `menu_opration` VALUES (37, 13);
INSERT INTO `menu_opration` VALUES (39, 13);
INSERT INTO `menu_opration` VALUES (28, 14);
INSERT INTO `menu_opration` VALUES (30, 14);
INSERT INTO `menu_opration` VALUES (31, 14);
INSERT INTO `menu_opration` VALUES (32, 14);
INSERT INTO `menu_opration` VALUES (33, 14);
INSERT INTO `menu_opration` VALUES (34, 14);
INSERT INTO `menu_opration` VALUES (35, 14);
INSERT INTO `menu_opration` VALUES (36, 14);
INSERT INTO `menu_opration` VALUES (39, 14);

-- ----------------------------
-- Table structure for opration
-- ----------------------------
DROP TABLE IF EXISTS `opration`;
CREATE TABLE `opration`  (
  `op_id` int(0) NOT NULL AUTO_INCREMENT,
  `op_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '操作名称',
  `op_create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `op_update_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '最后修改时间',
  PRIMARY KEY (`op_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 15 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of opration
-- ----------------------------
INSERT INTO `opration` VALUES (1, '菜单管理-查看', '2021-02-22 17:52:23', '2021-02-22 17:52:23');
INSERT INTO `opration` VALUES (2, '菜单管理-编辑', '2021-02-22 17:57:44', '2021-02-22 17:57:44');
INSERT INTO `opration` VALUES (3, '用户管理-查看', '2021-02-22 17:57:53', '2021-02-22 17:57:53');
INSERT INTO `opration` VALUES (4, '用户管理-编辑', '2021-02-22 17:58:01', '2021-02-22 17:58:01');
INSERT INTO `opration` VALUES (5, '组织机构管理-查看', '2021-02-22 17:58:14', '2021-02-22 17:58:14');
INSERT INTO `opration` VALUES (6, '组织机构管理-编辑', '2021-02-22 17:58:22', '2021-02-22 17:58:22');
INSERT INTO `opration` VALUES (7, '角色管理-查看', '2021-02-22 17:58:31', '2021-02-22 17:58:31');
INSERT INTO `opration` VALUES (8, '角色管理-编辑', '2021-02-22 17:58:42', '2021-02-22 17:58:42');
INSERT INTO `opration` VALUES (9, '参数管理-编辑', '2021-03-10 09:43:10', '2021-03-10 09:43:10');
INSERT INTO `opration` VALUES (10, '参数管理-查看', '2021-03-10 09:43:27', '2021-03-10 09:43:27');
INSERT INTO `opration` VALUES (11, '字典管理-查看', '2021-03-10 09:52:43', '2021-03-10 09:52:43');
INSERT INTO `opration` VALUES (12, '字典管理-编辑', '2021-03-10 09:52:45', '2021-03-10 09:52:45');
INSERT INTO `opration` VALUES (13, '字典类型管理-查看', '2021-03-10 09:52:52', '2021-03-10 09:52:52');
INSERT INTO `opration` VALUES (14, '字典类型管理-编辑', '2021-03-10 09:53:03', '2021-03-10 09:53:03');

-- ----------------------------
-- Table structure for opration_resource
-- ----------------------------
DROP TABLE IF EXISTS `opration_resource`;
CREATE TABLE `opration_resource`  (
  `op_id` int(0) NOT NULL,
  `resource_id` int(0) NOT NULL,
  PRIMARY KEY (`op_id`, `resource_id`) USING BTREE,
  INDEX `opration_resource_ibfk_2`(`resource_id`) USING BTREE,
  CONSTRAINT `opration_resource_ibfk_1` FOREIGN KEY (`op_id`) REFERENCES `opration` (`op_id`) ON DELETE CASCADE ON UPDATE RESTRICT,
  CONSTRAINT `opration_resource_ibfk_2` FOREIGN KEY (`resource_id`) REFERENCES `resource` (`resource_id`) ON DELETE CASCADE ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of opration_resource
-- ----------------------------
INSERT INTO `opration_resource` VALUES (1, 1);
INSERT INTO `opration_resource` VALUES (2, 2);
INSERT INTO `opration_resource` VALUES (3, 3);
INSERT INTO `opration_resource` VALUES (4, 4);
INSERT INTO `opration_resource` VALUES (4, 5);
INSERT INTO `opration_resource` VALUES (5, 6);
INSERT INTO `opration_resource` VALUES (6, 7);
INSERT INTO `opration_resource` VALUES (7, 8);
INSERT INTO `opration_resource` VALUES (3, 9);
INSERT INTO `opration_resource` VALUES (7, 10);
INSERT INTO `opration_resource` VALUES (8, 11);
INSERT INTO `opration_resource` VALUES (8, 12);
INSERT INTO `opration_resource` VALUES (10, 13);
INSERT INTO `opration_resource` VALUES (10, 14);
INSERT INTO `opration_resource` VALUES (9, 15);
INSERT INTO `opration_resource` VALUES (9, 16);
INSERT INTO `opration_resource` VALUES (11, 17);
INSERT INTO `opration_resource` VALUES (11, 18);
INSERT INTO `opration_resource` VALUES (12, 19);
INSERT INTO `opration_resource` VALUES (12, 20);
INSERT INTO `opration_resource` VALUES (13, 21);
INSERT INTO `opration_resource` VALUES (13, 22);
INSERT INTO `opration_resource` VALUES (14, 23);
INSERT INTO `opration_resource` VALUES (14, 24);

-- ----------------------------
-- Table structure for org
-- ----------------------------
DROP TABLE IF EXISTS `org`;
CREATE TABLE `org`  (
  `org_id` int(0) NOT NULL AUTO_INCREMENT,
  `org_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '组织机构名称',
  `org_pid` int(0) NULL DEFAULT NULL COMMENT '上级组织机构',
  `org_desc` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '组织机构描述',
  `org_create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `org_update_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '最后修改时间',
  PRIMARY KEY (`org_id`) USING BTREE,
  UNIQUE INDEX `org_name`(`org_name`) USING BTREE,
  INDEX `org_ibfk_1`(`org_pid`) USING BTREE,
  CONSTRAINT `org_ibfk_1` FOREIGN KEY (`org_pid`) REFERENCES `org` (`org_id`) ON DELETE CASCADE ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 38 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of org
-- ----------------------------
INSERT INTO `org` VALUES (3, '软件所', NULL, NULL, NULL, '2021-03-11 13:40:52');
INSERT INTO `org` VALUES (4, '天基实验室', 3, '天基综合信息实验室', NULL, '2021-03-11 13:40:42');
INSERT INTO `org` VALUES (6, '二部', 4, NULL, NULL, '2021-03-11 13:40:59');
INSERT INTO `org` VALUES (7, '三部', 4, NULL, NULL, '2021-03-11 13:41:33');
INSERT INTO `org` VALUES (8, '推演组', 7, NULL, NULL, '2021-03-11 13:41:40');
INSERT INTO `org` VALUES (9, '大数据智能应用', 7, NULL, NULL, '2021-03-11 13:41:46');
INSERT INTO `org` VALUES (10, '激光方针组', 7, NULL, NULL, '2021-03-11 13:41:53');
INSERT INTO `org` VALUES (14, '组1', 7, '111', NULL, '2021-03-11 13:42:06');
INSERT INTO `org` VALUES (21, '组yyyy', 14, 'w333', '2021-02-21 13:37:52', '2021-02-21 13:37:52');
INSERT INTO `org` VALUES (22, '组zzzz', 14, 'w333', '2021-02-21 13:38:20', '2021-02-21 13:38:20');
INSERT INTO `org` VALUES (23, '组mmmm', 14, 'w333', '2021-02-21 13:39:32', '2021-02-21 13:39:32');
INSERT INTO `org` VALUES (24, '组nnnn', 14, 'w333', '2021-02-21 13:39:57', '2021-02-21 13:39:57');
INSERT INTO `org` VALUES (25, '组aaa', 14, 'w333', '2021-02-21 13:40:25', '2021-02-21 13:40:25');
INSERT INTO `org` VALUES (26, '组bbb', 14, 'w333', '2021-02-21 13:42:54', '2021-02-21 13:42:54');
INSERT INTO `org` VALUES (27, '组ccc', 14, 'w333', '2021-02-21 13:44:24', '2021-02-21 13:44:24');
INSERT INTO `org` VALUES (28, '组ddd', 14, 'w333', '2021-02-21 13:45:15', '2021-02-21 13:45:15');
INSERT INTO `org` VALUES (29, '组eeee', 14, 'w333', '2021-02-21 13:49:41', '2021-02-21 13:49:41');
INSERT INTO `org` VALUES (30, '组ffff', 14, 'w333', '2021-02-21 13:51:24', '2021-02-21 13:51:24');
INSERT INTO `org` VALUES (31, '组hhh', 7, 'w333', NULL, '2021-02-21 13:56:32');
INSERT INTO `org` VALUES (32, '测试数据修改', 6, NULL, NULL, '2021-02-24 11:31:14');
INSERT INTO `org` VALUES (35, '一部', 4, '11', NULL, '2021-03-11 13:42:12');
INSERT INTO `org` VALUES (36, '组gggg', 14, 'w333', '2021-02-26 10:10:36', '2021-02-26 10:10:36');
INSERT INTO `org` VALUES (37, '组xxx', NULL, '测试描述', NULL, '2021-03-09 13:43:01');

-- ----------------------------
-- Table structure for org_role
-- ----------------------------
DROP TABLE IF EXISTS `org_role`;
CREATE TABLE `org_role`  (
  `org_id` int(0) NOT NULL,
  `role_id` int(0) NOT NULL,
  PRIMARY KEY (`org_id`, `role_id`) USING BTREE,
  INDEX `org_role_ibfk_2`(`role_id`) USING BTREE,
  CONSTRAINT `org_role_ibfk_1` FOREIGN KEY (`org_id`) REFERENCES `org` (`org_id`) ON DELETE CASCADE ON UPDATE RESTRICT,
  CONSTRAINT `org_role_ibfk_2` FOREIGN KEY (`role_id`) REFERENCES `role` (`role_id`) ON DELETE CASCADE ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of org_role
-- ----------------------------
INSERT INTO `org_role` VALUES (3, 2);
INSERT INTO `org_role` VALUES (4, 2);
INSERT INTO `org_role` VALUES (6, 2);
INSERT INTO `org_role` VALUES (7, 2);
INSERT INTO `org_role` VALUES (8, 2);
INSERT INTO `org_role` VALUES (9, 2);
INSERT INTO `org_role` VALUES (10, 2);
INSERT INTO `org_role` VALUES (14, 2);
INSERT INTO `org_role` VALUES (32, 2);
INSERT INTO `org_role` VALUES (3, 4);
INSERT INTO `org_role` VALUES (4, 4);
INSERT INTO `org_role` VALUES (6, 4);
INSERT INTO `org_role` VALUES (7, 4);
INSERT INTO `org_role` VALUES (8, 4);
INSERT INTO `org_role` VALUES (9, 4);
INSERT INTO `org_role` VALUES (10, 4);
INSERT INTO `org_role` VALUES (14, 4);
INSERT INTO `org_role` VALUES (37, 4);
INSERT INTO `org_role` VALUES (3, 5);
INSERT INTO `org_role` VALUES (4, 5);
INSERT INTO `org_role` VALUES (6, 5);
INSERT INTO `org_role` VALUES (7, 5);
INSERT INTO `org_role` VALUES (8, 5);
INSERT INTO `org_role` VALUES (9, 5);
INSERT INTO `org_role` VALUES (10, 5);
INSERT INTO `org_role` VALUES (14, 5);
INSERT INTO `org_role` VALUES (35, 5);
INSERT INTO `org_role` VALUES (37, 5);
INSERT INTO `org_role` VALUES (3, 7);
INSERT INTO `org_role` VALUES (4, 7);
INSERT INTO `org_role` VALUES (6, 7);
INSERT INTO `org_role` VALUES (7, 7);
INSERT INTO `org_role` VALUES (8, 7);
INSERT INTO `org_role` VALUES (9, 7);
INSERT INTO `org_role` VALUES (10, 7);
INSERT INTO `org_role` VALUES (14, 7);
INSERT INTO `org_role` VALUES (3, 11);
INSERT INTO `org_role` VALUES (4, 11);
INSERT INTO `org_role` VALUES (6, 11);
INSERT INTO `org_role` VALUES (7, 11);
INSERT INTO `org_role` VALUES (8, 11);
INSERT INTO `org_role` VALUES (9, 11);
INSERT INTO `org_role` VALUES (10, 11);
INSERT INTO `org_role` VALUES (14, 11);

-- ----------------------------
-- Table structure for org_user
-- ----------------------------
DROP TABLE IF EXISTS `org_user`;
CREATE TABLE `org_user`  (
  `org_id` int(0) NOT NULL,
  `user_id` int(0) NOT NULL,
  PRIMARY KEY (`org_id`, `user_id`) USING BTREE,
  INDEX `org_user_ibfk_2`(`user_id`) USING BTREE,
  CONSTRAINT `org_user_ibfk_1` FOREIGN KEY (`org_id`) REFERENCES `org` (`org_id`) ON DELETE CASCADE ON UPDATE RESTRICT,
  CONSTRAINT `org_user_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE CASCADE ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of org_user
-- ----------------------------
INSERT INTO `org_user` VALUES (9, 20);
INSERT INTO `org_user` VALUES (9, 23);
INSERT INTO `org_user` VALUES (35, 28);
INSERT INTO `org_user` VALUES (14, 39);
INSERT INTO `org_user` VALUES (9, 40);
INSERT INTO `org_user` VALUES (35, 41);
INSERT INTO `org_user` VALUES (32, 42);
INSERT INTO `org_user` VALUES (8, 46);

-- ----------------------------
-- Table structure for param
-- ----------------------------
DROP TABLE IF EXISTS `param`;
CREATE TABLE `param`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `param_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `param_key` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `param_value` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `param_type` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '业务类',
  `create_by` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `create_time` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `update_by` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `update_time` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `param_desc` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of param
-- ----------------------------
INSERT INTO `param` VALUES (1, '系统监控-访问日志-保留时长', 'sys.log.holdPeriod', '1d', '系统类', 'admin', '2021-02-26 10:51:57', 'unknown', '2021-02-26 14:43:18', '根据该参数清除之前的访问日志数据');
INSERT INTO `param` VALUES (5, '系统监控-测试', 'sys.test.a', 'test', '系统类', 'test', '2021-02-26 15:31:33', 'admin', '2021-03-05 10:37:19', '根据该参数清除之前的访问日志数据');
INSERT INTO `param` VALUES (6, '系统监控-访问日志-hello', 'sys.test.aaa', 'hello iscas', '业务类', 'unknown', '2021-03-01 10:18:48', 'unknown', '2021-03-01 13:42:13', 'hello world');
INSERT INTO `param` VALUES (10, '测试数据', '12222', '12', '业务类', 'unknown', '2021-03-05 14:02:57', 'unknown', '2021-03-05 14:03:03', '111');

-- ----------------------------
-- Table structure for resource
-- ----------------------------
DROP TABLE IF EXISTS `resource`;
CREATE TABLE `resource`  (
  `resource_id` int(0) NOT NULL AUTO_INCREMENT COMMENT '资源ID',
  `resource_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '后端URL',
  `resource_desc` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '资源描述',
  `resource_create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `resource_update_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '最后修改时间',
  PRIMARY KEY (`resource_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 25 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of resource
-- ----------------------------
INSERT INTO `resource` VALUES (1, '/menu', '获取菜单树', '2021-02-22 17:49:58', '2021-02-22 17:51:39');
INSERT INTO `resource` VALUES (2, '/menu/node/*', '编辑菜单', '2021-02-22 17:50:39', '2021-02-22 17:54:52');
INSERT INTO `resource` VALUES (3, '/user/header', '获取用户表头', '2021-02-22 17:53:52', '2021-02-22 17:53:52');
INSERT INTO `resource` VALUES (4, '/user/del', '删除用户', '2021-02-22 17:54:15', '2021-02-22 17:54:34');
INSERT INTO `resource` VALUES (5, '/user/data', '编辑用户', '2021-02-22 17:54:32', '2021-02-22 17:54:32');
INSERT INTO `resource` VALUES (6, '/org', '获取组织机构树', '2021-02-22 17:55:09', '2021-02-22 17:55:09');
INSERT INTO `resource` VALUES (7, '/org/node/*', '编辑组织机构', '2021-02-22 17:55:30', '2021-02-22 17:55:30');
INSERT INTO `resource` VALUES (8, '/role/header', '获取角色表头', '2021-02-22 17:56:20', '2021-02-22 17:56:20');
INSERT INTO `resource` VALUES (9, '/user', '获取用户', '2021-02-22 17:56:36', '2021-02-22 17:56:36');
INSERT INTO `resource` VALUES (10, '/role', '获取角色', '2021-02-22 17:56:54', '2021-02-22 17:56:54');
INSERT INTO `resource` VALUES (11, '/role/del', '删除角色', '2021-02-22 17:57:11', '2021-02-22 17:57:11');
INSERT INTO `resource` VALUES (12, '/role/data', '编辑角色', '2021-02-22 17:57:25', '2021-02-22 17:57:25');
INSERT INTO `resource` VALUES (13, '/param/getHeader', '获取参数表头', '2021-03-10 09:37:43', '2021-03-10 09:48:38');
INSERT INTO `resource` VALUES (14, '/param/getData', '获取参数', '2021-03-10 09:38:21', '2021-03-10 09:48:46');
INSERT INTO `resource` VALUES (15, '/param/del', '删除参数', '2021-03-10 09:38:48', '2021-03-10 09:38:48');
INSERT INTO `resource` VALUES (16, '/param/data', '编辑参数', '2021-03-10 09:39:19', '2021-03-10 09:39:19');
INSERT INTO `resource` VALUES (17, '/dictData/getHeader', '获取字典数据表头', '2021-03-10 09:46:00', '2021-03-10 09:50:08');
INSERT INTO `resource` VALUES (18, '/dictData/getData', '获字典数据', '2021-03-10 09:46:04', '2021-03-10 09:50:10');
INSERT INTO `resource` VALUES (19, '/dictData/del', '删除字典数据', '2021-03-10 09:46:09', '2021-03-10 09:50:12');
INSERT INTO `resource` VALUES (20, '/dictData/data', '编辑字典数据', '2021-03-10 09:46:14', '2021-03-10 09:50:15');
INSERT INTO `resource` VALUES (21, '/dictDataType/getHeader', '获取字典类型数据表头', '2021-03-10 09:46:00', '2021-03-10 09:50:08');
INSERT INTO `resource` VALUES (22, '/dictDataType/getData', '获字典类型数据', '2021-03-10 09:46:04', '2021-03-10 09:50:10');
INSERT INTO `resource` VALUES (23, '/dictDataType/del', '删除字典类型数据', '2021-03-10 09:46:09', '2021-03-10 09:50:12');
INSERT INTO `resource` VALUES (24, '/dictDataType/data', '编辑字典类型数据', '2021-03-10 09:46:14', '2021-03-10 09:50:15');

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role`  (
  `role_id` int(0) NOT NULL AUTO_INCREMENT,
  `role_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '角色名称',
  `role_create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP,
  `role_update_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`role_id`) USING BTREE,
  UNIQUE INDEX `role_name`(`role_name`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 12 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES (2, '测试角色1', '2021-02-22 10:23:19', '2021-02-25 09:29:22');
INSERT INTO `role` VALUES (4, '普通用户11', '2021-02-22 14:35:01', '2021-02-25 09:29:22');
INSERT INTO `role` VALUES (5, '普通用户12', '2021-02-22 14:35:20', '2021-02-25 09:29:23');
INSERT INTO `role` VALUES (7, '普通用户13', '2021-02-22 14:41:27', '2021-02-25 09:29:24');
INSERT INTO `role` VALUES (11, 'super', NULL, NULL);

-- ----------------------------
-- Table structure for role_menu
-- ----------------------------
DROP TABLE IF EXISTS `role_menu`;
CREATE TABLE `role_menu`  (
  `role_id` int(0) NOT NULL,
  `menu_id` int(0) NOT NULL,
  PRIMARY KEY (`role_id`, `menu_id`) USING BTREE,
  INDEX `role_menu_ibfk_2`(`menu_id`) USING BTREE,
  CONSTRAINT `role_menu_ibfk_1` FOREIGN KEY (`role_id`) REFERENCES `role` (`role_id`) ON DELETE CASCADE ON UPDATE RESTRICT,
  CONSTRAINT `role_menu_ibfk_2` FOREIGN KEY (`menu_id`) REFERENCES `menu` (`menu_id`) ON DELETE CASCADE ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of role_menu
-- ----------------------------
INSERT INTO `role_menu` VALUES (11, 27);
INSERT INTO `role_menu` VALUES (2, 28);
INSERT INTO `role_menu` VALUES (4, 28);
INSERT INTO `role_menu` VALUES (11, 28);
INSERT INTO `role_menu` VALUES (11, 29);
INSERT INTO `role_menu` VALUES (2, 30);
INSERT INTO `role_menu` VALUES (4, 30);
INSERT INTO `role_menu` VALUES (11, 30);
INSERT INTO `role_menu` VALUES (11, 31);
INSERT INTO `role_menu` VALUES (4, 32);
INSERT INTO `role_menu` VALUES (11, 32);
INSERT INTO `role_menu` VALUES (4, 33);
INSERT INTO `role_menu` VALUES (11, 33);
INSERT INTO `role_menu` VALUES (4, 34);
INSERT INTO `role_menu` VALUES (11, 34);
INSERT INTO `role_menu` VALUES (4, 35);
INSERT INTO `role_menu` VALUES (11, 35);
INSERT INTO `role_menu` VALUES (4, 36);
INSERT INTO `role_menu` VALUES (5, 36);
INSERT INTO `role_menu` VALUES (11, 36);
INSERT INTO `role_menu` VALUES (2, 37);
INSERT INTO `role_menu` VALUES (4, 37);
INSERT INTO `role_menu` VALUES (5, 37);
INSERT INTO `role_menu` VALUES (11, 37);
INSERT INTO `role_menu` VALUES (4, 38);
INSERT INTO `role_menu` VALUES (5, 38);
INSERT INTO `role_menu` VALUES (11, 38);
INSERT INTO `role_menu` VALUES (4, 39);
INSERT INTO `role_menu` VALUES (5, 39);
INSERT INTO `role_menu` VALUES (11, 39);
INSERT INTO `role_menu` VALUES (4, 40);
INSERT INTO `role_menu` VALUES (11, 40);
INSERT INTO `role_menu` VALUES (4, 41);
INSERT INTO `role_menu` VALUES (5, 41);
INSERT INTO `role_menu` VALUES (11, 41);
INSERT INTO `role_menu` VALUES (4, 42);
INSERT INTO `role_menu` VALUES (5, 42);
INSERT INTO `role_menu` VALUES (11, 42);
INSERT INTO `role_menu` VALUES (4, 43);
INSERT INTO `role_menu` VALUES (5, 43);
INSERT INTO `role_menu` VALUES (11, 43);
INSERT INTO `role_menu` VALUES (11, 44);
INSERT INTO `role_menu` VALUES (11, 45);
INSERT INTO `role_menu` VALUES (11, 46);
INSERT INTO `role_menu` VALUES (11, 47);
INSERT INTO `role_menu` VALUES (11, 48);

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `user_id` int(0) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `user_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户名',
  `user_real_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '真实姓名',
  `user_pwd` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '密码',
  `user_tel` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '电话号码',
  `user_email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '邮箱',
  `user_status` smallint(0) NULL DEFAULT 1 COMMENT '0 停用 1 启用',
  `user_create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `user_update_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '最后修改时间',
  PRIMARY KEY (`user_id`) USING BTREE,
  UNIQUE INDEX `user_name`(`user_name`) USING BTREE,
  INDEX `user_ibfk_1`(`user_status`) USING BTREE,
  CONSTRAINT `user_ibfk_1` FOREIGN KEY (`user_status`) REFERENCES `user_status` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 48 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, 'zhangsan', '张三', '763f2de02b9028330f49663c392377a9b01f02279f49a864', '13879145689', '', 1, '2021-02-22 14:49:26', '2021-03-10 10:57:08');
INSERT INTO `user` VALUES (17, 'zhaoliu', '赵六3', 'f4b11258c837f1df56532362485d3287a43ba19b78505731', '15867890043', '12334@qq.com', 1, '2021-02-22 14:51:21', '2021-03-11 13:34:52');
INSERT INTO `user` VALUES (18, 'wangwu', '王五', '16ab61a92562b46b2959370c76fd28b9247692314f70d76f', '13879145689', '123@qq.com', 1, '2021-02-22 15:30:35', '2021-02-22 15:30:35');
INSERT INTO `user` VALUES (20, 'wangwu2', '王五2', '813299228e58646424a20b2115e306d88238469f24f1ac0e', '13879145689', '123@qq.com', 1, '2021-02-22 15:33:08', '2021-02-22 15:33:08');
INSERT INTO `user` VALUES (22, 'user1', '用户1', 'b8787f176263d95689f5f480e2b865b0a751b91d1632ba30', '158784579512', '12334@qq.com', 1, '2021-02-22 15:36:39', '2021-02-22 15:43:16');
INSERT INTO `user` VALUES (23, 'user2', '用户2', '06d388e18c2bc3da81b4609769617d64a594f60501a2ff1c', '15878457951', '12334@qq.com', 1, '2021-02-22 16:05:17', '2021-02-24 10:48:07');
INSERT INTO `user` VALUES (28, 'test1', '测试1', 'f6c58dc75423004f6c46bb7248d79424b743c41605859279', NULL, NULL, 1, '2021-02-24 15:33:35', '2021-02-24 15:33:35');
INSERT INTO `user` VALUES (34, 'admin', NULL, '139e7b60c568e39341379230b7493529e95b797534105a01', NULL, NULL, 1, '2021-02-25 13:24:36', '2021-02-25 13:24:36');
INSERT INTO `user` VALUES (35, 'wangwu8', '王五8', '88344550ed3225184e66742ce3416500ee9ab54194384645', '13879145689', '123@qq.com', 1, '2021-02-25 14:19:14', '2021-02-25 14:19:14');
INSERT INTO `user` VALUES (37, 'wangwu9', '王五9', 'a6270cc1820568c52949419056566fe9879a50a41b351094', '13879145689', '123@qq.com', 1, '2021-02-25 14:21:02', '2021-02-25 14:21:02');
INSERT INTO `user` VALUES (38, 'wangwu10', '王五10', '694698f7bb61e2bc67c0e396e6842e76fe4d317010c9562e', '13879145689', '123@qq.com', 1, '2021-02-25 14:21:42', '2021-02-25 14:21:42');
INSERT INTO `user` VALUES (39, 'wangwu12', '王五12', 'a8e724c9565262f43bf30f8fa3bd25e7d30b624762836695', '13879145689', '123@qq.com', 1, '2021-02-25 14:22:31', '2021-02-25 14:22:31');
INSERT INTO `user` VALUES (40, 'test', '测试数据', 'd8d765133e9462c36f280c4bb31a5cd9ff78621c0790a80a', NULL, NULL, 1, '2021-03-01 14:36:09', '2021-03-01 14:36:09');
INSERT INTO `user` VALUES (41, 'test5', 'test5', 'e3a129b3c24556d58112d77ec4725e12e73217460a96117c', NULL, NULL, 0, '2021-03-01 14:40:32', '2021-03-01 14:40:32');
INSERT INTO `user` VALUES (42, 'test1234', '测试', '525175b3fb3aa5c40f100e60806a87371236638968c0be04', '', 'lyr@163.com', 1, '2021-03-05 10:49:56', '2021-03-05 14:48:45');
INSERT INTO `user` VALUES (43, 'lisi02', 'aaa', '89b781950d41b35e17935961534357005f0e40413da22274', '18811451234', '1030355111@qq.com', 1, '2021-03-10 17:56:37', '2021-03-10 17:56:37');
INSERT INTO `user` VALUES (46, 'test22', '测试用户1', '967491e2fd67c8a75621865e927b6b970b3c08db1de4911f', '15263524152', '123456@163.com', 1, '2021-03-11 13:38:15', '2021-03-11 13:38:15');
INSERT INTO `user` VALUES (47, 'test33', '测试3', '852097269d9fc2cd69c85583527985b74f7874ea3205fb84', '18596857485', '123456@163.com', 1, '2021-03-11 13:43:54', '2021-03-11 13:43:54');

-- ----------------------------
-- Table structure for user_role
-- ----------------------------
DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role`  (
  `user_id` int(0) NOT NULL,
  `role_id` int(0) NOT NULL,
  PRIMARY KEY (`user_id`, `role_id`) USING BTREE,
  INDEX `user_role_ibfk_2`(`role_id`) USING BTREE,
  CONSTRAINT `user_role_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE CASCADE ON UPDATE RESTRICT,
  CONSTRAINT `user_role_ibfk_2` FOREIGN KEY (`role_id`) REFERENCES `role` (`role_id`) ON DELETE CASCADE ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_role
-- ----------------------------
INSERT INTO `user_role` VALUES (17, 2);
INSERT INTO `user_role` VALUES (20, 2);
INSERT INTO `user_role` VALUES (22, 2);
INSERT INTO `user_role` VALUES (23, 2);
INSERT INTO `user_role` VALUES (39, 2);
INSERT INTO `user_role` VALUES (46, 2);
INSERT INTO `user_role` VALUES (47, 2);
INSERT INTO `user_role` VALUES (1, 4);
INSERT INTO `user_role` VALUES (40, 4);
INSERT INTO `user_role` VALUES (43, 4);
INSERT INTO `user_role` VALUES (40, 5);
INSERT INTO `user_role` VALUES (41, 5);
INSERT INTO `user_role` VALUES (20, 7);
INSERT INTO `user_role` VALUES (28, 7);
INSERT INTO `user_role` VALUES (42, 7);
INSERT INTO `user_role` VALUES (46, 7);
INSERT INTO `user_role` VALUES (34, 11);

-- ----------------------------
-- Table structure for user_status
-- ----------------------------
DROP TABLE IF EXISTS `user_status`;
CREATE TABLE `user_status`  (
  `id` smallint(0) NOT NULL,
  `user_status_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_status
-- ----------------------------
INSERT INTO `user_status` VALUES (0, '不启用');
INSERT INTO `user_status` VALUES (1, '启用');

-- ----------------------------
-- Table structure for ws_data
-- ----------------------------
DROP TABLE IF EXISTS `ws_data`;
CREATE TABLE `ws_data`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `msg_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `user_identify` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `persistent` bit(1) NULL DEFAULT b'1',
  `data` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL,
  `destination` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `ack` bit(1) NULL DEFAULT b'0',
  `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2445 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of ws_data
-- ----------------------------
INSERT INTO `ws_data` VALUES (2245, 'BUSINESS', '55b9bb1d-7ad3-446e-9ad7-06af291db018', 'admin', b'1', '测试点对点数据，持久化1616048610005', '/queue/message', b'0', '2021-03-18 14:23:29');
INSERT INTO `ws_data` VALUES (2246, 'BUSINESS', '02ba07e6-d208-4511-9fc1-c19163f396b6', 'admin', b'1', '测试点对点数据，持久化1616048640003', '/queue/message', b'0', '2021-03-18 14:23:59');
INSERT INTO `ws_data` VALUES (2247, 'BUSINESS', '3d0de8fb-fb3e-42f1-b48e-9480278ea54a', 'admin', b'1', '测试点对点数据，持久化1616048670003', '/queue/message', b'0', '2021-03-18 14:24:29');
INSERT INTO `ws_data` VALUES (2248, 'BUSINESS', '4286237c-36c1-46fa-b0fd-374dde6f377e', 'admin', b'1', '测试点对点数据，持久化1616048700004', '/queue/message', b'0', '2021-03-18 14:24:59');
INSERT INTO `ws_data` VALUES (2249, 'BUSINESS', '15dbfa3f-225f-4d45-ba2d-9df9e2c1ba91', 'admin', b'1', '测试点对点数据，持久化1616048730002', '/queue/message', b'0', '2021-03-18 14:25:29');
INSERT INTO `ws_data` VALUES (2250, 'BUSINESS', 'd7720418-fc5d-4a2d-a129-5219b27c7499', 'admin', b'1', '测试点对点数据，持久化1616048760007', '/queue/message', b'0', '2021-03-18 14:25:59');
INSERT INTO `ws_data` VALUES (2251, 'BUSINESS', '20fa243f-227f-4a2f-9210-2dd1e46060d8', 'admin', b'1', '测试点对点数据，持久化1616048790003', '/queue/message', b'0', '2021-03-18 14:26:29');
INSERT INTO `ws_data` VALUES (2252, 'BUSINESS', '4b02579c-2a98-4431-b206-be8b079078e8', 'admin', b'1', '测试点对点数据，持久化1616048820005', '/queue/message', b'0', '2021-03-18 14:26:59');
INSERT INTO `ws_data` VALUES (2253, 'BUSINESS', '9c5dd3e7-8f90-4c10-afca-7e22c8cd0b90', 'admin', b'1', '测试点对点数据，持久化1616048850009', '/queue/message', b'0', '2021-03-18 14:27:29');
INSERT INTO `ws_data` VALUES (2254, 'BUSINESS', '5e5f6e0a-8073-4bb0-ba77-f980722b7457', 'admin', b'1', '测试点对点数据，持久化1616048880007', '/queue/message', b'0', '2021-03-18 14:27:59');
INSERT INTO `ws_data` VALUES (2255, 'BUSINESS', '68fc175c-367c-45b5-974a-1a2ef6c77fa1', 'admin', b'1', '测试点对点数据，持久化1616048910006', '/queue/message', b'0', '2021-03-18 14:28:29');
INSERT INTO `ws_data` VALUES (2256, 'BUSINESS', '19d8f472-6850-4b28-984a-5fd091e820c6', 'admin', b'1', '测试点对点数据，持久化1616048940007', '/queue/message', b'0', '2021-03-18 14:28:59');
INSERT INTO `ws_data` VALUES (2257, 'BUSINESS', 'c62e36e4-0917-4526-a04b-fe644013fd37', 'admin', b'1', '测试点对点数据，持久化1616048970009', '/queue/message', b'0', '2021-03-18 14:29:29');
INSERT INTO `ws_data` VALUES (2258, 'BUSINESS', '02804593-7a28-4aaf-be94-b94e3e79784f', 'admin', b'1', '测试点对点数据，持久化1616049000003', '/queue/message', b'0', '2021-03-18 14:29:59');
INSERT INTO `ws_data` VALUES (2259, 'BUSINESS', 'd29c7688-1ba0-4270-974c-cd7bd6922a67', 'admin', b'1', '测试点对点数据，持久化1616049030005', '/queue/message', b'0', '2021-03-18 14:30:29');
INSERT INTO `ws_data` VALUES (2260, 'BUSINESS', '2e03ac6a-b476-4b19-b400-393344a53f91', 'admin', b'1', '测试点对点数据，持久化1616049060004', '/queue/message', b'0', '2021-03-18 14:30:59');
INSERT INTO `ws_data` VALUES (2261, 'BUSINESS', '64737f86-1cac-4f02-a3be-f06e9fe7b347', 'admin', b'1', '测试点对点数据，持久化1616049090002', '/queue/message', b'0', '2021-03-18 14:31:29');
INSERT INTO `ws_data` VALUES (2262, 'BUSINESS', '4aa79249-294f-4fa2-abf9-51c85d6b929b', 'admin', b'1', '测试点对点数据，持久化1616049120005', '/queue/message', b'0', '2021-03-18 14:31:59');
INSERT INTO `ws_data` VALUES (2263, 'BUSINESS', 'bf7849d7-2eda-480e-8ec7-cc0d863e9652', 'admin', b'1', '测试点对点数据，持久化1616049150010', '/queue/message', b'0', '2021-03-18 14:32:29');
INSERT INTO `ws_data` VALUES (2264, 'BUSINESS', '9bc1d041-d254-41ec-9a7e-35822dd5f706', 'admin', b'1', '测试点对点数据，持久化1616049180004', '/queue/message', b'0', '2021-03-18 14:32:59');
INSERT INTO `ws_data` VALUES (2265, 'BUSINESS', 'd736ba4a-45af-40e2-8f40-99aa4fa578f1', 'admin', b'1', '测试点对点数据，持久化1616049210002', '/queue/message', b'0', '2021-03-18 14:33:29');
INSERT INTO `ws_data` VALUES (2266, 'BUSINESS', '45b2669e-fdd0-47bb-a7a4-0398d5a14d08', 'admin', b'1', '测试点对点数据，持久化1616049240003', '/queue/message', b'0', '2021-03-18 14:33:59');
INSERT INTO `ws_data` VALUES (2267, 'BUSINESS', '11359413-8372-481f-a367-586ffa234454', 'admin3', b'1', '服务器推送得数据', '/queue/message', b'0', '2021-03-18 14:34:10');
INSERT INTO `ws_data` VALUES (2268, 'BUSINESS', '1a97a052-d96a-407d-b15b-ce91f2fea5b9', 'admin', b'1', '测试点对点数据，持久化1616049270004', '/queue/message', b'0', '2021-03-18 14:34:29');
INSERT INTO `ws_data` VALUES (2269, 'BUSINESS', '8ba67d83-9500-49c4-9504-3edda57c8ad4', 'admin', b'1', '测试点对点数据，持久化1616049300003', '/queue/message', b'0', '2021-03-18 14:34:59');
INSERT INTO `ws_data` VALUES (2270, 'BUSINESS', 'dd37316c-d671-4770-95f5-6d4fcef1d8df', 'admin', b'1', '测试点对点数据，持久化1616049330003', '/queue/message', b'0', '2021-03-18 14:35:29');
INSERT INTO `ws_data` VALUES (2271, 'BUSINESS', '0f1e760d-9cfa-4508-804a-ffeeaa924f56', 'admin', b'1', '测试点对点数据，持久化1616049360005', '/queue/message', b'0', '2021-03-18 14:35:59');
INSERT INTO `ws_data` VALUES (2272, 'BUSINESS', '2469dfc2-1b7b-47c4-b027-851a0b4a59d8', 'admin', b'1', '测试点对点数据，持久化1616049390018', '/queue/message', b'0', '2021-03-18 14:36:29');
INSERT INTO `ws_data` VALUES (2273, 'BUSINESS', 'c0643bc6-f2e3-4ae1-87b6-81900b30593d', 'admin', b'1', '测试点对点数据，持久化1616049420004', '/queue/message', b'0', '2021-03-18 14:36:59');
INSERT INTO `ws_data` VALUES (2274, 'BUSINESS', '7de2259c-7458-4ebd-9c84-815f823962df', 'admin', b'1', '测试点对点数据，持久化1616049450004', '/queue/message', b'0', '2021-03-18 14:37:29');
INSERT INTO `ws_data` VALUES (2275, 'BUSINESS', '93e224c0-8cab-4cc3-8e3d-df0618bbfaf7', 'admin', b'1', '测试点对点数据，持久化1616049480004', '/queue/message', b'0', '2021-03-18 14:37:59');
INSERT INTO `ws_data` VALUES (2276, 'BUSINESS', 'a3839de4-2c2b-49cd-a64b-c6e6e2ceb86d', 'admin', b'1', '测试点对点数据，持久化1616049510004', '/queue/message', b'0', '2021-03-18 14:38:29');
INSERT INTO `ws_data` VALUES (2277, 'BUSINESS', '638b9b23-ed39-42b1-bc7a-9423bc22694b', 'admin', b'1', '测试点对点数据，持久化1616049540006', '/queue/message', b'0', '2021-03-18 14:38:59');
INSERT INTO `ws_data` VALUES (2278, 'BUSINESS', '7a8fd5a2-b6c4-4fe8-86bf-f712a0354d21', 'admin', b'1', '测试点对点数据，持久化1616049570007', '/queue/message', b'0', '2021-03-18 14:39:29');
INSERT INTO `ws_data` VALUES (2279, 'BUSINESS', 'a99e4bf7-5fd2-4460-af1a-1a0bf0d00f06', 'admin', b'1', '测试点对点数据，持久化1616049600004', '/queue/message', b'0', '2021-03-18 14:39:59');
INSERT INTO `ws_data` VALUES (2280, 'BUSINESS', '8e6b31e4-ec8a-4b2c-9f39-de70e3438150', 'admin', b'1', '测试点对点数据，持久化1616049630004', '/queue/message', b'0', '2021-03-18 14:40:29');
INSERT INTO `ws_data` VALUES (2281, 'BUSINESS', 'bf569f0c-b5e0-4d0a-8a9e-592f1e538cbc', 'admin', b'1', '测试点对点数据，持久化1616049660005', '/queue/message', b'0', '2021-03-18 14:40:59');
INSERT INTO `ws_data` VALUES (2282, 'BUSINESS', 'c3e0a1b6-9729-455a-ac7f-4bef4582d029', 'admin', b'1', '测试点对点数据，持久化1616049690006', '/queue/message', b'0', '2021-03-18 14:41:29');
INSERT INTO `ws_data` VALUES (2283, 'BUSINESS', '4a7062bb-d6b7-4e59-aec9-74415d76c0ee', 'admin', b'1', '测试点对点数据，持久化1616049720005', '/queue/message', b'0', '2021-03-18 14:41:59');
INSERT INTO `ws_data` VALUES (2284, 'BUSINESS', '4cde23c5-6c6c-44b5-a4cf-9c9efcc69016', 'admin', b'1', '测试点对点数据，持久化1616049750003', '/queue/message', b'0', '2021-03-18 14:42:29');
INSERT INTO `ws_data` VALUES (2285, 'BUSINESS', 'e8b3b4fc-4f94-48ee-9ffc-42932e80ec54', 'admin', b'1', '测试点对点数据，持久化1616049780007', '/queue/message', b'0', '2021-03-18 14:42:59');
INSERT INTO `ws_data` VALUES (2286, 'BUSINESS', '25e1ad34-9b61-45d0-b2a4-6c27c058e72f', 'admin', b'1', '测试点对点数据，持久化1616049810003', '/queue/message', b'0', '2021-03-18 14:43:29');
INSERT INTO `ws_data` VALUES (2287, 'BUSINESS', 'ea567c0f-f0d6-4b15-bca6-ee9aaf40d2f1', 'admin', b'1', '测试点对点数据，持久化1616049840004', '/queue/message', b'0', '2021-03-18 14:43:59');
INSERT INTO `ws_data` VALUES (2288, 'BUSINESS', '7ea35b94-fcfe-47e1-b750-3f20220d8436', 'admin', b'1', '测试点对点数据，持久化1616049870007', '/queue/message', b'0', '2021-03-18 14:44:29');
INSERT INTO `ws_data` VALUES (2289, 'BUSINESS', '8b1a8056-51ef-458a-bd9d-fe1fc3b0ddf6', 'admin', b'1', '测试点对点数据，持久化1616049900007', '/queue/message', b'0', '2021-03-18 14:44:59');
INSERT INTO `ws_data` VALUES (2290, 'BUSINESS', '64e2091e-6a61-4514-84ec-5b2cff92302a', 'admin', b'1', '测试点对点数据，持久化1616049930004', '/queue/message', b'0', '2021-03-18 14:45:29');
INSERT INTO `ws_data` VALUES (2291, 'BUSINESS', '2cca9cb1-b030-4c85-aacf-b563e81e8652', 'admin', b'1', '测试点对点数据，持久化1616049960006', '/queue/message', b'0', '2021-03-18 14:45:59');
INSERT INTO `ws_data` VALUES (2292, 'BUSINESS', '47749f7c-362a-4b10-ab56-1ee5899f3b9d', 'admin', b'1', '测试点对点数据，持久化1616049990003', '/queue/message', b'0', '2021-03-18 14:46:29');
INSERT INTO `ws_data` VALUES (2293, 'BUSINESS', '5be1c864-9b78-4003-991b-ba0702c84547', 'admin', b'1', '测试点对点数据，持久化1616050020007', '/queue/message', b'0', '2021-03-18 14:46:59');
INSERT INTO `ws_data` VALUES (2294, 'BUSINESS', 'c6865bfe-dc4d-4877-b77d-209e0f7173b0', 'admin', b'1', '测试点对点数据，持久化1616050050005', '/queue/message', b'0', '2021-03-18 14:47:29');
INSERT INTO `ws_data` VALUES (2295, 'BUSINESS', '94147166-c5c5-4fce-838e-bdf0ca994c08', 'admin', b'1', '测试点对点数据，持久化1616050080004', '/queue/message', b'0', '2021-03-18 14:47:59');
INSERT INTO `ws_data` VALUES (2296, 'BUSINESS', 'f507bea9-1783-44bd-bc30-b872cafdc4f0', 'admin', b'1', '测试点对点数据，持久化1616050110003', '/queue/message', b'0', '2021-03-18 14:48:29');
INSERT INTO `ws_data` VALUES (2297, 'BUSINESS', '3520cc5c-c3ba-4769-9bd3-5f2f8a578b04', 'admin', b'1', '测试点对点数据，持久化1616050140004', '/queue/message', b'0', '2021-03-18 14:48:59');
INSERT INTO `ws_data` VALUES (2298, 'BUSINESS', '0d6fb0e9-792d-4118-b73a-e6715707ac06', 'admin', b'1', '测试点对点数据，持久化1616050170005', '/queue/message', b'0', '2021-03-18 14:49:29');
INSERT INTO `ws_data` VALUES (2299, 'BUSINESS', 'f0b27b50-dc90-42d3-ada5-27026b82e666', 'admin', b'1', '测试点对点数据，持久化1616050200004', '/queue/message', b'0', '2021-03-18 14:49:59');
INSERT INTO `ws_data` VALUES (2300, 'BUSINESS', '5319d379-4b27-429a-983d-ea50ca762f6c', 'admin', b'1', '测试点对点数据，持久化1616050230006', '/queue/message', b'0', '2021-03-18 14:50:29');
INSERT INTO `ws_data` VALUES (2301, 'BUSINESS', '6499ca8e-5fe9-471e-b8c9-e72e83c86b76', 'admin', b'1', '测试点对点数据，持久化1616050260008', '/queue/message', b'0', '2021-03-18 14:50:59');
INSERT INTO `ws_data` VALUES (2302, 'BUSINESS', '968e84ad-4d0e-491d-a7e5-92f95f725de5', 'admin', b'1', '测试点对点数据，持久化1616050290003', '/queue/message', b'0', '2021-03-18 14:51:29');
INSERT INTO `ws_data` VALUES (2303, 'BUSINESS', 'fc18ce97-64f1-415c-b0d4-314d646c34cc', 'admin', b'1', '测试点对点数据，持久化1616050320006', '/queue/message', b'0', '2021-03-18 14:51:59');
INSERT INTO `ws_data` VALUES (2304, 'BUSINESS', 'cd78e61a-1f0c-4ac9-b4dd-9e003b87e93e', 'admin', b'1', '测试点对点数据，持久化1616050350004', '/queue/message', b'0', '2021-03-18 14:52:29');
INSERT INTO `ws_data` VALUES (2305, 'BUSINESS', '236a1b79-2287-4cb4-95a1-ca6e52493082', 'admin', b'1', '测试点对点数据，持久化1616050380003', '/queue/message', b'0', '2021-03-18 14:52:59');
INSERT INTO `ws_data` VALUES (2306, 'BUSINESS', 'ebc28972-2fe3-49de-90ff-ebf05344c1e9', 'admin', b'1', '测试点对点数据，持久化1616050410004', '/queue/message', b'0', '2021-03-18 14:53:29');
INSERT INTO `ws_data` VALUES (2307, 'BUSINESS', '9294cd0e-85cc-463c-bb80-6125d0662712', 'admin', b'1', '测试点对点数据，持久化1616050440005', '/queue/message', b'0', '2021-03-18 14:53:59');
INSERT INTO `ws_data` VALUES (2308, 'BUSINESS', '4d7a466f-999c-4fc0-8f73-268d5d55524d', 'admin', b'1', '测试点对点数据，持久化1616050470013', '/queue/message', b'0', '2021-03-18 14:54:29');
INSERT INTO `ws_data` VALUES (2309, 'BUSINESS', 'af96770e-d257-428e-b595-f0e4bc7156bd', 'admin', b'1', '测试点对点数据，持久化1616050500010', '/queue/message', b'0', '2021-03-18 14:54:59');
INSERT INTO `ws_data` VALUES (2310, 'BUSINESS', '8759eea9-5d1d-41c8-a587-65174a648971', 'admin', b'1', '测试点对点数据，持久化1616050530004', '/queue/message', b'0', '2021-03-18 14:55:29');
INSERT INTO `ws_data` VALUES (2311, 'BUSINESS', '86b62b3e-6b46-4207-876e-22afc08d7a55', 'admin', b'1', '测试点对点数据，持久化1616050560003', '/queue/message', b'0', '2021-03-18 14:55:59');
INSERT INTO `ws_data` VALUES (2312, 'BUSINESS', '08d0f505-1e5d-4d6d-8458-cbd99d1a61b3', 'admin', b'1', '测试点对点数据，持久化1616050590003', '/queue/message', b'0', '2021-03-18 14:56:29');
INSERT INTO `ws_data` VALUES (2313, 'BUSINESS', '939d2223-2c96-4fd1-a783-2ed165da5c44', 'admin', b'1', '测试点对点数据，持久化1616050620005', '/queue/message', b'0', '2021-03-18 14:56:59');
INSERT INTO `ws_data` VALUES (2314, 'BUSINESS', '8f6e07a0-b16c-484c-b118-099465c912fe', 'admin', b'1', '测试点对点数据，持久化1616050650008', '/queue/message', b'0', '2021-03-18 14:57:29');
INSERT INTO `ws_data` VALUES (2315, 'BUSINESS', '911f2878-5e64-4f83-ae62-8a5f335d11fc', 'admin', b'1', '测试点对点数据，持久化1616050680002', '/queue/message', b'0', '2021-03-18 14:57:59');
INSERT INTO `ws_data` VALUES (2316, 'BUSINESS', '9b6c607b-214a-46bd-a19e-5c6e486bbb64', 'admin', b'1', '测试点对点数据，持久化1616050710005', '/queue/message', b'0', '2021-03-18 14:58:29');
INSERT INTO `ws_data` VALUES (2317, 'BUSINESS', '89fd198f-c7fc-4570-870f-872ef6d4300c', 'admin', b'1', '测试点对点数据，持久化1616050740005', '/queue/message', b'0', '2021-03-18 14:58:59');
INSERT INTO `ws_data` VALUES (2318, 'BUSINESS', '0ac3535c-7776-4696-8fe8-93523d8ba7bb', 'admin', b'1', '测试点对点数据，持久化1616050770008', '/queue/message', b'0', '2021-03-18 14:59:29');
INSERT INTO `ws_data` VALUES (2319, 'BUSINESS', '584f15f6-9f9b-443b-9b67-758ad2d2880a', 'admin', b'1', '测试点对点数据，持久化1616050800002', '/queue/message', b'0', '2021-03-18 14:59:59');
INSERT INTO `ws_data` VALUES (2320, 'BUSINESS', 'a656e2f2-8fd8-41ad-b34b-a7c23d0104df', 'admin', b'1', '测试点对点数据，持久化1616050830004', '/queue/message', b'0', '2021-03-18 15:00:29');
INSERT INTO `ws_data` VALUES (2321, 'BUSINESS', '981eb8b4-3e65-43e8-a491-fd405348bc3d', 'admin', b'1', '测试点对点数据，持久化1616050860008', '/queue/message', b'0', '2021-03-18 15:00:59');
INSERT INTO `ws_data` VALUES (2322, 'BUSINESS', '6abbd7c2-f33d-4d11-8cca-c5dad415598c', 'admin', b'1', '测试点对点数据，持久化1616050890004', '/queue/message', b'0', '2021-03-18 15:01:29');
INSERT INTO `ws_data` VALUES (2323, 'BUSINESS', 'a0c9d8ec-3e84-4b74-a4ed-f53e01326173', 'admin', b'1', '测试点对点数据，持久化1616050920004', '/queue/message', b'0', '2021-03-18 15:01:59');
INSERT INTO `ws_data` VALUES (2324, 'BUSINESS', 'c089abae-fc22-4c06-bbf2-4d0fab1f5733', 'admin', b'1', '测试点对点数据，持久化1616050950003', '/queue/message', b'0', '2021-03-18 15:02:29');
INSERT INTO `ws_data` VALUES (2325, 'BUSINESS', '0fe51604-12b9-47c6-92c8-9056792dba95', 'admin', b'1', '测试点对点数据，持久化1616050980006', '/queue/message', b'0', '2021-03-18 15:02:59');
INSERT INTO `ws_data` VALUES (2326, 'BUSINESS', 'fe6e88bc-6cf9-43ab-b3c3-0ab7f8462d7f', 'admin', b'1', '测试点对点数据，持久化1616051010003', '/queue/message', b'0', '2021-03-18 15:03:29');
INSERT INTO `ws_data` VALUES (2327, 'BUSINESS', 'b8d1170f-b13c-4a30-99ab-0b754b31d3ec', 'admin', b'1', '测试点对点数据，持久化1616051040003', '/queue/message', b'0', '2021-03-18 15:03:59');
INSERT INTO `ws_data` VALUES (2328, 'BUSINESS', '1ce12042-6aef-424d-a1e9-e9b60f056b55', 'admin', b'1', '测试点对点数据，持久化1616051070003', '/queue/message', b'0', '2021-03-18 15:04:29');
INSERT INTO `ws_data` VALUES (2329, 'BUSINESS', 'f0f18f4d-36b0-486a-a308-d995f3a5e380', 'admin', b'1', '测试点对点数据，持久化1616051100004', '/queue/message', b'0', '2021-03-18 15:04:59');
INSERT INTO `ws_data` VALUES (2330, 'BUSINESS', '09f200f0-9f58-4822-8cf4-741ae1b26d65', 'admin', b'1', '测试点对点数据，持久化1616051130005', '/queue/message', b'0', '2021-03-18 15:05:29');
INSERT INTO `ws_data` VALUES (2331, 'BUSINESS', '9322a75e-e750-4c89-8047-3314c1bdb326', 'admin', b'1', '测试点对点数据，持久化1616051160004', '/queue/message', b'0', '2021-03-18 15:05:59');
INSERT INTO `ws_data` VALUES (2332, 'BUSINESS', '7123a3bd-3123-4e03-b45c-02fd21ef3aab', 'admin', b'1', '测试点对点数据，持久化1616051190003', '/queue/message', b'0', '2021-03-18 15:06:29');
INSERT INTO `ws_data` VALUES (2333, 'BUSINESS', 'de02a8d4-ea08-4114-aaa9-76368893ca38', 'admin', b'1', '测试点对点数据，持久化1616051220004', '/queue/message', b'0', '2021-03-18 15:06:59');
INSERT INTO `ws_data` VALUES (2334, 'BUSINESS', '8e5352fc-0248-4436-acbc-c34790283398', 'admin', b'1', '测试点对点数据，持久化1616051250003', '/queue/message', b'0', '2021-03-18 15:07:29');
INSERT INTO `ws_data` VALUES (2335, 'BUSINESS', 'ef520e30-83a4-4940-9cd4-9cde7402ace9', 'admin', b'1', '测试点对点数据，持久化1616051280007', '/queue/message', b'0', '2021-03-18 15:07:59');
INSERT INTO `ws_data` VALUES (2336, 'BUSINESS', '398b57ed-a9e7-47a2-b4c8-ee0c7b4bdbdd', 'admin', b'1', '测试点对点数据，持久化1616051310004', '/queue/message', b'0', '2021-03-18 15:08:29');
INSERT INTO `ws_data` VALUES (2337, 'BUSINESS', 'f5984f0d-a4af-4b5c-892a-374542861391', 'admin', b'1', '测试点对点数据，持久化1616051340002', '/queue/message', b'0', '2021-03-18 15:08:59');
INSERT INTO `ws_data` VALUES (2338, 'BUSINESS', '9513ce46-49c2-458d-802e-659033f8bafd', 'admin', b'1', '测试点对点数据，持久化1616051370003', '/queue/message', b'0', '2021-03-18 15:09:29');
INSERT INTO `ws_data` VALUES (2339, 'BUSINESS', '5248aba0-94c2-4574-8b63-b4f9216e9c7c', 'admin', b'1', '测试点对点数据，持久化1616051400004', '/queue/message', b'0', '2021-03-18 15:09:59');
INSERT INTO `ws_data` VALUES (2340, 'BUSINESS', '5ca4ca62-77d5-4ce7-a1c5-deec4df85065', 'admin', b'1', '测试点对点数据，持久化1616051430003', '/queue/message', b'0', '2021-03-18 15:10:29');
INSERT INTO `ws_data` VALUES (2341, 'BUSINESS', '4a230c44-7b77-4197-894e-9de7fd191216', 'admin', b'1', '测试点对点数据，持久化1616051460005', '/queue/message', b'0', '2021-03-18 15:10:59');
INSERT INTO `ws_data` VALUES (2342, 'BUSINESS', '36e9e0d5-63d0-497a-8574-53dd2f199099', 'admin', b'1', '测试点对点数据，持久化1616051490005', '/queue/message', b'0', '2021-03-18 15:11:29');
INSERT INTO `ws_data` VALUES (2343, 'BUSINESS', '41f1b782-69e7-48fd-9756-3e14da8f4138', 'admin', b'1', '测试点对点数据，持久化1616051520004', '/queue/message', b'0', '2021-03-18 15:11:59');
INSERT INTO `ws_data` VALUES (2344, 'BUSINESS', '1d42662b-afd3-4c55-92fd-1fe119dc092c', 'admin', b'1', '测试点对点数据，持久化1616051550018', '/queue/message', b'0', '2021-03-18 15:12:29');
INSERT INTO `ws_data` VALUES (2345, 'BUSINESS', '207bf54f-a5d0-417c-b4f7-3a43c1002213', 'admin', b'1', '测试点对点数据，持久化1616051580004', '/queue/message', b'0', '2021-03-18 15:12:59');
INSERT INTO `ws_data` VALUES (2346, 'BUSINESS', '06c509b9-1626-49a6-a20a-5f670faaabe9', 'admin', b'1', '测试点对点数据，持久化1616051610005', '/queue/message', b'0', '2021-03-18 15:13:29');
INSERT INTO `ws_data` VALUES (2347, 'BUSINESS', 'd65a5f99-12cc-4cd7-8475-5a63db4d7260', 'admin', b'1', '测试点对点数据，持久化1616051640004', '/queue/message', b'0', '2021-03-18 15:13:59');
INSERT INTO `ws_data` VALUES (2348, 'BUSINESS', '6b2f0aae-1dfc-417b-9236-4bb256340474', 'admin', b'1', '测试点对点数据，持久化1616051670004', '/queue/message', b'0', '2021-03-18 15:14:29');
INSERT INTO `ws_data` VALUES (2349, 'BUSINESS', '93fe5fa9-83e4-46ef-9235-d10dc1db4827', 'admin', b'1', '测试点对点数据，持久化1616051700005', '/queue/message', b'0', '2021-03-18 15:14:59');
INSERT INTO `ws_data` VALUES (2350, 'BUSINESS', 'e497fb72-a842-4ac4-a001-f5f9044e0b29', 'admin', b'1', '测试点对点数据，持久化1616051730005', '/queue/message', b'0', '2021-03-18 15:15:29');
INSERT INTO `ws_data` VALUES (2351, 'BUSINESS', 'd7a5bba2-963b-483c-9a95-cab68932b45f', 'admin', b'1', '测试点对点数据，持久化1616051760007', '/queue/message', b'0', '2021-03-18 15:15:59');
INSERT INTO `ws_data` VALUES (2352, 'BUSINESS', 'a5742521-a012-4db3-8baf-a8b9da1d9a26', 'admin', b'1', '测试点对点数据，持久化1616051790003', '/queue/message', b'0', '2021-03-18 15:16:29');
INSERT INTO `ws_data` VALUES (2353, 'BUSINESS', 'ff447b76-3f55-4424-ac13-7e530a6bc373', 'admin', b'1', '测试点对点数据，持久化1616051820003', '/queue/message', b'0', '2021-03-18 15:16:59');
INSERT INTO `ws_data` VALUES (2354, 'BUSINESS', '7e36153d-1cf1-40d7-a6bc-c569255c72a9', 'admin', b'1', '测试点对点数据，持久化1616051850005', '/queue/message', b'0', '2021-03-18 15:17:29');
INSERT INTO `ws_data` VALUES (2355, 'BUSINESS', '82b0e50a-e540-41e2-9f96-9a2fb20ca7cb', 'admin', b'1', '测试点对点数据，持久化1616051880006', '/queue/message', b'0', '2021-03-18 15:17:59');
INSERT INTO `ws_data` VALUES (2356, 'BUSINESS', '5bb5701d-c379-4601-96b2-b0e993307fe5', 'admin', b'1', '测试点对点数据，持久化1616051910007', '/queue/message', b'0', '2021-03-18 15:18:29');
INSERT INTO `ws_data` VALUES (2357, 'BUSINESS', 'e60ba279-6efd-41b2-b38a-09d1abe3483e', 'admin', b'1', '测试点对点数据，持久化1616051940006', '/queue/message', b'0', '2021-03-18 15:18:59');
INSERT INTO `ws_data` VALUES (2358, 'BUSINESS', '5b667b7d-3225-4f5c-aeec-42e7982ab5a1', 'admin', b'1', '测试点对点数据，持久化1616051970006', '/queue/message', b'0', '2021-03-18 15:19:29');
INSERT INTO `ws_data` VALUES (2359, 'BUSINESS', 'e8d116bd-ffcc-448e-867a-973e34994b98', 'admin', b'1', '测试点对点数据，持久化1616052000004', '/queue/message', b'0', '2021-03-18 15:19:59');
INSERT INTO `ws_data` VALUES (2360, 'BUSINESS', 'f7fd81ff-45a5-4875-a539-a6cdaea8661b', 'admin', b'1', '测试点对点数据，持久化1616052030005', '/queue/message', b'0', '2021-03-18 15:20:29');
INSERT INTO `ws_data` VALUES (2361, 'BUSINESS', '684e6aec-5131-4569-a11f-905642c01d10', 'admin', b'1', '测试点对点数据，持久化1616052060003', '/queue/message', b'0', '2021-03-18 15:20:59');
INSERT INTO `ws_data` VALUES (2362, 'BUSINESS', '4d44df4c-714c-4e3e-8932-7adecdbc3c1e', 'admin', b'1', '测试点对点数据，持久化1616052090002', '/queue/message', b'0', '2021-03-18 15:21:29');
INSERT INTO `ws_data` VALUES (2363, 'BUSINESS', 'fb3b2b78-ec61-4e22-bbf2-c5da7bf0ce28', 'admin', b'1', '测试点对点数据，持久化1616052120004', '/queue/message', b'0', '2021-03-18 15:21:59');
INSERT INTO `ws_data` VALUES (2364, 'BUSINESS', 'bfb41bb5-6f44-4487-bd3e-27dd2227e144', 'admin', b'1', '测试点对点数据，持久化1616052150003', '/queue/message', b'0', '2021-03-18 15:22:29');
INSERT INTO `ws_data` VALUES (2365, 'BUSINESS', '1ffd46cb-c27e-42b6-b7b6-9a51e6a4ff6b', 'admin', b'1', '测试点对点数据，持久化1616052180003', '/queue/message', b'0', '2021-03-18 15:22:59');
INSERT INTO `ws_data` VALUES (2366, 'BUSINESS', 'a147a1b4-752c-4cc4-ae70-a41f9443d523', 'admin', b'1', '测试点对点数据，持久化1616052210003', '/queue/message', b'0', '2021-03-18 15:23:29');
INSERT INTO `ws_data` VALUES (2367, 'BUSINESS', 'e823fcc3-fb4f-45a0-b67e-c7a575564c5f', 'admin', b'1', '测试点对点数据，持久化1616052240006', '/queue/message', b'0', '2021-03-18 15:23:59');
INSERT INTO `ws_data` VALUES (2368, 'BUSINESS', 'd1f68d44-bfa2-4753-9ea4-d161c222e380', 'admin', b'1', '测试点对点数据，持久化1616052270003', '/queue/message', b'0', '2021-03-18 15:24:29');
INSERT INTO `ws_data` VALUES (2369, 'BUSINESS', 'd3cd0892-54db-4018-b521-871b3fa0afde', 'admin', b'1', '测试点对点数据，持久化1616052300009', '/queue/message', b'0', '2021-03-18 15:24:59');
INSERT INTO `ws_data` VALUES (2370, 'BUSINESS', '4227a43a-4731-484e-bd00-d1a8c9e1dd23', 'admin', b'1', '测试点对点数据，持久化1616052330025', '/queue/message', b'0', '2021-03-18 15:25:29');
INSERT INTO `ws_data` VALUES (2371, 'BUSINESS', 'c5251430-9d1c-4930-8e6c-a4ce572fa9e9', 'admin', b'1', '测试点对点数据，持久化1616052360005', '/queue/message', b'0', '2021-03-18 15:25:59');
INSERT INTO `ws_data` VALUES (2372, 'BUSINESS', 'ac5a3093-d710-4a91-88bb-cf6453ae7485', 'admin', b'1', '测试点对点数据，持久化1616052390006', '/queue/message', b'0', '2021-03-18 15:26:29');
INSERT INTO `ws_data` VALUES (2373, 'BUSINESS', '94b561ee-f7c4-49ca-ad5d-c3e8cd118d93', 'admin', b'1', '测试点对点数据，持久化1616052420002', '/queue/message', b'0', '2021-03-18 15:26:59');
INSERT INTO `ws_data` VALUES (2374, 'BUSINESS', '3bcbb51f-7c52-48c2-a8d3-a366df3db0ef', 'admin', b'1', '测试点对点数据，持久化1616052450003', '/queue/message', b'0', '2021-03-18 15:27:29');
INSERT INTO `ws_data` VALUES (2375, 'BUSINESS', 'b01323fd-ec4f-44d2-8cdc-c2240df556f1', 'admin', b'1', '测试点对点数据，持久化1616052480005', '/queue/message', b'0', '2021-03-18 15:27:59');
INSERT INTO `ws_data` VALUES (2376, 'BUSINESS', '34e0da6c-927a-43b7-ac15-a6cd716bb0cf', 'admin', b'1', '测试点对点数据，持久化1616052510001', '/queue/message', b'0', '2021-03-18 15:28:29');
INSERT INTO `ws_data` VALUES (2377, 'BUSINESS', '4f2a30a0-2d4c-4746-bea2-47aaf35213b5', 'admin', b'1', '测试点对点数据，持久化1616052540005', '/queue/message', b'0', '2021-03-18 15:28:59');
INSERT INTO `ws_data` VALUES (2378, 'BUSINESS', '64432d6b-834c-4939-82e1-049838dde374', 'admin', b'1', '测试点对点数据，持久化1616052570004', '/queue/message', b'0', '2021-03-18 15:29:29');
INSERT INTO `ws_data` VALUES (2379, 'BUSINESS', 'a194a7db-8916-44cc-9a40-d5e0a448af36', 'admin', b'1', '测试点对点数据，持久化1616052600003', '/queue/message', b'0', '2021-03-18 15:29:59');
INSERT INTO `ws_data` VALUES (2380, 'BUSINESS', '8895af0d-d188-4594-aba9-4ad0451aed64', 'admin', b'1', '测试点对点数据，持久化1616052630002', '/queue/message', b'0', '2021-03-18 15:30:29');
INSERT INTO `ws_data` VALUES (2381, 'BUSINESS', 'eb665ebc-8237-44ce-91cf-cd20c0134dcc', 'admin', b'1', '测试点对点数据，持久化1616052660012', '/queue/message', b'0', '2021-03-18 15:30:59');
INSERT INTO `ws_data` VALUES (2382, 'BUSINESS', 'b6acaf46-4db5-4b67-ab3d-2b77335a6eef', 'admin', b'1', '测试点对点数据，持久化1616052690004', '/queue/message', b'0', '2021-03-18 15:31:29');
INSERT INTO `ws_data` VALUES (2383, 'BUSINESS', '020e83a6-a9ca-4c8a-955d-d92653a0953a', 'admin', b'1', '测试点对点数据，持久化1616052720003', '/queue/message', b'0', '2021-03-18 15:31:59');
INSERT INTO `ws_data` VALUES (2384, 'BUSINESS', '0445ca05-0809-4741-9b25-97ee7a7842b3', 'admin', b'1', '测试点对点数据，持久化1616052750002', '/queue/message', b'0', '2021-03-18 15:32:29');
INSERT INTO `ws_data` VALUES (2385, 'BUSINESS', '04c022d7-97c6-471d-96b0-328a3028b0ea', 'admin', b'1', '测试点对点数据，持久化1616052780003', '/queue/message', b'0', '2021-03-18 15:32:59');
INSERT INTO `ws_data` VALUES (2386, 'BUSINESS', '5f0b1135-71d0-43ad-bdf4-5b4430bc6aa9', 'admin', b'1', '测试点对点数据，持久化1616052810004', '/queue/message', b'0', '2021-03-18 15:33:29');
INSERT INTO `ws_data` VALUES (2387, 'BUSINESS', '673a7116-eb99-42fb-a257-64e061e86754', 'admin', b'1', '测试点对点数据，持久化1616052840006', '/queue/message', b'0', '2021-03-18 15:33:59');
INSERT INTO `ws_data` VALUES (2388, 'BUSINESS', '84ce8366-1f93-4354-abb5-b5c0cc091cfb', 'admin', b'1', '测试点对点数据，持久化1616052870004', '/queue/message', b'0', '2021-03-18 15:34:29');
INSERT INTO `ws_data` VALUES (2389, 'BUSINESS', '278017fd-a94c-483d-8103-7b8b2a87d1fb', 'admin', b'1', '测试点对点数据，持久化1616052900003', '/queue/message', b'0', '2021-03-18 15:34:59');
INSERT INTO `ws_data` VALUES (2390, 'BUSINESS', 'bfda7ca2-30b5-441b-8434-e59c5970208d', 'admin', b'1', '测试点对点数据，持久化1616052930003', '/queue/message', b'0', '2021-03-18 15:35:29');
INSERT INTO `ws_data` VALUES (2391, 'BUSINESS', 'e5e7bb1a-de9d-4b99-9d0c-7e42df7df9b3', 'admin', b'1', '测试点对点数据，持久化1616052960003', '/queue/message', b'0', '2021-03-18 15:35:59');
INSERT INTO `ws_data` VALUES (2392, 'BUSINESS', '02560d0c-3e95-4bf8-a716-c52453a18603', 'admin', b'1', '测试点对点数据，持久化1616052990004', '/queue/message', b'0', '2021-03-18 15:36:29');
INSERT INTO `ws_data` VALUES (2393, 'BUSINESS', 'e1ac4002-6cfc-46ac-a27c-067b175bb9b1', 'admin', b'1', '测试点对点数据，持久化1616053020007', '/queue/message', b'0', '2021-03-18 15:36:59');
INSERT INTO `ws_data` VALUES (2394, 'BUSINESS', '64ccb8c5-9159-4977-b598-d1545793bd1c', 'admin', b'1', '测试点对点数据，持久化1616053050006', '/queue/message', b'0', '2021-03-18 15:37:29');
INSERT INTO `ws_data` VALUES (2395, 'BUSINESS', '8e314630-5b89-4d68-b019-ef36af6701a0', 'admin', b'1', '测试点对点数据，持久化1616053080006', '/queue/message', b'0', '2021-03-18 15:37:59');
INSERT INTO `ws_data` VALUES (2396, 'BUSINESS', '63d4fc68-bb4c-4432-99e9-6587af4d1c2a', 'admin', b'1', '测试点对点数据，持久化1616053110003', '/queue/message', b'0', '2021-03-18 15:38:29');
INSERT INTO `ws_data` VALUES (2397, 'BUSINESS', 'adba81ef-a05e-47c6-b77a-1826b14df1c8', 'admin', b'1', '测试点对点数据，持久化1616053140005', '/queue/message', b'0', '2021-03-18 15:38:59');
INSERT INTO `ws_data` VALUES (2398, 'BUSINESS', 'db8a7ca9-ca59-46b5-b8df-8213caa29f29', 'admin', b'1', '测试点对点数据，持久化1616053170004', '/queue/message', b'0', '2021-03-18 15:39:29');
INSERT INTO `ws_data` VALUES (2399, 'BUSINESS', '0650e28b-2079-4428-bbca-d5bd19082027', 'admin', b'1', '测试点对点数据，持久化1616053200004', '/queue/message', b'0', '2021-03-18 15:39:59');
INSERT INTO `ws_data` VALUES (2400, 'BUSINESS', '2ceef2a1-c0b9-45c7-80f3-23a6fb752e22', 'admin', b'1', '测试点对点数据，持久化1616053230003', '/queue/message', b'0', '2021-03-18 15:40:29');
INSERT INTO `ws_data` VALUES (2401, 'BUSINESS', '2a0aeebd-aea3-4529-99a3-cb3c9d4fc521', 'admin', b'1', '测试点对点数据，持久化1616053260004', '/queue/message', b'0', '2021-03-18 15:40:59');
INSERT INTO `ws_data` VALUES (2402, 'BUSINESS', 'ee4a4bcc-4cd3-48d7-b23d-98d06f24fe87', 'admin', b'1', '测试点对点数据，持久化1616053290005', '/queue/message', b'0', '2021-03-18 15:41:29');
INSERT INTO `ws_data` VALUES (2403, 'BUSINESS', 'edfec114-9978-4848-a26f-999288527ad7', 'admin', b'1', '测试点对点数据，持久化1616053320003', '/queue/message', b'0', '2021-03-18 15:41:59');
INSERT INTO `ws_data` VALUES (2404, 'BUSINESS', '2efc0f1d-54c9-4540-949d-d4dba91359f2', 'admin', b'1', '测试点对点数据，持久化1616053350007', '/queue/message', b'0', '2021-03-18 15:42:29');
INSERT INTO `ws_data` VALUES (2405, 'BUSINESS', '85436fdc-4e0f-4d01-8e2b-bb0d32e7be88', 'admin', b'1', '测试点对点数据，持久化1616053380003', '/queue/message', b'0', '2021-03-18 15:42:59');
INSERT INTO `ws_data` VALUES (2406, 'BUSINESS', '38ef2ba5-5787-4206-a982-3888bfbc146b', 'admin', b'1', '测试点对点数据，持久化1616053410009', '/queue/message', b'0', '2021-03-18 15:43:29');
INSERT INTO `ws_data` VALUES (2407, 'BUSINESS', '2292cc89-92a4-4865-9bbf-fbdb353a7186', 'admin', b'1', '测试点对点数据，持久化1616053440005', '/queue/message', b'0', '2021-03-18 15:43:59');
INSERT INTO `ws_data` VALUES (2408, 'BUSINESS', 'b773b363-b38b-4121-8650-9b36130e7479', 'admin', b'1', '测试点对点数据，持久化1616053470003', '/queue/message', b'0', '2021-03-18 15:44:29');
INSERT INTO `ws_data` VALUES (2409, 'BUSINESS', 'ec505f2c-4d35-4227-ac19-3881e0b3400a', 'admin', b'1', '测试点对点数据，持久化1616053500003', '/queue/message', b'0', '2021-03-18 15:44:59');
INSERT INTO `ws_data` VALUES (2410, 'BUSINESS', '0159bdf5-f831-4b7d-b0f6-7ed96b62011e', 'admin', b'1', '测试点对点数据，持久化1616053530004', '/queue/message', b'0', '2021-03-18 15:45:29');
INSERT INTO `ws_data` VALUES (2411, 'BUSINESS', '47ac64ce-9023-43c5-ba7f-fa4aec54ac45', 'admin', b'1', '测试点对点数据，持久化1616053560007', '/queue/message', b'0', '2021-03-18 15:45:59');
INSERT INTO `ws_data` VALUES (2412, 'BUSINESS', 'd5d95a62-b071-463c-97bb-d28299440bf5', 'admin', b'1', '测试点对点数据，持久化1616053590004', '/queue/message', b'0', '2021-03-18 15:46:29');
INSERT INTO `ws_data` VALUES (2413, 'BUSINESS', 'fd841b66-6fac-46c5-b107-e48e25d838d2', 'admin', b'1', '测试点对点数据，持久化1616053620004', '/queue/message', b'0', '2021-03-18 15:46:59');
INSERT INTO `ws_data` VALUES (2414, 'BUSINESS', '7baffd71-83cd-4993-97fb-ce506c2a8d47', 'admin', b'1', '测试点对点数据，持久化1616053650009', '/queue/message', b'0', '2021-03-18 15:47:29');
INSERT INTO `ws_data` VALUES (2415, 'BUSINESS', '616ec267-398e-482e-bae5-f4ed90ce130f', 'admin', b'1', '测试点对点数据，持久化1616053680010', '/queue/message', b'0', '2021-03-18 15:47:59');
INSERT INTO `ws_data` VALUES (2416, 'BUSINESS', 'bc8c4f17-058c-4dab-bd77-b2e5295bbc4e', 'admin', b'1', '测试点对点数据，持久化1616053710009', '/queue/message', b'0', '2021-03-18 15:48:29');
INSERT INTO `ws_data` VALUES (2417, 'BUSINESS', '063cb93d-6318-44ed-bfff-d562eb20d6dc', 'admin', b'1', '测试点对点数据，持久化1616053740004', '/queue/message', b'0', '2021-03-18 15:48:59');
INSERT INTO `ws_data` VALUES (2418, 'BUSINESS', '894e6055-7190-4d42-b3ff-c8294162a17a', 'admin', b'1', '测试点对点数据，持久化1616053770004', '/queue/message', b'0', '2021-03-18 15:49:29');
INSERT INTO `ws_data` VALUES (2419, 'BUSINESS', '268fc697-934f-43a1-9e3f-0e185fef2693', 'admin', b'1', '测试点对点数据，持久化1616053800003', '/queue/message', b'0', '2021-03-18 15:49:59');
INSERT INTO `ws_data` VALUES (2420, 'BUSINESS', '8ba67493-8ecc-4ea2-9fc0-e7c733d22bd8', 'admin', b'1', '测试点对点数据，持久化1616053830008', '/queue/message', b'0', '2021-03-18 15:50:29');
INSERT INTO `ws_data` VALUES (2421, 'BUSINESS', '11b0c062-c1eb-4d2e-8a7c-497f5865b5cd', 'admin', b'1', '测试点对点数据，持久化1616053860007', '/queue/message', b'0', '2021-03-18 15:50:59');
INSERT INTO `ws_data` VALUES (2422, 'BUSINESS', '26d53cb4-97c1-4ee3-9836-026b59f75733', 'admin', b'1', '测试点对点数据，持久化1616053890005', '/queue/message', b'0', '2021-03-18 15:51:29');
INSERT INTO `ws_data` VALUES (2423, 'BUSINESS', '9d8d0153-9bd7-4d4a-baa9-80e08d932097', 'admin', b'1', '测试点对点数据，持久化1616053920005', '/queue/message', b'0', '2021-03-18 15:51:59');
INSERT INTO `ws_data` VALUES (2424, 'BUSINESS', '53476a50-8cc6-4c70-8c12-3d5a858ab060', 'admin', b'1', '测试点对点数据，持久化1616053950004', '/queue/message', b'0', '2021-03-18 15:52:29');
INSERT INTO `ws_data` VALUES (2425, 'BUSINESS', 'a01628f8-f6be-4a6e-83f3-bc370623e6cb', 'admin', b'1', '测试点对点数据，持久化1616053980003', '/queue/message', b'0', '2021-03-18 15:52:59');
INSERT INTO `ws_data` VALUES (2426, 'BUSINESS', 'bb971e52-a89d-4673-8164-a240f82a1279', 'admin', b'1', '测试点对点数据，持久化1616054010008', '/queue/message', b'0', '2021-03-18 15:53:29');
INSERT INTO `ws_data` VALUES (2427, 'BUSINESS', '11f46fdc-f6ef-49c2-b682-98f82da6eb8e', 'admin', b'1', '测试点对点数据，持久化1616054040005', '/queue/message', b'0', '2021-03-18 15:53:59');
INSERT INTO `ws_data` VALUES (2428, 'BUSINESS', 'c69ccbdd-46e2-41af-baae-20e97c789feb', 'admin', b'1', '测试点对点数据，持久化1616054070004', '/queue/message', b'0', '2021-03-18 15:54:29');
INSERT INTO `ws_data` VALUES (2429, 'BUSINESS', '0d0ec48b-effe-41c8-b96b-2c8b693cf34c', 'admin', b'1', '测试点对点数据，持久化1616054100007', '/queue/message', b'0', '2021-03-18 15:54:59');
INSERT INTO `ws_data` VALUES (2430, 'BUSINESS', '288b0e2f-c3b0-4246-b652-824c6b275084', 'admin', b'1', '测试点对点数据，持久化1616054130014', '/queue/message', b'0', '2021-03-18 15:55:29');
INSERT INTO `ws_data` VALUES (2431, 'BUSINESS', '58c98b70-c305-4f19-8744-269cd0a42b43', 'admin', b'1', '测试点对点数据，持久化1616054160003', '/queue/message', b'0', '2021-03-18 15:55:59');
INSERT INTO `ws_data` VALUES (2432, 'BUSINESS', '0d64c3bb-d551-4ae2-8449-3cdb3bef22f0', 'admin', b'1', '测试点对点数据，持久化1616054190004', '/queue/message', b'0', '2021-03-18 15:56:29');
INSERT INTO `ws_data` VALUES (2433, 'BUSINESS', 'f4a905b8-843d-4946-872b-73a97117bda3', 'admin', b'1', '测试点对点数据，持久化1616054220004', '/queue/message', b'0', '2021-03-18 15:56:59');
INSERT INTO `ws_data` VALUES (2434, 'BUSINESS', '4eb007a6-adaa-4391-93d7-314b63a10297', 'admin', b'1', '测试点对点数据，持久化1616054250004', '/queue/message', b'0', '2021-03-18 15:57:29');
INSERT INTO `ws_data` VALUES (2435, 'BUSINESS', 'aecd3f6d-a34a-4c68-b036-7fdb1b3afb97', 'admin', b'1', '测试点对点数据，持久化1616054280003', '/queue/message', b'0', '2021-03-18 15:57:59');
INSERT INTO `ws_data` VALUES (2436, 'BUSINESS', '2d8d38ce-2e87-47b8-a332-10150c936df8', 'admin', b'1', '测试点对点数据，持久化1616054310005', '/queue/message', b'0', '2021-03-18 15:58:29');
INSERT INTO `ws_data` VALUES (2437, 'BUSINESS', 'c0bebd18-e18a-4eca-8d7d-29f7919ce946', 'admin', b'1', '测试点对点数据，持久化1616054340004', '/queue/message', b'0', '2021-03-18 15:58:59');
INSERT INTO `ws_data` VALUES (2438, 'BUSINESS', '01f4d797-dcdd-4ea3-a400-564b8f590f1d', 'admin', b'1', '测试点对点数据，持久化1616054370008', '/queue/message', b'0', '2021-03-18 15:59:29');
INSERT INTO `ws_data` VALUES (2439, 'BUSINESS', '17a5541d-9743-4469-9f48-0510c7ea588d', 'admin', b'1', '测试点对点数据，持久化1616054400003', '/queue/message', b'0', '2021-03-18 15:59:59');
INSERT INTO `ws_data` VALUES (2440, 'BUSINESS', '1c1e042c-3362-452e-bde6-f3c18d80bb83', 'admin', b'1', '测试点对点数据，持久化1616054430005', '/queue/message', b'0', '2021-03-18 16:00:29');
INSERT INTO `ws_data` VALUES (2441, 'BUSINESS', '56c694de-5dc8-4bb1-b6bb-2e4f6046ead1', 'admin', b'1', '测试点对点数据，持久化1616054460007', '/queue/message', b'0', '2021-03-18 16:00:59');
INSERT INTO `ws_data` VALUES (2442, 'BUSINESS', '8a7cdc6c-b8fa-457d-8f98-85334e6f67ff', 'admin', b'1', '测试点对点数据，持久化1616054490003', '/queue/message', b'0', '2021-03-18 16:01:29');
INSERT INTO `ws_data` VALUES (2443, 'BUSINESS', 'a00eac5e-6b0a-4a90-8b08-3e09ab931e62', 'admin', b'1', '测试点对点数据，持久化1616054520006', '/queue/message', b'0', '2021-03-18 16:01:59');
INSERT INTO `ws_data` VALUES (2444, 'BUSINESS', '1e33a1de-ecef-4864-95a1-74cf068ccaa8', 'admin', b'1', '测试点对点数据，持久化1616054550010', '/queue/message', b'0', '2021-03-18 16:02:29');

-- ----------------------------
-- Table structure for xxcolumn_definition
-- ----------------------------
DROP TABLE IF EXISTS `xxcolumn_definition`;
CREATE TABLE `xxcolumn_definition`  (
  `id` int(0) NOT NULL AUTO_INCREMENT COMMENT '自增唯一id',
  `tableIdentity` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '表名',
  `field` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '列名',
  `header` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '列显示中文名称',
  `sequence` int(0) NULL DEFAULT NULL COMMENT '序号',
  `editable` bit(1) NULL DEFAULT b'0' COMMENT '修改时是否允许编辑',
  `sortable` bit(1) NULL DEFAULT NULL COMMENT '1为可排序，0为不可排序',
  `type` char(16) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '列类型，用于前台展示（String,Integer,Float,Double,Date）',
  `selectUrl` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '下拉列表的URL',
  `search` bit(1) NULL DEFAULT b'0' COMMENT '是否提供检索',
  `searchType` char(8) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'like/exat  like模糊，默认exat全匹配，prefix：前缀匹配   range：范围查询',
  `link` bit(1) NULL DEFAULT NULL,
  `addable` bit(1) NULL DEFAULT b'0' COMMENT '新增时是否需要添加(如果对表进行编辑、新增操作设为TRUE)',
  `hidden` bit(1) NULL DEFAULT b'0' COMMENT '是否在表格中隐藏，新增、编辑时应该不隐藏',
  `refTable` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '关联表',
  `refColumn` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '关联列',
  `refValue` varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '固定comobox值，通过半角逗号分隔',
  `required` bit(1) NULL DEFAULT b'0' COMMENT '是否必填',
  `reg` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '正则校验规则',
  `minLength` int(0) NULL DEFAULT NULL COMMENT '最小长度',
  `maxLength` int(0) NULL DEFAULT NULL COMMENT '最大长度',
  `distinct` bit(1) NULL DEFAULT NULL COMMENT '是否不允许重复',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 11194 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '表描述信息' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of xxcolumn_definition
-- ----------------------------
INSERT INTO `xxcolumn_definition` VALUES (11132, 'role', 'role_id', 'ID', 1, b'0', b'0', 'text', NULL, b'0', 'exact', b'0', b'0', b'0', NULL, NULL, NULL, b'0', NULL, NULL, NULL, b'0');
INSERT INTO `xxcolumn_definition` VALUES (11133, 'role', 'role_name', '名称', 2, b'1', b'0', 'text', NULL, b'1', 'like', b'0', b'1', b'0', NULL, NULL, NULL, b'0', NULL, 2, 50, b'0');
INSERT INTO `xxcolumn_definition` VALUES (11134, 'role', 'role_create_time', '创建时间', 3, b'0', b'0', 'daterange', NULL, b'1', 'range', b'0', b'0', b'0', NULL, NULL, NULL, b'0', NULL, NULL, NULL, b'0');
INSERT INTO `xxcolumn_definition` VALUES (11135, 'role', 'role_update_time', '更新时间', 4, b'0', NULL, 'daterange', NULL, b'1', 'range', NULL, b'0', b'0', NULL, NULL, NULL, b'0', NULL, NULL, NULL, NULL);
INSERT INTO `xxcolumn_definition` VALUES (11136, 'user', 'user_id', 'ID', 1, b'0', NULL, 'text', NULL, b'0', NULL, NULL, b'0', b'1', NULL, NULL, NULL, b'0', NULL, NULL, NULL, NULL);
INSERT INTO `xxcolumn_definition` VALUES (11137, 'user', 'user_name', '用户名', 2, b'1', NULL, 'text', NULL, b'1', 'like', NULL, b'1', b'0', NULL, NULL, NULL, b'1', NULL, 2, 50, NULL);
INSERT INTO `xxcolumn_definition` VALUES (11138, 'user', 'user_real_name', '真实姓名', 3, b'1', NULL, 'text', NULL, b'0', NULL, NULL, b'1', b'0', NULL, NULL, NULL, b'1', NULL, NULL, NULL, NULL);
INSERT INTO `xxcolumn_definition` VALUES (11139, 'user', 'user_pwd', '密码', 4, b'0', NULL, 'text', NULL, b'0', NULL, NULL, b'0', b'1', NULL, NULL, NULL, b'0', NULL, NULL, NULL, NULL);
INSERT INTO `xxcolumn_definition` VALUES (11140, 'user', 'user_tel', '手机号码', 5, b'1', NULL, 'text', NULL, b'0', NULL, NULL, b'1', b'0', NULL, NULL, NULL, b'0', '', NULL, NULL, NULL);
INSERT INTO `xxcolumn_definition` VALUES (11141, 'user', 'user_email', '邮箱', 6, b'1', NULL, 'text', NULL, b'0', NULL, NULL, b'1', b'0', NULL, NULL, NULL, b'0', '^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$', NULL, NULL, NULL);
INSERT INTO `xxcolumn_definition` VALUES (11142, 'user', 'user_status', '用户状态', 7, b'1', NULL, 'select', NULL, b'1', 'exact', NULL, b'1', b'0', 'user_status', 'user_status_name', '', b'0', NULL, NULL, NULL, NULL);
INSERT INTO `xxcolumn_definition` VALUES (11143, 'user', 'user_create_time', '创建时间', 8, b'0', NULL, 'daterange', NULL, b'1', 'range', NULL, b'0', b'0', NULL, NULL, NULL, b'0', NULL, NULL, NULL, NULL);
INSERT INTO `xxcolumn_definition` VALUES (11144, 'user', 'user_update_time', '最后修改时间', 9, b'0', NULL, 'daterange', NULL, b'1', 'range', NULL, b'0', b'0', NULL, NULL, NULL, b'0', NULL, NULL, NULL, NULL);
INSERT INTO `xxcolumn_definition` VALUES (11145, 'user', 'user_role', '用户角色', 7, b'0', NULL, 'multiSelect', NULL, b'0', NULL, NULL, b'0', b'0', NULL, NULL, NULL, b'0', NULL, NULL, NULL, NULL);
INSERT INTO `xxcolumn_definition` VALUES (11146, 'user', 'user_role_edit', '用户角色', 10, b'1', NULL, 'multiSelect', '/combobox/role', b'0', NULL, NULL, b'1', b'1', NULL, NULL, NULL, b'0', NULL, NULL, NULL, NULL);
INSERT INTO `xxcolumn_definition` VALUES (11147, 'user', 'user_org', '组织机构', 7, b'0', NULL, 'selectCascader', '', b'0', NULL, NULL, b'0', b'0', NULL, NULL, NULL, b'0', NULL, NULL, NULL, NULL);
INSERT INTO `xxcolumn_definition` VALUES (11148, 'user', 'user_org_edit', '组织机构', 11, b'1', NULL, 'selectCascader', '/combobox/org/tree', b'0', NULL, NULL, b'1', b'1', NULL, NULL, NULL, b'0', NULL, NULL, NULL, NULL);
INSERT INTO `xxcolumn_definition` VALUES (11149, 'dict', 'id', '字典id', 1, b'0', b'0', 'text', NULL, b'0', NULL, NULL, b'0', b'0', NULL, NULL, NULL, b'0', NULL, NULL, NULL, NULL);
INSERT INTO `xxcolumn_definition` VALUES (11150, 'dict', 'key', '字典键', 2, b'0', b'1', 'text', NULL, b'0', 'exact', NULL, b'0', b'0', NULL, NULL, NULL, b'0', NULL, NULL, NULL, NULL);
INSERT INTO `xxcolumn_definition` VALUES (11151, 'dict', 'value', '字典值', 3, b'0', b'0', 'text', NULL, b'0', 'like', NULL, b'0', b'0', NULL, NULL, NULL, b'0', NULL, NULL, NULL, NULL);
INSERT INTO `xxcolumn_definition` VALUES (11152, 'dict', 'type', '字典类型', 4, b'0', b'0', 'text', NULL, b'0', 'extra', NULL, b'0', b'0', NULL, NULL, NULL, b'0', NULL, NULL, NULL, NULL);
INSERT INTO `xxcolumn_definition` VALUES (11153, 'dict', 'description', '字典描述', 5, b'0', b'0', 'text', NULL, b'0', NULL, NULL, b'0', b'0', NULL, NULL, NULL, b'0', NULL, NULL, NULL, NULL);
INSERT INTO `xxcolumn_definition` VALUES (11154, 'param', 'param_name', '参数名称', 1, b'1', b'0', 'text', NULL, b'0', '', NULL, b'1', b'0', NULL, NULL, NULL, b'0', NULL, NULL, NULL, NULL);
INSERT INTO `xxcolumn_definition` VALUES (11155, 'param', 'param_key', '参数键名', 2, b'1', b'1', 'text', NULL, b'1', 'like', NULL, b'1', b'0', NULL, NULL, NULL, b'0', NULL, 1, NULL, NULL);
INSERT INTO `xxcolumn_definition` VALUES (11156, 'param', 'param_value', '参数键值', 3, b'1', b'1', 'text', NULL, b'1', 'like', NULL, b'1', b'0', NULL, NULL, NULL, b'0', NULL, 1, NULL, NULL);
INSERT INTO `xxcolumn_definition` VALUES (11157, 'param', 'param_type', '参数类型', 4, b'0', b'1', 'text', NULL, b'0', NULL, NULL, b'0', b'0', NULL, NULL, NULL, b'0', NULL, NULL, NULL, NULL);
INSERT INTO `xxcolumn_definition` VALUES (11158, 'param', 'create_by', '创建者', 5, b'0', b'0', 'text', NULL, b'0', NULL, NULL, b'0', b'0', NULL, NULL, NULL, b'0', NULL, NULL, NULL, NULL);
INSERT INTO `xxcolumn_definition` VALUES (11159, 'param', 'create_time', '创建时间', 6, b'0', b'0', 'text', NULL, b'0', NULL, NULL, b'0', b'0', NULL, NULL, NULL, b'0', NULL, NULL, NULL, NULL);
INSERT INTO `xxcolumn_definition` VALUES (11160, 'param', 'update_by', '更新者', 7, b'0', b'0', 'text', NULL, b'0', NULL, NULL, b'0', b'0', NULL, NULL, NULL, b'0', NULL, NULL, NULL, NULL);
INSERT INTO `xxcolumn_definition` VALUES (11161, 'param', 'update_time', '更新时间', 8, b'0', b'1', 'text', NULL, b'0', NULL, NULL, b'0', b'0', NULL, NULL, NULL, b'0', NULL, NULL, NULL, NULL);
INSERT INTO `xxcolumn_definition` VALUES (11162, 'param', 'param_desc', '参数描述', 9, b'1', NULL, 'text', NULL, b'0', NULL, NULL, b'1', b'0', NULL, NULL, NULL, b'0', NULL, NULL, NULL, NULL);
INSERT INTO `xxcolumn_definition` VALUES (11163, 'log_info', 'id', '序号', 1, b'0', b'1', 'text', NULL, b'0', NULL, NULL, b'0', b'1', NULL, NULL, NULL, b'0', NULL, NULL, NULL, NULL);
INSERT INTO `xxcolumn_definition` VALUES (11164, 'log_info', 'log_type', '日志类型', 2, b'0', NULL, 'text', NULL, b'1', 'exact', NULL, b'0', b'0', NULL, NULL, NULL, b'0', NULL, NULL, NULL, NULL);
INSERT INTO `xxcolumn_definition` VALUES (11165, 'log_info', 'operate_user', '操作用户', 3, b'0', b'0', 'text', NULL, b'1', 'exact', NULL, b'0', b'0', NULL, NULL, NULL, b'0', NULL, NULL, NULL, NULL);
INSERT INTO `xxcolumn_definition` VALUES (11166, 'log_info', 'operate_type', '操作类型', 4, b'0', NULL, 'text', NULL, b'1', 'exact', NULL, b'0', b'0', NULL, NULL, NULL, b'0', NULL, NULL, NULL, NULL);
INSERT INTO `xxcolumn_definition` VALUES (11167, 'log_info', 'request_url', '请求地址', 5, b'0', NULL, 'text', NULL, b'0', NULL, NULL, b'0', b'0', NULL, NULL, NULL, b'0', NULL, NULL, NULL, NULL);
INSERT INTO `xxcolumn_definition` VALUES (11168, 'log_info', 'operate_time', '操作时间', 6, b'0', NULL, 'text', NULL, b'0', NULL, NULL, b'0', b'0', NULL, NULL, NULL, b'0', NULL, NULL, NULL, NULL);
INSERT INTO `xxcolumn_definition` VALUES (11169, 'log_info', 'request_took_time', '响应时间', 7, b'0', NULL, 'text', NULL, b'0', NULL, NULL, b'0', b'0', NULL, NULL, NULL, b'0', NULL, NULL, NULL, NULL);
INSERT INTO `xxcolumn_definition` VALUES (11170, 'ws_data', 'id', 'ID', 1, b'0', NULL, 'text', NULL, b'0', NULL, NULL, b'0', b'0', NULL, NULL, NULL, b'0', NULL, NULL, NULL, NULL);
INSERT INTO `xxcolumn_definition` VALUES (11171, 'ws_data', 'type', '类型', 2, b'0', NULL, 'text', NULL, b'0', NULL, NULL, b'0', b'0', NULL, NULL, NULL, b'0', NULL, NULL, NULL, NULL);
INSERT INTO `xxcolumn_definition` VALUES (11172, 'log_info', 'log_desc', '日志详情', 8, b'0', NULL, 'text', NULL, b'0', NULL, NULL, b'0', b'0', NULL, NULL, NULL, b'0', NULL, NULL, NULL, NULL);
INSERT INTO `xxcolumn_definition` VALUES (11173, 'ws_data', 'msg_id', '消息id', 3, b'0', NULL, 'text', NULL, b'0', NULL, NULL, b'0', b'0', NULL, NULL, NULL, b'0', NULL, NULL, NULL, NULL);
INSERT INTO `xxcolumn_definition` VALUES (11174, 'ws_data', 'user_identify', '用户标识', 4, b'0', NULL, 'text', NULL, b'1', 'like', NULL, b'0', b'0', NULL, NULL, NULL, b'0', NULL, NULL, NULL, NULL);
INSERT INTO `xxcolumn_definition` VALUES (11175, 'ws_data', 'data', '数据', 5, b'0', NULL, 'text', NULL, b'0', NULL, NULL, b'0', b'0', NULL, NULL, NULL, b'0', NULL, NULL, NULL, NULL);
INSERT INTO `xxcolumn_definition` VALUES (11176, 'ws_data', 'destination', '订阅路径', 6, b'0', NULL, 'text', NULL, b'0', NULL, NULL, b'0', b'0', NULL, NULL, NULL, b'0', NULL, NULL, NULL, NULL);
INSERT INTO `xxcolumn_definition` VALUES (11177, 'ws_data', 'create_time', '推送时间', 7, b'0', NULL, 'daterange', NULL, b'1', 'range', NULL, b'0', b'0', NULL, NULL, NULL, b'0', NULL, NULL, NULL, NULL);
INSERT INTO `xxcolumn_definition` VALUES (11178, 'dict_data', 'dict_label', '字典名称', 1, b'1', b'1', 'text', NULL, b'1', 'like', NULL, b'1', b'0', NULL, NULL, NULL, b'0', NULL, 1, NULL, NULL);
INSERT INTO `xxcolumn_definition` VALUES (11179, 'dict_data', 'dict_data_type', '数据类型', 2, b'1', b'1', 'text', NULL, b'1', 'like', NULL, b'1', b'0', NULL, NULL, NULL, b'0', NULL, 1, NULL, NULL);
INSERT INTO `xxcolumn_definition` VALUES (11180, 'dict_data', 'dict_type', '字典类型', 3, b'0', b'1', 'text', NULL, b'0', NULL, NULL, b'0', b'0', NULL, NULL, NULL, b'0', NULL, NULL, NULL, NULL);
INSERT INTO `xxcolumn_definition` VALUES (11181, 'dict_data', 'create_time', '创建时间', 4, b'0', NULL, 'text', NULL, b'0', NULL, NULL, b'0', b'0', NULL, NULL, NULL, b'0', NULL, NULL, NULL, NULL);
INSERT INTO `xxcolumn_definition` VALUES (11182, 'dict_data', 'create_by', '创建者', 5, b'0', b'1', 'text', NULL, b'0', NULL, NULL, b'0', b'0', NULL, NULL, NULL, b'0', NULL, NULL, NULL, NULL);
INSERT INTO `xxcolumn_definition` VALUES (11183, 'dict_data', 'update_time', '更新时间', 6, b'0', NULL, 'text', NULL, b'0', NULL, NULL, b'0', b'0', NULL, NULL, NULL, b'0', NULL, NULL, NULL, NULL);
INSERT INTO `xxcolumn_definition` VALUES (11184, 'dict_data', 'update_by', '更新者', 7, b'0', b'1', 'text', NULL, b'0', NULL, NULL, b'0', b'0', NULL, NULL, NULL, b'0', NULL, NULL, NULL, NULL);
INSERT INTO `xxcolumn_definition` VALUES (11185, 'dict_data', 'dict_desc', '字典描述', 8, b'1', NULL, 'text', NULL, b'0', '', NULL, b'1', b'0', NULL, NULL, NULL, b'0', NULL, NULL, NULL, NULL);
INSERT INTO `xxcolumn_definition` VALUES (11186, 'dict_data_type', 'dict_data_type', '数据类型', 1, b'1', b'0', 'text', NULL, b'1', 'exact', NULL, b'1', b'0', NULL, NULL, NULL, b'0', NULL, NULL, NULL, NULL);
INSERT INTO `xxcolumn_definition` VALUES (11187, 'dict_data_type', 'dict_data_key', '数据键', 2, b'1', b'1', 'text', NULL, b'1', 'like', NULL, b'1', b'0', NULL, NULL, NULL, b'0', NULL, 1, NULL, NULL);
INSERT INTO `xxcolumn_definition` VALUES (11188, 'dict_data_type', 'dict_data_value', '数据值', 3, b'1', NULL, 'text', NULL, b'1', 'like', NULL, b'1', b'0', NULL, NULL, NULL, b'0', NULL, 1, NULL, NULL);
INSERT INTO `xxcolumn_definition` VALUES (11189, 'dict_data_type', 'create_time', '创建时间', 4, b'0', NULL, 'text', NULL, b'0', NULL, NULL, b'0', b'0', NULL, NULL, NULL, b'0', NULL, NULL, NULL, NULL);
INSERT INTO `xxcolumn_definition` VALUES (11190, 'dict_data_type', 'create_by', '创建者', 5, b'0', NULL, 'text', NULL, b'0', NULL, NULL, b'0', b'0', NULL, NULL, NULL, b'0', NULL, NULL, NULL, NULL);
INSERT INTO `xxcolumn_definition` VALUES (11191, 'dict_data_type', 'update_time', '更新时间', 6, b'0', NULL, 'text', NULL, b'0', NULL, NULL, b'0', b'0', NULL, NULL, NULL, b'0', NULL, NULL, NULL, NULL);
INSERT INTO `xxcolumn_definition` VALUES (11192, 'dict_data_type', 'update_by', '更新者', 7, b'0', NULL, 'text', NULL, b'0', NULL, NULL, b'0', b'0', NULL, NULL, NULL, b'0', NULL, NULL, NULL, NULL);
INSERT INTO `xxcolumn_definition` VALUES (11193, 'dict_data_type', 'dict_data_desc', '描述', 8, b'1', NULL, 'text', NULL, b'0', NULL, NULL, b'1', b'0', NULL, NULL, NULL, b'0', NULL, NULL, NULL, NULL);

-- ----------------------------
-- Table structure for xxtable_definition
-- ----------------------------
DROP TABLE IF EXISTS `xxtable_definition`;
CREATE TABLE `xxtable_definition`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `tableIdentity` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '表格业务名称，前后台会话标识',
  `title` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '表格标题描述',
  `tableName` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '对应的数据库中的数据表名，用于删除、更新、插入',
  `sql` varchar(1023) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '过滤条件，如userName=‘张三’ AND userId = #{userId} AND sessionId = #{sessionId}',
  `checkbox` bit(1) NULL DEFAULT NULL,
  `backInfo` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `frontInfo` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '表格上方注释，如：注：XXX',
  `viewType` char(8) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'muti/single //显示方式，multi多行，single 单行',
  `cellEditable` bit(1) NULL DEFAULT NULL COMMENT ' true/fasle 表格是否在单元格编辑',
  `primaryKey` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '主键',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 11113 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of xxtable_definition
-- ----------------------------
INSERT INTO `xxtable_definition` VALUES (11105, 'dict', '字典', 'dict', 'select * from dict', NULL, NULL, NULL, NULL, NULL, 'id');
INSERT INTO `xxtable_definition` VALUES (11106, 'param', '参数设置', 'param', 'select * from param', NULL, NULL, NULL, NULL, NULL, 'id');
INSERT INTO `xxtable_definition` VALUES (11107, 'log_info', '访问日志', 'log_info', 'select * from log_info', b'0', '', '', '', b'0', 'id');
INSERT INTO `xxtable_definition` VALUES (11108, 'role', '角色', 'role', 'select * from role', NULL, NULL, NULL, NULL, NULL, 'role_id');
INSERT INTO `xxtable_definition` VALUES (11109, 'user', '用户', 'user', 'select * from user', NULL, NULL, NULL, NULL, NULL, 'user_id');
INSERT INTO `xxtable_definition` VALUES (11110, 'ws_data', '消息', 'ws_data', 'select * from ws_data', NULL, NULL, NULL, NULL, NULL, 'id');
INSERT INTO `xxtable_definition` VALUES (11111, 'dict_data', '字典管理', 'dict_data', 'select * from dict_data', NULL, NULL, NULL, NULL, NULL, 'id');
INSERT INTO `xxtable_definition` VALUES (11112, 'dict_data_type', '字典数据', 'dict_data_type', 'select * from dict_data_type', NULL, NULL, NULL, NULL, NULL, 'id');

CREATE TABLE shedlock(name VARCHAR(64) NOT NULL, lock_until TIMESTAMP(3) NOT NULL,
                      locked_at TIMESTAMP(3) NOT NULL, locked_by VARCHAR(255) NOT NULL, PRIMARY KEY (name));


SET FOREIGN_KEY_CHECKS = 1;
