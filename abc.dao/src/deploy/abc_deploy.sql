/*
Navicat MySQL Data Transfer

Source Server         : 192.168.10.20
Source Server Version : 50540
Source Host           : 192.168.10.20:3306
Source Database       : abc_test

Target Server Type    : MYSQL
Target Server Version : 50540
File Encoding         : 65001

Date: 2015-01-23 11:20:28
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `abc_account_info`
-- ----------------------------
DROP TABLE IF EXISTS `abc_account_info`;
CREATE TABLE `abc_account_info` (
  `account_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `account_user_id` int(11) DEFAULT NULL COMMENT '用户id',
  `account_user_type` tinyint(4) DEFAULT NULL COMMENT '用户类型',
  `account_legal_name` varchar(30) COLLATE utf8_bin DEFAULT NULL COMMENT '法人姓名',
  `account_user_name` varchar(30) COLLATE utf8_bin DEFAULT NULL COMMENT '用户姓名(企业名称)',
  `account_user_card` varchar(30) COLLATE utf8_bin DEFAULT NULL COMMENT '身份证号(法人身份证号)',
  `account_no` varchar(18) COLLATE utf8_bin DEFAULT NULL COMMENT '开户账户',
  `account_user_phone` varchar(15) COLLATE utf8_bin DEFAULT NULL COMMENT '手机号码(法人手机号码)',
  `account_user_email` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '邮箱地址(法人邮箱地址)',
  `account_bank_area` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '开户银行地区',
  `account_bank_name` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '开户银行名称',
  `account_bank_branch_name` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '开户银行支行名称',
  `account_user_account` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '帐号(企业对公账户号)',
  `account_cash_pwd` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '提现密码',
  `account_login_pwd` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '登录密码',
  `account_mark` varchar(512) COLLATE utf8_bin DEFAULT NULL COMMENT '备注',
  `account_del_flag` int(1) DEFAULT '0' COMMENT '删除标识（默认0未删除，已删除-1）',
  `account_modify_time` datetime DEFAULT NULL COMMENT '更新时间',
  `account_create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`account_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of abc_account_info
-- ----------------------------

-- ----------------------------
-- Table structure for `abc_activity_record`
-- ----------------------------
DROP TABLE IF EXISTS `abc_activity_record`;
CREATE TABLE `abc_activity_record` (
  `ar_id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `ar_type` tinyint(2) NOT NULL COMMENT '活动类型 1:投资活动 2:转让活动 3:收购活动',
  `ar_foreign_id` int(11) DEFAULT NULL COMMENT '外键id',
  `ar_createtime` datetime NOT NULL COMMENT '创建时间',
  `ar_creator` int(11) NOT NULL COMMENT '创建人',
  `ar_endtime` datetime DEFAULT NULL COMMENT '结束时间',
  PRIMARY KEY (`ar_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='业务活动记录表';

-- ----------------------------
-- Records of abc_activity_record
-- ----------------------------

-- ----------------------------
-- Table structure for `abc_area`
-- ----------------------------
DROP TABLE IF EXISTS `abc_area`;
CREATE TABLE `abc_area` (
  `area_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `area_name` varchar(20) COLLATE utf8_bin DEFAULT NULL COMMENT '地区名称',
  `area_super_area` varchar(20) COLLATE utf8_bin DEFAULT NULL COMMENT '父级地区',
  `area_area_code` varchar(20) COLLATE utf8_bin DEFAULT NULL COMMENT '地区编号',
  `area_sign` tinyint(1) DEFAULT NULL COMMENT '0：省 1：市 2：县',
  PRIMARY KEY (`area_id`)
) ENGINE=InnoDB AUTO_INCREMENT=189 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='区域信息表';

-- ----------------------------
-- Records of abc_area
-- ----------------------------
INSERT INTO `abc_area` VALUES ('1', '北京', null, '110', '0');
INSERT INTO `abc_area` VALUES ('2', '北京市', '110', '1000', '1');
INSERT INTO `abc_area` VALUES ('3', '天津市', null, '120', '0');
INSERT INTO `abc_area` VALUES ('4', '天津', '120', '1100', '1');
INSERT INTO `abc_area` VALUES ('5', '河北省', null, '130', '0');
INSERT INTO `abc_area` VALUES ('6', '石家庄', '130', '1210', '1');
INSERT INTO `abc_area` VALUES ('7', '唐山', '130', '1240', '1');
INSERT INTO `abc_area` VALUES ('8', '滦县', '130', '1243', '2');
INSERT INTO `abc_area` VALUES ('9', '滦南县', '130', '1244', '2');
INSERT INTO `abc_area` VALUES ('10', '乐亭县', '130', '1245', '2');
INSERT INTO `abc_area` VALUES ('11', '迁安市', '130', '1246', '2');
INSERT INTO `abc_area` VALUES ('12', '迁西县', '130', '1247', '2');
INSERT INTO `abc_area` VALUES ('13', '遵化市', '130', '1248', '2');
INSERT INTO `abc_area` VALUES ('14', '玉田县', '130', '1249', '2');
INSERT INTO `abc_area` VALUES ('15', '唐海县', '130', '1251', '2');
INSERT INTO `abc_area` VALUES ('16', '秦皇岛市', '130', '1260', '2');
INSERT INTO `abc_area` VALUES ('17', '青龙县', '130', '1261', '2');
INSERT INTO `abc_area` VALUES ('18', '昌黎县', '130', '1262', '2');
INSERT INTO `abc_area` VALUES ('19', '抚宁县', '130', '1263', '2');
INSERT INTO `abc_area` VALUES ('20', '卢龙县', '130', '1264', '2');
INSERT INTO `abc_area` VALUES ('21', '邯郸市', '130', '1270', '2');
INSERT INTO `abc_area` VALUES ('22', '邯郸县', '130', '1271', '2');
INSERT INTO `abc_area` VALUES ('23', '大名县', '130', '1281', '2');
INSERT INTO `abc_area` VALUES ('24', '魏县', '130', '1282', '2');
INSERT INTO `abc_area` VALUES ('25', '邱县', '130', '1284', '2');
INSERT INTO `abc_area` VALUES ('26', '鸡泽县', '130', '1285', '2');
INSERT INTO `abc_area` VALUES ('27', '肥乡县', '130', '1286', '2');
INSERT INTO `abc_area` VALUES ('28', '广平县', '130', '1287', '2');
INSERT INTO `abc_area` VALUES ('29', '成安县', '130', '1288', '2');
INSERT INTO `abc_area` VALUES ('30', '临漳县', '130', '1291', '2');
INSERT INTO `abc_area` VALUES ('31', '涉县', '130', '1292', '2');
INSERT INTO `abc_area` VALUES ('32', '永年县', '130', '1293', '2');
INSERT INTO `abc_area` VALUES ('33', '馆陶县', '130', '1294', '2');
INSERT INTO `abc_area` VALUES ('34', '武安市', '130', '1295', '2');
INSERT INTO `abc_area` VALUES ('35', '邢台市', '130', '1310', '2');
INSERT INTO `abc_area` VALUES ('36', '邢台县', '130', '1311', '2');
INSERT INTO `abc_area` VALUES ('37', '南宫市', '130', '1321', '2');
INSERT INTO `abc_area` VALUES ('38', '沙河市', '130', '1322', '2');
INSERT INTO `abc_area` VALUES ('39', '临城县', '130', '1323', '2');
INSERT INTO `abc_area` VALUES ('40', '内邱县', '130', '1324', '2');
INSERT INTO `abc_area` VALUES ('41', '柏乡县', '130', '1325', '2');
INSERT INTO `abc_area` VALUES ('42', '隆尧县', '130', '1326', '2');
INSERT INTO `abc_area` VALUES ('43', '任县', '130', '1327', '2');
INSERT INTO `abc_area` VALUES ('44', '南和县', '130', '1328', '2');
INSERT INTO `abc_area` VALUES ('45', '宁晋县', '130', '1329', '2');
INSERT INTO `abc_area` VALUES ('46', '巨鹿县', '130', '1331', '2');
INSERT INTO `abc_area` VALUES ('47', '新河县', '130', '1332', '2');
INSERT INTO `abc_area` VALUES ('48', '广宗县', '130', '1293', '2');
INSERT INTO `abc_area` VALUES ('49', '馆陶县', '130', '1294', '2');
INSERT INTO `abc_area` VALUES ('50', '武安市', '130', '1295', '2');
INSERT INTO `abc_area` VALUES ('51', '邢台市', '130', '1310', '2');
INSERT INTO `abc_area` VALUES ('52', '邢台县', '130', '1311', '2');
INSERT INTO `abc_area` VALUES ('53', '南宫市', '130', '1321', '2');
INSERT INTO `abc_area` VALUES ('54', '沙河市', '130', '1322', '2');
INSERT INTO `abc_area` VALUES ('55', '临城县', '130', '1323', '2');
INSERT INTO `abc_area` VALUES ('56', '内邱县', '130', '1333', '2');
INSERT INTO `abc_area` VALUES ('57', '平乡县', '130', '1334', '2');
INSERT INTO `abc_area` VALUES ('58', '威县', '130', '1335', '2');
INSERT INTO `abc_area` VALUES ('59', '清河县', '130', '1336', '2');
INSERT INTO `abc_area` VALUES ('60', '临西县', '130', '1337', '2');
INSERT INTO `abc_area` VALUES ('61', '保定市', '130', '1340', '2');
INSERT INTO `abc_area` VALUES ('62', '满城县', '130', '1341', '2');
INSERT INTO `abc_area` VALUES ('63', '清苑县', '130', '1342', '2');
INSERT INTO `abc_area` VALUES ('64', '定州市', '130', '1351', '2');
INSERT INTO `abc_area` VALUES ('65', '涿州市', '130', '1352', '2');
INSERT INTO `abc_area` VALUES ('66', '易县', '130', '1353', '2');
INSERT INTO `abc_area` VALUES ('67', '徐水县', '130', '1354', '2');
INSERT INTO `abc_area` VALUES ('68', '涞源县', '130', '1355', '2');
INSERT INTO `abc_area` VALUES ('69', '定兴县', '130', '1356', '2');
INSERT INTO `abc_area` VALUES ('70', '顺平县', '130', '1357', '2');
INSERT INTO `abc_area` VALUES ('71', '唐县', '130', '1358', '2');
INSERT INTO `abc_area` VALUES ('72', '望都县', '130', '1359', '2');
INSERT INTO `abc_area` VALUES ('73', '涞水县', '130', '1361', '2');
INSERT INTO `abc_area` VALUES ('74', '高阳县', '130', '1362', '2');
INSERT INTO `abc_area` VALUES ('75', '安新县', '130', '1363', '2');
INSERT INTO `abc_area` VALUES ('76', '雄县', '130', '1364', '2');
INSERT INTO `abc_area` VALUES ('77', '容城县', '130', '1365', '2');
INSERT INTO `abc_area` VALUES ('78', '高碑店市', '130', '1366', '2');
INSERT INTO `abc_area` VALUES ('79', '曲阳县', '130', '1367', '2');
INSERT INTO `abc_area` VALUES ('80', '阜平县', '130', '1368', '2');
INSERT INTO `abc_area` VALUES ('81', '安国市', '130', '1369', '2');
INSERT INTO `abc_area` VALUES ('82', '博野县', '130', '1371', '2');
INSERT INTO `abc_area` VALUES ('83', '蠡县', '130', '1372', '2');
INSERT INTO `abc_area` VALUES ('84', '张家口市', '130', '1380', '2');
INSERT INTO `abc_area` VALUES ('85', '宣化县', '130', '1381', '2');
INSERT INTO `abc_area` VALUES ('86', '张北县', '130', '1391', '2');
INSERT INTO `abc_area` VALUES ('87', '康保县', '130', '1392', '2');
INSERT INTO `abc_area` VALUES ('88', '沽源县', '130', '1393', '2');
INSERT INTO `abc_area` VALUES ('89', '尚义县', '130', '1394', '2');
INSERT INTO `abc_area` VALUES ('90', '蔚县', '130', '1395', '2');
INSERT INTO `abc_area` VALUES ('91', '阳原县', '130', '1396', '2');
INSERT INTO `abc_area` VALUES ('92', '怀安县', '130', '1397', '2');
INSERT INTO `abc_area` VALUES ('93', '湖南省', null, '430', '0');
INSERT INTO `abc_area` VALUES ('94', '道县', '430', '5654', '1');
INSERT INTO `abc_area` VALUES ('95', '宁远县', '430', '5655', '1');
INSERT INTO `abc_area` VALUES ('96', '江永县', '430', '5656', '1');
INSERT INTO `abc_area` VALUES ('97', '江华县', '430', '5657', '1');
INSERT INTO `abc_area` VALUES ('98', '蓝山县', '430', '5658', '1');
INSERT INTO `abc_area` VALUES ('99', '新田县', '430', '5659', '1');
INSERT INTO `abc_area` VALUES ('100', '双牌县', '430', '5661', '1');
INSERT INTO `abc_area` VALUES ('101', '祁阳县', '430', '5662', '1');
INSERT INTO `abc_area` VALUES ('102', '怀化市', '430', '5670', '1');
INSERT INTO `abc_area` VALUES ('103', '洪江市', '430', '5672', '1');
INSERT INTO `abc_area` VALUES ('104', '沅陵县', '430', '5674', '1');
INSERT INTO `abc_area` VALUES ('105', '辰溪县', '430', '5675', '1');
INSERT INTO `abc_area` VALUES ('106', '溆浦县', '430', '5676', '1');
INSERT INTO `abc_area` VALUES ('107', '麻阳县', '430', '5677', '1');
INSERT INTO `abc_area` VALUES ('108', '新晃县', '430', '5678', '1');
INSERT INTO `abc_area` VALUES ('109', '芷江县', '430', '5679', '1');
INSERT INTO `abc_area` VALUES ('110', '会同县', '430', '5681', '1');
INSERT INTO `abc_area` VALUES ('111', '靖州县', '430', '5682', '1');
INSERT INTO `abc_area` VALUES ('112', '通道县', '430', '5683', '1');
INSERT INTO `abc_area` VALUES ('113', '吉首市', '430', '5690', '1');
INSERT INTO `abc_area` VALUES ('114', '泸溪县', '430', '5692', '1');
INSERT INTO `abc_area` VALUES ('115', '凤凰县', '430', '5693', '1');
INSERT INTO `abc_area` VALUES ('116', '花垣县', '430', '5694', '1');
INSERT INTO `abc_area` VALUES ('117', '保靖县', '430', '5695', '1');
INSERT INTO `abc_area` VALUES ('118', '古丈县', '430', '5696', '1');
INSERT INTO `abc_area` VALUES ('119', '永顺县', '430', '5697', '1');
INSERT INTO `abc_area` VALUES ('120', '龙山县', '430', '5698', '1');
INSERT INTO `abc_area` VALUES ('121', '双峰县', '430', '5624', '1');
INSERT INTO `abc_area` VALUES ('122', '新化县', '430', '5625', '1');
INSERT INTO `abc_area` VALUES ('123', '郴州市', '430', '5630', '1');
INSERT INTO `abc_area` VALUES ('124', '资兴市', '430', '5632', '1');
INSERT INTO `abc_area` VALUES ('125', '桂阳县', '430', '5634', '1');
INSERT INTO `abc_area` VALUES ('126', '永兴县', '430', '5635', '1');
INSERT INTO `abc_area` VALUES ('127', '宜章县', '430', '5636', '1');
INSERT INTO `abc_area` VALUES ('128', '嘉禾县', '430', '5637', '1');
INSERT INTO `abc_area` VALUES ('129', '临武县', '430', '5638', '1');
INSERT INTO `abc_area` VALUES ('130', '汝城县', '430', '5639', '1');
INSERT INTO `abc_area` VALUES ('131', '桂东县', '430', '5641', '1');
INSERT INTO `abc_area` VALUES ('132', '安仁县', '430', '5642', '1');
INSERT INTO `abc_area` VALUES ('133', '永州市', '430', '5650', '1');
INSERT INTO `abc_area` VALUES ('134', '东安县', '430', '5653', '1');
INSERT INTO `abc_area` VALUES ('135', '炎陵县', '430', '5526', '1');
INSERT INTO `abc_area` VALUES ('136', '长沙', '430', '5510', '1');
INSERT INTO `abc_area` VALUES ('137', '株州市', '430', '5520', '1');
INSERT INTO `abc_area` VALUES ('138', '株洲县', '430', '5521', '1');
INSERT INTO `abc_area` VALUES ('139', '攸县县', '430', '5522', '1');
INSERT INTO `abc_area` VALUES ('140', '茶陵县', '430', '5523', '1');
INSERT INTO `abc_area` VALUES ('141', '澧县', '430', '5583', '1');
INSERT INTO `abc_area` VALUES ('142', '醴陵市', '430', '5525', '1');
INSERT INTO `abc_area` VALUES ('143', '湘潭市', '430', '5530', '1');
INSERT INTO `abc_area` VALUES ('144', '湘乡市', '430', '5532', '1');
INSERT INTO `abc_area` VALUES ('145', '韶山县', '430', '5533', '1');
INSERT INTO `abc_area` VALUES ('146', '衡阳市', '430', '5540', '1');
INSERT INTO `abc_area` VALUES ('147', '衡阳县', '430', '5541', '1');
INSERT INTO `abc_area` VALUES ('148', '衡南县', '430', '5542', '1');
INSERT INTO `abc_area` VALUES ('149', '衡山县', '430', '5543', '1');
INSERT INTO `abc_area` VALUES ('150', '衡东县', '430', '5544', '1');
INSERT INTO `abc_area` VALUES ('151', '常宁县', '430', '5545', '1');
INSERT INTO `abc_area` VALUES ('152', '祁东县', '430', '5546', '1');
INSERT INTO `abc_area` VALUES ('153', '耒阳县', '430', '5547', '1');
INSERT INTO `abc_area` VALUES ('154', '邵阳市', '430', '5550', '1');
INSERT INTO `abc_area` VALUES ('155', '邵东县', '430', '5551', '1');
INSERT INTO `abc_area` VALUES ('156', '新邵县', '430', '5552', '1');
INSERT INTO `abc_area` VALUES ('157', '邵阳县', '430', '5553', '1');
INSERT INTO `abc_area` VALUES ('158', '隆回县', '430', '5554', '1');
INSERT INTO `abc_area` VALUES ('159', '洞口县', '430', '5555', '1');
INSERT INTO `abc_area` VALUES ('160', '武冈县', '430', '5556', '1');
INSERT INTO `abc_area` VALUES ('161', '绥宁县', '430', '5557', '1');
INSERT INTO `abc_area` VALUES ('162', '新宁县', '430', '5558', '1');
INSERT INTO `abc_area` VALUES ('163', '城步县', '430', '5559', '1');
INSERT INTO `abc_area` VALUES ('164', '岳阳市', '430', '5570', '1');
INSERT INTO `abc_area` VALUES ('165', '岳阳县', '430', '5571', '1');
INSERT INTO `abc_area` VALUES ('166', '临湘县', '430', '5572', '1');
INSERT INTO `abc_area` VALUES ('167', '华容县', '430', '5573', '1');
INSERT INTO `abc_area` VALUES ('168', '湘阴县', '430', '5574', '1');
INSERT INTO `abc_area` VALUES ('169', '平江县', '430', '5575', '1');
INSERT INTO `abc_area` VALUES ('170', '汩罗市', '430', '5576', '1');
INSERT INTO `abc_area` VALUES ('171', '常德市', '430', '5580', '1');
INSERT INTO `abc_area` VALUES ('172', '安乡县', '430', '5581', '1');
INSERT INTO `abc_area` VALUES ('173', '汉寿县', '430', '5582', '1');
INSERT INTO `abc_area` VALUES ('174', '临澧县', '430', '5584', '1');
INSERT INTO `abc_area` VALUES ('175', '桃源县', '430', '5585', '1');
INSERT INTO `abc_area` VALUES ('176', '石门县', '430', '5586', '1');
INSERT INTO `abc_area` VALUES ('177', '津市市', '430', '5587', '1');
INSERT INTO `abc_area` VALUES ('178', '张家界市', '430', '5590', '1');
INSERT INTO `abc_area` VALUES ('179', '慈利县', '430', '5591', '1');
INSERT INTO `abc_area` VALUES ('180', '桑植县', '430', '5592', '1');
INSERT INTO `abc_area` VALUES ('181', '益阳市', '430', '5610', '1');
INSERT INTO `abc_area` VALUES ('182', '沅江县', '430', '5612', '1');
INSERT INTO `abc_area` VALUES ('183', '南县', '430', '5614', '1');
INSERT INTO `abc_area` VALUES ('184', '桃江县', '430', '5615', '1');
INSERT INTO `abc_area` VALUES ('185', '安化县', '430', '5616', '1');
INSERT INTO `abc_area` VALUES ('186', '娄底市', '430', '5620', '1');
INSERT INTO `abc_area` VALUES ('187', '冷水江市', '430', '5622', '1');
INSERT INTO `abc_area` VALUES ('188', '涟源县', '430', '5623', '1');

-- ----------------------------
-- Table structure for `abc_article_class`
-- ----------------------------
DROP TABLE IF EXISTS `abc_article_class`;
CREATE TABLE `abc_article_class` (
  `ac_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '文章类别ID',
  `ac_name` varchar(20) NOT NULL DEFAULT '' COMMENT '文章类别',
  `ac_mode` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `ac_path` varchar(200) DEFAULT NULL,
  `ac_template` varchar(200) DEFAULT NULL,
  `ac_content_template` text CHARACTER SET utf8 COLLATE utf8_bin,
  `ac_desc` varchar(200) DEFAULT NULL,
  `ac_key_word` varchar(100) DEFAULT NULL,
  `ac_type` int(11) unsigned zerofill DEFAULT NULL,
  `ac_order` int(11) DEFAULT NULL,
  `ac_class` int(11) unsigned zerofill DEFAULT NULL COMMENT '父级类别ID',
  PRIMARY KEY (`ac_id`)
) ENGINE=MyISAM AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of abc_article_class
-- ----------------------------

-- ----------------------------
-- Table structure for `abc_article_info`
-- ----------------------------
DROP TABLE IF EXISTS `abc_article_info`;
CREATE TABLE `abc_article_info` (
  `ai_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '文章ID',
  `ai_class_id` int(11) DEFAULT NULL COMMENT '文章类别ID（外键表：abc_article_class）',
  `ai_article_title` varchar(125) DEFAULT NULL COMMENT '文章标题',
  `ai_article_content` text COMMENT '文章内容',
  `ai_is_top` int(1) DEFAULT NULL COMMENT '是否置顶（1：是 ，0：否）',
  `ai_article_source` varchar(125) DEFAULT NULL COMMENT '文章来源',
  `ai_add_emp` int(11) DEFAULT NULL COMMENT '发布者（外键表：SYS_employee）',
  `ai_add_date` datetime DEFAULT NULL COMMENT '发布者日期',
  `ai_article_count` int(10) unsigned zerofill DEFAULT NULL COMMENT '浏览次数',
  PRIMARY KEY (`ai_id`)
) ENGINE=MyISAM AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of abc_article_info
-- ----------------------------

-- ----------------------------
-- Table structure for `abc_bank_info`
-- ----------------------------
DROP TABLE IF EXISTS `abc_bank_info`;
CREATE TABLE `abc_bank_info` (
  `bank_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `bank_user_id` int(11) DEFAULT NULL COMMENT '用户id',
  `bank_user_type` tinyint(4) DEFAULT NULL COMMENT '用户类型',
  `bank_name` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '银行名称',
  `bank_no` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '银行卡号',
  `bank_lawer` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '法人账户姓名',
  PRIMARY KEY (`bank_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of abc_bank_info
-- ----------------------------

-- ----------------------------
-- Table structure for `abc_btn`
-- ----------------------------
DROP TABLE IF EXISTS `abc_btn`;
CREATE TABLE `abc_btn` (
  `btn_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键自增',
  `btn_name` varchar(30) COLLATE utf8_bin NOT NULL DEFAULT '0' COMMENT '按钮名称',
  `btn_icon` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '按钮图标',
  `btn_tag` varchar(20) COLLATE utf8_bin NOT NULL COMMENT '按钮在前端显示的标示',
  `btn_sort` int(11) NOT NULL DEFAULT '0' COMMENT '排序',
  `btn_des` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '备注',
  `btn_createtime` datetime DEFAULT NULL,
  `btn_modifytime` datetime DEFAULT NULL,
  PRIMARY KEY (`btn_id`)
) ENGINE=InnoDB AUTO_INCREMENT=219 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='按钮表';

-- ----------------------------
-- Records of abc_btn
-- ----------------------------
INSERT INTO `abc_btn` VALUES ('138', '浏览', 'icon-zoom', 'Browser', '99', '不能删除', null, null);
INSERT INTO `abc_btn` VALUES ('139', '添加', 'icon-add', 'Add', '98', '添加', null, null);
INSERT INTO `abc_btn` VALUES ('140', '修改', 'icon-pencil', 'Edit', '97', '修改', null, null);
INSERT INTO `abc_btn` VALUES ('141', '删除', 'icon-cross', 'Del', '96', '删除', null, null);
INSERT INTO `abc_btn` VALUES ('142', '导出Excel', 'icon-arrow_turn_right', 'Export', '69', '导出', null, null);
INSERT INTO `abc_btn` VALUES ('143', '导入', 'icon-arrow_turn_left', 'Import', '0', '导入', null, null);
INSERT INTO `abc_btn` VALUES ('144', '打印', 'icon-printer', 'Print', '70', '打印', null, null);
INSERT INTO `abc_btn` VALUES ('145', '上传', 'icon-folder_up', 'Upload', '0', '上传', null, null);
INSERT INTO `abc_btn` VALUES ('146', '下载', 'icon-download', 'DownLoad', '0', '下载', null, null);
INSERT INTO `abc_btn` VALUES ('147', '审核', 'icon-user_magnify', 'Audit', '94', '审核', null, null);
INSERT INTO `abc_btn` VALUES ('148', '复制', 'icon-page_copy', 'Copy', '0', '复制', null, null);
INSERT INTO `abc_btn` VALUES ('149', '授权', 'icon-lock', 'Authorization', '0', '授权', null, null);
INSERT INTO `abc_btn` VALUES ('150', '分配角色', 'icon-calculator_link', 'SetRole', '0', '分配角色', null, null);
INSERT INTO `abc_btn` VALUES ('151', '分配按钮', 'icon-plugin_add', 'SetBtn', '0', '分配按钮', null, null);
INSERT INTO `abc_btn` VALUES ('152', '修改密码', 'icon-key', 'EditPwd', '0', '修改密码', null, null);
INSERT INTO `abc_btn` VALUES ('153', '初始化', 'icon-cross', 'Initialize', '0', '初始化', null, null);
INSERT INTO `abc_btn` VALUES ('154', '还原', 'icon-coins', 'Restore', '0', '数据还原', null, null);
INSERT INTO `abc_btn` VALUES ('155', '发送', 'icon-arrow_turn_right', 'Send', '94', '发送', null, null);
INSERT INTO `abc_btn` VALUES ('156', '返回', 'icon-arrow_rotate_clockwise', 'GoBack', '-1', '返回', null, null);
INSERT INTO `abc_btn` VALUES ('157', '查看流程图', 'icon-zoom', 'ViewFlow', '0', '查看流程图', null, null);
INSERT INTO `abc_btn` VALUES ('158', '撤回', 'icon-arrow_redo', 'Recall', '93', '撤回', null, null);
INSERT INTO `abc_btn` VALUES ('159', '退回', 'icon-arrow_undo', 'Return', '92', '退回', null, null);
INSERT INTO `abc_btn` VALUES ('160', '维护', 'icon-user_red', 'Maintain', '50', '维护按钮', null, null);
INSERT INTO `abc_btn` VALUES ('161', '审批配置', 'icon-cog_edit', 'Approval', '0', '审批配置', null, null);
INSERT INTO `abc_btn` VALUES ('162', '查看', 'icon-zoom', 'LookUp', '95', '查看', null, null);
INSERT INTO `abc_btn` VALUES ('163', '搜索', 'icon-search', 'Search', '-2', '搜索', null, null);
INSERT INTO `abc_btn` VALUES ('164', '表决', 'icon-user', 'Vote', '80', '代偿还款评审表决', null, null);
INSERT INTO `abc_btn` VALUES ('165', '费用维护', 'icon-group_edit', 'FeeMaintain', '0', '追偿维护 费用维护', null, null);
INSERT INTO `abc_btn` VALUES ('166', '确认放款', 'icon-tux', 'ConfirmLoan', '0', '确认放款', null, null);
INSERT INTO `abc_btn` VALUES ('167', '打印呈批件', 'icon-w_fBtnSelectDate', 'PrintDocument', '0', '打印呈批件', null, null);
INSERT INTO `abc_btn` VALUES ('168', '查看评审意见', 'icon-group_gear', 'ReviewComments', '0', '查看评审意见', null, null);
INSERT INTO `abc_btn` VALUES ('169', '跟踪流程', 'icon-book_red', 'ProcTrack', '0', '跟踪流程', null, null);
INSERT INTO `abc_btn` VALUES ('170', '计算', 'icon-calculator', 'Calculate', '0', '计算按钮', null, null);
INSERT INTO `abc_btn` VALUES ('171', '签批', 'icon-wand', 'Signing', '80', '签批', null, null);
INSERT INTO `abc_btn` VALUES ('172', '审核意见', 'icon-report_user', 'CheckIdear', '0', '审核意见', null, null);
INSERT INTO `abc_btn` VALUES ('173', '数据授权', 'icon-folder_key', 'DataAuth', '0', '职务数据授权', null, null);
INSERT INTO `abc_btn` VALUES ('174', '代还', 'icon-coins', 'ReplacePay', '0', '代还', null, null);
INSERT INTO `abc_btn` VALUES ('175', '启用', 'icon-user_edit', 'Enable', '2', '启用', null, null);
INSERT INTO `abc_btn` VALUES ('176', '初始化密码', 'icon-key_start', 'InitPassword', '0', '初始化密码', null, null);
INSERT INTO `abc_btn` VALUES ('177', '发布', 'icon-report', 'Release', '0', '发布', null, null);
INSERT INTO `abc_btn` VALUES ('178', '资金匹配', 'icon-coins', 'MatchingFunds', '0', '资金匹配', null, null);
INSERT INTO `abc_btn` VALUES ('179', '匹配机构', 'icon-group_link', 'MatchingOrg', '0', '匹配机构', null, null);
INSERT INTO `abc_btn` VALUES ('180', '资金划转', 'icon-shield_go', 'TransferFunds', '0', '资金划转', null, null);
INSERT INTO `abc_btn` VALUES ('181', '打印资金划转单', 'icon-printer', 'PrintFundTransfer', '0', '打印资金划转单', null, null);
INSERT INTO `abc_btn` VALUES ('182', '打印收费单', 'icon-printer', 'PrintBill', '0', '打印收费单', null, null);
INSERT INTO `abc_btn` VALUES ('183', '打印投标清单', 'icon-printer', 'PrintBid', '0', '打印投标清单', null, null);
INSERT INTO `abc_btn` VALUES ('184', '还款', 'icon-coins_delete', 'Repayment', '0', '还款', null, null);
INSERT INTO `abc_btn` VALUES ('185', '开始统计', 'icon-chart_bar', 'StartSta', '0', '开始统计', null, null);
INSERT INTO `abc_btn` VALUES ('186', '查看意见', 'icon-report_user', 'ViewIdea', '0', '查看意见', null, null);
INSERT INTO `abc_btn` VALUES ('187', '担保匹配', 'icon-computer_link', 'GuarMatch', '0', '担保匹配', null, null);
INSERT INTO `abc_btn` VALUES ('188', '审核进度', 'icon-application_form_magnify', 'AuditSche', '0', '审核进度', null, null);
INSERT INTO `abc_btn` VALUES ('189', '强制还款', 'icon-coins', 'ComPay', '0', '强制还款', null, null);
INSERT INTO `abc_btn` VALUES ('190', '停用', 'icon-lock_stop', 'LockStop', '1', '停用', null, null);
INSERT INTO `abc_btn` VALUES ('191', '兑现划转', 'icon-shield_go', 'CashTransfer', '0', '兑现划转', null, null);
INSERT INTO `abc_btn` VALUES ('192', '发送审核', 'icon-arrow_turn_right', 'GuarCheck', '89', '发送担保审核', null, null);
INSERT INTO `abc_btn` VALUES ('193', '资料补全', 'icon-add', 'MatainAdd', '92', '资料补全', null, null);
INSERT INTO `abc_btn` VALUES ('194', '发送平台审核', 'icon-arrow_turn_right', 'SendToCheck', '90', '发送平台审核', null, null);
INSERT INTO `abc_btn` VALUES ('195', '查看历史', 'icon-bell', 'ViewHistory', '91', '查看历史', null, null);
INSERT INTO `abc_btn` VALUES ('196', '取消发布', 'icon-arrow_redo', 'CancelRelease', '0', '取消发布', null, null);
INSERT INTO `abc_btn` VALUES ('197', '打印转让清单', 'icon-printer', 'PrintTransfer', '0', '打印转让清单', null, null);
INSERT INTO `abc_btn` VALUES ('198', '生成页面', 'icon-ok', 'SitePub', '0', '生成页面', null, null);
INSERT INTO `abc_btn` VALUES ('204', '发布', 'icon-ok', 'Release', '0', '发布', '2014-12-06 18:06:47', null);
INSERT INTO `abc_btn` VALUES ('206', '强制满标', 'icon-coins', 'ComFull', '0', '强制满标', '2014-12-06 18:32:40', null);
INSERT INTO `abc_btn` VALUES ('207', '测试', null, 'deltest', '1', 'aaaaaaaaaaaaa', '2014-12-08 10:04:29', null);
INSERT INTO `abc_btn` VALUES ('208', '测试', null, 'deltest', '1', 'aaaaaaaaaaaaa', '2014-12-15 13:51:58', null);
INSERT INTO `abc_btn` VALUES ('209', '测试', null, 'deltest', '1', 'aaaaaaaaaaaaa', '2015-01-07 17:33:18', null);
INSERT INTO `abc_btn` VALUES ('210', '测试', null, 'deltest', '1', 'aaaaaaaaaaaaa', '2015-01-08 17:56:44', null);
INSERT INTO `abc_btn` VALUES ('211', '测试', null, 'deltest', '1', 'aaaaaaaaaaaaa', '2015-01-08 17:56:44', null);
INSERT INTO `abc_btn` VALUES ('212', '测试', null, 'deltest', '1', 'aaaaaaaaaaaaa', '2015-01-08 18:04:48', null);
INSERT INTO `abc_btn` VALUES ('213', '测试', null, 'deltest', '1', 'aaaaaaaaaaaaa', '2015-01-08 18:15:45', null);
INSERT INTO `abc_btn` VALUES ('214', '测试', null, 'deltest', '1', 'aaaaaaaaaaaaa', '2015-01-09 10:36:34', null);
INSERT INTO `abc_btn` VALUES ('215', '测试', null, 'deltest', '1', 'aaaaaaaaaaaaa', '2015-01-09 10:48:10', null);
INSERT INTO `abc_btn` VALUES ('216', '测试', null, 'deltest', '1', 'aaaaaaaaaaaaa', '2015-01-09 16:20:11', null);
INSERT INTO `abc_btn` VALUES ('217', '测试', null, 'deltest', '1', 'aaaaaaaaaaaaa', '2015-01-09 16:28:13', null);
INSERT INTO `abc_btn` VALUES ('218', '测试', null, 'deltest', '1', 'aaaaaaaaaaaaa', '2015-01-12 18:18:55', null);

-- ----------------------------
-- Table structure for `abc_buy_loan`
-- ----------------------------
DROP TABLE IF EXISTS `abc_buy_loan`;
CREATE TABLE `abc_buy_loan` (
  `bl_id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `bl_origin_id` int(11) NOT NULL COMMENT '原始贷款id',
  `bl_user_id` int(11) NOT NULL COMMENT '收购人',
  `bl_buy_money` decimal(18,2) NOT NULL COMMENT '收购金额',
  `bl_buy_total` decimal(18,2) NOT NULL COMMENT '收购债权总额',
  `bl_buy_period` int(11) DEFAULT NULL COMMENT '收购期数',
  `bl_fee` decimal(18,2) DEFAULT NULL COMMENT '收购手续费',
  `bl_state` tinyint(1) NOT NULL COMMENT '收购状态 -1:已删除 1:收购申请待审核 2：收购申请审核通过 3：收购申请审核未通过 4:收购中 5：满标待审 6：满标审核已通过 7：满标审核未通过 8：已流标 9:划转中 10:已划转',
  `bl_createtime` datetime NOT NULL COMMENT '创建日期',
  `bl_start_time` datetime DEFAULT NULL COMMENT '认购开始日期',
  `bl_end_time` int(11) DEFAULT NULL COMMENT '认购结束日期',
  `bl_full_time` datetime DEFAULT NULL COMMENT '满标日期',
  `bl_full_transferedtime` datetime DEFAULT NULL COMMENT '放款成功日期',
  `bl_is_transfer` tinyint(1) NOT NULL COMMENT '划转状态 1:已划转 0:未划转',
  `bl_current_invest` decimal(18,2) NOT NULL DEFAULT '0.00' COMMENT '当前投标总额',
  `bl_current_valid_invest` decimal(18,2) NOT NULL DEFAULT '0.00' COMMENT '当前有效投标总额',
  `bl_next_payment_id` int(11) NOT NULL COMMENT '下一次还款计划id',
  PRIMARY KEY (`bl_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='债权收购表';

-- ----------------------------
-- Records of abc_buy_loan
-- ----------------------------

-- ----------------------------
-- Table structure for `abc_buy_loan_subscribe`
-- ----------------------------
DROP TABLE IF EXISTS `abc_buy_loan_subscribe`;
CREATE TABLE `abc_buy_loan_subscribe` (
  `bls_id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `bls_buy_id` int(11) NOT NULL COMMENT '收购申请id',
  `bls_loan_id` int(11) NOT NULL COMMENT '贷款id',
  `bls_user_id` int(11) NOT NULL COMMENT '转让人',
  `bls_transfer_time` datetime DEFAULT NULL COMMENT '转让日期',
  `bls_transfer_money` decimal(18,2) DEFAULT NULL COMMENT '转让金额',
  `bls_earn_money` decimal(18,2) DEFAULT NULL COMMENT '收益金额',
  `bls_state` tinyint(1) NOT NULL COMMENT '状态 －1:已删除 1:待认购 2:认购中 3:认购成功 4:认购失败 5:拒绝认购 6:暂时忽略',
  `bls_createtime` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`bls_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='投资转让表';

-- ----------------------------
-- Records of abc_buy_loan_subscribe
-- ----------------------------

-- ----------------------------
-- Table structure for `abc_buy_notify`
-- ----------------------------
DROP TABLE IF EXISTS `abc_buy_notify`;
CREATE TABLE `abc_buy_notify` (
  `tr_id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `tr_buy_id` int(11) NOT NULL COMMENT '收购申请id',
  `tr_loan_id` int(11) NOT NULL COMMENT '贷款id',
  `tr_user_id` int(11) NOT NULL COMMENT '转让人',
  `tr_transfer_time` datetime DEFAULT NULL COMMENT '转让日期',
  `tr_transfer_money` decimal(18,2) DEFAULT NULL COMMENT '转让金额',
  `tr_earn_money` decimal(18,2) DEFAULT NULL COMMENT '收益金额',
  `tr_is_frozen` tinyint(1) DEFAULT NULL COMMENT '冻结状态 1:已冻结 0:已解冻',
  `tr_is_transfer` tinyint(1) DEFAULT NULL COMMENT '是否已转让 1:已转让 0:未转让 2:已否决',
  `tr_invest_state` tinyint(1) DEFAULT NULL COMMENT '划转状态 1:已划转 0:未划转',
  PRIMARY KEY (`tr_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='投资转让表';

-- ----------------------------
-- Records of abc_buy_notify
-- ----------------------------

-- ----------------------------
-- Table structure for `abc_cash_borrower`
-- ----------------------------
DROP TABLE IF EXISTS `abc_cash_borrower`;
CREATE TABLE `abc_cash_borrower` (
  `cb_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `cb_user_id` int(11) DEFAULT NULL COMMENT '用户id',
  `cb_total_money` decimal(18,2) DEFAULT NULL COMMENT '募集总额',
  `cb_total_pay` decimal(18,2) DEFAULT NULL COMMENT '已还款总额',
  `cb_total_sett` decimal(18,2) DEFAULT NULL COMMENT '未还款总额',
  `cb_pay_fee` decimal(18,2) DEFAULT NULL COMMENT '已还服务费',
  `cb_not_pay_fee` decimal(18,2) DEFAULT NULL COMMENT '未还服务费',
  `cb_pay_money` decimal(18,2) DEFAULT NULL COMMENT '已还本金',
  `cb_pay_rate` decimal(18,2) DEFAULT NULL COMMENT '已还利息',
  `cb_pay_over_rate` decimal(18,2) DEFAULT NULL COMMENT '已还罚金',
  `cb_not_pay_money` decimal(18,2) DEFAULT NULL COMMENT '未还本金',
  `cb_not_pay_rate` decimal(18,2) DEFAULT NULL COMMENT '未还利息',
  `cb_not_pay_over_rate` decimal(18,2) DEFAULT NULL COMMENT '未还罚金',
  `cb_total_charge` decimal(18,2) DEFAULT NULL COMMENT '充值总额',
  `cb_out_cash` decimal(18,2) DEFAULT NULL COMMENT '成功提现金额',
  `cb_to_account` decimal(18,2) DEFAULT NULL COMMENT '提现到账金额',
  `cb_out_fee` decimal(18,2) DEFAULT NULL COMMENT '提现费用',
  `cb_seq_no` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '交易流水号',
  PRIMARY KEY (`cb_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='借款人资金对账表';

-- ----------------------------
-- Records of abc_cash_borrower
-- ----------------------------

-- ----------------------------
-- Table structure for `abc_cash_invester`
-- ----------------------------
DROP TABLE IF EXISTS `abc_cash_invester`;
CREATE TABLE `abc_cash_invester` (
  `ci_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `ci_user_id` int(11) DEFAULT NULL COMMENT '用户id',
  `ci_total_cash` decimal(18,2) DEFAULT NULL COMMENT '资金总额',
  `ci_useable_money` decimal(18,2) DEFAULT NULL COMMENT '可用余额',
  `ci_collect_money` decimal(18,2) DEFAULT NULL COMMENT '回收本金',
  `ci_collect_rate` decimal(18,2) DEFAULT NULL COMMENT '回收利息',
  `ci_collect_over_rate` decimal(18,2) DEFAULT NULL COMMENT '回收罚金',
  `ci_transfer_money` decimal(18,2) DEFAULT NULL COMMENT '转让债权',
  `ci_transfer_fee` decimal(18,2) DEFAULT NULL COMMENT '转让手续费',
  `ci_buy_money` decimal(18,2) DEFAULT NULL COMMENT '买入债权',
  `ci_invest_money` decimal(18,2) DEFAULT NULL COMMENT '投资金额',
  `ci_invest_frozen` decimal(18,2) DEFAULT NULL COMMENT '投资冻结金额',
  `ci_total_incharge` decimal(18,2) DEFAULT NULL COMMENT '充值总额',
  `ci_out_cash` decimal(18,2) DEFAULT NULL COMMENT '成功提现金额',
  `ci_to_account` decimal(18,2) DEFAULT NULL COMMENT '提现到账金额',
  `ci_out_fee` decimal(18,2) DEFAULT NULL COMMENT '提现费用',
  `ci_seq_no` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '交易流水号',
  PRIMARY KEY (`ci_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='投资人资金对账表';

-- ----------------------------
-- Records of abc_cash_invester
-- ----------------------------

-- ----------------------------
-- Table structure for `abc_cash_record`
-- ----------------------------
DROP TABLE IF EXISTS `abc_cash_record`;
CREATE TABLE `abc_cash_record` (
  `cr_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `cr_request_url` varchar(1024) COLLATE utf8_bin DEFAULT NULL COMMENT '请求url',
  `cr_request_method` varchar(8) COLLATE utf8_bin DEFAULT NULL COMMENT '请求方式',
  `cr_request_parameter` varchar(4096) COLLATE utf8_bin DEFAULT NULL COMMENT '请求参数',
  `cr_seq_no` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '交易流水号',
  `cr_response` varchar(2048) COLLATE utf8_bin DEFAULT NULL COMMENT '支付接口返回数据',
  `cr_response_state` int(11) DEFAULT NULL COMMENT '支付请求的返回状态',
  `cr_money_amount` decimal(18,2) DEFAULT NULL COMMENT '支付钱数',
  `cr_operate_type` int(11) DEFAULT NULL COMMENT '支付操作类型（转账，退费，冻结，解冻，充值，提现）',
  `cr_operate_date` datetime DEFAULT NULL COMMENT '操作日期',
  PRIMARY KEY (`cr_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='资金操作记录表';

-- ----------------------------
-- Records of abc_cash_record
-- ----------------------------

-- ----------------------------
-- Table structure for `abc_charge_record`
-- ----------------------------
DROP TABLE IF EXISTS `abc_charge_record`;
CREATE TABLE `abc_charge_record` (
  `cr_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `cr_loan_id` int(11) DEFAULT NULL COMMENT '贷款id',
  `cr_loan_type` tinyint(4) DEFAULT NULL COMMENT '贷款类型',
  `cr_fee` decimal(18,2) DEFAULT NULL COMMENT '收取费用',
  `cr_fee_type` tinyint(4) DEFAULT NULL COMMENT '收费类型：1：平台放款手续费 2：平台服务费 3：担保服务费 4：转让手续费 5：收购手续费',
  `cr_fee_date` datetime DEFAULT NULL COMMENT '收费日期',
  `cr_seq_no` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '交易流水号',
  PRIMARY KEY (`cr_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='收费记录表';

-- ----------------------------
-- Records of abc_charge_record
-- ----------------------------

-- ----------------------------
-- Table structure for `abc_company_customer`
-- ----------------------------
DROP TABLE IF EXISTS `abc_company_customer`;
CREATE TABLE `abc_company_customer` (
  `cc_id` int(11) NOT NULL AUTO_INCREMENT,
  `cc_userid` int(11) DEFAULT NULL COMMENT '用户ID',
  `cc_company_name` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '公司名称',
  `cc_no` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '组织机构代码',
  `cc_company_type` tinyint(1) DEFAULT NULL COMMENT '企业性质	1：政府机关 2：国有企业 3：台（港、澳）资企业 4：合资企业 5：个体户 6：事业性单位 7：私营企业',
  `cc_company_scale` tinyint(1) DEFAULT NULL COMMENT '企业规模	1: 50人之内 2: 50~500人 3: 500人以上',
  `cc_total_capital` decimal(18,2) DEFAULT NULL COMMENT '资产总额',
  `cc_register_capital` decimal(18,2) DEFAULT NULL COMMENT '注册资金',
  `cc_register_date` datetime DEFAULT NULL COMMENT '注册日期',
  `cc_register_address` varchar(512) COLLATE utf8_bin DEFAULT NULL COMMENT '注册地址',
  `cc_contact_name` varchar(20) COLLATE utf8_bin DEFAULT NULL COMMENT '联系人姓名',
  `cc_contact_phone` varchar(20) COLLATE utf8_bin DEFAULT NULL COMMENT '联系电话',
  `cc_contact_address` varchar(512) COLLATE utf8_bin DEFAULT NULL COMMENT '联系地址',
  `cc_corporate` varchar(20) COLLATE utf8_bin DEFAULT NULL COMMENT '法定代表人',
  `cc_doc_type` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '证件类型',
  `cc_doc_no` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '证件号码',
  `cc_company_profile` text COLLATE utf8_bin COMMENT '公司简介',
  `cc_company_achievement` text COLLATE utf8_bin COMMENT '公司成就',
  `cc_company_address` varchar(512) COLLATE utf8_bin DEFAULT NULL COMMENT '公司地址',
  `cc_business_license` varchar(512) COLLATE utf8_bin DEFAULT NULL COMMENT '营业执照',
  `cc_license_no` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '营业执照号码',
  PRIMARY KEY (`cc_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin ROW_FORMAT=COMPACT COMMENT='公司客户基本信息表';

-- ----------------------------
-- Records of abc_company_customer
-- ----------------------------

-- ----------------------------
-- Table structure for `abc_company_customer_file`
-- ----------------------------
DROP TABLE IF EXISTS `abc_company_customer_file`;
CREATE TABLE `abc_company_customer_file` (
  `ccf_id` int(11) NOT NULL AUTO_INCREMENT,
  `ccf_company_customer_id` int(11) DEFAULT NULL COMMENT '客户ID',
  `ccf_file_name` varchar(128) COLLATE utf8_bin DEFAULT NULL COMMENT '附件名称',
  `ccf_file_url` varchar(128) COLLATE utf8_bin DEFAULT NULL COMMENT '附近路径',
  PRIMARY KEY (`ccf_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin ROW_FORMAT=COMPACT COMMENT='公司客户附件信息表';

-- ----------------------------
-- Records of abc_company_customer_file
-- ----------------------------

-- ----------------------------
-- Table structure for `abc_credit_apply`
-- ----------------------------
DROP TABLE IF EXISTS `abc_credit_apply`;
CREATE TABLE `abc_credit_apply` (
  `credit_apply_id` int(11) NOT NULL AUTO_INCREMENT,
  `credit_user_id` int(11) DEFAULT NULL COMMENT '申请人',
  `credit_type` tinyint(1) DEFAULT NULL COMMENT '申请额度类型  1：借款信用额度 2：投资担保额度 3：借款担保额度',
  `credit_old_amount` decimal(18,2) DEFAULT NULL COMMENT '原来的信用额度',
  `credit_apply_amount` decimal(18,2) DEFAULT NULL COMMENT '申请额度',
  `credit_review_amount` decimal(18,2) DEFAULT NULL COMMENT '审核额度',
  `credit_apply_date` datetime DEFAULT NULL COMMENT '申请日期',
  `credit_review_state` tinyint(1) DEFAULT NULL COMMENT '审核状态	0：待审核 1：审核已通过 2：审核未通过',
  `credit_apply_note` varchar(512) COLLATE utf8_bin DEFAULT NULL COMMENT '详细说明',
  `credit_last_modify_time` datetime DEFAULT NULL,
  PRIMARY KEY (`credit_apply_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin ROW_FORMAT=COMPACT COMMENT='信用额度申请表';

-- ----------------------------
-- Records of abc_credit_apply
-- ----------------------------

-- ----------------------------
-- Table structure for `abc_deal_record`
-- ----------------------------
DROP TABLE IF EXISTS `abc_deal_record`;
CREATE TABLE `abc_deal_record` (
  `dr_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `dr_out_seq_no` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '资金调用流水号，通过其可以确定一个唯一的资金调用,暂时未使用。在第三方支付接口不支持批量操作时使用。',
  `dr_inner_seq_no` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '内部交易流水号，一个交易流水号对应多个资金调用流水号',
  `dr_pay_account` varchar(18) COLLATE utf8_bin DEFAULT NULL COMMENT '付款人账户id account_info表',
  `dr_receive_account` varchar(18) COLLATE utf8_bin DEFAULT NULL COMMENT '收款人账户id account_info',
  `dr_money_amount` decimal(18,2) DEFAULT NULL COMMENT '交易金钱数',
  `dr_detail_type` int(2) DEFAULT NULL COMMENT '交易细则类型，如果没有则为交易类型(平台手续费、平台服务费、担保费、本金、利息等)',
  `dr_type` int(2) DEFAULT NULL COMMENT '交易类型(0投资，1还款，2收费，3资金划转，4退费)',
  `dr_business_id` int(11) DEFAULT NULL COMMENT '交易类型对应的交易的业务类型，做外键使用',
  `dr_cash_id` int(11) DEFAULT NULL COMMENT '资金操作记录表id',
  `dr_state` int(1) DEFAULT NULL COMMENT '交易状态,0 等待响应，1 成功 2 失败',
  `dr_operate_date` datetime DEFAULT NULL COMMENT '交易创建日期',
  `dr_operator` int(11) DEFAULT NULL COMMENT '交易操作人',
  PRIMARY KEY (`dr_id`),
  KEY `seqNoIndex` (`dr_inner_seq_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='交易记录表';

-- ----------------------------
-- Records of abc_deal_record
-- ----------------------------

-- ----------------------------
-- Table structure for `abc_down_recharge`
-- ----------------------------
DROP TABLE IF EXISTS `abc_down_recharge`;
CREATE TABLE `abc_down_recharge` (
  `dr_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `dr_user_id` int(11) DEFAULT NULL COMMENT '充值人id 外键 user表',
  `dr_bank_id` int(11) DEFAULT NULL COMMENT '充值银行ID 外键mon_bank_info',
  `dr_bank_no` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '充值银行卡号',
  `dr_date` datetime DEFAULT NULL COMMENT '充值日期',
  `dr_money` decimal(18,2) DEFAULT '0.00' COMMENT '充值金额',
  `dr_check_state` int(2) DEFAULT NULL COMMENT '审核状态',
  `dr_check_operator` int(11) DEFAULT NULL COMMENT '审核人id 外键employee',
  `dr_check_date` datetime DEFAULT NULL COMMENT '审核日期',
  `dr_check_idear` varchar(512) COLLATE utf8_bin DEFAULT NULL COMMENT '审核意见',
  `dr_file_id` varchar(512) COLLATE utf8_bin DEFAULT NULL COMMENT '影像资料dataID 非主键 关联file_upload_info表的data_id',
  `dr_mark` varchar(512) COLLATE utf8_bin DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`dr_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of abc_down_recharge
-- ----------------------------

-- ----------------------------
-- Table structure for `abc_employee`
-- ----------------------------
DROP TABLE IF EXISTS `abc_employee`;
CREATE TABLE `abc_employee` (
  `emp_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `emp_name` varchar(30) COLLATE utf8_bin NOT NULL COMMENT '用户名',
  `emp_real_name` varchar(30) COLLATE utf8_bin DEFAULT NULL COMMENT '真实姓名',
  `emp_password` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '密码',
  `emp_no` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '员工工号',
  `emp_doc_type` varchar(20) COLLATE utf8_bin DEFAULT NULL COMMENT '证件类型',
  `emp_doc_no` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '证件号码',
  `emp_email` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '电子邮箱',
  `emp_birthday` datetime DEFAULT NULL COMMENT '出生日期',
  `emp_worktime` datetime DEFAULT NULL COMMENT '参加工作时间',
  `emp_entrytime` datetime DEFAULT NULL COMMENT '入职时间',
  `emp_mobile` varchar(20) COLLATE utf8_bin DEFAULT NULL COMMENT '手机号码',
  `emp_phone` varchar(20) COLLATE utf8_bin DEFAULT NULL COMMENT '家庭电话',
  `emp_photo` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '照片',
  `emp_address` varchar(1000) COLLATE utf8_bin DEFAULT NULL COMMENT '详细地址',
  `emp_note` varchar(1000) COLLATE utf8_bin DEFAULT NULL COMMENT '备注',
  `emp_login_count` int(11) DEFAULT '0' COMMENT '登录次数',
  `emp_type` tinyint(1) DEFAULT NULL COMMENT '1：平台用户 2：小贷/担保用户',
  `emp_state` tinyint(1) DEFAULT '1' COMMENT '1：启用 0：停用 -1 : 已删除',
  `emp_area` varchar(20) COLLATE utf8_bin DEFAULT NULL COMMENT '所属地区',
  `emp_createtime` datetime DEFAULT NULL COMMENT '创建时间',
  `emp_lastlogintime` datetime DEFAULT NULL COMMENT '最近一次登录时间',
  `emp_org_id` int(11) DEFAULT NULL COMMENT '机构ID（目前机构和员工是一对一的关系，即一个机构有一个账户登录。如果不是机构用户，可以为空。机构ID放在员工表方便扩展为一个机构有多个用户的情形）',
  `emp_sex` tinyint(1) DEFAULT NULL COMMENT '0：女 1：男',
  `emp_qq` varchar(20) COLLATE utf8_bin DEFAULT NULL COMMENT 'QQ',
  `emp_head_img` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '头像',
  `emp_isonline` tinyint(1) DEFAULT '0' COMMENT '1：在线 0：离线',
  `emp_last_modify_time` datetime DEFAULT NULL,
  PRIMARY KEY (`emp_id`)
) ENGINE=InnoDB AUTO_INCREMENT=112 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='员工信息表';

-- ----------------------------
-- Records of abc_employee
-- ----------------------------
INSERT INTO `abc_employee` VALUES ('1', 'admin', '管理员', 'c4ca4238a0b923820dcc509a6f75849b', null, null, null, null, '2015-01-22 00:00:00', null, null, null, '', null, null, null, '18', null, '1', '1000', '2014-12-01 20:29:29', '2015-01-23 11:20:21', null, '1', '', null, '1', '2015-01-23 11:19:59');

-- ----------------------------
-- Table structure for `abc_employee_role`
-- ----------------------------
DROP TABLE IF EXISTS `abc_employee_role`;
CREATE TABLE `abc_employee_role` (
  `er_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键自增',
  `role_id` int(11) NOT NULL COMMENT 'abc_role外键',
  `emp_id` int(11) NOT NULL COMMENT 'abc_employee外键',
  PRIMARY KEY (`er_id`)
) ENGINE=InnoDB AUTO_INCREMENT=184 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='角色用户关联表';

-- ----------------------------
-- Records of abc_employee_role
-- ----------------------------
INSERT INTO `abc_employee_role` VALUES ('177', '6', '1');

-- ----------------------------
-- Table structure for `abc_fee_setting`
-- ----------------------------
DROP TABLE IF EXISTS `abc_fee_setting`;
CREATE TABLE `abc_fee_setting` (
  `fs_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `fs_type` int(11) DEFAULT NULL COMMENT '费用类型，对应FeeType枚举',
  `fs_loan_category` int(11) DEFAULT NULL COMMENT '项目类型，对应LoanCategory枚举',
  `fs_charge_type` int(11) DEFAULT NULL COMMENT '收费类型，对应ChargeType枚举',
  `fs_rate` double(18,2) DEFAULT NULL COMMENT '收费比例，适用于收费类型为“按比例收费”',
  `fs_min_amount` decimal(18,2) DEFAULT NULL COMMENT '最小金额，适用于收费类型为“按比例收费”',
  `fs_max_amount` decimal(18,2) DEFAULT NULL COMMENT '最大金额，适用于收费类型为“按比例收费”',
  `fs_accurate_amount` decimal(18,2) DEFAULT NULL COMMENT '确定的金额，适用于收费类型为“按每笔收费”',
  `fs_update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `fs_deleted` int(10) unsigned zerofill DEFAULT NULL COMMENT '是否逻辑删除 0否 1是 默认0未删除',
  PRIMARY KEY (`fs_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of abc_fee_setting
-- ----------------------------
INSERT INTO `abc_fee_setting` VALUES ('1', '1', '2', '1', '2.00', '10.00', '100.00', '12.00', '2014-12-11 11:32:39', '0000000000');
INSERT INTO `abc_fee_setting` VALUES ('2', '2', '2', '1', '2.00', '10.00', '100.00', '12.00', '2014-12-09 17:56:29', '0000000000');
INSERT INTO `abc_fee_setting` VALUES ('3', '3', '2', '1', '2.00', '10.00', '100.00', '12.00', '2014-12-09 16:22:15', '0000000000');
INSERT INTO `abc_fee_setting` VALUES ('4', '4', '2', '1', '2.00', '10.00', '100.00', '12.00', '2014-12-09 16:22:15', '0000000000');
INSERT INTO `abc_fee_setting` VALUES ('5', '5', '2', '1', '2.00', '10.00', '100.00', '12.00', '2014-12-09 16:22:15', '0000000000');
INSERT INTO `abc_fee_setting` VALUES ('6', '1', '1', '1', '2.00', '11.00', '111.00', '11.00', '2014-12-23 15:39:22', '0000000000');
INSERT INTO `abc_fee_setting` VALUES ('7', '2', '1', '1', '2.00', '11.00', '111.00', '111.00', '2014-12-23 15:40:20', '0000000000');
INSERT INTO `abc_fee_setting` VALUES ('23', '3', '1', '1', '3.00', '4.00', '55.00', '666.00', '2014-12-09 15:40:37', '0000000000');
INSERT INTO `abc_fee_setting` VALUES ('24', '4', '1', '1', '2.00', '3.00', '44.00', '555.00', '2014-12-22 15:40:56', '0000000000');
INSERT INTO `abc_fee_setting` VALUES ('25', '5', '1', '1', '22.00', '22.00', '22.00', '222.00', '2014-12-29 15:41:09', '0000000000');
INSERT INTO `abc_fee_setting` VALUES ('26', '1', '3', '1', '21.00', '21.00', '12.00', '1212.00', '2014-12-10 15:41:26', '0000000000');
INSERT INTO `abc_fee_setting` VALUES ('27', '2', '3', '2', '1.00', '21.00', '23.00', '23.00', '2015-01-06 15:41:47', '0000000000');
INSERT INTO `abc_fee_setting` VALUES ('28', '3', '3', '3', '3.00', '32.00', '24.00', '43.00', '2014-12-02 15:42:07', '0000000000');
INSERT INTO `abc_fee_setting` VALUES ('29', '4', '3', '2', '11.00', '23.00', '43.00', '54.00', '2014-12-23 15:42:27', '0000000000');
INSERT INTO `abc_fee_setting` VALUES ('30', '5', '3', '1', '32.00', '232.00', '323.00', '323.00', '2014-12-31 15:42:56', '0000000000');
INSERT INTO `abc_fee_setting` VALUES ('31', '1', '4', '3', '12.00', '23.00', '43.00', '54.00', '2014-12-30 15:43:13', '0000000000');
INSERT INTO `abc_fee_setting` VALUES ('32', '2', '4', '3', '44.00', '55.00', '66.00', '77.00', '2014-12-15 15:43:26', '0000000000');
INSERT INTO `abc_fee_setting` VALUES ('33', '3', '4', '2', '33.00', '33.00', '33.00', '33.00', '2014-12-30 15:43:41', '0000000000');
INSERT INTO `abc_fee_setting` VALUES ('34', '4', '4', '4', '23.00', '21.00', '21.00', '212.00', '2014-12-23 15:44:08', '0000000000');
INSERT INTO `abc_fee_setting` VALUES ('35', '5', '4', '3', '23.00', '21.00', '21.00', '32.00', '2014-12-30 15:44:23', '0000000000');
INSERT INTO `abc_fee_setting` VALUES ('36', '1', '3', '2', null, null, null, null, '2015-01-07 17:33:18', '0000000001');
INSERT INTO `abc_fee_setting` VALUES ('37', '1', '3', '2', null, null, null, null, '2015-01-08 17:56:44', '0000000001');
INSERT INTO `abc_fee_setting` VALUES ('38', '1', '3', '2', null, null, null, null, '2015-01-08 17:56:44', '0000000001');
INSERT INTO `abc_fee_setting` VALUES ('39', '1', '3', '2', null, null, null, null, '2015-01-08 18:04:48', '0000000001');
INSERT INTO `abc_fee_setting` VALUES ('40', '1', '3', '2', null, null, null, null, '2015-01-08 18:15:45', '0000000001');
INSERT INTO `abc_fee_setting` VALUES ('41', '1', '3', '2', null, null, null, null, '2015-01-09 10:36:35', '0000000001');
INSERT INTO `abc_fee_setting` VALUES ('42', '1', '3', '2', null, null, null, null, '2015-01-09 10:48:11', '0000000001');
INSERT INTO `abc_fee_setting` VALUES ('43', '1', '3', '2', null, null, null, null, '2015-01-09 16:20:12', '0000000001');
INSERT INTO `abc_fee_setting` VALUES ('44', '1', '3', '2', null, null, null, null, '2015-01-09 16:28:14', '0000000001');
INSERT INTO `abc_fee_setting` VALUES ('45', '1', '3', '2', null, null, null, null, '2015-01-12 18:18:55', '0000000001');

-- ----------------------------
-- Table structure for `abc_file_prove`
-- ----------------------------
DROP TABLE IF EXISTS `abc_file_prove`;
CREATE TABLE `abc_file_prove` (
  `fp_id` int(11) NOT NULL AUTO_INCREMENT,
  `fp_userid` int(11) DEFAULT NULL COMMENT '用户ID 关联user表',
  `fp_file_type` varchar(128) COLLATE utf8_bin DEFAULT NULL COMMENT '证明材料类型',
  `fp_file_url` varchar(512) COLLATE utf8_bin DEFAULT NULL COMMENT '证明材料路径',
  `fp_prove_date` datetime DEFAULT NULL COMMENT '申请日期',
  `fp_prove_state` tinyint(1) DEFAULT NULL COMMENT '审核状态	0：待审核 1：审核已通过 2：审核未通过',
  `fp_note` varchar(512) COLLATE utf8_bin DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`fp_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin ROW_FORMAT=COMPACT COMMENT='证明材料表';

-- ----------------------------
-- Records of abc_file_prove
-- ----------------------------

-- ----------------------------
-- Table structure for `abc_file_upload_info`
-- ----------------------------
DROP TABLE IF EXISTS `abc_file_upload_info`;
CREATE TABLE `abc_file_upload_info` (
  `fui_id` int(11) NOT NULL AUTO_INCREMENT,
  `fui_class_type` int(11) DEFAULT NULL COMMENT '模块类型',
  `fui_secondary_class` int(11) DEFAULT NULL COMMENT '二级模块类型',
  `fui_data_id` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '处理信息id',
  `fui_file_name` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '文件名称',
  `fui_file_path` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '文件路径',
  `fui_create_time` datetime DEFAULT NULL COMMENT '文件上传时间',
  `fui_state` tinyint(1) DEFAULT NULL COMMENT '0：停用 1：启用 -1:删除',
  PRIMARY KEY (`fui_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin ROW_FORMAT=COMPACT COMMENT='影像资料附件表';

-- ----------------------------
-- Records of abc_file_upload_info
-- ----------------------------

-- ----------------------------
-- Table structure for `abc_file_upload_job_record`
-- ----------------------------
DROP TABLE IF EXISTS `abc_file_upload_job_record`;
CREATE TABLE `abc_file_upload_job_record` (
  `record_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `record_file_name` varchar(128) DEFAULT NULL COMMENT '上传文件名',
  `record_status` int(11) DEFAULT NULL COMMENT '任务执行状态 0失败 1成功',
  `record_try_times` int(11) DEFAULT NULL COMMENT '任务尝试次数',
  `record_create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `record_update_time` datetime DEFAULT NULL COMMENT '最后更新时间',
  `record_refer_id` varchar(64) DEFAULT NULL COMMENT '引用id',
  `record_server_mac` varchar(32) DEFAULT NULL COMMENT '日志文件所属服务器的mac地址',
  `record_oss_url` varchar(512) DEFAULT NULL COMMENT '日志上传至oss后返回的oss的url',
  PRIMARY KEY (`record_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of abc_file_upload_job_record
-- ----------------------------

-- ----------------------------
-- Table structure for `abc_full_transfer_record`
-- ----------------------------
DROP TABLE IF EXISTS `abc_full_transfer_record`;
CREATE TABLE `abc_full_transfer_record` (
  `ftr_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `ftr_bid_id` int(11) DEFAULT NULL COMMENT '标id',
  `ftr_operator` int(11) DEFAULT NULL COMMENT '资金划转操作人',
  `ftr_user_id` int(11) DEFAULT NULL COMMENT '放款对象：用户id',
  `ftr_bid_type` tinyint(4) DEFAULT NULL COMMENT '标类型:1：正常标 2：转让标 3：收购标',
  `ftr_transfer_money` decimal(18,2) DEFAULT NULL COMMENT '实际划转金额',
  `ftr_pay_fee` decimal(18,2) DEFAULT NULL COMMENT '应收手续费',
  `ftr_guar_fee` decimal(18,2) DEFAULT NULL COMMENT '应收担保费',
  `ftr_real_pay_fee` decimal(18,2) DEFAULT '0.00' COMMENT '实收手续费',
  `ftr_real_guar_fee` decimal(18,2) DEFAULT '0.00' COMMENT '实收担保费',
  `ftr_transfer_date` datetime DEFAULT NULL COMMENT '放款日期',
  `ftr_seq_no` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '交易流水号',
  `ftr_deal_state` tinyint(1) NOT NULL DEFAULT '0' COMMENT '交易状态',
  `ftr_transfer_type` tinyint(1) NOT NULL COMMENT '资金划转类型',
  PRIMARY KEY (`ftr_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='满标资金划转';

-- ----------------------------
-- Records of abc_full_transfer_record
-- ----------------------------

-- ----------------------------
-- Table structure for `abc_fund_apply`
-- ----------------------------
DROP TABLE IF EXISTS `abc_fund_apply`;
CREATE TABLE `abc_fund_apply` (
  `fa_fund_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `fa_fund_no` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '目项编号',
  `fa_fund_name` varchar(125) COLLATE utf8_bin DEFAULT NULL COMMENT '基金名称',
  `fa_fund_comp` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '基金公司',
  `fa_fund_money` decimal(18,2) DEFAULT NULL COMMENT '预计发行规模',
  `fa_fund_period` decimal(18,2) DEFAULT NULL COMMENT '存续期',
  `fa_fund_rate` decimal(18,2) DEFAULT NULL COMMENT '预期年收益率',
  `fa_min_money` decimal(18,2) DEFAULT NULL COMMENT '最低认购金额',
  `fa_fund_industry` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '投资行业',
  `fa_pay_type` tinyint(1) DEFAULT NULL COMMENT '还款方式(1：等额本息 2：按月还息到期还本 3：等额本金)',
  `fa_fund_type` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '基金类型',
  `fa_martgage_rate` decimal(18,2) DEFAULT NULL COMMENT '抵押率',
  `fa_martgage_introl` varchar(1000) COLLATE utf8_bin DEFAULT NULL COMMENT '抵押物',
  `fa_fund_introl` varchar(1000) COLLATE utf8_bin DEFAULT NULL COMMENT '产品说明',
  `fa_fund_use` varchar(1000) COLLATE utf8_bin DEFAULT NULL COMMENT '资金用途',
  `fa_fund_risk` varchar(1000) COLLATE utf8_bin DEFAULT NULL COMMENT '风险控制',
  `fa_pay_resource` varchar(1000) COLLATE utf8_bin DEFAULT NULL COMMENT '还款来源',
  `fa_project_introl` varchar(1000) COLLATE utf8_bin DEFAULT NULL COMMENT '项目简介',
  `fa_comp_introl` varchar(1000) COLLATE utf8_bin DEFAULT NULL COMMENT '基金公司简介',
  `fa_fund_state` tinyint(1) DEFAULT NULL COMMENT '是否发布',
  `fa_add_date` datetime DEFAULT NULL COMMENT '发售日期',
  PRIMARY KEY (`fa_fund_id`)
) ENGINE=MyISAM AUTO_INCREMENT=29 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of abc_fund_apply
-- ----------------------------

-- ----------------------------
-- Table structure for `abc_fund_check`
-- ----------------------------
DROP TABLE IF EXISTS `abc_fund_check`;
CREATE TABLE `abc_fund_check` (
  `fc_check_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `fc_order_id` int(11) DEFAULT NULL COMMENT '预约ID(外键表：abc_fund_order)',
  `fc_check_emp` int(11) DEFAULT NULL COMMENT '审核人ID(外键表：abc_employee)',
  `fc_check_date` datetime DEFAULT NULL COMMENT '审核日期',
  `fc_check_idear` varchar(512) COLLATE utf8_bin DEFAULT NULL COMMENT '审核意见',
  `fc_recharge_money` decimal(18,2) DEFAULT NULL COMMENT '充值金额',
  `fc_recharge_date` datetime DEFAULT NULL COMMENT '充值日期',
  `fc_check_state` tinyint(1) DEFAULT NULL COMMENT '审核状态(1：已确认 2：已放弃)',
  PRIMARY KEY (`fc_check_id`)
) ENGINE=MyISAM AUTO_INCREMENT=24 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of abc_fund_check
-- ----------------------------

-- ----------------------------
-- Table structure for `abc_fund_order`
-- ----------------------------
DROP TABLE IF EXISTS `abc_fund_order`;
CREATE TABLE `abc_fund_order` (
  `fo_order_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `fo_fund_id` int(11) DEFAULT NULL COMMENT '有限合伙项目ID(外键表：abc_fund_apply)',
  `fo_user_id` int(11) DEFAULT NULL COMMENT '预约人',
  `fo_order_date` datetime DEFAULT NULL COMMENT '预约日期',
  `fo_user_name` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '预约人姓名',
  `fo_user_phone` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '预约人电话',
  `fo_order_state` tinyint(1) DEFAULT NULL COMMENT '预约状态(0：待审核 1：已确认 2：已放弃)',
  PRIMARY KEY (`fo_order_id`)
) ENGINE=MyISAM AUTO_INCREMENT=26 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of abc_fund_order
-- ----------------------------

-- ----------------------------
-- Table structure for `abc_fund_profit`
-- ----------------------------
DROP TABLE IF EXISTS `abc_fund_profit`;
CREATE TABLE `abc_fund_profit` (
  `fp_profit_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `fp_fund_id` int(11) DEFAULT NULL COMMENT '有限合伙表ID,外键表：abc_fund_apply',
  `fp_min_money` decimal(18,2) DEFAULT NULL COMMENT '最小金额',
  `fp_max_money` decimal(18,2) DEFAULT NULL COMMENT '最大金额',
  `fp_profit_yields` decimal(18,2) DEFAULT NULL COMMENT '年化收益率',
  PRIMARY KEY (`fp_profit_id`)
) ENGINE=MyISAM AUTO_INCREMENT=11 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='有限合伙预约-收益说明表';

-- ----------------------------
-- Records of abc_fund_profit
-- ----------------------------

-- ----------------------------
-- Table structure for `abc_government`
-- ----------------------------
DROP TABLE IF EXISTS `abc_government`;
CREATE TABLE `abc_government` (
  `gov_id` int(11) NOT NULL AUTO_INCREMENT,
  `gov_name` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '机构名称',
  `gov_email` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '公司邮箱',
  `gov_no` varchar(20) COLLATE utf8_bin DEFAULT NULL COMMENT '组织机构代码',
  `gov_business_license` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '营业执照号码',
  `gov_scale` tinyint(1) DEFAULT NULL COMMENT '企业规模	"0:50人以下\n1:50~500人\n2:500人以上"',
  `gov_register_capital` decimal(18,2) DEFAULT NULL COMMENT '注册资金',
  `gov_total_capital` decimal(18,2) DEFAULT NULL COMMENT '资产总额',
  `gov_max_loan_amount` decimal(18,2) DEFAULT NULL COMMENT '最大借款额度',
  `gov_register_division` varchar(20) COLLATE utf8_bin DEFAULT NULL COMMENT '注册地行政区划',
  `gov_register_address` varchar(125) COLLATE utf8_bin DEFAULT NULL COMMENT '注册地址',
  `gov_register_date` datetime DEFAULT NULL COMMENT '注册日期',
  `gov_contact` varchar(20) COLLATE utf8_bin DEFAULT NULL COMMENT '联系人',
  `gov_contact_phone` varchar(20) COLLATE utf8_bin DEFAULT NULL COMMENT '联系电话',
  `gov_customer_manager` int(11) DEFAULT NULL COMMENT '客户经理',
  `gov_corporate` varchar(20) COLLATE utf8_bin DEFAULT NULL COMMENT '法定代表人',
  `gov_doc_type` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '证件类型',
  `gov_doc_no` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '证件号码',
  `gov_credit_level` varchar(20) COLLATE utf8_bin DEFAULT NULL COMMENT '信用级别',
  `gov_area` varchar(20) COLLATE utf8_bin DEFAULT NULL COMMENT '所属地区',
  `gov_address_detail` varchar(125) COLLATE utf8_bin DEFAULT NULL COMMENT '详细地址',
  `gov_is_offer_guar` tinyint(1) DEFAULT NULL COMMENT '是否提供担保	1：是 0：否',
  `gov_max_guar_amount` decimal(18,2) DEFAULT NULL COMMENT '最大担保额度',
  `gov_sett_guar_amount` decimal(18,2) DEFAULT NULL COMMENT '可用担保额度',
  `gov_logo` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '机构logo图片',
  `gov_profile` text COLLATE utf8_bin COMMENT '公司概况',
  `gov_team_management` text COLLATE utf8_bin COMMENT '团队管理',
  `gov_development_history` text COLLATE utf8_bin COMMENT '发展历史',
  `gov_guar_card` text COLLATE utf8_bin COMMENT '融资性担保牌照',
  `gov_partner` text COLLATE utf8_bin COMMENT '合作机构',
  `gov_cooperate_agreement` text COLLATE utf8_bin COMMENT '合作协议',
  `gov_is_enable` tinyint(1) DEFAULT '1' COMMENT '是否启用	1：启用 0：停用 -1 : 已删除',
  `gov_state` tinyint(1) DEFAULT NULL COMMENT '机构状态	0：待审核 1：审核已通过 2：审核未通过',
  `gov_reviewer` int(11) DEFAULT NULL COMMENT '审核人',
  `gov_review_date` datetime DEFAULT NULL COMMENT '审核日期',
  `gov_add_emp` int(11) DEFAULT NULL COMMENT '添加人',
  `gov_add_date` datetime DEFAULT NULL COMMENT '添加日期',
  `gov_last_modify_time` datetime DEFAULT NULL,
  PRIMARY KEY (`gov_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin ROW_FORMAT=COMPACT COMMENT='机构信息表';

-- ----------------------------
-- Records of abc_government
-- ----------------------------

-- ----------------------------
-- Table structure for `abc_gov_guar`
-- ----------------------------
DROP TABLE IF EXISTS `abc_gov_guar`;
CREATE TABLE `abc_gov_guar` (
  `gg_id` int(11) NOT NULL AUTO_INCREMENT,
  `gg_gov_id` int(11) DEFAULT NULL COMMENT '机构ID',
  `gg_guar_id` int(11) DEFAULT NULL COMMENT '担保机构ID',
  `gg_guar_amount` decimal(18,2) DEFAULT '0.00' COMMENT '已担保额度',
  PRIMARY KEY (`gg_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin ROW_FORMAT=COMPACT COMMENT='机构信息与担保机构关联表';

-- ----------------------------
-- Records of abc_gov_guar
-- ----------------------------

-- ----------------------------
-- Table structure for `abc_gov_update_detail`
-- ----------------------------
DROP TABLE IF EXISTS `abc_gov_update_detail`;
CREATE TABLE `abc_gov_update_detail` (
  `guh_id` int(11) NOT NULL AUTO_INCREMENT,
  `guh_update_history_id` int(11) DEFAULT NULL COMMENT '关联更新记录表ID',
  `guh_field` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '修改字段',
  `guh_field_old` text COLLATE utf8_bin COMMENT '修改前值',
  `guh_filed_new` text COLLATE utf8_bin COMMENT '修改后值',
  PRIMARY KEY (`guh_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin ROW_FORMAT=COMPACT COMMENT='机构字段信息更改详细记录表';

-- ----------------------------
-- Records of abc_gov_update_detail
-- ----------------------------

-- ----------------------------
-- Table structure for `abc_gov_update_history`
-- ----------------------------
DROP TABLE IF EXISTS `abc_gov_update_history`;
CREATE TABLE `abc_gov_update_history` (
  `guh_id` int(11) NOT NULL AUTO_INCREMENT,
  `guh_govid` int(11) DEFAULT NULL COMMENT '机构ID 关联机构信息表',
  `guh_update_emp` int(11) DEFAULT NULL COMMENT '修改人 关联员工表',
  `guh_update_date` datetime DEFAULT NULL COMMENT '修改日期',
  `guh_update_number` int(11) DEFAULT NULL COMMENT '修改批次',
  `guh_auditor_id` int(11) DEFAULT NULL COMMENT '审核人 关联员工表（暂时没用，审核信息在review表中）',
  `guh_auditor_date` datetime DEFAULT NULL COMMENT '审核日期（暂时没用，审核信息在review表中）',
  `guh_audit_note` varchar(512) COLLATE utf8_bin DEFAULT NULL COMMENT '审核意见（暂时没用，审核信息在review表中）',
  `guh_audit_state` tinyint(1) DEFAULT NULL COMMENT '审核状态	0：待审核 1：审核已通过 2：审核未通过',
  PRIMARY KEY (`guh_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin ROW_FORMAT=COMPACT COMMENT='机构信息更新记录表，包含批次，修改人，审核人及审核结果等';

-- ----------------------------
-- Records of abc_gov_update_history
-- ----------------------------

-- ----------------------------
-- Table structure for `abc_invest`
-- ----------------------------
DROP TABLE IF EXISTS `abc_invest`;
CREATE TABLE `abc_invest` (
  `in_id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `in_user_id` int(11) NOT NULL COMMENT '投资人',
  `in_createtime` datetime NOT NULL COMMENT '投资日期',
  `in_modifytime` datetime NOT NULL COMMENT '更新日期',
  `in_invest_money` decimal(18,2) NOT NULL DEFAULT '0.00' COMMENT '投资金额',
  `in_valid_invest_money` decimal(18,2) NOT NULL DEFAULT '0.00' COMMENT '有效投资金额',
  `in_invest_state` tinyint(1) NOT NULL COMMENT '投资状态 -1：已删除 0：未支付 1:支付失败 2:支付成功 3:已撤资 4:待收益 5:被转让 6:被收购 7:收益完成',
  `in_inner_seq_no` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '内部交易流水号',
  `in_withdraw_seq_no` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '撤投交易流水号',
  `in_origin_id` int(11) NOT NULL COMMENT '原始贷款id',
  `in_bid_type` tinyint(11) NOT NULL COMMENT '标类型：0:普通标 1:转让标 2:收购标',
  `in_bid_id` int(11) NOT NULL COMMENT '根据标类型，外键到不同的标',
  PRIMARY KEY (`in_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='项目投资表';

-- ----------------------------
-- Records of abc_invest
-- ----------------------------

-- ----------------------------
-- Table structure for `abc_invest_order`
-- ----------------------------
DROP TABLE IF EXISTS `abc_invest_order`;
CREATE TABLE `abc_invest_order` (
  `io_id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `io_origin_id` int(11) NOT NULL COMMENT '原始贷款id',
  `io_user_id` int(11) NOT NULL COMMENT '投资人',
  `io_order_money` decimal(18,2) NOT NULL COMMENT '订单金额',
  `io_bid_type` tinyint(1) NOT NULL COMMENT '标类型 1：正常标 2：转让标 3：收购标',
  `io_bid_id` int(11) NOT NULL COMMENT '根据标类型，外键到不同的标',
  `io_inner_seq_no` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '内部交易流水号',
  `io_order_state` tinyint(1) NOT NULL COMMENT '订单状态 -1：已删除 0：未支付 1:支付失败 2:支付成功',
  `io_createtime` datetime NOT NULL COMMENT '订单创建日期',
  `io_modifytime` datetime NOT NULL COMMENT '订单更新日期',
  PRIMARY KEY (`io_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='项目投资订单表';

-- ----------------------------
-- Records of abc_invest_order
-- ----------------------------

-- ----------------------------
-- Table structure for `abc_invest_record`
-- ----------------------------
DROP TABLE IF EXISTS `abc_invest_record`;
CREATE TABLE `abc_invest_record` (
  `ir_id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `ir_loan_id` int(11) NOT NULL COMMENT '贷款id',
  `ir_transfer_id` int(11) DEFAULT NULL COMMENT '转让申请id',
  `ir_invest_emp` int(11) NOT NULL COMMENT '投资人',
  `ir_invest_money` decimal(18,2) DEFAULT NULL COMMENT '投资金额',
  `ir_credit_money` decimal(18,2) DEFAULT NULL COMMENT '实际项目金额',
  `ir_invest_time` datetime DEFAULT NULL COMMENT '投资日期',
  `ir_order_time` datetime DEFAULT NULL COMMENT '投标订单日期',
  `ir_order_no` varchar(11) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '投标订单流水号',
  `ir_transact_mark` varchar(11) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '商户专属交易唯一标识',
  `ir_frozen_state` tinyint(1) DEFAULT NULL COMMENT '冻结状态 1：已冻结 0：已解冻',
  `ir_transfer_state` tinyint(1) DEFAULT NULL COMMENT '划转状态 1：已划转 0：未划转',
  `ir_invest_type` tinyint(1) DEFAULT NULL COMMENT '投资类型 1：正常标 2：转让标 3：收购标',
  `ir_deleted` tinyint(1) DEFAULT NULL COMMENT '删除标记 1：已删除 0：未删除',
  `ir_cash_record_id` int(11) DEFAULT NULL COMMENT '资金操作记录id',
  PRIMARY KEY (`ir_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='投资项目表';

-- ----------------------------
-- Records of abc_invest_record
-- ----------------------------

-- ----------------------------
-- Table structure for `abc_invite`
-- ----------------------------
DROP TABLE IF EXISTS `abc_invite`;
CREATE TABLE `abc_invite` (
  `invite_id` int(11) NOT NULL AUTO_INCREMENT,
  `invite_user_id` int(11) DEFAULT NULL COMMENT '邀请人id',
  `invite_invitee_id` int(11) DEFAULT NULL COMMENT '被邀请人id',
  `invite_user_type` tinyint(1) DEFAULT NULL COMMENT '邀请人类型 1：前台网友 2：平台用户',
  `invite_is_valid` tinyint(1) DEFAULT NULL COMMENT '是否已生效  0：未生效 1：已生效',
  `invite_start_date` datetime DEFAULT NULL COMMENT '开始日期',
  `invite_end_date` datetime DEFAULT NULL COMMENT '到期日期',
  `invite_reward_money` decimal(18,2) DEFAULT NULL COMMENT '奖励邀请人金额',
  `invite_reward_state` tinyint(1) DEFAULT NULL COMMENT '是否已使用	0：未使用 1：已使用',
  `invite_create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `invite_last_modify_time` datetime DEFAULT NULL COMMENT '最后修改时间',
  PRIMARY KEY (`invite_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin ROW_FORMAT=COMPACT COMMENT='推荐邀请表';

-- ----------------------------
-- Records of abc_invite
-- ----------------------------

-- ----------------------------
-- Table structure for `abc_level`
-- ----------------------------
DROP TABLE IF EXISTS `abc_level`;
CREATE TABLE `abc_level` (
  `lev_id` int(11) NOT NULL AUTO_INCREMENT,
  `lev_name` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '等级名称',
  `lev_min_score` int(11) DEFAULT NULL COMMENT '等级最大积分值',
  `lev_max_score` int(11) DEFAULT NULL COMMENT '等级最大积分值',
  `lev_icon` varchar(512) COLLATE utf8_bin DEFAULT NULL COMMENT '等级图片路径',
  PRIMARY KEY (`lev_id`)
) ENGINE=InnoDB AUTO_INCREMENT=91 DEFAULT CHARSET=utf8 COLLATE=utf8_bin ROW_FORMAT=COMPACT COMMENT='等级信息表';

-- ----------------------------
-- Records of abc_level
-- ----------------------------
INSERT INTO `abc_level` VALUES ('83', '一级', '0', '9', '/Images/level/s_red_1.gif');
INSERT INTO `abc_level` VALUES ('84', '二级', '10', '19', '/Images/level/s_red_2.gif');
INSERT INTO `abc_level` VALUES ('85', '三级', '20', '29', '/Images/level/s_red_3.gif');
INSERT INTO `abc_level` VALUES ('86', '四级', '30', '39', '/Images/level/s_red_4.gif');
INSERT INTO `abc_level` VALUES ('87', '五级', '40', '49', '/Images/level/s_red_5.gif');
INSERT INTO `abc_level` VALUES ('88', '六级', '50', '59', '/Images/level/s_blue_1.gif');
INSERT INTO `abc_level` VALUES ('89', '七级', '60', '69', '/Images/level/s_blue_2.gif');
INSERT INTO `abc_level` VALUES ('90', '八级', '70', '79', '/Images/level/s_blue_3.gif');

-- ----------------------------
-- Table structure for `abc_loan`
-- ----------------------------
DROP TABLE IF EXISTS `abc_loan`;
CREATE TABLE `abc_loan` (
  `loan_id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `loan_logo` varchar(256) DEFAULT NULL COMMENT '项目logo',
  `loan_from_intent` tinyint(1) DEFAULT NULL COMMENT '是否来自前台意向',
  `loan_intent_id` int(11) DEFAULT NULL COMMENT '意向申请id',
  `loan_no` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '项目编号',
  `loan_emp_type` tinyint(1) DEFAULT '0' COMMENT '借款人类型 1:个人 2:企业 3:借款结构 4:平台',
  `loan_user_id` int(11) NOT NULL COMMENT '借款人id',
  `loan_gov` int(11) DEFAULT NULL COMMENT '借款机构id 外键',
  `loan_guar_gov` int(11) DEFAULT NULL COMMENT '担保机构id 外键',
  `loan_money` decimal(18,2) NOT NULL COMMENT '借款金额',
  `loan_rate` decimal(18,2) NOT NULL COMMENT '年化收益率',
  `loan_period` int(11) NOT NULL COMMENT '借款期限',
  `loan_period_type` tinyint(1) NOT NULL COMMENT '期限类型 1:年 2:月 3:日',
  `loan_min_invest` decimal(18,2) NOT NULL DEFAULT '0.00' COMMENT '最低投标金额',
  `loan_max_invest` decimal(18,2) DEFAULT NULL COMMENT '最高投标金额',
  `loan_current_invest` decimal(18,2) NOT NULL DEFAULT '0.00' COMMENT '当前投标总额',
  `loan_current_valid_invest` decimal(18,2) NOT NULL DEFAULT '0.00' COMMENT '当前有效投标总额',
  `loan_pay_type` tinyint(1) NOT NULL DEFAULT '0' COMMENT '还款方式 1:等额本息 2:按月还息到月还本 3:等额本金',
  `loan_product` int(11) DEFAULT NULL COMMENT '项目类型 外键',
  `loan_invest_starttime` datetime DEFAULT NULL COMMENT '投资开始时间',
  `loan_invest_endtime` datetime DEFAULT NULL COMMENT '投资结束时间',
  `loan_invest_fulltime` datetime DEFAULT NULL COMMENT '投资满标时间',
  `loan_full_transferedtime` datetime DEFAULT NULL COMMENT '放款成功日期',
  `loan_clear_type` tinyint(1) NOT NULL COMMENT '结算方式 1:固定还款日 2:非固定还款日',
  `loan_use` varchar(512) DEFAULT NULL COMMENT '借款用途',
  `loan_note` text COMMENT '项目备注',
  `loan_state` tinyint(2) NOT NULL COMMENT '项目状态 -1:已删除 1:意向待审核 2:意向审核通过 3:意向审核未通过 4:待项目初审 5:项目初审通过 6:项目初审已退回 7:项目初审未通过 8:待发布 9:招标中 10:满标待审 11:满标审核通过 12:满标审核未通过 13:已流标 14:划转中 15:还款中 16:已结清 17: 融资维护待审核 18: 融资维护审核通过 19: 融资维护审核未通过',
  `loan_category` tinyint(1) NOT NULL COMMENT '项目分类 1:企业经营贷 2:房屋抵押贷 3:汽车抵押贷 4:个人轻松贷',
  `loan_category_id` int(11) DEFAULT NULL COMMENT '项目分类 外键',
  `loan_file_url` varchar(512) DEFAULT NULL COMMENT '附件url',
  `loan_creator` int(11) NOT NULL COMMENT '添加人 外键',
  `loan_modifier` int(11) NOT NULL COMMENT '修改人 外键',
  `loan_createtime` datetime NOT NULL COMMENT '创建时间',
  `loan_modifiytime` datetime NOT NULL COMMENT '修改时间',
  `loan_deleted` tinyint(1) NOT NULL COMMENT '删除标记 0:未删除 1:已删除',
  PRIMARY KEY (`loan_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='项目申请表';

-- ----------------------------
-- Records of abc_loan
-- ----------------------------

-- ----------------------------
-- Table structure for `abc_loan_car`
-- ----------------------------
DROP TABLE IF EXISTS `abc_loan_car`;
CREATE TABLE `abc_loan_car` (
  `lc_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `lc_loan_id` int(11) DEFAULT NULL COMMENT '贷款id',
  `lc_car_brand` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '汽车品牌',
  `lc_car_series` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '汽车车系',
  `lc_car_output` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '汽车排量',
  `lc_car_color` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '汽车颜色',
  `lc_buy_year` int(11) DEFAULT NULL COMMENT '购买年份',
  `lc_car_time` datetime DEFAULT NULL COMMENT '上牌日期',
  `lc_car_run` decimal(18,2) DEFAULT NULL COMMENT '里程数',
  `lc_assess_money` decimal(18,2) DEFAULT NULL COMMENT '评估价格',
  `lc_car_address` varchar(512) COLLATE utf8_bin DEFAULT NULL COMMENT '汽车现址',
  `lc_createtime` datetime NOT NULL COMMENT '添加时间',
  `lc_modifytime` datetime NOT NULL COMMENT '修改时间',
  `lc_is_deleted` tinyint(1) unsigned zerofill NOT NULL DEFAULT '0' COMMENT '是否被删除 0:否 1:是',
  PRIMARY KEY (`lc_id`)
) ENGINE=MyISAM AUTO_INCREMENT=53 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='项目-汽车抵押表';

-- ----------------------------
-- Records of abc_loan_car
-- ----------------------------

-- ----------------------------
-- Table structure for `abc_loan_cust`
-- ----------------------------
DROP TABLE IF EXISTS `abc_loan_cust`;
CREATE TABLE `abc_loan_cust` (
  `lcu_id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `lcu_loan_id` varchar(64) NOT NULL DEFAULT '' COMMENT '贷款id',
  `lcu_cust_name` varchar(64) NOT NULL DEFAULT '' COMMENT '公司名称',
  `lcu_cust_type` varchar(64) DEFAULT NULL COMMENT '公司性质',
  `lcu_cust_industry` varchar(64) DEFAULT NULL COMMENT '所属行业',
  `lcu_cust_no` varchar(64) NOT NULL DEFAULT '' COMMENT '组织机构代码',
  `lcu_tax_no` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '税务登记号',
  `lcu_biz_no` varchar(64) DEFAULT '' COMMENT '营业执照',
  `lcu_total_capital` decimal(18,2) DEFAULT NULL COMMENT '资产总额',
  `lcu_cust_scale` tinyint(1) DEFAULT NULL COMMENT '公司规模 0:50人以下 1:50-500人 2:500人以上',
  `lcu_legal_person` varchar(64) DEFAULT NULL COMMENT '法人姓名',
  `lcu_card_type` varchar(64) DEFAULT NULL COMMENT '证件类型',
  `lcu_card_no` varchar(64) DEFAULT NULL COMMENT '证件号码',
  `lcu_contact_person` varchar(20) DEFAULT NULL COMMENT '联系人',
  `lcu_contact_phone` varchar(20) DEFAULT NULL COMMENT '联系电话',
  `lcu_month_salary` decimal(18,0) DEFAULT NULL COMMENT '月收入',
  `lcu_regist_money` decimal(18,2) DEFAULT NULL COMMENT '注册资本',
  `lcu_regist_time` datetime DEFAULT NULL COMMENT '注册日期',
  `lcu_regist_no` varchar(32) DEFAULT NULL COMMENT '注册登记号',
  `lcu_regist_address` varchar(512) DEFAULT NULL COMMENT '注册地址',
  `lcu_area_id` varchar(32) DEFAULT NULL COMMENT '所属地区',
  `lcu_area_address` varchar(512) DEFAULT NULL COMMENT '详细地址',
  PRIMARY KEY (`lcu_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='项目-企业经营贷-借款人信息表';

-- ----------------------------
-- Records of abc_loan_cust
-- ----------------------------

-- ----------------------------
-- Table structure for `abc_loan_house`
-- ----------------------------
DROP TABLE IF EXISTS `abc_loan_house`;
CREATE TABLE `abc_loan_house` (
  `lh_id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `lh_loan_id` int(11) NOT NULL COMMENT '贷款id',
  `lh_house_measure` decimal(18,2) DEFAULT NULL COMMENT '房产面积',
  `lh_cover_measure` decimal(18,2) DEFAULT NULL COMMENT '占地面积',
  `lh_house_no` varchar(11) NOT NULL DEFAULT '' COMMENT '房产编号',
  `lh_house_area` varchar(128) DEFAULT NULL COMMENT '所属小区',
  `lh_house_age` decimal(18,2) DEFAULT NULL COMMENT '房龄',
  `lh_is_mortgage` tinyint(1) DEFAULT NULL COMMENT '是否按揭 1:是 0:否',
  `lh_assess_money` decimal(18,2) DEFAULT NULL COMMENT '评估价格',
  `lh_createtime` datetime NOT NULL COMMENT '添加时间',
  `lh_modifytime` datetime NOT NULL COMMENT '修改时间',
  `lh_is_deleted` tinyint(1) unsigned zerofill NOT NULL DEFAULT '0' COMMENT '是否被删除 0:否 1:是',
  PRIMARY KEY (`lh_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='项目-房产抵押表';

-- ----------------------------
-- Records of abc_loan_house
-- ----------------------------

-- ----------------------------
-- Table structure for `abc_loan_intent_apply`
-- ----------------------------
DROP TABLE IF EXISTS `abc_loan_intent_apply`;
CREATE TABLE `abc_loan_intent_apply` (
  `lia_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '申请编号 id 主键 唯一',
  `lia_intent_emp_type` int(10) DEFAULT NULL COMMENT '借款人类型 1个人、2企业 3、借款机构 4 平台',
  `lia_user_id` int(11) NOT NULL COMMENT '联系人ID',
  `lia_user_name` varchar(60) DEFAULT NULL COMMENT '联系人名称',
  `lia_phone` varchar(32) DEFAULT NULL COMMENT '手机号码',
  `lia_intent_money` decimal(18,2) DEFAULT NULL COMMENT '融资金额',
  `lia_area` int(11) DEFAULT NULL COMMENT '所在区域 以数字代替身份的名字 0：云南 1：北京 2：上海 3：重庆',
  `lia_note` varchar(500) DEFAULT NULL COMMENT '备注说明',
  `lia_intent_time` datetime DEFAULT NULL COMMENT '申请时间',
  `lia_intent_status` int(10) DEFAULT NULL COMMENT '意向标状态 -1.已删除 1 意向待审核 2 意向审核通过 3 意向审核未通过 ',
  `lia_audit_time` datetime DEFAULT NULL COMMENT '审核时间',
  `lia_audit_opinion` varchar(200) DEFAULT NULL COMMENT '审核意见',
  `lia_intent_rate` decimal(18,2) DEFAULT NULL COMMENT '融资利率(年化收益率)',
  `lia_intent_title` varchar(50) DEFAULT NULL COMMENT '申请标题',
  `lia_intent_category` int(10) DEFAULT NULL COMMENT '产品类型 1.个人信用贷、2.汽车抵押贷、3.房产抵押贷 4.企业经营贷',
  `lia_intent_no` varchar(50) DEFAULT NULL COMMENT '意向编号',
  `lia_intent_use` varchar(200) DEFAULT NULL COMMENT '融资用途',
  `lia_intent_period` int(10) DEFAULT NULL COMMENT '借款期限',
  `lia_intent_period_unit` int(1) DEFAULT NULL COMMENT '期限类型 1.年  2.个月  3.日',
  `lia_intent_paytype` int(1) DEFAULT NULL COMMENT '还款方式 1 等额本息 2.按月还息到期还本 3.等额本金 4.到期本息',
  `lia_loan_id` int(11) DEFAULT NULL COMMENT '相关联的项目ID',
  `lia_file_url` varchar(512) NOT NULL DEFAULT '' COMMENT '附件uuid',
  PRIMARY KEY (`lia_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='融资申请表';

-- ----------------------------
-- Records of abc_loan_intent_apply
-- ----------------------------

-- ----------------------------
-- Table structure for `abc_loan_person`
-- ----------------------------
DROP TABLE IF EXISTS `abc_loan_person`;
CREATE TABLE `abc_loan_person` (
  `lp_id` int(11) NOT NULL,
  `lp_loan_id` int(11) DEFAULT NULL COMMENT '项目id',
  `lp_card_type` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '证件类型',
  `lp_card_no` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '证件号码',
  `lp_is_marry` tinyint(1) DEFAULT NULL COMMENT '婚姻状况',
  `lp_contact_person` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '联系人',
  `lp_contact_phone` varchar(20) COLLATE utf8_bin DEFAULT NULL COMMENT '联系人电话',
  `lp_month_salary` decimal(18,2) DEFAULT NULL COMMENT '月收入',
  `lp_work_name` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '单位名称',
  `lp_work_type` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '单位性质',
  `lp_work_year` tinyint(2) DEFAULT NULL COMMENT '工作年限',
  `lp_area_id` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '所属地区',
  `lp_person_address` varchar(512) COLLATE utf8_bin DEFAULT NULL COMMENT '详细地址',
  PRIMARY KEY (`lp_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='项目-个人信用贷-借款人信息表';

-- ----------------------------
-- Records of abc_loan_person
-- ----------------------------

-- ----------------------------
-- Table structure for `abc_loan_trace_record`
-- ----------------------------
DROP TABLE IF EXISTS `abc_loan_trace_record`;
CREATE TABLE `abc_loan_trace_record` (
  `ltr_id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `ltr_loan_id` int(11) DEFAULT NULL COMMENT '普通标id',
  `ltr_old_loan_state` tinyint(2) DEFAULT NULL COMMENT '旧的项目状态',
  `ltr_new_loan_state` tinyint(2) DEFAULT NULL COMMENT '新的项目状态',
  `ltr_trace_operation` int(11) NOT NULL COMMENT '跟踪操作',
  `ltr_creator` int(11) NOT NULL COMMENT '创建人',
  `ltr_createtime` datetime NOT NULL COMMENT '创建时间',
  `ltr_note` varchar(512) DEFAULT NULL COMMENT '备注说明',
  PRIMARY KEY (`ltr_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='项目跟踪状态记录表';

-- ----------------------------
-- Records of abc_loan_trace_record
-- ----------------------------

-- ----------------------------
-- Table structure for `abc_login_log`
-- ----------------------------
DROP TABLE IF EXISTS `abc_login_log`;
CREATE TABLE `abc_login_log` (
  `ll_id` int(11) NOT NULL AUTO_INCREMENT,
  `ll_emp_id` int(11) DEFAULT NULL COMMENT '登录人 员工ID 关联员工表',
  `ll_ip` varchar(20) CHARACTER SET utf8 NOT NULL DEFAULT '' COMMENT '登录IP',
  `ll_login_time` datetime NOT NULL COMMENT '登录系统时间',
  `ll_logout_time` datetime DEFAULT NULL COMMENT '退出系统时间',
  `ll_login_state` tinyint(1) NOT NULL COMMENT '登录状态  -1：已删除 0：用户登录失败 1：用户登录成功 2：用户主动登出',
  PRIMARY KEY (`ll_id`)
) ENGINE=MyISAM AUTO_INCREMENT=1772 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='系统登录日志表';

-- ----------------------------
-- Records of abc_login_log
-- ----------------------------
INSERT INTO `abc_login_log` VALUES ('1742', '1', '0:0:0:0:0:0:0:1', '2015-01-22 15:24:55', null, '1');
INSERT INTO `abc_login_log` VALUES ('1743', '1', '0:0:0:0:0:0:0:1', '2015-01-22 15:47:01', null, '1');
INSERT INTO `abc_login_log` VALUES ('1744', '1', '0:0:0:0:0:0:0:1', '2015-01-22 16:06:55', null, '1');
INSERT INTO `abc_login_log` VALUES ('1745', '1', '0:0:0:0:0:0:0:1', '2015-01-22 16:28:18', null, '1');
INSERT INTO `abc_login_log` VALUES ('1746', '107', '0:0:0:0:0:0:0:1', '2015-01-22 16:33:55', null, '1');
INSERT INTO `abc_login_log` VALUES ('1747', '1', '0:0:0:0:0:0:0:1', '2015-01-22 16:34:30', null, '1');
INSERT INTO `abc_login_log` VALUES ('1748', '1', '0:0:0:0:0:0:0:1', '2015-01-22 16:37:23', null, '1');
INSERT INTO `abc_login_log` VALUES ('1749', '1', '0:0:0:0:0:0:0:1', '2015-01-22 16:38:46', null, '1');
INSERT INTO `abc_login_log` VALUES ('1750', '2', '0:0:0:0:0:0:0:1', '2015-01-22 17:00:13', null, '1');
INSERT INTO `abc_login_log` VALUES ('1751', '1', '0:0:0:0:0:0:0:1', '2015-01-22 17:00:17', null, '1');
INSERT INTO `abc_login_log` VALUES ('1752', '1', '127.0.0.1', '2015-01-22 17:05:49', null, '1');
INSERT INTO `abc_login_log` VALUES ('1753', null, '127.0.0.1', '2015-01-22 17:06:02', null, '0');
INSERT INTO `abc_login_log` VALUES ('1754', '108', '127.0.0.1', '2015-01-22 17:06:09', null, '1');
INSERT INTO `abc_login_log` VALUES ('1755', '1', '127.0.0.1', '2015-01-22 17:06:15', null, '1');
INSERT INTO `abc_login_log` VALUES ('1756', '110', '0:0:0:0:0:0:0:1', '2015-01-22 17:06:57', null, '1');
INSERT INTO `abc_login_log` VALUES ('1757', '108', '127.0.0.1', '2015-01-22 17:10:19', null, '1');
INSERT INTO `abc_login_log` VALUES ('1758', '1', '127.0.0.1', '2015-01-22 17:10:45', null, '1');
INSERT INTO `abc_login_log` VALUES ('1759', '108', '127.0.0.1', '2015-01-22 17:11:23', null, '1');
INSERT INTO `abc_login_log` VALUES ('1760', '1', '127.0.0.1', '2015-01-22 17:11:34', null, '1');
INSERT INTO `abc_login_log` VALUES ('1761', '111', '127.0.0.1', '2015-01-22 17:19:05', null, '1');
INSERT INTO `abc_login_log` VALUES ('1762', '1', '127.0.0.1', '2015-01-22 17:19:44', null, '1');
INSERT INTO `abc_login_log` VALUES ('1763', '109', '127.0.0.1', '2015-01-22 17:25:30', null, '1');
INSERT INTO `abc_login_log` VALUES ('1764', '1', '127.0.0.1', '2015-01-22 17:26:20', null, '1');
INSERT INTO `abc_login_log` VALUES ('1765', '1', '127.0.0.1', '2015-01-22 18:15:07', null, '1');
INSERT INTO `abc_login_log` VALUES ('1766', '2', '127.0.0.1', '2015-01-22 18:21:58', null, '1');
INSERT INTO `abc_login_log` VALUES ('1767', '1', '127.0.0.1', '2015-01-22 18:22:36', null, '1');
INSERT INTO `abc_login_log` VALUES ('1768', '1', '127.0.0.1', '2015-01-23 11:07:20', null, '1');
INSERT INTO `abc_login_log` VALUES ('1769', '108', '127.0.0.1', '2015-01-23 11:18:18', null, '1');
INSERT INTO `abc_login_log` VALUES ('1770', '2', '127.0.0.1', '2015-01-23 11:18:38', null, '1');
INSERT INTO `abc_login_log` VALUES ('1771', '1', '127.0.0.1', '2015-01-23 11:20:21', null, '1');

-- ----------------------------
-- Table structure for `abc_log_file_record`
-- ----------------------------
DROP TABLE IF EXISTS `abc_log_file_record`;
CREATE TABLE `abc_log_file_record` (
  `record_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `record_file_name` varchar(128) DEFAULT NULL COMMENT '上传文件名',
  `record_file_date` varchar(10) DEFAULT NULL COMMENT '日志文件的日期',
  `record_status` int(11) DEFAULT NULL COMMENT '任务执行状态 0失败（不可重试） 1成功 -1上次失败（仍可重试）',
  `record_try_times` int(11) DEFAULT NULL COMMENT '任务尝试次数',
  `record_create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `record_update_time` datetime DEFAULT NULL COMMENT '最后更新时间',
  `record_refer_id` varchar(64) DEFAULT NULL COMMENT '引用id（暂时无用，未来可通过此字段与http请求记录关联）',
  `record_server_mac` varchar(32) DEFAULT NULL COMMENT '日志文件所属服务器的mac地址',
  `record_oss_url` varchar(512) DEFAULT NULL COMMENT '日志上传至oss后返回的oss的url',
  `record_file_size` varchar(128) DEFAULT NULL COMMENT '文件大小',
  PRIMARY KEY (`record_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of abc_log_file_record
-- ----------------------------

-- ----------------------------
-- Table structure for `abc_menu`
-- ----------------------------
DROP TABLE IF EXISTS `abc_menu`;
CREATE TABLE `abc_menu` (
  `menu_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键自增',
  `menu_name` varchar(50) COLLATE utf8_bin NOT NULL COMMENT '菜单名称',
  `menu_url` varchar(200) COLLATE utf8_bin NOT NULL COMMENT '菜单url',
  `menu_parent` int(11) NOT NULL DEFAULT '0' COMMENT '父级菜单编号',
  `menu_smallicon` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '小图标路径',
  `menu_bigicon` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '大图标路径',
  `menu_sort` int(11) DEFAULT '0' COMMENT '排序位置',
  `menu_visible` int(11) DEFAULT '0' COMMENT '是否可见 0 不可见 1可见',
  `menu_des` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '备注',
  `menu_createtime` datetime DEFAULT NULL,
  `menu_modiftime` datetime DEFAULT NULL,
  PRIMARY KEY (`menu_id`)
) ENGINE=InnoDB AUTO_INCREMENT=139 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of abc_menu
-- ----------------------------
INSERT INTO `abc_menu` VALUES ('18', '系统管理', '#', '0', 'icon-application_double', null, '0', '1', null, '2014-12-01 11:44:45', null);
INSERT INTO `abc_menu` VALUES ('19', '菜单管理', '/System/menuManage', '18', 'icon-book_open', null, '0', '1', null, '2014-12-01 11:44:45', null);
INSERT INTO `abc_menu` VALUES ('20', '角色管理', '/System/roleManage', '18', 'icon-users', null, '0', '1', null, '2014-12-01 11:44:45', null);
INSERT INTO `abc_menu` VALUES ('26', '友情链接', '/link/friendLinkListView', '0', 'icon-cart_remove', '2222', '0', '1', null, '2014-12-02 15:27:34', null);
INSERT INTO `abc_menu` VALUES ('27', '积分类型', '/P2PScoreManage/scoreTypeListView', '23', null, null, '1', '1', null, '2014-12-02 15:27:39', null);
INSERT INTO `abc_menu` VALUES ('29', '积分类型', '/P2PScoreManage/scoreTypeListView', '23', null, null, '0', '1', null, '2014-12-02 15:28:36', null);
INSERT INTO `abc_menu` VALUES ('30', '测啊大大', '#', '21', null, null, '0', '1', null, '2014-12-02 15:28:57', null);
INSERT INTO `abc_menu` VALUES ('31', '积分类型', '/P2PScoreManage/scoreTypeListView', '23', null, null, '3', '1', null, '2014-12-02 15:29:38', null);
INSERT INTO `abc_menu` VALUES ('35', '再次测试', '#', '30', null, null, '0', '1', null, '2014-12-02 18:18:18', null);
INSERT INTO `abc_menu` VALUES ('36', '积分类型', '/score/scoreTypeListView', '40', 'icon-computer_error', null, '0', '1', null, '2014-12-02 18:21:51', null);
INSERT INTO `abc_menu` VALUES ('38', '项目审核', '#', '0', 'icon-application_split', null, '5', '1', null, '2014-12-02 20:12:16', null);
INSERT INTO `abc_menu` VALUES ('39', '满标审核', '/review/LoanFullListCheckView', '38', 'icon-book_addresses_delete', null, '0', '1', null, '2014-12-02 20:12:43', null);
INSERT INTO `abc_menu` VALUES ('40', '积分管理', '#', '0', 'icon-compass', null, '0', '1', null, '2014-12-03 09:58:53', null);
INSERT INTO `abc_menu` VALUES ('41', '管理员管理', '/employee/employeeListView', '18', 'icon-group_error', null, '0', '1', null, '2014-12-03 10:19:55', null);
INSERT INTO `abc_menu` VALUES ('42', '积分列表', '/score/scoreListView', '40', 'icon-award_star_gold_3', null, '0', '1', null, '2014-12-03 13:39:28', null);
INSERT INTO `abc_menu` VALUES ('43', '机构管理', '#', '0', 'icon-contrast', null, '0', '1', null, '2014-12-03 17:11:46', null);
INSERT INTO `abc_menu` VALUES ('44', '账户维护', '/account/comOpenAccountListView', '43', 'icon-control_blank_blue', null, '0', '1', null, '2014-12-03 17:12:51', null);
INSERT INTO `abc_menu` VALUES ('45', '项目初审', '/review/LoanListCheckView', '38', 'icon-application_get', null, '3', '1', null, '2014-12-03 18:43:39', null);
INSERT INTO `abc_menu` VALUES ('47', '转让审核', '#', '0', 'icon-control_fastforward_blue', null, '0', '1', null, '2014-12-03 20:07:46', null);
INSERT INTO `abc_menu` VALUES ('48', '转让初审', '/review/loanRedeemListCheckView', '47', 'icon-contrast_increase', null, '2', '1', null, '2014-12-03 20:08:47', null);
INSERT INTO `abc_menu` VALUES ('49', '转让满标审核', '/review/transferFullListCheckView', '47', 'icon-book_open', null, '1', '1', null, '2014-12-03 20:09:57', null);
INSERT INTO `abc_menu` VALUES ('50', '融资意向管理', '#', '0', 'icon-coins', '1', '11', '1', null, '2014-12-04 11:33:25', null);
INSERT INTO `abc_menu` VALUES ('51', '意向审核', '/review/intentListCheckView', '50', 'icon-coins_add', '1', '0', '1', null, '2014-12-04 11:50:46', null);
INSERT INTO `abc_menu` VALUES ('53', '栏目管理', '/site/columnView', '124', 'icon-bookmark_delete', '11', '0', '1', null, '2014-12-04 16:35:38', null);
INSERT INTO `abc_menu` VALUES ('58', '文章管理', '/article/ArticleView', '18', 'icon-book_addresses', '1', '0', '1', null, '2014-12-05 15:49:37', null);
INSERT INTO `abc_menu` VALUES ('59', '机构维护', '/government/govListView', '43', 'icon-group_gear', null, '0', '1', null, '2014-12-08 10:07:57', null);
INSERT INTO `abc_menu` VALUES ('60', '费用设置', '/feeset/costSettingAddView', '18', 'icon-wrench', 'icon-wrench', '0', '1', null, '2014-12-17 14:48:18', null);
INSERT INTO `abc_menu` VALUES ('62', '自营认证管理', '#', '0', 'icon-application_double', null, '0', '1', null, '2014-12-09 10:44:45', null);
INSERT INTO `abc_menu` VALUES ('63', '客户查询', '/selfprove/CustomerManagementListView', '62', 'icon-application_side_list', null, '0', '1', null, '2014-12-09 10:45:45', null);
INSERT INTO `abc_menu` VALUES ('64', '参数配置', '/sysset/systemSettingView', '18', 'icon-table', '1', '0', '1', null, '2014-12-10 16:31:00', null);
INSERT INTO `abc_menu` VALUES ('66', '密码修改', '/orgpassword/orgPwdEditView', '138', 'icon-key', '2212', '0', '1', null, '2014-12-12 11:39:33', null);
INSERT INTO `abc_menu` VALUES ('67', '产品管理', '#', '0', 'icon-cart_remove', '2222', '0', '1', null, '2014-12-15 13:12:30', null);
INSERT INTO `abc_menu` VALUES ('68', '产品维护', '/product/productListView', '67', 'icon-cart_remove', '5.png', '0', '1', null, '2014-12-15 13:15:57', null);
INSERT INTO `abc_menu` VALUES ('69', '产品录入', '/product/productMenuAddView', '67', 'icon-cart_remove', '7.png', '0', '1', null, '2014-12-15 13:17:43', null);
INSERT INTO `abc_menu` VALUES ('70', '更新审核', '/government/govModifyListCheckView', '43', 'icon-group_go', null, '0', '1', null, '2014-12-15 13:41:24', null);
INSERT INTO `abc_menu` VALUES ('71', '贷款还款', '/loanpay/loanPayListView', '0', 'icon-application_side_tree', null, '1', '1', null, '2014-12-15 15:44:00', null);
INSERT INTO `abc_menu` VALUES ('72', '积分兑现设置', '/score/scoreManageListView', '40', 'icon-award_star_gold_1', null, '0', '1', null, '2014-12-17 10:22:33', null);
INSERT INTO `abc_menu` VALUES ('73', '积分兑现审核', '/score/scoreToCashListCheckView', '0', 'icon-award_star_bronze_1', null, '0', '1', null, '2014-12-17 10:23:17', null);
INSERT INTO `abc_menu` VALUES ('74', '费用统计', '/charge/costStatisticsView', '81', 'icon-chart_bar', '1', '0', '1', null, '2014-12-17 10:23:33', null);
INSERT INTO `abc_menu` VALUES ('75', '等级管理', '/score/levelManageView', '40', 'icon-award_star_bronze_3', null, '0', '1', null, '2014-12-17 11:46:02', null);
INSERT INTO `abc_menu` VALUES ('76', '账户管理', '#', '0', 'icon-user', null, '0', '1', null, '2014-12-17 14:00:44', null);
INSERT INTO `abc_menu` VALUES ('77', '账户开户', '/account/openAccountView', '76', 'icon-user_add', null, '0', '1', null, '2014-12-17 14:01:51', null);
INSERT INTO `abc_menu` VALUES ('78', '我的机构', '/government/myGovEditView', '43', 'icon-group', null, '0', '1', null, '2014-12-17 17:33:55', null);
INSERT INTO `abc_menu` VALUES ('79', '余额查询', '/account/settMoneyLookUpView', '76', 'icon-money_dollar', null, '0', '1', null, '2014-12-17 18:11:21', null);
INSERT INTO `abc_menu` VALUES ('80', '维护记录', '/government/govModifyListView', '43', 'icon-application_view_columns', null, '0', '1', null, '2014-12-17 21:18:33', null);
INSERT INTO `abc_menu` VALUES ('81', '统计分析', '#', '0', 'icon-calculator', null, '0', '1', null, '2014-12-18 20:54:30', null);
INSERT INTO `abc_menu` VALUES ('82', '资金对账表', '/reportAnalysis/CapitalAccountView', '81', 'icon-chart_pie', null, '0', '1', null, '2014-12-18 20:58:41', null);
INSERT INTO `abc_menu` VALUES ('84', '实名认证', '/selfprove/RealNameListView', '62', 'icon-application_side_list', null, '0', '1', null, '2014-12-22 10:42:22', null);
INSERT INTO `abc_menu` VALUES ('85', '邮箱认证', '/selfprove/EmailListView', '62', 'icon-application_side_list', null, '0', '1', null, '2014-12-22 11:37:31', null);
INSERT INTO `abc_menu` VALUES ('86', '手机认证', '/selfprove/MobileListView', '62', 'icon-device_stylus', null, '0', '1', null, '2014-12-22 11:40:09', null);
INSERT INTO `abc_menu` VALUES ('87', '项目管理', '#', '0', 'icon-application_side_list', null, '4', '1', null, '2014-12-22 13:39:38', null);
INSERT INTO `abc_menu` VALUES ('88', '项目发布', '/projectmanage/ProjectReleaseListView', '87', 'icon-report_picture', null, '3', '1', null, '2014-12-22 13:47:27', null);
INSERT INTO `abc_menu` VALUES ('89', '项目跟踪', '/projectmanage/ProjectTrackListView', '87', 'icon-page_white_code', null, '2', '1', null, '2014-12-22 13:48:59', null);
INSERT INTO `abc_menu` VALUES ('90', '转让跟踪', '/projectmanage/ProjectScheduleListView', '87', 'icon-page_magnify', null, '1', '1', null, '2014-12-22 13:50:49', null);
INSERT INTO `abc_menu` VALUES ('91', '有限合伙管理', '#', '0', 'icon-application_side_tree', '', '0', '1', '', '2014-12-22 15:20:10', '2014-12-22 15:20:25');
INSERT INTO `abc_menu` VALUES ('92', '有限合伙维护', '/fund/FundApplyListView', '91', 'icon-application_side_tree', '', '0', '1', '', '2014-12-22 15:20:15', '2014-12-22 15:20:33');
INSERT INTO `abc_menu` VALUES ('93', '资金管理', '#', '0', 'icon-coins', null, '2', '1', null, '2014-12-22 15:24:43', null);
INSERT INTO `abc_menu` VALUES ('94', '有限合伙预约', '/fund/FundOrderListView', '91', 'icon-application_side_tree', '', '0', '1', '', '2014-12-22 00:00:00', '2014-12-22 15:35:00');
INSERT INTO `abc_menu` VALUES ('95', '银行账户管理', '/bank/MonBankInfoListView', '91', 'icon-application_side_tree', '', '0', '1', '', '2014-12-22 00:00:00', '2014-12-22 15:35:03');
INSERT INTO `abc_menu` VALUES ('96', '退费申请', '/moneyManage/moneyReturnAddView', '93', 'icon-coins', null, '0', '1', null, '2014-12-22 15:31:00', null);
INSERT INTO `abc_menu` VALUES ('98', '个人提现查询', '/extr/investorExtrListView', '103', 'icon-chart_bar', null, '0', '1', null, '2014-12-23 15:11:12', null);
INSERT INTO `abc_menu` VALUES ('99', '个人充值查询', '/recharge/investorRechargeListView', '103', 'icon-chart_bar', null, '0', '1', null, '2014-12-24 10:00:28', null);
INSERT INTO `abc_menu` VALUES ('100', '机构提现查询', '/extr/guarGovExtrListView', '103', 'icon-chart_bar', null, '0', '1', null, '2014-12-24 14:24:13', null);
INSERT INTO `abc_menu` VALUES ('101', '机构充值查询', '/recharge/GuarRechargeListView', '103', 'icon-chart_bar', null, '0', '1', null, '2014-12-24 15:53:56', null);
INSERT INTO `abc_menu` VALUES ('102', '退款审核', '/moneyManage/RefundApplyListCheckView', '93', 'icon-coins', null, '0', '1', null, '2014-12-24 20:07:14', null);
INSERT INTO `abc_menu` VALUES ('103', '充值提现查询', '#', '0', 'icon-chart_curve', null, '0', '1', null, '2014-12-25 15:27:15', null);
INSERT INTO `abc_menu` VALUES ('106', '红包管理', '#', '0', 'icon-folder_heart', null, '0', '1', null, '2014-12-25 21:06:00', null);
INSERT INTO `abc_menu` VALUES ('107', '红包查询', '/redrewardmanage/RedRewardListView', '106', 'icon-folder_explore', null, '0', '1', null, '2014-12-25 21:07:28', null);
INSERT INTO `abc_menu` VALUES ('108', '收购审核', '#', '0', null, null, '0', '1', null, '2014-12-26 13:26:40', null);
INSERT INTO `abc_menu` VALUES ('109', '收购初审', '/review/buyLoanListCheckView', '108', null, null, '2', '1', null, '2014-12-26 13:27:30', null);
INSERT INTO `abc_menu` VALUES ('110', '收购满标审核', '/review/buyLoanFullListCheckView', '108', null, null, '0', '1', null, '2014-12-26 13:28:14', null);
INSERT INTO `abc_menu` VALUES ('111', '满标划转', '/moneyManage/MoneyTransferListView', '93', 'icon-coins_add', null, '0', '1', null, '2014-12-26 17:24:50', null);
INSERT INTO `abc_menu` VALUES ('112', '转让满标划转', '/moneyManage/AttFulScaTsfListView', '93', 'icon-coins_add', null, '0', '1', null, '2014-12-26 17:27:29', null);
INSERT INTO `abc_menu` VALUES ('113', '收购满标划转', '/moneyManage/BuyFullTransferListView', '93', 'icon-coins_add', null, '0', '1', null, '2014-12-26 17:28:20', null);
INSERT INTO `abc_menu` VALUES ('114', '融资管理', '/loan/LoanListView', '0', 'icon-application_side_tree', null, '8', '1', null, '2014-12-26 17:52:03', null);
INSERT INTO `abc_menu` VALUES ('115', '融资维护', '/loan/LoanListView', '114', 'icon-application_edit', null, '1', '1', null, '2014-12-26 17:52:49', null);
INSERT INTO `abc_menu` VALUES ('116', '融资申请', '/loan/LoanTempListView', '114', 'icon-application_side_tree', null, '2', '1', null, '2014-12-26 17:53:19', null);
INSERT INTO `abc_menu` VALUES ('117', '担保审核', '/review/guarGovListCheckView', '0', 'icon-application_stop', null, '0', '1', null, '2014-12-27 11:53:09', null);
INSERT INTO `abc_menu` VALUES ('118', '信贷审核', '/review/loanGovListCheckView', '0', 'icon-transmit_go', null, '0', '1', null, '2014-12-27 11:58:33', null);
INSERT INTO `abc_menu` VALUES ('119', '资金划转统计', '/reportAnalysis/TransferFundsStatisticsView', '81', 'icon-chart_pie', null, '0', '1', null, '2014-12-31 17:42:16', null);
INSERT INTO `abc_menu` VALUES ('120', '编号模板设置', '/sysset/NumberRuleView', '18', 'icon-text_ab', null, '0', '1', null, '2015-01-03 11:20:40', null);
INSERT INTO `abc_menu` VALUES ('121', '数据备份', '/sysset/DataBackupView', '18', 'icon-database_save', 'icon-database_save', '0', '1', null, '2015-01-03 22:02:16', null);
INSERT INTO `abc_menu` VALUES ('122', '数据恢复', '/sysset/DataRecoveryView', '18', 'icon-database_refresh', 'icon-database_refresh', '0', '1', null, '2015-01-03 22:03:00', null);
INSERT INTO `abc_menu` VALUES ('123', '系统日志', '/sysset/SystemLogView', '18', 'icon-book_edit', null, '0', '1', null, '2015-01-03 22:05:57', null);
INSERT INTO `abc_menu` VALUES ('124', '网站管理', '#', '0', 'icon-cog', null, '0', '1', null, '2015-01-03 22:09:47', null);
INSERT INTO `abc_menu` VALUES ('125', '网站设置', '/site/SiteConfigView', '124', 'icon-award_star_silver_3', null, '0', '1', null, '2015-01-03 22:12:11', null);
INSERT INTO `abc_menu` VALUES ('126', '理财管理', '#', '0', 'icon-coins', null, '0', '1', null, '2015-01-04 18:28:04', null);
INSERT INTO `abc_menu` VALUES ('127', '邀请好友', '/invite/rewardFriendView', '126', 'icon-bell', null, '0', '1', null, '2015-01-04 18:30:41', null);
INSERT INTO `abc_menu` VALUES ('128', '邀请奖励', '/invite/rewardListView', '126', 'icon-coins', null, '0', '1', null, '2015-01-04 18:32:05', null);
INSERT INTO `abc_menu` VALUES ('129', '自营审核管理', '#', '0', 'icon-application_side_tree', null, '0', '1', null, '2015-01-06 14:53:21', null);
INSERT INTO `abc_menu` VALUES ('131', '信用额度审核', '/credit/creditListCheckView', '129', 'icon-application_side_list', null, '0', '1', null, '2015-01-06 14:58:39', null);
INSERT INTO `abc_menu` VALUES ('134', '退回审核', '/review/LoanBackListCheckView', '38', 'icon-application_side_list', null, '0', '1', null, '2015-01-06 22:03:59', null);
INSERT INTO `abc_menu` VALUES ('135', '充值管理', '#', '0', 'icon-hourglass_go', null, '0', '1', null, '2015-01-06 22:27:49', null);
INSERT INTO `abc_menu` VALUES ('136', '线下充值审核', '/recharge/DownLineListCheckView', '135', 'icon-hourglass_go', null, '0', '1', null, '2015-01-06 22:28:48', null);
INSERT INTO `abc_menu` VALUES ('138', '个人信息', '#', '0', 'icon-user_mature', null, '0', '1', null, '2015-01-06 23:41:01', null);

-- ----------------------------
-- Table structure for `abc_menu_btn`
-- ----------------------------
DROP TABLE IF EXISTS `abc_menu_btn`;
CREATE TABLE `abc_menu_btn` (
  `mbt_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键自增',
  `menu_id` int(11) NOT NULL COMMENT 'abc_menu外键',
  `btn_id` int(11) NOT NULL COMMENT 'abc_btn外键',
  PRIMARY KEY (`mbt_id`)
) ENGINE=InnoDB AUTO_INCREMENT=802 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='菜单按钮表';

-- ----------------------------
-- Records of abc_menu_btn
-- ----------------------------
INSERT INTO `abc_menu_btn` VALUES ('167', '59', '139');
INSERT INTO `abc_menu_btn` VALUES ('168', '59', '140');
INSERT INTO `abc_menu_btn` VALUES ('169', '59', '141');
INSERT INTO `abc_menu_btn` VALUES ('170', '59', '162');
INSERT INTO `abc_menu_btn` VALUES ('171', '59', '175');
INSERT INTO `abc_menu_btn` VALUES ('172', '59', '190');
INSERT INTO `abc_menu_btn` VALUES ('173', '59', '176');
INSERT INTO `abc_menu_btn` VALUES ('174', '59', '163');
INSERT INTO `abc_menu_btn` VALUES ('175', '20', '139');
INSERT INTO `abc_menu_btn` VALUES ('176', '20', '140');
INSERT INTO `abc_menu_btn` VALUES ('177', '20', '141');
INSERT INTO `abc_menu_btn` VALUES ('181', '19', '139');
INSERT INTO `abc_menu_btn` VALUES ('182', '19', '140');
INSERT INTO `abc_menu_btn` VALUES ('183', '19', '141');
INSERT INTO `abc_menu_btn` VALUES ('184', '47', '138');
INSERT INTO `abc_menu_btn` VALUES ('202', '67', '138');
INSERT INTO `abc_menu_btn` VALUES ('210', '68', '139');
INSERT INTO `abc_menu_btn` VALUES ('211', '68', '140');
INSERT INTO `abc_menu_btn` VALUES ('212', '68', '141');
INSERT INTO `abc_menu_btn` VALUES ('213', '68', '162');
INSERT INTO `abc_menu_btn` VALUES ('214', '68', '163');
INSERT INTO `abc_menu_btn` VALUES ('215', '69', '139');
INSERT INTO `abc_menu_btn` VALUES ('273', '70', '138');
INSERT INTO `abc_menu_btn` VALUES ('274', '70', '162');
INSERT INTO `abc_menu_btn` VALUES ('275', '70', '147');
INSERT INTO `abc_menu_btn` VALUES ('276', '70', '163');
INSERT INTO `abc_menu_btn` VALUES ('277', '70', '172');
INSERT INTO `abc_menu_btn` VALUES ('278', '40', '138');
INSERT INTO `abc_menu_btn` VALUES ('279', '42', '140');
INSERT INTO `abc_menu_btn` VALUES ('280', '42', '162');
INSERT INTO `abc_menu_btn` VALUES ('281', '42', '163');
INSERT INTO `abc_menu_btn` VALUES ('291', '36', '139');
INSERT INTO `abc_menu_btn` VALUES ('292', '36', '140');
INSERT INTO `abc_menu_btn` VALUES ('293', '36', '141');
INSERT INTO `abc_menu_btn` VALUES ('294', '36', '163');
INSERT INTO `abc_menu_btn` VALUES ('313', '41', '139');
INSERT INTO `abc_menu_btn` VALUES ('314', '41', '140');
INSERT INTO `abc_menu_btn` VALUES ('315', '41', '141');
INSERT INTO `abc_menu_btn` VALUES ('316', '41', '162');
INSERT INTO `abc_menu_btn` VALUES ('317', '41', '175');
INSERT INTO `abc_menu_btn` VALUES ('318', '41', '190');
INSERT INTO `abc_menu_btn` VALUES ('319', '41', '176');
INSERT INTO `abc_menu_btn` VALUES ('320', '41', '163');
INSERT INTO `abc_menu_btn` VALUES ('321', '41', '138');
INSERT INTO `abc_menu_btn` VALUES ('323', '62', '138');
INSERT INTO `abc_menu_btn` VALUES ('324', '62', '139');
INSERT INTO `abc_menu_btn` VALUES ('325', '62', '140');
INSERT INTO `abc_menu_btn` VALUES ('326', '62', '141');
INSERT INTO `abc_menu_btn` VALUES ('327', '63', '162');
INSERT INTO `abc_menu_btn` VALUES ('328', '63', '139');
INSERT INTO `abc_menu_btn` VALUES ('329', '63', '140');
INSERT INTO `abc_menu_btn` VALUES ('330', '63', '141');
INSERT INTO `abc_menu_btn` VALUES ('335', '72', '139');
INSERT INTO `abc_menu_btn` VALUES ('336', '72', '140');
INSERT INTO `abc_menu_btn` VALUES ('337', '72', '141');
INSERT INTO `abc_menu_btn` VALUES ('338', '72', '138');
INSERT INTO `abc_menu_btn` VALUES ('349', '71', '138');
INSERT INTO `abc_menu_btn` VALUES ('350', '71', '162');
INSERT INTO `abc_menu_btn` VALUES ('351', '71', '174');
INSERT INTO `abc_menu_btn` VALUES ('352', '71', '184');
INSERT INTO `abc_menu_btn` VALUES ('353', '71', '189');
INSERT INTO `abc_menu_btn` VALUES ('358', '75', '138');
INSERT INTO `abc_menu_btn` VALUES ('359', '76', '138');
INSERT INTO `abc_menu_btn` VALUES ('360', '77', '138');
INSERT INTO `abc_menu_btn` VALUES ('363', '38', '138');
INSERT INTO `abc_menu_btn` VALUES ('368', '74', '138');
INSERT INTO `abc_menu_btn` VALUES ('369', '74', '139');
INSERT INTO `abc_menu_btn` VALUES ('370', '74', '140');
INSERT INTO `abc_menu_btn` VALUES ('371', '74', '141');
INSERT INTO `abc_menu_btn` VALUES ('372', '78', '138');
INSERT INTO `abc_menu_btn` VALUES ('373', '79', '138');
INSERT INTO `abc_menu_btn` VALUES ('374', '80', '162');
INSERT INTO `abc_menu_btn` VALUES ('375', '80', '138');
INSERT INTO `abc_menu_btn` VALUES ('376', '80', '163');
INSERT INTO `abc_menu_btn` VALUES ('377', '64', '138');
INSERT INTO `abc_menu_btn` VALUES ('378', '81', '138');
INSERT INTO `abc_menu_btn` VALUES ('380', '50', '138');
INSERT INTO `abc_menu_btn` VALUES ('408', '85', '138');
INSERT INTO `abc_menu_btn` VALUES ('410', '86', '163');
INSERT INTO `abc_menu_btn` VALUES ('423', '90', '138');
INSERT INTO `abc_menu_btn` VALUES ('424', '90', '142');
INSERT INTO `abc_menu_btn` VALUES ('425', '90', '162');
INSERT INTO `abc_menu_btn` VALUES ('426', '90', '163');
INSERT INTO `abc_menu_btn` VALUES ('428', '87', '138');
INSERT INTO `abc_menu_btn` VALUES ('435', '93', '138');
INSERT INTO `abc_menu_btn` VALUES ('436', '96', '138');
INSERT INTO `abc_menu_btn` VALUES ('448', '91', '138');
INSERT INTO `abc_menu_btn` VALUES ('461', '92', '139');
INSERT INTO `abc_menu_btn` VALUES ('462', '92', '140');
INSERT INTO `abc_menu_btn` VALUES ('463', '92', '141');
INSERT INTO `abc_menu_btn` VALUES ('464', '92', '177');
INSERT INTO `abc_menu_btn` VALUES ('465', '92', '196');
INSERT INTO `abc_menu_btn` VALUES ('466', '92', '163');
INSERT INTO `abc_menu_btn` VALUES ('467', '94', '162');
INSERT INTO `abc_menu_btn` VALUES ('468', '94', '147');
INSERT INTO `abc_menu_btn` VALUES ('469', '94', '163');
INSERT INTO `abc_menu_btn` VALUES ('472', '58', '138');
INSERT INTO `abc_menu_btn` VALUES ('473', '58', '139');
INSERT INTO `abc_menu_btn` VALUES ('474', '58', '140');
INSERT INTO `abc_menu_btn` VALUES ('475', '58', '141');
INSERT INTO `abc_menu_btn` VALUES ('476', '95', '139');
INSERT INTO `abc_menu_btn` VALUES ('477', '95', '140');
INSERT INTO `abc_menu_btn` VALUES ('478', '95', '141');
INSERT INTO `abc_menu_btn` VALUES ('479', '95', '162');
INSERT INTO `abc_menu_btn` VALUES ('480', '95', '163');
INSERT INTO `abc_menu_btn` VALUES ('483', '84', '162');
INSERT INTO `abc_menu_btn` VALUES ('484', '84', '147');
INSERT INTO `abc_menu_btn` VALUES ('485', '84', '163');
INSERT INTO `abc_menu_btn` VALUES ('494', '102', '138');
INSERT INTO `abc_menu_btn` VALUES ('495', '102', '147');
INSERT INTO `abc_menu_btn` VALUES ('496', '102', '162');
INSERT INTO `abc_menu_btn` VALUES ('497', '103', '138');
INSERT INTO `abc_menu_btn` VALUES ('503', '44', '139');
INSERT INTO `abc_menu_btn` VALUES ('504', '44', '140');
INSERT INTO `abc_menu_btn` VALUES ('505', '44', '141');
INSERT INTO `abc_menu_btn` VALUES ('506', '44', '162');
INSERT INTO `abc_menu_btn` VALUES ('507', '44', '163');
INSERT INTO `abc_menu_btn` VALUES ('508', '104', '138');
INSERT INTO `abc_menu_btn` VALUES ('509', '105', '138');
INSERT INTO `abc_menu_btn` VALUES ('510', '107', '138');
INSERT INTO `abc_menu_btn` VALUES ('511', '106', '138');
INSERT INTO `abc_menu_btn` VALUES ('517', '108', '138');
INSERT INTO `abc_menu_btn` VALUES ('527', '114', '138');
INSERT INTO `abc_menu_btn` VALUES ('543', '116', '138');
INSERT INTO `abc_menu_btn` VALUES ('574', '111', '138');
INSERT INTO `abc_menu_btn` VALUES ('575', '111', '180');
INSERT INTO `abc_menu_btn` VALUES ('576', '111', '163');
INSERT INTO `abc_menu_btn` VALUES ('577', '112', '138');
INSERT INTO `abc_menu_btn` VALUES ('578', '112', '180');
INSERT INTO `abc_menu_btn` VALUES ('579', '112', '163');
INSERT INTO `abc_menu_btn` VALUES ('580', '113', '138');
INSERT INTO `abc_menu_btn` VALUES ('581', '113', '180');
INSERT INTO `abc_menu_btn` VALUES ('582', '113', '163');
INSERT INTO `abc_menu_btn` VALUES ('583', '89', '138');
INSERT INTO `abc_menu_btn` VALUES ('584', '89', '162');
INSERT INTO `abc_menu_btn` VALUES ('585', '89', '142');
INSERT INTO `abc_menu_btn` VALUES ('586', '89', '163');
INSERT INTO `abc_menu_btn` VALUES ('587', '89', '206');
INSERT INTO `abc_menu_btn` VALUES ('588', '60', '138');
INSERT INTO `abc_menu_btn` VALUES ('589', '60', '139');
INSERT INTO `abc_menu_btn` VALUES ('590', '60', '140');
INSERT INTO `abc_menu_btn` VALUES ('591', '60', '141');
INSERT INTO `abc_menu_btn` VALUES ('592', '39', '162');
INSERT INTO `abc_menu_btn` VALUES ('593', '39', '147');
INSERT INTO `abc_menu_btn` VALUES ('594', '39', '163');
INSERT INTO `abc_menu_btn` VALUES ('595', '39', '172');
INSERT INTO `abc_menu_btn` VALUES ('600', '48', '162');
INSERT INTO `abc_menu_btn` VALUES ('601', '48', '147');
INSERT INTO `abc_menu_btn` VALUES ('602', '48', '163');
INSERT INTO `abc_menu_btn` VALUES ('603', '48', '172');
INSERT INTO `abc_menu_btn` VALUES ('604', '49', '162');
INSERT INTO `abc_menu_btn` VALUES ('605', '49', '147');
INSERT INTO `abc_menu_btn` VALUES ('606', '49', '163');
INSERT INTO `abc_menu_btn` VALUES ('607', '49', '172');
INSERT INTO `abc_menu_btn` VALUES ('614', '73', '138');
INSERT INTO `abc_menu_btn` VALUES ('615', '73', '147');
INSERT INTO `abc_menu_btn` VALUES ('616', '73', '163');
INSERT INTO `abc_menu_btn` VALUES ('617', '73', '172');
INSERT INTO `abc_menu_btn` VALUES ('618', '109', '162');
INSERT INTO `abc_menu_btn` VALUES ('619', '109', '147');
INSERT INTO `abc_menu_btn` VALUES ('620', '109', '163');
INSERT INTO `abc_menu_btn` VALUES ('621', '109', '172');
INSERT INTO `abc_menu_btn` VALUES ('630', '110', '162');
INSERT INTO `abc_menu_btn` VALUES ('631', '110', '147');
INSERT INTO `abc_menu_btn` VALUES ('632', '110', '163');
INSERT INTO `abc_menu_btn` VALUES ('633', '110', '172');
INSERT INTO `abc_menu_btn` VALUES ('635', '119', '163');
INSERT INTO `abc_menu_btn` VALUES ('636', '120', '138');
INSERT INTO `abc_menu_btn` VALUES ('637', '121', '138');
INSERT INTO `abc_menu_btn` VALUES ('638', '122', '138');
INSERT INTO `abc_menu_btn` VALUES ('639', '123', '138');
INSERT INTO `abc_menu_btn` VALUES ('641', '124', '138');
INSERT INTO `abc_menu_btn` VALUES ('649', '126', '138');
INSERT INTO `abc_menu_btn` VALUES ('650', '127', '138');
INSERT INTO `abc_menu_btn` VALUES ('651', '128', '138');
INSERT INTO `abc_menu_btn` VALUES ('652', '128', '163');
INSERT INTO `abc_menu_btn` VALUES ('653', '53', '138');
INSERT INTO `abc_menu_btn` VALUES ('654', '53', '139');
INSERT INTO `abc_menu_btn` VALUES ('655', '53', '140');
INSERT INTO `abc_menu_btn` VALUES ('656', '53', '141');
INSERT INTO `abc_menu_btn` VALUES ('657', '125', '138');
INSERT INTO `abc_menu_btn` VALUES ('658', '125', '139');
INSERT INTO `abc_menu_btn` VALUES ('659', '125', '140');
INSERT INTO `abc_menu_btn` VALUES ('660', '125', '141');
INSERT INTO `abc_menu_btn` VALUES ('678', '118', '138');
INSERT INTO `abc_menu_btn` VALUES ('679', '118', '162');
INSERT INTO `abc_menu_btn` VALUES ('680', '118', '147');
INSERT INTO `abc_menu_btn` VALUES ('681', '118', '158');
INSERT INTO `abc_menu_btn` VALUES ('682', '118', '159');
INSERT INTO `abc_menu_btn` VALUES ('683', '118', '193');
INSERT INTO `abc_menu_btn` VALUES ('684', '118', '194');
INSERT INTO `abc_menu_btn` VALUES ('685', '118', '172');
INSERT INTO `abc_menu_btn` VALUES ('686', '118', '163');
INSERT INTO `abc_menu_btn` VALUES ('687', '118', '192');
INSERT INTO `abc_menu_btn` VALUES ('689', '131', '138');
INSERT INTO `abc_menu_btn` VALUES ('690', '131', '162');
INSERT INTO `abc_menu_btn` VALUES ('691', '131', '147');
INSERT INTO `abc_menu_btn` VALUES ('692', '131', '163');
INSERT INTO `abc_menu_btn` VALUES ('716', '135', '138');
INSERT INTO `abc_menu_btn` VALUES ('722', '26', '138');
INSERT INTO `abc_menu_btn` VALUES ('723', '26', '139');
INSERT INTO `abc_menu_btn` VALUES ('724', '26', '140');
INSERT INTO `abc_menu_btn` VALUES ('725', '117', '138');
INSERT INTO `abc_menu_btn` VALUES ('726', '117', '162');
INSERT INTO `abc_menu_btn` VALUES ('727', '117', '147');
INSERT INTO `abc_menu_btn` VALUES ('728', '117', '158');
INSERT INTO `abc_menu_btn` VALUES ('729', '117', '159');
INSERT INTO `abc_menu_btn` VALUES ('730', '117', '193');
INSERT INTO `abc_menu_btn` VALUES ('731', '117', '172');
INSERT INTO `abc_menu_btn` VALUES ('732', '117', '163');
INSERT INTO `abc_menu_btn` VALUES ('733', '117', '194');
INSERT INTO `abc_menu_btn` VALUES ('734', '129', '138');
INSERT INTO `abc_menu_btn` VALUES ('735', '66', '138');
INSERT INTO `abc_menu_btn` VALUES ('736', '138', '138');
INSERT INTO `abc_menu_btn` VALUES ('737', '98', '163');
INSERT INTO `abc_menu_btn` VALUES ('738', '99', '163');
INSERT INTO `abc_menu_btn` VALUES ('739', '100', '163');
INSERT INTO `abc_menu_btn` VALUES ('740', '101', '163');
INSERT INTO `abc_menu_btn` VALUES ('748', '88', '138');
INSERT INTO `abc_menu_btn` VALUES ('749', '88', '162');
INSERT INTO `abc_menu_btn` VALUES ('750', '88', '142');
INSERT INTO `abc_menu_btn` VALUES ('751', '88', '196');
INSERT INTO `abc_menu_btn` VALUES ('752', '88', '204');
INSERT INTO `abc_menu_btn` VALUES ('753', '88', '163');
INSERT INTO `abc_menu_btn` VALUES ('754', '88', '159');
INSERT INTO `abc_menu_btn` VALUES ('764', '136', '162');
INSERT INTO `abc_menu_btn` VALUES ('765', '136', '147');
INSERT INTO `abc_menu_btn` VALUES ('766', '136', '163');
INSERT INTO `abc_menu_btn` VALUES ('767', '51', '162');
INSERT INTO `abc_menu_btn` VALUES ('768', '51', '147');
INSERT INTO `abc_menu_btn` VALUES ('769', '51', '158');
INSERT INTO `abc_menu_btn` VALUES ('770', '51', '193');
INSERT INTO `abc_menu_btn` VALUES ('771', '51', '194');
INSERT INTO `abc_menu_btn` VALUES ('772', '51', '192');
INSERT INTO `abc_menu_btn` VALUES ('773', '51', '172');
INSERT INTO `abc_menu_btn` VALUES ('774', '51', '163');
INSERT INTO `abc_menu_btn` VALUES ('775', '82', '138');
INSERT INTO `abc_menu_btn` VALUES ('776', '82', '163');
INSERT INTO `abc_menu_btn` VALUES ('777', '115', '139');
INSERT INTO `abc_menu_btn` VALUES ('778', '115', '140');
INSERT INTO `abc_menu_btn` VALUES ('779', '115', '141');
INSERT INTO `abc_menu_btn` VALUES ('780', '115', '162');
INSERT INTO `abc_menu_btn` VALUES ('781', '115', '158');
INSERT INTO `abc_menu_btn` VALUES ('782', '115', '194');
INSERT INTO `abc_menu_btn` VALUES ('783', '115', '192');
INSERT INTO `abc_menu_btn` VALUES ('784', '115', '163');
INSERT INTO `abc_menu_btn` VALUES ('785', '115', '172');
INSERT INTO `abc_menu_btn` VALUES ('786', '45', '162');
INSERT INTO `abc_menu_btn` VALUES ('787', '45', '147');
INSERT INTO `abc_menu_btn` VALUES ('788', '45', '158');
INSERT INTO `abc_menu_btn` VALUES ('789', '45', '159');
INSERT INTO `abc_menu_btn` VALUES ('790', '45', '172');
INSERT INTO `abc_menu_btn` VALUES ('791', '45', '163');
INSERT INTO `abc_menu_btn` VALUES ('797', '134', '162');
INSERT INTO `abc_menu_btn` VALUES ('798', '134', '147');
INSERT INTO `abc_menu_btn` VALUES ('799', '134', '172');
INSERT INTO `abc_menu_btn` VALUES ('800', '134', '163');
INSERT INTO `abc_menu_btn` VALUES ('801', '43', '138');

-- ----------------------------
-- Table structure for `abc_menu_btn_log`
-- ----------------------------
DROP TABLE IF EXISTS `abc_menu_btn_log`;
CREATE TABLE `abc_menu_btn_log` (
  `bl_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键自增',
  `emp_id` int(11) NOT NULL COMMENT '操作人  abc_employee外键',
  `menu_id` int(11) NOT NULL COMMENT '操作的menu  abc_menu外键',
  `btn_id` int(11) NOT NULL COMMENT '操作的btn  abc_menu',
  `operat_type` int(11) NOT NULL COMMENT '1:添加按钮 2:删除按钮 ',
  `operat_time` datetime NOT NULL COMMENT '操作时间',
  PRIMARY KEY (`bl_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='按钮菜单关联操作日志表';

-- ----------------------------
-- Records of abc_menu_btn_log
-- ----------------------------

-- ----------------------------
-- Table structure for `abc_menu_log`
-- ----------------------------
DROP TABLE IF EXISTS `abc_menu_log`;
CREATE TABLE `abc_menu_log` (
  `ml_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键自增',
  `emp_id` int(11) NOT NULL COMMENT 'abc_employee外键',
  `operat_type` int(11) NOT NULL COMMENT '操作类型 1:添加 2:修改 3:删除 ',
  `operat_before` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '操作前的json值',
  `operat_after` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '操作后的json值',
  `operat_time` datetime NOT NULL COMMENT '操作时间',
  PRIMARY KEY (`ml_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='菜单操作日志表';

-- ----------------------------
-- Records of abc_menu_log
-- ----------------------------

-- ----------------------------
-- Table structure for `abc_message_info`
-- ----------------------------
DROP TABLE IF EXISTS `abc_message_info`;
CREATE TABLE `abc_message_info` (
  `sys_message_id` int(4) NOT NULL AUTO_INCREMENT,
  `sys_message_title` varchar(152) COLLATE utf8_bin DEFAULT NULL COMMENT '留言标题',
  `sys_message_content` text COLLATE utf8_bin COMMENT '留言内容',
  `sys_user_id` int(4) DEFAULT NULL,
  `sys_user_type` char(1) COLLATE utf8_bin DEFAULT NULL,
  `sys_to_user` int(4) DEFAULT NULL,
  `sys_to_user_type` char(1) COLLATE utf8_bin DEFAULT NULL,
  `sys_message_state` char(1) COLLATE utf8_bin DEFAULT NULL,
  `sys_del_sign` char(2) COLLATE utf8_bin DEFAULT NULL,
  `sys_message_date` datetime DEFAULT NULL COMMENT '留言日期',
  PRIMARY KEY (`sys_message_id`)
) ENGINE=MyISAM AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of abc_message_info
-- ----------------------------

-- ----------------------------
-- Table structure for `abc_message_reply`
-- ----------------------------
DROP TABLE IF EXISTS `abc_message_reply`;
CREATE TABLE `abc_message_reply` (
  `sys_reply_id` int(4) NOT NULL AUTO_INCREMENT,
  `sys_message_id` int(4) DEFAULT NULL COMMENT '外键表：SYS_messag_info 留言ID',
  `sys_user_id` int(4) DEFAULT NULL COMMENT '外键表：CST_user_info 留言人',
  `sys_user_type` char(1) COLLATE utf8_bin DEFAULT NULL COMMENT '留言人类型',
  `sys_reply_content` text COLLATE utf8_bin COMMENT '回复内容',
  `sys_reply_date` datetime DEFAULT NULL COMMENT '回复日期',
  PRIMARY KEY (`sys_reply_id`)
) ENGINE=MyISAM AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of abc_message_reply
-- ----------------------------

-- ----------------------------
-- Table structure for `abc_mon_bank_info`
-- ----------------------------
DROP TABLE IF EXISTS `abc_mon_bank_info`;
CREATE TABLE `abc_mon_bank_info` (
  `mon_bank_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `mon_fund_id` int(11) DEFAULT NULL COMMENT '有限合伙id',
  `fun_fund_name` varchar(125) COLLATE utf8_bin DEFAULT NULL COMMENT '基金名称',
  `mon_bank_name` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '银行名称',
  `mon_bank_card` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '银行卡号',
  `mon_user_namer` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '用户户名',
  PRIMARY KEY (`mon_bank_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of abc_mon_bank_info
-- ----------------------------

-- ----------------------------
-- Table structure for `abc_operate_log`
-- ----------------------------
DROP TABLE IF EXISTS `abc_operate_log`;
CREATE TABLE `abc_operate_log` (
  `ol_id` int(11) NOT NULL AUTO_INCREMENT,
  `ol_emp_id` int(11) DEFAULT NULL COMMENT '操作人id',
  `ol_IP` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '登录IP',
  `ol_operate_time` datetime DEFAULT NULL COMMENT '操作时间',
  `ol_operate_type` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '操作类型',
  `ol_module` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '操作模块',
  `ol_content` varchar(512) COLLATE utf8_bin DEFAULT NULL COMMENT '操作内容',
  `ol_state` tinyint(1) DEFAULT NULL COMMENT '操作日志状态 -1：已删除 1：启用',
  PRIMARY KEY (`ol_id`)
) ENGINE=InnoDB AUTO_INCREMENT=104 DEFAULT CHARSET=utf8 COLLATE=utf8_bin ROW_FORMAT=COMPACT COMMENT='数据操作日志表';

-- ----------------------------
-- Records of abc_operate_log
-- ----------------------------
INSERT INTO `abc_operate_log` VALUES ('81', '1', '0:0:0:0:0:0:0:1', '2015-01-22 15:32:32', '添加', '机构管理', '添加了一个机构', '1');
INSERT INTO `abc_operate_log` VALUES ('82', '1', '0:0:0:0:0:0:0:1', '2015-01-22 15:32:52', '添加', '机构管理', '添加了一个机构', '1');
INSERT INTO `abc_operate_log` VALUES ('83', '1', '0:0:0:0:0:0:0:1', '2015-01-22 15:33:39', '添加', '机构管理', '添加了一个机构', '1');
INSERT INTO `abc_operate_log` VALUES ('84', '1', '0:0:0:0:0:0:0:1', '2015-01-22 15:34:09', '添加', '机构管理', '添加了一个机构', '1');
INSERT INTO `abc_operate_log` VALUES ('85', '1', '0:0:0:0:0:0:0:1', '2015-01-22 15:36:17', '添加', '机构管理', '添加了一个机构', '1');
INSERT INTO `abc_operate_log` VALUES ('86', '1', '0:0:0:0:0:0:0:1', '2015-01-22 15:47:57', '添加', '机构管理', '添加了一个机构', '1');
INSERT INTO `abc_operate_log` VALUES ('87', '1', '0:0:0:0:0:0:0:1', '2015-01-22 15:48:53', '添加', '机构管理', '添加了一个机构', '1');
INSERT INTO `abc_operate_log` VALUES ('88', '1', '0:0:0:0:0:0:0:1', '2015-01-22 15:49:25', '添加', '机构管理', '添加了一个机构', '1');
INSERT INTO `abc_operate_log` VALUES ('89', '1', '0:0:0:0:0:0:0:1', '2015-01-22 15:49:33', '删除', '机构管理', '删除了ID为84的机构', '1');
INSERT INTO `abc_operate_log` VALUES ('90', '1', '0:0:0:0:0:0:0:1', '2015-01-22 15:49:36', '修改', '机构管理', '禁用了ID为84的机构', '1');
INSERT INTO `abc_operate_log` VALUES ('91', '1', '0:0:0:0:0:0:0:1', '2015-01-22 15:49:39', '删除', '机构管理', '删除了ID为84的机构', '1');
INSERT INTO `abc_operate_log` VALUES ('92', '1', '0:0:0:0:0:0:0:1', '2015-01-22 15:49:44', '修改', '机构管理', '启用了ID为84的机构', '1');
INSERT INTO `abc_operate_log` VALUES ('93', '1', '0:0:0:0:0:0:0:1', '2015-01-22 15:49:53', '修改', '机构管理', '禁用了ID为88的机构', '1');
INSERT INTO `abc_operate_log` VALUES ('94', '1', '0:0:0:0:0:0:0:1', '2015-01-22 15:50:00', '删除', '机构管理', '删除了ID为88的机构', '1');
INSERT INTO `abc_operate_log` VALUES ('95', '1', '0:0:0:0:0:0:0:1', '2015-01-22 15:51:08', '修改', '机构管理', '修改了ID为88的机构', '1');
INSERT INTO `abc_operate_log` VALUES ('96', '1', '0:0:0:0:0:0:0:1', '2015-01-22 15:54:08', '修改', '机构管理', '修改了ID为88的机构', '1');
INSERT INTO `abc_operate_log` VALUES ('97', '1', '0:0:0:0:0:0:0:1', '2015-01-22 15:57:47', '修改', '机构管理', '修改了ID为88的机构', '1');
INSERT INTO `abc_operate_log` VALUES ('98', '1', '0:0:0:0:0:0:0:1', '2015-01-22 16:07:11', '修改', '机构管理', '修改了ID为88的机构', '1');
INSERT INTO `abc_operate_log` VALUES ('99', '1', '0:0:0:0:0:0:0:1', '2015-01-22 16:33:47', '添加', '管理员管理', '添加了一个管理员', '1');
INSERT INTO `abc_operate_log` VALUES ('100', '1', '0:0:0:0:0:0:0:1', '2015-01-22 17:01:04', '添加', '管理员管理', '添加了一个管理员', '1');
INSERT INTO `abc_operate_log` VALUES ('101', '1', '0:0:0:0:0:0:0:1', '2015-01-22 17:01:14', '添加', '管理员管理', '添加了一个管理员', '1');
INSERT INTO `abc_operate_log` VALUES ('102', '1', '0:0:0:0:0:0:0:1', '2015-01-22 17:01:24', '添加', '管理员管理', '添加了一个管理员', '1');
INSERT INTO `abc_operate_log` VALUES ('103', '1', '0:0:0:0:0:0:0:1', '2015-01-22 17:01:33', '添加', '管理员管理', '添加了一个管理员', '1');

-- ----------------------------
-- Table structure for `abc_oss_invoke_log`
-- ----------------------------
DROP TABLE IF EXISTS `abc_oss_invoke_log`;
CREATE TABLE `abc_oss_invoke_log` (
  `log_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '记录id',
  `log_req_method` varchar(8) DEFAULT NULL COMMENT 'HTTP请求方法',
  `log_req_content` varchar(1024) DEFAULT NULL COMMENT 'HTTP请求内容',
  `log_resp_code` int(11) DEFAULT NULL COMMENT 'HTTP响应码',
  `log_resp_content` varchar(1024) DEFAULT NULL COMMENT 'HTTP响应内容',
  `log_ext_msg` varchar(512) DEFAULT NULL COMMENT '其他信息',
  `log_status` int(11) DEFAULT NULL COMMENT 'HTTP调用状态 0失败 1成功',
  `log_time` datetime DEFAULT NULL COMMENT '调用记录日期时间',
  `log_refer_id` varchar(64) DEFAULT NULL COMMENT '引用id',
  PRIMARY KEY (`log_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of abc_oss_invoke_log
-- ----------------------------

-- ----------------------------
-- Table structure for `abc_pay_record`
-- ----------------------------
DROP TABLE IF EXISTS `abc_pay_record`;
CREATE TABLE `abc_pay_record` (
  `pr_id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `pr_payment_id` int(11) NOT NULL COMMENT '还款计划id',
  `pr_loan_id` int(11) NOT NULL COMMENT '贷款id',
  `pr_pay_period` int(11) NOT NULL COMMENT '还款计划期数',
  `pr_pay_total` decimal(18,2) NOT NULL COMMENT '实还总额',
  `pr_pay_money` decimal(18,2) DEFAULT NULL COMMENT '实还本金',
  `pr_pay_interest` decimal(18,2) DEFAULT NULL COMMENT '实还利息',
  `pr_pay_fine` decimal(18,2) DEFAULT NULL COMMENT '实还罚金',
  `pr_pay_service_fee` decimal(18,2) DEFAULT NULL COMMENT '实还平台服务费',
  `pr_pay_guar_fee` decimal(18,2) DEFAULT NULL COMMENT '实还担保服务费',
  `pr_pay_remain_money` decimal(18,2) DEFAULT NULL COMMENT '剩余罚金 因罚金是时时计算的，当第一次罚金未还清时，保留剩余未还罚金',
  `pr_cash_id` int(11) DEFAULT NULL COMMENT '借款人/投资人 对账表id',
  `pr_pay_type` tinyint(1) NOT NULL COMMENT '还款状态 1：正常还款 2：平台代还 3：强制还款',
  `pr_paytime` datetime NOT NULL COMMENT '实还日期',
  PRIMARY KEY (`pr_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='还款记录表';

-- ----------------------------
-- Records of abc_pay_record
-- ----------------------------

-- ----------------------------
-- Table structure for `abc_plan_income`
-- ----------------------------
DROP TABLE IF EXISTS `abc_plan_income`;
CREATE TABLE `abc_plan_income` (
  `pi_id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `pi_full_trans_record_id` int(11) NOT NULL COMMENT '满标资金划转记录id',
  `pi_payment_plan_id` int(11) NOT NULL COMMENT '借款人还款计划表id',
  `pi_invest_id` int(11) NOT NULL COMMENT '投资记录id',
  `pi_loan_id` int(11) NOT NULL COMMENT '普通标id',
  `pi_pay_capital` decimal(18,2) NOT NULL COMMENT '应还本金',
  `pi_pay_interest` decimal(18,2) NOT NULL DEFAULT '0.00' COMMENT '应还利息',
  `pi_pay_fine` decimal(18,2) NOT NULL DEFAULT '0.00' COMMENT '应还罚金',
  `pi_pay_total_money` decimal(18,2) NOT NULL DEFAULT '0.00' COMMENT '应还总额',
  `pi_collect_capital` decimal(18,2) NOT NULL DEFAULT '0.00' COMMENT '实还本金',
  `pi_collect_interest` decimal(18,2) NOT NULL DEFAULT '0.00' COMMENT '实还利息',
  `pi_collect_fine` decimal(18,2) NOT NULL DEFAULT '0.00' COMMENT '实还罚金',
  `pi_collect_total` decimal(18,2) NOT NULL DEFAULT '0.00' COMMENT '实还总额',
  `pi_paytime` datetime DEFAULT NULL COMMENT '应还日期',
  `pi_collecttime` datetime DEFAULT NULL COMMENT '实还日期',
  `pi_remain_fine` decimal(18,2) NOT NULL DEFAULT '0.00' COMMENT '剩余罚金 因罚金是时时计算的，当第一次罚金未还清时，保留剩余未还罚金',
  `pi_loan_period` int(11) NOT NULL COMMENT '期数',
  `pi_income_plan_state` tinyint(1) NOT NULL COMMENT '收益计划状态 -2:未激活 -1: 已删除 0:待收益 2:已结清 3:已被转出 4:已被收购',
  `pi_beneficiary` int(11) NOT NULL COMMENT '收益人',
  `pi_inner_seq_no` varchar(64) DEFAULT NULL COMMENT '内部交易流水号',
  `pi_createtime` datetime NOT NULL COMMENT '创建时间',
  `pi_modifytime` datetime NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`pi_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='投资人收回计划表';

-- ----------------------------
-- Records of abc_plan_income
-- ----------------------------

-- ----------------------------
-- Table structure for `abc_plan_payment`
-- ----------------------------
DROP TABLE IF EXISTS `abc_plan_payment`;
CREATE TABLE `abc_plan_payment` (
  `pp_id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `pp_full_trans_record_id` int(11) NOT NULL COMMENT '满标资金划转记录id',
  `pp_loan_id` int(11) NOT NULL COMMENT '原始标id',
  `pp_pay_capital` decimal(18,2) NOT NULL DEFAULT '0.00' COMMENT '应还本金',
  `pp_pay_interest` decimal(18,2) NOT NULL DEFAULT '0.00' COMMENT '应还利息',
  `pp_pay_fine` decimal(18,2) NOT NULL DEFAULT '0.00' COMMENT '应还罚金',
  `pp_pay_service_fee` decimal(18,2) NOT NULL DEFAULT '0.00' COMMENT '应还平台服务费',
  `pp_pay_guar_fee` decimal(18,2) NOT NULL DEFAULT '0.00' COMMENT '应还平台担保费',
  `pp_pay_total_money` decimal(18,2) NOT NULL DEFAULT '0.00' COMMENT '应还总额',
  `pp_pay_collect_capital` decimal(18,2) NOT NULL DEFAULT '0.00' COMMENT '实还本金',
  `pp_pay_collect_interest` decimal(18,2) NOT NULL DEFAULT '0.00' COMMENT '实还利息',
  `pp_pay_collect_fine` decimal(18,2) NOT NULL DEFAULT '0.00' COMMENT '实还罚金',
  `pp_collect_service_fee` decimal(18,2) NOT NULL DEFAULT '0.00' COMMENT '实还平台服务费',
  `pp_collect_guar_fee` decimal(18,2) NOT NULL DEFAULT '0.00' COMMENT '实还担保服务费',
  `pp_collect_total` decimal(18,2) NOT NULL DEFAULT '0.00' COMMENT '实还总额',
  `pp_remain_fine` decimal(18,2) NOT NULL DEFAULT '0.00' COMMENT '剩余罚金 因罚金是时时计算的，当第一次罚金未还清时，保留剩余未还罚金',
  `pp_loan_period` int(11) NOT NULL COMMENT '期数',
  `pp_is_clear` tinyint(11) NOT NULL DEFAULT '0' COMMENT '是否还清 1:是 0:否',
  `pp_paytime` datetime NOT NULL COMMENT '应还日期',
  `pp_collecttime` datetime DEFAULT NULL COMMENT '实还日期',
  `pp_pay_state` tinyint(1) NOT NULL COMMENT '还款状态 －1:未激活 0:未还清 1:付款中 2:已还清',
  `pp_pay_type` tinyint(1) DEFAULT NULL COMMENT '还款类型 1:正常还款 2:平台代还 3:强制还款',
  `pp_replace_state` tinyint(1) DEFAULT '0' COMMENT '代还是否已还 1:是 0:否',
  `pp_loanee` int(11) NOT NULL COMMENT '借款人',
  `pp_inner_seq_no` varchar(64) DEFAULT NULL COMMENT '内部交易流水号',
  `pp_createtime` datetime NOT NULL COMMENT '创建时间',
  `pp_modifytime` datetime NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`pp_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='借款人还款计划表';

-- ----------------------------
-- Records of abc_plan_payment
-- ----------------------------

-- ----------------------------
-- Table structure for `abc_product_info`
-- ----------------------------
DROP TABLE IF EXISTS `abc_product_info`;
CREATE TABLE `abc_product_info` (
  `product_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `product_name` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '产品名称',
  `product_mark` varchar(30) COLLATE utf8_bin DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`product_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='产品信息表';

-- ----------------------------
-- Records of abc_product_info
-- ----------------------------

-- ----------------------------
-- Table structure for `abc_product_rate`
-- ----------------------------
DROP TABLE IF EXISTS `abc_product_rate`;
CREATE TABLE `abc_product_rate` (
  `product_rate_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `product_id` int(11) DEFAULT NULL COMMENT '产品ID',
  `product_rate` decimal(18,2) DEFAULT NULL COMMENT '年化收益率',
  `product_min_period` int(11) DEFAULT NULL COMMENT '最小期限',
  `product_max_period` int(11) DEFAULT NULL COMMENT '最大期限',
  PRIMARY KEY (`product_rate_id`),
  KEY `FY_product_id_1` (`product_id`)
) ENGINE=MyISAM AUTO_INCREMENT=11 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='产品期限利率表';

-- ----------------------------
-- Records of abc_product_rate
-- ----------------------------

-- ----------------------------
-- Table structure for `abc_realname_auth`
-- ----------------------------
DROP TABLE IF EXISTS `abc_realname_auth`;
CREATE TABLE `abc_realname_auth` (
  `rnp_id` int(11) NOT NULL AUTO_INCREMENT,
  `rnp_userid` int(11) DEFAULT NULL COMMENT '用户ID',
  `rnp_name` varchar(20) COLLATE utf8_bin DEFAULT NULL COMMENT '真实姓名',
  `rnp_sex` tinyint(1) DEFAULT NULL COMMENT '性别	0：女 1：男',
  `rnp_nation` char(15) COLLATE utf8_bin DEFAULT NULL COMMENT '民族',
  `rnp_birthday` datetime DEFAULT NULL COMMENT '出生日期',
  `rnp_doc_type` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '证件类型',
  `rnp_doc_no` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '证件号码',
  `rnp_native` varchar(25) COLLATE utf8_bin DEFAULT NULL COMMENT '籍贯',
  `rnp_idcard_front` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '身份证正面照片',
  `rnp_idcard_back` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '身份证背面照片',
  `rnp_apply_date` datetime DEFAULT NULL COMMENT '申请日期',
  `rnp_review_state` tinyint(1) DEFAULT NULL COMMENT '审核状态	0：待审核 1：审核已通过 2：审核未通过',
  `rnp_review_date` datetime DEFAULT NULL COMMENT '审核日期',
  `rnp_review_note` varchar(512) COLLATE utf8_bin DEFAULT NULL COMMENT '审核意见',
  PRIMARY KEY (`rnp_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin ROW_FORMAT=COMPACT COMMENT='实名认证申请表';

-- ----------------------------
-- Records of abc_realname_auth
-- ----------------------------

-- ----------------------------
-- Table structure for `abc_recharge_record`
-- ----------------------------
DROP TABLE IF EXISTS `abc_recharge_record`;
CREATE TABLE `abc_recharge_record` (
  `recharge_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '充值记录表',
  `recharge_user_id` int(11) DEFAULT NULL COMMENT '充值用户id',
  `recharge_account_id` varchar(18) COLLATE utf8_bin DEFAULT NULL COMMENT '充值账户',
  `recharge_seq_no` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '交易流水号',
  `recharge_amount` decimal(18,2) DEFAULT NULL COMMENT '充值金额',
  `recharge_state` int(1) DEFAULT NULL COMMENT '充值状态，0充值中，1充值成功，2充值不成功',
  `recharge_date` datetime DEFAULT NULL COMMENT '充值时间',
  PRIMARY KEY (`recharge_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='充值记录表';

-- ----------------------------
-- Records of abc_recharge_record
-- ----------------------------

-- ----------------------------
-- Table structure for `abc_red`
-- ----------------------------
DROP TABLE IF EXISTS `abc_red`;
CREATE TABLE `abc_red` (
  `red_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '红包编号',
  `red_name` varchar(50) DEFAULT NULL COMMENT '红包名称',
  `red_desc` varchar(500) DEFAULT NULL COMMENT '红包描述',
  `red_type` tinyint(1) DEFAULT NULL COMMENT '红包类型   1：新手专享红包 2：新手特权红包 \r\n3：成功进阶红包 4：项目奖励红包\r\n5：个人奖励红包 6：推荐邀请红包',
  `red_amount` decimal(18,2) DEFAULT NULL COMMENT '红包金额',
  `red_createtime` datetime DEFAULT NULL COMMENT '红包创建时间',
  `red_creator` int(11) DEFAULT NULL COMMENT '创建红包的用户id abc_user',
  `red_state` int(1) DEFAULT NULL COMMENT '红包机制状态 -1：删除  0：无效 1：有效',
  `red_reward_number` int(11) DEFAULT NULL COMMENT '红包发放次数,当红包为个人奖励类型时，记录个人奖励红包数；当红包为项目奖励类型时，记录项目奖励红包数',
  `red_amt` decimal(11,2) DEFAULT NULL COMMENT '红包发放金额',
  `red_theme` varchar(60) DEFAULT NULL COMMENT '红包主题',
  `red_use_scope` varchar(200) DEFAULT NULL COMMENT '使用范围（如：投资|受让|收购）多个产品范围时，用“|”分割',
  `red_bizid` int(11) DEFAULT NULL COMMENT '项目id',
  `red_closetime` datetime DEFAULT NULL COMMENT '''红包失效时间',
  `red_sendtime` datetime DEFAULT NULL COMMENT '红包发放时间',
  PRIMARY KEY (`red_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='红包表';

-- ----------------------------
-- Records of abc_red
-- ----------------------------

-- ----------------------------
-- Table structure for `abc_red_send`
-- ----------------------------
DROP TABLE IF EXISTS `abc_red_send`;
CREATE TABLE `abc_red_send` (
  `rs_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '发放的红包记录编号',
  `rs_red_id` int(11) DEFAULT NULL COMMENT '红包机制 abc_redenvelope',
  `rs_amt` decimal(11,2) DEFAULT NULL COMMENT '红包发放金额',
  `rs_theme` varchar(60) DEFAULT NULL COMMENT '红包奖励主题',
  `rs_valid_amount` decimal(18,2) DEFAULT NULL COMMENT '红包有效金额',
  `rs_type` tinyint(1) DEFAULT NULL COMMENT '红包奖励类型：1：个人奖励；2：项目奖励',
  `rs_userid` int(11) DEFAULT NULL COMMENT '红包用户id',
  `rs_bizid` int(11) DEFAULT NULL COMMENT '项目id',
  `rs_use_scope` varchar(200) DEFAULT NULL COMMENT '使用范围（如：投资|受让|收购）多个产品范围时，用“|”分割',
  `rs_lifetime` int(2) DEFAULT NULL COMMENT '红包有效使用期限 即发送红包后的有效使用时间 单位/月',
  `rs_invest_amount` decimal(18,2) DEFAULT NULL COMMENT '投资奖励自动发放条件',
  `rs_starttime` datetime DEFAULT NULL COMMENT '红包开始有效时间',
  `rs_closetime` datetime DEFAULT NULL COMMENT '红包失效时间',
  `rs_sendtime` datetime DEFAULT NULL COMMENT '红包发放时间',
  `rs_sender` int(11) DEFAULT NULL COMMENT '发放红包的用户id abc_user',
  `rs_state` tinyint(1) DEFAULT '0' COMMENT '红包使用状态 :  0:已过期 1:未使用，2：已使用',
  PRIMARY KEY (`rs_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='红包发放表';

-- ----------------------------
-- Records of abc_red_send
-- ----------------------------

-- ----------------------------
-- Table structure for `abc_red_use`
-- ----------------------------
DROP TABLE IF EXISTS `abc_red_use`;
CREATE TABLE `abc_red_use` (
  `ru_id` int(11) NOT NULL AUTO_INCREMENT,
  `ru_redvsend_id` int(11) NOT NULL COMMENT '红包发放id',
  `ru_type` int(2) DEFAULT NULL COMMENT '红包使用类型 ：如投资',
  `ru_biz_id` int(11) DEFAULT NULL COMMENT '红包使用投资id 如果收购也可以使用就再添加一个收购id',
  `ru_userid` int(11) NOT NULL COMMENT '红包用户id',
  `ru_usetime` datetime DEFAULT NULL COMMENT '红包使用时间',
  `ru_desc` varchar(500) DEFAULT NULL COMMENT '红包使用描述',
  `ru_amount` decimal(18,2) DEFAULT NULL COMMENT '红包使用金额',
  `ru_remainder_amount` decimal(18,2) DEFAULT NULL COMMENT '红包剩余金额',
  `ru_deduct_fee` decimal(18,2) DEFAULT NULL COMMENT '抵扣手续费',
  `ru_deduct_discount` decimal(18,2) DEFAULT NULL COMMENT '抵扣折让费',
  `ru_usecount` int(11) DEFAULT NULL COMMENT '红包使用次数',
  PRIMARY KEY (`ru_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='红包使用表';

-- ----------------------------
-- Records of abc_red_use
-- ----------------------------

-- ----------------------------
-- Table structure for `abc_refund_apply`
-- ----------------------------
DROP TABLE IF EXISTS `abc_refund_apply`;
CREATE TABLE `abc_refund_apply` (
  `ra_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `ra_user_id` int(11) DEFAULT NULL COMMENT '用户id',
  `ra_account_no` varchar(18) COLLATE utf8_bin DEFAULT NULL COMMENT '退回账户号',
  `ra_reason` varchar(1000) COLLATE utf8_bin DEFAULT NULL COMMENT '退费原因',
  `ra_user_phone` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '用户手机号',
  `ra_amount` decimal(18,2) DEFAULT NULL COMMENT '退费金额',
  `ra_applicant_id` int(11) DEFAULT NULL COMMENT '申请人id employee',
  `ra_apply_date` datetime DEFAULT NULL COMMENT '申请日期',
  `ra_apply_state` tinyint(4) DEFAULT NULL COMMENT '申请状态 0待审核 1 通过 2 未通过',
  PRIMARY KEY (`ra_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='退费申请';

-- ----------------------------
-- Records of abc_refund_apply
-- ----------------------------

-- ----------------------------
-- Table structure for `abc_refund_record`
-- ----------------------------
DROP TABLE IF EXISTS `abc_refund_record`;
CREATE TABLE `abc_refund_record` (
  `refund_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `refund_user_id` int(11) DEFAULT NULL COMMENT '用户id',
  `refund_account_no` varchar(18) COLLATE utf8_bin DEFAULT NULL COMMENT '退回开户账户 取账户开户表中的：开户账户',
  `refund_user_phone` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '退回用户手机：网友基本信息表中user_phone，如果上述字段中没有值，取 账户开户表user_phone',
  `refund_reason` varchar(1000) COLLATE utf8_bin DEFAULT NULL COMMENT '退费原因',
  `refund_amount` decimal(18,2) NOT NULL COMMENT '退费金额',
  `refund_operator` int(11) DEFAULT NULL COMMENT '操作人id：employee',
  `refund_date` datetime DEFAULT NULL COMMENT '退费日期',
  `refund_seq_no` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '交易流水号',
  `refund_state` int(1) DEFAULT NULL COMMENT '退费状态(0退款中，1退款成功，2退款失败)',
  `refund_applicant` int(11) DEFAULT NULL COMMENT '退费申请人id',
  `refund_apply_date` datetime DEFAULT NULL COMMENT '申请日期',
  `refund_apply_opinion` varchar(1024) COLLATE utf8_bin DEFAULT NULL COMMENT '审核意见',
  `refund_apply_state` int(1) DEFAULT NULL COMMENT '申请状态(0待审核 1 通过 2 未通过)',
  `refund_check_date` datetime DEFAULT NULL COMMENT '审核日期',
  PRIMARY KEY (`refund_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='退费记录表';

-- ----------------------------
-- Records of abc_refund_record
-- ----------------------------

-- ----------------------------
-- Table structure for `abc_review`
-- ----------------------------
DROP TABLE IF EXISTS `abc_review`;
CREATE TABLE `abc_review` (
  `review_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '审核id',
  `review_origin_review_id` int(11) DEFAULT NULL COMMENT '创建此审核的原始审核id，可为空',
  `review_type` int(11) DEFAULT NULL COMMENT '审核类型，对应枚举类ReviewType',
  `review_apply_id` int(11) DEFAULT NULL COMMENT '对应的申请id',
  `review_info` varchar(512) DEFAULT NULL COMMENT '简要的审核说明信息',
  `review_suspend` int(11) DEFAULT NULL COMMENT '审核是否挂起 0否 1是 默认0未挂起',
  `review_end` int(11) DEFAULT NULL COMMENT '审核的流程是否已结束 0否 1是 默认0未结束',
  `review_curr_role_idx` int(11) DEFAULT NULL COMMENT '当前待审核角色index',
  `review_curr_emp_id` int(11) DEFAULT NULL COMMENT '当前待审核人id',
  `review_state` int(11) DEFAULT NULL COMMENT '审核状态 0待审核 1审核通过 2不通过',
  `review_last_op_log_id` int(11) DEFAULT NULL COMMENT '最后有效审核操作记录id',
  `review_last_send_log_id` int(11) DEFAULT NULL COMMENT '此审核相关的最后发送记录id, -1表示空，无记录',
  `review_version` int(11) DEFAULT NULL COMMENT '版本号，对审核的任何操作都会使其加1',
  `review_create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `review_update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `review_deleted` int(11) DEFAULT NULL COMMENT '逻辑删除标识，0未删除 -1已删除',
  PRIMARY KEY (`review_id`),
  KEY `unionIndex` (`review_type`,`review_apply_id`,`review_deleted`)
) ENGINE=InnoDB AUTO_INCREMENT=317 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of abc_review
-- ----------------------------
INSERT INTO `abc_review` VALUES ('316', null, '15', '64', null, '0', '0', '2', null, '0', null, '-1', '1', '2015-01-22 16:07:11', '2015-01-22 16:07:11', '0');

-- ----------------------------
-- Table structure for `abc_review_op_log`
-- ----------------------------
DROP TABLE IF EXISTS `abc_review_op_log`;
CREATE TABLE `abc_review_op_log` (
  `rol_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '审核操作记录id',
  `rol_review_id` int(11) DEFAULT NULL COMMENT '审核id',
  `rol_role_idx` int(11) DEFAULT NULL COMMENT '审核者角色的index',
  `rol_emp_id` int(11) DEFAULT NULL COMMENT '审核人id',
  `rol_op` int(11) DEFAULT NULL COMMENT '审核操作 1通过 2否决 3打回 4挂起 5转移',
  `rol_msg` varchar(512) DEFAULT NULL COMMENT '审核意见',
  `rol_next_role_idx` int(11) DEFAULT NULL COMMENT '下一审核人角色的index',
  `rol_next_emp_id` int(11) DEFAULT NULL COMMENT '下一审核人id',
  `rol_date` datetime DEFAULT NULL COMMENT '审核操作日期',
  PRIMARY KEY (`rol_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of abc_review_op_log
-- ----------------------------

-- ----------------------------
-- Table structure for `abc_review_send_log`
-- ----------------------------
DROP TABLE IF EXISTS `abc_review_send_log`;
CREATE TABLE `abc_review_send_log` (
  `rsl_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `rsl_review_id` int(11) DEFAULT NULL COMMENT '相关的ReviewID',
  `rsl_review_version` int(11) DEFAULT NULL COMMENT '此次发送时相关审核的版本号',
  `rsl_prev_id` int(11) DEFAULT NULL COMMENT '上一条发送记录ID',
  `rsl_from_role` int(11) DEFAULT NULL COMMENT '发送方角色',
  `rsl_from_emp` int(11) DEFAULT NULL COMMENT '发送方员工ID',
  `rsl_to_role` int(11) DEFAULT NULL COMMENT '接收方角色',
  `rsl_to_emp` int(11) DEFAULT NULL COMMENT '接收方员工ID',
  `rsl_next_review_id` int(11) DEFAULT NULL COMMENT '如果此次发送涉及到审核改变，改变后的ReviewID',
  `rsl_create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `rsl_update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`rsl_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of abc_review_send_log
-- ----------------------------

-- ----------------------------
-- Table structure for `abc_review_send_status`
-- ----------------------------
DROP TABLE IF EXISTS `abc_review_send_status`;
CREATE TABLE `abc_review_send_status` (
  `rss_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `rss_review_id` int(11) DEFAULT NULL COMMENT '相关联的审核ID',
  `rss_send_loan_gov` int(11) DEFAULT NULL COMMENT '是否发送到小贷公司 0否 1是',
  `rss_send_guar_gov` int(11) DEFAULT NULL COMMENT '是否发送到担保机构 0否 1是',
  `rss_send_platform` int(11) DEFAULT NULL COMMENT '是否发送到平台审核 0否 1是',
  PRIMARY KEY (`rss_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of abc_review_send_status
-- ----------------------------

-- ----------------------------
-- Table structure for `abc_role`
-- ----------------------------
DROP TABLE IF EXISTS `abc_role`;
CREATE TABLE `abc_role` (
  `role_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键，自增',
  `role_name` varchar(20) COLLATE utf8_bin NOT NULL COMMENT '角色名称',
  `role_default` int(11) DEFAULT '0',
  `role_sort` int(11) NOT NULL DEFAULT '0' COMMENT '角色排序',
  `role_des` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '备注',
  `role_createtime` datetime DEFAULT NULL COMMENT '建立时间',
  `role_modifytime` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='角色表';

-- ----------------------------
-- Records of abc_role
-- ----------------------------
INSERT INTO `abc_role` VALUES ('1', '理财经理', '0', '0', null, '2014-12-11 10:27:54', null);
INSERT INTO `abc_role` VALUES ('2', '平台客服', '0', '0', null, '2014-12-11 10:28:17', null);
INSERT INTO `abc_role` VALUES ('3', '平台财务', '0', '0', null, '2014-12-11 10:28:36', null);
INSERT INTO `abc_role` VALUES ('4', '借贷机构', '0', '0', null, '2014-12-11 10:28:50', null);
INSERT INTO `abc_role` VALUES ('5', '担保机构', '0', '0', null, '2014-12-11 10:29:01', null);
INSERT INTO `abc_role` VALUES ('6', '管理员', '1', '2', null, '2014-11-19 16:54:07', null);

-- ----------------------------
-- Table structure for `abc_role_btn`
-- ----------------------------
DROP TABLE IF EXISTS `abc_role_btn`;
CREATE TABLE `abc_role_btn` (
  `rbt_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键自增',
  `role_id` int(11) NOT NULL COMMENT 'abc_role外键',
  `btn_id` int(11) NOT NULL COMMENT 'abc_btn外键',
  `menu_id` int(11) NOT NULL COMMENT 'abc_menu外键',
  PRIMARY KEY (`rbt_id`)
) ENGINE=InnoDB AUTO_INCREMENT=19939 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='角色按钮表';

-- ----------------------------
-- Records of abc_role_btn
-- ----------------------------
INSERT INTO `abc_role_btn` VALUES ('19490', '6', '138', '18');
INSERT INTO `abc_role_btn` VALUES ('19491', '6', '139', '19');
INSERT INTO `abc_role_btn` VALUES ('19492', '6', '140', '19');
INSERT INTO `abc_role_btn` VALUES ('19493', '6', '141', '19');
INSERT INTO `abc_role_btn` VALUES ('19494', '6', '151', '19');
INSERT INTO `abc_role_btn` VALUES ('19495', '6', '139', '20');
INSERT INTO `abc_role_btn` VALUES ('19496', '6', '140', '20');
INSERT INTO `abc_role_btn` VALUES ('19497', '6', '141', '20');
INSERT INTO `abc_role_btn` VALUES ('19498', '6', '149', '20');
INSERT INTO `abc_role_btn` VALUES ('19499', '6', '139', '41');
INSERT INTO `abc_role_btn` VALUES ('19500', '6', '140', '41');
INSERT INTO `abc_role_btn` VALUES ('19501', '6', '141', '41');
INSERT INTO `abc_role_btn` VALUES ('19502', '6', '162', '41');
INSERT INTO `abc_role_btn` VALUES ('19503', '6', '175', '41');
INSERT INTO `abc_role_btn` VALUES ('19504', '6', '190', '41');
INSERT INTO `abc_role_btn` VALUES ('19505', '6', '176', '41');
INSERT INTO `abc_role_btn` VALUES ('19506', '6', '163', '41');
INSERT INTO `abc_role_btn` VALUES ('19507', '6', '138', '64');
INSERT INTO `abc_role_btn` VALUES ('19508', '6', '139', '60');
INSERT INTO `abc_role_btn` VALUES ('19509', '6', '140', '60');
INSERT INTO `abc_role_btn` VALUES ('19510', '6', '141', '60');
INSERT INTO `abc_role_btn` VALUES ('19511', '6', '163', '60');
INSERT INTO `abc_role_btn` VALUES ('19512', '6', '139', '58');
INSERT INTO `abc_role_btn` VALUES ('19513', '6', '140', '58');
INSERT INTO `abc_role_btn` VALUES ('19514', '6', '141', '58');
INSERT INTO `abc_role_btn` VALUES ('19515', '6', '163', '58');
INSERT INTO `abc_role_btn` VALUES ('19516', '6', '138', '120');
INSERT INTO `abc_role_btn` VALUES ('19517', '6', '138', '121');
INSERT INTO `abc_role_btn` VALUES ('19518', '6', '138', '122');
INSERT INTO `abc_role_btn` VALUES ('19519', '6', '138', '66');
INSERT INTO `abc_role_btn` VALUES ('19520', '6', '138', '123');
INSERT INTO `abc_role_btn` VALUES ('19521', '6', '138', '124');
INSERT INTO `abc_role_btn` VALUES ('19522', '6', '139', '53');
INSERT INTO `abc_role_btn` VALUES ('19523', '6', '140', '53');
INSERT INTO `abc_role_btn` VALUES ('19524', '6', '141', '53');
INSERT INTO `abc_role_btn` VALUES ('19525', '6', '198', '53');
INSERT INTO `abc_role_btn` VALUES ('19526', '6', '139', '125');
INSERT INTO `abc_role_btn` VALUES ('19527', '6', '140', '125');
INSERT INTO `abc_role_btn` VALUES ('19528', '6', '141', '125');
INSERT INTO `abc_role_btn` VALUES ('19537', '1', '163', '128');
INSERT INTO `abc_role_btn` VALUES ('19538', '1', '138', '66');
INSERT INTO `abc_role_btn` VALUES ('19539', '1', '138', '138');
INSERT INTO `abc_role_btn` VALUES ('19540', '1', '138', '76');
INSERT INTO `abc_role_btn` VALUES ('19541', '1', '138', '77');
INSERT INTO `abc_role_btn` VALUES ('19542', '1', '138', '126');
INSERT INTO `abc_role_btn` VALUES ('19543', '1', '138', '79');
INSERT INTO `abc_role_btn` VALUES ('19563', '5', '138', '117');
INSERT INTO `abc_role_btn` VALUES ('19564', '5', '147', '117');
INSERT INTO `abc_role_btn` VALUES ('19565', '5', '158', '117');
INSERT INTO `abc_role_btn` VALUES ('19566', '5', '159', '117');
INSERT INTO `abc_role_btn` VALUES ('19567', '5', '162', '117');
INSERT INTO `abc_role_btn` VALUES ('19568', '5', '163', '117');
INSERT INTO `abc_role_btn` VALUES ('19569', '5', '172', '117');
INSERT INTO `abc_role_btn` VALUES ('19570', '5', '193', '117');
INSERT INTO `abc_role_btn` VALUES ('19571', '5', '194', '117');
INSERT INTO `abc_role_btn` VALUES ('19572', '5', '138', '138');
INSERT INTO `abc_role_btn` VALUES ('19573', '5', '162', '80');
INSERT INTO `abc_role_btn` VALUES ('19574', '5', '163', '80');
INSERT INTO `abc_role_btn` VALUES ('19575', '5', '138', '66');
INSERT INTO `abc_role_btn` VALUES ('19576', '5', '138', '76');
INSERT INTO `abc_role_btn` VALUES ('19577', '5', '138', '43');
INSERT INTO `abc_role_btn` VALUES ('19578', '5', '138', '78');
INSERT INTO `abc_role_btn` VALUES ('19579', '5', '138', '79');
INSERT INTO `abc_role_btn` VALUES ('19580', '3', '138', '102');
INSERT INTO `abc_role_btn` VALUES ('19581', '3', '147', '102');
INSERT INTO `abc_role_btn` VALUES ('19582', '3', '162', '102');
INSERT INTO `abc_role_btn` VALUES ('19583', '3', '163', '119');
INSERT INTO `abc_role_btn` VALUES ('19584', '3', '138', '138');
INSERT INTO `abc_role_btn` VALUES ('19585', '3', '138', '81');
INSERT INTO `abc_role_btn` VALUES ('19586', '3', '138', '66');
INSERT INTO `abc_role_btn` VALUES ('19587', '3', '138', '113');
INSERT INTO `abc_role_btn` VALUES ('19588', '3', '163', '113');
INSERT INTO `abc_role_btn` VALUES ('19589', '3', '180', '113');
INSERT INTO `abc_role_btn` VALUES ('19590', '3', '138', '96');
INSERT INTO `abc_role_btn` VALUES ('19591', '3', '138', '112');
INSERT INTO `abc_role_btn` VALUES ('19592', '3', '163', '112');
INSERT INTO `abc_role_btn` VALUES ('19593', '3', '180', '112');
INSERT INTO `abc_role_btn` VALUES ('19594', '3', '138', '82');
INSERT INTO `abc_role_btn` VALUES ('19595', '3', '163', '82');
INSERT INTO `abc_role_btn` VALUES ('19596', '3', '138', '93');
INSERT INTO `abc_role_btn` VALUES ('19597', '3', '138', '111');
INSERT INTO `abc_role_btn` VALUES ('19598', '3', '163', '111');
INSERT INTO `abc_role_btn` VALUES ('19599', '3', '180', '111');
INSERT INTO `abc_role_btn` VALUES ('19600', '3', '138', '74');
INSERT INTO `abc_role_btn` VALUES ('19601', '3', '139', '74');
INSERT INTO `abc_role_btn` VALUES ('19602', '3', '140', '74');
INSERT INTO `abc_role_btn` VALUES ('19603', '3', '141', '74');
INSERT INTO `abc_role_btn` VALUES ('19638', '4', '147', '118');
INSERT INTO `abc_role_btn` VALUES ('19639', '4', '158', '118');
INSERT INTO `abc_role_btn` VALUES ('19640', '4', '159', '118');
INSERT INTO `abc_role_btn` VALUES ('19641', '4', '162', '118');
INSERT INTO `abc_role_btn` VALUES ('19642', '4', '163', '118');
INSERT INTO `abc_role_btn` VALUES ('19643', '4', '172', '118');
INSERT INTO `abc_role_btn` VALUES ('19644', '4', '192', '118');
INSERT INTO `abc_role_btn` VALUES ('19645', '4', '193', '118');
INSERT INTO `abc_role_btn` VALUES ('19646', '4', '194', '118');
INSERT INTO `abc_role_btn` VALUES ('19647', '4', '138', '138');
INSERT INTO `abc_role_btn` VALUES ('19648', '4', '138', '116');
INSERT INTO `abc_role_btn` VALUES ('19649', '4', '138', '71');
INSERT INTO `abc_role_btn` VALUES ('19650', '4', '162', '71');
INSERT INTO `abc_role_btn` VALUES ('19651', '4', '174', '71');
INSERT INTO `abc_role_btn` VALUES ('19652', '4', '184', '71');
INSERT INTO `abc_role_btn` VALUES ('19653', '4', '189', '71');
INSERT INTO `abc_role_btn` VALUES ('19654', '4', '139', '115');
INSERT INTO `abc_role_btn` VALUES ('19655', '4', '140', '115');
INSERT INTO `abc_role_btn` VALUES ('19656', '4', '141', '115');
INSERT INTO `abc_role_btn` VALUES ('19657', '4', '158', '115');
INSERT INTO `abc_role_btn` VALUES ('19658', '4', '162', '115');
INSERT INTO `abc_role_btn` VALUES ('19659', '4', '163', '115');
INSERT INTO `abc_role_btn` VALUES ('19660', '4', '172', '115');
INSERT INTO `abc_role_btn` VALUES ('19661', '4', '192', '115');
INSERT INTO `abc_role_btn` VALUES ('19662', '4', '194', '115');
INSERT INTO `abc_role_btn` VALUES ('19663', '4', '138', '114');
INSERT INTO `abc_role_btn` VALUES ('19664', '4', '162', '80');
INSERT INTO `abc_role_btn` VALUES ('19665', '4', '163', '80');
INSERT INTO `abc_role_btn` VALUES ('19666', '4', '138', '66');
INSERT INTO `abc_role_btn` VALUES ('19667', '4', '138', '76');
INSERT INTO `abc_role_btn` VALUES ('19668', '4', '138', '43');
INSERT INTO `abc_role_btn` VALUES ('19669', '4', '138', '78');
INSERT INTO `abc_role_btn` VALUES ('19670', '4', '138', '79');
INSERT INTO `abc_role_btn` VALUES ('19784', '2', '147', '136');
INSERT INTO `abc_role_btn` VALUES ('19785', '2', '162', '136');
INSERT INTO `abc_role_btn` VALUES ('19786', '2', '163', '136');
INSERT INTO `abc_role_btn` VALUES ('19787', '2', '138', '138');
INSERT INTO `abc_role_btn` VALUES ('19788', '2', '138', '129');
INSERT INTO `abc_role_btn` VALUES ('19789', '2', '138', '131');
INSERT INTO `abc_role_btn` VALUES ('19790', '2', '147', '131');
INSERT INTO `abc_role_btn` VALUES ('19791', '2', '162', '131');
INSERT INTO `abc_role_btn` VALUES ('19792', '2', '163', '131');
INSERT INTO `abc_role_btn` VALUES ('19793', '2', '138', '135');
INSERT INTO `abc_role_btn` VALUES ('19794', '2', '147', '134');
INSERT INTO `abc_role_btn` VALUES ('19795', '2', '162', '134');
INSERT INTO `abc_role_btn` VALUES ('19796', '2', '163', '134');
INSERT INTO `abc_role_btn` VALUES ('19797', '2', '172', '134');
INSERT INTO `abc_role_btn` VALUES ('19798', '2', '138', '26');
INSERT INTO `abc_role_btn` VALUES ('19799', '2', '139', '26');
INSERT INTO `abc_role_btn` VALUES ('19800', '2', '140', '26');
INSERT INTO `abc_role_btn` VALUES ('19801', '2', '138', '38');
INSERT INTO `abc_role_btn` VALUES ('19802', '2', '147', '39');
INSERT INTO `abc_role_btn` VALUES ('19803', '2', '162', '39');
INSERT INTO `abc_role_btn` VALUES ('19804', '2', '163', '39');
INSERT INTO `abc_role_btn` VALUES ('19805', '2', '172', '39');
INSERT INTO `abc_role_btn` VALUES ('19806', '2', '139', '36');
INSERT INTO `abc_role_btn` VALUES ('19807', '2', '140', '36');
INSERT INTO `abc_role_btn` VALUES ('19808', '2', '141', '36');
INSERT INTO `abc_role_btn` VALUES ('19809', '2', '163', '36');
INSERT INTO `abc_role_btn` VALUES ('19810', '2', '140', '42');
INSERT INTO `abc_role_btn` VALUES ('19811', '2', '162', '42');
INSERT INTO `abc_role_btn` VALUES ('19812', '2', '163', '42');
INSERT INTO `abc_role_btn` VALUES ('19813', '2', '138', '43');
INSERT INTO `abc_role_btn` VALUES ('19814', '2', '138', '40');
INSERT INTO `abc_role_btn` VALUES ('19815', '2', '138', '47');
INSERT INTO `abc_role_btn` VALUES ('19816', '2', '139', '44');
INSERT INTO `abc_role_btn` VALUES ('19817', '2', '140', '44');
INSERT INTO `abc_role_btn` VALUES ('19818', '2', '141', '44');
INSERT INTO `abc_role_btn` VALUES ('19819', '2', '162', '44');
INSERT INTO `abc_role_btn` VALUES ('19820', '2', '163', '44');
INSERT INTO `abc_role_btn` VALUES ('19821', '2', '147', '45');
INSERT INTO `abc_role_btn` VALUES ('19822', '2', '158', '45');
INSERT INTO `abc_role_btn` VALUES ('19823', '2', '159', '45');
INSERT INTO `abc_role_btn` VALUES ('19824', '2', '162', '45');
INSERT INTO `abc_role_btn` VALUES ('19825', '2', '163', '45');
INSERT INTO `abc_role_btn` VALUES ('19826', '2', '172', '45');
INSERT INTO `abc_role_btn` VALUES ('19827', '2', '147', '51');
INSERT INTO `abc_role_btn` VALUES ('19828', '2', '158', '51');
INSERT INTO `abc_role_btn` VALUES ('19829', '2', '162', '51');
INSERT INTO `abc_role_btn` VALUES ('19830', '2', '163', '51');
INSERT INTO `abc_role_btn` VALUES ('19831', '2', '172', '51');
INSERT INTO `abc_role_btn` VALUES ('19832', '2', '192', '51');
INSERT INTO `abc_role_btn` VALUES ('19833', '2', '193', '51');
INSERT INTO `abc_role_btn` VALUES ('19834', '2', '194', '51');
INSERT INTO `abc_role_btn` VALUES ('19835', '2', '138', '50');
INSERT INTO `abc_role_btn` VALUES ('19836', '2', '147', '49');
INSERT INTO `abc_role_btn` VALUES ('19837', '2', '162', '49');
INSERT INTO `abc_role_btn` VALUES ('19838', '2', '163', '49');
INSERT INTO `abc_role_btn` VALUES ('19839', '2', '172', '49');
INSERT INTO `abc_role_btn` VALUES ('19840', '2', '147', '48');
INSERT INTO `abc_role_btn` VALUES ('19841', '2', '162', '48');
INSERT INTO `abc_role_btn` VALUES ('19842', '2', '163', '48');
INSERT INTO `abc_role_btn` VALUES ('19843', '2', '172', '48');
INSERT INTO `abc_role_btn` VALUES ('19844', '2', '139', '59');
INSERT INTO `abc_role_btn` VALUES ('19845', '2', '140', '59');
INSERT INTO `abc_role_btn` VALUES ('19846', '2', '141', '59');
INSERT INTO `abc_role_btn` VALUES ('19847', '2', '162', '59');
INSERT INTO `abc_role_btn` VALUES ('19848', '2', '163', '59');
INSERT INTO `abc_role_btn` VALUES ('19849', '2', '175', '59');
INSERT INTO `abc_role_btn` VALUES ('19850', '2', '176', '59');
INSERT INTO `abc_role_btn` VALUES ('19851', '2', '190', '59');
INSERT INTO `abc_role_btn` VALUES ('19852', '2', '139', '63');
INSERT INTO `abc_role_btn` VALUES ('19853', '2', '140', '63');
INSERT INTO `abc_role_btn` VALUES ('19854', '2', '141', '63');
INSERT INTO `abc_role_btn` VALUES ('19855', '2', '162', '63');
INSERT INTO `abc_role_btn` VALUES ('19856', '2', '138', '62');
INSERT INTO `abc_role_btn` VALUES ('19857', '2', '139', '68');
INSERT INTO `abc_role_btn` VALUES ('19858', '2', '140', '68');
INSERT INTO `abc_role_btn` VALUES ('19859', '2', '141', '68');
INSERT INTO `abc_role_btn` VALUES ('19860', '2', '162', '68');
INSERT INTO `abc_role_btn` VALUES ('19861', '2', '163', '68');
INSERT INTO `abc_role_btn` VALUES ('19862', '2', '139', '69');
INSERT INTO `abc_role_btn` VALUES ('19863', '2', '147', '70');
INSERT INTO `abc_role_btn` VALUES ('19864', '2', '162', '70');
INSERT INTO `abc_role_btn` VALUES ('19865', '2', '163', '70');
INSERT INTO `abc_role_btn` VALUES ('19866', '2', '172', '70');
INSERT INTO `abc_role_btn` VALUES ('19867', '2', '138', '71');
INSERT INTO `abc_role_btn` VALUES ('19868', '2', '162', '71');
INSERT INTO `abc_role_btn` VALUES ('19869', '2', '174', '71');
INSERT INTO `abc_role_btn` VALUES ('19870', '2', '184', '71');
INSERT INTO `abc_role_btn` VALUES ('19871', '2', '189', '71');
INSERT INTO `abc_role_btn` VALUES ('19872', '2', '138', '66');
INSERT INTO `abc_role_btn` VALUES ('19873', '2', '138', '67');
INSERT INTO `abc_role_btn` VALUES ('19874', '2', '138', '72');
INSERT INTO `abc_role_btn` VALUES ('19875', '2', '139', '72');
INSERT INTO `abc_role_btn` VALUES ('19876', '2', '140', '72');
INSERT INTO `abc_role_btn` VALUES ('19877', '2', '141', '72');
INSERT INTO `abc_role_btn` VALUES ('19878', '2', '138', '73');
INSERT INTO `abc_role_btn` VALUES ('19879', '2', '147', '73');
INSERT INTO `abc_role_btn` VALUES ('19880', '2', '163', '73');
INSERT INTO `abc_role_btn` VALUES ('19881', '2', '172', '73');
INSERT INTO `abc_role_btn` VALUES ('19882', '2', '138', '75');
INSERT INTO `abc_role_btn` VALUES ('19883', '2', '138', '85');
INSERT INTO `abc_role_btn` VALUES ('19884', '2', '147', '84');
INSERT INTO `abc_role_btn` VALUES ('19885', '2', '162', '84');
INSERT INTO `abc_role_btn` VALUES ('19886', '2', '163', '84');
INSERT INTO `abc_role_btn` VALUES ('19887', '2', '138', '87');
INSERT INTO `abc_role_btn` VALUES ('19888', '2', '163', '86');
INSERT INTO `abc_role_btn` VALUES ('19889', '2', '139', '92');
INSERT INTO `abc_role_btn` VALUES ('19890', '2', '140', '92');
INSERT INTO `abc_role_btn` VALUES ('19891', '2', '141', '92');
INSERT INTO `abc_role_btn` VALUES ('19892', '2', '163', '92');
INSERT INTO `abc_role_btn` VALUES ('19893', '2', '177', '92');
INSERT INTO `abc_role_btn` VALUES ('19894', '2', '196', '92');
INSERT INTO `abc_role_btn` VALUES ('19895', '2', '139', '95');
INSERT INTO `abc_role_btn` VALUES ('19896', '2', '140', '95');
INSERT INTO `abc_role_btn` VALUES ('19897', '2', '141', '95');
INSERT INTO `abc_role_btn` VALUES ('19898', '2', '162', '95');
INSERT INTO `abc_role_btn` VALUES ('19899', '2', '163', '95');
INSERT INTO `abc_role_btn` VALUES ('19900', '2', '147', '94');
INSERT INTO `abc_role_btn` VALUES ('19901', '2', '162', '94');
INSERT INTO `abc_role_btn` VALUES ('19902', '2', '163', '94');
INSERT INTO `abc_role_btn` VALUES ('19903', '2', '142', '89');
INSERT INTO `abc_role_btn` VALUES ('19904', '2', '162', '89');
INSERT INTO `abc_role_btn` VALUES ('19905', '2', '163', '89');
INSERT INTO `abc_role_btn` VALUES ('19906', '2', '206', '89');
INSERT INTO `abc_role_btn` VALUES ('19907', '2', '142', '88');
INSERT INTO `abc_role_btn` VALUES ('19908', '2', '159', '88');
INSERT INTO `abc_role_btn` VALUES ('19909', '2', '162', '88');
INSERT INTO `abc_role_btn` VALUES ('19910', '2', '163', '88');
INSERT INTO `abc_role_btn` VALUES ('19911', '2', '196', '88');
INSERT INTO `abc_role_btn` VALUES ('19912', '2', '204', '88');
INSERT INTO `abc_role_btn` VALUES ('19913', '2', '138', '91');
INSERT INTO `abc_role_btn` VALUES ('19914', '2', '142', '90');
INSERT INTO `abc_role_btn` VALUES ('19915', '2', '162', '90');
INSERT INTO `abc_role_btn` VALUES ('19916', '2', '163', '90');
INSERT INTO `abc_role_btn` VALUES ('19917', '2', '147', '110');
INSERT INTO `abc_role_btn` VALUES ('19918', '2', '162', '110');
INSERT INTO `abc_role_btn` VALUES ('19919', '2', '163', '110');
INSERT INTO `abc_role_btn` VALUES ('19920', '2', '172', '110');
INSERT INTO `abc_role_btn` VALUES ('19921', '2', '138', '108');
INSERT INTO `abc_role_btn` VALUES ('19922', '2', '147', '109');
INSERT INTO `abc_role_btn` VALUES ('19923', '2', '162', '109');
INSERT INTO `abc_role_btn` VALUES ('19924', '2', '163', '109');
INSERT INTO `abc_role_btn` VALUES ('19925', '2', '172', '109');
INSERT INTO `abc_role_btn` VALUES ('19926', '2', '138', '106');
INSERT INTO `abc_role_btn` VALUES ('19927', '2', '138', '107');
INSERT INTO `abc_role_btn` VALUES ('19928', '2', '138', '116');
INSERT INTO `abc_role_btn` VALUES ('19929', '2', '139', '115');
INSERT INTO `abc_role_btn` VALUES ('19930', '2', '140', '115');
INSERT INTO `abc_role_btn` VALUES ('19931', '2', '141', '115');
INSERT INTO `abc_role_btn` VALUES ('19932', '2', '158', '115');
INSERT INTO `abc_role_btn` VALUES ('19933', '2', '162', '115');
INSERT INTO `abc_role_btn` VALUES ('19934', '2', '163', '115');
INSERT INTO `abc_role_btn` VALUES ('19935', '2', '172', '115');
INSERT INTO `abc_role_btn` VALUES ('19936', '2', '192', '115');
INSERT INTO `abc_role_btn` VALUES ('19937', '2', '194', '115');
INSERT INTO `abc_role_btn` VALUES ('19938', '2', '138', '114');

-- ----------------------------
-- Table structure for `abc_role_log`
-- ----------------------------
DROP TABLE IF EXISTS `abc_role_log`;
CREATE TABLE `abc_role_log` (
  `al_id` int(11) NOT NULL AUTO_INCREMENT,
  `emp_id` int(11) NOT NULL COMMENT '操作人  abc_employee外键',
  `menu_id` int(11) NOT NULL COMMENT '被操作的menu  abc_menu外键',
  `btn_id` int(11) NOT NULL COMMENT '被操作的btn   abc_btn外键',
  `role_id` int(11) NOT NULL COMMENT '被操作角色 abc_role外键',
  `operat_before` varchar(100) COLLATE utf8_bin DEFAULT NULL,
  `operat_after` varchar(100) COLLATE utf8_bin DEFAULT NULL,
  `operat_time` datetime NOT NULL,
  PRIMARY KEY (`al_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='权限授予资料表';

-- ----------------------------
-- Records of abc_role_log
-- ----------------------------

-- ----------------------------
-- Table structure for `abc_scene_prove`
-- ----------------------------
DROP TABLE IF EXISTS `abc_scene_prove`;
CREATE TABLE `abc_scene_prove` (
  `sp_id` int(11) NOT NULL AUTO_INCREMENT,
  `sp_user_id` int(11) DEFAULT NULL COMMENT '用户ID 关联user表',
  `sp_apply_date` datetime DEFAULT NULL COMMENT '申请日期',
  `sp_note` varchar(512) COLLATE utf8_bin DEFAULT NULL COMMENT '备注',
  `sp_prove_state` tinyint(1) DEFAULT NULL COMMENT '审核状态	0：待审核 1：审核已通过 2：审核未通过',
  PRIMARY KEY (`sp_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin ROW_FORMAT=COMPACT COMMENT='现场认证申请表';

-- ----------------------------
-- Records of abc_scene_prove
-- ----------------------------

-- ----------------------------
-- Table structure for `abc_score`
-- ----------------------------
DROP TABLE IF EXISTS `abc_score`;
CREATE TABLE `abc_score` (
  `score_id` int(11) NOT NULL AUTO_INCREMENT,
  `score_name` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '积分名称',
  `score_code` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '积分代码',
  `score_value` int(11) DEFAULT NULL COMMENT '积分值',
  `score_createtime` datetime DEFAULT NULL COMMENT '积分创建时间',
  `score_state` tinyint(1) DEFAULT '1' COMMENT '积分状态 0：禁用 1：启用 2：已删除',
  PRIMARY KEY (`score_id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8 COLLATE=utf8_bin ROW_FORMAT=COMPACT COMMENT='积分类型表';

-- ----------------------------
-- Records of abc_score
-- ----------------------------
INSERT INTO `abc_score` VALUES ('1', '邀请推荐', 'yqtj', '10', '2014-11-26 10:56:26', '1');
INSERT INTO `abc_score` VALUES ('2', '还款结清', 'hkjq', '15', '2014-11-26 10:56:44', '1');
INSERT INTO `abc_score` VALUES ('3', '强制还款', 'qzhk', '5', '2014-11-22 15:59:16', '1');
INSERT INTO `abc_score` VALUES ('4', '系统代还', 'xtdh', '-10', '2014-11-26 10:57:40', '1');
INSERT INTO `abc_score` VALUES ('5', '正常还款', 'zchk', '10', '2014-11-26 10:59:50', '1');
INSERT INTO `abc_score` VALUES ('6', '债权收购', 'zqsg', '5', '2014-11-26 10:59:53', '1');
INSERT INTO `abc_score` VALUES ('7', '债权转让', 'zqzr', '-5', '2014-11-26 10:59:55', '1');
INSERT INTO `abc_score` VALUES ('8', '项目投资', 'xmtz', '10', '2014-11-26 10:59:56', '1');
INSERT INTO `abc_score` VALUES ('9', '银行绑卡', 'yhbk', '5', '2014-11-26 10:59:57', '1');
INSERT INTO `abc_score` VALUES ('10', '账户开户', 'zhkh', '5', '2014-11-26 10:59:59', '1');

-- ----------------------------
-- Table structure for `abc_score_config`
-- ----------------------------
DROP TABLE IF EXISTS `abc_score_config`;
CREATE TABLE `abc_score_config` (
  `sc_id` int(11) NOT NULL AUTO_INCREMENT,
  `sc_max_score` int(11) DEFAULT NULL COMMENT '兑换积分',
  `sc_score_pic` varchar(512) COLLATE utf8_bin DEFAULT NULL COMMENT '积分兑换图片',
  `sc_score_cash` decimal(10,0) DEFAULT NULL COMMENT '积分兑换金额',
  PRIMARY KEY (`sc_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin ROW_FORMAT=COMPACT COMMENT='积分兑换投资券设置表';

-- ----------------------------
-- Records of abc_score_config
-- ----------------------------

-- ----------------------------
-- Table structure for `abc_score_history`
-- ----------------------------
DROP TABLE IF EXISTS `abc_score_history`;
CREATE TABLE `abc_score_history` (
  `sh_id` int(11) NOT NULL AUTO_INCREMENT,
  `sh_user_id` int(11) DEFAULT NULL COMMENT '用户ID',
  `sh_score_id` int(11) DEFAULT NULL COMMENT '积分ID',
  `sh_createtime` datetime DEFAULT NULL COMMENT '添加时间',
  `sh_note` varchar(512) COLLATE utf8_bin DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`sh_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin ROW_FORMAT=COMPACT COMMENT='积分记录表';

-- ----------------------------
-- Records of abc_score_history
-- ----------------------------

-- ----------------------------
-- Table structure for `abc_score_usage_record`
-- ----------------------------
DROP TABLE IF EXISTS `abc_score_usage_record`;
CREATE TABLE `abc_score_usage_record` (
  `sur_id` int(11) NOT NULL AUTO_INCREMENT,
  `sur_user_id` int(11) DEFAULT NULL COMMENT '用户ID 关联user表',
  `sur_exchange_score` int(11) DEFAULT NULL COMMENT '兑换积分',
  `sur_exchange_cash` decimal(10,0) DEFAULT NULL COMMENT '兑换金额',
  `sur_exchange_date` datetime DEFAULT NULL COMMENT '兑现日期',
  `sur_start_date` datetime DEFAULT NULL COMMENT '有效开始日期',
  `sur_end_date` datetime DEFAULT NULL COMMENT '有效结束日期',
  `sur_use_money` decimal(10,0) DEFAULT NULL COMMENT '划转金额 即已使用金额',
  `sur_review_state` tinyint(1) DEFAULT NULL COMMENT '审核状态 0：待审核 1：审核已通过 2：审核未通过',
  `sur_score_state` tinyint(1) DEFAULT NULL COMMENT '投资券状态 0：未使用 1：已使用 2：已过期',
  PRIMARY KEY (`sur_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin ROW_FORMAT=COMPACT COMMENT='积分兑换投资券记录表';

-- ----------------------------
-- Records of abc_score_usage_record
-- ----------------------------

-- ----------------------------
-- Table structure for `abc_sys_config`
-- ----------------------------
DROP TABLE IF EXISTS `abc_sys_config`;
CREATE TABLE `abc_sys_config` (
  `conf_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `conf_key` varchar(128) NOT NULL DEFAULT '' COMMENT '键名，对应SysConfig枚举',
  `conf_value` text COMMENT '值',
  `conf_desc` text COMMENT '描述',
  `conf_create_time` datetime NOT NULL COMMENT '创建时间',
  `conf_modify_time` datetime NOT NULL COMMENT '修改时间',
  `conf_name` varchar(128) DEFAULT NULL COMMENT '参数名称',
  PRIMARY KEY (`conf_id`)
) ENGINE=InnoDB AUTO_INCREMENT=148 DEFAULT CHARSET=utf8 COMMENT='系统配置表';

-- ----------------------------
-- Records of abc_sys_config
-- ----------------------------
INSERT INTO `abc_sys_config` VALUES ('11', 'EMPLOYEE_DEFAULT_PASSWORD', '123456', null, '2014-12-11 16:21:38', '2014-12-11 16:21:41', null);
INSERT INTO `abc_sys_config` VALUES ('12', 'SMS_USER', '3213', '短信用户名', '2014-12-11 16:24:35', '2014-12-11 16:24:35', null);
INSERT INTO `abc_sys_config` VALUES ('42', 'MAIL_SMTP_SERVER', '12', 'SMTP服务器', '2014-12-11 20:22:07', '2014-12-11 20:22:07', null);
INSERT INTO `abc_sys_config` VALUES ('43', 'MAIL_ADDRESS', 'tiuwer@qq.com', '邮箱地址', '2014-12-11 20:22:07', '2014-12-11 20:22:07', null);
INSERT INTO `abc_sys_config` VALUES ('44', 'MAIL_PASSWORD', '212', '邮箱密码', '2014-12-11 20:22:07', '2014-12-11 20:22:07', null);
INSERT INTO `abc_sys_config` VALUES ('45', 'MAIL_SENDER_NAME', '21', '发件人昵称或姓名', '2014-12-11 20:22:07', '2014-12-11 20:22:07', null);
INSERT INTO `abc_sys_config` VALUES ('46', 'SMS_USER', '212', '短信用户名', '2014-12-11 20:22:27', '2014-12-11 20:22:27', null);
INSERT INTO `abc_sys_config` VALUES ('47', 'SMS_PASSWORD', '212', '短信密码', '2014-12-11 20:22:27', '2014-12-11 20:22:27', null);
INSERT INTO `abc_sys_config` VALUES ('48', 'SHUTDOWN_SITE', '1', '关闭站点', '2014-12-12 11:24:00', '2014-12-12 11:24:00', null);
INSERT INTO `abc_sys_config` VALUES ('49', 'SHUTDOWN_INFO', '1', '网站关闭信息', '2014-12-12 11:24:00', '2014-12-12 11:24:00', null);
INSERT INTO `abc_sys_config` VALUES ('50', 'MIN_CREDIT_LIMIT', '2', '起始最低信用额度', '2014-12-12 11:24:00', '2014-12-12 11:24:00', null);
INSERT INTO `abc_sys_config` VALUES ('51', 'PUNISH_INTEREST_RATE', '3', '罚息利率', '2014-12-12 11:24:00', '2014-12-12 11:24:00', null);
INSERT INTO `abc_sys_config` VALUES ('52', 'CONTINUE_DESIGNATED_PAY', '1', '上期代还未收回是否继续代还', '2014-12-12 11:24:00', '2014-12-12 11:24:00', null);
INSERT INTO `abc_sys_config` VALUES ('53', 'LOAN_TRANSFER_LIMIT', '', '债权转让次数', '2014-12-12 11:24:00', '2014-12-12 11:24:00', null);
INSERT INTO `abc_sys_config` VALUES ('54', 'LOAN_TRANSFER_DISCONT_MIN', '', '最小债权转让折扣', '2014-12-12 11:24:00', '2014-12-12 11:24:00', null);
INSERT INTO `abc_sys_config` VALUES ('55', 'LOAN_TRANSFER_DISCONT_MAX', '5', '最大债权转让折扣', '2014-12-12 11:24:00', '2014-12-12 11:24:00', null);
INSERT INTO `abc_sys_config` VALUES ('56', 'LOAN_TRANSFER_FEE_RATE', '6', '转让手续费率', '2014-12-12 11:24:00', '2014-12-12 11:24:00', null);
INSERT INTO `abc_sys_config` VALUES ('57', 'LOAN_TRANSFER_PERIOD', '7', '首次债权转让距放款周期', '2014-12-12 11:24:00', '2014-12-12 11:24:00', null);
INSERT INTO `abc_sys_config` VALUES ('58', 'LOAN_TRANSFER_PERIOD_TYPE', '1', '首次债权转让距放款周期类型', '2014-12-12 11:24:00', '2014-12-12 11:24:00', null);
INSERT INTO `abc_sys_config` VALUES ('59', 'LOAN_LIMIT_PER_DAY', '8', '个体每日融资项目数', '2014-12-12 11:24:00', '2014-12-12 11:24:00', null);
INSERT INTO `abc_sys_config` VALUES ('60', 'SHUTDOWN_SITE', '1', '关闭站点', '2014-12-12 11:37:55', '2014-12-12 11:37:55', null);
INSERT INTO `abc_sys_config` VALUES ('61', 'SHUTDOWN_INFO', '1', '网站关闭信息', '2014-12-12 11:37:55', '2014-12-12 11:37:55', null);
INSERT INTO `abc_sys_config` VALUES ('62', 'MIN_CREDIT_LIMIT', '2', '起始最低信用额度', '2014-12-12 11:37:55', '2014-12-12 11:37:55', null);
INSERT INTO `abc_sys_config` VALUES ('63', 'PUNISH_INTEREST_RATE', '3', '罚息利率', '2014-12-12 11:37:55', '2014-12-12 11:37:55', null);
INSERT INTO `abc_sys_config` VALUES ('64', 'CONTINUE_DESIGNATED_PAY', '1', '上期代还未收回是否继续代还', '2014-12-12 11:37:55', '2014-12-12 11:37:55', null);
INSERT INTO `abc_sys_config` VALUES ('65', 'LOAN_TRANSFER_LIMIT', '', '债权转让次数', '2014-12-12 11:37:55', '2014-12-12 11:37:55', null);
INSERT INTO `abc_sys_config` VALUES ('66', 'LOAN_TRANSFER_DISCONT_MIN', '4', '最小债权转让折扣', '2014-12-12 11:37:55', '2014-12-12 11:37:55', null);
INSERT INTO `abc_sys_config` VALUES ('67', 'LOAN_TRANSFER_DISCONT_MAX', '5', '最大债权转让折扣', '2014-12-12 11:37:55', '2014-12-12 11:37:55', null);
INSERT INTO `abc_sys_config` VALUES ('68', 'LOAN_TRANSFER_FEE_RATE', '6', '转让手续费率', '2014-12-12 11:37:55', '2014-12-12 11:37:55', null);
INSERT INTO `abc_sys_config` VALUES ('69', 'LOAN_TRANSFER_PERIOD', '7', '首次债权转让距放款周期', '2014-12-12 11:37:55', '2014-12-12 11:37:55', null);
INSERT INTO `abc_sys_config` VALUES ('70', 'LOAN_TRANSFER_PERIOD_TYPE', '1', '首次债权转让距放款周期类型', '2014-12-12 11:37:55', '2014-12-12 11:37:55', null);
INSERT INTO `abc_sys_config` VALUES ('71', 'LOAN_LIMIT_PER_DAY', '8', '个体每日融资项目数', '2014-12-12 11:37:55', '2014-12-12 11:37:55', null);
INSERT INTO `abc_sys_config` VALUES ('72', 'SHUTDOWN_SITE', '1', '关闭站点', '2014-12-12 18:12:52', '2014-12-12 18:12:52', null);
INSERT INTO `abc_sys_config` VALUES ('73', 'SHUTDOWN_INFO', '1', '网站关闭信息', '2014-12-12 18:12:52', '2014-12-12 18:12:52', null);
INSERT INTO `abc_sys_config` VALUES ('74', 'MIN_CREDIT_LIMIT', '2', '起始最低信用额度', '2014-12-12 18:12:52', '2014-12-12 18:12:52', null);
INSERT INTO `abc_sys_config` VALUES ('75', 'PUNISH_INTEREST_RATE', '3', '罚息利率', '2014-12-12 18:12:52', '2014-12-12 18:12:52', null);
INSERT INTO `abc_sys_config` VALUES ('76', 'CONTINUE_DESIGNATED_PAY', '1', '上期代还未收回是否继续代还', '2014-12-12 18:12:52', '2014-12-12 18:12:52', null);
INSERT INTO `abc_sys_config` VALUES ('77', 'LOAN_TRANSFER_LIMIT', '', '债权转让次数', '2014-12-12 18:12:52', '2014-12-12 18:12:52', null);
INSERT INTO `abc_sys_config` VALUES ('78', 'LOAN_TRANSFER_DISCONT_MIN', '4', '最小债权转让折扣', '2014-12-12 18:12:52', '2014-12-12 18:12:52', null);
INSERT INTO `abc_sys_config` VALUES ('79', 'LOAN_TRANSFER_DISCONT_MAX', '5', '最大债权转让折扣', '2014-12-12 18:12:52', '2014-12-12 18:12:52', null);
INSERT INTO `abc_sys_config` VALUES ('80', 'LOAN_TRANSFER_FEE_RATE', '6', '转让手续费率', '2014-12-12 18:12:52', '2014-12-12 18:12:52', null);
INSERT INTO `abc_sys_config` VALUES ('81', 'LOAN_TRANSFER_PERIOD', '7', '首次债权转让距放款周期', '2014-12-12 18:12:52', '2014-12-12 18:12:52', null);
INSERT INTO `abc_sys_config` VALUES ('82', 'LOAN_TRANSFER_PERIOD_TYPE', '1', '首次债权转让距放款周期类型', '2014-12-12 18:12:52', '2014-12-12 18:12:52', null);
INSERT INTO `abc_sys_config` VALUES ('83', 'LOAN_LIMIT_PER_DAY', '8', '个体每日融资项目数', '2014-12-12 18:12:52', '2014-12-12 18:12:52', null);
INSERT INTO `abc_sys_config` VALUES ('84', 'SHUTDOWN_SITE', '1', '关闭站点', '2014-12-12 18:13:09', '2014-12-12 18:13:09', null);
INSERT INTO `abc_sys_config` VALUES ('85', 'SHUTDOWN_INFO', '1', '网站关闭信息', '2014-12-12 18:13:09', '2014-12-12 18:13:09', null);
INSERT INTO `abc_sys_config` VALUES ('86', 'MIN_CREDIT_LIMIT', '2', '起始最低信用额度', '2014-12-12 18:13:09', '2014-12-12 18:13:09', null);
INSERT INTO `abc_sys_config` VALUES ('87', 'PUNISH_INTEREST_RATE', '3', '罚息利率', '2014-12-12 18:13:09', '2014-12-12 18:13:09', null);
INSERT INTO `abc_sys_config` VALUES ('88', 'CONTINUE_DESIGNATED_PAY', '1', '上期代还未收回是否继续代还', '2014-12-12 18:13:09', '2014-12-12 18:13:09', null);
INSERT INTO `abc_sys_config` VALUES ('89', 'LOAN_TRANSFER_LIMIT', '', '债权转让次数', '2014-12-12 18:13:09', '2014-12-12 18:13:09', null);
INSERT INTO `abc_sys_config` VALUES ('90', 'LOAN_TRANSFER_DISCONT_MIN', '4', '最小债权转让折扣', '2014-12-12 18:13:09', '2014-12-12 18:13:09', null);
INSERT INTO `abc_sys_config` VALUES ('91', 'LOAN_TRANSFER_DISCONT_MAX', '5', '最大债权转让折扣', '2014-12-12 18:13:09', '2014-12-12 18:13:09', null);
INSERT INTO `abc_sys_config` VALUES ('92', 'LOAN_TRANSFER_FEE_RATE', '6', '转让手续费率', '2014-12-12 18:13:09', '2014-12-12 18:13:09', null);
INSERT INTO `abc_sys_config` VALUES ('93', 'LOAN_TRANSFER_PERIOD', '7', '首次债权转让距放款周期', '2014-12-12 18:13:09', '2014-12-12 18:13:09', null);
INSERT INTO `abc_sys_config` VALUES ('94', 'LOAN_TRANSFER_PERIOD_TYPE', '1', '首次债权转让距放款周期类型', '2014-12-12 18:13:09', '2014-12-12 18:13:09', null);
INSERT INTO `abc_sys_config` VALUES ('95', 'LOAN_LIMIT_PER_DAY', '8', '个体每日融资项目数', '2014-12-12 18:13:09', '2014-12-12 18:13:09', null);
INSERT INTO `abc_sys_config` VALUES ('96', 'SHUTDOWN_SITE', '1', '关闭站点', '2014-12-12 18:26:50', '2014-12-12 18:26:50', null);
INSERT INTO `abc_sys_config` VALUES ('97', 'SHUTDOWN_INFO', '1', '网站关闭信息', '2014-12-12 18:26:50', '2014-12-12 18:26:50', null);
INSERT INTO `abc_sys_config` VALUES ('98', 'MIN_CREDIT_LIMIT', '2', '起始最低信用额度', '2014-12-12 18:26:50', '2014-12-12 18:26:50', null);
INSERT INTO `abc_sys_config` VALUES ('99', 'PUNISH_INTEREST_RATE', '3', '罚息利率', '2014-12-12 18:26:50', '2014-12-12 18:26:50', null);
INSERT INTO `abc_sys_config` VALUES ('100', 'CONTINUE_DESIGNATED_PAY', '1', '上期代还未收回是否继续代还', '2014-12-12 18:26:50', '2014-12-12 18:26:50', null);
INSERT INTO `abc_sys_config` VALUES ('101', 'LOAN_TRANSFER_LIMIT', '22', '债权转让次数', '2014-12-12 18:26:50', '2014-12-12 18:26:50', null);
INSERT INTO `abc_sys_config` VALUES ('102', 'LOAN_TRANSFER_DISCONT_MIN', '4', '最小债权转让折扣', '2014-12-12 18:26:50', '2014-12-12 18:26:50', null);
INSERT INTO `abc_sys_config` VALUES ('103', 'LOAN_TRANSFER_DISCONT_MAX', '5', '最大债权转让折扣', '2014-12-12 18:26:50', '2014-12-12 18:26:50', null);
INSERT INTO `abc_sys_config` VALUES ('104', 'LOAN_TRANSFER_FEE_RATE', '6', '转让手续费率', '2014-12-12 18:26:50', '2014-12-12 18:26:50', null);
INSERT INTO `abc_sys_config` VALUES ('105', 'LOAN_TRANSFER_PERIOD', '7', '首次债权转让距放款周期', '2014-12-12 18:26:50', '2014-12-12 18:26:50', null);
INSERT INTO `abc_sys_config` VALUES ('106', 'LOAN_TRANSFER_PERIOD_TYPE', '2', '首次债权转让距放款周期类型', '2014-12-12 18:26:50', '2014-12-12 18:26:50', null);
INSERT INTO `abc_sys_config` VALUES ('107', 'LOAN_LIMIT_PER_DAY', '8', '个体每日融资项目数', '2014-12-12 18:26:50', '2014-12-12 18:26:50', null);
INSERT INTO `abc_sys_config` VALUES ('108', 'SHUTDOWN_SITE', '1', '关闭站点', '2014-12-12 18:47:45', '2014-12-12 18:47:45', null);
INSERT INTO `abc_sys_config` VALUES ('109', 'SHUTDOWN_INFO', '1', '网站关闭信息', '2014-12-12 18:47:45', '2014-12-12 18:47:45', null);
INSERT INTO `abc_sys_config` VALUES ('110', 'MIN_CREDIT_LIMIT', '2', '起始最低信用额度', '2014-12-12 18:47:45', '2014-12-12 18:47:45', null);
INSERT INTO `abc_sys_config` VALUES ('111', 'PUNISH_INTEREST_RATE', '3', '罚息利率', '2014-12-12 18:47:45', '2014-12-12 18:47:45', null);
INSERT INTO `abc_sys_config` VALUES ('112', 'CONTINUE_DESIGNATED_PAY', '1', '上期代还未收回是否继续代还', '2014-12-12 18:47:45', '2014-12-12 18:47:45', null);
INSERT INTO `abc_sys_config` VALUES ('113', 'LOAN_TRANSFER_LIMIT', '', '债权转让次数', '2014-12-12 18:47:45', '2014-12-12 18:47:45', null);
INSERT INTO `abc_sys_config` VALUES ('114', 'LOAN_TRANSFER_DISCONT_MIN', '4', '最小债权转让折扣', '2014-12-12 18:47:45', '2014-12-12 18:47:45', null);
INSERT INTO `abc_sys_config` VALUES ('115', 'LOAN_TRANSFER_DISCONT_MAX', '5', '最大债权转让折扣', '2014-12-12 18:47:45', '2014-12-12 18:47:45', null);
INSERT INTO `abc_sys_config` VALUES ('116', 'LOAN_TRANSFER_FEE_RATE', '6', '转让手续费率', '2014-12-12 18:47:45', '2014-12-12 18:47:45', null);
INSERT INTO `abc_sys_config` VALUES ('117', 'LOAN_TRANSFER_PERIOD', '7', '首次债权转让距放款周期', '2014-12-12 18:47:45', '2014-12-12 18:47:45', null);
INSERT INTO `abc_sys_config` VALUES ('118', 'LOAN_TRANSFER_PERIOD_TYPE', '2', '首次债权转让距放款周期类型', '2014-12-12 18:47:45', '2014-12-12 18:47:45', null);
INSERT INTO `abc_sys_config` VALUES ('119', 'LOAN_LIMIT_PER_DAY', '8', '个体每日融资项目数', '2014-12-12 18:47:45', '2014-12-12 18:47:45', null);
INSERT INTO `abc_sys_config` VALUES ('120', 'SHUTDOWN_SITE', '1', '关闭站点', '2014-12-12 18:48:37', '2014-12-12 18:48:37', null);
INSERT INTO `abc_sys_config` VALUES ('121', 'SHUTDOWN_INFO', '1', '网站关闭信息', '2014-12-12 18:48:37', '2014-12-12 18:48:37', null);
INSERT INTO `abc_sys_config` VALUES ('122', 'MIN_CREDIT_LIMIT', '2', '起始最低信用额度', '2014-12-12 18:48:37', '2014-12-12 18:48:37', null);
INSERT INTO `abc_sys_config` VALUES ('123', 'PUNISH_INTEREST_RATE', '3', '罚息利率', '2014-12-12 18:48:37', '2014-12-12 18:48:37', null);
INSERT INTO `abc_sys_config` VALUES ('124', 'CONTINUE_DESIGNATED_PAY', '1', '上期代还未收回是否继续代还', '2014-12-12 18:48:37', '2014-12-12 18:48:37', null);
INSERT INTO `abc_sys_config` VALUES ('125', 'LOAN_TRANSFER_LIMIT', '2', '债权转让次数', '2014-12-12 18:48:37', '2014-12-12 18:48:37', null);
INSERT INTO `abc_sys_config` VALUES ('126', 'LOAN_TRANSFER_DISCONT_MIN', '4', '最小债权转让折扣', '2014-12-12 18:48:37', '2014-12-12 18:48:37', null);
INSERT INTO `abc_sys_config` VALUES ('127', 'LOAN_TRANSFER_DISCONT_MAX', '5', '最大债权转让折扣', '2014-12-12 18:48:37', '2014-12-12 18:48:37', null);
INSERT INTO `abc_sys_config` VALUES ('128', 'LOAN_TRANSFER_FEE_RATE', '6', '转让手续费率', '2014-12-12 18:48:37', '2014-12-12 18:48:37', null);
INSERT INTO `abc_sys_config` VALUES ('129', 'LOAN_TRANSFER_PERIOD', '7', '首次债权转让距放款周期', '2014-12-12 18:48:37', '2014-12-12 18:48:37', null);
INSERT INTO `abc_sys_config` VALUES ('130', 'LOAN_TRANSFER_PERIOD_TYPE', '2', '首次债权转让距放款周期类型', '2014-12-12 18:48:37', '2014-12-12 18:48:37', null);
INSERT INTO `abc_sys_config` VALUES ('131', 'LOAN_LIMIT_PER_DAY', '8', '个体每日融资项目数', '2014-12-12 18:48:37', '2014-12-12 18:48:37', null);
INSERT INTO `abc_sys_config` VALUES ('132', 'SHUTDOWN_SITE', '1', '关闭站点', '2014-12-12 18:49:30', '2014-12-12 18:49:30', null);
INSERT INTO `abc_sys_config` VALUES ('133', 'SHUTDOWN_INFO', '1', '网站关闭信息', '2014-12-12 18:49:30', '2014-12-12 18:49:30', null);
INSERT INTO `abc_sys_config` VALUES ('134', 'MIN_CREDIT_LIMIT', '2', '起始最低信用额度', '2014-12-12 18:49:30', '2014-12-12 18:49:30', null);
INSERT INTO `abc_sys_config` VALUES ('135', 'PUNISH_INTEREST_RATE', '3', '罚息利率', '2014-12-12 18:49:30', '2014-12-12 18:49:30', null);
INSERT INTO `abc_sys_config` VALUES ('136', 'CONTINUE_DESIGNATED_PAY', '1', '上期代还未收回是否继续代还', '2014-12-12 18:49:30', '2014-12-12 18:49:30', null);
INSERT INTO `abc_sys_config` VALUES ('137', 'LOAN_TRANSFER_LIMIT', '', '债权转让次数', '2014-12-12 18:49:30', '2014-12-12 18:49:30', null);
INSERT INTO `abc_sys_config` VALUES ('138', 'LOAN_TRANSFER_DISCONT_MIN', '4', '最小债权转让折扣', '2014-12-12 18:49:30', '2014-12-12 18:49:30', null);
INSERT INTO `abc_sys_config` VALUES ('139', 'LOAN_TRANSFER_DISCONT_MAX', '5', '最大债权转让折扣', '2014-12-12 18:49:30', '2014-12-12 18:49:30', null);
INSERT INTO `abc_sys_config` VALUES ('140', 'LOAN_TRANSFER_FEE_RATE', '6', '转让手续费率', '2014-12-12 18:49:30', '2014-12-12 18:49:30', null);
INSERT INTO `abc_sys_config` VALUES ('141', 'LOAN_TRANSFER_PERIOD', '7', '首次债权转让距放款周期', '2014-12-12 18:49:30', '2014-12-12 18:49:30', null);
INSERT INTO `abc_sys_config` VALUES ('142', 'LOAN_TRANSFER_PERIOD_TYPE', '2', '首次债权转让距放款周期类型', '2014-12-12 18:49:30', '2014-12-12 18:49:30', null);
INSERT INTO `abc_sys_config` VALUES ('143', 'LOAN_LIMIT_PER_DAY', '8', '个体每日融资项目数', '2014-12-12 18:49:30', '2014-12-12 18:49:30', null);
INSERT INTO `abc_sys_config` VALUES ('144', 'SCORE_USAGE_VALID_PERIOD', '3', '单位:月', '2014-12-27 14:21:43', '2014-12-27 14:22:12', null);
INSERT INTO `abc_sys_config` VALUES ('145', 'PLATFORM_ACCOUNT', '15209855822', '平台账户', '2014-12-27 14:21:43', '2014-12-27 14:21:43', null);
INSERT INTO `abc_sys_config` VALUES ('146', 'NUMBER_RULE', '皖yyyyMMdd字第%04d号', '项目编号设置', '2014-12-27 14:21:43', '2015-01-11 22:43:51', null);
INSERT INTO `abc_sys_config` VALUES ('147', 'SITE_AUTO_PREFIX_das', 'dasdzz', 'xa', '2015-01-05 09:35:21', '2015-01-05 09:35:29', 'dsa');

-- ----------------------------
-- Table structure for `abc_sys_link_info`
-- ----------------------------
DROP TABLE IF EXISTS `abc_sys_link_info`;
CREATE TABLE `abc_sys_link_info` (
  `sl_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `sl_title` varchar(50) DEFAULT NULL COMMENT '链接标题',
  `sl_logo` varchar(50) DEFAULT NULL COMMENT '链接logo',
  `sl_address` varchar(50) DEFAULT NULL COMMENT '链接url',
  `sl_mark` varchar(512) DEFAULT NULL COMMENT '链接描述',
  `sl_width` decimal(18,2) unsigned zerofill DEFAULT NULL COMMENT '链接宽度',
  `sl_height` decimal(18,2) unsigned zerofill DEFAULT NULL COMMENT '链接高度',
  PRIMARY KEY (`sl_id`)
) ENGINE=MyISAM AUTO_INCREMENT=30 DEFAULT CHARSET=utf8 COMMENT='友情链接表';

-- ----------------------------
-- Records of abc_sys_link_info
-- ----------------------------

-- ----------------------------
-- Table structure for `abc_tocash_record`
-- ----------------------------
DROP TABLE IF EXISTS `abc_tocash_record`;
CREATE TABLE `abc_tocash_record` (
  `tocash_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '提现id',
  `tocash_user_id` int(11) DEFAULT NULL COMMENT '提现用户id',
  `tocash_account_id` varchar(18) COLLATE utf8_bin DEFAULT NULL COMMENT '提现账户No',
  `tocash_amount` decimal(18,2) DEFAULT NULL COMMENT '提现金额',
  `tocash_seq_no` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '提现交易流水号',
  `tocash_state` int(1) DEFAULT NULL COMMENT '提现状态：0提现中，1提现成功，2提现失败',
  `tocash_date` datetime DEFAULT NULL COMMENT '提现时间',
  PRIMARY KEY (`tocash_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='提现记录表';

-- ----------------------------
-- Records of abc_tocash_record
-- ----------------------------

-- ----------------------------
-- Table structure for `abc_transfer_loan`
-- ----------------------------
DROP TABLE IF EXISTS `abc_transfer_loan`;
CREATE TABLE `abc_transfer_loan` (
  `tl_id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `tl_origin_id` int(11) NOT NULL COMMENT '原始贷款id',
  `tl_invest_id` int(11) NOT NULL COMMENT '投资id',
  `tl_user_id` int(11) NOT NULL COMMENT '转让人',
  `tl_transfer_total` decimal(18,2) NOT NULL COMMENT '转让债权总额',
  `tl_transfer_money` decimal(18,2) NOT NULL COMMENT '转让金额',
  `tl_transfer_rate` decimal(18,2) DEFAULT NULL COMMENT '转让后利率',
  `tl_transfer_fee` decimal(18,2) DEFAULT NULL COMMENT '转让手续费',
  `tl_transfer_discount_rate` decimal(18,2) DEFAULT NULL COMMENT '转让折让率',
  `tl_transfer_discount_fee` decimal(18,2) DEFAULT NULL COMMENT '转让折让费',
  `tl_transfer_period` int(11) NOT NULL COMMENT '转让期数 比如：12期的收回计划表，还了3期，第4期转让，那么转让期数=12-3 = 9（期）',
  `tl_state` tinyint(1) NOT NULL COMMENT '转让状态 0：待审核 1：初审已通过 2：初审未通过 3：转让中 4：满标待审 5：满标审核通过 6：满标审核未通过 7：已流标 8：划转中 9：已划转',
  `tl_createtime` datetime NOT NULL COMMENT '转让申请日期 1、当转让人部分转让时，存在剩余金额，且同一笔贷款时，若第一笔债权未满标时，不允许转让第二次；\\n2、当未放款前，借款人还钱了，那么该转让申请作废，投资人钱重新进行返还，借款人把钱还给原债权人',
  `tl_modifytime` datetime NOT NULL COMMENT '修改日期',
  `tl_invest_starttime` datetime DEFAULT NULL COMMENT '投资开始时间',
  `tl_invest_endtime` datetime DEFAULT NULL COMMENT '投资结束时间',
  `tl_fulltime` datetime DEFAULT NULL COMMENT '满标日期',
  `tl_full_transferedtime` datetime DEFAULT NULL COMMENT '放款成功日期',
  `tl_current_invest` decimal(18,2) NOT NULL DEFAULT '0.00' COMMENT '当前投标总额',
  `tl_current_valid_invest` decimal(18,2) NOT NULL DEFAULT '0.00' COMMENT '当前有效投标总额',
  `tl_next_payment_id` int(11) NOT NULL COMMENT '下一次还款计划id',
  PRIMARY KEY (`tl_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='债权转让表';

-- ----------------------------
-- Records of abc_transfer_loan
-- ----------------------------

-- ----------------------------
-- Table structure for `abc_transfer_schedule`
-- ----------------------------
DROP TABLE IF EXISTS `abc_transfer_schedule`;
CREATE TABLE `abc_transfer_schedule` (
  `ts_id` int(11) NOT NULL AUTO_INCREMENT,
  `ts_name` int(11) DEFAULT NULL,
  `ts_operator_id` int(11) DEFAULT NULL,
  `ts_operate_time` datetime DEFAULT NULL,
  `ts_state` int(11) DEFAULT NULL,
  `ts_loan_id` int(11) DEFAULT NULL,
  `ts_transfer_loan_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`ts_id`)
) ENGINE=MyISAM AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of abc_transfer_schedule
-- ----------------------------

-- ----------------------------
-- Table structure for `abc_user`
-- ----------------------------
DROP TABLE IF EXISTS `abc_user`;
CREATE TABLE `abc_user` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_name` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '用户名',
  `user_real_name` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '真实姓名',
  `user_pwd` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '登录密码',
  `user_deal_pwd` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '交易密码',
  `user_type` tinyint(1) DEFAULT NULL COMMENT '用户类型	1：个人 2：企业',
  `user_doc_type` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '证件类型',
  `user_doc_no` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '证件号码',
  `user_sex` tinyint(1) DEFAULT NULL COMMENT '性别	1:男  0:女',
  `user_birthday` datetime DEFAULT NULL COMMENT '生日',
  `user_nation` varchar(15) COLLATE utf8_bin DEFAULT NULL COMMENT '民族',
  `user_native` varchar(25) COLLATE utf8_bin DEFAULT NULL COMMENT '籍贯',
  `user_phone` varchar(15) COLLATE utf8_bin DEFAULT NULL COMMENT '手机号码',
  `user_email` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '电子邮箱',
  `user_marry` tinyint(1) DEFAULT NULL COMMENT '婚姻状况	1:已婚 2:未婚 3:离婚 4:丧偶',
  `user_month_income` decimal(18,2) DEFAULT NULL COMMENT '月收入',
  `user_login_count` int(11) DEFAULT NULL COMMENT '登录次数',
  `user_head_img` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '网站头像',
  `user_score` int(10) DEFAULT '0' COMMENT '用户积分',
  `user_score_lastmodifytime` datetime DEFAULT NULL COMMENT '积分最后修改时间',
  `user_state` tinyint(1) DEFAULT '1' COMMENT '是否启用	1：是 0：否 -1 : 已删除',
  `user_auto_invest` tinyint(1) DEFAULT NULL COMMENT '是否开启自动投标计划	1：开启 0：未开启',
  `user_recommend_userid` int(11) DEFAULT NULL COMMENT '推荐人id',
  `user_loan_credit` decimal(18,2) DEFAULT NULL COMMENT '信用额度',
  `user_credit_sett` decimal(18,2) DEFAULT NULL COMMENT '可用信用额度',
  `user_register_date` datetime DEFAULT NULL COMMENT '注册日期',
  `user_realname_isproven` tinyint(1) DEFAULT '0' COMMENT '是否实名认证 	1：是 0：否',
  `user_mobile_isbinded` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否绑定手机号	1：是 0：否',
  `user_email_isbinded` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否绑定邮箱 	1：是 0：否',
  `user_bankcard_isbinded` tinyint(1) DEFAULT '0' COMMENT '是否绑定银行卡 	1：是 0：否',
  `user_email_verify_code` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '邮箱激活时验证码',
  `user_email_verify_date` datetime DEFAULT NULL COMMENT '邮箱激活时时间',
  `user_mobile_verify_date` datetime DEFAULT NULL COMMENT '手机认证日期',
  `user_isonline` tinyint(1) DEFAULT NULL COMMENT '1：已在线  0：未在线',
  `user_business_state` tinyint(1) DEFAULT NULL COMMENT '业务相关状态 1：注册成功 2：账户已开户 3：已充值 4：已投资',
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='用户（网友）信息表';

-- ----------------------------
-- Records of abc_user
-- ----------------------------

-- ----------------------------
-- Table structure for `abc_user_account`
-- ----------------------------
DROP TABLE IF EXISTS `abc_user_account`;
CREATE TABLE `abc_user_account` (
  `ua_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `ua_total_money` decimal(18,2) DEFAULT NULL COMMENT '资金总额',
  `ua_useable_money` decimal(18,2) DEFAULT NULL COMMENT '可用余额',
  `ua_frozen` decimal(18,2) DEFAULT NULL COMMENT '冻结金额',
  `ua_account_no` varchar(18) COLLATE utf8_bin DEFAULT NULL COMMENT '用户账户id',
  `ua_operate_date` datetime DEFAULT NULL COMMENT '操作日期',
  PRIMARY KEY (`ua_id`),
  KEY `accountNoIndex` (`ua_account_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of abc_user_account
-- ----------------------------

-- ----------------------------
-- Table structure for `abc_user_company`
-- ----------------------------
DROP TABLE IF EXISTS `abc_user_company`;
CREATE TABLE `abc_user_company` (
  `uc_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `uc_userid` int(11) DEFAULT NULL COMMENT '用户ID',
  `uc_company_name` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '单位名称',
  `uc_company_type` tinyint(1) DEFAULT NULL COMMENT '单位性质	1：政府机关 2：国有企业 3：台（港、澳）资企业 4：合资企业 5：个体户 6：事业性单位 7：私营企业',
  `uc_company_industry` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '单位行业	"1：农、林、牧、渔业\n2：制造业\n3：电力、燃气及水的生产和供应业\n4：建筑业\n5：交通运输、仓储和邮政业\n6：信息传输、计算机服务和软件业\n7：批发和零售业\n8：金融业\n9：房地产业\n10：采矿业\n11：租赁和商务服务业\n12：科学研究、技术服务和地质勘查业\n13：水利、环境和公共设施管理业\n14：居民服务和其他服务业\n15：教育\n16：卫生、社会保障和社会福利业\n17：文化、体育和娱乐业\n18：公共管理和社会组织\n19：国际组织"',
  `uc_work_year` tinyint(1) DEFAULT NULL COMMENT '工作年限	1：一年以内 2：一年以上 3：二年以上 4：三年以上 5：四年以上 6：五年以上 7 六年以上',
  `uc_position` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '职位',
  `uc_level` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '工作级别',
  `uc_phone` varchar(20) COLLATE utf8_bin DEFAULT NULL COMMENT '工作电话',
  `uc_work_period` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '工作时间',
  `uc_company_site` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '公司网址',
  `uc_company_address` varchar(512) COLLATE utf8_bin DEFAULT NULL COMMENT '公司地址',
  `uc_note` varchar(512) COLLATE utf8_bin DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`uc_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin ROW_FORMAT=COMPACT COMMENT='个人单位资料表';

-- ----------------------------
-- Records of abc_user_company
-- ----------------------------

-- ----------------------------
-- Table structure for `abc_user_contact`
-- ----------------------------
DROP TABLE IF EXISTS `abc_user_contact`;
CREATE TABLE `abc_user_contact` (
  `uc_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `uc_userid` int(11) DEFAULT NULL COMMENT '用户ID',
  `uc_phone` varchar(20) COLLATE utf8_bin DEFAULT NULL COMMENT '手机号码',
  `uc_home_phone` varchar(20) COLLATE utf8_bin DEFAULT NULL COMMENT '居住地电话',
  `uc_city` varchar(25) COLLATE utf8_bin DEFAULT NULL COMMENT '居住所在省市',
  `uc_post` varchar(10) COLLATE utf8_bin DEFAULT NULL COMMENT '居住地邮编',
  `uc_address` varchar(512) COLLATE utf8_bin DEFAULT NULL COMMENT '现居住地址',
  `uc_second_contact_name` varchar(15) COLLATE utf8_bin DEFAULT NULL COMMENT '第二联系人姓名',
  `uc_second_contact_relationship` varchar(15) COLLATE utf8_bin DEFAULT NULL COMMENT '第二联系人关系',
  `uc_second_contact_phone` varchar(15) COLLATE utf8_bin DEFAULT NULL COMMENT '第二联系人电话',
  `uc_third_contact_name` varchar(15) COLLATE utf8_bin DEFAULT NULL COMMENT '第三联系人姓名',
  `uc_third_contact_relationship` varchar(15) COLLATE utf8_bin DEFAULT NULL COMMENT '第三联系人关系',
  `uc_third_contact_phone` varchar(15) COLLATE utf8_bin DEFAULT NULL COMMENT '第三联系人电话',
  `uc_fourth_contact_name` varchar(15) COLLATE utf8_bin DEFAULT NULL COMMENT '第四联系人姓名',
  `uc_fourth_contact_relationship` varchar(15) COLLATE utf8_bin DEFAULT NULL COMMENT '第四联系人关系',
  `uc_fourth_contact_phone` varchar(15) COLLATE utf8_bin DEFAULT NULL COMMENT '第四联系人电话',
  `uc_msn` varchar(20) COLLATE utf8_bin DEFAULT NULL COMMENT 'MSN',
  `uc_qq` varchar(20) COLLATE utf8_bin DEFAULT NULL COMMENT 'QQ',
  PRIMARY KEY (`uc_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='个人联系方式表';

-- ----------------------------
-- Records of abc_user_contact
-- ----------------------------

-- ----------------------------
-- Table structure for `abc_user_education`
-- ----------------------------
DROP TABLE IF EXISTS `abc_user_education`;
CREATE TABLE `abc_user_education` (
  `ue_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `ue_userid` int(11) DEFAULT NULL COMMENT '用户ID',
  `ue_top_education` tinyint(1) DEFAULT NULL COMMENT '最高学历	0：小学 1：初中 2：高中 3：中专 4：专科 5：本科 6：研究生  7： 博士  8：博士后  9：其它',
  `ue_top_school` varchar(25) COLLATE utf8_bin DEFAULT NULL COMMENT '最高学历院校',
  `ue_major` varchar(25) COLLATE utf8_bin DEFAULT NULL COMMENT '专业',
  `ue_start_time` datetime DEFAULT NULL COMMENT '入学时间',
  `ue_end_time` datetime DEFAULT NULL COMMENT '毕业时间',
  PRIMARY KEY (`ue_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin ROW_FORMAT=COMPACT COMMENT='个人教育背景表';

-- ----------------------------
-- Records of abc_user_education
-- ----------------------------

-- ----------------------------
-- Table structure for `abc_user_finance`
-- ----------------------------
DROP TABLE IF EXISTS `abc_user_finance`;
CREATE TABLE `abc_user_finance` (
  `uf_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `uf_userid` int(11) DEFAULT NULL COMMENT '用户ID',
  `uf_unsecured_pay` decimal(18,2) DEFAULT NULL COMMENT '每月无抵押贷款还款额',
  `uf_credit_pay` decimal(18,2) DEFAULT NULL COMMENT '每月信用卡还款金额',
  `uf_house_pay` decimal(18,2) DEFAULT NULL COMMENT '每月房屋按揭金额',
  `uf_car_pay` decimal(18,2) DEFAULT NULL COMMENT '每月汽车按揭金额',
  `uf_own_house` tinyint(1) DEFAULT NULL COMMENT '自有房产	1：是 0：否',
  `uf_own_car` tinyint(1) DEFAULT NULL COMMENT '自有汽车	1：是 0：否',
  PRIMARY KEY (`uf_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='个人财务状况资料表';

-- ----------------------------
-- Records of abc_user_finance
-- ----------------------------

-- ----------------------------
-- Table structure for `abc_user_house`
-- ----------------------------
DROP TABLE IF EXISTS `abc_user_house`;
CREATE TABLE `abc_user_house` (
  `uh_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `uh_user_id` int(11) DEFAULT NULL COMMENT '用户id',
  `uh_address` varchar(512) COLLATE utf8_bin DEFAULT NULL COMMENT '房产地址',
  `uh_area` decimal(18,2) DEFAULT NULL COMMENT '建筑面积',
  `uh_pay_state` char(1) COLLATE utf8_bin DEFAULT NULL COMMENT '供款状况',
  `uh_date` datetime DEFAULT NULL COMMENT '建筑日期',
  `uh_owner_one` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '所有权1',
  `uh_share_one` decimal(18,2) DEFAULT NULL COMMENT '所有权1的份额',
  `uh_owner_two` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '所有权2',
  `uh_share_two` decimal(18,2) DEFAULT NULL COMMENT '所有权2的份额',
  `uh_loan_period` int(11) DEFAULT NULL COMMENT '贷款年限',
  `uh_month_pay` decimal(18,2) DEFAULT NULL COMMENT '每月供款',
  `uh_sett_pay` decimal(18,2) DEFAULT NULL COMMENT '尚欠供款余额',
  `uh_pay_bank` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '按揭银行',
  PRIMARY KEY (`uh_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='个人房产信息表';

-- ----------------------------
-- Records of abc_user_house
-- ----------------------------

-- ----------------------------
-- Table structure for `abc_user_other_info`
-- ----------------------------
DROP TABLE IF EXISTS `abc_user_other_info`;
CREATE TABLE `abc_user_other_info` (
  `uoi_id` int(11) NOT NULL AUTO_INCREMENT,
  `uoi_userid` int(11) DEFAULT NULL COMMENT '用户ID',
  `uoi_ability` varchar(15) COLLATE utf8_bin DEFAULT NULL COMMENT '个人能力',
  `uoi_hobby` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '个人爱好',
  `uoi_note` varchar(512) COLLATE utf8_bin DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`uoi_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin ROW_FORMAT=COMPACT COMMENT='个人其他信息表';

-- ----------------------------
-- Records of abc_user_other_info
-- ----------------------------

-- ----------------------------
-- Table structure for `abc_user_owner`
-- ----------------------------
DROP TABLE IF EXISTS `abc_user_owner`;
CREATE TABLE `abc_user_owner` (
  `uo_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `uo_user_id` int(11) DEFAULT NULL COMMENT '用户id',
  `uo_type` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '私营企业类型',
  `uo_address` varchar(512) COLLATE utf8_bin DEFAULT NULL COMMENT '经营场所地址',
  `uo_date` datetime DEFAULT NULL COMMENT '成立日期',
  `uo_rent_period` decimal(18,2) DEFAULT NULL COMMENT '租期',
  `uo_rent_money` decimal(18,2) DEFAULT NULL COMMENT '租金',
  `uo_tax_no` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '税务编号',
  `uo_registrat_no` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '工商登记号',
  `uo_profit_money` decimal(18,2) DEFAULT NULL COMMENT '全年盈利/亏损额',
  `uo_employ_num` int(11) DEFAULT NULL COMMENT '雇佣人数',
  `uo_month_pay` decimal(18,2) DEFAULT NULL COMMENT '每月供款?',
  `uo_sett_pay` decimal(18,2) DEFAULT NULL COMMENT '尚欠供款余额?',
  `uo_bank_pay` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '按揭银行?',
  PRIMARY KEY (`uo_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='个人私营业主资料表';

-- ----------------------------
-- Records of abc_user_owner
-- ----------------------------

-- ----------------------------
-- Table structure for `abc_user_spouse`
-- ----------------------------
DROP TABLE IF EXISTS `abc_user_spouse`;
CREATE TABLE `abc_user_spouse` (
  `us_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `us_user_id` int(11) DEFAULT NULL COMMENT '用户id',
  `us_name` varchar(25) COLLATE utf8_bin DEFAULT NULL COMMENT '配偶姓名',
  `us_income` decimal(18,2) DEFAULT NULL COMMENT '配偶收入',
  `us_phone` varchar(15) COLLATE utf8_bin DEFAULT NULL COMMENT '配偶移动电话',
  `us_work_phone` varchar(15) COLLATE utf8_bin DEFAULT NULL COMMENT '配偶工作电话',
  `us_work_company` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '配偶工作单位',
  `us_position` varchar(25) COLLATE utf8_bin DEFAULT NULL COMMENT '配偶职位',
  `us_work_address` varchar(512) COLLATE utf8_bin DEFAULT NULL COMMENT '配偶工作地点',
  PRIMARY KEY (`us_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='个人配偶资料表';

-- ----------------------------
-- Records of abc_user_spouse
-- ----------------------------

-- ----------------------------
-- View structure for `abc_view_cash_borrower`
-- ----------------------------
DROP VIEW IF EXISTS `abc_view_cash_borrower`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`%` SQL SECURITY DEFINER VIEW `abc_view_cash_borrower` AS select `pp`.`pp_pay_capital` AS `pp_pay_capital`,`pp`.`pp_pay_interest` AS `pp_pay_interest`,`pp`.`pp_pay_fine` AS `pp_pay_fine`,`pp`.`pp_pay_service_fee` AS `pp_pay_service_fee`,`pp`.`pp_pay_guar_fee` AS `pp_pay_guar_fee`,`pp`.`pp_pay_total_money` AS `pp_pay_total_money`,`pp`.`pp_pay_collect_capital` AS `pp_pay_collect_capital`,`pp`.`pp_pay_collect_interest` AS `pp_pay_collect_interest`,`pp`.`pp_pay_collect_fine` AS `pp_pay_collect_fine`,`pp`.`pp_collect_service_fee` AS `pp_collect_service_fee`,`pp`.`pp_collect_guar_fee` AS `pp_collect_guar_fee`,`pp`.`pp_collect_total` AS `pp_collect_total`,`pp`.`pp_remain_fine` AS `pp_remain_fine`,`pp`.`pp_loan_period` AS `pp_loan_period`,`pp`.`pp_is_clear` AS `pp_is_clear`,`pp`.`pp_pay_state` AS `pp_pay_state`,`pp`.`pp_replace_state` AS `pp_replace_state`,`pp`.`pp_loanee` AS `pp_loanee`,`au`.`user_name` AS `user_name`,`au`.`user_real_name` AS `user_real_name`,`au`.`user_type` AS `user_type` from (`abc_plan_payment` `pp` join `abc_user` `au` on((`pp`.`pp_loanee` = `au`.`user_id`))) ;

-- ----------------------------
-- View structure for `abc_view_cash_invester`
-- ----------------------------
DROP VIEW IF EXISTS `abc_view_cash_invester`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`%` SQL SECURITY DEFINER VIEW `abc_view_cash_invester` AS select `abc_account_info`.`account_user_id` AS `account_user_id`,`abc_account_info`.`account_user_name` AS `account_user_name`,`abc_user`.`user_real_name` AS `user_real_name`,`abc_account_info`.`account_user_type` AS `account_user_type`,`abc_account_info`.`account_no` AS `account_no`,`abc_plan_income`.`pi_pay_capital` AS `pi_pay_capital`,`abc_plan_income`.`pi_pay_interest` AS `pi_pay_interest`,`abc_plan_income`.`pi_pay_fine` AS `pi_pay_fine`,`abc_plan_income`.`pi_pay_total_money` AS `pi_pay_total_money`,`abc_plan_income`.`pi_collect_capital` AS `pi_collect_capital`,`abc_plan_income`.`pi_collect_interest` AS `pi_collect_interest`,`abc_plan_income`.`pi_collect_fine` AS `pi_collect_fine`,`abc_plan_income`.`pi_collect_total` AS `pi_collect_total`,`abc_plan_income`.`pi_remain_fine` AS `pi_remain_fine`,`abc_plan_income`.`pi_income_plan_state` AS `pi_income_plan_state`,`abc_invest`.`in_invest_money` AS `in_invest_money`,`abc_invest`.`in_valid_invest_money` AS `in_valid_invest_money`,`abc_invest`.`in_invest_state` AS `in_invest_state`,`abc_transfer_loan`.`tl_transfer_total` AS `tl_transfer_total`,`abc_transfer_loan`.`tl_transfer_fee` AS `tl_transfer_fee`,`abc_transfer_loan`.`tl_state` AS `tl_state`,`abc_buy_loan`.`bl_buy_total` AS `bl_buy_total`,`abc_buy_loan`.`bl_fee` AS `bl_fee`,`abc_buy_loan`.`bl_state` AS `bl_state`,`abc_buy_loan_subscribe`.`bls_transfer_money` AS `bls_transfer_money`,`abc_buy_loan_subscribe`.`bls_state` AS `bls_state` from ((((((`abc_account_info` left join `abc_plan_income` on((`abc_account_info`.`account_user_id` = `abc_plan_income`.`pi_beneficiary`))) left join `abc_invest` on((`abc_account_info`.`account_user_id` = `abc_invest`.`in_user_id`))) left join `abc_buy_loan` on((`abc_account_info`.`account_user_id` = `abc_buy_loan`.`bl_user_id`))) left join `abc_transfer_loan` on((`abc_account_info`.`account_user_id` = `abc_transfer_loan`.`tl_user_id`))) left join `abc_user` on((`abc_account_info`.`account_user_id` = `abc_user`.`user_id`))) left join `abc_buy_loan_subscribe` on((`abc_account_info`.`account_user_id` = `abc_buy_loan_subscribe`.`bls_user_id`))) where (`abc_account_info`.`account_user_type` <> 3) ;
