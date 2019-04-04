package com.autoserve.abc.web.util;

import com.alibaba.citrus.util.StringUtil;

/**
 * 数据脱敏规则
 */
public class SafeUtil {

    /**
     * 邮箱显示脱敏
     */
    public static String hideEmail(String email) {
        if (StringUtil.isBlank(email)) {
            return "******";
        }

        int eIndex = StringUtil.indexOf(email, "@");
        if (eIndex == -1) {
            return "******";
        }

        if (eIndex < 3) {
            return StringUtil.overlay(email, "***", eIndex, eIndex);
        }

        return StringUtil.overlay(email, "***", 3, eIndex);
    }

    /**
     * 姓名 如果要隐藏，隐藏第一个字
     *
     * @param name
     * @return
     */
    public static String hideName(String name) {
        return StringUtil.overlay(name, "**", 1, name.length());
    }
    

    /**
     * 企业工商注册号 显示后三位
     *
     * @param license
     * @return
     */
    public static String hideBusinessLicense(String license) {
        return StringUtil.overlay(license, "************", 0, license.length() - 3);
    }

    /**
     * 淘宝昵称 显示首/尾各1位，中间加** 例如：风**扬，m**d
     *
     * @param nick
     * @return
     */
    public static String hideTaobaoNick(String nick) {
        return StringUtil.overlay(nick, "**", 2, nick.length() - 1);
    }

    /**
     * 手机号 大陆：显示前 3 位 + **** + 后 4 位。如：137****9050
     *
     * @param mobile
     * @return
     */
    public static String hideMobile(String mobile) {
        return StringUtil.overlay(mobile, "****", 3, mobile.length() - 4);
    }

    /**
     * 手机号 香港、澳门：显示前2位＋****+后2位。如：90****85
     *
     * @param mobile
     * @return
     */
    public static String hideMobileHK(String mobile) {
        return StringUtil.overlay(mobile, "****", 2, mobile.length() - 2);
    }

    /**
     * 手机号 台湾：显示前2位＋****+后3位。如：90****856
     *
     * @param mobile
     * @return
     */
    public static String hideMobileTai(String mobile) {
        return StringUtil.overlay(mobile, "****", 2, mobile.length() - 3);
    }

    /**
     * 身份证号/军官证号/护照号 身份证号： 显示前 1 位＋ *（实际位数）＋后 1 位，如：5****************9 最低要求
     * 前6和后4位 军官证号，护照号： 使用缺省信息隐藏规则
     *
     * @param IDNumber
     * @return
     */
    public static String hideIDNumber(String IDNumber) {

        return StringUtil.overlay(IDNumber, "********", 6, IDNumber.length() - 4);
    }

    /**
     * 固定电话号码 如需要部分隐藏，推荐的规范：显示区号和后4位
     *
     * @param telephone 电话，非区号部分
     * @return
     */
    public static String hideTelephone(String telephone) {
        return StringUtil.overlay(telephone, "****", 0, telephone.length() - 4);
    }
	/**
	 * @param str 源字符串
	 * @param startNums 头部显示字符个数
	 * @param starNums 中间显示*号个数
	 * @param endNums 尾部显示字符个数
	 * @return
	 */
    public static String hideStr(String str,int startNums,int starNums,int endNums) {
    	if(StringUtil.isEmpty(str)) return "";
    	StringBuffer sb = new StringBuffer();
    	for(int i=0;i<starNums;i++){
    		sb.append("*");
    	}
    	if(str.length()<=startNums && str.length()>1){
    		return StringUtil.overlay(str, sb.toString(), 0, str.length()-1);
    	}
    	if(str.length()<=endNums && str.length()>1){
    		return StringUtil.overlay(str, sb.toString(), 1, str.length());
    	}
    	if(str.length()>startNums+endNums){
    		return StringUtil.overlay(str, sb.toString(), startNums, str.length() - endNums);
    	}else{
			return StringUtil.overlay(str, sb.toString(), startNums, str.length());
    	}
    }
    
    public static void main(String[] args) throws Exception {
        //姓名
        /*System.out.println(hideName("张名花"));
        System.out.println(hideBusinessLicense("1101105003676652"));
        System.out.println(hideBusinessLicense("12"));
        System.out.println(hideTaobaoNick("huihuiplay"));
        System.out.println(hideTaobaoNick("张名花"));
        System.out.println(hideTaobaoNick("赵传"));
        System.out.println(hideMobile("15081676812"));
        System.out.println(hideMobile(""));
        System.out.println(hideTelephone("82676812"));*/
    	System.out.println(hideStr("abcdef",2,2,2));
        System.out.println(hideStr("abcdef",3,2,3));
        System.out.println(hideStr("abcdef",3,2,4));
    }
}
