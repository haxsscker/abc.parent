package com.autoserve.abc.service.biz.impl.banel;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.BanelDO;
import com.autoserve.abc.dao.intf.BanelDao;
import com.autoserve.abc.service.biz.intf.banel.BanelService;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.service.biz.result.ListResult;
import com.autoserve.abc.service.biz.result.PageResult;
import com.autoserve.abc.service.biz.result.PlainResult;

@Service
public class BanelServiceImpl implements BanelService {

	@Resource
	private BanelDao banelDao;

	@Override
	public PageResult<BanelDO> queryListByPage(PageCondition pageCondition) {

		PageResult<BanelDO> result = new PageResult<BanelDO>(
				pageCondition.getPage(), pageCondition.getPageSize());

		int count = this.banelDao.countList();
		if (count > 0) {
			result.setTotalCount(count);
			result.setData(this.banelDao.findListByPage(pageCondition));
		}
		return result;
	}

	@Override
	public BaseResult removeBanel(Integer id) {
		this.banelDao.delete(id);
		return new BaseResult();
	}

	@Override
    public BaseResult createBanel(BanelDO banel) {
        this.banelDao.insert(banel);
        return new BaseResult();
    }
	
	@Override
    public PlainResult<BanelDO> queryById(int id) {
        PlainResult<BanelDO> result = new PlainResult<BanelDO>();
        result.setData(this.banelDao.findById(id));
        return result;
    }
	
	@Override
    public BaseResult modifyBanel(BanelDO banel) {
        this.banelDao.update(banel);
        return new BaseResult();
    }
	
	@Override
	public ListResult<BanelDO> queryListByGroup(String groupCode) {

		ListResult<BanelDO> result = new ListResult<BanelDO>();

		try {
			result.setData(this.banelDao.findListByGroupCode(groupCode));
		} catch (Exception e) {
			e.printStackTrace();
			result.setSuccess(false);
		}

		return result;
	}
	
}
