package com.autoserve.abc.service.util;

import java.util.Random;

/**
 * 类RandomUserName.java的实现描述：微信用户关注公众号后随机生成账号
 * 
 * @author WangMing 2015年6月23日 上午10:55:00
 */
public class RandomUserName {

    //微信平台自动生成用户名前缀
    public final static String  USERNAMEPREFIX    = "WECHAT";

    //用户名字符组称
    public final static char[]  USERNAMEWORD      = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C',
            'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X',
            'Y', 'Z'                             };

    //随机生成最小用户名长度
    public final static Integer MINUSERNAMELENGTH = 5;

    //获取随机生成的用户名
    public static String getRandomUserName() {
        Random random = new Random();
        //用户名长度
        Integer userNameLength = random.nextInt(6) + MINUSERNAMELENGTH;
        //获取用户名的字符构成
        char[] codes = new char[userNameLength];
        int userNameWordLenth = USERNAMEWORD.length;

        for (int i = 0; i < userNameLength; i++) {

            codes[i] = USERNAMEWORD[random.nextInt(userNameWordLenth)];
        }
        return USERNAMEPREFIX + String.valueOf(codes);
    }

}
