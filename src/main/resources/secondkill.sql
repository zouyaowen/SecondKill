/*
Navicat MySQL Data Transfer

Source Server         : mysql
Source Server Version : 50722
Source Host           : 127.0.0.1:3306
Source Database       : secondkill

Target Server Type    : MYSQL
Target Server Version : 50722
File Encoding         : 65001

Date: 2019-06-16 21:01:32
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for item
-- ----------------------------
DROP TABLE IF EXISTS `item`;
CREATE TABLE `item` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(32) NOT NULL DEFAULT '',
  `price` decimal(10,2) NOT NULL DEFAULT '0.00',
  `description` varchar(256) NOT NULL DEFAULT '',
  `sales` int(11) NOT NULL DEFAULT '0',
  `img_url` varchar(128) NOT NULL DEFAULT '',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of item
-- ----------------------------
INSERT INTO `item` VALUES ('1', 'huawei', '200.00', '好用的手机', '12', 'http://8.pic.pc6.com/thumb/up/2018-1/2018111203031193270653480_600_0.png');
INSERT INTO `item` VALUES ('2', 'xiaomi', '200.00', '好用的手机', '0', 'http://8.pic.pc6.com/thumb/up/2018-1/2018111203031193270653480_600_0.png');
INSERT INTO `item` VALUES ('3', 'meizu', '200.00', '好用的手机', '0', 'http://8.pic.pc6.com/thumb/up/2018-1/2018111203031193270653480_600_0.png');
INSERT INTO `item` VALUES ('4', 'chuizi', '200.00', '好用的手机', '0', 'http://8.pic.pc6.com/thumb/up/2018-1/2018111203031193270653480_600_0.png');
INSERT INTO `item` VALUES ('5', 'iphone', '200.00', '苹果手机', '0', 'http://8.pic.pc6.com/thumb/up/2018-1/2018111203031193270653480_600_0.png');

-- ----------------------------
-- Table structure for item_stock
-- ----------------------------
DROP TABLE IF EXISTS `item_stock`;
CREATE TABLE `item_stock` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `stock` int(11) NOT NULL DEFAULT '0',
  `item_id` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of item_stock
-- ----------------------------
INSERT INTO `item_stock` VALUES ('1', '88', '1');
INSERT INTO `item_stock` VALUES ('2', '100', '2');
INSERT INTO `item_stock` VALUES ('3', '100', '3');
INSERT INTO `item_stock` VALUES ('4', '100', '4');
INSERT INTO `item_stock` VALUES ('5', '100', '5');

-- ----------------------------
-- Table structure for order_info
-- ----------------------------
DROP TABLE IF EXISTS `order_info`;
CREATE TABLE `order_info` (
  `id` varchar(32) NOT NULL,
  `user_id` int(11) NOT NULL DEFAULT '0',
  `item_id` int(11) NOT NULL DEFAULT '0',
  `item_price` decimal(10,2) NOT NULL DEFAULT '0.00',
  `item_quantity` int(11) NOT NULL DEFAULT '0',
  `order_amount` decimal(10,2) NOT NULL DEFAULT '0.00',
  `sec_kill_id` int(10) unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of order_info
-- ----------------------------
INSERT INTO `order_info` VALUES ('2019060900000000', '1', '1', '200.00', '2', '400.00', '0');
INSERT INTO `order_info` VALUES ('2019060900000100', '1', '1', '200.00', '2', '400.00', '0');
INSERT INTO `order_info` VALUES ('2019060900000200', '1', '1', '200.00', '3', '600.00', '0');
INSERT INTO `order_info` VALUES ('2019060900000300', '1', '1', '200.00', '5', '1000.00', '0');

-- ----------------------------
-- Table structure for seckill
-- ----------------------------
DROP TABLE IF EXISTS `seckill`;
CREATE TABLE `seckill` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `secKill_name` varchar(255) NOT NULL DEFAULT '',
  `start_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `end_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `item_id` int(11) NOT NULL DEFAULT '0',
  `secKill_price` decimal(10,2) unsigned NOT NULL DEFAULT '0.00',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of seckill
-- ----------------------------
INSERT INTO `seckill` VALUES ('1', '促销', '2019-06-09 23:27:05', '2019-06-12 23:27:11', '1', '100.00');

-- ----------------------------
-- Table structure for sequence_info
-- ----------------------------
DROP TABLE IF EXISTS `sequence_info`;
CREATE TABLE `sequence_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL DEFAULT '',
  `current_value` int(11) NOT NULL DEFAULT '0',
  `step` int(10) unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of sequence_info
-- ----------------------------
INSERT INTO `sequence_info` VALUES ('1', 'order_info', '4', '1');

-- ----------------------------
-- Table structure for user_info
-- ----------------------------
DROP TABLE IF EXISTS `user_info`;
CREATE TABLE `user_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(16) NOT NULL DEFAULT '',
  `gender` tinyint(4) NOT NULL DEFAULT '0' COMMENT '1:男性；2：女性',
  `age` tinyint(3) unsigned NOT NULL DEFAULT '0',
  `telphone` varchar(16) NOT NULL DEFAULT '',
  `register_mode` varchar(16) NOT NULL DEFAULT '',
  `third_party_id` varchar(32) NOT NULL DEFAULT '',
  PRIMARY KEY (`id`),
  UNIQUE KEY `telphone_unique_index` (`telphone`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of user_info
-- ----------------------------
INSERT INTO `user_info` VALUES ('1', 'aaa', '1', '20', '166', 'wechat', 'dhnsdhdrhdhdrghddg');
INSERT INTO `user_info` VALUES ('2', 'zzz', '1', '22', '176', '', '');
INSERT INTO `user_info` VALUES ('3', 'zzz', '1', '22', '111', '', '');
INSERT INTO `user_info` VALUES ('4', '222', '1', '22', '222', '', '');
INSERT INTO `user_info` VALUES ('5', '188', '1', '33', '188', '', '');

-- ----------------------------
-- Table structure for user_password
-- ----------------------------
DROP TABLE IF EXISTS `user_password`;
CREATE TABLE `user_password` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `encript_password` varchar(64) NOT NULL DEFAULT '',
  `user_id` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of user_password
-- ----------------------------
INSERT INTO `user_password` VALUES ('1', '202cb962ac59075b964b07152d234b70', '1');
INSERT INTO `user_password` VALUES ('2', '202cb962ac59075b964b07152d234b70', '2');
INSERT INTO `user_password` VALUES ('3', '202cb962ac59075b964b07152d234b70', '3');
INSERT INTO `user_password` VALUES ('4', '202cb962ac59075b964b07152d234b70', '4');
INSERT INTO `user_password` VALUES ('5', '202cb962ac59075b964b07152d234b70', '5');
