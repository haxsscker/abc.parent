package com.autoserve.abc.dao.common.password;

import java.util.Properties;

import javax.security.auth.callback.PasswordCallback;

public class DBPasswordCallback extends PasswordCallback {
    private static final long   serialVersionUID = -2034820150051613774L;
    private static final String PASSWORD_KEY     = "password";
    private String              url;
    private Properties          properties;

    public DBPasswordCallback(String prompt, boolean echoOn) {
        super(prompt, echoOn);
    }

    public DBPasswordCallback() {
        this("db pwd", true);
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Properties getProperties() {
        return properties;
    }

    public void setProperties(Properties properties) {
        this.properties = properties;
        String pwd = properties.getProperty(PASSWORD_KEY);

        try {
            setPassword(DBPasswordUtil.decode(pwd).toCharArray());
        } catch (Exception e) {
            setPassword(pwd.toCharArray());
        }
    }
}
