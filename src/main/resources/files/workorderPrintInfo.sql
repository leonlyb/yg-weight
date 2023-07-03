/*
 Navicat Premium Data Transfer

 Source Server         : 202.196.192.225
 Source Server Type    : MySQL
 Source Server Version : 80027
 Source Host           : 202.196.192.225:3306
 Source Schema         : ulhf_weighbridg

 Target Server Type    : MySQL
 Target Server Version : 80027
 File Encoding         : 65001

 Date: 03/07/2023 15:43:30
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for workorderPrintInfo
-- ----------------------------
DROP TABLE IF EXISTS `workorderPrintInfo`;
CREATE TABLE `workorderPrintInfo`  (
  `id` int NOT NULL COMMENT '打印工单日志id',
  `woKy` int NULL DEFAULT NULL COMMENT '工单Id',
  `imgPath` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '预览图片路径',
  `filePath` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '打印文件路径 pdf 或者 excel',
  `printTime` datetime NULL DEFAULT NULL COMMENT '打印时间',
  `printCount` int NULL DEFAULT NULL COMMENT '打印次数',
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of workorderPrintInfo
-- ----------------------------

SET FOREIGN_KEY_CHECKS = 1;
