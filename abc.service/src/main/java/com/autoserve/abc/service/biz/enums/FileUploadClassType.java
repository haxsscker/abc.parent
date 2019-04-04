package com.autoserve.abc.service.biz.enums;

/**
 * 上传文件所属模块大类别
 *
 * @author RJQ 2014/12/25 13:44.
 */
public enum FileUploadClassType {

    /**
     * 借款
     */
    LOAN(1, "借款"),
    DOWN_LINE_RECHARGE(2, "线下充值审核"),
    FRIEND_LINK(2, "友情链接上传");

    FileUploadClassType(int type, String des) {
        this.type = type;
        this.des = des;
    }

    public int getType() {
        return type;
    }

    public String getDes() {
        return des;
    }

    public final int    type;
    public final String des;

    public static FileUploadClassType valueOf(Integer type) {
        for (FileUploadClassType classType : values()) {
            if (type != null && classType.type == type) {
                return classType;
            }
        }

        return null;
    }
}
