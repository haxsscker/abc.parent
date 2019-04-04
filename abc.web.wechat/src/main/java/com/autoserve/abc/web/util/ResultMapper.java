package com.autoserve.abc.web.util;

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
    public static <T> JsonBaseVO toBaseVO(BaseResult baseResult) {
        JsonBaseVO result = new JsonBaseVO();
        result.setSuccess(baseResult.isSuccess());
        result.setMessage(getLocalMessage(baseResult));

        return result;
    }

    public static <T> JsonPlainVO<T> toPlainVO(PlainResult<T> plainResult) {
        JsonPlainVO<T> result = new JsonPlainVO<T>();
        result.setSuccess(plainResult.isSuccess());
        result.setMessage(getLocalMessage(plainResult));
        result.setData(plainResult.getData());
        return result;
    }

    public static <T> JsonListVO<T> toListVO(ListResult<T> listResult) {
        JsonListVO<T> result = new JsonListVO<T>();
        result.setSuccess(listResult.isSuccess());
        result.setMessage(getLocalMessage(listResult));
        result.setRows(listResult.getData());

        return result;
    }

    public static <T> JsonPageVO<T> toPageVO(PageResult<T> pageResult) {
        JsonPageVO<T> result = new JsonPageVO<T>();
        result.setSuccess(pageResult.isSuccess());
        result.setMessage(getLocalMessage(pageResult));
        result.setRows(pageResult.getData());
        result.setTotal(pageResult.getTotalCount());

        return result;
    }

    /**
     * 将service层的分页结果 转换成 封装的Json结果 <br>
     *
     * @param pageResult 分页结果
     * @param convertedRows 转换后用于展示的结果
     * @return JsonPageVO<T> 封装的Json结果
     */
    public static <T, R> JsonPageVO<R> toPageVO(PageResult<T> pageResult, List<R> convertedRows) {
        JsonPageVO<R> result = new JsonPageVO<R>();
        result.setSuccess(pageResult.isSuccess());
        result.setMessage(getLocalMessage(pageResult));
        result.setRows(convertedRows);
        result.setTotal(pageResult.getTotalCount());

        return result;
    }

    private static String getLocalMessage(BaseResult br) {
        return ErrorMessageUtil.getLocalMessage(br.getMessage());
    }

}
