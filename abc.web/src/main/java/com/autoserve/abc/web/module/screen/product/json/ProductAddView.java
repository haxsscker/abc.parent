package com.autoserve.abc.web.module.screen.product.json;

import java.util.List;

import javax.annotation.Resource;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.dataresolver.Param;
import com.alibaba.fastjson.JSON;
import com.autoserve.abc.service.biz.entity.ProductRateInfo;
import com.autoserve.abc.service.biz.intf.product.ProductRateService;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.web.convert.ProductRateInfoVOConverter;
import com.autoserve.abc.web.util.ResultMapper;
import com.autoserve.abc.web.vo.JsonBaseVO;
import com.autoserve.abc.web.vo.product.ProductRateInfoVO;

/**
 * 产品添加功能
 * 
 * @author weihuimin 2014/12/15
 */
public class ProductAddView {

    @Resource
    private ProductRateService productRateService;

    public JsonBaseVO execute(ParameterParser params, @Param("main") String main, @Param("rate") String rate,
                              @Param("actionMode") String actionMode) {

        ProductRateInfoVO mainVO = JSON.parseObject(main, ProductRateInfoVO.class);
        List<ProductRateInfoVO> rateVOList = JSON.parseArray(rate, ProductRateInfoVO.class);

        ProductRateInfo mainProductRateInfo = ProductRateInfoVOConverter.toProductRateInfo(mainVO);
        List<ProductRateInfo> rateList = ProductRateInfoVOConverter.toEntityList(rateVOList);

        if (actionMode != null && "2".equals(actionMode)) {//编辑后保存

            if (null != mainProductRateInfo) {
                this.productRateService.deleteByProductId(mainProductRateInfo.getProductId());
            }
            BaseResult baseResult = this.productRateService.insertRateUpdateInfo(mainProductRateInfo, rateList);

            return ResultMapper.toBaseVO(baseResult);

        }

        BaseResult baseResult = this.productRateService.insertProduct(mainProductRateInfo, rateList);

        return ResultMapper.toBaseVO(baseResult);
    }

}
