package com.autoserve.abc.service.biz.enums;

/**
 * 上传文件所属模块二级类别
 * 
 * @author RJQ 2014/12/25 13:44.
 */
public enum FileUploadSecondaryClass {

    /**
     * 影像资料
     */
    IMAGE_DATA(1, "其他"),
    GUA_DATA(2, "担保资料"),
    QUA_DATA(3, "实地资料"),
    SPOT_DATA(4, "资质资料"),
    SAFE_DATA(5, "风控资料"),
    POST_DATA(6, "贷后资料");

    FileUploadSecondaryClass(int type, String des) {
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

    public static FileUploadSecondaryClass valueOf(Integer type) {
        for (FileUploadSecondaryClass classType : values()) {
            if (type != null && classType.type == type) {
                return classType;
            }
        }

        return null;
    }
}
