package com.autoserve.abc.web.util;

import com.alibaba.citrus.util.StringUtil;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 用来处理一些业务逻辑相关的事情
 */
public class LogicUtil {
    /** 1K */
    private static Long         STORAGE_K                          = 1024l;
    private static Long         STORAGE_M                          = 1024l * 1024l;
    private static Long         STORAGE_G                          = 1024l * 1024l * 1024l;
    
    /** 验证EMAIL地址的正则表达式 */
    public static final String  EMAIL_REGEXPATTENSTR               = "^\\w+([.!#$%&'*+-/=?^_`{|}~]\\w*)*@\\w+(([-]*|[.])\\w+)*\\.\\w+(([-]*|[.])\\w+)*$";

    /** 完整邮件格式 ，eg：udvs<udvs@163.com> */
    public static String        FULL_MAIL_FORMAT_REGEX             = "(\"{0,1}(\\w|\\W)*\"{0,1})\\s*<(\\w+([.!#$%&'*+-/=?^_`{|}~]\\w*)*)@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*>";

    /** 普通邮件格式 ，eg：udvs@163.com */
    public static String        MAIL_FORMAT_REGEX                  = "(\\w+([.!#$%&'*+-/=?^_`{|}~]\\w*)*)@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*";
    public static String        MAIL_ALIAS_REGEX                   = "[[\u4e00-\u9fa5]|\\w|_|\\s|\\-|@|.]*";
    public static Pattern       emailPattern;
    public static Pattern       fullEmailPattern;
    public static Pattern       mailAliasPattern;
    public static Pattern       domainPattern;
    public static Pattern       domainPatternIncludePrefix;                                                                                                                  //@doomain.com
    /** 普通域格式 ，eg：163.com */
    public static String        DOMAIN_FORMAT_REGEX                = "([([A-Za-z0-9]|\\-|_)]+\\.)+[a-zA-Z]{2,3}";
    public static String        DOMAIN_INCLUDE_PREFIX_FORMAT_REGEX = "@([([A-Za-z0-9]|\\-|_)]+\\.)+[a-zA-Z]{2,3}";
    static {
        emailPattern = Pattern.compile(MAIL_FORMAT_REGEX);
        fullEmailPattern = Pattern.compile(FULL_MAIL_FORMAT_REGEX);
        mailAliasPattern = Pattern.compile(MAIL_ALIAS_REGEX);
        domainPattern = Pattern.compile(DOMAIN_FORMAT_REGEX);
        domainPatternIncludePrefix = Pattern.compile(DOMAIN_INCLUDE_PREFIX_FORMAT_REGEX);
    }

    private static final char[] PWD_CHAR                           = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i',
        'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3',
        '4', '5', '6', '7', '8', '9'                          };

    public static Map<String, String> formateStorage(String totalStorage, String usedStorage) throws Exception {
        return formateStorage(Long.valueOf(totalStorage), Long.valueOf(usedStorage));
    }

    /**
     * 首页展示容量大小的计算方法
     *
     * @param totalStorage
     * @param usedStorage
     * @return
     */
    public static Map<String, String> formateStorage(Long totalStorage, Long usedStorage) throws Exception {
        Map<String, String> storageMap = new HashMap<String, String>();

        String totalStorageS = formateStorage(totalStorage, 3);
        String usedStorageS = formateStorage(usedStorage, 3);
        String unusedStorageS = "";

        if ("".equals(totalStorageS)) {
            totalStorageS = "0K";
        }
        if ("".equals(usedStorageS)) {
            usedStorageS = "0K";
        }

        String totalUnit = totalStorageS.substring(totalStorageS.length() - 1, totalStorageS.length());
        String usedUnit = usedStorageS.substring(usedStorageS.length() - 1, usedStorageS.length());

        Float totalStorageF = Float.valueOf(totalStorageS.substring(0, totalStorageS.length() - 1));
        Float usedStorageF = Float.valueOf(usedStorageS.substring(0, usedStorageS.length() - 1));
        Float unusedStorageF = 0F;

        Long totalStorageL = 0L;
        Long usedStorageL = 0L;
        Long unusedStorageL = 0L;
        if ("G".equals(totalUnit)) {
            //  已使用容量单位为G
            if ("G".equals(usedUnit)) {
                unusedStorageF = totalStorageF * 1000 - usedStorageF * 1000;
                //  未使用容量小于1G
                if (unusedStorageF < 1000) {
                    if (unusedStorageF.intValue() == 0) {
                        unusedStorageS = "0G";
                    } else {
                        unusedStorageS = unusedStorageF.longValue() + "M";
                    }
                }
                //  未使用容量大于等于1G
                else {
                    unusedStorageS = (unusedStorageF / 1000 + "G").replace(".0G", "G");
                }
                //  已使用容量单位为M
            } else if ("M".equals(usedUnit)) {
                usedStorageL = usedStorageF.longValue();
                usedStorageS = usedStorageL + "M";

                unusedStorageF = totalStorageF * 1000 - usedStorageL;
                //  未使用容量小于1G
                if (unusedStorageF < 1000) {
                    unusedStorageS = unusedStorageF.longValue() + "M";
                }
                //  未使用容量大于等于1G
                else {
                    unusedStorageS = (unusedStorageF / 1000 + "G").replace(".0G", "G");
                }
                //  已使用容量单位为K
            } else {
                unusedStorageS = totalStorageS;
                usedStorageS = usedStorageF.longValue() + "K";
            }
        }

        //  总容量单位为M
        if ("M".equals(totalUnit)) {
            //  已使用容量单位为M
            if ("M".equals(usedUnit)) {
                unusedStorageF = totalStorageF * 1000 - usedStorageF * 1000;
                //  未使用容量小于1M
                if (unusedStorageF < 1000) {
                    if (unusedStorageF.intValue() == 0) {
                        unusedStorageS = "0M";
                    } else {
                        unusedStorageS = unusedStorageF.longValue() + "K";
                    }
                }
                //  未使用容量大于等于1M
                else {
                    unusedStorageS = (unusedStorageF / 1000 + "M").replace(".0M", "M");
                }
            }
            //  已使用容量单位为K
            else {

                usedStorageS = usedStorageF.longValue() + "K";

                unusedStorageF = totalStorageF * 1000 - usedStorageF.longValue();
                //  未使用容量小于1M
                if (unusedStorageF < 1000) {
                    unusedStorageS = unusedStorageF.longValue() + "K";
                }
                //  未使用容量大于等于1M
                else {
                    unusedStorageS = (unusedStorageF / 1000 + "M").replace(".0M", "M");
                }
            }
        }
        //  总容量单位为K
        if ("K".equals(totalUnit)) {
            totalStorageL = totalStorageF.longValue();
            usedStorageL = usedStorageF.longValue();
            unusedStorageL = totalStorageL - usedStorageL;
            totalStorageS = totalStorageL + "K";
            usedStorageS = usedStorageL + "K";
            unusedStorageS = unusedStorageL + "K";
        }

        storageMap.put("total", totalStorageS);
        storageMap.put("used", usedStorageS);
        storageMap.put("unused", unusedStorageS);
        return storageMap;
    }

    public static String formateStorage(Long storage) {
        return formateStorage(storage, 0);
    }

    /**
     * 获取容量大小的表示方法 <br/>
     * 1. 如果数值为null或者空字符串，或者非数字类型，返回空字符串<br/>
     * 2. 小于1K 返回1K <br/>
     * 3. 小于1M 返回XXK </br> 4. 小于1G 返回xxM </br> 5. 其他返回xxG </br>
     *
     * @param storage storage unit:byte
     * @return String 容量大小的表示方法
     */
    public static String formateStorage(Long storage, int c) {
        if (storage == null) {
            return "";
        }

        NumberFormat f = null;

        if (c > 0 && c <= 8) {
            String p = "#.";

            for (int i = 0; i < c; i++) {
                p += "#";
            }

            f = new DecimalFormat(p);
        }

        if (storage == 0) {
            return "0K";
        } else if (storage < STORAGE_K) {
            return "1K";
        } else if (storage < STORAGE_M) {
            return (f == null ? ("" + storage / STORAGE_K) : f.format(((double) storage) / STORAGE_K)) + "K";
        } else if (storage < STORAGE_G) {
            return (f == null ? ("" + storage / STORAGE_M) : f.format(((double) storage) / STORAGE_M)) + "M";
        } else {
            return (f == null ? ("" + storage / STORAGE_G) : f.format(((double) storage) / STORAGE_G)) + "G";
        }
    }

    /**
     * 获取容量大小的表示方法 <br/>
     * 1. 如果数值为null或者空字符串，或者非数字类型，返回空字符串<br/>
     * 2. 小于1K 返回1K <br/>
     * 3. 小于1M 返回XXK </br> 4. 小于1G 返回xxM </br> 5. 其他返回xxG </br>
     *
     * @param storage storage unit:byte
     * @return String 容量大小的表示方法
     */
    public static String formateStorage(Integer intStorage) {
        if (intStorage == null) {
            return "";
        }

        Long storage = Long.valueOf(intStorage);

        return formateStorage(storage);
    }

    /**
     * 获取容量大小的表示方法 <br/>
     * 1. 如果数值为null或者空字符串，或者非数字类型，返回空字符串<br/>
     * 2. 小于1K 返回1K <br/>
     * 3. 小于1M 返回XXK </br> 4. 小于1G 返回xxM </br> 5. 其他返回xxG </br>
     *
     * @param storage storage unit:byte
     * @return String 容量大小的表示方法
     */
    public static String formateStorage(String str) {
        if (StringUtils.isEmpty(str) || (!StringUtils.isNumeric(str))) {
            return "";
        }

        Long storage = Long.valueOf(str);

        return formateStorage(storage);
    }

    public static String formateStorage(String str, int c) {
        if (StringUtils.isEmpty(str) || (!StringUtils.isNumeric(str))) {
            return "";
        }

        Long storage = Long.valueOf(str);

        return formateStorage(storage, c);
    }

    /**
     * 获取容量大小的表示方法 1. 小于1K 返回1K 2. 小于1M 返回XXK 3. 小于1G 返回xxM 4. 其他返回xxG
     *
     * @param storage storage unit:byte
     * @return String 容量大小的表示方法
     */
    public static String formateAttachStorage(String str) {
        if (StringUtils.isEmpty(str) || (!StringUtils.isNumeric(str))) {
            return "";
        }

        Double length = Double.valueOf(str);
        return formateAttachStorage(length);
    }

    public static String formateAttachStorage(Double str) {
        if (str == null) {
            return "0Byte";
        }

        Double length = str;
        Double dl = null;
        BigDecimal b = null;
        String value = "";
        String unit = "";
        if (length < 1024) {
            value = String.format("%.2f", str);
            unit = "Byte";
        } else if (length < 1048576) {
            b = new BigDecimal((length / STORAGE_K));
            dl = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
            value = String.format("%.2f", dl);
            unit = "K";
        } else if (length < STORAGE_G) {
            b = new BigDecimal((length / STORAGE_M));
            dl = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
            value = String.format("%.2f", dl);
            unit = "M";
        } else {
            value = String.format("%.2f", (length / STORAGE_G));
            unit = "G";
        }

        // 如果有小数点去除尾巴上的0
        while (value.indexOf(".") > 0 && value.endsWith("0")) {
            value = value.substring(0, value.length() - 1);
        }

        if (value.endsWith(".")) {
            value = value.substring(0, value.length() - 1);
        }

        return value + unit;
    }

    public static String formateAttachStorage(Integer str) {
        Double length = Double.valueOf(str);
        return formateAttachStorage(length);
    }

    public static String formateAttachStorage(Long str) {
        Double length = Double.valueOf(str);
        return formateAttachStorage(length);
    }

    /**
     * 占用空间全部用(M)为单位折算。 文件夹为空则为0M 其余合计总量后折算为M单位。保留小数点后两位，两位后数据四舍五入。
     * 文件夹列表已使用容量的表示方法 当0.02>=文件夹大小>=0时，均显示0.01M
     *
     * @param str
     * @return
     */
    public static String formatFolderStorage(Double str) {
        if (str == null || str == 0) {
            return "0M";
        }

        Double length = str;
        Double dl = null;
        BigDecimal b = null;
        b = new BigDecimal((length / STORAGE_M));
        dl = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        if (dl < 0.02) {
            return "0.01M";
        }
        return String.format("%.2f", dl) + "M";
    }

    public static String formatFolderStorage(Long str) {
        Double length = Double.valueOf(str);
        return formatFolderStorage(length);
    }

    /**
     * 计算使用空间百分比<br/>
     * 保留小数点1位，小数点第二位之后4舍5入, 如果使用量小于0.1%,且大于0，则显示0.1%,如果为0，则显示0.0%
     *
     * @param usedStorage
     * @param totalStorage
     * @return String 百分比
     */
    public static String calcUsedPercent(Long usedStorage, Long totalStorage) {
        if (usedStorage == null || totalStorage == null) {
            return "";
        }
        return calcUsedPercent(usedStorage.longValue(), totalStorage.longValue());
    }

    /**
     * 计算剩余空间百分比<br/>
     * 百分比精确到小数点后一位， 小数点第二位之后的数据舍去
     *
     * @param usedStorage
     * @param totalStorage
     * @return String 百分比
     */
    public static String calcUnusedPercent(Long usedStorage, Long totalStorage) {
        if (usedStorage == null || totalStorage == null) {
            return "";
        }
        return calcUnusedPercent(usedStorage.longValue(), totalStorage.longValue());
    }

    /**
     * 计算剩余空间百分比<br/>
     * 百分比精确到小数点后一位， 小数点第二位之后的数据舍去
     *
     * @param usedStorage
     * @param totalStorage
     * @return String 百分比
     */
    public static String calcUnusedPercent(String usedStorage, String totalStorage) {
        if (StringUtil.isEmpty(usedStorage) || StringUtil.isEmpty(totalStorage)) {
            return "";
        }
        return calcUnusedPercent(Long.valueOf(usedStorage), Long.valueOf(totalStorage));
    }

    /**
     * 计算剩余空间百分比<br/>
     * 百分比精确到小数点后一位， 小数点第二位之后的数据舍去
     *
     * @param usedStorage
     * @param totalStorage
     * @return
     */
    public static String calcUnusedPercent(long usedStorage, long totalStorage) {
        if (usedStorage < 0 || totalStorage <= 0) {
            return "0.0%";
        }
        if (usedStorage == 0) {
            return "100.0%";
        }

        BigDecimal bigUnusedStorage = new BigDecimal((totalStorage - usedStorage) * 100);
        BigDecimal bigTotalStorage = new BigDecimal(totalStorage);
        BigDecimal percent = bigUnusedStorage.divide(bigTotalStorage, 1, BigDecimal.ROUND_DOWN);
        double dPercent = percent.doubleValue();
        DecimalFormat df = new DecimalFormat("#0.0");
        return df.format(dPercent) + "%";
    }

    /**
     * 计算已使用百分比
     *
     * @param usedStorage
     * @param totalStorage
     * @return
     */
    public static String calcUsedPercent(long usedStorage, long totalStorage) {

        if (totalStorage <= 0 || (totalStorage <= usedStorage)) {
            return "100.0%";
        }
        if (usedStorage < 0 || usedStorage == 0) {
            return "0.0%";
        }

        BigDecimal bigUsedStorage = new BigDecimal((usedStorage) * 100);
        BigDecimal bigTotalStorage = new BigDecimal(totalStorage);
        BigDecimal percent = bigUsedStorage.divide(bigTotalStorage, 1, BigDecimal.ROUND_HALF_UP);
        double dPercent = percent.doubleValue();
        DecimalFormat df = new DecimalFormat("#0.0");
        String ret = df.format(dPercent) + "%";
        if ("0.0%".equals(ret)) {
            return "0.1%";
        }
        return ret;

    }

    /**
     * 计算剩余空间百分比<br/>
     * 百分比精确到小数点后一位， 小数点第二位之后的数据舍去
     *
     * @param usedStorage
     * @param totalStorage
     * @return
     */
    public static String calcUsedPercent(int usedStorage, int totalStorage) {
        return calcUsedPercent((long) usedStorage, (long) totalStorage);
    }

    /**
     * 计算剩余空间百分比<br/>
     * 百分比精确到小数点后一位， 小数点第二位之后的数据舍去
     *
     * @param usedStorage
     * @param totalStorage
     * @return
     */
    public static String calcUsedPercent(String usedStorage, String totalStorage) {
        try {
            long used = Long.valueOf(usedStorage);
            long total = Long.valueOf(totalStorage);
            return calcUsedPercent(used, total);
        } catch (Exception e) {
            return calcUsedPercent(0, 0);
        }
    }

    /**
     * 得到显示名
     *
     * @param emailInfo
     * @param noteName
     * @return String 显示名
     */
    public static String getName(String emailInfo, String noteName) {
        if (StringUtils.isNotEmpty(noteName)) {
            return noteName;
        }

        if (StringUtils.isEmpty(emailInfo)) {
            return "";
        }

        return getEmailName(emailInfo);
    }

    /**
     * 得到显示名,中文算作两位,字符算一位,如果长度超过15位,截取字符串当前长度减2
     *
     * @param emailInfo
     * @param noteName
     * @return String 显示名
     */
    public static String getNameForShort(String emailInfo, String noteName, String loginEmail, String loginEmailName) {
        String str = null;
        if (StringUtils.isNotEmpty(noteName)) {
            str = noteName;
        } else {
            if (StringUtils.isEmpty(emailInfo)) {
                return "";
            }

            str = getEmailName(emailInfo, loginEmail, loginEmailName);
        }

        return str;
    }

    public static String getOneEmail(String oneEmail) {
        String email = oneEmail.trim();
        Matcher m = fullEmailPattern.matcher(email);

        if (m.matches()) {
            String group = m.group();
            int end = group.lastIndexOf(">");
            int start = group.lastIndexOf("<", end) + 1;

            return group.substring(start, end);
        } else {
            m = emailPattern.matcher(email);
            if (m.matches()) {
                return email;
            }
        }

        return null;
    }

    public static String getEmail(String emailInfo) {
        if (StringUtils.isEmpty(emailInfo)) {
            return "";
        }

        Set<String> emailSet = new HashSet<String>();
        String[] emails = emailInfo.split(",");
        StringBuilder emailString = new StringBuilder();
        boolean isFirst = true;

        for (int i = 0; i < emails.length; i++) {
            String oneEmail = getOneEmail(emails[i]);

            if (StringUtils.isNotEmpty(oneEmail)) {
                if (!emailSet.contains(oneEmail)) {
                    if (!isFirst) {
                        emailString.append(", ");
                    }

                    isFirst = false;
                    emailString.append(oneEmail);
                    emailSet.add(oneEmail);
                }
            }
        }

        return emailString.toString();
    }

    /**
     * 把类似于如下格式的数据，把其中的email数据提取出来 <br/>
     * 1. "xx"<xxx@abc.com> -> xxx@abc.com true <br/>
     * 2. xxx<ddd@abc.com> -> ddd@abc.com true<br/>
     * 3.<ddd@abc.com> -> ddd@abc.com true<br/>
     * 4. eee@abc.com.cn ->eee@abc.com.cn true<br/>
     * 4. asdfd -> false <br/>
     *
     * @param emailInfo email数据地址
     * @return String 格式化的email，如果邮件地址不合法返回空字符串
     */
    public static boolean isEmail(String emailInfo) {
        if (StringUtils.isEmpty(emailInfo)) {
            return false;
        }

        Matcher m = fullEmailPattern.matcher(emailInfo);
        if (m.matches()) {
            return true;
        }

        m = emailPattern.matcher(emailInfo);
        if (m.matches()) {
            return true;
        }

        return false;
    }

    /**
     * 把类似于如下格式的数据，把其中的email数据提取出来 <br/>
     * 1. eee@abc.com.cn ->eee@abc.com.cn true<br/>
     * 2. asdfd -> false <br/>
     *
     * @param emailInfo email数据地址
     * @return boolean true 是合法的email ，false不是email
     */
    public static boolean isSimpleEmail(String emailInfo) {
        if (StringUtils.isEmpty(emailInfo)) {
            return false;
        }

        Matcher m = emailPattern.matcher(emailInfo);
        if (m.matches()) {
            return true;
        }

        return false;
    }

    /**
     * 把类似于如下格式的数据，把其中的email数据提取出来 <br/>
     * 1. eee@abc.com.cn ->eee@abc.com.cn true<br/>
     * 2. asdfd -> false <br/>
     *
     * @param domainInfo 域地址
     * @return boolean true 是合法的域 ，false不是合法的域
     */
    public static boolean isDomain(String domainInfo) {
        if (StringUtils.isEmpty(domainInfo)) {
            return false;
        }

        Matcher m = domainPattern.matcher(domainInfo);
        if (m.matches()) {
            return true;
        }

        return false;
    }

    /***
     * 判断domainInfo的格式是否类似于@xxx.com或者@xx.cn，必须以@作为前缀
     *
     * @param domainInfo
     * @return
     */
    public static boolean isDomainIncludePrefix(String domainInfo) {
        if (StringUtils.isEmpty(domainInfo)) {
            return false;
        }

        Matcher m = domainPatternIncludePrefix.matcher(domainInfo);
        if (m.matches()) {
            return true;
        }

        return false;
    }

    /**
     * get system temp folder
     *
     * @return String temp folder
     */
    public static String getTempFolderPath() {
        String tempFilePath = System.getProperty("java.io.tmpdir");
        if (!(tempFilePath.endsWith("/") || tempFilePath.endsWith("\\"))) {
            tempFilePath = tempFilePath.concat("/");
        }
        return tempFilePath;
    }

    /**
     * 把输入的字符串按照分割符号（半角逗号，半角封号）分割，<br/>
     * 1. 如果分割符号在两个双引号或者<>之间则不算<br/>
     * 2. 如果双引号之前有\则不算双引号<br/>
     * （多个邮件地址比如："huanghongqing"<hhq@adf.com>;"bdss"<asdf@asdf.com>）.<br/>
     * 多个邮件地址之间用逗号或者封号分割，如下所示：<br/>
     * 1. email1;email2;email3<br/>
     * 2. email1,email2,email3<br/>
     * 单个邮件地址格式如下所示：<br/>
     * 1. abcd@abcde.com <br/>
     * 2. <abcd@abcd.com> <br/>
     * 3. "te;,st"<abcd@abcd.com> <br/>
     * 4. 'te;st'<abcd@abcd.com> <br/>
     * 5. 'te;st'<abc;,d@abcd.com> <br/>
     * 状态描述：<br/>
     * 0. 初始状态 1. 读到第一个" 2. 读到第二个" 前面没有\" 3.<br/>
     * 读到< 4. 读到> 5. 在状态0情况下读到非",<,半脚空格的时候<br/>
     *
     * @param emails 多个邮件地址
     * @return List<String> 多个邮件地址
     */
    public static List<String> splitMail(String emails) {
        List<String> mailList = new ArrayList<String>();

        if (StringUtils.isEmpty(emails)) {
            return mailList;
        }
        emails = emails.trim();
        String mail;
        StringBuilder sb = new StringBuilder();
        // 转化成为一个字符数组
        char[] emailChars = emails.toCharArray();
        int status = 0;
        for (int i = 0; i < emailChars.length; i++) {
            switch (emailChars[i]) {
                case '"':
                    if (status == 0) {
                        status = 1;
                    } else if (status == 1 && (emailChars[i - 1] != '\\')) {
                        status = 2;
                    }
                    sb.append(emailChars[i]);
                    break;
                case '<':
                    if (status == 2 || status == 0 || status == 5) {
                        status = 3;
                    }
                    sb.append(emailChars[i]);
                    break;
                case '>':
                    if (status == 3) {
                        status = 4;
                    }
                    sb.append(emailChars[i]);
                    break;
                case ';':
                case ',':
                    // 如果逗号不在""或者 <>之间，就当作是结束符号
                    if (status != 1 && status != 3) {
                        status = 0;
                        mail = sb.toString().trim();
                        if (mail.length() > 0) {
                            mailList.add(mail);
                            sb = new StringBuilder();
                        }
                    } else {
                        sb.append(emailChars[i]);
                    }
                    break;
                case ' ':
                    sb.append(emailChars[i]);
                    break;
                default:
                    if (status == 0) {
                        status = 5;
                    }
                    sb.append(emailChars[i]);
                    break;
            }
        }
        mail = sb.toString().trim();
        if (mail.length() > 0) {
            mailList.add(mail);
        }
        return mailList;
    }

    /**
     * 取得多个邮件地址中的第一个邮箱地址
     *
     * @param mails 收件人
     * @return String 如果有则返回地址，没有则返回空字符串
     */
    public static String getFirstMail(String mails) {
        List<String> mailList = splitMail(mails);
        String email = "";
        if (mailList.size() > 0) {
            email = getEmail(mailList.get(0));
            if (StringUtils.isEmpty(email)) {
                email = mailList.get(0);
            }
        }

        return email;
    }

    /**
     * 取得多个邮件地址中的第一个邮箱地址,包含昵称
     *
     * @param mails 收件人
     * @return String 如果有则返回地址，没有则返回空字符串
     */
    public static String getFirstOrigianlMail(String mails) {
        List<String> mailList = splitMail(mails);
        if (mailList.size() > 0) {
            return mailList.get(0);
        }
        return "";
    }

    /**
     * 转义掉html标签
     *
     * @param str
     * @return
     */
    public static String covertHtmlToStr(String str) {
        if (StringUtils.isEmpty(str)) {
            return "";
        }

        return StringEscapeUtils.escapeHtml(str);
    }

    public static String getEmailNameArray(List<String> emailNames) {
        StringBuilder emailString = new StringBuilder();

        for (int i = 0; emailNames != null && i < emailNames.size(); i++) {

            if (!StringUtils.isEmpty(emailNames.get(i))) {
                if (emailString.length() > 0) {
                    emailString.append(',');
                }

                emailString.append(emailNames.get(i));
            }
        }

        return emailString.toString();
    }

    public static String getEmailName(String emailInfo) {
        return getEmailName(emailInfo, null, null);
    }

    /**
     * 把类似于如下格式的数据，把其中的名字数据提取出来 <br/>
     * 1. "xx"<xxx@abc.com> -> xxx@abc.com <br/>
     * 2. xxx<ddd@abc.com> -> ddd@abc.com <br/>
     * 3.<ddd@abc.com> -> ddd@abc.com <br/>
     * 4. eee@abc.com.cn ->eee@abc.com.cn <br/>
     * 4. asdfd -> 空字符串 <br/>
     *
     * @param emailInfo email数据地址
     * @return String 格式化的email，如果邮件地址不合法返回空字符串
     */
    public static String getEmailName(String emailInfo, String loginEmail, String loginEmailName) {
        String[] emails = emailInfo.split("[;,]");
        StringBuilder emailString = new StringBuilder();
        boolean isFirst = true;

        for (int j = 0; j < emails.length; j++) {
            String email = emails[j].trim();
            String name = null;

            if (!StringUtils.isEmpty(loginEmail)) {
                String m = getEmail(email);

                if (loginEmail.equalsIgnoreCase(m)) {
                    name = loginEmailName;
                }
            }

            if (StringUtils.isEmpty(name)) {
                name = getNameByFullEmail(email);

                if (StringUtils.isEmpty(name)) {
                    Matcher m = emailPattern.matcher(email);
                    if (m.matches()) {
                        for (int i = 0; i < m.groupCount(); i++) {
                            name = m.group(1);
                        }
                    }
                }
            }

            if (!isFirst) {
                emailString.append(", ");
            }
            isFirst = false;
            emailString.append(name);
        }

        return emailString.toString();
    }

    /**
     * 把类似于如下格式的数据，把其中的名字数据提取出来 <br/>
     * 1. "xx"<xxx@abc.com> -> xxx@abc.com <br/>
     * 2. xxx<ddd@abc.com> -> ddd@abc.com <br/>
     * 3.<ddd@abc.com> -> ddd@abc.com <br/>
     * 4. eee@abc.com.cn ->eee@abc.com.cn <br/>
     * 4. asdfd -> 空字符串 <br/>
     *
     * @param emailInfo email数据地址
     * @return String 格式化的email，如果邮件地址不合法返回空字符串
     */
    public static String getNameByFullEmail(String emailInfo) {
        if (StringUtils.isEmpty(emailInfo)) {
            return "";
        }
        String name = "";
        emailInfo = emailInfo.trim();
        Matcher m = fullEmailPattern.matcher(emailInfo);
        if (m.matches()) {
            name = StringUtils.isNotEmpty(m.group(1)) ? m.group(1) : m.group(3);
            if (name != null) {
                name = name.trim();
                if (name.startsWith("\"") && name.endsWith("\"") && name.length() >= 2) {
                    name = name.substring(1, name.length() - 1);
                } else if (name.startsWith("'") && name.endsWith("'") && name.length() >= 2) {
                    name = name.substring(1, name.length() - 1);
                }
            }
        }

        return name;
    }

    /**
     * 匹配出所有访问附件服务器的url对应的附件路径
     *
     * @param content
     * @param url
     * @return
     */
    public static List<String> matchAttachPath(String content, String url) {
        List<String> result = new ArrayList<String>();
        if (StringUtils.isEmpty(content) || StringUtils.isEmpty(url)) {
            return result;
        }

        String regex = "<[^<]+=(\"|\'){0,1}" + url
                + "\\?attachment_name=([^\"\']+)&attachment_path=([^\"\'&]+)(&[^\"\']+)*(\"|\'){0,1}[^>]*>";
        Pattern p = java.util.regex.Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(content);
        while (m.find()) {
            try {
                result.add(URLDecoder.decode(m.group(3), "UTF-8"));
            } catch (UnsupportedEncodingException e) {
            }
        }

        return result;
    }

    /**
     * 根据URL获取邮件domain的名称
     *
     * @param url
     * @return String 邮件domain
     */
    public static String getMailDomainFromUrl(String url) {
        String domain = getDomainFromUrl(url);
        if (StringUtil.isEmpty(domain)) {
            return "";
        }
        String prefix = "mail.";
        if (domain.startsWith(prefix)) {
            return domain.substring(5);
        } else {
            return domain;
        }
    }

    /**
     * 根据URL获取domain
     *
     * @param url
     * @return String domain
     */
    public static String getDomainFromUrl(String url) {
        if (StringUtil.isEmpty(url)) {
            return "";
        }
        return url.replaceAll("(.+//)([^:/]+)(:\\w+)?(/.*)?", "$2");
    }

    /**
     * 判断指定的文件名称的格式是否如下所示：<br/>
     * 1. filePrefix + (n) + fileSuffix <br/>
     * 2. filePrefix + fileSuffix <br/>
     *
     * @param fileName 目标文件名称
     * @param filePrefix 文件名称
     * @param fileSuffix 文件后缀名
     * @return index 如果不匹配则返回-1, 如果匹配则返回n，如果
     */
    public static long getFileIndex(String fileName, String filePrefix, String fileSuffix) {
        if (StringUtils.isEmpty(fileName)) {
            return -1;
        }
        int startIndex = 0;
        int endIndex = fileName.length();
        if (!StringUtil.isEmpty(filePrefix)) {
            if (fileName.startsWith(filePrefix)) {
                startIndex = filePrefix.length();
            } else {
                return -1;
            }
        }
        if (!StringUtil.isEmpty(fileSuffix)) {
            if (fileName.endsWith(fileSuffix)) {
                endIndex = endIndex - fileSuffix.length();
            } else {
                return -1;
            }
        }
        if (startIndex == endIndex) {
            return 0;
        }

        String temp = fileName.substring(startIndex, endIndex);
        int length = temp.length();
        if (length > 2 && temp.startsWith("(") && temp.endsWith(")")) {
            String strIndex = temp.substring(1, length - 1);
            if (StringUtils.isNumeric(strIndex)) {
                return Long.parseLong(strIndex);
            }
        }
        return -1;
    }

    /**
     * 获取多个邮件的email地址 如"阿斯顿2" <abc@mail008.alisoft-inc.com>,"杨东"
     * <yangdong@mail008.alisoft-inc.com>
     * 返回abc@mail008.alisoft-inc.com，yangdong@mail008.alisoft-inc.com
     *
     * @param emails
     * @return
     */
    public static List<String> getEmailNames(String emails) {
        List<String> resultList = new ArrayList<String>();
        List<String> emailList = splitMail(emails);
        for (String fullEmail : emailList) {
            String email = getEmail(fullEmail);
            if (!resultList.contains(email)) {
                resultList.add(email);
            }
        }
        return resultList;
    }

    public static List<String> getUniqueFullEmailNames(String emails) {
        List<String> resultList = new ArrayList<String>();
        List<String> emailList = splitMail(emails);
        for (String fullEmail : emailList) {
            if (!resultList.contains(fullEmail)) {
                resultList.add(fullEmail);
            }
        }
        return resultList;
    }

    /**
     * 生成随机8位密码
     *
     * @return String 随机数
     */
    public static String create8password() {
        StringBuffer password = new StringBuffer();
        Random rand = new Random();
        int length = PWD_CHAR.length;
        int x;
        for (int i = 0; i < 8; i++) {
            x = rand.nextInt(length);
            password.append(PWD_CHAR[x]);
        }
        return password.toString();
    }

    /**
     * 将字节转换为M 并且没有M后缀
     *
     * @param str
     * @return
     */
    public static String formateStorageToMAndNoSuffix(String str) {
        if (StringUtils.isEmpty(str) || (!StringUtils.isNumeric(str))) {
            return "";
        }

        Double length = Double.valueOf(str);
        ;
        Double dl = null;
        BigDecimal b = null;
        String value = "";

        b = new BigDecimal((length / STORAGE_M));
        dl = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        value = String.format("%.2f", dl);

        // 如果有小数点去除尾巴上的0
        while (value.indexOf(".") > 0 && value.endsWith("0")) {
            value = value.substring(0, value.length() - 1);
        }

        if (value.endsWith(".")) {
            value = value.substring(0, value.length() - 1);
        }

        return value;
    }

    /**
     * 根据email地址获取domain信息
     *
     * @param email email地址
     * @return String domain信息
     */
    public static String getDomainByEmail(String email) {
        if (!validEmail(email)) {
            return "";
        }
        int index = email.indexOf('@');
        return email.substring(index + 1);
    }

    /**
     * check一个字符串是否是email
     *
     * @param email email地址
     * @return boolean 输入数据是否是email地址，true 是， false不是
     */
    public static boolean validEmail(String email) {
        if (StringUtils.isBlank(email)) {
            return false;
        }

        Pattern pattern = Pattern.compile(EMAIL_REGEXPATTENSTR);
        Matcher matcher = pattern.matcher(email);
        if (matcher.matches()) {
            return true;
        }
        return false;
    }

}
