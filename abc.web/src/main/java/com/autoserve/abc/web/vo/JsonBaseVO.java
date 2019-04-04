package com.autoserve.abc.web.vo;

/**
 * JsonBaseVO
 *
 * @author Kevin Fan
 * @since 2013-11-12 下午4:04:52
 */
public class JsonBaseVO {
    public static final JsonBaseVO SUCCESS = new JsonBaseVO(true, "successful");

    /**
     * 标识本次调用是否返回
     */
    private boolean                success = true;                              //defaul is true

    /**
     * 输出信息
     */
    private String                 message;

    /**
     * 跳转至的url
     */
    private String                 redirectUrl;

    public JsonBaseVO() {
        this.success = true;
        this.message = "";
        this.redirectUrl = "";
    }

    public JsonBaseVO(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getRedirectUrl() {
        return redirectUrl;
    }

    public void setRedirectUrl(String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }
}
