@@ -1,48 +0,0 @@
/*
 Navicat Premium Data Transfer

 Source Server         : MySQL
 Source Server Type    : MySQL
 Source Server Version : 80023
 Source Host           : localhost:3306
 Source Schema         : db_example

 Target Server Type    : MySQL
 Target Server Version : 80023
 File Encoding         : 65001

 Date: 15/04/2021 17:29:52
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for fruit
-- ----------------------------
DROP TABLE IF EXISTS `fruit`;
CREATE TABLE `fruit`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `sale` int NULL DEFAULT NULL,
  `icon` varchar(300) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 16 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of fruit
-- ----------------------------
INSERT INTO `fruit` VALUES (3, '橘子', 299, 'https://img0.baidu.com/it/u=1014450101,331943515&fm=26&fmt=auto&gp=0.jpg');
INSERT INTO `fruit` VALUES (4, '葡萄', 555, 'https://img0.baidu.com/it/u=1014450101,331943515&fm=26&fmt=auto&gp=0.jpg');
INSERT INTO `fruit` VALUES (5, '葡萄', 555, 'https://img0.baidu.com/it/u=1014450101,331943515&fm=26&fmt=auto&gp=0.jpg');
INSERT INTO `fruit` VALUES (6, '火龙果', 133, 'http://pic30.nipic.com/20130605/5149126_102522221139_2.jpg');
INSERT INTO `fruit` VALUES (7, '葡萄', 555, 'https://img0.baidu.com/it/u=1014450101,331943515&fm=26&fmt=auto&gp=0.jpg');
INSERT INTO `fruit` VALUES (8, '葡萄', 555, 'https://img0.baidu.com/it/u=1014450101,331943515&fm=26&fmt=auto&gp=0.jpg');
INSERT INTO `fruit` VALUES (9, '葡萄', 555, 'https://img0.baidu.com/it/u=1014450101,331943515&fm=26&fmt=auto&gp=0.jpg');
INSERT INTO `fruit` VALUES (10, '葡萄', 555, 'https://img0.baidu.com/it/u=1014450101,331943515&fm=26&fmt=auto&gp=0.jpg');
INSERT INTO `fruit` VALUES (11, '葡萄', 555, 'https://img0.baidu.com/it/u=1014450101,331943515&fm=26&fmt=auto&gp=0.jpg');
INSERT INTO `fruit` VALUES (12, '葡萄', 555, 'https://img0.baidu.com/it/u=1014450101,331943515&fm=26&fmt=auto&gp=0.jpg');
INSERT INTO `fruit` VALUES (15, '芒果', 222, 'https://img.zcool.cn/community/01cf305af39f4ea801216045607729.png@1280w_1l_2o_100sh.png');
INSERT INTO `fruit` VALUES (16, '狗宝宝儿', 123, 'https://www.shianvip.com/wp-content/uploads/2020/12/1607880914.jpg');

SET FOREIGN_KEY_CHECKS = 1;