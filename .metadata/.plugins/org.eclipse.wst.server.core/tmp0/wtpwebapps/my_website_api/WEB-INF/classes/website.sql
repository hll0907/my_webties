/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 80011
Source Host           : localhost:3306
Source Database       : website

Target Server Type    : MYSQL
Target Server Version : 80011
File Encoding         : 65001

Date: 2018-07-04 16:46:32
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `user`
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `phone` varchar(11) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '',
  `nickname` varchar(50) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '',
  `salt` varchar(25) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '',
  `hash_password` varchar(50) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '',
  `head_pic` varchar(255) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '',
  `user_type` int(11) NOT NULL DEFAULT '0',
  `openid` varchar(255) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `last_visit_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `integral` int(11) NOT NULL DEFAULT '0',
  `vip_type` varchar(32) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '普通会员',
  `status` tinyint(1) NOT NULL DEFAULT '1',
  `actiCode` varchar(32) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1000 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- ----------------------------
-- Records of user
-- ----------------------------