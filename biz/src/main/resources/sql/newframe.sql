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

 Date: 26/03/2022 11:23:59
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
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

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
) ENGINE = InnoDB AUTO_INCREMENT = 15 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

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
INSERT INTO `dict_data_type` VALUES (14, NULL, NULL, NULL, NULL, '2022-03-22 08:53:47', 'unknown', NULL, NULL);
INSERT INTO `dict_data_type` VALUES (15, 'enum_test', '8', '88', NULL, '2022-03-22 08:55:07', 'unknown', NULL, NULL);

-- ----------------------------
-- Table structure for file_info
-- ----------------------------
DROP TABLE IF EXISTS `file_info`;
CREATE TABLE `file_info`  (
  `id` int(0) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '相对路径',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '文件名',
  `suffix` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '文件后缀',
  `size` bigint(0) NULL DEFAULT NULL COMMENT '文件大小(字节B)',
  `created_at` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '修改时间',
  `shard_index` int(0) NULL DEFAULT NULL COMMENT '已经上传的分片',
  `shard_size` int(0) NULL DEFAULT NULL COMMENT '分片大小(字节B)',
  `shard_total` int(0) NULL DEFAULT NULL COMMENT '分片总数',
  `file_key` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '文件标识',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `file_key`(`file_key`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of file_info
-- ----------------------------
INSERT INTO `file_info` VALUES (6, 'files\\89042_20170622172520.zip', '89042_20170622172520.zip', 'zip', 29378779, '2022-01-10 18:10:33', '2022-01-10 18:10:33', 2, 20971520, 2, '1uyBa5FxkgCsYwMCcA4GAk');
INSERT INTO `file_info` VALUES (7, 'files\\雷达-网关-添加2个接口.zip', '雷达-网关-添加2个接口.zip', 'zip', 52495657, '2022-01-11 09:03:46', '2022-01-11 09:03:46', 3, 20971520, 3, '79cIXgIuvSCGWKAkU4sKgs');
INSERT INTO `file_info` VALUES (8, 'files\\2883.exe', '2883.exe', 'exe', 60015792, '2022-01-11 09:23:27', '2022-01-11 09:23:27', 3, 20971520, 3, '5qAsd53tSMk0EiU04SScoq');
INSERT INTO `file_info` VALUES (9, 'files\\apache-jmeter-5.4.1.zip', 'apache-jmeter-5.4.1.zip', 'zip', 74032019, '2022-01-11 09:41:14', '2022-01-11 09:41:14', 4, 20971520, 4, '3Fhy0osUsgeGKqeq0Icua');

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
) ENGINE = InnoDB AUTO_INCREMENT = 57 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

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
) ENGINE = InnoDB AUTO_INCREMENT = 48 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

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
) ENGINE = InnoDB AUTO_INCREMENT = 14 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

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
) ENGINE = InnoDB AUTO_INCREMENT = 36 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

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
) ENGINE = InnoDB AUTO_INCREMENT = 14 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

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
-- Table structure for qrtz_blob_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_blob_triggers`;
CREATE TABLE `qrtz_blob_triggers`  (
  `sched_name` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `trigger_name` varchar(190) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `trigger_group` varchar(190) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `blob_data` blob NULL,
  PRIMARY KEY (`sched_name`, `trigger_name`, `trigger_group`) USING BTREE,
  INDEX `sched_name`(`sched_name`, `trigger_name`, `trigger_group`) USING BTREE,
  CONSTRAINT `qrtz_blob_triggers_ibfk_1` FOREIGN KEY (`sched_name`, `trigger_name`, `trigger_group`) REFERENCES `qrtz_triggers` (`sched_name`, `trigger_name`, `trigger_group`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for qrtz_calendars
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_calendars`;
CREATE TABLE `qrtz_calendars`  (
  `sched_name` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `calendar_name` varchar(190) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `calendar` blob NOT NULL,
  PRIMARY KEY (`sched_name`, `calendar_name`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for qrtz_cron_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_cron_triggers`;
CREATE TABLE `qrtz_cron_triggers`  (
  `sched_name` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `trigger_name` varchar(190) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `trigger_group` varchar(190) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `cron_expression` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `time_zone_id` varchar(80) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`sched_name`, `trigger_name`, `trigger_group`) USING BTREE,
  CONSTRAINT `qrtz_cron_triggers_ibfk_1` FOREIGN KEY (`sched_name`, `trigger_name`, `trigger_group`) REFERENCES `qrtz_triggers` (`sched_name`, `trigger_name`, `trigger_group`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for qrtz_fired_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_fired_triggers`;
CREATE TABLE `qrtz_fired_triggers`  (
  `sched_name` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `entry_id` varchar(95) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `trigger_name` varchar(190) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `trigger_group` varchar(190) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `instance_name` varchar(190) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `fired_time` bigint(0) NOT NULL,
  `sched_time` bigint(0) NOT NULL,
  `priority` int(0) NOT NULL,
  `state` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `job_name` varchar(190) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `job_group` varchar(190) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `is_nonconcurrent` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `requests_recovery` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`sched_name`, `entry_id`) USING BTREE,
  INDEX `idx_qrtz_ft_trig_inst_name`(`sched_name`, `instance_name`) USING BTREE,
  INDEX `idx_qrtz_ft_inst_job_req_rcvry`(`sched_name`, `instance_name`, `requests_recovery`) USING BTREE,
  INDEX `idx_qrtz_ft_j_g`(`sched_name`, `job_name`, `job_group`) USING BTREE,
  INDEX `idx_qrtz_ft_jg`(`sched_name`, `job_group`) USING BTREE,
  INDEX `idx_qrtz_ft_t_g`(`sched_name`, `trigger_name`, `trigger_group`) USING BTREE,
  INDEX `idx_qrtz_ft_tg`(`sched_name`, `trigger_group`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for qrtz_job
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_job`;
CREATE TABLE `qrtz_job`  (
  `id` int(0) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `job_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '任务名称',
  `cron` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT 'cron表达式',
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '描述',
  `bean_class` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '任务执行类（包名+类名）',
  `status` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '状态',
  `job_group` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '任务分组',
  `job_data_map` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '参数',
  `last_fire_time` datetime(0) NULL DEFAULT NULL COMMENT '上次执行时间',
  `next_fire_time` datetime(0) NULL DEFAULT NULL COMMENT '下次执行时间',
  `create_by` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '创建人ID',
  `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '更新人ID',
  `update_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '定时任务' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of qrtz_job
-- ----------------------------
INSERT INTO `qrtz_job` VALUES (3, 'job1', '*/5 * * * * ?', NULL, 'com.iscas.biz.mp.test.job.TestJob1', '暂停', 'default', '{\"username\":\"zhangsan\", \"age\":18}', '2022-03-26 11:13:43', NULL, NULL, '2022-03-26 11:12:03', NULL, '2022-03-26 11:12:06');
INSERT INTO `qrtz_job` VALUES (4, 'job2', '*/5 * * * * ?', NULL, 'cn.ac.iscas.dmo.quartz.job.TestJob2', '1', 'default', '{\"username\":\"zhangsan\", \"age\":18}', '2022-03-26 11:13:47', NULL, NULL, '2022-03-24 11:12:43', NULL, '2022-03-17 04:02:15');

-- ----------------------------
-- Table structure for qrtz_job_details
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_job_details`;
CREATE TABLE `qrtz_job_details`  (
  `sched_name` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `job_name` varchar(190) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `job_group` varchar(190) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `description` varchar(250) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `job_class_name` varchar(250) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `is_durable` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `is_nonconcurrent` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `is_update_data` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `requests_recovery` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `job_data` blob NULL,
  PRIMARY KEY (`sched_name`, `job_name`, `job_group`) USING BTREE,
  INDEX `idx_qrtz_j_req_recovery`(`sched_name`, `requests_recovery`) USING BTREE,
  INDEX `idx_qrtz_j_grp`(`sched_name`, `job_group`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for qrtz_locks
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_locks`;
CREATE TABLE `qrtz_locks`  (
  `sched_name` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `lock_name` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  PRIMARY KEY (`sched_name`, `lock_name`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of qrtz_locks
-- ----------------------------
INSERT INTO `qrtz_locks` VALUES ('clusteredScheduler', 'STATE_ACCESS');
INSERT INTO `qrtz_locks` VALUES ('clusteredScheduler', 'TRIGGER_ACCESS');

-- ----------------------------
-- Table structure for qrtz_paused_trigger_grps
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_paused_trigger_grps`;
CREATE TABLE `qrtz_paused_trigger_grps`  (
  `sched_name` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `trigger_group` varchar(190) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  PRIMARY KEY (`sched_name`, `trigger_group`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for qrtz_scheduler_state
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_scheduler_state`;
CREATE TABLE `qrtz_scheduler_state`  (
  `sched_name` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `instance_name` varchar(190) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `last_checkin_time` bigint(0) NOT NULL,
  `checkin_interval` bigint(0) NOT NULL,
  PRIMARY KEY (`sched_name`, `instance_name`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of qrtz_scheduler_state
-- ----------------------------
INSERT INTO `qrtz_scheduler_state` VALUES ('clusteredScheduler', 'C3Stones-PC', 1600918524362, 10000);

-- ----------------------------
-- Table structure for qrtz_simple_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_simple_triggers`;
CREATE TABLE `qrtz_simple_triggers`  (
  `sched_name` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `trigger_name` varchar(190) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `trigger_group` varchar(190) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `repeat_count` bigint(0) NOT NULL,
  `repeat_interval` bigint(0) NOT NULL,
  `times_triggered` bigint(0) NOT NULL,
  PRIMARY KEY (`sched_name`, `trigger_name`, `trigger_group`) USING BTREE,
  CONSTRAINT `qrtz_simple_triggers_ibfk_1` FOREIGN KEY (`sched_name`, `trigger_name`, `trigger_group`) REFERENCES `qrtz_triggers` (`sched_name`, `trigger_name`, `trigger_group`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for qrtz_simprop_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_simprop_triggers`;
CREATE TABLE `qrtz_simprop_triggers`  (
  `sched_name` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `trigger_name` varchar(190) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `trigger_group` varchar(190) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `str_prop_1` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `str_prop_2` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `str_prop_3` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `int_prop_1` int(0) NULL DEFAULT NULL,
  `int_prop_2` int(0) NULL DEFAULT NULL,
  `long_prop_1` bigint(0) NULL DEFAULT NULL,
  `long_prop_2` bigint(0) NULL DEFAULT NULL,
  `dec_prop_1` decimal(13, 4) NULL DEFAULT NULL,
  `dec_prop_2` decimal(13, 4) NULL DEFAULT NULL,
  `bool_prop_1` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `bool_prop_2` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`sched_name`, `trigger_name`, `trigger_group`) USING BTREE,
  CONSTRAINT `qrtz_simprop_triggers_ibfk_1` FOREIGN KEY (`sched_name`, `trigger_name`, `trigger_group`) REFERENCES `qrtz_triggers` (`sched_name`, `trigger_name`, `trigger_group`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for qrtz_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_triggers`;
CREATE TABLE `qrtz_triggers`  (
  `sched_name` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `trigger_name` varchar(190) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `trigger_group` varchar(190) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `job_name` varchar(190) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `job_group` varchar(190) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `description` varchar(250) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `next_fire_time` bigint(0) NULL DEFAULT NULL,
  `prev_fire_time` bigint(0) NULL DEFAULT NULL,
  `priority` int(0) NULL DEFAULT NULL,
  `trigger_state` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `trigger_type` varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `start_time` bigint(0) NOT NULL,
  `end_time` bigint(0) NULL DEFAULT NULL,
  `calendar_name` varchar(190) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `misfire_instr` smallint(0) NULL DEFAULT NULL,
  `job_data` blob NULL,
  PRIMARY KEY (`sched_name`, `trigger_name`, `trigger_group`) USING BTREE,
  INDEX `idx_qrtz_t_j`(`sched_name`, `job_name`, `job_group`) USING BTREE,
  INDEX `idx_qrtz_t_jg`(`sched_name`, `job_group`) USING BTREE,
  INDEX `idx_qrtz_t_c`(`sched_name`, `calendar_name`) USING BTREE,
  INDEX `idx_qrtz_t_g`(`sched_name`, `trigger_group`) USING BTREE,
  INDEX `idx_qrtz_t_state`(`sched_name`, `trigger_state`) USING BTREE,
  INDEX `idx_qrtz_t_n_state`(`sched_name`, `trigger_name`, `trigger_group`, `trigger_state`) USING BTREE,
  INDEX `idx_qrtz_t_n_g_state`(`sched_name`, `trigger_group`, `trigger_state`) USING BTREE,
  INDEX `idx_qrtz_t_next_fire_time`(`sched_name`, `next_fire_time`) USING BTREE,
  INDEX `idx_qrtz_t_nft_st`(`sched_name`, `trigger_state`, `next_fire_time`) USING BTREE,
  INDEX `idx_qrtz_t_nft_misfire`(`sched_name`, `misfire_instr`, `next_fire_time`) USING BTREE,
  INDEX `idx_qrtz_t_nft_st_misfire`(`sched_name`, `misfire_instr`, `next_fire_time`, `trigger_state`) USING BTREE,
  INDEX `idx_qrtz_t_nft_st_misfire_grp`(`sched_name`, `misfire_instr`, `next_fire_time`, `trigger_group`, `trigger_state`) USING BTREE,
  CONSTRAINT `qrtz_triggers_ibfk_1` FOREIGN KEY (`sched_name`, `job_name`, `job_group`) REFERENCES `qrtz_job_details` (`sched_name`, `job_name`, `job_group`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

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
) ENGINE = InnoDB AUTO_INCREMENT = 24 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

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
) ENGINE = InnoDB AUTO_INCREMENT = 14 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

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
INSERT INTO `shedlock` VALUES ('shedLockTest', '2022-03-26 11:00:10.005', '2022-03-26 11:00:00.014', 'DESKTOP-4OHH3FF');

-- ----------------------------
-- Table structure for test
-- ----------------------------
DROP TABLE IF EXISTS `test`;
CREATE TABLE `test`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `age` int(0) NULL DEFAULT NULL,
  `flag` int(0) NULL DEFAULT 0,
  `create_time` datetime(0) NULL DEFAULT NULL,
  `update_time` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 14 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of test
-- ----------------------------
INSERT INTO `test` VALUES (1, '1', 1, 0, NULL, NULL);
INSERT INTO `test` VALUES (2, '2', 2, 0, NULL, NULL);
INSERT INTO `test` VALUES (3, '3', 44, 0, NULL, NULL);
INSERT INTO `test` VALUES (4, '4', 23, 0, NULL, NULL);
INSERT INTO `test` VALUES (5, '5', 18, 1, NULL, NULL);
INSERT INTO `test` VALUES (12, '张三', 12, 0, '2022-01-03 21:05:53', '2022-01-04 09:30:02');
INSERT INTO `test` VALUES (13, '张三', 12, 0, '2022-01-04 09:26:38', '2022-01-04 09:30:02');
INSERT INTO `test` VALUES (14, '张三', 12, 0, '2022-01-04 09:29:41', '2022-01-04 09:30:02');

-- ----------------------------
-- Table structure for test_mp_ar
-- ----------------------------
DROP TABLE IF EXISTS `test_mp_ar`;
CREATE TABLE `test_mp_ar`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 71 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

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
-- Table structure for test_yy
-- ----------------------------
DROP TABLE IF EXISTS `test_yy`;
CREATE TABLE `test_yy`  (
  `c1` tinyint(0) NULL DEFAULT NULL COMMENT 'test comment',
  `vendor_group2` int(0) NOT NULL COMMENT '11',
  `vendor_group` int(0) NOT NULL,
  `c2` smallint(0) NULL DEFAULT NULL,
  `c3` int(0) NULL DEFAULT NULL,
  `c4` int(0) NULL DEFAULT NULL,
  `c5` int(0) NULL DEFAULT NULL,
  `c6` bigint(0) NULL DEFAULT NULL,
  `c9` double NULL DEFAULT NULL,
  `c10` datetime(0) NULL DEFAULT NULL,
  `C11` datetime(0) NULL DEFAULT NULL,
  `C12` datetime(0) NULL DEFAULT NULL,
  `C13` datetime(0) NULL DEFAULT NULL,
  `c14` datetime(0) NULL DEFAULT NULL,
  `c15` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `c16` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `c17` blob NULL,
  `c18` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL,
  `c19` blob NULL,
  `c20` mediumtext CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL,
  `add_col` tinyint(0) NULL DEFAULT NULL COMMENT 'test comment'
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '123' ROW_FORMAT = Dynamic;

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
) ENGINE = InnoDB AUTO_INCREMENT = 77 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

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
INSERT INTO `user_info` VALUES (52, 'wangermazi', NULL, NULL, '12547889874', NULL, 1, '2022-01-07 10:02:28', '2022-01-07 10:02:28');
INSERT INTO `user_info` VALUES (53, '测试数据', NULL, NULL, '12547889874', NULL, 1, '2022-01-07 10:02:28', '2022-01-07 10:02:28');

-- ----------------------------
-- Table structure for user_info_menu
-- ----------------------------
DROP TABLE IF EXISTS `user_info_menu`;
CREATE TABLE `user_info_menu`  (
  `menu_id` int(0) NOT NULL AUTO_INCREMENT,
  `menu_pid` int(0) NULL DEFAULT NULL,
  `menu_page` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '前端的路径',
  `menu_create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `menu_update_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP COMMENT '最后修改时间',
  `menu_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `dmo_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '隐藏列,数据中台处理的游标',
  PRIMARY KEY (`menu_id`) USING BTREE,
  UNIQUE INDEX `dmo_id`(`dmo_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

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
) ENGINE = InnoDB AUTO_INCREMENT = 20515 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of ws_data
-- ----------------------------
INSERT INTO `ws_data` VALUES (20315, 'BUSINESS', '5ba5ef18-aff7-42bd-967c-1cf5ca75197a', 'admin', b'1', '测试点对点数据，持久化1647825480012', '/queue/message', b'0', '2022-03-21 09:18:00');
INSERT INTO `ws_data` VALUES (20316, 'BUSINESS', '2cf40a19-ee6f-4375-8193-e8368c57a75c', 'admin', b'1', '测试点对点数据，持久化1647825480019', '/queue/message', b'0', '2022-03-21 09:18:00');
INSERT INTO `ws_data` VALUES (20317, 'BUSINESS', 'ac6fe6c8-544c-4297-bd4b-b75894a0bb09', 'admin', b'1', '测试点对点数据，持久化1647825480024', '/queue/message', b'0', '2022-03-21 09:18:00');
INSERT INTO `ws_data` VALUES (20318, 'BUSINESS', '0fb8695a-b3ae-4d70-9192-e207a8f2d393', 'admin', b'1', '测试点对点数据，持久化1647825510018', '/queue/message', b'0', '2022-03-21 09:18:30');
INSERT INTO `ws_data` VALUES (20319, 'BUSINESS', '73a83fbf-8f92-4408-9b17-e462f4cc121c', 'admin', b'1', '测试点对点数据，持久化1647825510020', '/queue/message', b'0', '2022-03-21 09:18:30');
INSERT INTO `ws_data` VALUES (20320, 'BUSINESS', 'b14ec1f4-43cc-42cd-8cc1-db81eea5b976', 'admin', b'1', '测试点对点数据，持久化1647825510019', '/queue/message', b'0', '2022-03-21 09:18:30');
INSERT INTO `ws_data` VALUES (20321, 'BUSINESS', 'd964d05e-2126-4959-b4f9-9a5d0fda8249', 'admin', b'1', '测试点对点数据，持久化1647825540024', '/queue/message', b'0', '2022-03-21 09:19:00');
INSERT INTO `ws_data` VALUES (20322, 'BUSINESS', 'bcd4a947-9903-4c7d-aabc-7c300c8e22e5', 'admin', b'1', '测试点对点数据，持久化1647825540017', '/queue/message', b'0', '2022-03-21 09:19:00');
INSERT INTO `ws_data` VALUES (20323, 'BUSINESS', 'ea43e55f-5e68-4a58-99b5-237c28be2591', 'admin', b'1', '测试点对点数据，持久化1647825540023', '/queue/message', b'0', '2022-03-21 09:19:00');
INSERT INTO `ws_data` VALUES (20324, 'BUSINESS', '19862480-47ba-4a71-a0ae-79c91beb5592', 'admin', b'1', '测试点对点数据，持久化1647825570014', '/queue/message', b'0', '2022-03-21 09:19:30');
INSERT INTO `ws_data` VALUES (20325, 'BUSINESS', '8a84faee-39bd-4b7d-8614-39ef7c07bf92', 'admin', b'1', '测试点对点数据，持久化1647825570014', '/queue/message', b'0', '2022-03-21 09:19:30');
INSERT INTO `ws_data` VALUES (20326, 'BUSINESS', '66369370-aa95-40af-a14d-4c5323b2641d', 'admin', b'1', '测试点对点数据，持久化1647825570013', '/queue/message', b'0', '2022-03-21 09:19:30');
INSERT INTO `ws_data` VALUES (20327, 'BUSINESS', '1a5fcc79-a586-40f8-b775-27607a35d1c1', 'admin', b'1', '测试点对点数据，持久化1647825600009', '/queue/message', b'0', '2022-03-21 09:20:00');
INSERT INTO `ws_data` VALUES (20328, 'BUSINESS', 'f322e61d-c230-489e-aa84-f3d9dec75714', 'admin', b'1', '测试点对点数据，持久化1647825600009', '/queue/message', b'0', '2022-03-21 09:20:00');
INSERT INTO `ws_data` VALUES (20329, 'BUSINESS', '9f368b8a-72cd-4099-9f08-d4ca777d3a77', 'admin', b'1', '测试点对点数据，持久化1647825600020', '/queue/message', b'0', '2022-03-21 09:20:00');
INSERT INTO `ws_data` VALUES (20330, 'BUSINESS', 'd2e01215-6aec-404b-b24e-b72d1e4b1c31', 'admin', b'1', '测试点对点数据，持久化1647825630011', '/queue/message', b'0', '2022-03-21 09:20:30');
INSERT INTO `ws_data` VALUES (20331, 'BUSINESS', '057fdb77-f1b2-4eb7-ad3b-1a293eebe227', 'admin', b'1', '测试点对点数据，持久化1647825630025', '/queue/message', b'0', '2022-03-21 09:20:30');
INSERT INTO `ws_data` VALUES (20332, 'BUSINESS', '8d92b67b-f683-4c84-b7ff-029de4d4bc70', 'admin', b'1', '测试点对点数据，持久化1647825630031', '/queue/message', b'0', '2022-03-21 09:20:30');
INSERT INTO `ws_data` VALUES (20333, 'BUSINESS', '5176c4da-439f-42e4-b939-30fe90a96a44', 'admin', b'1', '测试点对点数据，持久化1647825660017', '/queue/message', b'0', '2022-03-21 09:21:00');
INSERT INTO `ws_data` VALUES (20334, 'BUSINESS', '77524d12-9509-41c0-a038-a6c563d94148', 'admin', b'1', '测试点对点数据，持久化1647825660011', '/queue/message', b'0', '2022-03-21 09:21:00');
INSERT INTO `ws_data` VALUES (20335, 'BUSINESS', '5ddc8fb8-a8f7-4474-b43e-ee9401668ae3', 'admin', b'1', '测试点对点数据，持久化1647825660022', '/queue/message', b'0', '2022-03-21 09:21:00');
INSERT INTO `ws_data` VALUES (20336, 'BUSINESS', '2567e665-800b-4646-8f7b-a3f02cfac82e', 'admin', b'1', '测试点对点数据，持久化1647825690008', '/queue/message', b'0', '2022-03-21 09:21:30');
INSERT INTO `ws_data` VALUES (20337, 'BUSINESS', '33a56a06-9fd4-401a-a4aa-7664d03bacf7', 'admin', b'1', '测试点对点数据，持久化1647825690039', '/queue/message', b'0', '2022-03-21 09:21:30');
INSERT INTO `ws_data` VALUES (20338, 'BUSINESS', '1a23fa48-0c56-480a-9a9b-3e4d29a8787f', 'admin', b'1', '测试点对点数据，持久化1647825690030', '/queue/message', b'0', '2022-03-21 09:21:30');
INSERT INTO `ws_data` VALUES (20339, 'BUSINESS', 'e708ec5b-e06f-4f22-aa72-500d1049b65f', 'admin', b'1', '测试点对点数据，持久化1647825720024', '/queue/message', b'0', '2022-03-21 09:22:00');
INSERT INTO `ws_data` VALUES (20340, 'BUSINESS', '2993320c-b240-4971-9b3b-8e634d58064c', 'admin', b'1', '测试点对点数据，持久化1647825720027', '/queue/message', b'0', '2022-03-21 09:22:00');
INSERT INTO `ws_data` VALUES (20341, 'BUSINESS', 'af6396b7-46d2-4f3d-af8a-16bab6d4e2ab', 'admin', b'1', '测试点对点数据，持久化1647825720028', '/queue/message', b'0', '2022-03-21 09:22:00');
INSERT INTO `ws_data` VALUES (20342, 'BUSINESS', '8df90f02-68e8-40ef-83fb-5885d757eac7', 'admin', b'1', '测试点对点数据，持久化1647825720056', '/queue/message', b'0', '2022-03-21 09:22:00');
INSERT INTO `ws_data` VALUES (20343, 'BUSINESS', 'd21e618f-cbb5-4248-925e-fea704d4345f', 'admin', b'1', '测试点对点数据，持久化1647825750013', '/queue/message', b'0', '2022-03-21 09:22:30');
INSERT INTO `ws_data` VALUES (20344, 'BUSINESS', 'a6074467-b74d-47d8-9af2-a5f3f3c6dd76', 'admin', b'1', '测试点对点数据，持久化1647825750022', '/queue/message', b'0', '2022-03-21 09:22:30');
INSERT INTO `ws_data` VALUES (20345, 'BUSINESS', '595ccf27-e733-4fb5-9518-0dd19bc9dfbd', 'admin', b'1', '测试点对点数据，持久化1647825750021', '/queue/message', b'0', '2022-03-21 09:22:30');
INSERT INTO `ws_data` VALUES (20346, 'BUSINESS', '4db253bc-ca2c-4b59-bc01-e995c63054d7', 'admin', b'1', '测试点对点数据，持久化1647825780014', '/queue/message', b'0', '2022-03-21 09:23:00');
INSERT INTO `ws_data` VALUES (20347, 'BUSINESS', '2945d6be-8ce0-45e5-bb75-ae0bdfeb57a4', 'admin', b'1', '测试点对点数据，持久化1647825780015', '/queue/message', b'0', '2022-03-21 09:23:00');
INSERT INTO `ws_data` VALUES (20348, 'BUSINESS', '60e9cde4-e092-4b82-8a91-d8dc369c6cbc', 'admin', b'1', '测试点对点数据，持久化1647825780019', '/queue/message', b'0', '2022-03-21 09:23:00');
INSERT INTO `ws_data` VALUES (20349, 'BUSINESS', '570e92ef-3f5f-4130-ac5e-92ba4af31d4a', 'admin', b'1', '测试点对点数据，持久化1647825810011', '/queue/message', b'0', '2022-03-21 09:23:30');
INSERT INTO `ws_data` VALUES (20350, 'BUSINESS', 'b6e6d6b9-464a-4796-b660-b1a694533093', 'admin', b'1', '测试点对点数据，持久化1647825810019', '/queue/message', b'0', '2022-03-21 09:23:30');
INSERT INTO `ws_data` VALUES (20351, 'BUSINESS', '2dd63817-8db1-4b57-a1c2-b9246e6a4bb4', 'admin', b'1', '测试点对点数据，持久化1647825810014', '/queue/message', b'0', '2022-03-21 09:23:30');
INSERT INTO `ws_data` VALUES (20352, 'BUSINESS', 'd8895f21-7613-4226-9d6a-ae7685fd6014', 'admin', b'1', '测试点对点数据，持久化1647825820138', '/queue/message', b'0', '2022-03-21 09:23:40');
INSERT INTO `ws_data` VALUES (20353, 'BUSINESS', '8a212ffd-b794-4318-9ba1-ad861318e456', 'admin', b'1', '测试点对点数据，持久化1647825840010', '/queue/message', b'0', '2022-03-21 09:24:00');
INSERT INTO `ws_data` VALUES (20354, 'BUSINESS', '01f33394-4f81-46e0-ba12-277b40b0936f', 'admin', b'1', '测试点对点数据，持久化1647825840011', '/queue/message', b'0', '2022-03-21 09:24:00');
INSERT INTO `ws_data` VALUES (20355, 'BUSINESS', 'c5bb402e-0142-439a-ba44-075d45745862', 'admin', b'1', '测试点对点数据，持久化1647825840019', '/queue/message', b'0', '2022-03-21 09:24:00');
INSERT INTO `ws_data` VALUES (20356, 'BUSINESS', '2d9d32c9-bbb5-4148-89bb-95a12eb3cc5c', 'admin', b'1', '测试点对点数据，持久化1647825842621', '/queue/message', b'0', '2022-03-21 09:24:02');
INSERT INTO `ws_data` VALUES (20357, 'BUSINESS', 'e09c3b59-b567-4679-85af-ac157a7c23ac', 'admin', b'1', '测试点对点数据，持久化1647825870012', '/queue/message', b'0', '2022-03-21 09:24:30');
INSERT INTO `ws_data` VALUES (20358, 'BUSINESS', 'c90d6606-5052-4fad-a154-8d7081d27535', 'admin', b'1', '测试点对点数据，持久化1647825870013', '/queue/message', b'0', '2022-03-21 09:24:30');
INSERT INTO `ws_data` VALUES (20359, 'BUSINESS', 'd2e1e63d-0226-458e-ad37-0b5cad4e0a1e', 'admin', b'1', '测试点对点数据，持久化1647825870010', '/queue/message', b'0', '2022-03-21 09:24:30');
INSERT INTO `ws_data` VALUES (20360, 'BUSINESS', 'a644185f-e874-490c-8bad-1c09d2955d7c', 'admin', b'1', '测试点对点数据，持久化1647825881955', '/queue/message', b'0', '2022-03-21 09:24:41');
INSERT INTO `ws_data` VALUES (20361, 'BUSINESS', 'd0256f9c-cfcd-42c2-b597-7389e9441c54', 'admin', b'1', '测试点对点数据，持久化1647825900013', '/queue/message', b'0', '2022-03-21 09:25:00');
INSERT INTO `ws_data` VALUES (20362, 'BUSINESS', 'd79e262e-483b-412a-a4c0-816ba3fb0b15', 'admin', b'1', '测试点对点数据，持久化1647825900014', '/queue/message', b'0', '2022-03-21 09:25:00');
INSERT INTO `ws_data` VALUES (20363, 'BUSINESS', '978b8dd5-56d2-4c87-bdec-914a8846fe01', 'admin', b'1', '测试点对点数据，持久化1647825900021', '/queue/message', b'0', '2022-03-21 09:25:00');
INSERT INTO `ws_data` VALUES (20364, 'BUSINESS', '1e76bbdd-5464-4761-99b9-8aec59eb363d', 'admin', b'1', '测试点对点数据，持久化1647825900016', '/queue/message', b'0', '2022-03-21 09:25:00');
INSERT INTO `ws_data` VALUES (20365, 'BUSINESS', '2f55d34c-3f94-46c5-95bc-7fea11ac90a3', 'admin', b'1', '测试点对点数据，持久化1647825930010', '/queue/message', b'0', '2022-03-21 09:25:30');
INSERT INTO `ws_data` VALUES (20366, 'BUSINESS', '844cd0cc-e340-456f-a9b9-2bc39178bb32', 'admin', b'1', '测试点对点数据，持久化1647825930022', '/queue/message', b'0', '2022-03-21 09:25:30');
INSERT INTO `ws_data` VALUES (20367, 'BUSINESS', '8b14f148-527a-4049-b0df-a9c61f7c9355', 'admin', b'1', '测试点对点数据，持久化1647825930027', '/queue/message', b'0', '2022-03-21 09:25:30');
INSERT INTO `ws_data` VALUES (20368, 'BUSINESS', '9f44d9bc-5f18-4bb9-b62f-4a3029c66f98', 'admin', b'1', '测试点对点数据，持久化1647825930020', '/queue/message', b'0', '2022-03-21 09:25:30');
INSERT INTO `ws_data` VALUES (20369, 'BUSINESS', 'a6f43723-1e3d-44ac-94a5-ce1db9095ff5', 'admin', b'1', '测试点对点数据，持久化1647825960015', '/queue/message', b'0', '2022-03-21 09:26:00');
INSERT INTO `ws_data` VALUES (20370, 'BUSINESS', 'acd188a3-8e69-4b2d-b76f-34d998eea54f', 'admin', b'1', '测试点对点数据，持久化1647825960009', '/queue/message', b'0', '2022-03-21 09:26:00');
INSERT INTO `ws_data` VALUES (20371, 'BUSINESS', 'fd7e05cc-0d90-40e2-888c-9fb2273d8441', 'admin', b'1', '测试点对点数据，持久化1647825960026', '/queue/message', b'0', '2022-03-21 09:26:00');
INSERT INTO `ws_data` VALUES (20372, 'BUSINESS', 'b552d7fb-54dc-4bd8-ad52-66cc84865ad6', 'admin', b'1', '测试点对点数据，持久化1647825960020', '/queue/message', b'0', '2022-03-21 09:26:00');
INSERT INTO `ws_data` VALUES (20373, 'BUSINESS', '0bcd6756-3a4c-4e2e-bfcb-dee31b7ea009', 'admin', b'1', '测试点对点数据，持久化1647825990018', '/queue/message', b'0', '2022-03-21 09:26:30');
INSERT INTO `ws_data` VALUES (20374, 'BUSINESS', '12285cfe-30d3-444e-9a7d-c7e554623036', 'admin', b'1', '测试点对点数据，持久化1647825990031', '/queue/message', b'0', '2022-03-21 09:26:30');
INSERT INTO `ws_data` VALUES (20375, 'BUSINESS', 'd6add369-48ad-4186-8568-941b58a51186', 'admin', b'1', '测试点对点数据，持久化1647825990028', '/queue/message', b'0', '2022-03-21 09:26:30');
INSERT INTO `ws_data` VALUES (20376, 'BUSINESS', 'e407f79c-cd7a-4193-a1b2-67f7008fe027', 'admin', b'1', '测试点对点数据，持久化1647825990028', '/queue/message', b'0', '2022-03-21 09:26:30');
INSERT INTO `ws_data` VALUES (20377, 'BUSINESS', '17116439-bc50-4bad-9364-509f9310363a', 'admin', b'1', '测试点对点数据，持久化1647826020009', '/queue/message', b'0', '2022-03-21 09:27:00');
INSERT INTO `ws_data` VALUES (20378, 'BUSINESS', '9032ca81-0d57-42d8-9869-c2ee963a2393', 'admin', b'1', '测试点对点数据，持久化1647826020017', '/queue/message', b'0', '2022-03-21 09:27:00');
INSERT INTO `ws_data` VALUES (20379, 'BUSINESS', 'eac43834-34e8-4236-a732-d584eafe5994', 'admin', b'1', '测试点对点数据，持久化1647826020023', '/queue/message', b'0', '2022-03-21 09:27:00');
INSERT INTO `ws_data` VALUES (20380, 'BUSINESS', 'ea9031ac-f4cb-4389-90cb-ceff72081fb2', 'admin', b'1', '测试点对点数据，持久化1647826020028', '/queue/message', b'0', '2022-03-21 09:27:00');
INSERT INTO `ws_data` VALUES (20381, 'BUSINESS', '2bf45a5a-85b0-4ef1-9127-31c9df899879', 'admin', b'1', '测试点对点数据，持久化1647826050016', '/queue/message', b'0', '2022-03-21 09:27:30');
INSERT INTO `ws_data` VALUES (20382, 'BUSINESS', 'fa60d8ac-9f4c-4051-8ddd-85b8c9aa65e8', 'admin', b'1', '测试点对点数据，持久化1647826050011', '/queue/message', b'0', '2022-03-21 09:27:30');
INSERT INTO `ws_data` VALUES (20383, 'BUSINESS', 'b7da9c28-418b-48af-88f4-78bc7f44c36b', 'admin', b'1', '测试点对点数据，持久化1647826050010', '/queue/message', b'0', '2022-03-21 09:27:30');
INSERT INTO `ws_data` VALUES (20384, 'BUSINESS', '5621e654-ae5f-4387-9442-cc88d0530a77', 'admin', b'1', '测试点对点数据，持久化1647826050034', '/queue/message', b'0', '2022-03-21 09:27:30');
INSERT INTO `ws_data` VALUES (20385, 'BUSINESS', '3ea9663c-27cd-4728-a4ac-4919fbfe4bb0', 'admin', b'1', '测试点对点数据，持久化1647826080015', '/queue/message', b'0', '2022-03-21 09:28:00');
INSERT INTO `ws_data` VALUES (20386, 'BUSINESS', 'da7d9433-28af-4387-9e72-5fc198bd7d00', 'admin', b'1', '测试点对点数据，持久化1647826080025', '/queue/message', b'0', '2022-03-21 09:28:00');
INSERT INTO `ws_data` VALUES (20387, 'BUSINESS', 'ff33c13f-0a96-4717-b550-1a5a185a5e73', 'admin', b'1', '测试点对点数据，持久化1647826080012', '/queue/message', b'0', '2022-03-21 09:28:00');
INSERT INTO `ws_data` VALUES (20388, 'BUSINESS', 'bd07122d-38c8-4ea2-ba6a-f85c156df89b', 'admin', b'1', '测试点对点数据，持久化1647826080024', '/queue/message', b'0', '2022-03-21 09:28:00');
INSERT INTO `ws_data` VALUES (20389, 'BUSINESS', '938817c0-6903-4a05-b41b-d670b6aa13cf', 'admin', b'1', '测试点对点数据，持久化1647826110004', '/queue/message', b'0', '2022-03-21 09:28:30');
INSERT INTO `ws_data` VALUES (20390, 'BUSINESS', 'c53b6444-1c97-4e7d-913e-0127eb43a6d5', 'admin', b'1', '测试点对点数据，持久化1647826110009', '/queue/message', b'0', '2022-03-21 09:28:30');
INSERT INTO `ws_data` VALUES (20391, 'BUSINESS', '2b33325b-fb04-4f0b-8bca-8568e2413394', 'admin', b'1', '测试点对点数据，持久化1647826110017', '/queue/message', b'0', '2022-03-21 09:28:30');
INSERT INTO `ws_data` VALUES (20392, 'BUSINESS', 'e569f3e0-077c-4a3c-83a5-002fa22ea68c', 'admin', b'1', '测试点对点数据，持久化1647826110022', '/queue/message', b'0', '2022-03-21 09:28:30');
INSERT INTO `ws_data` VALUES (20393, 'BUSINESS', '96ec1bdd-9b26-4613-947c-31aff9dba9ff', 'admin', b'1', '测试点对点数据，持久化1647826140014', '/queue/message', b'0', '2022-03-21 09:29:00');
INSERT INTO `ws_data` VALUES (20394, 'BUSINESS', '6a4caeda-e5ae-4cf0-867f-b69911ae889a', 'admin', b'1', '测试点对点数据，持久化1647826140010', '/queue/message', b'0', '2022-03-21 09:29:00');
INSERT INTO `ws_data` VALUES (20395, 'BUSINESS', 'a66e0bc9-0fc8-4707-b3cc-adc920bac9aa', 'admin', b'1', '测试点对点数据，持久化1647826140011', '/queue/message', b'0', '2022-03-21 09:29:00');
INSERT INTO `ws_data` VALUES (20396, 'BUSINESS', '7d3a09a6-2f79-4d9a-a14b-fb057a1ae3f9', 'admin', b'1', '测试点对点数据，持久化1647826140016', '/queue/message', b'0', '2022-03-21 09:29:00');
INSERT INTO `ws_data` VALUES (20397, 'BUSINESS', '516e396c-5726-4839-a8e7-173243f08a76', 'admin', b'1', '测试点对点数据，持久化1647826170008', '/queue/message', b'0', '2022-03-21 09:29:30');
INSERT INTO `ws_data` VALUES (20398, 'BUSINESS', '66f9332c-58a8-4053-9711-baf21cc23b06', 'admin', b'1', '测试点对点数据，持久化1647826170013', '/queue/message', b'0', '2022-03-21 09:29:30');
INSERT INTO `ws_data` VALUES (20399, 'BUSINESS', '1d649250-653e-4721-a6af-42b7cba2edf7', 'admin', b'1', '测试点对点数据，持久化1647826170018', '/queue/message', b'0', '2022-03-21 09:29:30');
INSERT INTO `ws_data` VALUES (20400, 'BUSINESS', '3d7cc97c-bbe8-4b89-acbf-e7613275172a', 'admin', b'1', '测试点对点数据，持久化1647826170028', '/queue/message', b'0', '2022-03-21 09:29:30');
INSERT INTO `ws_data` VALUES (20401, 'BUSINESS', '424d126d-ec35-4702-9825-fd2a97a307fc', 'admin', b'1', '测试点对点数据，持久化1647826200010', '/queue/message', b'0', '2022-03-21 09:30:00');
INSERT INTO `ws_data` VALUES (20402, 'BUSINESS', '2270362d-f751-4695-b51a-6697d2802b2b', 'admin', b'1', '测试点对点数据，持久化1647826200012', '/queue/message', b'0', '2022-03-21 09:30:00');
INSERT INTO `ws_data` VALUES (20403, 'BUSINESS', 'fcfa6686-1b5e-4c8b-bc31-b55e82b55db3', 'admin', b'1', '测试点对点数据，持久化1647826200011', '/queue/message', b'0', '2022-03-21 09:30:00');
INSERT INTO `ws_data` VALUES (20404, 'BUSINESS', '16d7a58c-539e-4781-9da2-f1264f14edd1', 'admin', b'1', '测试点对点数据，持久化1647826200031', '/queue/message', b'0', '2022-03-21 09:30:00');
INSERT INTO `ws_data` VALUES (20405, 'BUSINESS', '1762699b-5a43-4076-af6d-17ce6dca9dca', 'admin', b'1', '测试点对点数据，持久化1647826230016', '/queue/message', b'0', '2022-03-21 09:30:30');
INSERT INTO `ws_data` VALUES (20406, 'BUSINESS', '1ff026f1-c1dc-4522-a40a-cb2627c99ffd', 'admin', b'1', '测试点对点数据，持久化1647826230011', '/queue/message', b'0', '2022-03-21 09:30:30');
INSERT INTO `ws_data` VALUES (20407, 'BUSINESS', '4740b47c-49e7-490d-96f1-5ddc3b13cb34', 'admin', b'1', '测试点对点数据，持久化1647826230012', '/queue/message', b'0', '2022-03-21 09:30:30');
INSERT INTO `ws_data` VALUES (20408, 'BUSINESS', '360a7bfc-caf0-4683-ad86-971ee4324e36', 'admin', b'1', '测试点对点数据，持久化1647826230017', '/queue/message', b'0', '2022-03-21 09:30:30');
INSERT INTO `ws_data` VALUES (20409, 'BUSINESS', 'f702e2cf-091d-44d6-99fc-b55b3ddb7cc2', 'admin', b'1', '测试点对点数据，持久化1647826260007', '/queue/message', b'0', '2022-03-21 09:31:00');
INSERT INTO `ws_data` VALUES (20410, 'BUSINESS', '047945b2-a91a-4a5b-bcfa-31ebc5c22252', 'admin', b'1', '测试点对点数据，持久化1647826260011', '/queue/message', b'0', '2022-03-21 09:31:00');
INSERT INTO `ws_data` VALUES (20411, 'BUSINESS', '5c7719d6-b890-4057-86e2-9da17d9b566b', 'admin', b'1', '测试点对点数据，持久化1647826260015', '/queue/message', b'0', '2022-03-21 09:31:00');
INSERT INTO `ws_data` VALUES (20412, 'BUSINESS', 'a5ee2070-a3b3-4d1c-a287-39a1ebb5f191', 'admin', b'1', '测试点对点数据，持久化1647826260026', '/queue/message', b'0', '2022-03-21 09:31:00');
INSERT INTO `ws_data` VALUES (20413, 'BUSINESS', 'cc8c3097-9b62-49db-bae8-c311b3651d51', 'admin', b'1', '测试点对点数据，持久化1647826290018', '/queue/message', b'0', '2022-03-21 09:31:30');
INSERT INTO `ws_data` VALUES (20414, 'BUSINESS', '64ff4f5b-c720-4dd3-8d68-e944d7025cd0', 'admin', b'1', '测试点对点数据，持久化1647826290014', '/queue/message', b'0', '2022-03-21 09:31:30');
INSERT INTO `ws_data` VALUES (20415, 'BUSINESS', '96a9b296-ec98-4225-8720-ce114b57b7e3', 'admin', b'1', '测试点对点数据，持久化1647826290016', '/queue/message', b'0', '2022-03-21 09:31:30');
INSERT INTO `ws_data` VALUES (20416, 'BUSINESS', 'a55409fa-6d63-4f02-9025-040f7510d88a', 'admin', b'1', '测试点对点数据，持久化1647826290022', '/queue/message', b'0', '2022-03-21 09:31:30');
INSERT INTO `ws_data` VALUES (20417, 'BUSINESS', 'fcb2eff6-fa06-4782-ade6-b0af15a891cd', 'admin', b'1', '测试点对点数据，持久化1647826320011', '/queue/message', b'0', '2022-03-21 09:32:00');
INSERT INTO `ws_data` VALUES (20418, 'BUSINESS', 'b2466a3b-d894-4263-a94c-781362ee9b37', 'admin', b'1', '测试点对点数据，持久化1647826320010', '/queue/message', b'0', '2022-03-21 09:32:00');
INSERT INTO `ws_data` VALUES (20419, 'BUSINESS', '4d3496a7-57c5-4a8f-b117-d4125845062d', 'admin', b'1', '测试点对点数据，持久化1647826320020', '/queue/message', b'0', '2022-03-21 09:32:00');
INSERT INTO `ws_data` VALUES (20420, 'BUSINESS', 'ae2d0fa8-c9d8-455d-9fce-f1ee0a934a75', 'admin', b'1', '测试点对点数据，持久化1647826320032', '/queue/message', b'0', '2022-03-21 09:32:00');
INSERT INTO `ws_data` VALUES (20421, 'BUSINESS', '6ce1d278-9c74-43dc-a597-9667a554ea07', 'admin', b'1', '测试点对点数据，持久化1647826350008', '/queue/message', b'0', '2022-03-21 09:32:30');
INSERT INTO `ws_data` VALUES (20422, 'BUSINESS', '219e4961-b2f9-486f-926e-a2b37271d414', 'admin', b'1', '测试点对点数据，持久化1647826350008', '/queue/message', b'0', '2022-03-21 09:32:30');
INSERT INTO `ws_data` VALUES (20423, 'BUSINESS', 'b47bc112-33f5-4fce-94cd-5ae6fca1662e', 'admin', b'1', '测试点对点数据，持久化1647826350011', '/queue/message', b'0', '2022-03-21 09:32:30');
INSERT INTO `ws_data` VALUES (20424, 'BUSINESS', '8e0ecc03-aea1-422d-b204-68d28d8f29c8', 'admin', b'1', '测试点对点数据，持久化1647826350031', '/queue/message', b'0', '2022-03-21 09:32:30');
INSERT INTO `ws_data` VALUES (20425, 'BUSINESS', '174977b4-d31d-40af-8f3c-79dc6cd1ed72', 'admin', b'1', '测试点对点数据，持久化1647826380013', '/queue/message', b'0', '2022-03-21 09:33:00');
INSERT INTO `ws_data` VALUES (20426, 'BUSINESS', '2563e01e-419c-47c6-86d7-f140c0a27ac4', 'admin', b'1', '测试点对点数据，持久化1647826380022', '/queue/message', b'0', '2022-03-21 09:33:00');
INSERT INTO `ws_data` VALUES (20427, 'BUSINESS', 'ac514110-18b1-4e58-b263-36fc51c4b3df', 'admin', b'1', '测试点对点数据，持久化1647826380020', '/queue/message', b'0', '2022-03-21 09:33:00');
INSERT INTO `ws_data` VALUES (20428, 'BUSINESS', '10c61820-c2f8-4138-a4da-abbee0e7de13', 'admin', b'1', '测试点对点数据，持久化1647826380017', '/queue/message', b'0', '2022-03-21 09:33:00');
INSERT INTO `ws_data` VALUES (20429, 'BUSINESS', '19775007-7cd8-44e1-8d12-643966c364b1', 'admin', b'1', '测试点对点数据，持久化1647826410009', '/queue/message', b'0', '2022-03-21 09:33:30');
INSERT INTO `ws_data` VALUES (20430, 'BUSINESS', '77b24393-1b82-47a5-af20-8ed22bfeb58d', 'admin', b'1', '测试点对点数据，持久化1647826410011', '/queue/message', b'0', '2022-03-21 09:33:30');
INSERT INTO `ws_data` VALUES (20431, 'BUSINESS', 'bc51391e-c65d-4fe9-85aa-e70d89ef0244', 'admin', b'1', '测试点对点数据，持久化1647826410016', '/queue/message', b'0', '2022-03-21 09:33:30');
INSERT INTO `ws_data` VALUES (20432, 'BUSINESS', '775d1f52-2418-48fd-a96d-66d95228efc1', 'admin', b'1', '测试点对点数据，持久化1647826440010', '/queue/message', b'0', '2022-03-21 09:34:00');
INSERT INTO `ws_data` VALUES (20433, 'BUSINESS', '417087a8-5891-4824-ad3b-797f4ef51094', 'admin', b'1', '测试点对点数据，持久化1647826440021', '/queue/message', b'0', '2022-03-21 09:34:00');
INSERT INTO `ws_data` VALUES (20434, 'BUSINESS', 'a6dfa7a5-9990-47aa-bd88-dbf1880e3adc', 'admin', b'1', '测试点对点数据，持久化1647826440020', '/queue/message', b'0', '2022-03-21 09:34:00');
INSERT INTO `ws_data` VALUES (20435, 'BUSINESS', 'de14cb33-d95a-4585-9b6d-2ddcf8f22b8e', 'admin', b'1', '测试点对点数据，持久化1647826440533', '/queue/message', b'0', '2022-03-21 09:34:09');
INSERT INTO `ws_data` VALUES (20436, 'BUSINESS', 'cd7afa64-21b1-4938-b9fb-28df9a30b129', 'admin', b'1', '测试点对点数据，持久化1647826470011', '/queue/message', b'0', '2022-03-21 09:34:30');
INSERT INTO `ws_data` VALUES (20437, 'BUSINESS', '556a400a-9318-42aa-a315-175d2c97ad9e', 'admin', b'1', '测试点对点数据，持久化1647826470016', '/queue/message', b'0', '2022-03-21 09:34:30');
INSERT INTO `ws_data` VALUES (20438, 'BUSINESS', '13f74d14-f2a2-4bd7-8572-2f5c69f72837', 'admin', b'1', '测试点对点数据，持久化1647826470028', '/queue/message', b'0', '2022-03-21 09:34:30');
INSERT INTO `ws_data` VALUES (20439, 'BUSINESS', 'a39efbf5-3f2e-4004-9507-318efc63501a', 'admin', b'1', '测试点对点数据，持久化1647826470034', '/queue/message', b'0', '2022-03-21 09:34:30');
INSERT INTO `ws_data` VALUES (20440, 'BUSINESS', 'd863ee3a-e0e1-4064-95c1-7997cdbb279a', 'admin', b'1', '测试点对点数据，持久化1647826500016', '/queue/message', b'0', '2022-03-21 09:35:00');
INSERT INTO `ws_data` VALUES (20441, 'BUSINESS', '3b82e5d3-561b-4e6f-80dd-7f3924ae9a22', 'admin', b'1', '测试点对点数据，持久化1647826500017', '/queue/message', b'0', '2022-03-21 09:35:00');
INSERT INTO `ws_data` VALUES (20442, 'BUSINESS', '9a67f796-ae3a-45a7-aa41-e3710948df4b', 'admin', b'1', '测试点对点数据，持久化1647826500019', '/queue/message', b'0', '2022-03-21 09:35:00');
INSERT INTO `ws_data` VALUES (20443, 'BUSINESS', '927abf03-7be9-47ca-abbb-f00827f58b51', 'admin', b'1', '测试点对点数据，持久化1647826500033', '/queue/message', b'0', '2022-03-21 09:35:00');
INSERT INTO `ws_data` VALUES (20444, 'BUSINESS', '3be319d0-2e4f-4063-b04f-8fdfb3cca546', 'admin', b'1', '测试点对点数据，持久化1647826530008', '/queue/message', b'0', '2022-03-21 09:35:30');
INSERT INTO `ws_data` VALUES (20445, 'BUSINESS', 'f5804a58-7642-4b9d-98f2-bc9e18101b19', 'admin', b'1', '测试点对点数据，持久化1647826530013', '/queue/message', b'0', '2022-03-21 09:35:30');
INSERT INTO `ws_data` VALUES (20446, 'BUSINESS', '6e5cd972-e9c4-4698-a37d-c04701f83169', 'admin', b'1', '测试点对点数据，持久化1647826530018', '/queue/message', b'0', '2022-03-21 09:35:30');
INSERT INTO `ws_data` VALUES (20447, 'BUSINESS', 'c0477bee-b225-49b6-9475-8c261106fea6', 'admin', b'1', '测试点对点数据，持久化1647826530010', '/queue/message', b'0', '2022-03-21 09:35:30');
INSERT INTO `ws_data` VALUES (20448, 'BUSINESS', '4fff12cb-cf3a-4331-9d72-1664bf72be2f', 'admin', b'1', '测试点对点数据，持久化1647826560018', '/queue/message', b'0', '2022-03-21 09:36:00');
INSERT INTO `ws_data` VALUES (20449, 'BUSINESS', '0f763a36-222d-4f91-b003-fae136ec5f10', 'admin', b'1', '测试点对点数据，持久化1647826560013', '/queue/message', b'0', '2022-03-21 09:36:00');
INSERT INTO `ws_data` VALUES (20450, 'BUSINESS', '716d12d0-7724-48e1-91c2-42e7f2e6a5e6', 'admin', b'1', '测试点对点数据，持久化1647826560018', '/queue/message', b'0', '2022-03-21 09:36:00');
INSERT INTO `ws_data` VALUES (20451, 'BUSINESS', 'd8310af1-b131-4f79-af28-b939a30e5ecb', 'admin', b'1', '测试点对点数据，持久化1647826560031', '/queue/message', b'0', '2022-03-21 09:36:00');
INSERT INTO `ws_data` VALUES (20452, 'BUSINESS', '62750980-5c4b-4c19-a66f-1ed82fca5c1b', 'admin', b'1', '测试点对点数据，持久化1647826590009', '/queue/message', b'0', '2022-03-21 09:36:30');
INSERT INTO `ws_data` VALUES (20453, 'BUSINESS', 'd111cf84-22ae-41bd-8264-07618dd79507', 'admin', b'1', '测试点对点数据，持久化1647826590012', '/queue/message', b'0', '2022-03-21 09:36:30');
INSERT INTO `ws_data` VALUES (20454, 'BUSINESS', '2f63fea8-3535-4054-a99e-74cde9a5213a', 'admin', b'1', '测试点对点数据，持久化1647826590014', '/queue/message', b'0', '2022-03-21 09:36:30');
INSERT INTO `ws_data` VALUES (20455, 'BUSINESS', '5552bb9a-ff1f-4ae4-82f5-5616c15b2a4a', 'admin', b'1', '测试点对点数据，持久化1647826620011', '/queue/message', b'0', '2022-03-21 09:37:00');
INSERT INTO `ws_data` VALUES (20456, 'BUSINESS', '7dfaaef1-3385-4a94-bb17-6129e7af2ec0', 'admin', b'1', '测试点对点数据，持久化1647826620013', '/queue/message', b'0', '2022-03-21 09:37:00');
INSERT INTO `ws_data` VALUES (20457, 'BUSINESS', 'ad876aa4-0938-45df-acc9-a77d3a17109f', 'admin', b'1', '测试点对点数据，持久化1647826620019', '/queue/message', b'0', '2022-03-21 09:37:00');
INSERT INTO `ws_data` VALUES (20458, 'BUSINESS', '86c17b88-820c-477a-a270-9067c7cfbcd1', 'admin', b'1', '测试点对点数据，持久化1647826624915', '/queue/message', b'0', '2022-03-21 09:37:04');
INSERT INTO `ws_data` VALUES (20459, 'BUSINESS', 'ba61e323-529f-4a67-ac9c-78f97c69b9ab', 'admin', b'1', '测试点对点数据，持久化1647826650007', '/queue/message', b'0', '2022-03-21 09:37:30');
INSERT INTO `ws_data` VALUES (20460, 'BUSINESS', '920c3b2e-5dfd-4a01-b3a1-d11b46f45bc8', 'admin', b'1', '测试点对点数据，持久化1647826650012', '/queue/message', b'0', '2022-03-21 09:37:30');
INSERT INTO `ws_data` VALUES (20461, 'BUSINESS', 'c41e3470-78ba-434e-a00f-92aca56beddf', 'admin', b'1', '测试点对点数据，持久化1647826650014', '/queue/message', b'0', '2022-03-21 09:37:30');
INSERT INTO `ws_data` VALUES (20462, 'BUSINESS', '216067f3-ffda-4885-9759-d8bcd6ea40e4', 'admin', b'1', '测试点对点数据，持久化1647826680008', '/queue/message', b'0', '2022-03-21 09:38:00');
INSERT INTO `ws_data` VALUES (20463, 'BUSINESS', '0e7f12b0-b37a-454d-9892-845e5293a884', 'admin', b'1', '测试点对点数据，持久化1647826680013', '/queue/message', b'0', '2022-03-21 09:38:00');
INSERT INTO `ws_data` VALUES (20464, 'BUSINESS', '564f9d27-1712-419d-8b36-235383897436', 'admin', b'1', '测试点对点数据，持久化1647826680024', '/queue/message', b'0', '2022-03-21 09:38:00');
INSERT INTO `ws_data` VALUES (20465, 'BUSINESS', 'da54a200-a470-4ebb-83d5-40de21569260', 'admin', b'1', '测试点对点数据，持久化1647826652143', '/queue/message', b'0', '2022-03-21 09:38:20');
INSERT INTO `ws_data` VALUES (20466, 'BUSINESS', '9859e61b-8ef4-45f2-81b9-c5b974f041ce', 'admin', b'1', '测试点对点数据，持久化1647826710012', '/queue/message', b'0', '2022-03-21 09:38:30');
INSERT INTO `ws_data` VALUES (20467, 'BUSINESS', '17a2f578-d1f7-483d-a191-d5246c8893c8', 'admin', b'1', '测试点对点数据，持久化1647826710020', '/queue/message', b'0', '2022-03-21 09:38:30');
INSERT INTO `ws_data` VALUES (20468, 'BUSINESS', 'd5b5d764-803b-4e88-a315-6c41280dde1e', 'admin', b'1', '测试点对点数据，持久化1647826710030', '/queue/message', b'0', '2022-03-21 09:38:30');
INSERT INTO `ws_data` VALUES (20469, 'BUSINESS', 'e200a94a-2818-41d7-ae5e-b86635aaa4c1', 'admin', b'1', '测试点对点数据，持久化1647826740009', '/queue/message', b'0', '2022-03-21 09:39:00');
INSERT INTO `ws_data` VALUES (20470, 'BUSINESS', '8de5d079-a9ab-4772-bba6-5b37942ff505', 'admin', b'1', '测试点对点数据，持久化1647826740017', '/queue/message', b'0', '2022-03-21 09:39:00');
INSERT INTO `ws_data` VALUES (20471, 'BUSINESS', 'd8678e0c-addc-4a95-be97-c56c031daa14', 'admin', b'1', '测试点对点数据，持久化1647826740017', '/queue/message', b'0', '2022-03-21 09:39:00');
INSERT INTO `ws_data` VALUES (20472, 'BUSINESS', 'c916694d-4e95-4754-9981-4e9cc9146d13', 'admin', b'1', '测试点对点数据，持久化1647826740029', '/queue/message', b'0', '2022-03-21 09:39:00');
INSERT INTO `ws_data` VALUES (20473, 'BUSINESS', '0e9b208b-f9cd-4b97-81b9-375f32e5a0ae', 'admin', b'1', '测试点对点数据，持久化1647826770007', '/queue/message', b'0', '2022-03-21 09:39:30');
INSERT INTO `ws_data` VALUES (20474, 'BUSINESS', '0d061531-3839-4eed-812f-07aa88faefb0', 'admin', b'1', '测试点对点数据，持久化1647826770011', '/queue/message', b'0', '2022-03-21 09:39:30');
INSERT INTO `ws_data` VALUES (20475, 'BUSINESS', 'de36bf22-ec04-4a21-8214-1c9a5f2f8e81', 'admin', b'1', '测试点对点数据，持久化1647826770010', '/queue/message', b'0', '2022-03-21 09:39:30');
INSERT INTO `ws_data` VALUES (20476, 'BUSINESS', 'a1ae54c0-d01f-452d-ac88-837f6daa7e3b', 'admin', b'1', '测试点对点数据，持久化1647826770024', '/queue/message', b'0', '2022-03-21 09:39:30');
INSERT INTO `ws_data` VALUES (20477, 'BUSINESS', 'efa0c5f0-b7b2-48b4-936d-8c6babfb43e5', 'admin', b'1', '测试点对点数据，持久化1647826800025', '/queue/message', b'0', '2022-03-21 09:40:00');
INSERT INTO `ws_data` VALUES (20478, 'BUSINESS', '6039a092-019c-4940-8f7f-051011d859f7', 'admin', b'1', '测试点对点数据，持久化1647826800022', '/queue/message', b'0', '2022-03-21 09:40:00');
INSERT INTO `ws_data` VALUES (20479, 'BUSINESS', '5393d286-bb95-4af3-84c0-ab85b047adab', 'admin', b'1', '测试点对点数据，持久化1647826800021', '/queue/message', b'0', '2022-03-21 09:40:00');
INSERT INTO `ws_data` VALUES (20480, 'BUSINESS', '7e66b116-5a1a-481d-b3d3-976ea7490d72', 'admin', b'1', '测试点对点数据，持久化1647826800029', '/queue/message', b'0', '2022-03-21 09:40:00');
INSERT INTO `ws_data` VALUES (20481, 'BUSINESS', 'c6052007-e64f-494a-a56d-7ed04e10814b', 'admin', b'1', '测试点对点数据，持久化1647826830010', '/queue/message', b'0', '2022-03-21 09:40:30');
INSERT INTO `ws_data` VALUES (20482, 'BUSINESS', 'a320140d-de94-412c-af72-d746b6fd3dea', 'admin', b'1', '测试点对点数据，持久化1647826830023', '/queue/message', b'0', '2022-03-21 09:40:30');
INSERT INTO `ws_data` VALUES (20483, 'BUSINESS', '4a595ccd-bb16-4c7b-8077-36d07f41d813', 'admin', b'1', '测试点对点数据，持久化1647826830013', '/queue/message', b'0', '2022-03-21 09:40:30');
INSERT INTO `ws_data` VALUES (20484, 'BUSINESS', '130e8901-18c4-4a39-a3ce-8b2eb284c8bb', 'admin', b'1', '测试点对点数据，持久化1647826830020', '/queue/message', b'0', '2022-03-21 09:40:30');
INSERT INTO `ws_data` VALUES (20485, 'BUSINESS', 'c4dc429f-ff1a-4f0a-a508-44456b778802', 'admin', b'1', '测试点对点数据，持久化1647826860010', '/queue/message', b'0', '2022-03-21 09:41:00');
INSERT INTO `ws_data` VALUES (20486, 'BUSINESS', '3b6824da-8b5d-429f-9f29-b846f684d444', 'admin', b'1', '测试点对点数据，持久化1647826860018', '/queue/message', b'0', '2022-03-21 09:41:00');
INSERT INTO `ws_data` VALUES (20487, 'BUSINESS', '8333d8d2-a0c9-4493-a2ca-63d1d9e56f25', 'admin', b'1', '测试点对点数据，持久化1647826860018', '/queue/message', b'0', '2022-03-21 09:41:00');
INSERT INTO `ws_data` VALUES (20488, 'BUSINESS', 'ebe90ac1-950c-43b4-a3b7-56175cbf8305', 'admin', b'1', '测试点对点数据，持久化1647826860022', '/queue/message', b'0', '2022-03-21 09:41:00');
INSERT INTO `ws_data` VALUES (20489, 'BUSINESS', 'c9d2264c-1e79-4718-9cd6-d69d040dcd17', 'admin', b'1', '测试点对点数据，持久化1647826890040', '/queue/message', b'0', '2022-03-21 09:41:30');
INSERT INTO `ws_data` VALUES (20490, 'BUSINESS', '7d4cae9e-2150-4035-b875-2e6519f2341d', 'admin', b'1', '测试点对点数据，持久化1647826890049', '/queue/message', b'0', '2022-03-21 09:41:30');
INSERT INTO `ws_data` VALUES (20491, 'BUSINESS', 'c2a7d7bf-a2fd-4688-8a93-1c5e3435fa8c', 'admin', b'1', '测试点对点数据，持久化1647826890046', '/queue/message', b'0', '2022-03-21 09:41:30');
INSERT INTO `ws_data` VALUES (20492, 'BUSINESS', 'bdff63d3-b967-45f7-9da8-bf47c28cd3ed', 'admin', b'1', '测试点对点数据，持久化1647826890047', '/queue/message', b'0', '2022-03-21 09:41:30');
INSERT INTO `ws_data` VALUES (20493, 'BUSINESS', '041845d6-9f79-44d4-9d03-24295c868091', 'admin', b'1', '测试点对点数据，持久化1647826920021', '/queue/message', b'0', '2022-03-21 09:42:00');
INSERT INTO `ws_data` VALUES (20494, 'BUSINESS', 'a4f99c63-ed4e-4551-86cc-15b731a36e53', 'admin', b'1', '测试点对点数据，持久化1647826920017', '/queue/message', b'0', '2022-03-21 09:42:00');
INSERT INTO `ws_data` VALUES (20495, 'BUSINESS', 'fc885c79-971e-4acd-abde-80864c38223b', 'admin', b'1', '测试点对点数据，持久化1647826920016', '/queue/message', b'0', '2022-03-21 09:42:00');
INSERT INTO `ws_data` VALUES (20496, 'BUSINESS', '6203a837-4b5f-4284-818a-cfccd913dc8b', 'admin', b'1', '测试点对点数据，持久化1647826920034', '/queue/message', b'0', '2022-03-21 09:42:00');
INSERT INTO `ws_data` VALUES (20497, 'BUSINESS', 'f4736ee9-001f-4297-9430-e020f16f8e5b', 'admin', b'1', '测试点对点数据，持久化1647826950016', '/queue/message', b'0', '2022-03-21 09:42:30');
INSERT INTO `ws_data` VALUES (20498, 'BUSINESS', 'bac72229-a058-4d18-ad94-44a8e10a130e', 'admin', b'1', '测试点对点数据，持久化1647826950011', '/queue/message', b'0', '2022-03-21 09:42:30');
INSERT INTO `ws_data` VALUES (20499, 'BUSINESS', 'f2217c04-b1f6-443f-90a9-c519a49fe960', 'admin', b'1', '测试点对点数据，持久化1647826950016', '/queue/message', b'0', '2022-03-21 09:42:30');
INSERT INTO `ws_data` VALUES (20500, 'BUSINESS', 'a0ec3986-34be-4d78-a3ad-69d1ac8af9ce', 'admin', b'1', '测试点对点数据，持久化1647826950017', '/queue/message', b'0', '2022-03-21 09:42:30');
INSERT INTO `ws_data` VALUES (20501, 'BUSINESS', '48304eef-7e17-4684-8503-25fc92550582', 'admin', b'1', '测试点对点数据，持久化1647826980013', '/queue/message', b'0', '2022-03-21 09:43:00');
INSERT INTO `ws_data` VALUES (20502, 'BUSINESS', '43a2abd7-3efc-4352-b4e8-fc1d440f533a', 'admin', b'1', '测试点对点数据，持久化1647826980019', '/queue/message', b'0', '2022-03-21 09:43:00');
INSERT INTO `ws_data` VALUES (20503, 'BUSINESS', '3c7f18a2-39bf-473b-9c38-4aebfc720501', 'admin', b'1', '测试点对点数据，持久化1647826980019', '/queue/message', b'0', '2022-03-21 09:43:00');
INSERT INTO `ws_data` VALUES (20504, 'BUSINESS', 'a3ef3071-e7b0-430e-951d-0c94c63aa610', 'admin', b'1', '测试点对点数据，持久化1647826980113', '/queue/message', b'0', '2022-03-21 09:43:00');
INSERT INTO `ws_data` VALUES (20505, 'BUSINESS', '2aa4ef46-3edf-4f60-a8b5-a73b8b304e52', 'admin', b'1', '测试点对点数据，持久化1647827010010', '/queue/message', b'0', '2022-03-21 09:43:30');
INSERT INTO `ws_data` VALUES (20506, 'BUSINESS', '0a12f73d-e3e9-42be-82f0-3b1b936c5c79', 'admin', b'1', '测试点对点数据，持久化1647827010010', '/queue/message', b'0', '2022-03-21 09:43:30');
INSERT INTO `ws_data` VALUES (20507, 'BUSINESS', '359d5afd-bf10-4443-916d-5f86ae03ad2b', 'admin', b'1', '测试点对点数据，持久化1647827010014', '/queue/message', b'0', '2022-03-21 09:43:30');
INSERT INTO `ws_data` VALUES (20508, 'BUSINESS', 'ea5fab36-c88f-4916-837d-4a42cb75fda3', 'admin', b'1', '测试点对点数据，持久化1647827010021', '/queue/message', b'0', '2022-03-21 09:43:30');
INSERT INTO `ws_data` VALUES (20509, 'BUSINESS', 'a6c021b2-81eb-4d0c-be50-388e590641c9', 'admin', b'1', '测试点对点数据，持久化1647827040017', '/queue/message', b'0', '2022-03-21 09:44:00');
INSERT INTO `ws_data` VALUES (20510, 'BUSINESS', '93d5b5c5-2a93-499b-ab10-0041789f5e90', 'admin', b'1', '测试点对点数据，持久化1647827040012', '/queue/message', b'0', '2022-03-21 09:44:00');
INSERT INTO `ws_data` VALUES (20511, 'BUSINESS', '51c94416-da7a-4bcb-80f3-5453f4bc68e4', 'admin', b'1', '测试点对点数据，持久化1647827040010', '/queue/message', b'0', '2022-03-21 09:44:00');
INSERT INTO `ws_data` VALUES (20512, 'BUSINESS', '1d8fb972-a986-4e6e-b358-2e703660447f', 'admin', b'1', '测试点对点数据，持久化1647827040063', '/queue/message', b'0', '2022-03-21 09:44:00');
INSERT INTO `ws_data` VALUES (20513, 'BUSINESS', '18cb1732-4c62-47ea-ab7a-e2260f789f59', 'admin', b'1', '测试点对点数据，持久化1647827070007', '/queue/message', b'0', '2022-03-21 09:44:30');
INSERT INTO `ws_data` VALUES (20514, 'BUSINESS', 'e8a048c3-0077-453d-aa1a-5fcc6d2d082d', 'admin', b'1', '测试点对点数据，持久化1647827070009', '/queue/message', b'0', '2022-03-21 09:44:30');

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
) ENGINE = InnoDB AUTO_INCREMENT = 11112 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

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
