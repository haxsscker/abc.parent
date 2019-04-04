package com.autoserve.abc.service.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DataAccessException;

import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.service.biz.result.CommonResultCode;
import com.autoserve.abc.service.biz.result.IErrorCode;
import com.autoserve.abc.service.biz.result.ListResult;
import com.autoserve.abc.service.biz.result.PageResult;
import com.autoserve.abc.service.biz.result.PlainResult;
import com.autoserve.abc.service.exception.BusinessException;

/**
 * Service层异常拦截
 */
@Aspect
@Order(Ordered.HIGHEST_PRECEDENCE)
@SuppressWarnings("rawtypes")
public class ExceptionHandleAspect {
    private final Logger logger = LoggerFactory.getLogger(ExceptionHandleAspect.class);

    @Around("within(com.autoserve.abc.service.biz..*)")
    public Object handleException(ProceedingJoinPoint joinPoint) {
        Object result = null;
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();

        Class returnType = signature.getReturnType();
        try {
            result = joinPoint.proceed();
        } catch (Throwable ex) {
            //log error msg
            logError(signature.toString(), ex);

            //translate exception to BaseResult
            result = ExceptionTranslator.translate(ex, returnType);
        }

        return result;
    }

    private void logError(String method, Throwable ex) {
        logger.error("exception throwed by method: {}, error is {}", method, ex.getMessage());
        if (logger.isDebugEnabled()) {
            logger.debug(method, ex);
        }
    }

    private static class ExceptionTranslator {
        public static Object translate(Throwable ex, Class returnType) {
            IErrorCode rc = CommonResultCode.DEFAULT_INTERNAL_ERROR;
            //String args = ex.getMessage();

            if (ex instanceof DataAccessException) {
                rc = CommonResultCode.ERROR_DB;
            } else if (ex instanceof BusinessException) {
                return ResultFactory.createResult(CommonResultCode.BIZ_ERROR, returnType, ex.getMessage());
            }

            return ResultFactory.createResult(rc, returnType);
        }
    }

    private static class ResultFactory {
        static Object createResult(IErrorCode rc, Class returnType, Object... args) {
            BaseResult result = null;
            if (PlainResult.class.equals(returnType)) {
                result = new PlainResult();
            } else if (PageResult.class.equals(returnType)) {
                result = new PageResult(1, 20);
            } else if (ListResult.class.equals(returnType)) {
                result = new ListResult();
            } else {
                result = new BaseResult();
            }

            result.setErrorMessage(rc, args);
            return result;
        }
    }
}
