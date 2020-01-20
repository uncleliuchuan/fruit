/*
 Navicat Premium Data Transfer

 Source Server         : 测试服务器
 Source Server Type    : MySQL
 Source Server Version : 50641
 Source Host           : 39.104.138.184:3306
 Source Schema         : fresh_fruit_selection

 Target Server Type    : MySQL
 Target Server Version : 50641
 File Encoding         : 65001

 Date: 20/01/2020 13:43:27
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for alipayment_order
-- ----------------------------
DROP TABLE IF EXISTS `alipayment_order`;
CREATE TABLE `alipayment_order`  (
  `Id` int(11) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `notify_time` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '通知时间 通知时间yyyy-MM-dd HH:mm:ss',
  `gmt_create` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '交易创建时间 交易创建时间:yyyy-MM-dd HH:mm:ss',
  `gmt_payment` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '交易付款时间',
  `gmt_refund` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '交易退款时间 交易退款时间',
  `gmt_close` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '交易结束时间 交易结束时间',
  `trade_no` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '支付宝的交易号 支付宝的交易号',
  `out_trade_no` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '商户订单号 商户系统的唯一订单号',
  `out_biz_no` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '商户业务号 商户业务ID，主要是退款通知中返回退款申请的流水号',
  `buyer_logon_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '买家支付宝账号 买家支付宝账号',
  `seller_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '卖家支付宝用户号 卖家支付宝用户号',
  `total_amount` decimal(10, 2) DEFAULT NULL COMMENT '订单金额 本次交易支付的订单金额，单位为人民币（元）',
  `receipt_amount` decimal(10, 2) DEFAULT NULL COMMENT '实收金额 商家在交易中实际收到的款项，单位为元',
  `invoice_amount` decimal(10, 2) DEFAULT NULL COMMENT '开票金额 用户在交易中支付的可开发票的金额',
  `buyer_pay_amount` decimal(10, 2) DEFAULT NULL COMMENT '付款金额 用户在交易中支付的金额',
  `trade_status` int(11) DEFAULT 0 COMMENT '交易状态 TRADE_FINISHED交易结束并不可退款=3\r\nTRADE_SUCCESS交易支付成功=2\r\nTRADE_CLOSED未付款交易超时关闭或支付完成后全额退款=1\r\nWAIT_BUYER_PAY交易创建并等待买家付款=0',
  `refund_fee` decimal(10, 2) DEFAULT NULL COMMENT '总退款金额',
  `seller_email` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '卖家支付宝账号',
  PRIMARY KEY (`Id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 79 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '支付宝订单表 存放支付宝订单信息' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of alipayment_order
-- ----------------------------
INSERT INTO `alipayment_order` VALUES (1, NULL, NULL, NULL, NULL, NULL, NULL, 'fruit1ed1c79a-5aed-4f6f-9c85-85a32f6a5b54', NULL, NULL, NULL, 15.00, 0.00, 0.00, 0.00, 0, 0.00, NULL);
INSERT INTO `alipayment_order` VALUES (2, NULL, NULL, NULL, NULL, NULL, NULL, 'fruit60ea1656-38c3-43ed-85a9-89d605a4ebb0', NULL, NULL, NULL, 15.00, 0.00, 0.00, 0.00, 0, 0.00, NULL);
INSERT INTO `alipayment_order` VALUES (3, NULL, NULL, NULL, NULL, NULL, NULL, 'fruita21fff6f-bf29-4450-a0bc-70d5d199fd38', NULL, NULL, NULL, 15.00, 0.00, 0.00, 0.00, 0, 0.00, NULL);
INSERT INTO `alipayment_order` VALUES (4, NULL, NULL, NULL, NULL, NULL, NULL, 'fruit09161c5c-3437-4ad7-be54-1f14e36051fd', NULL, NULL, NULL, 15.00, 0.00, 0.00, 0.00, 0, 0.00, NULL);
INSERT INTO `alipayment_order` VALUES (5, NULL, NULL, NULL, NULL, NULL, NULL, 'fruitbc1228d4-1af3-4ab1-bf7e-78ce1c88e0b9', NULL, NULL, NULL, 15.00, 0.00, 0.00, 0.00, 0, 0.00, NULL);
INSERT INTO `alipayment_order` VALUES (6, NULL, NULL, NULL, NULL, NULL, NULL, 'fruit7a41b061-4ec8-49dd-b24e-0e1c994f366d', NULL, NULL, NULL, 15.00, 0.00, 0.00, 0.00, 0, 0.00, NULL);
INSERT INTO `alipayment_order` VALUES (7, NULL, NULL, NULL, NULL, NULL, NULL, 'fruit4e928bc8-74b4-48be-a610-c10a15bb0a4b', NULL, NULL, NULL, 26.50, 0.00, 0.00, 0.00, 0, 0.00, NULL);
INSERT INTO `alipayment_order` VALUES (8, NULL, NULL, NULL, NULL, NULL, NULL, 'fruit0cd8c33f-8266-4660-8320-d28ef8d9e3f0', NULL, NULL, NULL, 26.50, 0.00, 0.00, 0.00, 0, 0.00, NULL);
INSERT INTO `alipayment_order` VALUES (9, NULL, NULL, NULL, NULL, NULL, NULL, 'fruite4f10b59-518c-4e2f-bd59-7f25072b3224', NULL, NULL, NULL, 38.00, 0.00, 0.00, 0.00, 0, 0.00, NULL);
INSERT INTO `alipayment_order` VALUES (10, NULL, NULL, NULL, NULL, NULL, NULL, 'fruit36b4bb80-43b6-496c-8cf2-d7e18dc674a5', NULL, NULL, NULL, 15.00, 0.00, 0.00, 0.00, 0, 0.00, NULL);
INSERT INTO `alipayment_order` VALUES (11, NULL, NULL, NULL, NULL, NULL, NULL, 'fruitf8170515-2900-4efb-b34e-738a8bc2a2c3', NULL, NULL, NULL, 15.00, 0.00, 0.00, 0.00, 0, 0.00, NULL);
INSERT INTO `alipayment_order` VALUES (12, '2020-01-13 16:39:36', '2020-01-13 16:25:20', '2020-01-13 16:25:21', NULL, NULL, '2020011322001453541410604801', 'vipf8b83e83-50b5-4b63-903f-d63b8a10d85b', NULL, '185****4633', '2088331450488701', 0.01, 0.01, 0.01, 0.01, 2, 0.00, '18612099257@163.com');
INSERT INTO `alipayment_order` VALUES (13, '2020-01-13 16:33:49', '2020-01-13 16:33:48', '2020-01-13 16:33:48', NULL, NULL, '2020011322001453541410772876', 'vip5ab7c388-3cd0-46d8-b68c-4ac443156fec', NULL, '185******33', '2088331450488701', 0.01, 0.00, 0.00, 0.00, 2, 0.00, '18612099257@163.com');
INSERT INTO `alipayment_order` VALUES (14, '2020-01-13 16:36:04', '2020-01-13 16:36:02', '2020-01-13 16:36:03', NULL, NULL, '2020011322001426191409385625', 'vip2e00dafa-2d89-45da-8020-25abe112cf70', NULL, '133***@qq.com', '2088331450488701', 0.01, 0.00, 0.00, 0.00, 2, 0.00, '18612099257@163.com');
INSERT INTO `alipayment_order` VALUES (15, '2020-01-13 16:36:21', '2020-01-13 16:36:20', '2020-01-13 16:36:20', NULL, NULL, '2020011322001453541410577624', 'vip557d1f3e-5080-419c-962b-28ec4d4f6a84', NULL, '185******33', '2088331450488701', 0.01, 0.00, 0.00, 0.00, 2, 0.00, '18612099257@163.com');
INSERT INTO `alipayment_order` VALUES (16, NULL, NULL, NULL, NULL, NULL, NULL, 'fruite2926819-2a78-4c45-a6d3-334f4bf67624', NULL, NULL, NULL, 15.00, 0.00, 0.00, 0.00, 0, 0.00, NULL);
INSERT INTO `alipayment_order` VALUES (17, '2020-01-13 16:48:44', '2020-01-13 16:45:24', '2020-01-13 16:45:25', NULL, NULL, '2020011322001453541410792440', 'fruit59cde6ae-4a34-4980-97cf-1262e79fcbdf', NULL, '185****4633', '2088331450488701', 0.01, 0.01, 0.01, 0.01, 2, 0.00, '18612099257@163.com');
INSERT INTO `alipayment_order` VALUES (18, '2020-01-13 16:52:29', '2020-01-13 16:52:28', '2020-01-13 16:52:29', NULL, NULL, '2020011322001426191409409794', 'vip588151f7-3fec-4f73-88a4-51382638c2c0', NULL, '133***@qq.com', '2088331450488701', 0.01, 0.00, 0.00, 0.00, 2, 0.00, '18612099257@163.com');
INSERT INTO `alipayment_order` VALUES (19, NULL, NULL, NULL, NULL, NULL, NULL, 'fruit22ae5ae7-70fa-450a-841a-3bd19594a0dc', NULL, NULL, NULL, 30.00, 0.00, 0.00, 0.00, 0, 0.00, NULL);
INSERT INTO `alipayment_order` VALUES (20, '2020-01-13 20:02:32', '2020-01-13 19:59:18', '2020-01-13 19:59:19', NULL, NULL, '2020011322001438261409186220', 'fruit43c9131d-f8cd-4f51-b86c-243a8a0a6da4', NULL, '150****8992', '2088331450488701', 0.01, 0.01, 0.01, 0.01, 2, 0.00, '18612099257@163.com');
INSERT INTO `alipayment_order` VALUES (21, '2020-01-13 20:05:47', '2020-01-13 20:02:41', '2020-01-13 20:02:41', NULL, NULL, '2020011322001438261409516506', 'fruit53c84ecf-5d7e-47d2-9bb9-19d9e41bcfd2', NULL, '150****8992', '2088331450488701', 0.01, 0.01, 0.01, 0.01, 2, 0.00, '18612099257@163.com');
INSERT INTO `alipayment_order` VALUES (22, '2020-01-13 20:10:04', '2020-01-13 20:07:07', '2020-01-13 20:07:08', NULL, NULL, '2020011322001438261409401385', 'fruitdebac049-c38c-40a0-bcef-5b1aeab4a067', NULL, '150****8992', '2088331450488701', 0.01, 0.01, 0.01, 0.01, 2, 0.00, '18612099257@163.com');
INSERT INTO `alipayment_order` VALUES (23, NULL, NULL, NULL, NULL, NULL, NULL, 'fruitca306285-b0dc-40cf-98fc-4131238aef09', NULL, NULL, NULL, 15.00, 0.00, 0.00, 0.00, 0, 0.00, NULL);
INSERT INTO `alipayment_order` VALUES (24, '2020-01-14 11:15:39', '2020-01-14 11:12:25', '2020-01-14 11:12:25', NULL, NULL, '2020011422001438261409542748', 'fruit4aee80b5-457d-4fba-8d12-9e17cd70efc1', NULL, '150****8992', '2088331450488701', 0.01, 0.01, 0.01, 0.01, 2, 0.00, '18612099257@163.com');
INSERT INTO `alipayment_order` VALUES (25, '2020-01-14 11:22:35', '2020-01-14 11:19:25', '2020-01-14 11:19:25', NULL, NULL, '2020011422001438261409611048', 'fruitece40ce3-cf9a-47d2-96b6-782b14135efc', NULL, '150****8992', '2088331450488701', 0.01, 0.01, 0.01, 0.01, 2, 0.00, '18612099257@163.com');
INSERT INTO `alipayment_order` VALUES (26, '2020-01-14 11:25:12', '2020-01-14 11:22:13', '2020-01-14 11:22:13', NULL, NULL, '2020011422001438261409525473', 'fruit75f9824e-62ff-4716-b8e3-db6e4b8112ae', NULL, '150****8992', '2088331450488701', 0.01, 0.01, 0.01, 0.01, 2, 0.00, '18612099257@163.com');
INSERT INTO `alipayment_order` VALUES (27, '2020-01-14 11:30:33', '2020-01-14 11:27:06', '2020-01-14 11:27:07', NULL, NULL, '2020011422001438261409538206', 'fruit07c846c9-88ad-4bcf-a4ff-67341af11087', NULL, '150****8992', '2088331450488701', 0.01, 0.01, 0.01, 0.01, 2, 0.00, '18612099257@163.com');
INSERT INTO `alipayment_order` VALUES (28, '2020-01-14 11:31:06', '2020-01-14 11:28:26', '2020-01-14 11:28:27', NULL, NULL, '2020011422001438261409611049', 'fruit0b73ff95-6e0a-4f3c-9566-2f241b0e8923', NULL, '150****8992', '2088331450488701', 0.01, 0.01, 0.01, 0.01, 2, 0.00, '18612099257@163.com');
INSERT INTO `alipayment_order` VALUES (29, '2020-01-14 11:38:03', '2020-01-14 11:35:22', '2020-01-14 11:35:23', NULL, NULL, '2020011422001438261409527261', 'fruita81684fc-4c11-4757-a531-1d4922c38319', NULL, '150****8992', '2088331450488701', 0.01, 0.01, 0.01, 0.01, 2, 0.00, '18612099257@163.com');
INSERT INTO `alipayment_order` VALUES (30, NULL, NULL, NULL, NULL, NULL, NULL, 'fruitf17d37c0-b0e9-4ad8-82f2-0bbe26d852c9', NULL, NULL, NULL, 1.00, 0.00, 0.00, 0.00, 0, 0.00, NULL);
INSERT INTO `alipayment_order` VALUES (31, '2020-01-14 11:49:42', '2020-01-14 11:46:54', '2020-01-14 11:46:54', NULL, NULL, '2020011422001438261409516628', 'fruit6162e345-4101-4f91-9a9e-0f4bc635a385', NULL, '150****8992', '2088331450488701', 0.01, 0.01, 0.01, 0.01, 2, 0.00, '18612099257@163.com');
INSERT INTO `alipayment_order` VALUES (32, NULL, NULL, NULL, NULL, NULL, NULL, 'fruit6e0ce8a8-7a9d-42b1-8143-58e28a910d4e', NULL, NULL, NULL, 15.00, 0.00, 0.00, 0.00, 0, 0.00, NULL);
INSERT INTO `alipayment_order` VALUES (33, '2020-01-14 14:10:34', '2020-01-14 14:07:26', '2020-01-14 14:07:26', NULL, NULL, '2020011422001438261409590559', 'fruit6e271c5e-0e4b-48fb-9dbe-5c30c7f723ac', NULL, '150****8992', '2088331450488701', 0.01, 0.01, 0.01, 0.01, 2, 0.00, '18612099257@163.com');
INSERT INTO `alipayment_order` VALUES (34, NULL, NULL, NULL, NULL, NULL, NULL, 'fruit7115f198-bc6d-418d-9e50-80f53dca35af', NULL, NULL, NULL, 15.00, 0.00, 0.00, 0.00, 0, 0.00, NULL);
INSERT INTO `alipayment_order` VALUES (35, NULL, NULL, NULL, NULL, NULL, NULL, 'fruitc611e26b-abb5-4d73-9a41-6c0b20671778', NULL, NULL, NULL, 15.00, 0.00, 0.00, 0.00, 0, 0.00, NULL);
INSERT INTO `alipayment_order` VALUES (36, NULL, NULL, NULL, NULL, NULL, NULL, 'fruit882ae4db-c047-44cd-8429-a9132da64ac4', NULL, NULL, NULL, 15.00, 0.00, 0.00, 0.00, 0, 0.00, NULL);
INSERT INTO `alipayment_order` VALUES (37, '2020-01-14 14:19:37', '2020-01-14 14:16:44', '2020-01-14 14:16:44', NULL, NULL, '2020011422001438261409621626', 'fruitd49c4dfc-4aec-43cd-8766-3bd929904144', NULL, '150****8992', '2088331450488701', 0.01, 0.01, 0.01, 0.01, 2, 0.00, '18612099257@163.com');
INSERT INTO `alipayment_order` VALUES (38, '2020-01-14 14:23:29', '2020-01-14 14:20:48', '2020-01-14 14:20:49', NULL, NULL, '2020011422001438261409621628', 'fruit8bf5e497-de4f-4812-bdcf-f009d6d60464', NULL, '150****8992', '2088331450488701', 0.01, 0.01, 0.01, 0.01, 2, 0.00, '18612099257@163.com');
INSERT INTO `alipayment_order` VALUES (39, NULL, NULL, NULL, NULL, NULL, NULL, 'fruitc0a4df8c-fae0-463f-86bd-e3f048e28929', NULL, NULL, NULL, 15.00, 0.00, 0.00, 0.00, 0, 0.00, NULL);
INSERT INTO `alipayment_order` VALUES (40, '2020-01-15 13:37:21', '2020-01-15 13:34:38', '2020-01-15 13:34:38', NULL, NULL, '2020011522001453541411383232', 'fruit6ba4963b-5fa7-449b-8c35-a23ba3ec23b4', NULL, '185****4633', '2088331450488701', 0.01, 0.01, 0.01, 0.01, 2, 0.00, '18612099257@163.com');
INSERT INTO `alipayment_order` VALUES (41, NULL, NULL, NULL, NULL, NULL, NULL, 'fruit70cc852e-3ca0-4936-b3b4-cf4e52b22142', NULL, NULL, NULL, 15.00, 0.00, 0.00, 0.00, 0, 0.00, NULL);
INSERT INTO `alipayment_order` VALUES (42, '2020-01-15 13:41:16', '2020-01-15 13:38:42', '2020-01-15 13:38:43', NULL, NULL, '2020011522001453541411503350', 'fruitc6e7be28-dee2-4871-9056-a7038784cef3', NULL, '185****4633', '2088331450488701', 0.01, 0.01, 0.01, 0.01, 2, 0.00, '18612099257@163.com');
INSERT INTO `alipayment_order` VALUES (43, NULL, NULL, NULL, NULL, NULL, NULL, 'fruit92a0b2ab-f685-403e-8255-e7f77e45fde7', NULL, NULL, NULL, 15.00, 0.00, 0.00, 0.00, 0, 0.00, NULL);
INSERT INTO `alipayment_order` VALUES (44, '2020-01-15 13:58:44', '2020-01-15 13:55:30', '2020-01-15 13:55:31', NULL, NULL, '2020011522001453541411457641', 'fruit23e8807d-88b6-465c-adaf-4b098257b828', NULL, '185****4633', '2088331450488701', 0.01, 0.01, 0.01, 0.01, 2, 0.00, '18612099257@163.com');
INSERT INTO `alipayment_order` VALUES (45, '2020-01-15 14:07:43', '2020-01-15 14:07:42', '2020-01-15 14:07:42', NULL, NULL, '2020011522001453541411713061', 'fruit70fac23c-ad9d-4c46-85b3-94c5e464d29c', NULL, '185******33', '2088331450488701', 0.01, 0.00, 0.00, 0.00, 2, 0.00, '18612099257@163.com');
INSERT INTO `alipayment_order` VALUES (46, '2020-01-15 14:08:03', '2020-01-15 14:08:02', '2020-01-15 14:08:03', NULL, NULL, '2020011522001453541411371826', 'fruit11e0d882-ddfb-489e-b713-4f0a8fc75014', NULL, '185******33', '2088331450488701', 0.01, 0.00, 0.00, 0.00, 2, 0.00, '18612099257@163.com');
INSERT INTO `alipayment_order` VALUES (47, '2020-01-15 19:50:07', '2020-01-15 19:50:06', '2020-01-15 19:50:07', NULL, NULL, '2020011522001438261410386597', 'fruit84711af8-d3fc-409c-a689-7480e552ed47', NULL, '150****8992', '2088331450488701', 0.01, 0.01, 0.01, 0.01, 2, 0.00, '18612099257@163.com');
INSERT INTO `alipayment_order` VALUES (48, NULL, NULL, NULL, NULL, NULL, NULL, 'fruit10301527-b39e-4406-9a2b-aaae805a62f2', NULL, NULL, NULL, 0.01, 0.00, 0.00, 0.00, 0, 0.00, NULL);
INSERT INTO `alipayment_order` VALUES (49, NULL, NULL, NULL, NULL, NULL, NULL, 'fruit4e2e2bd4-1ba8-4b48-b1a8-92c03617cdfc', NULL, NULL, NULL, 0.01, 0.00, 0.00, 0.00, 0, 0.00, NULL);
INSERT INTO `alipayment_order` VALUES (50, NULL, NULL, NULL, NULL, NULL, NULL, 'fruit88645daa-fab0-4922-b3a0-8789224ca899', NULL, NULL, NULL, 0.01, 0.00, 0.00, 0.00, 0, 0.00, NULL);
INSERT INTO `alipayment_order` VALUES (51, NULL, NULL, NULL, NULL, NULL, NULL, 'fruitb56750dc-5f2c-48e1-b6c7-73c272f8ebe3', NULL, NULL, NULL, 0.01, 0.00, 0.00, 0.00, 0, 0.00, NULL);
INSERT INTO `alipayment_order` VALUES (52, NULL, NULL, NULL, NULL, NULL, NULL, 'fruit54ab7221-3d9a-4bb1-ac50-dd797aec481b', NULL, NULL, NULL, 0.01, 0.00, 0.00, 0.00, 0, 0.00, NULL);
INSERT INTO `alipayment_order` VALUES (53, '2020-01-16 15:21:57', '2020-01-16 15:21:56', '2020-01-16 15:21:56', NULL, NULL, '2020011622001453541411842454', 'vip2e6c0255-5aaf-4451-b598-681de4f4ffc3', NULL, '185******33', '2088331450488701', 0.01, 0.00, 0.00, 0.00, 2, 0.00, '18612099257@163.com');
INSERT INTO `alipayment_order` VALUES (54, NULL, NULL, NULL, NULL, NULL, NULL, 'fruit9074c0b8-2656-4250-9ac1-031cce672f4e', NULL, NULL, NULL, 0.01, 0.00, 0.00, 0.00, 0, 0.00, NULL);
INSERT INTO `alipayment_order` VALUES (55, '2020-01-17 12:55:32', '2020-01-17 12:55:31', '2020-01-17 12:55:31', NULL, NULL, '2020011722001445241411081146', 'vip03c31237-20cd-4789-9193-0d071354908b', NULL, '187******17', '2088331450488701', 0.01, 0.00, 0.00, 0.00, 2, 0.00, '18612099257@163.com');
INSERT INTO `alipayment_order` VALUES (56, '2020-01-17 13:04:53', '2020-01-17 13:04:52', '2020-01-17 13:04:53', NULL, NULL, '2020011722001445241411094707', 'fruit81ff8405-0d24-4160-8384-40446de55ea9', NULL, '187******17', '2088331450488701', 0.02, 0.00, 0.00, 0.00, 2, 0.00, '18612099257@163.com');
INSERT INTO `alipayment_order` VALUES (57, NULL, NULL, NULL, NULL, NULL, NULL, 'vip8d201d0a-9e29-4275-b393-f0c2de7d33d5', NULL, NULL, NULL, 0.01, 0.00, 0.00, 0.00, 0, 0.00, NULL);
INSERT INTO `alipayment_order` VALUES (58, NULL, NULL, NULL, NULL, NULL, NULL, 'vip54b1b5b1-0bef-450d-aee0-b40243dd5984', NULL, NULL, NULL, 0.01, 0.00, 0.00, 0.00, 0, 0.00, NULL);
INSERT INTO `alipayment_order` VALUES (59, NULL, NULL, NULL, NULL, NULL, NULL, 'fruit8466dc31-700e-40ad-a14c-f0eefb5ac3ea', NULL, NULL, NULL, 0.01, 0.00, 0.00, 0.00, 0, 0.00, NULL);
INSERT INTO `alipayment_order` VALUES (60, NULL, NULL, NULL, NULL, NULL, NULL, 'fruit344ca6ee-d155-4cc7-9b7f-3cbe7b21258e', NULL, NULL, NULL, 0.01, 0.00, 0.00, 0.00, 0, 0.00, NULL);
INSERT INTO `alipayment_order` VALUES (61, NULL, NULL, NULL, NULL, NULL, NULL, 'fruitb2b19a6d-a628-4c75-92ca-0eec2584311d', NULL, NULL, NULL, 0.03, 0.00, 0.00, 0.00, 0, 0.00, NULL);
INSERT INTO `alipayment_order` VALUES (62, NULL, NULL, NULL, NULL, NULL, NULL, 'fruit6a6a90e9-fd84-4a9f-9388-3afed29e34c4', NULL, NULL, NULL, 0.03, 0.00, 0.00, 0.00, 0, 0.00, NULL);
INSERT INTO `alipayment_order` VALUES (63, '2020-01-18 15:03:12', '2020-01-18 15:03:12', '2020-01-18 15:03:12', NULL, NULL, '2020011822001461821414419479', 'vipd209fd95-b0d7-4743-abe1-8a2d61ecc03f', NULL, '152******61', '2088331450488701', 0.01, 0.00, 0.00, 0.00, 2, 0.00, '18612099257@163.com');
INSERT INTO `alipayment_order` VALUES (64, NULL, NULL, NULL, NULL, NULL, NULL, 'fruit3efaed2e-c2e4-4e4e-ab96-50b6eb6dd2c2', NULL, NULL, NULL, 0.01, 0.00, 0.00, 0.00, 0, 0.00, NULL);
INSERT INTO `alipayment_order` VALUES (65, NULL, NULL, NULL, NULL, NULL, NULL, 'vip58fcd2eb-fee9-44c5-8c15-cf57a6d476f0', NULL, NULL, NULL, 0.01, 0.00, 0.00, 0.00, 0, 0.00, NULL);
INSERT INTO `alipayment_order` VALUES (66, NULL, NULL, NULL, NULL, NULL, NULL, 'fruit30b90ac6-cf0e-451d-b578-68aa94802835', NULL, NULL, NULL, 0.02, 0.00, 0.00, 0.00, 0, 0.00, NULL);
INSERT INTO `alipayment_order` VALUES (67, '2020-01-18 17:06:23', '2020-01-18 17:06:22', '2020-01-18 17:06:23', NULL, NULL, '2020011822001490651436811033', 'fruit9c12d39e-9b10-4938-b116-ad8e35f2ef10', NULL, '176******37', '2088331450488701', 0.01, 0.00, 0.00, 0.00, 2, 0.00, '18612099257@163.com');
INSERT INTO `alipayment_order` VALUES (68, NULL, NULL, NULL, NULL, NULL, NULL, 'vip595448ab-6b89-4714-8971-17c533536cf8', NULL, NULL, NULL, 0.01, 0.00, 0.00, 0.00, 0, 0.00, NULL);
INSERT INTO `alipayment_order` VALUES (69, '2020-01-19 14:22:56', '2020-01-19 14:22:55', '2020-01-19 14:22:55', NULL, NULL, '2020011922001461821414810675', 'fruit2b32e002-2d8b-4a0f-b0c5-8a46ffa23015', NULL, '152******61', '2088331450488701', 0.01, 0.00, 0.00, 0.00, 2, 0.00, '18612099257@163.com');
INSERT INTO `alipayment_order` VALUES (70, NULL, NULL, NULL, NULL, NULL, NULL, 'fruitc785e334-a482-40b1-a7cd-d4698bf8194b', NULL, NULL, NULL, 0.01, 0.00, 0.00, 0.00, 0, 0.00, NULL);
INSERT INTO `alipayment_order` VALUES (71, '2020-01-19 14:56:27', '2020-01-19 14:56:26', '2020-01-19 14:56:27', NULL, NULL, '2020011922001438261411350362', 'fruit295746fa-0160-46e3-8d8f-45b6dbd37748', NULL, '150******92', '2088331450488701', 0.01, 0.00, 0.00, 0.00, 2, 0.00, '18612099257@163.com');
INSERT INTO `alipayment_order` VALUES (72, '2020-01-19 15:08:17', '2020-01-19 15:08:17', '2020-01-19 15:08:17', NULL, NULL, '2020011922001438261411509816', 'fruit267aefd5-2d93-436d-859b-32a4b43de400', NULL, '150******92', '2088331450488701', 0.01, 0.00, 0.00, 0.00, 2, 0.00, '18612099257@163.com');
INSERT INTO `alipayment_order` VALUES (73, NULL, NULL, NULL, NULL, NULL, NULL, 'fruitf420d0cd-0bdd-45e7-8986-7c1aeb9e4281', NULL, NULL, NULL, 0.01, 0.00, 0.00, 0.00, 0, 0.00, NULL);
INSERT INTO `alipayment_order` VALUES (74, NULL, NULL, NULL, NULL, NULL, NULL, 'fruita9b4e369-e8c0-4031-bcb8-0f76a28877d0', NULL, NULL, NULL, 0.01, 0.00, 0.00, 0.00, 0, 0.00, NULL);
INSERT INTO `alipayment_order` VALUES (75, NULL, NULL, NULL, NULL, NULL, NULL, 'fruit1f0c02fe-f734-4ab8-86d0-b8ec85779bf1', NULL, NULL, NULL, 0.01, 0.00, 0.00, 0.00, 0, 0.00, NULL);
INSERT INTO `alipayment_order` VALUES (76, NULL, NULL, NULL, NULL, NULL, NULL, 'fruit6b665c4f-e590-418f-a51c-bfe70f99f1e3', NULL, NULL, NULL, 0.01, 0.00, 0.00, 0.00, 0, 0.00, NULL);
INSERT INTO `alipayment_order` VALUES (77, '2020-01-19 15:40:58', '2020-01-19 15:40:57', '2020-01-19 15:40:57', NULL, NULL, '2020011922001438261411433326', 'fruit62fab721-1776-41df-8556-b9c7f7e5f4df', NULL, '150******92', '2088331450488701', 0.01, 0.00, 0.00, 0.00, 2, 0.00, '18612099257@163.com');
INSERT INTO `alipayment_order` VALUES (78, NULL, NULL, NULL, NULL, NULL, NULL, 'fruit4f595199-3a16-45d1-b0a9-162f45d7bfa5', NULL, NULL, NULL, 0.01, 0.00, 0.00, 0.00, 0, 0.00, NULL);

-- ----------------------------
-- Table structure for assistance
-- ----------------------------
DROP TABLE IF EXISTS `assistance`;
CREATE TABLE `assistance`  (
  `id` int(15) NOT NULL AUTO_INCREMENT,
  `fruit_id` int(15) DEFAULT NULL COMMENT '水果ID',
  `user_id` int(15) DEFAULT NULL COMMENT '发起人ID',
  `create_time` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '创建时间',
  `assistance_status` int(1) DEFAULT 0 COMMENT '助力状态 0-待助力 1-助力成功',
  `success_time` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '助力成功时间',
  `address_id` int(15) DEFAULT NULL COMMENT '地址id',
  `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '收货人姓名',
  `phone` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '收货人手机号',
  `address` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '收货人详细地址',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 20 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of assistance
-- ----------------------------
INSERT INTO `assistance` VALUES (17, 1, 11, '20200118150344', 0, NULL, 74, '比堪培拉', '15213544846', '北京 北京市 东城区 接回来茉莉');
INSERT INTO `assistance` VALUES (18, 1, 2, '20200118151436', 1, '20200118151719', 70, '星有万种', '18410716982', ' 北京市 房山区 拱辰街道 咯娜姆Rom拖');
INSERT INTO `assistance` VALUES (19, 1, 5, '20200119160459', 0, NULL, 36, '张先生', '13457897968', '北京 北京市 朝阳区 无所天蝎座');

-- ----------------------------
-- Table structure for assistance_log
-- ----------------------------
DROP TABLE IF EXISTS `assistance_log`;
CREATE TABLE `assistance_log`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `assistance_id` int(15) DEFAULT NULL COMMENT '助力id',
  `sponsor_id` int(15) DEFAULT NULL COMMENT '发起人ID',
  `auth_id` int(15) DEFAULT NULL COMMENT '助力人微信id',
  `create_time` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 12 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of assistance_log
-- ----------------------------
INSERT INTO `assistance_log` VALUES (6, 18, 4, 5, '20200116172732');
INSERT INTO `assistance_log` VALUES (7, 18, 4, 5, '20200116172732');
INSERT INTO `assistance_log` VALUES (8, 18, 4, 5, '20200116172732');
INSERT INTO `assistance_log` VALUES (9, 18, 4, 5, '20200116172732');
INSERT INTO `assistance_log` VALUES (10, 18, 2, 12, '20200118151719');
INSERT INTO `assistance_log` VALUES (11, 19, 5, 4, '20200119160531');

-- ----------------------------
-- Table structure for fruit
-- ----------------------------
DROP TABLE IF EXISTS `fruit`;
CREATE TABLE `fruit`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `fruit_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '水果名称',
  `fruit_picture_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '首页图片',
  `fruit_price` decimal(10, 1) DEFAULT NULL COMMENT '水果价格',
  `fruit_vip_price` decimal(10, 1) DEFAULT NULL COMMENT '会员价',
  `fruit_type` int(5) DEFAULT NULL COMMENT '水果类型枚举',
  `fruit_detail_carousel` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '详情页轮播图',
  `fruit_num` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '单位价格水果数量 例：1提/约3kg',
  `fruit_accepted_num` int(100) DEFAULT NULL COMMENT '水果已售数量',
  `fruit_origin_place` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '水果产地 例：烟台',
  `fruit_specifications` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '规格 例：1箱',
  `fruit_weight` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '水果重量 例：16粒装',
  `fruit_packing` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '水果包装 例：箱装',
  `fruit_quality` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '保质期 例：7天',
  `fruit_storage_mode` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '储存方式 例：常温',
  `fruit_detail` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '水果图片详情',
  `status` int(1) DEFAULT 1 COMMENT '水果状态 -1下架/1上架',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of fruit
-- ----------------------------
INSERT INTO `fruit` VALUES (1, '爱媛果冻橙', 'http://img.staticvip.cn/cd9f49d6-0b31-4680-950f-668a143a1a19', 39.9, 15.0, 1, 'http://img.staticvip.cn/2203d826-e503-478e-84cc-d22e010fef75', '1提/约3kg', 45, '烟台', '1箱', '16粒装', '箱装', '7天', '常温', 'http://img.staticvip.cn/022ba566-e24c-422a-830c-2fbcdaa96f76', 1);
INSERT INTO `fruit` VALUES (2, '烟台红富士', 'http://img.staticvip.cn/2203d826-e503-478e-84cc-d22e010fef75', 30.0, 11.5, 2, 'http://img.staticvip.cn/2203d826-e503-478e-84cc-d22e010fef75', '1提/约3kg', 9, '烟台', '1箱', '16粒装', '箱装', '7天', '常温', 'http://img.staticvip.cn/022ba566-e24c-422a-830c-2fbcdaa96f76', 1);
INSERT INTO `fruit` VALUES (3, '香蕉', 'http://img.staticvip.cn/2203d826-e503-478e-84cc-d22e010fef75', 39.9, 15.0, 3, 'http://img.staticvip.cn/2203d826-e503-478e-84cc-d22e010fef75', '1提/约3kg', 9, '烟台', '1箱', '16粒装', '箱装', '7天', '常温', 'http://img.staticvip.cn/022ba566-e24c-422a-830c-2fbcdaa96f76', 1);
INSERT INTO `fruit` VALUES (4, '测试', 'http://img.staticvip.cn/de4589a4-cf9d-4a8f-9408-f4c261e59079', 20.0, 10.0, 6, 'http://img.staticvip.cn/a7f1e781-188f-4f33-9bd0-5818cefed1c3', '阿迪王所', NULL, '发', '阿凡达', '但是', '啊', '电饭锅', '和谁', 'http://img.staticvip.cn/f81d4677-8cb4-4191-8791-d1f177ced8aa', 1);
INSERT INTO `fruit` VALUES (5, '12', 'http://img.staticvip.cn/48d70e63-9cc7-4a66-9e96-76754157a27b', 12.0, 1.0, 2, 'http://img.staticvip.cn/2b773c22-f1e3-4ee4-9bc1-ad6c01af168f', '12', 6, '12', '12', '12', '12', '12', '12', 'http://img.staticvip.cn/440df7e3-c71a-48e2-9db7-b782476f35fa', 1);
INSERT INTO `fruit` VALUES (6, '123', 'http://img.staticvip.cn/82a93bf1-7ee0-4bb8-9ae1-cdb8ede94b7a', 12.0, 1.0, 1, 'http://img.staticvip.cn/cf2bc649-8ee7-45a6-b98f-81c0e74cd4e9;http://img.staticvip.cn/063bf6fa-2bfe-4ea1-947d-8638773da47d', '123', 4, '12', '12', '123', '12', '12', '12', 'http://img.staticvip.cn/577e100c-b83f-44c2-87ff-33a0bd6a36b3;http://img.staticvip.cn/dfa673b7-632d-4f2f-a578-39d25bb0203b;http://img.staticvip.cn/43238f66-61fe-4243-ab75-fee7c8a76e5c', 1);

-- ----------------------------
-- Table structure for fruit_comments
-- ----------------------------
DROP TABLE IF EXISTS `fruit_comments`;
CREATE TABLE `fruit_comments`  (
  `id` int(15) NOT NULL AUTO_INCREMENT,
  `fruit_id` int(15) DEFAULT NULL COMMENT '水果id',
  `user_id` int(15) DEFAULT NULL COMMENT '用户id',
  `texture` int(5) DEFAULT 5 COMMENT '口感',
  `appearance` int(5) DEFAULT 5 COMMENT '颜值',
  `comment_content` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '评论内容',
  `comment_url` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '评论图片',
  `create_time` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '创建时间',
  `order_id` int(15) DEFAULT NULL COMMENT '订单ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 195 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of fruit_comments
-- ----------------------------
INSERT INTO `fruit_comments` VALUES (1, 1, 1, 5, 5, 'sadfsadsd', NULL, '20191210152204', 1);
INSERT INTO `fruit_comments` VALUES (2, 1, 9, 4, 5, '个大多汁;大小很均匀;汁多甜', NULL, '20200110121002', NULL);
INSERT INTO `fruit_comments` VALUES (3, 1, 4, 5, 5, '请求', NULL, '20200116144124', NULL);
INSERT INTO `fruit_comments` VALUES (4, 2, 9, 5, 5, '新鲜;回头客;分量很足', 'http://img.staticvip.cn/Frgh3Xl4ip2O9LIW4o-80smAAe8d;http://img.staticvip.cn/Fj1zguta3wZMhKnBX1QjUfJL2J7h', '20200116152547', 183);
INSERT INTO `fruit_comments` VALUES (5, 2, 9, 5, 5, '新鲜;回头客;分量很足', 'http://img.staticvip.cn/FjappbbB7bXNEr5_COsgu1RBuhy7;http://img.staticvip.cn/Frxw3ygMQS_LoR94naXV3Yc-DyG1', '20200116154356', 184);
INSERT INTO `fruit_comments` VALUES (6, 1, 9, 5, 5, '个大多汁;大小很均匀;有坏果', 'http://img.staticvip.cn/Fqqqtrczwz8xODyL6FrZ2JpsGWDB;http://img.staticvip.cn/FkmUFq69ng9Trp-2uLE_GLohYhUS', '20200116155355', 9);
INSERT INTO `fruit_comments` VALUES (7, 1, 9, 5, 4, '个大多汁;酸甜可口;分量很足', 'http://img.staticvip.cn/Fqqqtrczwz8xODyL6FrZ2JpsGWDB;http://img.staticvip.cn/Fj1zguta3wZMhKnBX1QjUfJL2J7h', '20200116155528', 7);
INSERT INTO `fruit_comments` VALUES (177, 1, 4, 2, 1, '没有坏果;分量很足;回头客', 'http://img.staticvip.cn/icon_20200117135056', '20200117135320', NULL);
INSERT INTO `fruit_comments` VALUES (178, 1, 4, 2, 1, '没有坏果;分量很足;回头客', 'http://img.staticvip.cn/icon_20200117135056', '20200117135402', 177);
INSERT INTO `fruit_comments` VALUES (179, 1, 2, 5, 5, '颜值好评;新鲜;分量很足', 'http://img.staticvip.cn/FgixPiZb5y1QEJAyM0nKO0moigl3', '20200117141501', 188);
INSERT INTO `fruit_comments` VALUES (180, 1, 4, 1, 1, '个大多汁;有坏果', 'http://img.staticvip.cn/icon_20200117141535', '20200117141538', 178);
INSERT INTO `fruit_comments` VALUES (181, 2, 2, 5, 5, '水分充足;大小很均匀', 'http://img.staticvip.cn/FmR1XdhtoZJyz8HHg-rKRer2iD6X', '20200117141540', 189);
INSERT INTO `fruit_comments` VALUES (182, 1, 4, 1, 3, '酸甜可口;物流慢;大小很均匀', 'http://img.staticvip.cn/icon_20200117142209', '20200117142212', 174);
INSERT INTO `fruit_comments` VALUES (183, 3, 4, 2, 1, '个大多汁;酸甜可口;有坏果', 'http://img.staticvip.cn/icon_20200117143043', '20200117143046', 150);
INSERT INTO `fruit_comments` VALUES (184, 5, 4, 1, 1, '非常好吃;没有坏果;回头客', 'http://img.staticvip.cn/icon_20200117143606', '20200117143609', 149);
INSERT INTO `fruit_comments` VALUES (185, 3, 4, 1, 2, '大小很均匀;物流慢;个大多汁', 'http://img.staticvip.cn/icon_20200117144706', '20200117144717', 193);
INSERT INTO `fruit_comments` VALUES (186, 5, 4, 2, 2, '个大多汁;大小很均匀;物流慢', '', '20200117145405', 194);
INSERT INTO `fruit_comments` VALUES (187, 1, 4, 1, 1, '个大多汁;酸甜可口;有坏果', '', '20200117154510', 196);
INSERT INTO `fruit_comments` VALUES (188, 1, 4, 1, 3, '个大多汁', '', '20200117160439', 197);
INSERT INTO `fruit_comments` VALUES (189, 1, 9, 5, 4, '包装很好;汁多甜;物流慢', NULL, '20200118155718', 163);
INSERT INTO `fruit_comments` VALUES (190, 1, 12, 5, 5, '新鲜;颜值好评', '', '20200118170925', 222);
INSERT INTO `fruit_comments` VALUES (191, 1, 12, 1, 1, '包装很好', '', '20200119141103', 225);
INSERT INTO `fruit_comments` VALUES (192, 1, 9, 5, 5, '包装很好;物流慢', NULL, '20200119141409', 158);
INSERT INTO `fruit_comments` VALUES (193, 6, 11, 5, 4, '有坏果;非常好吃;颜值好评;回头客;大小很均匀', '', '20200119142728', 231);
INSERT INTO `fruit_comments` VALUES (194, 1, 4, 1, 2, '个大多汁;酸甜可口;颜值好评', '', '20200119145528', 223);

-- ----------------------------
-- Table structure for membership_benefits
-- ----------------------------
DROP TABLE IF EXISTS `membership_benefits`;
CREATE TABLE `membership_benefits`  (
  `id` int(15) NOT NULL AUTO_INCREMENT,
  `fruit_id` int(15) DEFAULT NULL COMMENT '水果ID',
  `surplus_num` int(15) DEFAULT NULL COMMENT '剩余数量',
  `today_surplus_num` int(15) DEFAULT 10 COMMENT '今日剩余数量',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of membership_benefits
-- ----------------------------
INSERT INTO `membership_benefits` VALUES (1, 1, 10, 10);
INSERT INTO `membership_benefits` VALUES (2, 2, 100, 10);
INSERT INTO `membership_benefits` VALUES (4, 3, 12, 10);
INSERT INTO `membership_benefits` VALUES (5, 4, 12, 10);

-- ----------------------------
-- Table structure for order_info
-- ----------------------------
DROP TABLE IF EXISTS `order_info`;
CREATE TABLE `order_info`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `order_no` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '订单单号',
  `shop_id` int(36) DEFAULT NULL COMMENT '商店编号',
  `fruit_count` int(11) DEFAULT NULL COMMENT '水果数量',
  `fruit_amount_total` decimal(10, 2) DEFAULT NULL COMMENT '水果总价',
  `order_status` int(4) DEFAULT NULL COMMENT '订单状态 待付款0,待发货1,待收货2，待评价3，已评价4，申请退款5，已退款6，付款失败-1',
  `create_time` varchar(15) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '订单创建时间',
  `create_user` int(15) DEFAULT NULL COMMENT '创建用户',
  `fruit_id` int(11) DEFAULT NULL COMMENT '水果ID',
  `body` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '对一笔交易的具体描述信息。',
  `fruit_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '商品名称',
  `courier_number` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '快递单号',
  `logistics_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '物流名称',
  `address_id` int(15) DEFAULT NULL COMMENT '用户地址ID',
  `pay_type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '支付类型，alipay-支付宝支付,weixin-微信支付',
  `pay_amount` decimal(10, 2) DEFAULT NULL COMMENT '实付金额',
  `order_time` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '订单下单时间',
  `pay_time` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '订单支付时间',
  `delivery_time` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '发货时间',
  `confirm_receiving_time` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '收货时间',
  `outer_trade_no` varchar(48) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '交易订单号，比如支付宝给我平台的订单号',
  `order_info_type` int(1) DEFAULT 0 COMMENT '订单类型 0-普通订单/1-助力订单',
  `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '收货人姓名',
  `phone` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '收货人手机号',
  `address` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '收货人详细地址',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 249 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of order_info
-- ----------------------------
INSERT INTO `order_info` VALUES (7, 'fruit:1:9:f6f0e29f-6de2-4b2b-hgdfh6945ff1239', 1, 1, 15.00, 4, '20200108143501', 9, 1, '超级果蔬--爱媛果冻橙', '爱媛果冻橙', NULL, NULL, 44, 'weixin', NULL, '20200108143501', NULL, '20200114162055', '20200116155511', 'fruit7a41b061-4ec8-49dd-eqwerc994f366d', 0, 'GUI', '18500974633', ' 北京市 东城区 东华门街道 Dddddd');
INSERT INTO `order_info` VALUES (8, 'fruit:1:9:f6f0e76f-6sdfgsdb-93af-7c6945ff1239', 1, 1, 15.00, 7, '20200108143501', 9, 1, '超级果蔬--爱媛果冻橙', '爱媛果冻橙', NULL, NULL, 44, 'weixin', NULL, '20200108143501', NULL, NULL, '20200108155335', 'fruit7a41b061-4ec8-49ddytrhfsdc994f366d', 0, 'GUI', '18500974633', ' 北京市 东城区 东华门街道 Dddddd');
INSERT INTO `order_info` VALUES (9, 'fruit:1:9:f6f0ejhgk-6de2-4b2b-93af-7c6945ff1239', 1, 1, 15.00, 4, '20200108143501', 9, 1, '超级果蔬--爱媛果冻橙', '爱媛果冻橙', NULL, NULL, 44, 'weixin', NULL, '20200108143501', NULL, NULL, NULL, 'fruit7a41b061-4ec8-49dd-b2tryjryt994f366d', 0, 'GUI', '18500974633', ' 北京市 东城区 东华门街道 Dddddd');
INSERT INTO `order_info` VALUES (10, 'fruit:1:9:f6f0.kjghf-6de2-4b2b-93af-7c6945ff1239', 1, 1, 15.00, 4, '20200108143501', 9, 1, '超级果蔬--爱媛果冻橙', '爱媛果冻橙', NULL, NULL, 44, 'weixin', NULL, '20200108143501', NULL, NULL, NULL, 'fruit7a41b061-4ecytjyrew-b24e-0e1c994f366d', 0, 'GUI', '18500974633', ' 北京市 东城区 东华门街道 Dddddd');
INSERT INTO `order_info` VALUES (11, 'fruit:1:9:f6f0e29f-6de2-erwteerwaf-7c6945ff1239', 1, 1, 15.00, 5, '20200108143501', 9, 1, '超级果蔬--爱媛果冻橙', '爱媛果冻橙', NULL, NULL, 44, 'weixin', NULL, '20200108143501', NULL, NULL, NULL, 'fruit7a41b061-4ewersafdadsb24e-0e1c994f366d', 0, 'GUI', '18500974633', ' 北京市 东城区 东华门街道 Dddddd');
INSERT INTO `order_info` VALUES (12, 'fruit:1:9:f6f0e29f-6ddfghsfsghdf-93af-7c6945ff1239', 1, 1, 15.00, 6, '20200108143501', 9, 1, '超级果蔬--爱媛果冻橙', '爱媛果冻橙', NULL, NULL, 44, 'weixin', NULL, '20200108143501', NULL, NULL, NULL, 'fruit7a41b061-4ecwertwertwerb24e-0e1c994f366d', 0, 'GUI', '18500974633', ' 北京市 东城区 东华门街道 Dddddd');
INSERT INTO `order_info` VALUES (13, 'fruit:1:9:f6f0e29f-6retywtfsfdzfg3af-7c6945ff1239', 1, 1, 15.00, 4, '20200108143501', 9, 1, '超级果蔬--爱媛果冻橙', '爱媛果冻橙', NULL, NULL, 44, 'weixin', NULL, '20200108143501', NULL, NULL, NULL, 'fruit7a41b061-4ec8gsdfg-0e1c994f366d', 0, 'GUI', '18500974633', ' 北京市 东城区 东华门街道 Dddddd');
INSERT INTO `order_info` VALUES (86, 'fruit:1:1:fa9a2a0f-5152-4793-a3b4-950725a84656', 1, 1, 15.00, 1, '20200113112755', 1, 1, '超级果蔬--爱媛果冻橙', '爱媛果冻橙', NULL, NULL, 28, 'weixin', 15.00, '20200113112755', '20200113120213', NULL, NULL, 'fruitd9d9ad7c1', 0, 'GUI', '18500974633', ' 北京市 市辖区 东城区 东华门街道 Eeeeeee');
INSERT INTO `order_info` VALUES (87, 'fruit:1:1:c92cf6d5-6554-4078-bbe7-78e597b70f93', 1, 1, 15.00, 1, '20200113114421', 1, 1, '超级果蔬--爱媛果冻橙', '爱媛果冻橙', NULL, NULL, 28, 'weixin', 15.00, '20200113114421', '20200113115832', NULL, NULL, 'fruit0d00ff96-890d-4d89-a8f7-4a8', 0, 'GUI', '18500974633', ' 北京市 市辖区 东城区 东华门街道 Eeeeeee');
INSERT INTO `order_info` VALUES (88, 'fruitList:1:1:4a99535b-7016-425a-9b9c-d8276f87e896', 1, 1, 15.00, 1, '20200113141750', 1, 1, '超级果蔬--爱媛果冻橙', '爱媛果冻橙', NULL, NULL, 28, 'weixin', 15.00, '20200113141750', '20200113141758', NULL, NULL, 'fruit5c611731-fce5-4baa-9302-563', 0, 'GUI', '18500974633', ' 北京市 市辖区 东城区 东华门街道 Eeeeeee');
INSERT INTO `order_info` VALUES (89, 'fruitList:2:1:18576771-62c8-4d4b-84c5-3d35644740bc', 2, 1, 11.50, 1, '20200113141750', 1, 2, '超级果蔬--烟台红富士', '烟台红富士', NULL, NULL, 28, 'weixin', 11.50, '20200113141750', '20200113141758', NULL, NULL, 'fruit5c611731-fce5-4baa-9302-563', 0, 'GUI', '18500974633', ' 北京市 市辖区 东城区 东华门街道 Eeeeeee');
INSERT INTO `order_info` VALUES (113, 'fruit:1:1:7eaffa26-8c25-4794-96b4-4bc893162510', 1, 2, 30.00, 1, '20200113164519', 1, 1, '超级果蔬--爱媛果冻橙', '爱媛果冻橙', NULL, NULL, 28, 'alipay', 30.00, '20200113164519', '20200113164844', NULL, NULL, 'fruit59cde6ae-4a34-4980-97cf-1262e79fcbdf', 0, 'GUI', '18500974633', ' 北京市 市辖区 东城区 东华门街道 Eeeeeee');
INSERT INTO `order_info` VALUES (119, 'fruit:1:4:29f2c1e6-32ab-4c99-966d-d9d7e36e5897', 1, 1, 15.00, 7, '20200113171610', 4, 1, '超级果蔬--爱媛果冻橙', '爱媛果冻橙', NULL, NULL, 67, 'weixin', 15.00, '20200113171610', '20200113171618', NULL, NULL, 'fruit7e6b5c63-bb7e-4f56-986d-500', 0, '轰轰轰', '15001098992', '北京 北京市 东城区 好好考');
INSERT INTO `order_info` VALUES (120, 'fruit:2:4:e2780196-c616-40ca-9972-be84d1ab81ea', 2, 1, 11.50, 7, '20200113172001', 4, 2, '超级果蔬--烟台红富士', '烟台红富士', NULL, NULL, 67, 'weixin', 11.50, '20200113172001', '20200113172014', NULL, NULL, 'fruit6b435287-669a-4091-8cd0-cbc', 0, '轰轰轰', '15001098992', '北京 北京市 东城区 好好考');
INSERT INTO `order_info` VALUES (121, 'fruit:1:4:912f3fcc-b2fd-48d3-a6c4-00b8a3a3c2bd', 1, 1, 15.00, 5, '20200113172645', 4, 1, '超级果蔬--爱媛果冻橙', '爱媛果冻橙', NULL, NULL, 67, 'weixin', 15.00, '20200113172645', '20200113172655', NULL, NULL, 'fruit785205eb-b6d4-46b7-be57-184', 0, '轰轰轰', '15001098992', '北京 北京市 东城区 好好考');
INSERT INTO `order_info` VALUES (125, 'fruit:2:6:cbb39360-ce65-4881-9a32-6ed370e28ff2', 2, 1, 11.50, 1, '20200113200511', 6, 2, '超级果蔬--烟台红富士', '烟台红富士', NULL, NULL, 62, 'weixin', 11.50, '20200113200511', '20200113200519', NULL, NULL, 'fruitdc2a3f45-3b83-4156-b35a-04a', 0, '具体', '15778584455', '北京 北京市 东城区 看上去突然');
INSERT INTO `order_info` VALUES (126, 'fruit:1:6:f4168746-1a23-45ce-8a37-43b57367f722', 1, 1, 15.00, 2, '20200113200556', 6, 1, '超级果蔬--爱媛果冻橙', '爱媛果冻橙', NULL, NULL, 62, 'weixin', 15.00, '20200113200556', '20200113200608', '20200117142607', NULL, 'fruit9b662452-6000-4ebf-b5aa-7b1', 0, '具体', '15778584455', '北京 北京市 东城区 看上去突然');
INSERT INTO `order_info` VALUES (127, 'fruit:1:6:1dea90d5-5bc4-4d48-9156-7401e6e02517', 1, 1, 15.00, 2, '20200113200704', 6, 1, '超级果蔬--爱媛果冻橙', '爱媛果冻橙', NULL, NULL, 62, 'alipay', 15.00, '20200113200704', '20200113201004', '20200117142611', NULL, 'fruitdebac049-c38c-40a0-bcef-5b1aeab4a067', 0, '具体', '15778584455', '北京 北京市 东城区 看上去突然');
INSERT INTO `order_info` VALUES (130, 'fruit:1:6:c9e73ead-7be6-47ce-8b76-ec51fe0f3bad', 1, 1, 15.00, 1, '20200114111220', 6, 1, '超级果蔬--爱媛果冻橙', '爱媛果冻橙', NULL, NULL, 62, 'alipay', 15.00, '20200114111220', '20200114111539', NULL, NULL, 'fruit4aee80b5-457d-4fba-8d12-9e17cd70efc1', 0, '具体', '15778584455', '北京 北京市 东城区 看上去突然');
INSERT INTO `order_info` VALUES (136, 'fruit:3:6:9ab1e0d8-fe0f-41e2-9a37-7fd762b5993d', 3, 1, 15.00, 1, '20200114113120', 6, 3, '超级果蔬--香蕉', '香蕉', NULL, NULL, 62, 'weixin', 15.00, '20200114113120', '20200114113128', NULL, NULL, 'fruit3383299e-4252-417a-842f-83e', 0, '具体', '15778584455', '北京 北京市 东城区 看上去突然');
INSERT INTO `order_info` VALUES (137, 'fruit:5:6:3ed65bcf-a155-4b15-ab38-9b26b9c3d38f', 2, 1, 1.00, 2, '20200114113235', 6, 5, '超级果蔬--12', '12', NULL, NULL, 62, 'weixin', 1.00, '20200114113235', '20200114113241', '20200117143154', NULL, 'fruit9ce46650-17a4-4811-bc38-2af', 0, '具体', '15778584455', '北京 北京市 东城区 看上去突然');
INSERT INTO `order_info` VALUES (138, 'fruit:5:6:52ef6614-aac9-4341-b63a-ef7680863201', 2, 1, 1.00, 2, '20200114113302', 6, 5, '超级果蔬--12', '12', NULL, NULL, 62, 'weixin', 1.00, '20200114113302', '20200114113313', '20200117143151', NULL, 'fruit396a4973-5932-412f-96a7-18a', 0, '具体', '15778584455', '北京 北京市 东城区 看上去突然');
INSERT INTO `order_info` VALUES (139, 'fruit:2:6:78b42f9f-e7ee-4707-883a-2bc88445add9', 2, 1, 11.50, 1, '20200114113511', 6, 2, '超级果蔬--烟台红富士', '烟台红富士', NULL, NULL, 62, 'alipay', 11.50, '20200114113511', '20200114113803', NULL, NULL, 'fruita81684fc-4c11-4757-a531-1d4922c38319', 0, '具体', '15778584455', '北京 北京市 东城区 看上去突然');
INSERT INTO `order_info` VALUES (144, 'fruit:5:6:d3d6b4ec-f9f1-4fb9-b25f-f7fe5c0fd0d2', 2, 1, 1.00, 1, '20200114140721', 6, 5, '超级果蔬--12', '12', NULL, NULL, 62, 'alipay', 1.00, '20200114140721', '20200114141035', NULL, NULL, 'fruit6e271c5e-0e4b-48fb-9dbe-5c30c7f723ac', 0, '具体', '15778584455', '北京 北京市 东城区 看上去突然');
INSERT INTO `order_info` VALUES (148, 'fruit:3:4:9139c153-a177-47e6-8f4e-f32d63e6472a', 3, 1, 15.00, 5, '20200114141639', 4, 3, '超级果蔬--香蕉', '香蕉', NULL, NULL, 67, 'alipay', 15.00, '20200114141639', '20200114141937', NULL, NULL, 'fruitd49c4dfc-4aec-43cd-8766-3bd929904144', 0, '轰轰轰', '15001098992', '北京 北京市 东城区 好好考');
INSERT INTO `order_info` VALUES (149, 'fruit:5:4:e9430ae2-6e50-4c4d-8877-42aa5d0e3438', 2, 1, 1.00, 4, '20200114142042', 4, 5, '超级果蔬--12', '12', NULL, NULL, 67, 'alipay', 1.00, '20200114142042', '20200114142329', '20200117143205', '20200117143242', 'fruit8bf5e497-de4f-4812-bdcf-f009d6d60464', 0, '轰轰轰', '15001098992', '北京 北京市 东城区 好好考');
INSERT INTO `order_info` VALUES (150, 'fruit:3:4:b892f3dd-1028-495f-b865-2af02a579ea6', 3, 2, 30.00, 4, '20200114184111', 4, 3, '超级果蔬--香蕉', '香蕉', NULL, NULL, 67, 'weixin', 30.00, '20200114184111', '20200114184122', '20200117142602', '20200117142626', 'fruit44dcbadc-c93a-4dc6-a5c2-5bf', 0, '轰轰轰', '15001098992', '北京 北京市 东城区 好好考');
INSERT INTO `order_info` VALUES (156, 'fruit:3:9:648f2c37-97b6-4054-9081-8243eeccb40a', 3, 1, 15.00, 5, '20200115133351', 9, 3, '超级果蔬--香蕉', '香蕉', NULL, NULL, 44, 'alipay', 15.00, '20200115133351', '20200115133721', NULL, NULL, 'fruit6ba4963b-5fa7-449b-8c35-a23ba3ec23b4', 0, 'GUI', '18500974633', ' 北京市 东城区 东华门街道 Dddddd');
INSERT INTO `order_info` VALUES (157, 'fruit:1:9:ce11a7df-ccb8-4f01-b4fd-da6ccca21067', 1, 1, 15.00, 5, '20200115133805', 9, 1, '超级果蔬--爱媛果冻橙', '爱媛果冻橙', NULL, NULL, 44, 'weixin', 15.00, '20200115133805', '20200115134618', NULL, NULL, 'fruit6cc1e263-1709-4a7a-ac7d-137', 0, 'GUI', '18500974633', ' 北京市 东城区 东华门街道 Dddddd');
INSERT INTO `order_info` VALUES (158, 'fruit:1:9:98f5de2f-916f-4885-a818-e6ed472dae44', 1, 1, 15.00, 4, '20200115133812', 9, 1, '超级果蔬--爱媛果冻橙', '爱媛果冻橙', NULL, NULL, 44, 'alipay', 15.00, '20200115133812', '20200115134116', '20200117143146', '20200117152425', 'fruitc6e7be28-dee2-4871-9056-a7038784cef3', 0, 'GUI', '18500974633', ' 北京市 东城区 东华门街道 Dddddd');
INSERT INTO `order_info` VALUES (161, 'fruit:1:9:f3ce644e-ea10-4a12-8707-5b93a75c0e07', 1, 1, 15.00, 6, '20200115135136', 9, 1, '超级果蔬--爱媛果冻橙', '爱媛果冻橙', NULL, NULL, 44, 'alipay', 15.00, '20200115135136', '20200115135844', '20200117142600', '20200118140045', 'fruit23e8807d-88b6-465c-adaf-4b098257b828', 0, 'GUI', '18500974633', ' 北京市 东城区 东华门街道 Dddddd');
INSERT INTO `order_info` VALUES (162, 'fruit:1:9:819494e3-14ba-43d6-b6c7-079d9b425678', 1, 1, 15.00, 7, '20200115135458', 9, 1, '超级果蔬--爱媛果冻橙', '爱媛果冻橙', NULL, NULL, 44, 'alipay', 15.00, '20200115135458', '20200115140803', '20200117142556', '20200118140021', 'fruit11e0d882-ddfb-489e-b713-4f0a8fc75014', 0, 'GUI', '18500974633', ' 北京市 东城区 东华门街道 Dddddd');
INSERT INTO `order_info` VALUES (163, 'fruit:1:9:290fbbc1-ee31-4a73-acbc-1c4205b1a12e', 1, 1, 15.00, 4, '20200115140734', 9, 1, '超级果蔬--爱媛果冻橙', '爱媛果冻橙', NULL, NULL, 44, 'alipay', 15.00, '20200115140734', '20200115140743', '20200117142141', '20200118153443', 'fruit70fac23c-ad9d-4c46-85b3-94c5e464d29c', 0, 'GUI', '18500974633', ' 北京市 东城区 东华门街道 Dddddd');
INSERT INTO `order_info` VALUES (167, 'fruit:2:5:58f88d3f-a446-42e2-b4ad-daa031347ea7', 2, 1, 11.50, 5, '20200115150406', 5, 2, '超级果蔬--烟台红富士', '烟台红富士', NULL, NULL, 36, 'weixin', 11.50, '20200115150406', '20200115150415', NULL, NULL, 'fruitdadbbb82-38dd-480f-8923-f8a', 0, '张先生', '13457897968', '北京 北京市 朝阳区 无所天蝎座');
INSERT INTO `order_info` VALUES (171, 'fruit:1:5:38790e1c-37b5-48bf-9054-2b37f37bcd77', 1, 1, 15.00, 5, '20200115181417', 5, 1, '超级果蔬--爱媛果冻橙', '爱媛果冻橙', NULL, NULL, 36, 'weixin', 15.00, '20200115181417', '20200115181426', NULL, NULL, 'fruit10798a17-92f9-4f9a-9ca4-6b9', 0, '张先生', '13457897968', '北京 北京市 朝阳区 无所天蝎座');
INSERT INTO `order_info` VALUES (174, 'fruit:1:4:c10e7a66-59ba-4c30-ae1b-d0f419da961a', 1, 1, 15.00, 4, '20200115194109', 4, 1, '超级果蔬--爱媛果冻橙', '爱媛果冻橙', NULL, NULL, 67, 'alipay', 15.00, '20200115194109', '20200115195007', '20200117142138', '20200117142149', 'fruit84711af8-d3fc-409c-a689-7480e552ed47', 0, '轰轰轰', '15001098992', '北京 北京市 东城区 好好考');
INSERT INTO `order_info` VALUES (177, 'fruit:1:4:42e274bc-cd31-4ecf-a606-18632fa8c5dc', 1, 1, 15.00, 4, '20200115200633', 4, 1, '超级果蔬--爱媛果冻橙', '爱媛果冻橙', NULL, NULL, 67, 'weixin', 15.00, '20200115200633', '20200115201107', '20200117103134', '20200117111746', 'fruit3f19f650-6339-477a-b3ff-cf0', 0, '轰轰轰', '15001098992', '北京 北京市 东城区 好好考');
INSERT INTO `order_info` VALUES (178, 'fruit:1:4:69f702b5-e50c-42d9-ac6c-d6a2bd833a0d', 1, 1, 15.00, 4, '20200115202807', 4, 1, '超级果蔬--爱媛果冻橙', '爱媛果冻橙', NULL, NULL, 67, 'weixin', 15.00, '20200115202807', '20200115202830', '20200116160142', '20200117111927', 'fruit2c3ff52b-bc14-40b0-9076-fd5', 0, '轰轰轰', '15001098992', '北京 北京市 东城区 好好考');
INSERT INTO `order_info` VALUES (183, 'fruitList:1:9:cd2c1578-72f5-4de6-b95d-170f737d3058', 1, 2, 30.00, 4, '20200116152247', 9, 1, '超级果蔬--爱媛果冻橙', '爱媛果冻橙', NULL, NULL, 44, 'weixin', 30.00, '20200116152247', '20200116152256', '20200116152449', '20200116152518', 'fruit20ccc95b-66f8-4982-8627-640', 0, 'GUI', '18500974633', ' 北京市 东城区 东华门街道 Dddddd');
INSERT INTO `order_info` VALUES (184, 'fruitList:2:9:684db77c-9182-4a01-9e30-d453d95c74ce', 2, 1, 11.50, 4, '20200116152247', 9, 2, '超级果蔬--烟台红富士', '烟台红富士', NULL, NULL, 44, 'weixin', 11.50, '20200116152247', '20200116152256', '20200116152457', '20200116152528', 'fruit20ccc95b-66f8-4982-8627-640', 0, 'GUI', '18500974633', ' 北京市 东城区 东华门街道 Dddddd');
INSERT INTO `order_info` VALUES (188, 'fruitList:1:2:88fcecdc-82e7-42db-a703-8412369606c9', 1, 1, 0.01, 4, '20200117130439', 2, 1, '超级果蔬--爱媛果冻橙', '爱媛果冻橙', NULL, NULL, 70, 'alipay', 0.01, '20200117130439', '20200117130453', '20200117141428', '20200117141444', 'fruit81ff8405-0d24-4160-8384-40446de55ea9', 0, '星有万种', '18410716982', ' 北京市 房山区 拱辰街道 咯娜姆Rom拖');
INSERT INTO `order_info` VALUES (189, 'fruitList:2:2:290c94cb-57c5-49d2-93c3-c7daa5d68114', 2, 2, 0.01, 4, '20200117130439', 2, 2, '超级果蔬--烟台红富士', '烟台红富士', NULL, NULL, 70, 'alipay', 0.01, '20200117130439', '20200117130453', '20200117141433', '20200117141446', 'fruit81ff8405-0d24-4160-8384-40446de55ea9', 0, '星有万种', '18410716982', ' 北京市 房山区 拱辰街道 咯娜姆Rom拖');
INSERT INTO `order_info` VALUES (193, 'fruit:3:4:66370d05-7f2b-4ae5-aaba-dc67904042ed', 3, 2, 0.01, 4, '20200117143806', 4, 3, '超级果蔬--香蕉', '香蕉', NULL, NULL, 67, 'weixin', 0.01, '20200117143806', '20200117143813', '20200117143842', '20200117143856', 'fruit6807e1a4-62af-4afd-8940-39e', 0, '轰轰轰', '15001098992', '北京 北京市 东城区 好好考');
INSERT INTO `order_info` VALUES (194, 'fruit:5:4:714c6c4c-1587-49ce-bef7-4261782114c1', 2, 1, 0.01, 4, '20200117145321', 4, 5, '超级果蔬--12', '12', NULL, NULL, 67, 'weixin', 0.01, '20200117145321', '20200117145329', '20200117145346', '20200117145357', 'fruit8b92441f-2a65-4e7a-ad83-56d', 0, '轰轰轰', '15001098992', '北京 北京市 东城区 好好考');
INSERT INTO `order_info` VALUES (196, 'fruit:1:4:115dcb11-54a0-407e-825e-78eb00617903', 1, 1, 0.01, 4, '20200117154220', 4, 1, '超级果蔬--爱媛果冻橙', '爱媛果冻橙', NULL, NULL, 67, 'weixin', 0.01, '20200117154220', '20200117154341', '20200117154451', '20200117154458', 'fruit30ca94ac-0b4b-4b35-b989-fa1', 0, '轰轰轰', '15001098992', '北京 北京市 东城区 好好考');
INSERT INTO `order_info` VALUES (197, 'fruit:1:4:56618a9d-a7e7-46bf-b669-bc9709e4feb2', 1, 1, 0.01, 4, '20200117160314', 4, 1, '超级果蔬--爱媛果冻橙', '爱媛果冻橙', NULL, NULL, 67, 'weixin', 0.01, '20200117160314', '20200117160320', '20200117160401', '20200117160410', 'fruit4b265ab0-9065-4905-bec4-2ff', 0, '轰轰轰', '15001098992', '北京 北京市 东城区 好好考');
INSERT INTO `order_info` VALUES (198, 'fruit:3:9:4f0cd059-a7bb-4f01-bf6b-098cdc7756fe', 3, 1, 0.01, 7, '20200117161841', 9, 3, '超级果蔬--香蕉', '香蕉', NULL, NULL, 44, 'weixin', 0.01, '20200117161841', '20200117161848', NULL, NULL, 'fruit7c03136c-54fd-4c9a-a1ea-c7b', 0, 'GUI', '18500974633', ' 北京市 东城区 东华门街道 Dddddd');
INSERT INTO `order_info` VALUES (199, 'fruit:1:4:1222962d-f8a5-471e-9e9e-3883b6966123', 1, 1, 0.01, 6, '20200117171830', 4, 1, '超级果蔬--爱媛果冻橙', '爱媛果冻橙', NULL, NULL, 67, 'weixin', 0.01, '20200117171830', '20200117171914', '20200118165521', '20200118165531', 'fruitd796966e-fd68-4fa0-bed7-290', 0, '轰轰轰', '15001098992', '北京 北京市 东城区 好好考');
INSERT INTO `order_info` VALUES (210, 'c860403f-bdd3-4349-9ced-d6ca50a1ab74', 1, 1, 0.00, 1, '20200118151719', 2, 1, '助力已满5人，助力成功！！！', '爱媛果冻橙', NULL, NULL, 70, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 1, '星有万种', '18410716982', ' 北京市 房山区 拱辰街道 咯娜姆Rom拖');
INSERT INTO `order_info` VALUES (211, 'fruit:1:11:9a90d491-d997-4d85-8338-c959a3505120', 1, 1, 0.01, 7, '20200118152542', 11, 1, '超级果蔬--爱媛果冻橙', '爱媛果冻橙', NULL, NULL, 74, 'weixin', 0.01, '20200118152542', '20200118152552', '20200118152610', '20200118152621', 'fruit73462410-3ea7-40ce-887e-60d', 0, '比堪培拉', '15213544846', '北京 北京市 东城区 接回来茉莉');
INSERT INTO `order_info` VALUES (212, 'fruit:1:11:bfaaea01-8381-4daa-8557-35abd33558c1', 1, 1, 0.01, 8, '20200118153420', 11, 1, '超级果蔬--爱媛果冻橙', '爱媛果冻橙', NULL, NULL, 74, 'weixin', 0.01, '20200118153420', '20200118153429', '20200119141656', '20200119141705', 'fruit0f5ceb92-4daf-4807-a291-771', 0, '比堪培拉', '15213544846', '北京 北京市 东城区 接回来茉莉');
INSERT INTO `order_info` VALUES (217, 'fruit:1:19:d3f35348-c274-442f-b306-94f5c16c51ce', 1, 1, 0.01, 7, '20200118155612', 19, 1, '超级果蔬--爱媛果冻橙', '爱媛果冻橙', NULL, NULL, 78, 'weixin', 0.01, '20200118155612', '20200118155621', NULL, NULL, 'fruite65d9575-658f-4158-b671-45e', 0, '野驴', '13264255201', ' 北京市 丰台区 其他 新发地3号楼');
INSERT INTO `order_info` VALUES (218, 'fruit:1:19:802e2bc6-cf56-4b9b-af17-05593bb3c24f', 1, 1, 0.01, 7, '20200118160848', 19, 1, '超级果蔬--爱媛果冻橙', '爱媛果冻橙', NULL, NULL, 80, 'weixin', 0.01, '20200118160848', '20200118160854', NULL, NULL, 'fruit9b23b3e2-d27d-458a-9f81-de0', 0, '野驴', '13264255201', ' 北京市 东城区 东华门街道 小胡同3号');
INSERT INTO `order_info` VALUES (220, 'fruit:1:5:b5ca34e9-72f4-4051-932f-d9f81c4f066f', 1, 1, 0.01, 1, '20200118163252', 5, 1, '超级果蔬--爱媛果冻橙', '爱媛果冻橙', NULL, NULL, 36, 'weixin', 0.01, '20200118163252', '20200118163328', NULL, NULL, 'fruit60bf45a5-979a-4dae-a4e4-b56', 0, '张先生', '13457897968', '北京 北京市 朝阳区 无所天蝎座');
INSERT INTO `order_info` VALUES (222, 'fruit:1:12:d09d2353-4cda-4bee-ac10-f1c14c6c3568', 1, 1, 0.01, 4, '20200118170152', 12, 1, '超级果蔬--爱媛果冻橙', '爱媛果冻橙', NULL, NULL, 75, 'weixin', 0.01, '20200118170152', '20200118170205', '20200118170230', '20200118170836', 'fruit0eb6e84f-4f55-4214-aab6-dfd', 0, '123', '15081613711', '北京 北京市 海淀区 123');
INSERT INTO `order_info` VALUES (223, 'fruit:1:4:247fa6ec-70dc-4b40-82bd-ac72d7980ad9', 1, 1, 0.01, 4, '20200118170613', 4, 1, '超级果蔬--爱媛果冻橙', '爱媛果冻橙', NULL, NULL, 67, 'alipay', 0.01, '20200118170613', '20200118170623', '20200118170701', '20200118170717', 'fruit9c12d39e-9b10-4938-b116-ad8e35f2ef10', 0, '轰轰轰', '15001098992', '北京 北京市 东城区 好好考');
INSERT INTO `order_info` VALUES (224, 'fruit:5:4:1ed5c8c2-5fe2-4083-90c6-b1b3d9fc18f3', 2, 1, 0.01, 1, '20200118170847', 4, 5, '超级果蔬--12', '12', NULL, NULL, 67, 'weixin', 0.01, '20200118170847', '20200118170856', NULL, NULL, 'fruit4ee8ccd7-7f87-4aeb-9661-37a', 0, '轰轰轰', '15001098992', '北京 北京市 东城区 好好考');
INSERT INTO `order_info` VALUES (225, 'fruit:1:12:a79d6ef3-6f85-4d52-b82f-884c0b69ed98', 1, 1, 0.01, 4, '20200119140939', 12, 1, '超级果蔬--爱媛果冻橙', '爱媛果冻橙', NULL, NULL, 81, 'weixin', 0.01, '20200119140939', '20200119140948', '20200119141015', '20200119141042', 'fruit98910af4-a716-43ea-9ea8-bbb', 0, '赵艳彤', '11011011011', '北京 北京市 机场生活区 阿斯顿');
INSERT INTO `order_info` VALUES (227, 'fruit:1:12:0230855d-56db-451f-b202-3db254b0a029', 1, 1, 0.01, 7, '20200119141144', 12, 1, '超级果蔬--爱媛果冻橙', '爱媛果冻橙', NULL, NULL, 75, 'weixin', 0.01, '20200119141144', '20200119141151', NULL, NULL, 'fruitcb19db47-878d-45dc-8e8d-340', 0, '123', '15081613711', '北京 北京市 海淀区 123');
INSERT INTO `order_info` VALUES (229, 'fruit:1:11:a2dabdc1-7573-4ee8-b51a-69dc1ddcc513', 1, 2, 0.01, 7, '20200119142047', 11, 1, '超级果蔬--爱媛果冻橙', '爱媛果冻橙', NULL, NULL, 74, 'weixin', 0.01, '20200119142047', '20200119142057', NULL, NULL, 'fruitef8ee7d2-f35f-41c2-a9df-d03', 0, '比堪培拉', '15213544846', '北京 北京市 东城区 接回来茉莉');
INSERT INTO `order_info` VALUES (230, 'fruit:3:11:4c75813c-bf9d-44b8-99a7-b2b3718ec2f5', 3, 1, 0.01, 7, '20200119142244', 11, 3, '超级果蔬--香蕉', '香蕉', NULL, NULL, 74, 'alipay', 0.01, '20200119142244', '20200119142256', '20200119142309', '20200119142329', 'fruit2b32e002-2d8b-4a0f-b0c5-8a46ffa23015', 0, '比堪培拉', '15213544846', '北京 北京市 东城区 接回来茉莉');
INSERT INTO `order_info` VALUES (231, 'fruit:6:11:508c1e42-04ae-4b17-8ad4-70eafe26da7e', 1, 1, 0.01, 4, '20200119142634', 11, 6, '超级果蔬--123', '123', NULL, NULL, 74, 'weixin', 0.01, '20200119142634', '20200119142655', '20200119142705', '20200119142710', 'fruit9f59ce72-31e0-43c1-a96f-f22', 0, '比堪培拉', '15213544846', '北京 北京市 东城区 接回来茉莉');
INSERT INTO `order_info` VALUES (232, 'fruit:1:5:d98df907-71cb-4a9d-853a-36edc2507e91', 1, 1, 0.01, 1, '20200119143148', 5, 1, '超级果蔬--爱媛果冻橙', '爱媛果冻橙', NULL, NULL, 36, 'weixin', 0.01, '20200119143148', '20200119143158', NULL, NULL, 'fruit2d13aaa9-48b5-440c-87cd-534', 0, '张先生', '13457897968', '北京 北京市 朝阳区 无所天蝎座');
INSERT INTO `order_info` VALUES (233, 'fruit:1:11:c3b45d5a-6beb-435f-b32e-81feea096f73', 1, 1, 0.01, 5, '20200119143620', 11, 1, '超级果蔬--爱媛果冻橙', '爱媛果冻橙', NULL, NULL, 74, 'weixin', 0.01, '20200119143620', '20200119143628', NULL, NULL, 'fruit2ad6d780-7fa6-43a0-b7c7-638', 0, '比堪培拉', '15213544846', '北京 北京市 东城区 接回来茉莉');
INSERT INTO `order_info` VALUES (235, 'fruit:2:4:8d5fb13a-aaf5-4cb6-901e-dd64efb6d898', 2, 1, 0.01, 7, '20200119145621', 4, 2, '超级果蔬--烟台红富士', '烟台红富士', NULL, NULL, 67, 'alipay', 0.01, '20200119145621', '20200119145627', NULL, NULL, 'fruit295746fa-0160-46e3-8d8f-45b6dbd37748', 0, '轰轰轰', '15001098992', '北京 北京市 东城区 好好考');
INSERT INTO `order_info` VALUES (236, 'fruit:6:5:424fe323-a02a-4d69-b9e9-2db9aaba3163', 1, 1, 0.01, 5, '20200119150157', 5, 6, '超级果蔬--123', '123', NULL, NULL, 36, 'weixin', 0.01, '20200119150157', '20200119150209', NULL, NULL, 'fruit49ac390c-0482-435d-a2fb-ab6', 0, '张先生', '13457897968', '北京 北京市 朝阳区 无所天蝎座');
INSERT INTO `order_info` VALUES (237, 'fruit:1:4:5e9e24fe-8f45-4cb0-bde7-c8ea1cd1f5d4', 1, 1, 0.01, 1, '20200119150811', 4, 1, '超级果蔬--爱媛果冻橙', '爱媛果冻橙', NULL, NULL, 67, 'alipay', 0.01, '20200119150811', '20200119150818', NULL, NULL, 'fruit267aefd5-2d93-436d-859b-32a4b43de400', 0, '轰轰轰', '15001098992', '北京 北京市 东城区 好好考');
INSERT INTO `order_info` VALUES (238, 'fruit:1:4:b4e43014-e74e-4306-8275-b6e7197459db', 1, 1, 0.01, 1, '20200119151449', 4, 1, '超级果蔬--爱媛果冻橙', '爱媛果冻橙', NULL, NULL, 67, 'weixin', 0.01, '20200119151449', '20200119151518', NULL, NULL, 'fruitbfc8465b-5d93-4b2e-9dda-fee', 0, '轰轰轰', '15001098992', '北京 北京市 东城区 好好考');
INSERT INTO `order_info` VALUES (243, 'fruit:6:4:e6081f9f-9d5b-4100-b121-593d43dfb1d9', 1, 1, 0.01, 1, '20200119153816', 4, 6, '超级果蔬--123', '123', NULL, NULL, 67, 'weixin', 0.01, '20200119153816', '20200119153826', NULL, NULL, 'fruitaa5aac3d-77b7-462d-9202-8ca', 0, '轰轰轰', '15001098992', '北京 北京市 东城区 好好考');
INSERT INTO `order_info` VALUES (244, 'fruit:1:4:af38c1ce-6d58-4abe-8c64-facb165d6fb7', 1, 1, 0.01, 1, '20200119154005', 4, 1, '超级果蔬--爱媛果冻橙', '爱媛果冻橙', NULL, NULL, 67, 'weixin', 0.01, '20200119154005', '20200119154011', NULL, NULL, 'fruitfb15c105-305f-4280-a37d-bc9', 0, '轰轰轰', '15001098992', '北京 北京市 东城区 好好考');
INSERT INTO `order_info` VALUES (245, 'fruit:6:4:148d4d0a-7c0b-4637-8587-c686188849ae', 1, 1, 0.01, 1, '20200119154053', 4, 6, '超级果蔬--123', '123', NULL, NULL, 67, 'alipay', 0.01, '20200119154053', '20200119154058', NULL, NULL, 'fruit62fab721-1776-41df-8556-b9c7f7e5f4df', 0, '轰轰轰', '15001098992', '北京 北京市 东城区 好好考');
INSERT INTO `order_info` VALUES (247, 'fruit:1:4:785ada9c-d2a5-4886-bbaf-9f09982af983', 1, 2, 0.01, 1, '20200119154448', 4, 1, '超级果蔬--爱媛果冻橙', '爱媛果冻橙', NULL, NULL, 67, 'weixin', 0.01, '20200119154448', '20200119154456', NULL, NULL, 'fruite7bdd329-5348-4388-8216-d96', 0, '轰轰轰', '15001098992', '北京 北京市 东城区 好好考');
INSERT INTO `order_info` VALUES (248, 'fruit:1:4:a6847632-6e84-4b80-8a6d-c143ed5b8b3a', 1, 1, 0.01, 1, '20200119154757', 4, 1, '超级果蔬--爱媛果冻橙', '爱媛果冻橙', NULL, NULL, 67, 'weixin', 0.01, '20200119154757', '20200119154805', NULL, NULL, 'fruit0e166eaa-e1e4-4f65-909f-5dc', 0, '轰轰轰', '15001098992', '北京 北京市 东城区 好好考');

-- ----------------------------
-- Table structure for order_refund
-- ----------------------------
DROP TABLE IF EXISTS `order_refund`;
CREATE TABLE `order_refund`  (
  `id` int(15) NOT NULL AUTO_INCREMENT,
  `order_no` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '订单编号',
  `out_refund_no` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '商户退款单号',
  `refund_fee` decimal(15, 2) DEFAULT NULL COMMENT '退款金额',
  `create_time` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '申请时间',
  `is_agree` int(1) DEFAULT NULL COMMENT '是否同意退款 0-拒绝 /1-同意',
  `fruit_status` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '货物状态',
  `refund_reason` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '退款原因',
  `refund_instruction` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '退款说明',
  `refund_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '退款凭证',
  `user_id` int(15) DEFAULT NULL COMMENT '用户ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 24 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of order_refund
-- ----------------------------
INSERT INTO `order_refund` VALUES (1, 'fruit:1:9:f6f0e76f-6sdfgsdb-93af-7c6945ff1239', 'refund44a3cbed-f09a-4cb5-8f78-8bd1d', 14.00, '20200110160650', 1, '未收到货', '快递/物流无跟踪记录', NULL, 'http://img.staticvip.cn/FjdG_I0S3r0kAIbNt0zpxBi9hYRp;http://img.staticvip.cn/Fs3ho1WVW7YQL0j0iopbdjTlxvGz', 9);
INSERT INTO `order_refund` VALUES (2, 'fruit:1:4:29f2c1e6-32ab-4c99-966d-d9d7e36e5897', 'refund661b2684-1abf-49db-8dea-fc962', 0.01, '20200114152254', 1, '1', '果子坏了', '难吃', NULL, 4);
INSERT INTO `order_refund` VALUES (3, 'fruit:2:4:e2780196-c616-40ca-9972-be84d1ab81ea', 'refund06996baa-504c-4acb-b0a4-1f552', 0.01, '20200114152428', 1, '1', '果子坏了', '难吃', NULL, 4);
INSERT INTO `order_refund` VALUES (4, 'fruit:1:4:912f3fcc-b2fd-48d3-a6c4-00b8a3a3c2bd', NULL, NULL, '20200115164408', NULL, '1', NULL, NULL, NULL, 4);
INSERT INTO `order_refund` VALUES (5, 'fruit:2:5:58f88d3f-a446-42e2-b4ad-daa031347ea7', NULL, 11.00, '20200115175705', NULL, NULL, NULL, '', 'http://img.staticvip.cn/icon_20200115175704', 5);
INSERT INTO `order_refund` VALUES (6, 'fruit:1:5:38790e1c-37b5-48bf-9054-2b37f37bcd77', NULL, 15.00, '20200115181514', NULL, NULL, NULL, '', '', 5);
INSERT INTO `order_refund` VALUES (7, 'fruit:3:4:9139c153-a177-47e6-8f4e-f32d63e6472a', NULL, 76.00, '20200115183453', NULL, NULL, '坏果、烂果', '或者坏了', '', 4);
INSERT INTO `order_refund` VALUES (8, 'fruit:3:9:648f2c37-97b6-4054-9081-8243eeccb40a', NULL, 15.00, '20200116150845', NULL, '未收到货', '发货超时', NULL, NULL, 9);
INSERT INTO `order_refund` VALUES (9, 'fruit:1:9:ce11a7df-ccb8-4f01-b4fd-da6ccca21067', NULL, 15.00, '20200116151325', NULL, '未收到货', '快递/物流无跟踪记录', NULL, 'http://img.staticvip.cn/Frgh3Xl4ip2O9LIW4o-80smAAe8d', 9);
INSERT INTO `order_refund` VALUES (10, 'fruit:1:9:f3ce644e-ea10-4a12-8707-5b93a75c0e07', NULL, 2.00, '20200118140256', NULL, '已收到货', '坏果、烂果', NULL, NULL, 9);
INSERT INTO `order_refund` VALUES (11, 'fruit:1:9:819494e3-14ba-43d6-b6c7-079d9b425678', 'refundbde5c6f7-6821-4b33-8730-8d0ff', 15.00, '20200118151612', 1, '已收到货', '质量问题', NULL, NULL, 9);
INSERT INTO `order_refund` VALUES (12, 'fruit:1:11:9a90d491-d997-4d85-8338-c959a3505120', 'refundb6f080cf-3ce0-438a-a05c-fe285', 0.01, '20200118152940', 1, NULL, '空包裹', '', 'http://img.staticvip.cn/icon_20200118152937', 11);
INSERT INTO `order_refund` VALUES (13, 'fruit:3:9:4f0cd059-a7bb-4f01-bf6b-098cdc7756fe', 'refunde3a4e93c-fb6a-47ed-bcbf-7594b', 0.01, '20200118154731', 1, '未收到货', '快递/物流无跟踪记录', NULL, NULL, 9);
INSERT INTO `order_refund` VALUES (14, 'fruit:1:19:d3f35348-c274-442f-b306-94f5c16c51ce', 'refund035ab98d-9c8d-4dc4-9e3b-5d0bf', 0.01, '20200118155807', 1, '未收到货', '其他', NULL, NULL, 19);
INSERT INTO `order_refund` VALUES (15, 'fruit:1:19:802e2bc6-cf56-4b9b-af17-05593bb3c24f', 'refund03329409-8072-4842-b923-5aca8', 0.01, '20200118160923', 1, '未收到货', '发货超时', NULL, NULL, 19);
INSERT INTO `order_refund` VALUES (16, 'fruit:1:4:1222962d-f8a5-471e-9e9e-3883b6966123', NULL, -0.01, '20200118165614', NULL, NULL, '质量问题', '挂了', 'http://img.staticvip.cn/icon_20200118165612', 4);
INSERT INTO `order_refund` VALUES (17, 'fruit:1:12:0230855d-56db-451f-b202-3db254b0a029', 'refund89e89ff4-fb02-4def-bdc9-d765d', 0.01, '20200119141241', 1, NULL, '不想要/不喜欢', '', '', 12);
INSERT INTO `order_refund` VALUES (18, 'fruit:1:11:bfaaea01-8381-4daa-8557-35abd33558c1', NULL, 0.01, '20200119141818', 0, NULL, '空包裹', NULL, 'http://img.staticvip.cn/FqeIThAbaq5ilRoxpa__SAhKv44S', 11);
INSERT INTO `order_refund` VALUES (19, 'fruit:1:11:a2dabdc1-7573-4ee8-b51a-69dc1ddcc513', 'refund745e0ef5-37ee-4d02-90f8-6e7a5', 0.01, '20200119142217', 1, NULL, '不想要/不喜欢', '', '', 11);
INSERT INTO `order_refund` VALUES (20, 'fruit:3:11:4c75813c-bf9d-44b8-99a7-b2b3718ec2f5', 'refund18c9396c-4d55-4767-8ab1-c8788', 0.00, '20200119142348', 1, NULL, '卖家发错货', '', 'http://img.staticvip.cn/icon_20200119142346', 11);
INSERT INTO `order_refund` VALUES (21, 'fruit:1:11:c3b45d5a-6beb-435f-b32e-81feea096f73', NULL, 0.01, '20200119143657', NULL, NULL, '不想要/不喜欢', '', '', 11);
INSERT INTO `order_refund` VALUES (22, 'fruit:2:4:8d5fb13a-aaf5-4cb6-901e-dd64efb6d898', 'refund3b7b1699-eee4-4998-83e3-36384', 0.01, '20200119145825', 1, '未收到货', '不想要/不喜欢', '呵呵呵', '', 4);
INSERT INTO `order_refund` VALUES (23, 'fruit:6:5:424fe323-a02a-4d69-b9e9-2db9aaba3163', NULL, 0.01, '20200119151544', NULL, NULL, NULL, '', '', 5);

-- ----------------------------
-- Table structure for sys_permissions
-- ----------------------------
DROP TABLE IF EXISTS `sys_permissions`;
CREATE TABLE `sys_permissions`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `url` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT 'url地址',
  `description` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT 'url描述',
  `name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '权限名称',
  `parent_id` int(100) DEFAULT NULL COMMENT '父节点ID',
  `available` tinyint(1) DEFAULT 0 COMMENT '是否锁定',
  `perm_type` int(1) DEFAULT NULL COMMENT '权限类型 1/菜单;2/按钮;3/接口',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `idx_sys_permissions_permission`(`url`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of sys_permissions
-- ----------------------------
INSERT INTO `sys_permissions` VALUES (1, '/系统管理', 'm:sys', '系统管理', NULL, 0, 1);
INSERT INTO `sys_permissions` VALUES (2, '/user/list', 'user:view', '用户管理', 1, 0, 1);
INSERT INTO `sys_permissions` VALUES (3, '/role/list', 'role:view', '角色管理', 1, 0, 1);
INSERT INTO `sys_permissions` VALUES (4, '/permission/list', 'permission:view', '权限管理', 1, 0, 1);
INSERT INTO `sys_permissions` VALUES (5, '', '*', '超级管理员', NULL, 0, NULL);

-- ----------------------------
-- Table structure for sys_roles
-- ----------------------------
DROP TABLE IF EXISTS `sys_roles`;
CREATE TABLE `sys_roles`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '角色编号',
  `role` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '角色名称',
  `description` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '角色描述',
  `available` tinyint(1) DEFAULT 0 COMMENT '是否锁定',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `idx_sys_roles_role`(`role`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of sys_roles
-- ----------------------------
INSERT INTO `sys_roles` VALUES (1, 'admin', '管理员', 0);
INSERT INTO `sys_roles` VALUES (2, 'shopAdmin', '商铺管理员', 0);
INSERT INTO `sys_roles` VALUES (3, 'test', '测试', 0);

-- ----------------------------
-- Table structure for sys_roles_permissions
-- ----------------------------
DROP TABLE IF EXISTS `sys_roles_permissions`;
CREATE TABLE `sys_roles_permissions`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `role_id` bigint(20) DEFAULT NULL COMMENT '角色编号',
  `permission_id` bigint(20) DEFAULT NULL COMMENT '权限编号',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of sys_roles_permissions
-- ----------------------------
INSERT INTO `sys_roles_permissions` VALUES (1, 1, 0);

-- ----------------------------
-- Table structure for sys_users
-- ----------------------------
DROP TABLE IF EXISTS `sys_users`;
CREATE TABLE `sys_users`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '昵称',
  `password` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '密码',
  `salt` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '盐值',
  `role_id` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '角色列表',
  `username` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '用户名',
  `locked` tinyint(1) DEFAULT 0 COMMENT '是否锁定',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `idx_sys_users_username`(`username`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of sys_users
-- ----------------------------
INSERT INTO `sys_users` VALUES (1, '管理员', '928bfd2577490322a6e19b793691467e', 'admin', '管理员', 'admin', 0);
INSERT INTO `sys_users` VALUES (2, '用户lc', '555a0cb48fa5473dd6f635c00457297e', 'admin_lc', NULL, 'admin_lc', 0);

-- ----------------------------
-- Table structure for sys_users_roles
-- ----------------------------
DROP TABLE IF EXISTS `sys_users_roles`;
CREATE TABLE `sys_users_roles`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `user_id` bigint(20) DEFAULT NULL COMMENT '用户编号',
  `role_id` bigint(20) DEFAULT NULL COMMENT '角色编号',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of sys_users_roles
-- ----------------------------
INSERT INTO `sys_users_roles` VALUES (1, 1, 1);
INSERT INTO `sys_users_roles` VALUES (3, 2, 2);
INSERT INTO `sys_users_roles` VALUES (4, 2, 3);

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` int(15) NOT NULL AUTO_INCREMENT,
  `account` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '账号',
  `role_id` int(10) DEFAULT 0 COMMENT '权限id',
  `vip_out_time` varchar(25) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT 'vip用户过期时间',
  `create_time` varchar(25) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建时间',
  `vip_time` varchar(25) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT 'vip用户充值时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 20 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, '18500974633', 1, '21011010152204', '20191210152204', '20191210152204');
INSERT INTO `user` VALUES (2, '18410716982', 1, '21000410152204', '20191211111904', '20191210152204');
INSERT INTO `user` VALUES (3, '18716028217', 1, '20991210152204', '20191211112959', '20191210152204');
INSERT INTO `user` VALUES (4, '15001098992', 1, '20991210152204', '20191211114027', '20191210152204');
INSERT INTO `user` VALUES (5, '13552184323', 1, '21000410152204', '20191214114520', '20191210152204');
INSERT INTO `user` VALUES (6, '17600685937', 1, '20991210152204', '20191218160044', '20191210152204');
INSERT INTO `user` VALUES (8, '15010197597', 1, '20991210152204', '20200102145132', '20191210152204');
INSERT INTO `user` VALUES (9, '18511694633', 1, '21010510152204', '20200106173949', '20191210152204');
INSERT INTO `user` VALUES (10, '15101070897', 0, NULL, '20200117101940', NULL);
INSERT INTO `user` VALUES (11, '15235134361', 1, '20200218150312', '20200118135724', '20200118150312');
INSERT INTO `user` VALUES (12, '15081613711', 0, NULL, '20200118143928', NULL);
INSERT INTO `user` VALUES (14, '15910673401', 0, NULL, '20200118152905', NULL);
INSERT INTO `user` VALUES (19, '13264255201', 0, NULL, '20200118155328', NULL);

-- ----------------------------
-- Table structure for user_address
-- ----------------------------
DROP TABLE IF EXISTS `user_address`;
CREATE TABLE `user_address`  (
  `id` int(15) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '收货人姓名',
  `phone` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '收货人手机号',
  `area` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '收货人所在地区',
  `address` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '收货人详细地址',
  `is_default` int(1) DEFAULT 0 COMMENT '是否默认0--否/1--是',
  `user_id` int(15) DEFAULT NULL COMMENT '用户ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 82 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of user_address
-- ----------------------------
INSERT INTO `user_address` VALUES (28, 'GUI', '18500974633', ' 北京市 市辖区 东城区 东华门街道', 'Eeeeeee', 1, 1);
INSERT INTO `user_address` VALUES (29, 'Tttt', '18500974633', ' 天津市 市辖区 河东区 大王庄街道', 'Refffff', 0, 1);
INSERT INTO `user_address` VALUES (36, '张先生', '13457897968', '北京 北京市 朝阳区', '无所天蝎座', 0, 5);
INSERT INTO `user_address` VALUES (37, '恶魔之眼', '13569486979', '北京 北京市 西城区', '哦梧桐细雨', 0, 5);
INSERT INTO `user_address` VALUES (44, '哈 v vs', '18500974633', ' 北京市 西城区 展览路街道', 'Dd是个傻过', 0, 9);
INSERT INTO `user_address` VALUES (56, '等一下', '13465889797', '北京 北京市 东城区', '您随心所欲', 0, 8);
INSERT INTO `user_address` VALUES (58, '李四', '13553948232', '北京 北京市 东城区', '北京 北京 东城区', 0, 8);
INSERT INTO `user_address` VALUES (59, '张三', '13553948232', ' ', '北京 北京 东城区', 0, 8);
INSERT INTO `user_address` VALUES (60, '定义', '13646497989', '北京 北京市 丰台区', '您随心所欲', 1, 8);
INSERT INTO `user_address` VALUES (62, '具体', '15778584455', '北京 北京市 东城区', '看上去突然', 1, 6);
INSERT INTO `user_address` VALUES (63, '旅途', '15888999888', '北京 北京市 东城区', '偷我图', 0, 6);
INSERT INTO `user_address` VALUES (67, '轰轰轰', '15001098992', '北京 北京市 东城区', '好好考', 0, 4);
INSERT INTO `user_address` VALUES (68, '小明', '17600685937', '北京 北京市 东城区', '你你你', 0, 4);
INSERT INTO `user_address` VALUES (69, '应用', '13546789557', '北京 北京市 崇文区', 'mins呜呜呜', 0, 10);
INSERT INTO `user_address` VALUES (70, '星有万种', '18410716982', ' 北京市 房山区 拱辰街道', '咯娜姆Rom拖', 0, 2);
INSERT INTO `user_address` VALUES (72, '嘎嘎 v', '15235143611', ' 北京市 东城区 景山街道', '上身哈巴狗', 1, 7);
INSERT INTO `user_address` VALUES (74, '比堪培拉', '15213544846', '北京 北京市 东城区', '接回来茉莉', 0, 11);
INSERT INTO `user_address` VALUES (75, '123', '15081613711', '北京 北京市 海淀区', '123', 0, 12);
INSERT INTO `user_address` VALUES (76, '哈哈 v说个', '15235424848', ' 北京市 朝阳区 香河园街道', '啥 v啊好爸爸 v哈吧', 1, 9);
INSERT INTO `user_address` VALUES (80, '野驴', '13264255201', ' 北京市 东城区 东华门街道', '小胡同3号', 0, 19);
INSERT INTO `user_address` VALUES (81, '赵艳彤', '11011011011', '北京 北京市 机场生活区', '阿斯顿', 1, 12);

-- ----------------------------
-- Table structure for user_auths
-- ----------------------------
DROP TABLE IF EXISTS `user_auths`;
CREATE TABLE `user_auths`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL COMMENT '用户ID',
  `unionid` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '唯一标识',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '第三方账号昵称',
  `gender` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '性别',
  `icon_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '头像地址',
  `third_type` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '三方登陆类型（wx:微信，qq:QQ登陆，wb:微博）',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 14 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of user_auths
-- ----------------------------
INSERT INTO `user_auths` VALUES (1, NULL, 'xxxxxx', '超音速', NULL, 'http://dlljdfdfjddjfsasafs', 'wx');
INSERT INTO `user_auths` VALUES (2, 5, 'oI89W6O7qkXZPGQ4cO7qD9VayU88', '超音速', NULL, 'http://thirdwx.qlogo.cn/mmopen/vi_32/DYAIOgq83ep13cBqJjaXtSDnibZzwbzEZuJBpgH54pibfFWEI74ApSPHKj6iaiahN9L9ZVFpmZRTaibwkt65H95xY6A/132', NULL);
INSERT INTO `user_auths` VALUES (3, 4, 'oI89W6LYxM_rCU3oiyaE7xNoxnhw', '心存善念²⁰²⁰', NULL, 'http://thirdwx.qlogo.cn/mmopen/vi_32/icBG4NmNfXzh5uRN1TNJp8JkRuCxAr6Bp28HnvnMkI3CcRa7icWA8maFHzcSybqpfiaa9tS8axiaItZsYaDWYv1nXQ/132', NULL);
INSERT INTO `user_auths` VALUES (4, 1, 'oa2TLs5n5CKfCBo2JUu76pxGw1Ww', '天使飞过的花园', '男', 'https://thirdwx.qlogo.cn/mmopen/vi_32/DYAIOgq83epZQAmWlv1pOYN0DzWFx1LqxRib6jYk8tYz5FWbgEjB0kvFhUYicjHFwdLP25n2BSUyicmAe89ibguyeQ/132', 'wx');
INSERT INTO `user_auths` VALUES (5, 10, 'oI89W6AlOzQTqfFIVOF6kNHepuCs', '我叫布落', NULL, 'http://thirdwx.qlogo.cn/mmopen/vi_32/rSTr6qD9FQlNwOOzKINH2Iblr6ojqJSs2qyuYynhRRM3oqhxeWULQPYgtT17oJpkWKG793FRqFnzFianEZP2zSg/132', NULL);
INSERT INTO `user_auths` VALUES (6, 2, 'oa2TLs2Z4_aSWB6EqHz3ND2rDLdY', '星有万种', '男', 'https://thirdwx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTKk3EFJHbFY68dtKczlLic6sct4gmEicibAJMgxO6P2aNwJbXarEZlNic0QKqyzWLJXYVdYWw6BeLkPKA/132', 'wx');
INSERT INTO `user_auths` VALUES (7, 9, '000091.9173dca0bc4d4797a928082ca8ca1b41.0931', NULL, NULL, NULL, 'apple');
INSERT INTO `user_auths` VALUES (8, 3, '000752.8681bf77e5064cbabbb4d5ac12872080.1004', NULL, NULL, NULL, 'apple');
INSERT INTO `user_auths` VALUES (9, 7, 'oI89W6OmQ-cf-aMWmOdfSBkK8oiU', 'Lady .', NULL, 'http://thirdwx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTITLHe5QsbmSiaTicSYsgtLCPwk6l9TINrXHDfaF6ynE3OwVFaTxDqv7lkZroAm8SEsxsBuPSIeAHZA/132', NULL);
INSERT INTO `user_auths` VALUES (13, 19, 'oa2TLs_SjvLGoTmZHpMeQE8kmDg0', 'GOD•野♤驴AJ-🙏🏽✨🎀', '男', 'https://thirdwx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTI1zqLFQKhW4QpuEawxQnqaibT0jFnsFiaw4SMt15qr1GsFEibpUeDeIibZHf2Asl6zDp3W9MPPH9iavTQ/132', 'wx');

-- ----------------------------
-- Table structure for user_information
-- ----------------------------
DROP TABLE IF EXISTS `user_information`;
CREATE TABLE `user_information`  (
  `id` int(15) NOT NULL AUTO_INCREMENT,
  `user_name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '昵称',
  `user_id` int(15) DEFAULT NULL COMMENT '用户ID',
  `user_icon_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '头像url',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 20 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of user_information
-- ----------------------------
INSERT INTO `user_information` VALUES (1, '哈哈哈', 1, 'http://img.staticvip.cn/Fk83b8dQ19B4NwDyEuDRkzBdO3Vt');
INSERT INTO `user_information` VALUES (2, '星有万种💪🏻', 2, 'http://img.staticvip.cn/FqYVRVIFgcXiDZAJiWVUs6ZXAs-y');
INSERT INTO `user_information` VALUES (3, NULL, 3, NULL);
INSERT INTO `user_information` VALUES (4, '鑫鑫', 4, 'http://img.staticvip.cn/icon_20200116111750');
INSERT INTO `user_information` VALUES (5, '超音速', 5, 'http://img.staticvip.cn/icon_20200115184824');
INSERT INTO `user_information` VALUES (6, NULL, 6, NULL);
INSERT INTO `user_information` VALUES (7, NULL, 7, NULL);
INSERT INTO `user_information` VALUES (8, NULL, 8, NULL);
INSERT INTO `user_information` VALUES (9, '嗯呢', 9, 'http://img.staticvip.cn/FgmNr5yM9ITJKH8Xw3IAapeCD3m9');
INSERT INTO `user_information` VALUES (10, '我叫布落', 10, 'http://thirdwx.qlogo.cn/mmopen/vi_32/rSTr6qD9FQlNwOOzKINH2Iblr6ojqJSs2qyuYynhRRM3oqhxeWULQPYgtT17oJpkWKG793FRqFnzFianEZP2zSg/132');
INSERT INTO `user_information` VALUES (11, '阿里河', 11, 'http://img.staticvip.cn/icon_20200118151943');
INSERT INTO `user_information` VALUES (12, '123', 12, 'http://img.staticvip.cn/icon_20200118151958');
INSERT INTO `user_information` VALUES (14, NULL, 14, NULL);
INSERT INTO `user_information` VALUES (19, 'GOD•野♤驴AJ-🙏🏽✨🎀', 19, 'https://thirdwx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTI1zqLFQKhW4QpuEawxQnqaibT0jFnsFiaw4SMt15qr1GsFEibpUeDeIibZHf2Asl6zDp3W9MPPH9iavTQ/132');

-- ----------------------------
-- Table structure for user_integral
-- ----------------------------
DROP TABLE IF EXISTS `user_integral`;
CREATE TABLE `user_integral`  (
  `id` int(15) NOT NULL AUTO_INCREMENT,
  `user_id` int(15) DEFAULT NULL COMMENT '用户id',
  `integral` int(15) DEFAULT NULL COMMENT '积分',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 18 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of user_integral
-- ----------------------------
INSERT INTO `user_integral` VALUES (1, 1, 86);
INSERT INTO `user_integral` VALUES (2, 9, 131);
INSERT INTO `user_integral` VALUES (3, 4, 132);
INSERT INTO `user_integral` VALUES (4, 6, 85);
INSERT INTO `user_integral` VALUES (5, 5, 26);
INSERT INTO `user_integral` VALUES (6, 10, 0);
INSERT INTO `user_integral` VALUES (7, 2, 0);
INSERT INTO `user_integral` VALUES (8, 7, 0);
INSERT INTO `user_integral` VALUES (9, 3, 0);
INSERT INTO `user_integral` VALUES (10, 11, 0);
INSERT INTO `user_integral` VALUES (11, 12, 0);
INSERT INTO `user_integral` VALUES (12, 13, 0);
INSERT INTO `user_integral` VALUES (13, 14, 0);
INSERT INTO `user_integral` VALUES (14, 15, 0);
INSERT INTO `user_integral` VALUES (15, 16, 0);
INSERT INTO `user_integral` VALUES (16, 17, 0);
INSERT INTO `user_integral` VALUES (17, 19, 0);

-- ----------------------------
-- Table structure for user_token
-- ----------------------------
DROP TABLE IF EXISTS `user_token`;
CREATE TABLE `user_token`  (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `token` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '用户token',
  `user_id` int(11) DEFAULT NULL COMMENT '用户id',
  `expiration_time` date DEFAULT NULL COMMENT '过期时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 332 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of user_token
-- ----------------------------
INSERT INTO `user_token` VALUES (175, 'eyJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOjYsImlhdCI6MTU3ODk4ODMyMzIwNCwiZXhwaXJhdGlvblRpbWUiOjE1ODA3MTYzMjMyMDQsImFjY291bnQiOiIxNzYwMDY4NTkzNyJ9.VBbOyKIdkhibMXuAQGXpTC8tGIrzpQ6QvcP5wC2MUT4', 6, '2020-02-03');
INSERT INTO `user_token` VALUES (177, 'eyJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOjgsImlhdCI6MTU3ODk5MDQwNDE3NywiZXhwaXJhdGlvblRpbWUiOjE1ODA3MTg0MDQxNzcsImFjY291bnQiOiIxNTAxMDE5NzU5NyJ9.OmGPaopjgMu6-TrwOO8NXv1gvztZ1j0BjTDn_zNxLGk', 8, '2020-02-03');
INSERT INTO `user_token` VALUES (210, 'eyJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOjEwLCJpYXQiOjE1NzkyMjc2NTIxNjMsImV4cGlyYXRpb25UaW1lIjoxNTgwOTU1NjUyMTYzLCJhY2NvdW50IjoiMTUxMDEwNzA4OTcifQ._6vHZLkqB6beZiIno2O0cNhlSS1WtuU_fxX87EaoDLQ', 10, '2020-02-05');
INSERT INTO `user_token` VALUES (231, 'eyJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOjMsImlhdCI6MTU3OTI1NTQ2MTk1MiwiZXhwaXJhdGlvblRpbWUiOjE1ODA5ODM0NjE5NTIsImFjY291bnQiOiIxODcxNjAyODIxNyJ9.coqIvaQJhHGS6JnGAzQvh4KeBRlP3G3SKabr6Nm4Sbo', 3, '2020-02-06');
INSERT INTO `user_token` VALUES (232, 'eyJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOjIsImlhdCI6MTU3OTI1NTQ3OTk4MCwiZXhwaXJhdGlvblRpbWUiOjE1ODA5ODM0Nzk5ODAsImFjY291bnQiOiIxODQxMDcxNjk4MiJ9.0jpgZENKmSSd40SkN_coVdCqMgSCEesqSQzteIc7nH0', 2, '2020-02-06');
INSERT INTO `user_token` VALUES (244, 'eyJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOjcsImlhdCI6MTU3OTMyNjE2MzczNywiZXhwaXJhdGlvblRpbWUiOjE1ODEwNTQxNjM3MzcsImFjY291bnQiOiIxNTIzNTEzNDM2MSJ9.f47kfIJXX8YSW-UHbL9kCZgACsVttO4KDJurPcar8dE', 7, '2020-02-06');
INSERT INTO `user_token` VALUES (296, 'eyJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOjEzLCJpYXQiOjE1NzkzMzI1NTk4MDgsImV4cGlyYXRpb25UaW1lIjoxNTgxMDYwNTU5ODA4LCJhY2NvdW50IjoiMTMyNjQyNTUyMDEifQ.QnC89DQpilyxsKZKAqEk-R3-8A4ORCqmXOB38j1l9Po', 13, '2020-02-07');
INSERT INTO `user_token` VALUES (298, 'eyJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOjE0LCJpYXQiOjE1NzkzMzI2NTk5NzMsImV4cGlyYXRpb25UaW1lIjoxNTgxMDYwNjU5OTczLCJhY2NvdW50IjoiMTU5MTA2NzM0MDEifQ._1O4QGdoO_igGcPbFdl95D934MvNyFr2u-Aq3KQQVG8', 14, '2020-02-07');
INSERT INTO `user_token` VALUES (299, 'eyJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOjE1LCJpYXQiOjE1NzkzMzI3MTA1OTAsImV4cGlyYXRpb25UaW1lIjoxNTgxMDYwNzEwNTkwLCJhY2NvdW50IjoiMTMyNjQyNTUyMDEifQ.7FyphevjRhBp7ETWda0OpnshCsgrzsIRzyyVY0JrBSo', 15, '2020-02-07');
INSERT INTO `user_token` VALUES (303, 'eyJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOjE2LCJpYXQiOjE1NzkzMzI5MDE0MjYsImV4cGlyYXRpb25UaW1lIjoxNTgxMDYwOTAxNDI2LCJhY2NvdW50IjoiMTMyNjQyNTUyMDEifQ.abBkSXCPXITCoQYNanDZ-vfi4K2Ieg2kAzSJ2vpSgww', 16, '2020-02-07');
INSERT INTO `user_token` VALUES (306, 'eyJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOjE3LCJpYXQiOjE1NzkzMzM1MDA2MTMsImV4cGlyYXRpb25UaW1lIjoxNTgxMDYxNTAwNjEzLCJhY2NvdW50IjoiMTMyNjQyNTUyMDEifQ.lYs2e_alrnDEtrnBPKOiqbdvNcC1mqiovGRkF_cHjvI', 17, '2020-02-07');
INSERT INTO `user_token` VALUES (310, 'eyJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOjE5LCJpYXQiOjE1NzkzMzQwNjk2MjgsImV4cGlyYXRpb25UaW1lIjoxNTgxMDYyMDY5NjI4LCJhY2NvdW50IjoiMTMyNjQyNTUyMDEifQ.nSyB0nee9doFHMedsXAixhUeyuTeDeqbJJEcnRqzY0I', 19, '2020-02-07');
INSERT INTO `user_token` VALUES (314, 'eyJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOjksImlhdCI6MTU3OTMzNDcyNzYxOCwiZXhwaXJhdGlvblRpbWUiOjE1ODEwNjI3Mjc2MTgsImFjY291bnQiOiIxODUxMTY5NDYzMyJ9.-3Wj8lQNmBH8AJOXQEMcObgLt-ggrBC3zsNI6lTErLY', 9, '2020-02-07');
INSERT INTO `user_token` VALUES (315, 'eyJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOjEsImlhdCI6MTU3OTMzNDc5Mjg4MiwiZXhwaXJhdGlvblRpbWUiOjE1ODEwNjI3OTI4ODIsImFjY291bnQiOiIxODUwMDk3NDYzMyJ9.ruRRKEBRdZFp3aiWcMvdtMZgMoqkpbCxLHU84PoZCuE', 1, '2020-02-07');
INSERT INTO `user_token` VALUES (325, 'eyJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOjUsImlhdCI6MTU3OTQwNDM0NDQ3NCwiZXhwaXJhdGlvblRpbWUiOjE1ODExMzIzNDQ0NzQsImFjY291bnQiOiIxMzU1MjE4NDMyMyJ9.lxjJ4jaVAxuaLkmiGRs6ZDEgL2mAGzNbzj_ETtPxpzg', 5, '2020-02-07');
INSERT INTO `user_token` VALUES (329, 'eyJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOjExLCJpYXQiOjE1Nzk0MTU3MTU5ODcsImV4cGlyYXRpb25UaW1lIjoxNTgxMTQzNzE1OTg3LCJhY2NvdW50IjoiMTUyMzUxMzQzNjEifQ.GXYii80H_ZCtGafDkyL5M8owKRY-QDselwla8WrEBfg', 11, '2020-02-08');
INSERT INTO `user_token` VALUES (330, 'eyJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOjEyLCJpYXQiOjE1Nzk0MjIwMDEzNTYsImV4cGlyYXRpb25UaW1lIjoxNTgxMTUwMDAxMzU2LCJhY2NvdW50IjoiMTUwODE2MTM3MTEifQ.-1M5c_NagcZy2SDzjsDngOK8ioOp_OS-cwYGyEHINp8', 12, '2020-02-08');
INSERT INTO `user_token` VALUES (331, 'eyJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOjQsImlhdCI6MTU3OTQ5MDMzNTA2MywiZXhwaXJhdGlvblRpbWUiOjE1ODEyMTgzMzUwNjMsImFjY291bnQiOiIxNTAwMTA5ODk5MiJ9.17wHPTYI-xrN12EXmAcKm1VQuPtYuBxnlzLd9DaI5Eo', 4, '2020-02-08');

-- ----------------------------
-- Table structure for vip_recharge_log
-- ----------------------------
DROP TABLE IF EXISTS `vip_recharge_log`;
CREATE TABLE `vip_recharge_log`  (
  `id` int(15) NOT NULL AUTO_INCREMENT,
  `order_no` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '订单单号',
  `recharge_amount` decimal(10, 2) DEFAULT NULL COMMENT '充值金额',
  `order_status` int(4) DEFAULT NULL COMMENT '订单状态 待付款0,已付款1，付款失败-1',
  `create_time` varchar(15) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '订单创建时间',
  `create_user` int(15) DEFAULT NULL COMMENT '创建用户',
  `vip_type` int(1) DEFAULT NULL COMMENT 'vip类型 1-月度，2季度，3年度',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 44 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of vip_recharge_log
-- ----------------------------
INSERT INTO `vip_recharge_log` VALUES (3, 'vip02f81125-1724-4b08-900a-8eb1e', 30.00, 1, '20200111171133', 1, 1);
INSERT INTO `vip_recharge_log` VALUES (4, 'vip0e86689b-ccaf-4331-a03e-d1edb', 30.00, 1, '20200111171733', 1, 1);
INSERT INTO `vip_recharge_log` VALUES (5, 'vipcb19f1e8-03f4-4bd1-b951-55f5a', 30.00, 1, '20200111172012', 1, 1);
INSERT INTO `vip_recharge_log` VALUES (6, 'vipa9e5933f-ca28-4192-b013-929a0', 30.00, 1, '20200111172219', 1, 1);
INSERT INTO `vip_recharge_log` VALUES (7, 'vip53e418bc-3e2f-4847-9974-d4707', 30.00, 1, '20200111172341', 1, 1);
INSERT INTO `vip_recharge_log` VALUES (8, 'vip72bffbe2-765f-42dd-8f9f-07373', 30.00, 1, '20200111172444', 1, 1);
INSERT INTO `vip_recharge_log` VALUES (9, 'vip45bef518-7bd5-4ef8-b9ad-e03dd', 90.00, 1, '20200113114949', 1, 2);
INSERT INTO `vip_recharge_log` VALUES (12, 'vipf1295e06-9cff-410e-8d5c-8cbdf', 30.00, 1, '20200113134931', 5, 1);
INSERT INTO `vip_recharge_log` VALUES (13, 'vip9ce30e7a-c5c1-471b-b82b-6f642', 30.00, 1, '20200113140552', 5, 1);
INSERT INTO `vip_recharge_log` VALUES (23, 'vip5ab7c388-3cd0-46d8-b68c-4ac443156fec', 30.00, 1, '20200113163342', 1, 1);
INSERT INTO `vip_recharge_log` VALUES (24, 'vip2e00dafa-2d89-45da-8020-25abe112cf70', 30.00, 1, '20200113163512', 5, 1);
INSERT INTO `vip_recharge_log` VALUES (25, 'vip557d1f3e-5080-419c-962b-28ec4d4f6a84', 360.00, 1, '20200113163615', 1, 3);
INSERT INTO `vip_recharge_log` VALUES (26, 'vip588151f7-3fec-4f73-88a4-51382638c2c0', 30.00, 1, '20200113165222', 5, 1);
INSERT INTO `vip_recharge_log` VALUES (28, 'vip776795a8-dc49-4fb9-855e-5897f', 10.00, 1, '20200116152103', 9, 1);
INSERT INTO `vip_recharge_log` VALUES (29, 'vip78dc19ec-8ebc-40cb-b568-d714d', 10.00, 1, '20200116152129', 9, 1);
INSERT INTO `vip_recharge_log` VALUES (30, 'vip2e6c0255-5aaf-4451-b598-681de4f4ffc3', 30.00, 1, '20200116152148', 9, 2);
INSERT INTO `vip_recharge_log` VALUES (31, 'vip55e5a07f-d975-49ed-8ef2-0367f', 120.00, 1, '20200116152209', 9, 3);
INSERT INTO `vip_recharge_log` VALUES (33, 'vip03c31237-20cd-4789-9193-0d071354908b', 10.00, 1, '20200117125525', 2, 1);
INSERT INTO `vip_recharge_log` VALUES (34, 'vip181222ca-2df4-40be-b336-2db36', 30.00, 1, '20200117125624', 2, 2);
INSERT INTO `vip_recharge_log` VALUES (37, 'vipd209fd95-b0d7-4743-abe1-8a2d61ecc03f', 10.00, 1, '20200118150304', 11, 1);

SET FOREIGN_KEY_CHECKS = 1;
