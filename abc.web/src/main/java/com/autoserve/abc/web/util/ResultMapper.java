package com.autoserve.abc.web.util;

import java.util.Collections;
import java.util.List;

import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.service.biz.result.ListResult;
import com.autoserve.abc.service.biz.result.PageResult;
import com.autoserve.abc.service.biz.result.PlainResult;
import com.autoserve.abc.web.vo.JsonBaseVO;
import com.autoserve.abc.web.vo.JsonListVO;
import com.autoserve.abc.web.vo.JsonPageVO;
import com.autoserve.abc.web.vo.JsonPlainVO;

public abstract class ResultMapper {

    /**
     * BaseResult 转换成 JsonBaseVO
     */
    public static <T> JsonBaseVO toBaseVO(BaseResult baseResult) {
        JsonBaseVO result = new JsonBaseVO();
        result.setSuccess(baseResult.isSuccess());
        result.setMessage(getLocalMessage(baseResult));

        return result;
    }

    /**
     * PlainResult 转换成 JsonPlainVO
     */
    public static <T> JsonPlainVO<T> toPlainVO(PlainResult<T> plainResult) {
        JsonPlainVO<T> result = new JsonPlainVO<T>();
        result.setSuccess(plainResult.isSuccess());
        result.setMessage(getLocalMessage(plainResult));
        result.setData(plainResult.getData());
        return result;
    }

    /**
     * ListResult 转换成 JsonListVO
     */
    @SuppressWarnings("unchecked")
    public static <T> JsonListVO<T> toListVO(ListResult<T> listResult) {
        JsonListVO<T> result = new JsonListVO<T>();
        result.setSuccess(listResult.isSuccess());
        result.setMessage(getLocalMessage(listResult));
        result.setRows(listResult.getData() == null ? Collections.EMPTY_LIST : listResult.getData());

        return result;
    }

    /**
     * PageResult 转换成 JsonPageVO
     */
    @SuppressWarnings("unchecked")
    public static <T> JsonPageVO<T> toPageVO(PageResult<T> pageResult) {
        JsonPageVO<T> result = new JsonPageVO<T>();
        result.setSuccess(pageResult.isSuccess());
        result.setMessage(getLocalMessage(pageResult));
        result.setTotal(pageResult.getTotalCount());
        result.setRows(pageResult.getData() == null ? Collections.EMPTY_LIST : pageResult.getData());

        return result;
    }

    /**
     * PageResult 转换成 JsonPageVO
     *
     * @param pageResult 分页结果
     * @param convertedRows 转换后用于展示的结果
     * @return JsonPageVO<T> 封装的Json结果
     */
    public static <T, R> JsonPageVO<R> toPageVO(PageResult<T> pageResult, List<R> convertedRows) {
        JsonPageVO<R> result = new JsonPageVO<R>();
        result.setSuccess(pageResult.isSuccess());
        result.setMessage(getLocalMessage(pageResult));
        result.setTotal(pageResult.getTotalCount());
        result.setRows(convertedRows);

        return result;
    }

    /**
     * 返回错误的JsonBaseVO信息
     */
    public static <T> JsonBaseVO toErrorBaseVO(String errorMsg) {
        JsonBaseVO result = new JsonBaseVO();
        result.setSuccess(false);
        result.setMessage(getLocalMessage(errorMsg));

        return result;
    }

    /**
     * 返回错误的JsonPlainVO信息
     */
    public static <T> JsonPlainVO<T> toErrorPlainVO(String errorMsg) {
        JsonPlainVO<T> result = new JsonPlainVO<T>();
        result.setSuccess(false);
        result.setMessage(getLocalMessage(errorMsg));
        return result;
    }

    /**
     * 返回错误的JsonListVO信息
     */
    @SuppressWarnings("unchecked")
    public static <T> JsonListVO<T> toErrorListVO(String errorMsg) {
        JsonListVO<T> result = new JsonListVO<T>();
        result.setSuccess(false);
        result.setMessage(getLocalMessage(errorMsg));
        result.setRows(Collections.EMPTY_LIST);

        return result;
    }

    /**
     * 返回错误的JsonPageVO信息
     */
    @SuppressWarnings("unchecked")
    public static <T> JsonPageVO<T> toErrorPageVO(String errorMsg) {
        JsonPageVO<T> result = new JsonPageVO<T>();
        result.setSuccess(false);
        result.setMessage(getLocalMessage(errorMsg));
        result.setRows(Collections.EMPTY_LIST);
        result.setTotal(0);

        return result;
    }

    private static String getLocalMessage(BaseResult br) {
        return ErrorMessageUtil.getLocalMessage(br.getMessage());
    }

    private static String getLocalMessage(String errorMsg) {
        return ErrorMessageUtil.getLocalMessage(errorMsg);
    }

}
