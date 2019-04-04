package com.autoserve.abc.web.util;

import org.springframework.context.NoSuchMessageException;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

public abstract class ErrorMessageUtil {
    private static final String                          DEFAULT_BUNDLE_NAME = "abc-web-i18n";

    private static ReloadableResourceBundleMessageSource messageSource;

    static {
        messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setDefaultEncoding("UTF-8");
        messageSource.setBasename(DEFAULT_BUNDLE_NAME);
    }

    public static String getLocalMessage(String originMessage) {
        String result = null;
        try {
            String code = originMessage.trim().replaceAll("\\s+", "_");
            result = messageSource.getMessage(code, null, null);
        } catch (NoSuchMessageException e) {
            //ignore
        }

        if (result == null) {
            result = originMessage;
        }

        return result;
    }
}
