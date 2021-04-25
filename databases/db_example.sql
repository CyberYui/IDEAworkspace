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

 Date: 25/04/2021 17:23:01
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

-- ----------------------------
-- Table structure for hibernate_sequence
-- ----------------------------
DROP TABLE IF EXISTS `hibernate_sequence`;
CREATE TABLE `hibernate_sequence`  (
  `next_val` bigint NULL DEFAULT NULL
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of hibernate_sequence
-- ----------------------------
INSERT INTO `hibernate_sequence` VALUES (3);

-- ----------------------------
-- Table structure for qrcodedb
-- ----------------------------
DROP TABLE IF EXISTS `qrcodedb`;
CREATE TABLE `qrcodedb`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `title` varchar(150) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `major` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `content` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL,
  `image` varchar(400) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `videos` varchar(400) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `address` varchar(400) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of qrcodedb
-- ----------------------------
INSERT INTO `qrcodedb` VALUES (1, 'first', 'doing', '![image](https://uicdn.toast.com/toastui/img/tui-editor-bi.png)\r\n\r\n# Awesome Editor!\r\n\r\nIt has been _released as opensource in 2018_ and has ~~continually~~ evolved to **receive 10k GitHub ⭐️ Stars**.\r\n\r\n## Create Instance\r\n\r\nYou can create an instance with the following code and use `getHtml()` and `getMarkdown()` of the [Editor](https://github.com/nhn/tui.editor).\r\n\r\n```js\r\nconst editor = new Editor(options);\r\n```\r\n\r\n> See the table below for default options\r\n> > More API information can be found in the document\r\n\r\n| name | type | description |\r\n| --- | --- | --- |\r\n| el | `HTMLElement` | container element |\r\n\r\n## Features\r\n\r\n* CommonMark + GFM Specifications\r\n   * Live Preview\r\n   * Scroll Sync\r\n   * Auto Indent\r\n   * Syntax Highlight\r\n        1. Markdown\r\n        2. Preview\r\n\r\n## Support Wrappers\r\n\r\n> * Wrappers\r\n>    1. [x] React\r\n>    2. [x] Vue\r\n>    3. [ ] Ember', 'https://images.pexels.com/photos/2982889/pexels-photo-2982889.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=150', 'https://images.pexels.com/photos/2982889/pexels-photo-2982889.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=150', 'https://images.pexels.com/photos/2982889/pexels-photo-2982889.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=150');
INSERT INTO `qrcodedb` VALUES (2, 'second', 'finish', '![image](https://uicdn.toast.com/toastui/img/tui-editor-bi.png)\r\n\r\n# Awesome Editor!\r\n\r\nIt has been _released as opensource in 2018_ and has ~~continually~~ evolved to **receive 10k GitHub ⭐️ Stars**.\r\n\r\n## Create Instance\r\n\r\nYou can create an instance with the following code and use `getHtml()` and `getMarkdown()` of the [Editor](https://github.com/nhn/tui.editor).\r\n\r\n```js\r\nconst editor = new Editor(options);\r\n```\r\n\r\n> See the table below for default options\r\n> > More API information can be found in the document\r\n\r\n| name | type | description |\r\n| --- | --- | --- |\r\n| el | `HTMLElement` | container element |\r\n\r\n## Features\r\n\r\n* CommonMark + GFM Specifications\r\n   * Live Preview\r\n   * Scroll Sync\r\n   * Auto Indent\r\n   * Syntax Highlight\r\n        1. Markdown\r\n        2. Preview\r\n\r\n## Support Wrappers\r\n\r\n> * Wrappers\r\n>    1. [x] React\r\n>    2. [x] Vue\r\n>    3. [ ] Ember', 'https://images.pexels.com/photos/2982889/pexels-photo-2982889.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=150', 'https://images.pexels.com/photos/2982889/pexels-photo-2982889.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=150', 'https://images.pexels.com/photos/2982889/pexels-photo-2982889.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=150');
INSERT INTO `qrcodedb` VALUES (3, 'third', 'killing', 'here are some of the simple contexts', 'https://images.pexels.com/photos/220512/pexels-photo-220512.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=150', 'https://images.pexels.com/photos/220512/pexels-photo-220512.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=150', 'https://images.pexels.com/photos/220512/pexels-photo-220512.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=150');
INSERT INTO `qrcodedb` VALUES (4, 'fourth', 'doinging', '', 'https://images.pexels.com/photos/220512/pexels-photo-220512.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=150', 'https://images.pexels.com/photos/220512/pexels-photo-220512.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=150', '');
INSERT INTO `qrcodedb` VALUES (5, 'fifth', 'break', 'break', 'break', 'break', '');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` int NOT NULL,
  `email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, 'someemail@someemailprovider.com', 'First');
INSERT INTO `user` VALUES (2, 'someemail@someemailprovider.com', 'Second');

SET FOREIGN_KEY_CHECKS = 1;
