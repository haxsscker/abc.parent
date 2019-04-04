package com.autoserve.abc.web.module.screen.common;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.autoserve.abc.web.vo.JsonBaseVO;

/**
 * 所有的异步请求的处理类
 * Created by pp on 2014/12/3.
 */
public class AsyncRequestHandler {

    /**
     * 所有异步请求的处理类  params中参数有
     * tbName  查询的表名 colName 查询的列名  vWhere 匹配条件  message 错误提示条件
     * 前端写JS只需要这四个条件
     * SycData方法会把输入框的信息封装入 vData 字段
     * 前端传过来的数据典型数据如下
     * {"tbName":"abc_role","colName":"role_name","vData":"管理员","vWhere":"!=","message":"管理员名称不能重复"}
     * @param params
     * @return
     */
    public JsonBaseVO execute(ParameterParser params){
        String tbName=params.getString("tbName");
        String colName=params.getString("colName");
        String vData=params.getString("vData");
        String vWhere=params.getString("vWhere");
        String message=params.getString("message");
        JsonBaseVO vo=new JsonBaseVO();
        if(tbName==null||colName==null||vData==null||vWhere==null||message==null){
            vo.setSuccess(false);
            vo.setMessage("数据校验异常");
        }else{

        }
        return null;
    }
}
