package com.autoserve.abc.web.module.screen.product;

import java.util.List;

import javax.annotation.Resource;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Context;
import com.alibaba.fastjson.JSON;
import com.autoserve.abc.service.biz.entity.ProductRateInfo;
import com.autoserve.abc.service.biz.intf.product.ProductRateService;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.service.biz.result.ListResult;
import com.autoserve.abc.web.convert.ProductRateInfoVOConverter;
import com.autoserve.abc.web.vo.JsonBaseVO;
import com.autoserve.abc.web.vo.product.ProductRateInfoVO;
/**产品编辑
 * @author weihuimin
 */
public class ProductEditView {

    @Resource
    private ProductRateService productRateService;

    public JsonBaseVO execute(Context context, ParameterParser params) {

        JsonBaseVO vo = new JsonBaseVO();
        Integer prProductId = params.getInt("pdo_product_id");
        Integer actionMode = params.getInt("actionMode");
        if (1 == actionMode) {
            ListResult<ProductRateInfo> result = this.productRateService.queryByProductId(prProductId);
            context.put("list", result.getData());
            context.put("productName", result.getData().get(0).getProductName());
            context.put("productMark", result.getData().get(0).getProductMark());
            context.put("pdo_product_id", prProductId);
        } else if (2 == actionMode) {
            String main = params.getString("main");
            String rate = params.getString("rate");
            ProductRateInfoVO mainVO = JSON.parseObject(main, ProductRateInfoVO.class);
            List<ProductRateInfoVO> rateVOList = JSON.parseArray(rate, ProductRateInfoVO.class);

            ProductRateInfo mainProductRateInfo = ProductRateInfoVOConverter.toProductRateInfo(mainVO);
            List<ProductRateInfo> rateList = ProductRateInfoVOConverter.toEntityList(rateVOList);
            BaseResult baseResult = this.productRateService.insertRateUpdateInfo(mainProductRateInfo, rateList);
            vo.setSuccess(true);
            vo.setMessage(baseResult.getMessage());
            return vo;
        }
        return new JsonBaseVO();
    }
}
