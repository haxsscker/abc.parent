package com.autoserve.abc.service.biz.enums;

/**
 * @author yuqing.zheng
 *         Created on 2014-12-15,11:13
 */
public enum IndustryType {
    AGRICULTURE(1 , "农、林、牧、渔业"),
    MANUFACTURE(2 , "制造业"),
    ELECTRIC(3 , "电力、燃气及水的生产和供应业"),
    ARCHITECTURE(4 , "建筑业"),
    TRANSPORTATION(5 , "交通运输、仓储和邮政业"),
    IT(6 , "信息传输、计算机服务和软件业"),
    RETAIL(7 , "批发和零售业"),
    FINANCE(8 , "金融业"),
    REAL_ESTATE(9 , "房地产业"),
    MINING(10, "采矿业"),
    RENT(11, "租赁和商务服务业"),
    SCIENCE(12, "科学研究、技术服务和地质勘查业"),
    WATER(13, "水利、环境和公共设施管理业"),
    SERVICE(14, "居民服务和其他服务业"),
    EDUCATION(15, "教育"),
    HEALTH(16, "卫生、社会保障和社会福利业"),
    CULTURE(17, "文化、体育和娱乐业"),
    SOCIETY_ORG(18, "公共管理和社会组织"),
    INTERNATIONAL_ORG(19, "国际组织"),
    CATERING(20, "住宿和餐饮业");

    IndustryType(int type, String prompt) {
        this.type = type;
        this.prompt = prompt;
    }

    public final int type;
    public final String prompt;

    public static IndustryType valueOf(Integer type) {
        for (IndustryType industryType : values()) {
            if (type != null && industryType.type == type) {
                return industryType;
            }
        }

        return null;
    }

    public int getType() {
		return type;
	}

	public String getPrompt() {
		return prompt;
	}

	@Override
    public String toString() {
        return prompt;
    }
}
