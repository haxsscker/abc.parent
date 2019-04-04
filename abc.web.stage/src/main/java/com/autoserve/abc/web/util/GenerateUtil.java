package com.autoserve.abc.web.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * 
* @ClassName: GenerateUtil 
* @Description: 工具类
* @author guobifeng
* @date 2013-03-06 
*
*/
public class GenerateUtil  {
	
	   
	private static Random randGen = null;
	private static char[] numbersAndLetters = null;

	private static Random randNumber = null;
	private static char[] numbers = null;
	
	/**
	 * 序列号生成 20 位
	 * @return
	 */
	public static final String generateSerial(){
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		String ss = df.format(new Date());
		Random rand = new Random();
		return ss+rand.nextInt(999);
	}
	
	
	/**
	 * 生成App通信密钥 24位字符串长度
	* @Title: generateAppSecret 
	* @Description: TODO
	* @Author zguowei jakbb01@gmail.com
	* @Time 2013-3-8 下午3:31:53
	* @return
	* @return String
	* @throws
	 */
	public static final String generateAppSecret() {
		return getRandomStr(24);
	}
	
	public static final String getRandomStr(int length) {
        if (length < 1) {
            return null;
        }
        if (randGen == null) {
               randGen = new Random();
               numbersAndLetters = ("0123456789abcdefghijklmnopqrstuvwxyz" +
                  "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ").toCharArray();
                }
        char [] randBuffer = new char[length];
        for (int i=0; i<randBuffer.length; i++) {
            randBuffer[i] = numbersAndLetters[randGen.nextInt(71)];
        }
        return new String(randBuffer);
    }
	
	/**
	 * 生成validCode六位随机验证码
	* @Title: generateValidCode 
	* @Author:guobifeng
	* @return String
	* @throws
	 */
	public static final String generateValidCode() {
		return getRandomNumber(6);
	}
	public static final String getRandomNumber(int length) {
        if (length < 1) {
            return null;
        }
        if (randNumber == null) {
        	 randNumber = new Random();
             numbers =("0123456789").toCharArray();
                }
        char [] randBuffer = new char[length];
        for (int i=0; i<randBuffer.length; i++) {
            randBuffer[i] = numbers[randNumber.nextInt(9)];
        }
        return new String(randBuffer);
    }
	/**
	* @Title: 生成uid 
	* @Description: 格式 (yyyyMMddHHmmss+10位随机数)
	* @return String
    **/
	public static final String generateUid() {
		SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddHHmmss");
	         if (randGen == null) {
	                randGen = new Random();
	                numbersAndLetters = ("0123456789abcdefghijklmnopqrstuvwxyz" +
	                   "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ").toCharArray();
	                 }
	         char [] randBuffer = new char[10];
	         for (int i=0; i<randBuffer.length; i++) {
	             randBuffer[i] = numbersAndLetters[randGen.nextInt(71)];
	         }
	         return sdf.format(new Date())+new String(randBuffer);
	}	
	
	
	
	/**密码加密时使用盐值
	 * 生成规则为5位随即字符数字
	 * @return
	 */
	public static final String getPwdSalt(){
		return getRandomStr(5);
	}
	
	
	public static void main(String[] args) {
		System.out.println(getRandomNumber(6));
	}
	
}
