package com.autoserve.abc.service.biz.impl.banel;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.MonthReportDO;
import com.autoserve.abc.dao.intf.MonthReportDao;
import com.autoserve.abc.service.biz.intf.banel.MonthRptService;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.service.biz.result.ListResult;
import com.autoserve.abc.service.biz.result.PageResult;
import com.autoserve.abc.service.biz.result.PlainResult;

@Service
public class MonthRptServiceImpl implements MonthRptService {

	@Resource
	private MonthReportDao monthReportDao;

	@Override
	public PageResult<MonthReportDO> queryListByPage(PageCondition pageCondition) {

		PageResult<MonthReportDO> result = new PageResult<MonthReportDO>(
				pageCondition.getPage(), pageCondition.getPageSize());

		int count = this.monthReportDao.countList();
		if (count > 0) {
			result.setTotalCount(count);
			result.setData(this.monthReportDao.findListByPage(pageCondition));
		}
		return result;
	}

	@Override
	public BaseResult removeRpt(Integer id) {
		this.monthReportDao.delete(id);
		return new BaseResult();
	}

	@Override
    public BaseResult createRpt(MonthReportDO banel) {
		List<MonthReportDO> list=this.monthReportDao.findListBySearchParam(banel,new PageCondition());
		if(list!=null&&list.size()>0){
			return new BaseResult(false,0,"该月度数据已存在，不可重复添加！");
		};
        this.monthReportDao.insert(banel);
        return new BaseResult();
    }
	
	@Override
    public PlainResult<MonthReportDO> queryById(int id) {
        PlainResult<MonthReportDO> result = new PlainResult<MonthReportDO>();
        result.setData(this.monthReportDao.findById(id));
        return result;
    }
	
	@Override
    public BaseResult modifyRpt(MonthReportDO banel) {
        this.monthReportDao.update(banel);
        return new BaseResult();
    }
	
	@Override
	public ListResult<MonthReportDO> queryListByYear(String year){
		ListResult<MonthReportDO> result = new ListResult<MonthReportDO>();
		result.setData(this.monthReportDao.findListByYear(year));
		return result;
	}
	
	/**
     * 根据条件查询
     * @param monthReportDO
     * @param pageCondition
     * @return
     */
	@Override
	public PageResult<MonthReportDO> queryListBySearchParam( MonthReportDO monthReportDO,
             PageCondition pageCondition){
		PageResult<MonthReportDO> pageResult = new PageResult<MonthReportDO>(pageCondition);
		int count = this.monthReportDao.countListBySearchParam(monthReportDO);
        if (count > 0) {
	        pageResult.setTotalCount(count);
	        pageResult.setData(this.monthReportDao.findListBySearchParam(monthReportDO, pageCondition));
        }
        return pageResult;
	}
}
