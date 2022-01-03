/*
 Navicat Premium Data Transfer

 Source Server         : 192.168.100.88
 Source Server Type    : MySQL
 Source Server Version : 80022
 Source Host           : 192.168.100.88:3306
 Source Schema         : newframe

 Target Server Type    : MySQL
 Target Server Version : 80022
 File Encoding         : 65001

 Date: 03/12/2021 15:21:59
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
  `dict_type` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '业务类',
  `dict_data_type` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `dict_label` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `dict_desc` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `create_time` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `create_by` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `update_time` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `update_by` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of dict_data
-- ----------------------------
INSERT INTO `dict_data` VALUES (1, '系统类', 'enum_test', '枚举测试', '测试枚举更新测试', '2021-03-01 17:29:29', 'unknown', '2021-03-01 17:31:06', 'unknown');
INSERT INTO `dict_data` VALUES (2, '业务类', 'user_status', '用户状态', '', '2021-03-05 12:12:03', 'admin', '2021-03-05 14:55:11', 'unknown');
INSERT INTO `dict_data` VALUES (5, '业务类', 'module', '所属模块', '描述所有zm子系统包含的模块', '2021-09-06 14:06:35', 'admin', NULL, NULL);

-- ----------------------------
-- Table structure for dict_data_type
-- ----------------------------
DROP TABLE IF EXISTS `dict_data_type`;
CREATE TABLE `dict_data_type`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `dict_data_type` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `dict_data_key` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `dict_data_value` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `dict_data_desc` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `create_time` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `create_by` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `update_time` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `update_by` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 14 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

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
-- Table structure for hibernate_sequence
-- ----------------------------
DROP TABLE IF EXISTS `hibernate_sequence`;
CREATE TABLE `hibernate_sequence`  (
  `next_val` bigint(0) NULL DEFAULT NULL
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of hibernate_sequence
-- ----------------------------
INSERT INTO `hibernate_sequence` VALUES (1);

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
) ENGINE = InnoDB AUTO_INCREMENT = 55 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for menu
-- ----------------------------
DROP TABLE IF EXISTS `menu`;
CREATE TABLE `menu`  (
  `menu_id` int(0) NOT NULL AUTO_INCREMENT,
  `menu_pid` int(0) NULL DEFAULT NULL,
  `menu_page` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '前端的路径',
  `menu_create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `menu_update_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP COMMENT '最后修改时间',
  `menu_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`menu_id`) USING BTREE,
  UNIQUE INDEX `menu_name`(`menu_name`) USING BTREE,
  INDEX `menu_ibfk_1`(`menu_pid`) USING BTREE,
  CONSTRAINT `menu_ibfk_1` FOREIGN KEY (`menu_pid`) REFERENCES `menu` (`menu_id`) ON DELETE CASCADE ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 49 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

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
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

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
  `op_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '操作名称',
  `op_create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `op_update_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '最后修改时间',
  PRIMARY KEY (`op_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 15 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

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
  CONSTRAINT `opration_resource_ibfk_2` FOREIGN KEY (`resource_id`) REFERENCES `resources` (`resource_id`) ON DELETE CASCADE ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

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
  `org_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '组织机构名称',
  `org_pid` int(0) NULL DEFAULT NULL COMMENT '上级组织机构',
  `org_desc` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '组织机构描述',
  `org_create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `org_update_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '最后修改时间',
  PRIMARY KEY (`org_id`) USING BTREE,
  UNIQUE INDEX `org_name`(`org_name`) USING BTREE,
  INDEX `org_ibfk_1`(`org_pid`) USING BTREE,
  CONSTRAINT `org_ibfk_1` FOREIGN KEY (`org_pid`) REFERENCES `org` (`org_id`) ON DELETE CASCADE ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 47 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

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
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

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
INSERT INTO `org_role` VALUES (3, 5);
INSERT INTO `org_role` VALUES (4, 5);
INSERT INTO `org_role` VALUES (6, 5);
INSERT INTO `org_role` VALUES (7, 5);
INSERT INTO `org_role` VALUES (8, 5);
INSERT INTO `org_role` VALUES (9, 5);
INSERT INTO `org_role` VALUES (10, 5);
INSERT INTO `org_role` VALUES (14, 5);
INSERT INTO `org_role` VALUES (35, 5);
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
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

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
  `param_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `param_key` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `param_value` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `param_type` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '业务类',
  `create_by` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `create_time` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `update_by` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `update_time` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `param_desc` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 16 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

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
-- Table structure for resources
-- ----------------------------
DROP TABLE IF EXISTS `resources`;
CREATE TABLE `resources`  (
  `resource_id` int(0) NOT NULL AUTO_INCREMENT COMMENT '资源ID',
  `resource_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '后端URL',
  `resource_desc` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '资源描述',
  `resource_create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `resource_update_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '最后修改时间',
  PRIMARY KEY (`resource_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 25 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of resources
-- ----------------------------
INSERT INTO `resources` VALUES (1, '/menu', '获取菜单树', '2021-02-22 17:49:58', '2021-02-22 17:51:39');
INSERT INTO `resources` VALUES (2, '/menu/node/*', '编辑菜单', '2021-02-22 17:50:39', '2021-02-22 17:54:52');
INSERT INTO `resources` VALUES (3, '/user/header', '获取用户表头', '2021-02-22 17:53:52', '2021-02-22 17:53:52');
INSERT INTO `resources` VALUES (4, '/user/del', '删除用户', '2021-02-22 17:54:15', '2021-02-22 17:54:34');
INSERT INTO `resources` VALUES (5, '/user/data', '编辑用户', '2021-02-22 17:54:32', '2021-02-22 17:54:32');
INSERT INTO `resources` VALUES (6, '/org', '获取组织机构树', '2021-02-22 17:55:09', '2021-02-22 17:55:09');
INSERT INTO `resources` VALUES (7, '/org/node/*', '编辑组织机构', '2021-02-22 17:55:30', '2021-02-22 17:55:30');
INSERT INTO `resources` VALUES (8, '/role/header', '获取角色表头', '2021-02-22 17:56:20', '2021-02-22 17:56:20');
INSERT INTO `resources` VALUES (9, '/user', '获取用户', '2021-02-22 17:56:36', '2021-02-22 17:56:36');
INSERT INTO `resources` VALUES (10, '/role', '获取角色', '2021-02-22 17:56:54', '2021-02-22 17:56:54');
INSERT INTO `resources` VALUES (11, '/role/del', '删除角色', '2021-02-22 17:57:11', '2021-02-22 17:57:11');
INSERT INTO `resources` VALUES (12, '/role/data', '编辑角色', '2021-02-22 17:57:25', '2021-02-22 17:57:25');
INSERT INTO `resources` VALUES (13, '/param/getHeader', '获取参数表头', '2021-03-10 09:37:43', '2021-03-10 09:48:38');
INSERT INTO `resources` VALUES (14, '/param/getData', '获取参数', '2021-03-10 09:38:21', '2021-03-10 09:48:46');
INSERT INTO `resources` VALUES (15, '/param/del', '删除参数', '2021-03-10 09:38:48', '2021-03-10 09:38:48');
INSERT INTO `resources` VALUES (16, '/param/data', '编辑参数', '2021-03-10 09:39:19', '2021-03-10 09:39:19');
INSERT INTO `resources` VALUES (17, '/dictData/getHeader', '获取字典数据表头', '2021-03-10 09:46:00', '2021-03-10 09:50:08');
INSERT INTO `resources` VALUES (18, '/dictData/getData', '获字典数据', '2021-03-10 09:46:04', '2021-03-10 09:50:10');
INSERT INTO `resources` VALUES (19, '/dictData/del', '删除字典数据', '2021-03-10 09:46:09', '2021-03-10 09:50:12');
INSERT INTO `resources` VALUES (20, '/dictData/data', '编辑字典数据', '2021-03-10 09:46:14', '2021-03-10 09:50:15');
INSERT INTO `resources` VALUES (21, '/dictDataType/getHeader', '获取字典类型数据表头', '2021-03-10 09:46:00', '2021-03-10 09:50:08');
INSERT INTO `resources` VALUES (22, '/dictDataType/getData', '获字典类型数据', '2021-03-10 09:46:04', '2021-03-10 09:50:10');
INSERT INTO `resources` VALUES (23, '/dictDataType/del', '删除字典类型数据', '2021-03-10 09:46:09', '2021-03-10 09:50:12');
INSERT INTO `resources` VALUES (24, '/dictDataType/data', '编辑字典类型数据', '2021-03-10 09:46:14', '2021-03-10 09:50:15');

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role`  (
  `role_id` int(0) NOT NULL AUTO_INCREMENT,
  `role_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '角色名称',
  `role_create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP,
  `role_update_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`role_id`) USING BTREE,
  UNIQUE INDEX `role_name`(`role_name`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 16 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES (2, '测试角色1', '2021-02-22 10:23:19', '2021-02-25 09:29:22');
INSERT INTO `role` VALUES (4, '普通用户11', '2021-02-22 14:35:01', '2021-02-25 09:29:22');
INSERT INTO `role` VALUES (5, '普通用户12', '2021-02-22 14:35:20', '2021-02-25 09:29:23');
INSERT INTO `role` VALUES (7, '普通用户13', '2021-02-22 14:41:27', '2021-02-25 09:29:24');
INSERT INTO `role` VALUES (11, 'super', NULL, NULL);
INSERT INTO `role` VALUES (12, '测试角色2', '2021-09-02 09:33:34', '2021-09-02 09:33:34');
INSERT INTO `role` VALUES (13, '测试角色', '2021-09-06 10:53:37', '2021-09-06 10:53:37');
INSERT INTO `role` VALUES (14, '管理员角色', '2021-09-06 10:54:01', '2021-09-06 10:54:01');

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
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of role_menu
-- ----------------------------
INSERT INTO `role_menu` VALUES (11, 27);
INSERT INTO `role_menu` VALUES (12, 27);
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
-- Table structure for role_opration
-- ----------------------------
DROP TABLE IF EXISTS `role_opration`;
CREATE TABLE `role_opration`  (
  `role_id` int(0) NOT NULL COMMENT '角色ID',
  `op_id` int(0) NOT NULL COMMENT '操作标识ID',
  PRIMARY KEY (`role_id`, `op_id`) USING BTREE,
  INDEX `op_id`(`op_id`) USING BTREE,
  CONSTRAINT `role_opration_ibfk_1` FOREIGN KEY (`role_id`) REFERENCES `role` (`role_id`) ON DELETE CASCADE ON UPDATE RESTRICT,
  CONSTRAINT `role_opration_ibfk_2` FOREIGN KEY (`op_id`) REFERENCES `opration` (`op_id`) ON DELETE CASCADE ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

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
INSERT INTO `shedlock` VALUES ('shedLockTest', '2021-12-03 15:01:24.105', '2021-12-03 15:01:14.242', 'DESKTOP-4OHH3FF');

-- ----------------------------
-- Table structure for test
-- ----------------------------
DROP TABLE IF EXISTS `test`;
CREATE TABLE `test`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `age` int(0),
  `flag` int DEFAULT '0',

  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of test
-- ----------------------------
INSERT INTO `test` VALUES (1, '1', 1, 0);
INSERT INTO `test` VALUES (2, '2', 2, 0);
INSERT INTO `test` VALUES (3, '3', 44, 0);
INSERT INTO `test` VALUES (4, '4', 23, 0);
INSERT INTO `test` VALUES (5, '5', 18, 0);

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
  `user_name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户名',
  `user_real_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '真实姓名',
  `user_pwd` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '密码',
  `user_tel` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '电话号码',
  `user_email` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '邮箱',
  `user_status` smallint(0) NULL DEFAULT 1 COMMENT '0 停用 1 启用',
  `user_create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `user_update_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '最后修改时间',
  PRIMARY KEY (`user_id`) USING BTREE,
  UNIQUE INDEX `user_name`(`user_name`) USING BTREE,
  INDEX `user_ibfk_1`(`user_status`) USING BTREE,
  CONSTRAINT `user_info_ibfk_1` FOREIGN KEY (`user_status`) REFERENCES `user_status` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 52 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_info
-- ----------------------------
INSERT INTO `user_info` VALUES (20, 'wangwu2', '王五2', 'b99119617b5af32394b8d90af1189ca70216353385b07172', '13879145689', '123@qq.com', 1, '2021-02-22 15:33:08', '2021-11-17 14:04:22');
INSERT INTO `user_info` VALUES (22, 'user1', '用户1', 'b8787f176263d95689f5f480e2b865b0a751b91d1632ba30', '15878457951', '12334@qq.com', 1, '2021-02-22 15:36:39', '2021-12-03 15:21:09');
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
INSERT INTO `user_info` VALUES (48, 'test123qwer', 'hyq', 'b1a33d015b9545385200fa2bd7a07179508122491472bb0c', '', '', 1, '2021-09-07 17:55:40', '2021-09-07 17:55:40');

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
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_role
-- ----------------------------
INSERT INTO `user_role` VALUES (22, 2);
INSERT INTO `user_role` VALUES (23, 2);
INSERT INTO `user_role` VALUES (39, 2);
INSERT INTO `user_role` VALUES (46, 2);
INSERT INTO `user_role` VALUES (47, 2);
INSERT INTO `user_role` VALUES (40, 4);
INSERT INTO `user_role` VALUES (43, 4);
INSERT INTO `user_role` VALUES (40, 5);
INSERT INTO `user_role` VALUES (41, 5);
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
  `user_status_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

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
  `type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `msg_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `user_identify` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `persistent` bit(1) NULL DEFAULT b'1',
  `data` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL,
  `destination` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `ack` bit(1) NULL DEFAULT b'0',
  `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 15089 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of ws_data
-- ----------------------------
INSERT INTO `ws_data` VALUES (14889, 'BUSINESS', 'a447bb64-4d5d-467e-a596-164fc3333f14', 'admin', b'1', '测试点对点数据，持久化1638433650120', '/queue/message', b'0', '2021-12-02 16:27:30');
INSERT INTO `ws_data` VALUES (14890, 'BUSINESS', '96c8b426-30f9-484a-9523-44668bcef55e', 'admin', b'1', '测试点对点数据，持久化1638433680007', '/queue/message', b'0', '2021-12-02 16:28:00');
INSERT INTO `ws_data` VALUES (14891, 'BUSINESS', 'e4740912-1437-4276-84fc-72f86dd6c4b9', 'admin', b'1', '测试点对点数据，持久化1638433710023', '/queue/message', b'0', '2021-12-02 16:28:30');
INSERT INTO `ws_data` VALUES (14892, 'BUSINESS', '8b296911-7a6d-498f-a305-8501aea84d43', 'admin', b'1', '测试点对点数据，持久化1638433740016', '/queue/message', b'0', '2021-12-02 16:29:00');
INSERT INTO `ws_data` VALUES (14893, 'BUSINESS', 'f13ff0be-16a3-403e-a8c3-6e2bd80f70bd', 'admin', b'1', '测试点对点数据，持久化1638433770013', '/queue/message', b'0', '2021-12-02 16:29:30');
INSERT INTO `ws_data` VALUES (14894, 'BUSINESS', '19ac0905-bd5f-4d53-8dc2-f961ecd98fb3', 'admin', b'1', '测试点对点数据，持久化1638433800012', '/queue/message', b'0', '2021-12-02 16:30:00');
INSERT INTO `ws_data` VALUES (14895, 'BUSINESS', '5b9d9f50-4d1f-488a-9e94-21a7315aedf6', 'admin', b'1', '测试点对点数据，持久化1638433830011', '/queue/message', b'0', '2021-12-02 16:30:30');
INSERT INTO `ws_data` VALUES (14896, 'BUSINESS', 'e987703f-da75-4aec-b60f-9b3b9c7100da', 'admin', b'1', '测试点对点数据，持久化1638433860013', '/queue/message', b'0', '2021-12-02 16:31:00');
INSERT INTO `ws_data` VALUES (14897, 'BUSINESS', 'edb4395c-0dbc-4e4b-8ccf-959eba575394', 'admin', b'1', '测试点对点数据，持久化1638433890017', '/queue/message', b'0', '2021-12-02 16:31:30');
INSERT INTO `ws_data` VALUES (14898, 'BUSINESS', 'fdd47abd-8c4f-4532-90c1-59e7be6a942f', 'admin', b'1', '测试点对点数据，持久化1638434556012', '/queue/message', b'0', '2021-12-02 16:42:36');
INSERT INTO `ws_data` VALUES (14899, 'BUSINESS', 'd9219e6f-6154-4dd6-8c1b-362cf9540129', 'admin', b'1', '测试点对点数据，持久化1638434610091', '/queue/message', b'0', '2021-12-02 16:43:30');
INSERT INTO `ws_data` VALUES (14900, 'BUSINESS', '9fbe2568-e776-4da1-b97a-b2d190f02112', 'admin', b'1', '测试点对点数据，持久化1638434640025', '/queue/message', b'0', '2021-12-02 16:44:00');
INSERT INTO `ws_data` VALUES (14901, 'BUSINESS', '6acc761e-a4c1-4df8-b74a-651ae0345fa2', 'admin', b'1', '测试点对点数据，持久化1638434670015', '/queue/message', b'0', '2021-12-02 16:44:30');
INSERT INTO `ws_data` VALUES (14902, 'BUSINESS', '0cd32daa-751e-4ada-8d9d-db6133bea1cf', 'admin', b'1', '测试点对点数据，持久化1638434700016', '/queue/message', b'0', '2021-12-02 16:45:00');
INSERT INTO `ws_data` VALUES (14903, 'BUSINESS', '72e2200e-34eb-4b1e-a5a8-0728ff57c80c', 'admin', b'1', '测试点对点数据，持久化1638434730011', '/queue/message', b'0', '2021-12-02 16:45:30');
INSERT INTO `ws_data` VALUES (14904, 'BUSINESS', '47c06dfc-c960-481b-a6d0-fc58f6ee64d0', 'admin', b'1', '测试点对点数据，持久化1638434760019', '/queue/message', b'0', '2021-12-02 16:46:00');
INSERT INTO `ws_data` VALUES (14905, 'BUSINESS', '3b315b5d-dbe2-4822-ad90-ab08da62d56f', 'admin', b'1', '测试点对点数据，持久化1638434790008', '/queue/message', b'0', '2021-12-02 16:46:30');
INSERT INTO `ws_data` VALUES (14906, 'BUSINESS', 'd0136c51-33ee-4acb-960b-2d2c553a8f53', 'admin', b'1', '测试点对点数据，持久化1638434820024', '/queue/message', b'0', '2021-12-02 16:47:00');
INSERT INTO `ws_data` VALUES (14907, 'BUSINESS', 'f9613505-f698-4d28-9950-89281ed3809f', 'admin', b'1', '测试点对点数据，持久化1638434850018', '/queue/message', b'0', '2021-12-02 16:47:30');
INSERT INTO `ws_data` VALUES (14908, 'BUSINESS', '3aa40693-d853-4af7-9824-835143f54a91', 'admin', b'1', '测试点对点数据，持久化1638434880019', '/queue/message', b'0', '2021-12-02 16:48:00');
INSERT INTO `ws_data` VALUES (14909, 'BUSINESS', 'baf9ae73-72e5-441e-8717-a7a1387c7520', 'admin', b'1', '测试点对点数据，持久化1638434910021', '/queue/message', b'0', '2021-12-02 16:48:30');
INSERT INTO `ws_data` VALUES (14910, 'BUSINESS', '62c98cfd-195b-4f37-8391-4ce9fbc6ded5', 'admin', b'1', '测试点对点数据，持久化1638434940018', '/queue/message', b'0', '2021-12-02 16:49:00');
INSERT INTO `ws_data` VALUES (14911, 'BUSINESS', '4be9cab4-1ccb-4340-823f-6dad9dcd4de1', 'admin', b'1', '测试点对点数据，持久化1638434970020', '/queue/message', b'0', '2021-12-02 16:49:30');
INSERT INTO `ws_data` VALUES (14912, 'BUSINESS', 'ec56b3c7-ea45-474d-896e-776fb13b80b3', 'admin', b'1', '测试点对点数据，持久化1638435000012', '/queue/message', b'0', '2021-12-02 16:50:00');
INSERT INTO `ws_data` VALUES (14913, 'BUSINESS', '6fc12ecf-61e3-4b1e-b657-c300f932b521', 'admin', b'1', '测试点对点数据，持久化1638435030014', '/queue/message', b'0', '2021-12-02 16:50:30');
INSERT INTO `ws_data` VALUES (14914, 'BUSINESS', '3d7a1d46-d94d-4b2b-9607-33d8b96ef7c9', 'admin', b'1', '测试点对点数据，持久化1638435060012', '/queue/message', b'0', '2021-12-02 16:51:00');
INSERT INTO `ws_data` VALUES (14915, 'BUSINESS', '3181f9d6-3129-4d8c-81e2-5c8cc924e701', 'admin', b'1', '测试点对点数据，持久化1638435090012', '/queue/message', b'0', '2021-12-02 16:51:30');
INSERT INTO `ws_data` VALUES (14916, 'BUSINESS', '6b228bbd-026c-4ebc-a77a-0defbd7e0c0e', 'admin', b'1', '测试点对点数据，持久化1638435120006', '/queue/message', b'0', '2021-12-02 16:52:00');
INSERT INTO `ws_data` VALUES (14917, 'BUSINESS', '8fc118db-3ee1-4d91-a0b3-ffaf246a8375', 'admin', b'1', '测试点对点数据，持久化1638435150008', '/queue/message', b'0', '2021-12-02 16:52:30');
INSERT INTO `ws_data` VALUES (14918, 'BUSINESS', 'a908b793-d254-4392-bc57-b4a7afeddf15', 'admin', b'1', '测试点对点数据，持久化1638435180007', '/queue/message', b'0', '2021-12-02 16:53:00');
INSERT INTO `ws_data` VALUES (14919, 'BUSINESS', '8673ee5d-7813-41e3-b4b8-a09a263b4044', 'admin', b'1', '测试点对点数据，持久化1638435210019', '/queue/message', b'0', '2021-12-02 16:53:30');
INSERT INTO `ws_data` VALUES (14920, 'BUSINESS', 'b5fb410b-67da-46e8-bb63-80790ee1e52a', 'admin', b'1', '测试点对点数据，持久化1638444690046', '/queue/message', b'0', '2021-12-02 19:31:30');
INSERT INTO `ws_data` VALUES (14921, 'BUSINESS', 'bd23be19-25fc-43f5-ad71-1f0e03838409', 'admin', b'1', '测试点对点数据，持久化1638444720023', '/queue/message', b'0', '2021-12-02 19:32:00');
INSERT INTO `ws_data` VALUES (14922, 'BUSINESS', '95c2703b-75ad-4cdd-853b-1e61261372b2', 'admin', b'1', '测试点对点数据，持久化1638444750014', '/queue/message', b'0', '2021-12-02 19:32:30');
INSERT INTO `ws_data` VALUES (14923, 'BUSINESS', 'b0b8579e-a568-4e5f-94fd-627a6299fac7', 'admin', b'1', '测试点对点数据，持久化1638444780013', '/queue/message', b'0', '2021-12-02 19:33:00');
INSERT INTO `ws_data` VALUES (14924, 'BUSINESS', 'dd41b4d1-b2c4-4cca-8c67-5eb9d302be08', 'admin', b'1', '测试点对点数据，持久化1638444810009', '/queue/message', b'0', '2021-12-02 19:33:30');
INSERT INTO `ws_data` VALUES (14925, 'BUSINESS', 'cd4becda-b740-48d5-9f56-a64912370b15', 'admin', b'1', '测试点对点数据，持久化1638444840018', '/queue/message', b'0', '2021-12-02 19:34:00');
INSERT INTO `ws_data` VALUES (14926, 'BUSINESS', 'eac72a88-477b-4d18-a5e6-c3d60511f324', 'admin', b'1', '测试点对点数据，持久化1638444870015', '/queue/message', b'0', '2021-12-02 19:34:30');
INSERT INTO `ws_data` VALUES (14927, 'BUSINESS', '0d909e6a-5f73-4735-93cd-ba3770362f72', 'admin', b'1', '测试点对点数据，持久化1638444900013', '/queue/message', b'0', '2021-12-02 19:35:00');
INSERT INTO `ws_data` VALUES (14928, 'BUSINESS', '90f7ce92-3d4d-4b88-8731-ac79d8eb7bf4', 'admin', b'1', '测试点对点数据，持久化1638444930008', '/queue/message', b'0', '2021-12-02 19:35:30');
INSERT INTO `ws_data` VALUES (14929, 'BUSINESS', '6d71b086-1e56-458d-b686-0855a69d85b8', 'admin', b'1', '测试点对点数据，持久化1638444960018', '/queue/message', b'0', '2021-12-02 19:36:00');
INSERT INTO `ws_data` VALUES (14930, 'BUSINESS', 'cc15f3d2-a9c5-487c-b22c-6bd1a936ed39', 'admin', b'1', '测试点对点数据，持久化1638444990010', '/queue/message', b'0', '2021-12-02 19:36:30');
INSERT INTO `ws_data` VALUES (14931, 'BUSINESS', '8d122b94-9955-4931-b95d-a7a022fabdad', 'admin', b'1', '测试点对点数据，持久化1638445020023', '/queue/message', b'0', '2021-12-02 19:37:00');
INSERT INTO `ws_data` VALUES (14932, 'BUSINESS', '3c59e64c-b337-482e-a655-39341e02c054', 'admin', b'1', '测试点对点数据，持久化1638445050016', '/queue/message', b'0', '2021-12-02 19:37:30');
INSERT INTO `ws_data` VALUES (14933, 'BUSINESS', '360636f0-29c5-4849-9e8f-3108b00b20f1', 'admin', b'1', '测试点对点数据，持久化1638445080015', '/queue/message', b'0', '2021-12-02 19:38:00');
INSERT INTO `ws_data` VALUES (14934, 'BUSINESS', '1b2aaafc-4762-4a10-9305-a1c59ba1f87c', 'admin', b'1', '测试点对点数据，持久化1638445110009', '/queue/message', b'0', '2021-12-02 19:38:30');
INSERT INTO `ws_data` VALUES (14935, 'BUSINESS', '9d0ffcd3-998f-4c9d-bdc5-b7d00d7b9554', 'admin', b'1', '测试点对点数据，持久化1638445140021', '/queue/message', b'0', '2021-12-02 19:39:00');
INSERT INTO `ws_data` VALUES (14936, 'BUSINESS', 'f532a69f-48ec-4629-9f13-9a3a4bce76ee', 'admin', b'1', '测试点对点数据，持久化1638445170011', '/queue/message', b'0', '2021-12-02 19:39:30');
INSERT INTO `ws_data` VALUES (14937, 'BUSINESS', 'f848c176-0bed-466d-a274-205da1d34f1c', 'admin', b'1', '测试点对点数据，持久化1638445200021', '/queue/message', b'0', '2021-12-02 19:40:00');
INSERT INTO `ws_data` VALUES (14938, 'BUSINESS', 'ad5de0f3-7796-4c26-8708-1f0b64f0bc4d', 'admin', b'1', '测试点对点数据，持久化1638445230011', '/queue/message', b'0', '2021-12-02 19:40:30');
INSERT INTO `ws_data` VALUES (14939, 'BUSINESS', 'f6ff9cb0-b982-41dd-aa6e-e1139ad454c2', 'admin', b'1', '测试点对点数据，持久化1638445260010', '/queue/message', b'0', '2021-12-02 19:41:00');
INSERT INTO `ws_data` VALUES (14940, 'BUSINESS', '472af9cf-c500-49fd-867a-b2ef52ab9d47', 'admin', b'1', '测试点对点数据，持久化1638445290015', '/queue/message', b'0', '2021-12-02 19:41:30');
INSERT INTO `ws_data` VALUES (14941, 'BUSINESS', 'bbf90e20-b043-46e0-b3b9-42ec2d5c9196', 'admin', b'1', '测试点对点数据，持久化1638445320014', '/queue/message', b'0', '2021-12-02 19:42:00');
INSERT INTO `ws_data` VALUES (14942, 'BUSINESS', 'f568a5e2-6450-42e4-88a8-d47fda140e87', 'admin', b'1', '测试点对点数据，持久化1638445350012', '/queue/message', b'0', '2021-12-02 19:42:30');
INSERT INTO `ws_data` VALUES (14943, 'BUSINESS', '7f1a63d6-3129-46eb-b8d3-e5a1b3bf7f34', 'admin', b'1', '测试点对点数据，持久化1638445380017', '/queue/message', b'0', '2021-12-02 19:43:00');
INSERT INTO `ws_data` VALUES (14944, 'BUSINESS', '5d3fb700-74cc-4b7e-a500-90c9d33e9976', 'admin', b'1', '测试点对点数据，持久化1638445410007', '/queue/message', b'0', '2021-12-02 19:43:30');
INSERT INTO `ws_data` VALUES (14945, 'BUSINESS', '7497954b-b2a2-4158-a8ee-bf8047df916a', 'admin', b'1', '测试点对点数据，持久化1638445440018', '/queue/message', b'0', '2021-12-02 19:44:00');
INSERT INTO `ws_data` VALUES (14946, 'BUSINESS', 'd79ee87c-ba20-4689-8e6c-dce656e7d8cb', 'admin', b'1', '测试点对点数据，持久化1638445470014', '/queue/message', b'0', '2021-12-02 19:44:30');
INSERT INTO `ws_data` VALUES (14947, 'BUSINESS', '3d132b89-bf10-45f4-b81c-7628873531b4', 'admin', b'1', '测试点对点数据，持久化1638445500010', '/queue/message', b'0', '2021-12-02 19:45:00');
INSERT INTO `ws_data` VALUES (14948, 'BUSINESS', 'b1a56ff2-8535-4ed4-bc66-533c0086214a', 'admin', b'1', '测试点对点数据，持久化1638445530008', '/queue/message', b'0', '2021-12-02 19:45:30');
INSERT INTO `ws_data` VALUES (14949, 'BUSINESS', '05268614-3729-4ae6-84ff-5d8beffc7a49', 'admin', b'1', '测试点对点数据，持久化1638445560017', '/queue/message', b'0', '2021-12-02 19:46:00');
INSERT INTO `ws_data` VALUES (14950, 'BUSINESS', '75e41b5e-1c90-42dd-aed9-c6f8a57fcbb3', 'admin', b'1', '测试点对点数据，持久化1638445590011', '/queue/message', b'0', '2021-12-02 19:46:30');
INSERT INTO `ws_data` VALUES (14951, 'BUSINESS', 'cbe31774-0037-41c2-ab7c-128774cead95', 'admin', b'1', '测试点对点数据，持久化1638445620018', '/queue/message', b'0', '2021-12-02 19:47:00');
INSERT INTO `ws_data` VALUES (14952, 'BUSINESS', '02dfc233-5166-4909-b588-e1db1c831067', 'admin', b'1', '测试点对点数据，持久化1638445650011', '/queue/message', b'0', '2021-12-02 19:47:30');
INSERT INTO `ws_data` VALUES (14953, 'BUSINESS', 'e2c42b2b-ea15-451d-9e97-34b233af6fc8', 'admin', b'1', '测试点对点数据，持久化1638445680008', '/queue/message', b'0', '2021-12-02 19:48:00');
INSERT INTO `ws_data` VALUES (14954, 'BUSINESS', '6079a241-ac8f-476e-beb9-142e4145be8e', 'admin', b'1', '测试点对点数据，持久化1638445710008', '/queue/message', b'0', '2021-12-02 19:48:30');
INSERT INTO `ws_data` VALUES (14955, 'BUSINESS', 'ba3be207-71f8-4a5f-8f41-003f91aa5208', 'admin', b'1', '测试点对点数据，持久化1638445740019', '/queue/message', b'0', '2021-12-02 19:49:00');
INSERT INTO `ws_data` VALUES (14956, 'BUSINESS', '03379882-337e-4463-9d9a-aed19aeb3b13', 'admin', b'1', '测试点对点数据，持久化1638445770019', '/queue/message', b'0', '2021-12-02 19:49:30');
INSERT INTO `ws_data` VALUES (14957, 'BUSINESS', 'f6689b2f-d395-4aca-a3c1-f68e4780c808', 'admin', b'1', '测试点对点数据，持久化1638445800011', '/queue/message', b'0', '2021-12-02 19:50:00');
INSERT INTO `ws_data` VALUES (14958, 'BUSINESS', 'ef730fa7-5569-4055-82b8-2f7969fff8ff', 'admin', b'1', '测试点对点数据，持久化1638445830005', '/queue/message', b'0', '2021-12-02 19:50:30');
INSERT INTO `ws_data` VALUES (14959, 'BUSINESS', '488f4668-2544-480b-b713-2b4b0d633033', 'admin', b'1', '测试点对点数据，持久化1638445860007', '/queue/message', b'0', '2021-12-02 19:51:00');
INSERT INTO `ws_data` VALUES (14960, 'BUSINESS', 'f5a62521-9311-40c8-86a0-4c5337967973', 'admin', b'1', '测试点对点数据，持久化1638445890017', '/queue/message', b'0', '2021-12-02 19:51:30');
INSERT INTO `ws_data` VALUES (14961, 'BUSINESS', '916d82b9-5f66-4244-be82-fca69a359f8d', 'admin', b'1', '测试点对点数据，持久化1638445920013', '/queue/message', b'0', '2021-12-02 19:52:00');
INSERT INTO `ws_data` VALUES (14962, 'BUSINESS', '25b117c6-c838-458f-8fd5-b591e0bbb673', 'admin', b'1', '测试点对点数据，持久化1638445950006', '/queue/message', b'0', '2021-12-02 19:52:30');
INSERT INTO `ws_data` VALUES (14963, 'BUSINESS', 'a2a2be8c-388f-44f7-ba0e-ecf3ef89120c', 'admin', b'1', '测试点对点数据，持久化1638445980006', '/queue/message', b'0', '2021-12-02 19:53:00');
INSERT INTO `ws_data` VALUES (14964, 'BUSINESS', 'dd8830c9-ee0f-4684-aeb4-0b7e54cf492d', 'admin', b'1', '测试点对点数据，持久化1638446010017', '/queue/message', b'0', '2021-12-02 19:53:30');
INSERT INTO `ws_data` VALUES (14965, 'BUSINESS', '9d9eed57-d726-438a-b796-65614412c58a', 'admin', b'1', '测试点对点数据，持久化1638446040018', '/queue/message', b'0', '2021-12-02 19:54:00');
INSERT INTO `ws_data` VALUES (14966, 'BUSINESS', '8f028655-7aea-44c1-a95c-b1afba45e046', 'admin', b'1', '测试点对点数据，持久化1638446070011', '/queue/message', b'0', '2021-12-02 19:54:30');
INSERT INTO `ws_data` VALUES (14967, 'BUSINESS', '5c689abf-2c72-4a59-a03f-74ef4617aeba', 'admin', b'1', '测试点对点数据，持久化1638446100007', '/queue/message', b'0', '2021-12-02 19:55:00');
INSERT INTO `ws_data` VALUES (14968, 'BUSINESS', '5d9a9640-731a-46f5-8a30-193fa2b0b51d', 'admin', b'1', '测试点对点数据，持久化1638446130016', '/queue/message', b'0', '2021-12-02 19:55:30');
INSERT INTO `ws_data` VALUES (14969, 'BUSINESS', '1a604a01-5289-4934-a46f-c52e55e0bd12', 'admin', b'1', '测试点对点数据，持久化1638446160013', '/queue/message', b'0', '2021-12-02 19:56:00');
INSERT INTO `ws_data` VALUES (14970, 'BUSINESS', 'abccb485-e94b-46ca-b70c-40e0c395b1df', 'admin', b'1', '测试点对点数据，持久化1638446190009', '/queue/message', b'0', '2021-12-02 19:56:30');
INSERT INTO `ws_data` VALUES (14971, 'BUSINESS', '03d2b031-a36d-4bc1-89fa-f3018a73619b', 'admin', b'1', '测试点对点数据，持久化1638446220007', '/queue/message', b'0', '2021-12-02 19:57:00');
INSERT INTO `ws_data` VALUES (14972, 'BUSINESS', '388d97ee-af62-495f-ac16-c02fdecf3b80', 'admin', b'1', '测试点对点数据，持久化1638446250019', '/queue/message', b'0', '2021-12-02 19:57:30');
INSERT INTO `ws_data` VALUES (14973, 'BUSINESS', '30768e96-5169-4303-b07f-aef86b0b0874', 'admin', b'1', '测试点对点数据，持久化1638446280017', '/queue/message', b'0', '2021-12-02 19:58:00');
INSERT INTO `ws_data` VALUES (14974, 'BUSINESS', 'd0dc4377-49ca-4b41-b98b-d4f9ec0913b8', 'admin', b'1', '测试点对点数据，持久化1638446310008', '/queue/message', b'0', '2021-12-02 19:58:30');
INSERT INTO `ws_data` VALUES (14975, 'BUSINESS', 'e8213432-493a-4787-8122-ee41995f6d89', 'admin', b'1', '测试点对点数据，持久化1638446340011', '/queue/message', b'0', '2021-12-02 19:59:00');
INSERT INTO `ws_data` VALUES (14976, 'BUSINESS', '6e00b8d9-1c82-4945-b37e-b60e83ddae7d', 'admin', b'1', '测试点对点数据，持久化1638446370019', '/queue/message', b'0', '2021-12-02 19:59:30');
INSERT INTO `ws_data` VALUES (14977, 'BUSINESS', '7babd8c1-b19b-45f7-84aa-d060de710346', 'admin', b'1', '测试点对点数据，持久化1638446400016', '/queue/message', b'0', '2021-12-02 20:00:00');
INSERT INTO `ws_data` VALUES (14978, 'BUSINESS', 'ad815ecb-5433-47d6-89fc-d5f02d2fb72a', 'admin', b'1', '测试点对点数据，持久化1638446430007', '/queue/message', b'0', '2021-12-02 20:00:30');
INSERT INTO `ws_data` VALUES (14979, 'BUSINESS', '35a1d685-fa73-4363-93e9-bb75e45d2f86', 'admin', b'1', '测试点对点数据，持久化1638446460015', '/queue/message', b'0', '2021-12-02 20:01:00');
INSERT INTO `ws_data` VALUES (14980, 'BUSINESS', '6ddfaab9-edbd-4ee9-804b-470a5056d57d', 'admin', b'1', '测试点对点数据，持久化1638446490009', '/queue/message', b'0', '2021-12-02 20:01:30');
INSERT INTO `ws_data` VALUES (14981, 'BUSINESS', 'b881e5c4-9495-4366-bd5e-a2eddc39335c', 'admin', b'1', '测试点对点数据，持久化1638446520010', '/queue/message', b'0', '2021-12-02 20:02:00');
INSERT INTO `ws_data` VALUES (14982, 'BUSINESS', '0d124197-674a-4316-8974-c292d01a68ec', 'admin', b'1', '测试点对点数据，持久化1638446550006', '/queue/message', b'0', '2021-12-02 20:02:30');
INSERT INTO `ws_data` VALUES (14983, 'BUSINESS', 'd93006f8-389e-41f0-bbc3-52fd9d670333', 'admin', b'1', '测试点对点数据，持久化1638446580018', '/queue/message', b'0', '2021-12-02 20:03:00');
INSERT INTO `ws_data` VALUES (14984, 'BUSINESS', '7a688d96-a1db-4158-9896-7a9c3713de48', 'admin', b'1', '测试点对点数据，持久化1638446610013', '/queue/message', b'0', '2021-12-02 20:03:30');
INSERT INTO `ws_data` VALUES (14985, 'BUSINESS', '328244c2-ebcc-4d31-b57f-e2e6ba1a3867', 'admin', b'1', '测试点对点数据，持久化1638446640011', '/queue/message', b'0', '2021-12-02 20:04:00');
INSERT INTO `ws_data` VALUES (14986, 'BUSINESS', '38fdabd0-4740-441d-a1ea-6ca1b1387dcd', 'admin', b'1', '测试点对点数据，持久化1638446670007', '/queue/message', b'0', '2021-12-02 20:04:30');
INSERT INTO `ws_data` VALUES (14987, 'BUSINESS', '99e4cf30-ccef-461e-bfc0-760c6c8d5738', 'admin', b'1', '测试点对点数据，持久化1638446700007', '/queue/message', b'0', '2021-12-02 20:05:00');
INSERT INTO `ws_data` VALUES (14988, 'BUSINESS', 'a6566906-3304-48b9-a9d7-fb1e99437a01', 'admin', b'1', '测试点对点数据，持久化1638446730015', '/queue/message', b'0', '2021-12-02 20:05:30');
INSERT INTO `ws_data` VALUES (14989, 'BUSINESS', '9bac3bb9-7524-43ad-a092-85206d6ddd19', 'admin', b'1', '测试点对点数据，持久化1638446760010', '/queue/message', b'0', '2021-12-02 20:06:00');
INSERT INTO `ws_data` VALUES (14990, 'BUSINESS', '147a732a-6683-4f17-8dc9-405d6bd3ab36', 'admin', b'1', '测试点对点数据，持久化1638446790004', '/queue/message', b'0', '2021-12-02 20:06:30');
INSERT INTO `ws_data` VALUES (14991, 'BUSINESS', '56e3087f-dc0a-4465-91b8-b28533fa4bc7', 'admin', b'1', '测试点对点数据，持久化1638446820018', '/queue/message', b'0', '2021-12-02 20:07:00');
INSERT INTO `ws_data` VALUES (14992, 'BUSINESS', '5d6b14d6-f2f0-470c-9f85-26bbf6afeafb', 'admin', b'1', '测试点对点数据，持久化1638446850010', '/queue/message', b'0', '2021-12-02 20:07:30');
INSERT INTO `ws_data` VALUES (14993, 'BUSINESS', 'a7f855f6-a331-426e-9f73-e516776b0516', 'admin', b'1', '测试点对点数据，持久化1638446880017', '/queue/message', b'0', '2021-12-02 20:08:00');
INSERT INTO `ws_data` VALUES (14994, 'BUSINESS', '2a219861-6378-46c6-9d8f-224a9b8f0ddf', 'admin', b'1', '测试点对点数据，持久化1638446910014', '/queue/message', b'0', '2021-12-02 20:08:30');
INSERT INTO `ws_data` VALUES (14995, 'BUSINESS', '7b5fd66c-2d07-4ba4-90db-5b098b3b7ef8', 'admin', b'1', '测试点对点数据，持久化1638446940014', '/queue/message', b'0', '2021-12-02 20:09:00');
INSERT INTO `ws_data` VALUES (14996, 'BUSINESS', '6ffd07c5-470a-4dd3-9182-8ef892b35c20', 'admin', b'1', '测试点对点数据，持久化1638446970011', '/queue/message', b'0', '2021-12-02 20:09:30');
INSERT INTO `ws_data` VALUES (14997, 'BUSINESS', 'a6ec58ac-033b-41e4-b85c-27e6432f8f53', 'admin', b'1', '测试点对点数据，持久化1638447000017', '/queue/message', b'0', '2021-12-02 20:10:00');
INSERT INTO `ws_data` VALUES (14998, 'BUSINESS', '4902fc51-eed1-4d54-909f-e1d434deccf3', 'admin', b'1', '测试点对点数据，持久化1638447030013', '/queue/message', b'0', '2021-12-02 20:10:30');
INSERT INTO `ws_data` VALUES (14999, 'BUSINESS', '37bc323b-f312-40f6-b066-7b6e641d7425', 'admin', b'1', '测试点对点数据，持久化1638447060012', '/queue/message', b'0', '2021-12-02 20:11:00');
INSERT INTO `ws_data` VALUES (15000, 'BUSINESS', 'dd0fdfc6-e825-46b8-8168-5bb260d93e31', 'admin', b'1', '测试点对点数据，持久化1638447090009', '/queue/message', b'0', '2021-12-02 20:11:30');
INSERT INTO `ws_data` VALUES (15001, 'BUSINESS', 'c4e7e3c2-45f7-44e7-b23f-90e3148b352c', 'admin', b'1', '测试点对点数据，持久化1638447120003', '/queue/message', b'0', '2021-12-02 20:12:00');
INSERT INTO `ws_data` VALUES (15002, 'BUSINESS', '514490a6-91ea-4e1a-9bc7-406fd71a92c9', 'admin', b'1', '测试点对点数据，持久化1638447150016', '/queue/message', b'0', '2021-12-02 20:12:30');
INSERT INTO `ws_data` VALUES (15003, 'BUSINESS', 'de4d9ead-ce8e-4264-a8ae-2bd37043aea0', 'admin', b'1', '测试点对点数据，持久化1638447180011', '/queue/message', b'0', '2021-12-02 20:13:00');
INSERT INTO `ws_data` VALUES (15004, 'BUSINESS', 'e58e4f3e-7693-419e-b861-c4191807089d', 'admin', b'1', '测试点对点数据，持久化1638447210006', '/queue/message', b'0', '2021-12-02 20:13:30');
INSERT INTO `ws_data` VALUES (15005, 'BUSINESS', '42698610-0069-4623-9432-fa0d7d5da2dc', 'admin', b'1', '测试点对点数据，持久化1638447240017', '/queue/message', b'0', '2021-12-02 20:14:00');
INSERT INTO `ws_data` VALUES (15006, 'BUSINESS', '552d3baf-19c4-4f20-a4a8-8ef64fabeb8d', 'admin', b'1', '测试点对点数据，持久化1638447270015', '/queue/message', b'0', '2021-12-02 20:14:30');
INSERT INTO `ws_data` VALUES (15007, 'BUSINESS', 'ee9354a1-1a9e-43f3-b5d1-8fe218cf281e', 'admin', b'1', '测试点对点数据，持久化1638447300014', '/queue/message', b'0', '2021-12-02 20:15:00');
INSERT INTO `ws_data` VALUES (15008, 'BUSINESS', '4ece8f4a-3464-4917-949f-454e5e86d5db', 'admin', b'1', '测试点对点数据，持久化1638447330008', '/queue/message', b'0', '2021-12-02 20:15:30');
INSERT INTO `ws_data` VALUES (15009, 'BUSINESS', 'a0fe5008-8ffb-48e7-96cc-d32d52599a29', 'admin', b'1', '测试点对点数据，持久化1638447360019', '/queue/message', b'0', '2021-12-02 20:16:00');
INSERT INTO `ws_data` VALUES (15010, 'BUSINESS', '9340bf5c-b8e6-4e18-8e6f-62361811a7bc', 'admin', b'1', '测试点对点数据，持久化1638447390013', '/queue/message', b'0', '2021-12-02 20:16:30');
INSERT INTO `ws_data` VALUES (15011, 'BUSINESS', 'd0f4d199-f7b4-4f25-8783-7ca61169f129', 'admin', b'1', '测试点对点数据，持久化1638447420017', '/queue/message', b'0', '2021-12-02 20:17:00');
INSERT INTO `ws_data` VALUES (15012, 'BUSINESS', 'd15d6e93-d146-4e12-b390-a819e113b9d2', 'admin', b'1', '测试点对点数据，持久化1638447450008', '/queue/message', b'0', '2021-12-02 20:17:30');
INSERT INTO `ws_data` VALUES (15013, 'BUSINESS', 'c89b2e3b-f5cb-432e-9ced-75b73dfe877d', 'admin', b'1', '测试点对点数据，持久化1638447480009', '/queue/message', b'0', '2021-12-02 20:18:00');
INSERT INTO `ws_data` VALUES (15014, 'BUSINESS', 'd12686d8-be47-4afa-bcb0-4bb90254adf4', 'admin', b'1', '测试点对点数据，持久化1638447510016', '/queue/message', b'0', '2021-12-02 20:18:30');
INSERT INTO `ws_data` VALUES (15015, 'BUSINESS', '3818049c-f27a-4c79-93f2-4fc7efe523e6', 'admin', b'1', '测试点对点数据，持久化1638447540016', '/queue/message', b'0', '2021-12-02 20:19:00');
INSERT INTO `ws_data` VALUES (15016, 'BUSINESS', 'dcc08350-8111-4420-b10f-1d81155b2585', 'admin', b'1', '测试点对点数据，持久化1638447570011', '/queue/message', b'0', '2021-12-02 20:19:30');
INSERT INTO `ws_data` VALUES (15017, 'BUSINESS', '2541109e-e77b-4c60-9bbe-680c62c5e20a', 'admin', b'1', '测试点对点数据，持久化1638447600010', '/queue/message', b'0', '2021-12-02 20:20:00');
INSERT INTO `ws_data` VALUES (15018, 'BUSINESS', 'e9e4b39c-4419-4e7c-8b67-a56b5421ab34', 'admin', b'1', '测试点对点数据，持久化1638447630017', '/queue/message', b'0', '2021-12-02 20:20:30');
INSERT INTO `ws_data` VALUES (15019, 'BUSINESS', '2c010877-3c8c-4655-9531-4fabcda1d5a7', 'admin', b'1', '测试点对点数据，持久化1638447660013', '/queue/message', b'0', '2021-12-02 20:21:00');
INSERT INTO `ws_data` VALUES (15020, 'BUSINESS', '3be7e4af-36a8-4940-a74f-440298848833', 'admin', b'1', '测试点对点数据，持久化1638447690008', '/queue/message', b'0', '2021-12-02 20:21:30');
INSERT INTO `ws_data` VALUES (15021, 'BUSINESS', 'd4a6f309-2c35-4c39-b0e8-61e5160e5588', 'admin', b'1', '测试点对点数据，持久化1638447720008', '/queue/message', b'0', '2021-12-02 20:22:00');
INSERT INTO `ws_data` VALUES (15022, 'BUSINESS', '22112591-ecd5-4a11-a43c-eca4f804949f', 'admin', b'1', '测试点对点数据，持久化1638447750016', '/queue/message', b'0', '2021-12-02 20:22:30');
INSERT INTO `ws_data` VALUES (15023, 'BUSINESS', '14f95764-89e5-4dc5-acd0-bf547c80487d', 'admin', b'1', '测试点对点数据，持久化1638447780015', '/queue/message', b'0', '2021-12-02 20:23:00');
INSERT INTO `ws_data` VALUES (15024, 'BUSINESS', 'cdb2ee63-a132-4f28-bb08-9c02a77b9284', 'admin', b'1', '测试点对点数据，持久化1638447810008', '/queue/message', b'0', '2021-12-02 20:23:30');
INSERT INTO `ws_data` VALUES (15025, 'BUSINESS', 'fcdd8c01-9968-46d6-ab2d-11666e387379', 'admin', b'1', '测试点对点数据，持久化1638447840006', '/queue/message', b'0', '2021-12-02 20:24:00');
INSERT INTO `ws_data` VALUES (15026, 'BUSINESS', '8458921a-9d91-4d70-b0e2-7c7e8bafd782', 'admin', b'1', '测试点对点数据，持久化1638447870027', '/queue/message', b'0', '2021-12-02 20:24:30');
INSERT INTO `ws_data` VALUES (15027, 'BUSINESS', 'b11a90a5-23dd-43ea-9a72-847d552a6980', 'admin', b'1', '测试点对点数据，持久化1638447900014', '/queue/message', b'0', '2021-12-02 20:25:00');
INSERT INTO `ws_data` VALUES (15028, 'BUSINESS', '9b9c52d3-d8e8-4867-b192-9dc1dcb22cc4', 'admin', b'1', '测试点对点数据，持久化1638447930015', '/queue/message', b'0', '2021-12-02 20:25:30');
INSERT INTO `ws_data` VALUES (15029, 'BUSINESS', '7a43e6a0-1f9a-4c7f-944b-5d0a31017232', 'admin', b'1', '测试点对点数据，持久化1638447960009', '/queue/message', b'0', '2021-12-02 20:26:00');
INSERT INTO `ws_data` VALUES (15030, 'BUSINESS', '33aa46a5-6911-46f6-8977-fe8f740271b3', 'admin', b'1', '测试点对点数据，持久化1638447990002', '/queue/message', b'0', '2021-12-02 20:26:30');
INSERT INTO `ws_data` VALUES (15031, 'BUSINESS', '3af852a8-9cf0-4e05-89b9-54f4a215af4a', 'admin', b'1', '测试点对点数据，持久化1638448020013', '/queue/message', b'0', '2021-12-02 20:27:00');
INSERT INTO `ws_data` VALUES (15032, 'BUSINESS', 'a8388c3b-9e0a-432c-815c-5997e38c1d17', 'admin', b'1', '测试点对点数据，持久化1638448050012', '/queue/message', b'0', '2021-12-02 20:27:30');
INSERT INTO `ws_data` VALUES (15033, 'BUSINESS', '8e847be9-7b42-428d-a85c-6b9ad147efce', 'admin', b'1', '测试点对点数据，持久化1638448080007', '/queue/message', b'0', '2021-12-02 20:28:00');
INSERT INTO `ws_data` VALUES (15034, 'BUSINESS', '0fbd053a-ce1e-46d4-8fe2-e177764426e3', 'admin', b'1', '测试点对点数据，持久化1638448110004', '/queue/message', b'0', '2021-12-02 20:28:30');
INSERT INTO `ws_data` VALUES (15035, 'BUSINESS', 'e1b5f588-ec0c-44b5-985d-ce6596b23983', 'admin', b'1', '测试点对点数据，持久化1638448140015', '/queue/message', b'0', '2021-12-02 20:29:00');
INSERT INTO `ws_data` VALUES (15036, 'BUSINESS', '33e8574b-871c-467c-a420-af3a44beaef1', 'admin', b'1', '测试点对点数据，持久化1638448170010', '/queue/message', b'0', '2021-12-02 20:29:30');
INSERT INTO `ws_data` VALUES (15037, 'BUSINESS', '82baba3d-70b2-4043-8425-f49ae766453f', 'admin', b'1', '测试点对点数据，持久化1638448200008', '/queue/message', b'0', '2021-12-02 20:30:00');
INSERT INTO `ws_data` VALUES (15038, 'BUSINESS', 'bd7e6211-74ce-4924-9691-745cc94c0518', 'admin', b'1', '测试点对点数据，持久化1638448230016', '/queue/message', b'0', '2021-12-02 20:30:30');
INSERT INTO `ws_data` VALUES (15039, 'BUSINESS', '1bebed57-4400-41bf-a355-801cd1641679', 'admin', b'1', '测试点对点数据，持久化1638448260014', '/queue/message', b'0', '2021-12-02 20:31:00');
INSERT INTO `ws_data` VALUES (15040, 'BUSINESS', 'fee8b0f7-e286-45cd-bb9f-24d36e8f9ba4', 'admin', b'1', '测试点对点数据，持久化1638448290009', '/queue/message', b'0', '2021-12-02 20:31:30');
INSERT INTO `ws_data` VALUES (15041, 'BUSINESS', '12f9a409-04a6-4c5c-9d12-96807e22dd0e', 'admin', b'1', '测试点对点数据，持久化1638448320009', '/queue/message', b'0', '2021-12-02 20:32:00');
INSERT INTO `ws_data` VALUES (15042, 'BUSINESS', '8ca2813f-7120-4273-aab4-a0bdbee541de', 'admin', b'1', '测试点对点数据，持久化1638448350003', '/queue/message', b'0', '2021-12-02 20:32:30');
INSERT INTO `ws_data` VALUES (15043, 'BUSINESS', 'b8cf3e7e-a73d-444f-8d13-17a5d37d448a', 'admin', b'1', '测试点对点数据，持久化1638448380017', '/queue/message', b'0', '2021-12-02 20:33:00');
INSERT INTO `ws_data` VALUES (15044, 'BUSINESS', '1d7e0f0b-0861-4200-9345-4384402ea97e', 'admin', b'1', '测试点对点数据，持久化1638448410011', '/queue/message', b'0', '2021-12-02 20:33:30');
INSERT INTO `ws_data` VALUES (15045, 'BUSINESS', '90cc0b9a-2e6f-46d1-af75-7c919eab5bff', 'admin', b'1', '测试点对点数据，持久化1638448440007', '/queue/message', b'0', '2021-12-02 20:34:00');
INSERT INTO `ws_data` VALUES (15046, 'BUSINESS', '755b6ea5-4039-4d25-be4b-295f984e7720', 'admin', b'1', '测试点对点数据，持久化1638448470013', '/queue/message', b'0', '2021-12-02 20:34:30');
INSERT INTO `ws_data` VALUES (15047, 'BUSINESS', '6faca587-7c01-48a1-a884-4e447a1ade5d', 'admin', b'1', '测试点对点数据，持久化1638448500009', '/queue/message', b'0', '2021-12-02 20:35:00');
INSERT INTO `ws_data` VALUES (15048, 'BUSINESS', '062d488f-86e8-49c1-a17c-7102303faba7', 'admin', b'1', '测试点对点数据，持久化1638448530003', '/queue/message', b'0', '2021-12-02 20:35:30');
INSERT INTO `ws_data` VALUES (15049, 'BUSINESS', '58b4d18e-e02a-4d99-867e-4083f2c0faa7', 'admin', b'1', '测试点对点数据，持久化1638448560019', '/queue/message', b'0', '2021-12-02 20:36:00');
INSERT INTO `ws_data` VALUES (15050, 'BUSINESS', '91944537-2597-4f74-bf53-35b8f96f34a1', 'admin', b'1', '测试点对点数据，持久化1638448590013', '/queue/message', b'0', '2021-12-02 20:36:30');
INSERT INTO `ws_data` VALUES (15051, 'BUSINESS', 'efec21df-91a2-4a6f-b29c-25cf222bf76c', 'admin', b'1', '测试点对点数据，持久化1638448620005', '/queue/message', b'0', '2021-12-02 20:37:00');
INSERT INTO `ws_data` VALUES (15052, 'BUSINESS', '800ccc93-79a3-4c49-bd3b-5ebb6ba90f83', 'admin', b'1', '测试点对点数据，持久化1638448650016', '/queue/message', b'0', '2021-12-02 20:37:30');
INSERT INTO `ws_data` VALUES (15053, 'BUSINESS', 'eb901223-2109-49f4-bdf8-cf2dc98f0b6d', 'admin', b'1', '测试点对点数据，持久化1638448680017', '/queue/message', b'0', '2021-12-02 20:38:00');
INSERT INTO `ws_data` VALUES (15054, 'BUSINESS', '2414abcf-e4d9-45bb-a69a-f88f19fbf1d7', 'admin', b'1', '测试点对点数据，持久化1638448710009', '/queue/message', b'0', '2021-12-02 20:38:30');
INSERT INTO `ws_data` VALUES (15055, 'BUSINESS', '422e05eb-959f-4a22-92bc-8cae3ae7dd78', 'admin', b'1', '测试点对点数据，持久化1638448740010', '/queue/message', b'0', '2021-12-02 20:39:00');
INSERT INTO `ws_data` VALUES (15056, 'BUSINESS', '52a082ae-7ac3-4f58-b539-add3467caad0', 'admin', b'1', '测试点对点数据，持久化1638448770006', '/queue/message', b'0', '2021-12-02 20:39:30');
INSERT INTO `ws_data` VALUES (15057, 'BUSINESS', '38480b5a-ebc2-4a0f-b6fe-d5eb849d7fde', 'admin', b'1', '测试点对点数据，持久化1638448800014', '/queue/message', b'0', '2021-12-02 20:40:00');
INSERT INTO `ws_data` VALUES (15058, 'BUSINESS', 'f0c76a89-2489-4974-8fd5-6c716b1eb74c', 'admin', b'1', '测试点对点数据，持久化1638448830011', '/queue/message', b'0', '2021-12-02 20:40:30');
INSERT INTO `ws_data` VALUES (15059, 'BUSINESS', 'ae1d8f06-6090-42f0-af42-f65bf1d703c2', 'admin', b'1', '测试点对点数据，持久化1638448860009', '/queue/message', b'0', '2021-12-02 20:41:00');
INSERT INTO `ws_data` VALUES (15060, 'BUSINESS', '440cb66a-0e89-4e3b-b56d-dcc8564f56f6', 'admin', b'1', '测试点对点数据，持久化1638448890006', '/queue/message', b'0', '2021-12-02 20:41:30');
INSERT INTO `ws_data` VALUES (15061, 'BUSINESS', 'be739276-5a69-4b42-9339-c4d38c63a0b7', 'admin', b'1', '测试点对点数据，持久化1638448920016', '/queue/message', b'0', '2021-12-02 20:42:00');
INSERT INTO `ws_data` VALUES (15062, 'BUSINESS', 'a821e871-ca66-4c7c-92b2-7dd1ba90de25', 'admin', b'1', '测试点对点数据，持久化1638448950014', '/queue/message', b'0', '2021-12-02 20:42:30');
INSERT INTO `ws_data` VALUES (15063, 'BUSINESS', 'e635ad0e-2989-442e-9c6f-70ebfcf3ecb9', 'admin', b'1', '测试点对点数据，持久化1638448980009', '/queue/message', b'0', '2021-12-02 20:43:00');
INSERT INTO `ws_data` VALUES (15064, 'BUSINESS', '472547c0-96ff-4bcc-b4d2-20adbccc38dd', 'admin', b'1', '测试点对点数据，持久化1638449010007', '/queue/message', b'0', '2021-12-02 20:43:30');
INSERT INTO `ws_data` VALUES (15065, 'BUSINESS', 'c612f264-f49a-4f7a-87b1-416dc441e053', 'admin', b'1', '测试点对点数据，持久化1638449040012', '/queue/message', b'0', '2021-12-02 20:44:00');
INSERT INTO `ws_data` VALUES (15066, 'BUSINESS', '0e93e9ff-aae1-46c6-b940-e7b540191b20', 'admin', b'1', '测试点对点数据，持久化1638449070008', '/queue/message', b'0', '2021-12-02 20:44:30');
INSERT INTO `ws_data` VALUES (15067, 'BUSINESS', 'c32f78ff-0b04-4afe-9b94-d7d0b3f4dbfd', 'admin', b'1', '测试点对点数据，持久化1638449100017', '/queue/message', b'0', '2021-12-02 20:45:00');
INSERT INTO `ws_data` VALUES (15068, 'BUSINESS', '5f62eae1-b725-400a-a1a0-c455fb0e320b', 'admin', b'1', '测试点对点数据，持久化1638449130010', '/queue/message', b'0', '2021-12-02 20:45:30');
INSERT INTO `ws_data` VALUES (15069, 'BUSINESS', '6c3f71f8-20c3-4001-8a04-2e9577245276', 'admin', b'1', '测试点对点数据，持久化1638449160008', '/queue/message', b'0', '2021-12-02 20:46:00');
INSERT INTO `ws_data` VALUES (15070, 'BUSINESS', '52ca04da-d578-402e-b9d3-cd2630d1658e', 'admin', b'1', '测试点对点数据，持久化1638449190004', '/queue/message', b'0', '2021-12-02 20:46:30');
INSERT INTO `ws_data` VALUES (15071, 'BUSINESS', '4ea36a16-4d73-48bc-aed6-b7008d048747', 'admin', b'1', '测试点对点数据，持久化1638449220005', '/queue/message', b'0', '2021-12-02 20:47:00');
INSERT INTO `ws_data` VALUES (15072, 'BUSINESS', '15171352-2a03-4d5e-9ec6-6f94507fd7aa', 'admin', b'1', '测试点对点数据，持久化1638449250012', '/queue/message', b'0', '2021-12-02 20:47:30');
INSERT INTO `ws_data` VALUES (15073, 'BUSINESS', 'fa5985b9-8d8f-461d-8401-b705e3df5b14', 'admin', b'1', '测试点对点数据，持久化1638449280009', '/queue/message', b'0', '2021-12-02 20:48:00');
INSERT INTO `ws_data` VALUES (15074, 'BUSINESS', '3e41395a-346e-4102-bd19-a53b6a6c6ae6', 'admin', b'1', '测试点对点数据，持久化1638449310006', '/queue/message', b'0', '2021-12-02 20:48:30');
INSERT INTO `ws_data` VALUES (15075, 'BUSINESS', '8ee01f11-7b86-4006-9cb2-8fa20fa8266a', 'admin', b'1', '测试点对点数据，持久化1638449340014', '/queue/message', b'0', '2021-12-02 20:49:00');
INSERT INTO `ws_data` VALUES (15076, 'BUSINESS', '18a90f02-097c-46a2-82aa-eefe6c4ee5f6', 'admin', b'1', '测试点对点数据，持久化1638449370011', '/queue/message', b'0', '2021-12-02 20:49:30');
INSERT INTO `ws_data` VALUES (15077, 'BUSINESS', '2f5d4500-6726-4e61-9eb1-b5e7b543d371', 'admin', b'1', '测试点对点数据，持久化1638449400008', '/queue/message', b'0', '2021-12-02 20:50:00');
INSERT INTO `ws_data` VALUES (15078, 'BUSINESS', 'ce21f99f-ac68-4864-b748-021dae5981f9', 'admin', b'1', '测试点对点数据，持久化1638449430002', '/queue/message', b'0', '2021-12-02 20:50:30');
INSERT INTO `ws_data` VALUES (15079, 'BUSINESS', '6bc7a9fe-e117-460c-93b5-0a36e426bf01', 'admin', b'1', '测试点对点数据，持久化1638449460016', '/queue/message', b'0', '2021-12-02 20:51:00');
INSERT INTO `ws_data` VALUES (15080, 'BUSINESS', '68b02fe8-9df0-4b08-923d-6bc36091aeb2', 'admin', b'1', '测试点对点数据，持久化1638449490009', '/queue/message', b'0', '2021-12-02 20:51:30');
INSERT INTO `ws_data` VALUES (15081, 'BUSINESS', '806b5b2a-04f8-490a-a547-a46f3b0ab488', 'admin', b'1', '测试点对点数据，持久化1638449520015', '/queue/message', b'0', '2021-12-02 20:52:00');
INSERT INTO `ws_data` VALUES (15082, 'BUSINESS', '8b699a06-9cde-454e-aa40-d54c500bc71f', 'admin', b'1', '测试点对点数据，持久化1638449550008', '/queue/message', b'0', '2021-12-02 20:52:30');
INSERT INTO `ws_data` VALUES (15083, 'BUSINESS', '2d624ed4-7630-4d61-925c-802ab37462c3', 'admin', b'1', '测试点对点数据，持久化1638449580016', '/queue/message', b'0', '2021-12-02 20:53:00');
INSERT INTO `ws_data` VALUES (15084, 'BUSINESS', '816bc208-c4c9-49c5-ab8d-3624ff084198', 'admin', b'1', '测试点对点数据，持久化1638449610015', '/queue/message', b'0', '2021-12-02 20:53:30');
INSERT INTO `ws_data` VALUES (15085, 'BUSINESS', '6f176805-058c-47df-915b-393e93d58258', 'admin', b'1', '测试点对点数据，持久化1638449640009', '/queue/message', b'0', '2021-12-02 20:54:00');
INSERT INTO `ws_data` VALUES (15086, 'BUSINESS', '750d0fa3-9eba-4357-8578-8d21a042bc1a', 'admin', b'1', '测试点对点数据，持久化1638449670004', '/queue/message', b'0', '2021-12-02 20:54:30');
INSERT INTO `ws_data` VALUES (15087, 'BUSINESS', '7c31cccc-5088-4f2d-94b3-372a388adb6a', 'admin', b'1', '测试点对点数据，持久化1638449700013', '/queue/message', b'0', '2021-12-02 20:55:00');
INSERT INTO `ws_data` VALUES (15088, 'BUSINESS', '367acc1f-51b1-4553-8b0f-41395161c05a', 'admin', b'1', '测试点对点数据，持久化1638449730010', '/queue/message', b'0', '2021-12-02 20:55:30');

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
INSERT INTO `xxcolumn_definition` VALUES (11177, 'ws_data', 'create_time', '推送时间', 7, b'0', NULL, 'daterange', NULL, b'1', 'range', NULL, b'0', b'0', NULL, NULL, '', b'0', NULL, NULL, NULL, NULL);
INSERT INTO `xxcolumn_definition` VALUES (11178, 'dict_data', 'dict_label', '字典名称', 1, b'1', b'1', 'text', NULL, b'1', 'like', NULL, b'1', b'0', NULL, NULL, NULL, b'0', NULL, 1, NULL, NULL);
INSERT INTO `xxcolumn_definition` VALUES (11179, 'dict_data', 'dict_data_type', '数据类型', 2, b'1', b'1', 'text', NULL, b'1', 'like', NULL, b'1', b'0', NULL, NULL, NULL, b'0', NULL, 1, NULL, NULL);
INSERT INTO `xxcolumn_definition` VALUES (11180, 'dict_data', 'dict_type', '字典类型', 3, b'0', b'1', 'text', NULL, b'0', NULL, NULL, b'0', b'0', NULL, NULL, NULL, b'0', NULL, NULL, NULL, NULL);
INSERT INTO `xxcolumn_definition` VALUES (11181, 'dict_data', 'create_time', '创建时间', 4, b'0', NULL, 'text', NULL, b'0', NULL, NULL, b'0', b'0', NULL, NULL, NULL, b'0', NULL, NULL, NULL, NULL);
INSERT INTO `xxcolumn_definition` VALUES (11182, 'dict_data', 'create_by', '创建者', 5, b'0', b'1', 'text', NULL, b'0', NULL, NULL, b'0', b'0', NULL, NULL, '', b'0', NULL, NULL, NULL, NULL);
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
) ENGINE = InnoDB AUTO_INCREMENT = 11114 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of xxtable_definition
-- ----------------------------
INSERT INTO `xxtable_definition` VALUES (11105, 'dict', '字典', 'dict', 'select * from dict', NULL, NULL, NULL, NULL, NULL, 'id');
INSERT INTO `xxtable_definition` VALUES (11106, 'param', '参数设置', 'param', 'select * from param', NULL, NULL, NULL, NULL, NULL, 'id');
INSERT INTO `xxtable_definition` VALUES (11107, 'log_info', '访问日志', 'log_info', 'select * from log_info', b'0', '', '', '', b'0', 'id');
INSERT INTO `xxtable_definition` VALUES (11108, 'role', '角色', 'role', 'select * from role', NULL, NULL, NULL, NULL, NULL, 'role_id');
INSERT INTO `xxtable_definition` VALUES (11109, 'user', '用户', 'user_info', 'select * from user_info', NULL, NULL, NULL, NULL, NULL, 'user_id');
INSERT INTO `xxtable_definition` VALUES (11110, 'ws_data', '消息', 'ws_data', 'select * from ws_data', NULL, NULL, NULL, NULL, NULL, 'id');
INSERT INTO `xxtable_definition` VALUES (11111, 'dict_data', '字典管理', 'dict_data', 'select * from dict_data', NULL, NULL, NULL, NULL, NULL, 'id');
INSERT INTO `xxtable_definition` VALUES (11112, 'dict_data_type', '字典数据', 'dict_data_type', 'select * from dict_data_type', NULL, NULL, NULL, NULL, NULL, 'id');

SET FOREIGN_KEY_CHECKS = 1;
