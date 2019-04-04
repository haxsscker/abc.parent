package com.autoserve.abc.web.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.context.NoSuchMessageException;

import java.util.Locale;

/**
 * I18NMessageSource
 * 
 * @author Kevin Fan
 * @since 2013-12-9 下午2:28:41
 */
public class I18NMessageSource implements MessageSourceAware {
    private final Logger  logger = LoggerFactory.getLogger(getClass());

    private MessageSource messageSource;

    @Override
    public void setMessageSource(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    public String getLocalMessage(String textCode, Object... args) {
        return getLocalMessage(textCode, null, args);
    }

    public String getLocalMessage(String textCode, Locale local, Object... args) {
        String result = null;
        try {
            result = messageSource.getMessage(textCode, args, local);
        } catch (NoSuchMessageException e) {
            logger.warn(e.getMessage());
        }

        if (result == null) {
            result = String.format(textCode, args);
        }

        return result;
    }
}
