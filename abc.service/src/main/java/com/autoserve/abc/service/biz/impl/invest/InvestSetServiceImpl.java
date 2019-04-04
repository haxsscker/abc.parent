package com.autoserve.abc.service.biz.impl.invest;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.autoserve.abc.dao.dataobject.InvestSetDO;
import com.autoserve.abc.dao.intf.InvestSetDao;
import com.autoserve.abc.service.biz.convert.InvestSetConverter;
import com.autoserve.abc.service.biz.entity.InvestSet;
import com.autoserve.abc.service.biz.intf.invest.InvestSetService;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.service.biz.result.ListResult;
import com.autoserve.abc.service.biz.result.PlainResult;

/**
 * 类InvestSetServiceImpl.java的实现描述：TODO 类实现描述
 * 
 * @author liuwei 2015年3月10日 下午5:14:17
 */
@Service
public class InvestSetServiceImpl implements InvestSetService {

    @Resource
    private InvestSetDao investSetDao;

    @Override
    public PlainResult<Integer> createInvest(InvestSet pojo) {
    	pojo.setCreateTime(new Date());
    	pojo.setModifyTime(new Date());
        this.investSetDao.insert(InvestSetConverter.toInvestSetDO(pojo));
        return new PlainResult<Integer>();
    }

    @Override
    public PlainResult<Integer> modifyInvest(InvestSet pojo) {
    	pojo.setModifyTime(new Date());
        this.investSetDao.updateByPrimaryKeySelective(InvestSetConverter.toInvestSetDO(pojo));
        return new PlainResult<Integer>();
    }

    @Override
    public ListResult<InvestSet> queryInvest(InvestSet pojo) {

        ListResult<InvestSet> result = new ListResult<InvestSet>();

        List<InvestSet> list = new ArrayList<InvestSet>();
        List<InvestSetDO> listDO = new ArrayList<InvestSetDO>();
        listDO = this.investSetDao.findiInvestSet(InvestSetConverter.toInvestSetDO(pojo));
        for (InvestSetDO investSetDO : listDO) {
            list.add(InvestSetConverter.toInvestSet(investSetDO));
        }
        result.setData(list);
        return result;

    }

	@Override
	public BaseResult removeInvestById(Integer id) {
		this.investSetDao.deleteByPrimaryKey(id);
		return new BaseResult();
	}
}
