package com.autoserve.abc.service.biz.impl.cash;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.autoserve.abc.dao.dataobject.CardCityBaseDO;
import com.autoserve.abc.dao.intf.CardCityBaseDao;
import com.autoserve.abc.service.biz.intf.cash.CardCityBaseService;
import com.autoserve.abc.service.biz.result.ListResult;
import com.autoserve.abc.service.biz.result.PlainResult;

@Service
public class CardCityBaseServiceImpl implements CardCityBaseService {

    @Resource
    private CardCityBaseDao cardCityBaseDao;

    @Override
    public PlainResult<CardCityBaseDO> queryCardByCode(Integer code) {
        PlainResult<CardCityBaseDO> result = new PlainResult<CardCityBaseDO>();
        result.setData(this.cardCityBaseDao.selectByPrimaryKey(code));
        return result;
    }

	@Override
	public ListResult<CardCityBaseDO> queryCardByprovCode(Integer provCode) {
		 ListResult<CardCityBaseDO> result = new ListResult<CardCityBaseDO>();
		 List<CardCityBaseDO> cards = cardCityBaseDao.queryCardByProvcode(provCode);
		 result.setData(cards);
		 return result;
	}

	@Override
	public ListResult<CardCityBaseDO> queryAllCity() {
		 ListResult<CardCityBaseDO> result = new ListResult<CardCityBaseDO>();
		 List<CardCityBaseDO> cards = cardCityBaseDao.queryAllCity();
		 result.setData(cards);
		 return result;
	}


}
