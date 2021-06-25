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

 Date: 25/06/2021 15:16:28
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for datatest
-- ----------------------------
DROP TABLE IF EXISTS `datatest`;
CREATE TABLE `datatest`  (
  `data` datetime(6) NULL DEFAULT NULL COMMENT '时间'
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of datatest
-- ----------------------------
INSERT INTO `datatest` VALUES ('2021-04-20 00:00:00.000000');
INSERT INTO `datatest` VALUES ('2021-04-20 10:12:53.000000');
INSERT INTO `datatest` VALUES ('2021-04-20 10:13:08.000000');
INSERT INTO `datatest` VALUES ('2021-04-20 00:00:00.000000');
INSERT INTO `datatest` VALUES ('2021-04-20 00:00:00.000000');
INSERT INTO `datatest` VALUES ('2021-04-20 10:16:49.000000');
INSERT INTO `datatest` VALUES ('2021-04-20 00:00:00.000000');
INSERT INTO `datatest` VALUES ('2021-04-20 10:18:48.000000');
INSERT INTO `datatest` VALUES ('2021-04-20 00:00:00.000000');
INSERT INTO `datatest` VALUES ('2021-04-20 10:20:08.000000');
INSERT INTO `datatest` VALUES ('2021-04-20 00:00:00.000000');
INSERT INTO `datatest` VALUES ('2021-04-20 10:20:44.000000');
INSERT INTO `datatest` VALUES ('2021-04-20 00:00:00.000000');
INSERT INTO `datatest` VALUES ('2021-04-20 10:21:16.000000');
INSERT INTO `datatest` VALUES ('2021-04-20 00:00:00.000000');
INSERT INTO `datatest` VALUES ('2021-04-20 10:21:22.000000');
INSERT INTO `datatest` VALUES ('2021-04-20 00:00:00.000000');
INSERT INTO `datatest` VALUES ('2021-04-20 10:22:52.000000');
INSERT INTO `datatest` VALUES ('2021-04-20 00:00:00.000000');
INSERT INTO `datatest` VALUES ('2021-04-20 10:25:06.000000');
INSERT INTO `datatest` VALUES ('2021-04-20 00:00:00.000000');
INSERT INTO `datatest` VALUES ('2021-04-20 10:25:55.000000');
INSERT INTO `datatest` VALUES ('2021-04-20 10:25:54.000000');

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
-- Table structure for job_execution_log
-- ----------------------------
DROP TABLE IF EXISTS `job_execution_log`;
CREATE TABLE `job_execution_log`  (
  `id` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `job_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `task_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `hostname` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `ip` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `sharding_item` int(0) NOT NULL,
  `execution_source` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `failure_cause` varchar(4000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `is_success` int(0) NOT NULL,
  `start_time` timestamp(0) NULL DEFAULT NULL,
  `complete_time` timestamp(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for job_status_trace_log
-- ----------------------------
DROP TABLE IF EXISTS `job_status_trace_log`;
CREATE TABLE `job_status_trace_log`  (
  `id` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `job_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `original_task_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `task_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `slave_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `source` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `execution_type` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `sharding_item` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `state` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `message` varchar(4000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `creation_time` timestamp(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `TASK_ID_STATE_INDEX`(`task_id`, `state`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

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
) ENGINE = InnoDB AUTO_INCREMENT = 47 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

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
) ENGINE = InnoDB AUTO_INCREMENT = 40 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

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
  CONSTRAINT `org_user_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `user_info` (`user_id`) ON DELETE CASCADE ON UPDATE RESTRICT
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
) ENGINE = InnoDB AUTO_INCREMENT = 16 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of param
-- ----------------------------
INSERT INTO `param` VALUES (1, '系统监控-访问日志-保留时长', 'sys.log.holdPeriod', '1d', '系统类', 'admin', '2021-02-26 10:51:57', 'unknown', '2021-02-26 14:43:18', '根据该参数清除之前的访问日志数据');
INSERT INTO `param` VALUES (5, '系统监控-测试', 'sys.test.a', 'test', '系统类', 'test', '2021-02-26 15:31:33', 'admin', '2021-03-05 10:37:19', '根据该参数清除之前的访问日志数据');
INSERT INTO `param` VALUES (6, '系统监控-访问日志-hello', 'sys.test.aaa', 'hello iscas', '业务类', 'unknown', '2021-03-01 10:18:48', 'unknown', '2021-03-01 13:42:13', 'hello world');
INSERT INTO `param` VALUES (10, '测试数据', '12222', '12', '业务类', 'unknown', '2021-03-05 14:02:57', 'unknown', '2021-03-05 14:03:03', '111');
INSERT INTO `param` VALUES (12, '测试数据2', 'kkk', 'vvv', '业务类', 'unknown', '2021-05-17 09:54:56', NULL, NULL, 'descddd');
INSERT INTO `param` VALUES (13, 'test3', 'test3', 'test3', '业务类', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `param` VALUES (14, 'test3', 'test3', 'test3', '业务类', NULL, NULL, NULL, NULL, NULL);

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
-- Table structure for shedlock
-- ----------------------------
DROP TABLE IF EXISTS `shedlock`;
CREATE TABLE `shedlock`  (
  `name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `lock_until` timestamp(3) NOT NULL,
  `locked_at` timestamp(3) NOT NULL,
  `locked_by` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  PRIMARY KEY (`name`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of shedlock
-- ----------------------------
INSERT INTO `shedlock` VALUES ('shedLockTest', '2021-06-25 15:16:56.000', '2021-06-25 15:16:46.001', 'aa4b227eb48d');

-- ----------------------------
-- Table structure for test
-- ----------------------------
DROP TABLE IF EXISTS `test`;
CREATE TABLE `test`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of test
-- ----------------------------
INSERT INTO `test` VALUES (1, '1');
INSERT INTO `test` VALUES (2, '2');
INSERT INTO `test` VALUES (3, '3');
INSERT INTO `test` VALUES (4, '4');
INSERT INTO `test` VALUES (5, '5');

-- ----------------------------
-- Table structure for test_mp_ar
-- ----------------------------
DROP TABLE IF EXISTS `test_mp_ar`;
CREATE TABLE `test_mp_ar`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 72 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of test_mp_ar
-- ----------------------------
INSERT INTO `test_mp_ar` VALUES (47, '111');
INSERT INTO `test_mp_ar` VALUES (49, '111');
INSERT INTO `test_mp_ar` VALUES (50, '111');
INSERT INTO `test_mp_ar` VALUES (51, '111');
INSERT INTO `test_mp_ar` VALUES (52, '111');
INSERT INTO `test_mp_ar` VALUES (53, '111');
INSERT INTO `test_mp_ar` VALUES (54, '111');
INSERT INTO `test_mp_ar` VALUES (55, '111');
INSERT INTO `test_mp_ar` VALUES (56, '1121');
INSERT INTO `test_mp_ar` VALUES (57, '1121');
INSERT INTO `test_mp_ar` VALUES (60, '1121');
INSERT INTO `test_mp_ar` VALUES (61, '1121');
INSERT INTO `test_mp_ar` VALUES (63, '1121');
INSERT INTO `test_mp_ar` VALUES (64, '1121');
INSERT INTO `test_mp_ar` VALUES (65, '1121');
INSERT INTO `test_mp_ar` VALUES (66, '1121');
INSERT INTO `test_mp_ar` VALUES (70, 'test1');
INSERT INTO `test_mp_ar` VALUES (71, '111');

-- ----------------------------
-- Table structure for user_info
-- ----------------------------
DROP TABLE IF EXISTS `user_info`;
CREATE TABLE `user_info`  (
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
  CONSTRAINT `user_info_ibfk_1` FOREIGN KEY (`user_status`) REFERENCES `user_status` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 48 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_info
-- ----------------------------
INSERT INTO `user_info` VALUES (1, 'zhangsan', '张三', '763f2de02b9028330f49663c392377a9b01f02279f49a864', '13879145689', '', 1, '2021-02-22 14:49:26', '2021-03-10 10:57:08');
INSERT INTO `user_info` VALUES (17, 'zhaoliu', '赵六3', 'f4b11258c837f1df56532362485d3287a43ba19b78505731', '15867890043', '12334@qq.com', 1, '2021-02-22 14:51:21', '2021-03-11 13:34:52');
INSERT INTO `user_info` VALUES (18, 'wangwu', '王五', '16ab61a92562b46b2959370c76fd28b9247692314f70d76f', '13879145689', '123@qq.com', 1, '2021-02-22 15:30:35', '2021-02-22 15:30:35');
INSERT INTO `user_info` VALUES (20, 'wangwu2', '王五2', '813299228e58646424a20b2115e306d88238469f24f1ac0e', '13879145689', '123@qq.com', 1, '2021-02-22 15:33:08', '2021-02-22 15:33:08');
INSERT INTO `user_info` VALUES (22, 'user1', '用户1', 'b8787f176263d95689f5f480e2b865b0a751b91d1632ba30', '158784579512', '12334@qq.com', 1, '2021-02-22 15:36:39', '2021-02-22 15:43:16');
INSERT INTO `user_info` VALUES (23, 'user2', '用户2', '06d388e18c2bc3da81b4609769617d64a594f60501a2ff1c', '15878457951', '12334@qq.com', 1, '2021-02-22 16:05:17', '2021-02-24 10:48:07');
INSERT INTO `user_info` VALUES (28, 'test1', '测试1', 'f6c58dc75423004f6c46bb7248d79424b743c41605859279', NULL, NULL, 1, '2021-02-24 15:33:35', '2021-02-24 15:33:35');
INSERT INTO `user_info` VALUES (34, 'admin', NULL, '139e7b60c568e39341379230b7493529e95b797534105a01', NULL, NULL, 1, '2021-02-25 13:24:36', '2021-02-25 13:24:36');
INSERT INTO `user_info` VALUES (35, 'wangwu8', '王五8', '88344550ed3225184e66742ce3416500ee9ab54194384645', '13879145689', '123@qq.com', 1, '2021-02-25 14:19:14', '2021-02-25 14:19:14');
INSERT INTO `user_info` VALUES (37, 'wangwu9', '王五9', 'a6270cc1820568c52949419056566fe9879a50a41b351094', '13879145689', '123@qq.com', 1, '2021-02-25 14:21:02', '2021-02-25 14:21:02');
INSERT INTO `user_info` VALUES (38, 'wangwu10', '王五10', '694698f7bb61e2bc67c0e396e6842e76fe4d317010c9562e', '13879145689', '123@qq.com', 1, '2021-02-25 14:21:42', '2021-02-25 14:21:42');
INSERT INTO `user_info` VALUES (39, 'wangwu12', '王五12', 'a8e724c9565262f43bf30f8fa3bd25e7d30b624762836695', '13879145689', '123@qq.com', 1, '2021-02-25 14:22:31', '2021-02-25 14:22:31');
INSERT INTO `user_info` VALUES (40, 'test', '测试数据', 'd8d765133e9462c36f280c4bb31a5cd9ff78621c0790a80a', NULL, NULL, 1, '2021-03-01 14:36:09', '2021-03-01 14:36:09');
INSERT INTO `user_info` VALUES (41, 'test5', 'test5', 'e3a129b3c24556d58112d77ec4725e12e73217460a96117c', NULL, NULL, 0, '2021-03-01 14:40:32', '2021-03-01 14:40:32');
INSERT INTO `user_info` VALUES (42, 'test1234', '测试', '525175b3fb3aa5c40f100e60806a87371236638968c0be04', '', 'lyr@163.com', 1, '2021-03-05 10:49:56', '2021-03-05 14:48:45');
INSERT INTO `user_info` VALUES (43, 'lisi02', 'aaa', '89b781950d41b35e17935961534357005f0e40413da22274', '18811451234', '1030355111@qq.com', 1, '2021-03-10 17:56:37', '2021-03-10 17:56:37');
INSERT INTO `user_info` VALUES (46, 'test22', '测试用户1', '967491e2fd67c8a75621865e927b6b970b3c08db1de4911f', '15263524152', '123456@163.com', 1, '2021-03-11 13:38:15', '2021-03-11 13:38:15');
INSERT INTO `user_info` VALUES (47, 'test33', '测试3', '852097269d9fc2cd69c85583527985b74f7874ea3205fb84', '18596857485', '123456@163.com', 1, '2021-03-11 13:43:54', '2021-03-11 13:43:54');

-- ----------------------------
-- Table structure for user_role
-- ----------------------------
DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role`  (
  `user_id` int(0) NOT NULL,
  `role_id` int(0) NOT NULL,
  PRIMARY KEY (`user_id`, `role_id`) USING BTREE,
  INDEX `user_role_ibfk_2`(`role_id`) USING BTREE,
  CONSTRAINT `user_role_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user_info` (`user_id`) ON DELETE CASCADE ON UPDATE RESTRICT,
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
) ENGINE = InnoDB AUTO_INCREMENT = 10149 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of ws_data
-- ----------------------------
INSERT INTO `ws_data` VALUES (9949, 'BUSINESS', '13c9f38d-985b-4693-9a07-b185e54f8634', 'admin', b'1', '测试点对点数据，持久化1624536210010', '/queue/message', b'0', '2021-06-24 20:03:30');
INSERT INTO `ws_data` VALUES (9950, 'BUSINESS', '0a37b4da-4033-42de-9282-94c778250a74', 'admin', b'1', '测试点对点数据，持久化1624536214420', '/queue/message', b'0', '2021-06-24 20:03:34');
INSERT INTO `ws_data` VALUES (9951, 'BUSINESS', '1027ad1a-4ddd-4358-adbb-c800e5656003', 'admin', b'1', '测试点对点数据，持久化1624536240008', '/queue/message', b'0', '2021-06-24 20:04:00');
INSERT INTO `ws_data` VALUES (9952, 'BUSINESS', '222baecd-2758-40f3-ac42-8e90acdbb6af', 'admin', b'1', '测试点对点数据，持久化1624536240009', '/queue/message', b'0', '2021-06-24 20:04:00');
INSERT INTO `ws_data` VALUES (9953, 'BUSINESS', 'b26980ac-ff33-4398-80bb-9e8a8be50cf4', 'admin', b'1', '测试点对点数据，持久化1624536270021', '/queue/message', b'0', '2021-06-24 20:04:30');
INSERT INTO `ws_data` VALUES (9954, 'BUSINESS', '8e3911c2-9954-41f9-abe4-4a6a4d808c1f', 'admin', b'1', '测试点对点数据，持久化1624536270023', '/queue/message', b'0', '2021-06-24 20:04:30');
INSERT INTO `ws_data` VALUES (9955, 'BUSINESS', '6e7f7417-50c7-400b-ac26-3936891075a9', 'admin', b'1', '测试点对点数据，持久化1624536300019', '/queue/message', b'0', '2021-06-24 20:05:00');
INSERT INTO `ws_data` VALUES (9956, 'BUSINESS', '7d142b1e-78a7-428d-a045-b650faf1b719', 'admin', b'1', '测试点对点数据，持久化1624536300018', '/queue/message', b'0', '2021-06-24 20:05:00');
INSERT INTO `ws_data` VALUES (9957, 'BUSINESS', 'a968899f-2ac3-4f42-a074-88da838753fe', 'admin', b'1', '测试点对点数据，持久化1624536330006', '/queue/message', b'0', '2021-06-24 20:05:30');
INSERT INTO `ws_data` VALUES (9958, 'BUSINESS', 'ff0f7378-b43f-4b08-bdbf-331d4e9f9988', 'admin', b'1', '测试点对点数据，持久化1624536330014', '/queue/message', b'0', '2021-06-24 20:05:30');
INSERT INTO `ws_data` VALUES (9959, 'BUSINESS', '1d02181c-4a4b-445d-8d52-2d6b7ff800da', 'admin', b'1', '测试点对点数据，持久化1624536360009', '/queue/message', b'0', '2021-06-24 20:06:00');
INSERT INTO `ws_data` VALUES (9960, 'BUSINESS', '7cbd58e4-6a0a-4247-8444-174f80d24ff1', 'admin', b'1', '测试点对点数据，持久化1624536360015', '/queue/message', b'0', '2021-06-24 20:06:00');
INSERT INTO `ws_data` VALUES (9961, 'BUSINESS', '1800b452-74f0-4eff-8a7e-1e2b5ac0f9a2', 'admin', b'1', '测试点对点数据，持久化1624536390007', '/queue/message', b'0', '2021-06-24 20:06:30');
INSERT INTO `ws_data` VALUES (9962, 'BUSINESS', 'f4578ef8-d3d5-4030-9f72-cc970aa8a977', 'admin', b'1', '测试点对点数据，持久化1624536390011', '/queue/message', b'0', '2021-06-24 20:06:30');
INSERT INTO `ws_data` VALUES (9963, 'BUSINESS', '610928cc-beae-4958-a83d-f5a8202a6628', 'admin', b'1', '测试点对点数据，持久化1624536420008', '/queue/message', b'0', '2021-06-24 20:07:00');
INSERT INTO `ws_data` VALUES (9964, 'BUSINESS', 'd4bf5cb8-f088-4ea3-94ca-eda608b0aceb', 'admin', b'1', '测试点对点数据，持久化1624536420016', '/queue/message', b'0', '2021-06-24 20:07:00');
INSERT INTO `ws_data` VALUES (9965, 'BUSINESS', '21f4ff58-9626-416a-afda-17de82fac842', 'admin', b'1', '测试点对点数据，持久化1624536450010', '/queue/message', b'0', '2021-06-24 20:07:30');
INSERT INTO `ws_data` VALUES (9966, 'BUSINESS', 'b93e25f1-4dcc-4b94-a9d6-82dd986a72ee', 'admin', b'1', '测试点对点数据，持久化1624536450009', '/queue/message', b'0', '2021-06-24 20:07:30');
INSERT INTO `ws_data` VALUES (9967, 'BUSINESS', '99087e36-a7bf-46db-8706-80f98784caa4', 'admin', b'1', '测试点对点数据，持久化1624536480017', '/queue/message', b'0', '2021-06-24 20:08:00');
INSERT INTO `ws_data` VALUES (9968, 'BUSINESS', 'd654f520-b960-4ce1-ace8-013d9d03ecbf', 'admin', b'1', '测试点对点数据，持久化1624536480017', '/queue/message', b'0', '2021-06-24 20:08:00');
INSERT INTO `ws_data` VALUES (9969, 'BUSINESS', '9f7b300d-ff46-4fdd-9112-cfc48d9e7278', 'admin', b'1', '测试点对点数据，持久化1624536510008', '/queue/message', b'0', '2021-06-24 20:08:30');
INSERT INTO `ws_data` VALUES (9970, 'BUSINESS', 'bb9fd073-d628-45a6-8794-e460145e108f', 'admin', b'1', '测试点对点数据，持久化1624536510016', '/queue/message', b'0', '2021-06-24 20:08:30');
INSERT INTO `ws_data` VALUES (9971, 'BUSINESS', '0c851d9c-6359-466a-ab37-34a10493ce2e', 'admin', b'1', '测试点对点数据，持久化1624536540010', '/queue/message', b'0', '2021-06-24 20:09:00');
INSERT INTO `ws_data` VALUES (9972, 'BUSINESS', '280926eb-994c-4c92-81a8-e49f02e2ec96', 'admin', b'1', '测试点对点数据，持久化1624536540011', '/queue/message', b'0', '2021-06-24 20:09:00');
INSERT INTO `ws_data` VALUES (9973, 'BUSINESS', '2c2406ab-3e58-4409-b8ab-f990f39fec06', 'admin', b'1', '测试点对点数据，持久化1624536570010', '/queue/message', b'0', '2021-06-24 20:09:30');
INSERT INTO `ws_data` VALUES (9974, 'BUSINESS', '87ab5588-6f93-4a67-8a40-e1dad6d2562d', 'admin', b'1', '测试点对点数据，持久化1624536570015', '/queue/message', b'0', '2021-06-24 20:09:30');
INSERT INTO `ws_data` VALUES (9975, 'BUSINESS', '86577804-8c78-425e-a77d-0d39b81cdf10', 'admin', b'1', '测试点对点数据，持久化1624536600009', '/queue/message', b'0', '2021-06-24 20:10:00');
INSERT INTO `ws_data` VALUES (9976, 'BUSINESS', 'b3425e87-5552-4701-b63c-11d88603fec7', 'admin', b'1', '测试点对点数据，持久化1624536600004', '/queue/message', b'0', '2021-06-24 20:10:00');
INSERT INTO `ws_data` VALUES (9977, 'BUSINESS', '15ddb7a5-253d-4f44-9aa7-b2d97f7e345b', 'admin', b'1', '测试点对点数据，持久化1624536630007', '/queue/message', b'0', '2021-06-24 20:10:30');
INSERT INTO `ws_data` VALUES (9978, 'BUSINESS', 'a5fbb2e5-edf8-46fe-b41a-2b2db5fdb9b1', 'admin', b'1', '测试点对点数据，持久化1624536630013', '/queue/message', b'0', '2021-06-24 20:10:30');
INSERT INTO `ws_data` VALUES (9979, 'BUSINESS', 'c576166e-4480-456b-a7d6-05ad86786146', 'admin', b'1', '测试点对点数据，持久化1624536660013', '/queue/message', b'0', '2021-06-24 20:11:00');
INSERT INTO `ws_data` VALUES (9980, 'BUSINESS', '39f2ed31-eb46-46b7-9af7-683fa4684ab8', 'admin', b'1', '测试点对点数据，持久化1624536660017', '/queue/message', b'0', '2021-06-24 20:11:00');
INSERT INTO `ws_data` VALUES (9981, 'BUSINESS', '45d48026-bce7-4c5d-af2b-892349d81678', 'admin', b'1', '测试点对点数据，持久化1624536690010', '/queue/message', b'0', '2021-06-24 20:11:30');
INSERT INTO `ws_data` VALUES (9982, 'BUSINESS', 'e7b0513e-5dd6-432b-8b97-72088cc052b5', 'admin', b'1', '测试点对点数据，持久化1624536690011', '/queue/message', b'0', '2021-06-24 20:11:30');
INSERT INTO `ws_data` VALUES (9983, 'BUSINESS', 'f0ce4373-2a69-4656-9c99-2b11411a1d48', 'admin', b'1', '测试点对点数据，持久化1624536720007', '/queue/message', b'0', '2021-06-24 20:12:00');
INSERT INTO `ws_data` VALUES (9984, 'BUSINESS', 'aa24a96f-e1c1-4545-9e7c-7ecd0451a43d', 'admin', b'1', '测试点对点数据，持久化1624536720010', '/queue/message', b'0', '2021-06-24 20:12:00');
INSERT INTO `ws_data` VALUES (9985, 'BUSINESS', 'd08bab21-8f6f-4dd4-9ac1-ae3829bdb2d5', 'admin', b'1', '测试点对点数据，持久化1624536750007', '/queue/message', b'0', '2021-06-24 20:12:30');
INSERT INTO `ws_data` VALUES (9986, 'BUSINESS', 'daf0600f-93fe-463d-8584-74c10ad2d255', 'admin', b'1', '测试点对点数据，持久化1624536750008', '/queue/message', b'0', '2021-06-24 20:12:30');
INSERT INTO `ws_data` VALUES (9987, 'BUSINESS', '4c12b1af-2f80-4e23-b4e3-c2ee2c8a5729', 'admin', b'1', '测试点对点数据，持久化1624536780003', '/queue/message', b'0', '2021-06-24 20:13:00');
INSERT INTO `ws_data` VALUES (9988, 'BUSINESS', 'be91d32a-8370-414f-87b8-b8d6e46d43dd', 'admin', b'1', '测试点对点数据，持久化1624536780008', '/queue/message', b'0', '2021-06-24 20:13:00');
INSERT INTO `ws_data` VALUES (9989, 'BUSINESS', '07609924-fb86-4bb1-8f6b-781708bf7ffc', 'admin', b'1', '测试点对点数据，持久化1624536810014', '/queue/message', b'0', '2021-06-24 20:13:30');
INSERT INTO `ws_data` VALUES (9990, 'BUSINESS', '82819894-2a2f-4ffd-ae38-6d4971f8d403', 'admin', b'1', '测试点对点数据，持久化1624536810018', '/queue/message', b'0', '2021-06-24 20:13:30');
INSERT INTO `ws_data` VALUES (9991, 'BUSINESS', '985e3168-3c63-449d-a249-6cff1e3501c9', 'admin', b'1', '测试点对点数据，持久化1624536840006', '/queue/message', b'0', '2021-06-24 20:14:00');
INSERT INTO `ws_data` VALUES (9992, 'BUSINESS', '60660c67-547d-4df9-b230-17133cf2dbba', 'admin', b'1', '测试点对点数据，持久化1624536840018', '/queue/message', b'0', '2021-06-24 20:14:00');
INSERT INTO `ws_data` VALUES (9993, 'BUSINESS', 'a4f64faa-3cce-4670-9dbe-190d9a2786ca', 'admin', b'1', '测试点对点数据，持久化1624536870014', '/queue/message', b'0', '2021-06-24 20:14:30');
INSERT INTO `ws_data` VALUES (9994, 'BUSINESS', 'f7da9d6a-6c2b-4999-baa8-6ba491d4ce20', 'admin', b'1', '测试点对点数据，持久化1624536870017', '/queue/message', b'0', '2021-06-24 20:14:30');
INSERT INTO `ws_data` VALUES (9995, 'BUSINESS', '0cef1310-b2f2-4757-8ae9-c8f21beb3734', 'admin', b'1', '测试点对点数据，持久化1624536900018', '/queue/message', b'0', '2021-06-24 20:15:00');
INSERT INTO `ws_data` VALUES (9996, 'BUSINESS', '459a6ee4-8cfa-4499-bc43-45d5c4991e2c', 'admin', b'1', '测试点对点数据，持久化1624536900018', '/queue/message', b'0', '2021-06-24 20:15:00');
INSERT INTO `ws_data` VALUES (9997, 'BUSINESS', '8662d93f-6a42-44a7-ba15-ed3ea0fe3852', 'admin', b'1', '测试点对点数据，持久化1624536930007', '/queue/message', b'0', '2021-06-24 20:15:30');
INSERT INTO `ws_data` VALUES (9998, 'BUSINESS', '8ddd7531-afc6-4f5f-ad66-8f4c4bc23e37', 'admin', b'1', '测试点对点数据，持久化1624536930003', '/queue/message', b'0', '2021-06-24 20:15:30');
INSERT INTO `ws_data` VALUES (9999, 'BUSINESS', '5cd0113c-bc39-40e9-b568-46dd3abf5b7c', 'admin', b'1', '测试点对点数据，持久化1624536960012', '/queue/message', b'0', '2021-06-24 20:16:00');
INSERT INTO `ws_data` VALUES (10000, 'BUSINESS', '71bf483f-1a54-48ef-ae84-490aa3396b06', 'admin', b'1', '测试点对点数据，持久化1624536960018', '/queue/message', b'0', '2021-06-24 20:16:00');
INSERT INTO `ws_data` VALUES (10001, 'BUSINESS', 'fb36a42a-a13a-4e46-a840-e42ed75dc385', 'admin', b'1', '测试点对点数据，持久化1624536990006', '/queue/message', b'0', '2021-06-24 20:16:30');
INSERT INTO `ws_data` VALUES (10002, 'BUSINESS', 'ae4898b9-1528-4760-a81c-42f4698dfa43', 'admin', b'1', '测试点对点数据，持久化1624536990013', '/queue/message', b'0', '2021-06-24 20:16:30');
INSERT INTO `ws_data` VALUES (10003, 'BUSINESS', 'c8685e67-f8be-4732-bd85-294866de1fc1', 'admin', b'1', '测试点对点数据，持久化1624537020015', '/queue/message', b'0', '2021-06-24 20:17:00');
INSERT INTO `ws_data` VALUES (10004, 'BUSINESS', 'de3498e0-937e-49e9-8c53-200a119c100b', 'admin', b'1', '测试点对点数据，持久化1624537020017', '/queue/message', b'0', '2021-06-24 20:17:00');
INSERT INTO `ws_data` VALUES (10005, 'BUSINESS', '56db3fb0-1d6f-4f62-bd0c-089b0d3d9a17', 'admin', b'1', '测试点对点数据，持久化1624537050015', '/queue/message', b'0', '2021-06-24 20:17:30');
INSERT INTO `ws_data` VALUES (10006, 'BUSINESS', '2a784869-6224-4726-8462-d52948ac1be5', 'admin', b'1', '测试点对点数据，持久化1624537050017', '/queue/message', b'0', '2021-06-24 20:17:30');
INSERT INTO `ws_data` VALUES (10007, 'BUSINESS', 'd7196e70-7f75-49c1-8a63-2f10603ea555', 'admin', b'1', '测试点对点数据，持久化1624537080019', '/queue/message', b'0', '2021-06-24 20:18:00');
INSERT INTO `ws_data` VALUES (10008, 'BUSINESS', '35c24187-c24e-4a6a-974a-798e15acf5ab', 'admin', b'1', '测试点对点数据，持久化1624537080018', '/queue/message', b'0', '2021-06-24 20:18:00');
INSERT INTO `ws_data` VALUES (10009, 'BUSINESS', '3e4fef4e-dbad-436e-b5ab-b4c46c33a25b', 'admin', b'1', '测试点对点数据，持久化1624537110013', '/queue/message', b'0', '2021-06-24 20:18:30');
INSERT INTO `ws_data` VALUES (10010, 'BUSINESS', '9160b06d-7572-45e7-9482-98b42ad6e6b3', 'admin', b'1', '测试点对点数据，持久化1624537110014', '/queue/message', b'0', '2021-06-24 20:18:30');
INSERT INTO `ws_data` VALUES (10011, 'BUSINESS', '996e3670-2315-4b53-aefc-f065afc39016', 'admin', b'1', '测试点对点数据，持久化1624537140014', '/queue/message', b'0', '2021-06-24 20:19:00');
INSERT INTO `ws_data` VALUES (10012, 'BUSINESS', '4dd0358c-c539-406a-ba06-6f064891d4ed', 'admin', b'1', '测试点对点数据，持久化1624537140030', '/queue/message', b'0', '2021-06-24 20:19:00');
INSERT INTO `ws_data` VALUES (10013, 'BUSINESS', '8d80d664-80ce-459e-8e68-048e2f6c3656', 'admin', b'1', '测试点对点数据，持久化1624537170006', '/queue/message', b'0', '2021-06-24 20:19:30');
INSERT INTO `ws_data` VALUES (10014, 'BUSINESS', '93021222-c13d-44d9-94d0-93b580333182', 'admin', b'1', '测试点对点数据，持久化1624537170016', '/queue/message', b'0', '2021-06-24 20:19:30');
INSERT INTO `ws_data` VALUES (10015, 'BUSINESS', 'd73abdec-cd35-46d7-88fe-c5672362e1b4', 'admin', b'1', '测试点对点数据，持久化1624537200008', '/queue/message', b'0', '2021-06-24 20:20:00');
INSERT INTO `ws_data` VALUES (10016, 'BUSINESS', 'c8fb95d6-7fe3-44b8-af80-6498989e056d', 'admin', b'1', '测试点对点数据，持久化1624537200016', '/queue/message', b'0', '2021-06-24 20:20:00');
INSERT INTO `ws_data` VALUES (10017, 'BUSINESS', 'b08383b8-520c-4f52-979e-28703529320b', 'admin', b'1', '测试点对点数据，持久化1624537230006', '/queue/message', b'0', '2021-06-24 20:20:30');
INSERT INTO `ws_data` VALUES (10018, 'BUSINESS', '7d2a3c7f-9890-4fc9-9f23-c45aead4694f', 'admin', b'1', '测试点对点数据，持久化1624537230006', '/queue/message', b'0', '2021-06-24 20:20:30');
INSERT INTO `ws_data` VALUES (10019, 'BUSINESS', '193a78bd-dd52-4e72-8b9f-837b5d524381', 'admin', b'1', '测试点对点数据，持久化1624537260018', '/queue/message', b'0', '2021-06-24 20:21:00');
INSERT INTO `ws_data` VALUES (10020, 'BUSINESS', 'ac9d70e2-d0da-4d61-b26e-aa46d127b51a', 'admin', b'1', '测试点对点数据，持久化1624537260010', '/queue/message', b'0', '2021-06-24 20:21:00');
INSERT INTO `ws_data` VALUES (10021, 'BUSINESS', 'a71af9e1-b749-4d26-8bfb-90cd8d6e4a40', 'admin', b'1', '测试点对点数据，持久化1624537290007', '/queue/message', b'0', '2021-06-24 20:21:30');
INSERT INTO `ws_data` VALUES (10022, 'BUSINESS', 'c0cc35ee-f605-46dd-ae9f-1adb7747e18d', 'admin', b'1', '测试点对点数据，持久化1624537290015', '/queue/message', b'0', '2021-06-24 20:21:30');
INSERT INTO `ws_data` VALUES (10023, 'BUSINESS', 'e4fb2420-13a2-40db-9e6d-516ea3e22d56', 'admin', b'1', '测试点对点数据，持久化1624537320007', '/queue/message', b'0', '2021-06-24 20:22:00');
INSERT INTO `ws_data` VALUES (10024, 'BUSINESS', '69e466b8-12be-4e1d-9bbb-f13cdb2571f9', 'admin', b'1', '测试点对点数据，持久化1624537320005', '/queue/message', b'0', '2021-06-24 20:22:00');
INSERT INTO `ws_data` VALUES (10025, 'BUSINESS', '6eb64592-8bf2-48ea-b8a5-b3ec1f9b875d', 'admin', b'1', '测试点对点数据，持久化1624537350008', '/queue/message', b'0', '2021-06-24 20:22:30');
INSERT INTO `ws_data` VALUES (10026, 'BUSINESS', 'a0e868e0-684b-4f0e-91ac-84f619438602', 'admin', b'1', '测试点对点数据，持久化1624537350017', '/queue/message', b'0', '2021-06-24 20:22:30');
INSERT INTO `ws_data` VALUES (10027, 'BUSINESS', '88bd8e29-ce94-49b8-a4ad-39ed59456960', 'admin', b'1', '测试点对点数据，持久化1624537380009', '/queue/message', b'0', '2021-06-24 20:23:00');
INSERT INTO `ws_data` VALUES (10028, 'BUSINESS', '39806680-cfc4-4979-b083-2e9aee14d916', 'admin', b'1', '测试点对点数据，持久化1624537380018', '/queue/message', b'0', '2021-06-24 20:23:00');
INSERT INTO `ws_data` VALUES (10029, 'BUSINESS', '3f0bfcb1-540c-4ccb-95f1-c95fd0837e22', 'admin', b'1', '测试点对点数据，持久化1624537410023', '/queue/message', b'0', '2021-06-24 20:23:30');
INSERT INTO `ws_data` VALUES (10030, 'BUSINESS', 'ba9d04b9-b47a-4f2f-a41d-61a279a5d997', 'admin', b'1', '测试点对点数据，持久化1624537410019', '/queue/message', b'0', '2021-06-24 20:23:30');
INSERT INTO `ws_data` VALUES (10031, 'BUSINESS', '3f2237a0-88a0-4a28-881d-0468b41cb67b', 'admin', b'1', '测试点对点数据，持久化1624537440007', '/queue/message', b'0', '2021-06-24 20:24:00');
INSERT INTO `ws_data` VALUES (10032, 'BUSINESS', 'e4e76224-1303-4dd2-8b30-287902c614ca', 'admin', b'1', '测试点对点数据，持久化1624537440011', '/queue/message', b'0', '2021-06-24 20:24:00');
INSERT INTO `ws_data` VALUES (10033, 'BUSINESS', 'e312694c-6df6-4de5-b3f9-40fc415a2b58', 'admin', b'1', '测试点对点数据，持久化1624537470011', '/queue/message', b'0', '2021-06-24 20:24:30');
INSERT INTO `ws_data` VALUES (10034, 'BUSINESS', '4b923606-84c3-4201-9217-12eaee7aa2b0', 'admin', b'1', '测试点对点数据，持久化1624537470016', '/queue/message', b'0', '2021-06-24 20:24:30');
INSERT INTO `ws_data` VALUES (10035, 'BUSINESS', 'e1859160-ca1b-4948-b899-0d91e56b2ae3', 'admin', b'1', '测试点对点数据，持久化1624537500007', '/queue/message', b'0', '2021-06-24 20:25:00');
INSERT INTO `ws_data` VALUES (10036, 'BUSINESS', '636a7a11-4002-43e8-adca-caba3fdf7485', 'admin', b'1', '测试点对点数据，持久化1624537500006', '/queue/message', b'0', '2021-06-24 20:25:00');
INSERT INTO `ws_data` VALUES (10037, 'BUSINESS', 'eebb6749-5d9d-48ba-a81d-1eb41ccd86e5', 'admin', b'1', '测试点对点数据，持久化1624537530009', '/queue/message', b'0', '2021-06-24 20:25:30');
INSERT INTO `ws_data` VALUES (10038, 'BUSINESS', '660ea8e0-130f-4057-bd0e-6ca532b8bb37', 'admin', b'1', '测试点对点数据，持久化1624537530020', '/queue/message', b'0', '2021-06-24 20:25:30');
INSERT INTO `ws_data` VALUES (10039, 'BUSINESS', 'de5a80e7-950a-4969-9c3a-b6766cbb84a7', 'admin', b'1', '测试点对点数据，持久化1624537560017', '/queue/message', b'0', '2021-06-24 20:26:00');
INSERT INTO `ws_data` VALUES (10040, 'BUSINESS', '8bc65e0f-b1fa-4e74-8279-4995342aef3e', 'admin', b'1', '测试点对点数据，持久化1624537560010', '/queue/message', b'0', '2021-06-24 20:26:00');
INSERT INTO `ws_data` VALUES (10041, 'BUSINESS', 'f1466207-c734-4be7-b1cb-c590817932b3', 'admin', b'1', '测试点对点数据，持久化1624537590006', '/queue/message', b'0', '2021-06-24 20:26:30');
INSERT INTO `ws_data` VALUES (10042, 'BUSINESS', '355a0852-7c3e-4fc2-a01c-a53643760f2c', 'admin', b'1', '测试点对点数据，持久化1624537590016', '/queue/message', b'0', '2021-06-24 20:26:30');
INSERT INTO `ws_data` VALUES (10043, 'BUSINESS', '87ba9063-b626-4128-9612-3efcf27da045', 'admin', b'1', '测试点对点数据，持久化1624537620007', '/queue/message', b'0', '2021-06-24 20:27:00');
INSERT INTO `ws_data` VALUES (10044, 'BUSINESS', 'db067f52-ffa2-4865-a2db-579e403f5092', 'admin', b'1', '测试点对点数据，持久化1624537620013', '/queue/message', b'0', '2021-06-24 20:27:00');
INSERT INTO `ws_data` VALUES (10045, 'BUSINESS', '9da189dc-626f-4a01-8780-67306bebcd0b', 'admin', b'1', '测试点对点数据，持久化1624537650007', '/queue/message', b'0', '2021-06-24 20:27:30');
INSERT INTO `ws_data` VALUES (10046, 'BUSINESS', 'cad4fcf5-113e-48d2-9c3f-35d182832ea6', 'admin', b'1', '测试点对点数据，持久化1624537650012', '/queue/message', b'0', '2021-06-24 20:27:30');
INSERT INTO `ws_data` VALUES (10047, 'BUSINESS', '9b040929-e040-463c-8c9e-bd9b4ec235db', 'admin', b'1', '测试点对点数据，持久化1624537680021', '/queue/message', b'0', '2021-06-24 20:28:00');
INSERT INTO `ws_data` VALUES (10048, 'BUSINESS', 'a4f586df-d5da-48b8-9f8b-6fd47e053ff8', 'admin', b'1', '测试点对点数据，持久化1624537680019', '/queue/message', b'0', '2021-06-24 20:28:00');
INSERT INTO `ws_data` VALUES (10049, 'BUSINESS', 'f1a31a43-215d-43a6-958a-30093962d656', 'admin', b'1', '测试点对点数据，持久化1624537710009', '/queue/message', b'0', '2021-06-24 20:28:30');
INSERT INTO `ws_data` VALUES (10050, 'BUSINESS', '3a4f6e22-6bb4-4553-b7f6-2afe21301cfa', 'admin', b'1', '测试点对点数据，持久化1624537710014', '/queue/message', b'0', '2021-06-24 20:28:30');
INSERT INTO `ws_data` VALUES (10051, 'BUSINESS', '62aa7266-b695-4753-948f-2ac37e3934fe', 'admin', b'1', '测试点对点数据，持久化1624537740010', '/queue/message', b'0', '2021-06-24 20:29:00');
INSERT INTO `ws_data` VALUES (10052, 'BUSINESS', '6f3bb9e8-a307-4cd1-965b-f99496c8c318', 'admin', b'1', '测试点对点数据，持久化1624537740015', '/queue/message', b'0', '2021-06-24 20:29:00');
INSERT INTO `ws_data` VALUES (10053, 'BUSINESS', '1f18eb88-5302-4a4a-8fc0-a3310cf31533', 'admin', b'1', '测试点对点数据，持久化1624537770007', '/queue/message', b'0', '2021-06-24 20:29:30');
INSERT INTO `ws_data` VALUES (10054, 'BUSINESS', '54fc4e0d-8fba-4d67-b21e-ffde9e355019', 'admin', b'1', '测试点对点数据，持久化1624537770006', '/queue/message', b'0', '2021-06-24 20:29:30');
INSERT INTO `ws_data` VALUES (10055, 'BUSINESS', '601d72c7-b03c-4f88-84e4-f0043ea76667', 'admin', b'1', '测试点对点数据，持久化1624537800009', '/queue/message', b'0', '2021-06-24 20:30:00');
INSERT INTO `ws_data` VALUES (10056, 'BUSINESS', '0c3dd31a-cccc-4075-abb0-372f417ad74d', 'admin', b'1', '测试点对点数据，持久化1624537800016', '/queue/message', b'0', '2021-06-24 20:30:00');
INSERT INTO `ws_data` VALUES (10057, 'BUSINESS', 'd6b22093-de0e-4e0e-afb1-b2a450b6f99a', 'admin', b'1', '测试点对点数据，持久化1624537830006', '/queue/message', b'0', '2021-06-24 20:30:30');
INSERT INTO `ws_data` VALUES (10058, 'BUSINESS', 'c0d7c8ff-da70-4dac-8bba-4b0e10f8de59', 'admin', b'1', '测试点对点数据，持久化1624537830005', '/queue/message', b'0', '2021-06-24 20:30:30');
INSERT INTO `ws_data` VALUES (10059, 'BUSINESS', '29634d5e-392f-49b2-a3f7-4092b07c938b', 'admin', b'1', '测试点对点数据，持久化1624537860010', '/queue/message', b'0', '2021-06-24 20:31:00');
INSERT INTO `ws_data` VALUES (10060, 'BUSINESS', '1e0074e4-fe69-4adb-87b3-81a520ee1daf', 'admin', b'1', '测试点对点数据，持久化1624537860010', '/queue/message', b'0', '2021-06-24 20:31:00');
INSERT INTO `ws_data` VALUES (10061, 'BUSINESS', '505a926f-cca5-4212-9eba-50f0e4b639b9', 'admin', b'1', '测试点对点数据，持久化1624537890006', '/queue/message', b'0', '2021-06-24 20:31:29');
INSERT INTO `ws_data` VALUES (10062, 'BUSINESS', '04f0ec3b-b64d-4f44-a714-6995ddbd4d87', 'admin', b'1', '测试点对点数据，持久化1624537890011', '/queue/message', b'0', '2021-06-24 20:31:30');
INSERT INTO `ws_data` VALUES (10063, 'BUSINESS', 'd24b0f83-b7c8-49bd-930c-591a8349a0d6', 'admin', b'1', '测试点对点数据，持久化1624537920016', '/queue/message', b'0', '2021-06-24 20:32:00');
INSERT INTO `ws_data` VALUES (10064, 'BUSINESS', 'b56f4dd5-c9bd-4277-9da1-dd891c44b82d', 'admin', b'1', '测试点对点数据，持久化1624537920014', '/queue/message', b'0', '2021-06-24 20:32:00');
INSERT INTO `ws_data` VALUES (10065, 'BUSINESS', '579b9694-5cde-45c3-824a-e582db115377', 'admin', b'1', '测试点对点数据，持久化1624537950013', '/queue/message', b'0', '2021-06-24 20:32:30');
INSERT INTO `ws_data` VALUES (10066, 'BUSINESS', '77c7c323-3e06-4ea3-87fb-da0914be35c6', 'admin', b'1', '测试点对点数据，持久化1624537950012', '/queue/message', b'0', '2021-06-24 20:32:30');
INSERT INTO `ws_data` VALUES (10067, 'BUSINESS', 'c78cbcb0-2218-4328-a54c-4a52f0bfd599', 'admin', b'1', '测试点对点数据，持久化1624537980008', '/queue/message', b'0', '2021-06-24 20:33:00');
INSERT INTO `ws_data` VALUES (10068, 'BUSINESS', 'c6f89d88-9568-423d-9fbd-71db010c6318', 'admin', b'1', '测试点对点数据，持久化1624537980015', '/queue/message', b'0', '2021-06-24 20:33:00');
INSERT INTO `ws_data` VALUES (10069, 'BUSINESS', '5a7f2e34-b7c5-4897-a062-e21163809f33', 'admin', b'1', '测试点对点数据，持久化1624538010018', '/queue/message', b'0', '2021-06-24 20:33:30');
INSERT INTO `ws_data` VALUES (10070, 'BUSINESS', 'cdb285b0-3f06-4a7f-ba20-ff9afdd85539', 'admin', b'1', '测试点对点数据，持久化1624538010018', '/queue/message', b'0', '2021-06-24 20:33:30');
INSERT INTO `ws_data` VALUES (10071, 'BUSINESS', '3929219d-8d23-42fd-b4dd-54852c43b286', 'admin', b'1', '测试点对点数据，持久化1624538040008', '/queue/message', b'0', '2021-06-24 20:34:00');
INSERT INTO `ws_data` VALUES (10072, 'BUSINESS', '112a95ef-01c7-48cc-a859-89de2db7c03a', 'admin', b'1', '测试点对点数据，持久化1624538040020', '/queue/message', b'0', '2021-06-24 20:34:00');
INSERT INTO `ws_data` VALUES (10073, 'BUSINESS', 'aa271c8d-c506-4519-a66f-35b1b3db4e07', 'admin', b'1', '测试点对点数据，持久化1624538070015', '/queue/message', b'0', '2021-06-24 20:34:30');
INSERT INTO `ws_data` VALUES (10074, 'BUSINESS', '5082aee4-2c83-4ab0-b1a2-d2ea11a8fa57', 'admin', b'1', '测试点对点数据，持久化1624538070018', '/queue/message', b'0', '2021-06-24 20:34:30');
INSERT INTO `ws_data` VALUES (10075, 'BUSINESS', 'ba486238-d70c-4d56-af07-7dea479e8ef0', 'admin', b'1', '测试点对点数据，持久化1624538100008', '/queue/message', b'0', '2021-06-24 20:35:00');
INSERT INTO `ws_data` VALUES (10076, 'BUSINESS', 'b776d587-8fd1-4566-b7ae-0b55fb7b0da2', 'admin', b'1', '测试点对点数据，持久化1624538100015', '/queue/message', b'0', '2021-06-24 20:35:00');
INSERT INTO `ws_data` VALUES (10077, 'BUSINESS', 'cb75cfa7-3bf2-419c-921c-e3a92406f393', 'admin', b'1', '测试点对点数据，持久化1624538130013', '/queue/message', b'0', '2021-06-24 20:35:30');
INSERT INTO `ws_data` VALUES (10078, 'BUSINESS', '39e9ce83-61c6-43ac-bdd1-0199c2ae264b', 'admin', b'1', '测试点对点数据，持久化1624538130012', '/queue/message', b'0', '2021-06-24 20:35:30');
INSERT INTO `ws_data` VALUES (10079, 'BUSINESS', '2410d2af-8825-4ae7-9f00-e9b8dc7d9934', 'admin', b'1', '测试点对点数据，持久化1624538160008', '/queue/message', b'0', '2021-06-24 20:36:00');
INSERT INTO `ws_data` VALUES (10080, 'BUSINESS', '89c0ff1d-5b6b-46be-8d64-e96af5b630a2', 'admin', b'1', '测试点对点数据，持久化1624538160009', '/queue/message', b'0', '2021-06-24 20:36:00');
INSERT INTO `ws_data` VALUES (10081, 'BUSINESS', '8c50588e-9f89-49a9-8570-eb678b08dd44', 'admin', b'1', '测试点对点数据，持久化1624538190008', '/queue/message', b'0', '2021-06-24 20:36:30');
INSERT INTO `ws_data` VALUES (10082, 'BUSINESS', '9409833c-7166-4bce-a949-6e5bb388a5b4', 'admin', b'1', '测试点对点数据，持久化1624538190035', '/queue/message', b'0', '2021-06-24 20:36:30');
INSERT INTO `ws_data` VALUES (10083, 'BUSINESS', '3541e86f-031e-4f10-bd58-d327ee5d7d36', 'admin', b'1', '测试点对点数据，持久化1624538220014', '/queue/message', b'0', '2021-06-24 20:37:00');
INSERT INTO `ws_data` VALUES (10084, 'BUSINESS', 'b4153651-36c3-4268-83b8-c189d0dae2d8', 'admin', b'1', '测试点对点数据，持久化1624538220017', '/queue/message', b'0', '2021-06-24 20:37:00');
INSERT INTO `ws_data` VALUES (10085, 'BUSINESS', '15a4e465-85c7-4fa0-9f2a-32475e0bc6e0', 'admin', b'1', '测试点对点数据，持久化1624538250008', '/queue/message', b'0', '2021-06-24 20:37:30');
INSERT INTO `ws_data` VALUES (10086, 'BUSINESS', 'fe73015d-9c04-49c1-be2e-c6f319914f99', 'admin', b'1', '测试点对点数据，持久化1624538250018', '/queue/message', b'0', '2021-06-24 20:37:30');
INSERT INTO `ws_data` VALUES (10087, 'BUSINESS', '8ad9bc24-1f38-4069-b421-ae5fee489110', 'admin', b'1', '测试点对点数据，持久化1624538280007', '/queue/message', b'0', '2021-06-24 20:38:00');
INSERT INTO `ws_data` VALUES (10088, 'BUSINESS', 'c9751c7c-34e2-4ef4-ba68-c7eaad6521ed', 'admin', b'1', '测试点对点数据，持久化1624538280012', '/queue/message', b'0', '2021-06-24 20:38:00');
INSERT INTO `ws_data` VALUES (10089, 'BUSINESS', '41bb0a40-0994-4ec1-91cd-286625c2d088', 'admin', b'1', '测试点对点数据，持久化1624538310011', '/queue/message', b'0', '2021-06-24 20:38:30');
INSERT INTO `ws_data` VALUES (10090, 'BUSINESS', 'fb06f077-c813-4d75-aa31-999c1e98bb06', 'admin', b'1', '测试点对点数据，持久化1624538310021', '/queue/message', b'0', '2021-06-24 20:38:30');
INSERT INTO `ws_data` VALUES (10091, 'BUSINESS', '5a448a04-b38c-4c4f-9d67-87066930464a', 'admin', b'1', '测试点对点数据，持久化1624538340006', '/queue/message', b'0', '2021-06-24 20:39:00');
INSERT INTO `ws_data` VALUES (10092, 'BUSINESS', '65c05c52-f73a-41a4-a551-8359683dd848', 'admin', b'1', '测试点对点数据，持久化1624538340022', '/queue/message', b'0', '2021-06-24 20:39:00');
INSERT INTO `ws_data` VALUES (10093, 'BUSINESS', '42c0c69b-c423-46d1-a796-0b31b45987e1', 'admin', b'1', '测试点对点数据，持久化1624538370011', '/queue/message', b'0', '2021-06-24 20:39:30');
INSERT INTO `ws_data` VALUES (10094, 'BUSINESS', '8cc7f5ba-a63b-49b4-bcbe-4254f5f767ba', 'admin', b'1', '测试点对点数据，持久化1624538370020', '/queue/message', b'0', '2021-06-24 20:39:30');
INSERT INTO `ws_data` VALUES (10095, 'BUSINESS', 'f2cf332d-ea63-4d41-adc4-ea7e918688d9', 'admin', b'1', '测试点对点数据，持久化1624538400007', '/queue/message', b'0', '2021-06-24 20:40:00');
INSERT INTO `ws_data` VALUES (10096, 'BUSINESS', '545e3c10-58f8-4cd7-95f3-dbd3fdcc7a3e', 'admin', b'1', '测试点对点数据，持久化1624538400015', '/queue/message', b'0', '2021-06-24 20:40:00');
INSERT INTO `ws_data` VALUES (10097, 'BUSINESS', 'b9643c75-21f5-4ad0-8fc4-3f50b6b5ef7b', 'admin', b'1', '测试点对点数据，持久化1624538430018', '/queue/message', b'0', '2021-06-24 20:40:30');
INSERT INTO `ws_data` VALUES (10098, 'BUSINESS', 'd44285dc-40c7-47fb-bac4-60dc7269c42b', 'admin', b'1', '测试点对点数据，持久化1624538430013', '/queue/message', b'0', '2021-06-24 20:40:30');
INSERT INTO `ws_data` VALUES (10099, 'BUSINESS', 'ff4ceb38-88c1-4294-9509-937b99d6adcc', 'admin', b'1', '测试点对点数据，持久化1624538460012', '/queue/message', b'0', '2021-06-24 20:41:00');
INSERT INTO `ws_data` VALUES (10100, 'BUSINESS', 'a4ad41b0-f476-4418-899b-72c921cd89c8', 'admin', b'1', '测试点对点数据，持久化1624538460014', '/queue/message', b'0', '2021-06-24 20:41:00');
INSERT INTO `ws_data` VALUES (10101, 'BUSINESS', '92dee733-363d-4e8b-9d79-52a4cb247213', 'admin', b'1', '测试点对点数据，持久化1624538490011', '/queue/message', b'0', '2021-06-24 20:41:30');
INSERT INTO `ws_data` VALUES (10102, 'BUSINESS', '54469855-2743-4376-87a3-18d4d77b035b', 'admin', b'1', '测试点对点数据，持久化1624538490010', '/queue/message', b'0', '2021-06-24 20:41:30');
INSERT INTO `ws_data` VALUES (10103, 'BUSINESS', 'f9d5346b-7394-46eb-9bdf-244feda6e662', 'admin', b'1', '测试点对点数据，持久化1624538520012', '/queue/message', b'0', '2021-06-24 20:42:00');
INSERT INTO `ws_data` VALUES (10104, 'BUSINESS', 'fa91bd76-ae95-42d2-913e-c1c814c31142', 'admin', b'1', '测试点对点数据，持久化1624538520006', '/queue/message', b'0', '2021-06-24 20:42:00');
INSERT INTO `ws_data` VALUES (10105, 'BUSINESS', '3ea82c2a-fc2f-4089-a2b8-aa59b95392c5', 'admin', b'1', '测试点对点数据，持久化1624538550011', '/queue/message', b'0', '2021-06-24 20:42:30');
INSERT INTO `ws_data` VALUES (10106, 'BUSINESS', 'ed11eaf7-fcc3-4f33-afbb-e1c1af77e2b0', 'admin', b'1', '测试点对点数据，持久化1624538550007', '/queue/message', b'0', '2021-06-24 20:42:30');
INSERT INTO `ws_data` VALUES (10107, 'BUSINESS', 'a91d9ec4-5058-45cb-a64a-8bcd3989f6b2', 'admin', b'1', '测试点对点数据，持久化1624538580006', '/queue/message', b'0', '2021-06-24 20:43:00');
INSERT INTO `ws_data` VALUES (10108, 'BUSINESS', 'c2acb385-3f43-4ae0-9693-ca6bed3e5428', 'admin', b'1', '测试点对点数据，持久化1624538580017', '/queue/message', b'0', '2021-06-24 20:43:00');
INSERT INTO `ws_data` VALUES (10109, 'BUSINESS', 'f7a452db-b913-4c9a-b36b-5abf65521bfe', 'admin', b'1', '测试点对点数据，持久化1624538610013', '/queue/message', b'0', '2021-06-24 20:43:30');
INSERT INTO `ws_data` VALUES (10110, 'BUSINESS', '6ceac07e-3783-4257-9f5c-9940e05f2d3b', 'admin', b'1', '测试点对点数据，持久化1624538610015', '/queue/message', b'0', '2021-06-24 20:43:30');
INSERT INTO `ws_data` VALUES (10111, 'BUSINESS', '65375a04-8d31-4107-8e3b-90be886ba444', 'admin', b'1', '测试点对点数据，持久化1624538640017', '/queue/message', b'0', '2021-06-24 20:44:00');
INSERT INTO `ws_data` VALUES (10112, 'BUSINESS', '5b28b8f0-ea50-4634-b0ca-eb53878341da', 'admin', b'1', '测试点对点数据，持久化1624538640016', '/queue/message', b'0', '2021-06-24 20:44:00');
INSERT INTO `ws_data` VALUES (10113, 'BUSINESS', 'ed866ba0-8b71-4b6d-ae8a-736742472343', 'admin', b'1', '测试点对点数据，持久化1624538670022', '/queue/message', b'0', '2021-06-24 20:44:30');
INSERT INTO `ws_data` VALUES (10114, 'BUSINESS', '87b3b1b7-c1d6-41ee-8fff-a12d1dbdecda', 'admin', b'1', '测试点对点数据，持久化1624538670016', '/queue/message', b'0', '2021-06-24 20:44:30');
INSERT INTO `ws_data` VALUES (10115, 'BUSINESS', '324843fa-93c1-4360-a11f-a0ee5f3e14b6', 'admin', b'1', '测试点对点数据，持久化1624538700016', '/queue/message', b'0', '2021-06-24 20:45:00');
INSERT INTO `ws_data` VALUES (10116, 'BUSINESS', '61c9d9ca-0dce-473a-b018-9cf00f1308e5', 'admin', b'1', '测试点对点数据，持久化1624538700017', '/queue/message', b'0', '2021-06-24 20:45:00');
INSERT INTO `ws_data` VALUES (10117, 'BUSINESS', 'e434c28f-53d0-4c6f-8211-287299b9cf46', 'admin', b'1', '测试点对点数据，持久化1624538730010', '/queue/message', b'0', '2021-06-24 20:45:30');
INSERT INTO `ws_data` VALUES (10118, 'BUSINESS', '7299e262-af66-4e76-994d-24cc914a27db', 'admin', b'1', '测试点对点数据，持久化1624538730013', '/queue/message', b'0', '2021-06-24 20:45:30');
INSERT INTO `ws_data` VALUES (10119, 'BUSINESS', 'cede515c-1478-40bc-aa8b-f891e22295f0', 'admin', b'1', '测试点对点数据，持久化1624538760007', '/queue/message', b'0', '2021-06-24 20:46:00');
INSERT INTO `ws_data` VALUES (10120, 'BUSINESS', '0b432a2e-01ca-47de-b95a-32c170936efa', 'admin', b'1', '测试点对点数据，持久化1624538760008', '/queue/message', b'0', '2021-06-24 20:46:00');
INSERT INTO `ws_data` VALUES (10121, 'BUSINESS', 'b9f603b8-66a8-4a77-b4a0-5ca33c480770', 'admin', b'1', '测试点对点数据，持久化1624538790011', '/queue/message', b'0', '2021-06-24 20:46:30');
INSERT INTO `ws_data` VALUES (10122, 'BUSINESS', '408bc06c-cc14-40a2-a278-f4d6b7fa80db', 'admin', b'1', '测试点对点数据，持久化1624538790010', '/queue/message', b'0', '2021-06-24 20:46:30');
INSERT INTO `ws_data` VALUES (10123, 'BUSINESS', '23ff8fcc-3de5-4af1-99c6-05371f8a92b0', 'admin', b'1', '测试点对点数据，持久化1624538820006', '/queue/message', b'0', '2021-06-24 20:46:59');
INSERT INTO `ws_data` VALUES (10124, 'BUSINESS', '545a97f3-991a-4207-b163-dcbd205231a0', 'admin', b'1', '测试点对点数据，持久化1624538820017', '/queue/message', b'0', '2021-06-24 20:47:00');
INSERT INTO `ws_data` VALUES (10125, 'BUSINESS', '4307eba1-cc00-460a-8567-904c03a2b9b1', 'admin', b'1', '测试点对点数据，持久化1624538850018', '/queue/message', b'0', '2021-06-24 20:47:30');
INSERT INTO `ws_data` VALUES (10126, 'BUSINESS', '0d5f3e53-ba45-40b6-932e-4f2ca19571a4', 'admin', b'1', '测试点对点数据，持久化1624538850013', '/queue/message', b'0', '2021-06-24 20:47:30');
INSERT INTO `ws_data` VALUES (10127, 'BUSINESS', 'eac9a6de-c5df-45a4-8088-6e640b4dd21d', 'admin', b'1', '测试点对点数据，持久化1624538880006', '/queue/message', b'0', '2021-06-24 20:48:00');
INSERT INTO `ws_data` VALUES (10128, 'BUSINESS', '985f044d-4de7-4791-ad8c-2a5dd96d0d39', 'admin', b'1', '测试点对点数据，持久化1624538880008', '/queue/message', b'0', '2021-06-24 20:48:00');
INSERT INTO `ws_data` VALUES (10129, 'BUSINESS', 'eb2fea35-b2c6-467b-9bd7-a3065fa3687c', 'admin', b'1', '测试点对点数据，持久化1624538910008', '/queue/message', b'0', '2021-06-24 20:48:30');
INSERT INTO `ws_data` VALUES (10130, 'BUSINESS', 'fb9934eb-c02c-4f55-af03-1aa22a96b395', 'admin', b'1', '测试点对点数据，持久化1624538910016', '/queue/message', b'0', '2021-06-24 20:48:30');
INSERT INTO `ws_data` VALUES (10131, 'BUSINESS', '6f1f0888-27c1-4242-b296-22f76c1662cc', 'admin', b'1', '测试点对点数据，持久化1624538940007', '/queue/message', b'0', '2021-06-24 20:49:00');
INSERT INTO `ws_data` VALUES (10132, 'BUSINESS', '7a3acb55-0416-43f5-aa04-2de8246376e4', 'admin', b'1', '测试点对点数据，持久化1624538940011', '/queue/message', b'0', '2021-06-24 20:49:00');
INSERT INTO `ws_data` VALUES (10133, 'BUSINESS', '8d532fa0-82d2-4488-bd40-10400fc10974', 'admin', b'1', '测试点对点数据，持久化1624538970012', '/queue/message', b'0', '2021-06-24 20:49:30');
INSERT INTO `ws_data` VALUES (10134, 'BUSINESS', 'b771b7d2-a719-4d5c-b02f-e6bb11c5b355', 'admin', b'1', '测试点对点数据，持久化1624538970018', '/queue/message', b'0', '2021-06-24 20:49:30');
INSERT INTO `ws_data` VALUES (10135, 'BUSINESS', '53b0c971-cbdb-4269-9434-15d91078d639', 'admin', b'1', '测试点对点数据，持久化1624539000007', '/queue/message', b'0', '2021-06-24 20:49:59');
INSERT INTO `ws_data` VALUES (10136, 'BUSINESS', '321aa99f-b809-4390-bb00-f7a6a15bb57f', 'admin', b'1', '测试点对点数据，持久化1624539000008', '/queue/message', b'0', '2021-06-24 20:50:00');
INSERT INTO `ws_data` VALUES (10137, 'BUSINESS', '9608099b-8de7-46ba-9195-db181c640eb5', 'admin', b'1', '测试点对点数据，持久化1624539030010', '/queue/message', b'0', '2021-06-24 20:50:30');
INSERT INTO `ws_data` VALUES (10138, 'BUSINESS', '0a6e52ff-ae2b-4ce0-b830-28cc9b5c95a2', 'admin', b'1', '测试点对点数据，持久化1624539030003', '/queue/message', b'0', '2021-06-24 20:50:30');
INSERT INTO `ws_data` VALUES (10139, 'BUSINESS', 'fb444892-83da-4e4d-93bb-170a1130d670', 'admin', b'1', '测试点对点数据，持久化1624539060007', '/queue/message', b'0', '2021-06-24 20:51:00');
INSERT INTO `ws_data` VALUES (10140, 'BUSINESS', '14a9cf59-a0ea-4484-bd7d-451db0015559', 'admin', b'1', '测试点对点数据，持久化1624539060017', '/queue/message', b'0', '2021-06-24 20:51:00');
INSERT INTO `ws_data` VALUES (10141, 'BUSINESS', '9985534b-f36d-4ffa-b4f8-5036b0cc5131', 'admin', b'1', '测试点对点数据，持久化1624539090010', '/queue/message', b'0', '2021-06-24 20:51:30');
INSERT INTO `ws_data` VALUES (10142, 'BUSINESS', 'fa9e8ab9-6602-4690-8df4-bf9875c754e7', 'admin', b'1', '测试点对点数据，持久化1624539090013', '/queue/message', b'0', '2021-06-24 20:51:30');
INSERT INTO `ws_data` VALUES (10143, 'BUSINESS', 'c9134d6c-e9b8-452f-8975-fe4a4e3f94ea', 'admin', b'1', '测试点对点数据，持久化1624539120006', '/queue/message', b'0', '2021-06-24 20:51:59');
INSERT INTO `ws_data` VALUES (10144, 'BUSINESS', 'fbb4c5f1-f1bc-4c35-94b5-74d3f176404d', 'admin', b'1', '测试点对点数据，持久化1624539120013', '/queue/message', b'0', '2021-06-24 20:52:00');
INSERT INTO `ws_data` VALUES (10145, 'BUSINESS', '4ac033e1-e147-4442-b50f-93ec3d8b3104', 'admin', b'1', '测试点对点数据，持久化1624539150019', '/queue/message', b'0', '2021-06-24 20:52:30');
INSERT INTO `ws_data` VALUES (10146, 'BUSINESS', '2fa7be77-1a05-4942-916c-0b4c9ccbe679', 'admin', b'1', '测试点对点数据，持久化1624539150015', '/queue/message', b'0', '2021-06-24 20:52:30');
INSERT INTO `ws_data` VALUES (10147, 'BUSINESS', 'bfde11bd-746e-4870-9414-6ae200cd953d', 'admin', b'1', '测试点对点数据，持久化1624539180007', '/queue/message', b'0', '2021-06-24 20:53:00');
INSERT INTO `ws_data` VALUES (10148, 'BUSINESS', '33aa6f78-3457-4e3e-95be-99293172adb4', 'admin', b'1', '测试点对点数据，持久化1624539180014', '/queue/message', b'0', '2021-06-24 20:53:00');

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

SET FOREIGN_KEY_CHECKS = 1;
