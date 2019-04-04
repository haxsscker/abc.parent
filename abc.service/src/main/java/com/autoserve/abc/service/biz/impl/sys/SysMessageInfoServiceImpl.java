package com.autoserve.abc.service.biz.impl.sys;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.SysMessageInfoDO;
import com.autoserve.abc.dao.dataobject.UserDO;
import com.autoserve.abc.dao.intf.MessageInfoDao;
import com.autoserve.abc.dao.intf.UserDao;
import com.autoserve.abc.service.biz.intf.sys.SysMessageInfoService;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.service.biz.result.PageResult;

@Service
public class SysMessageInfoServiceImpl implements SysMessageInfoService {
	
	@Resource
	private MessageInfoDao sysMessageInfoDao;
	
	@Resource
	private UserDao userDao;
	
	@Override
	public PageResult<SysMessageInfoDO> queryByUserId(int userId,
			PageCondition pageCondition) {
		PageResult<SysMessageInfoDO> result = new PageResult<SysMessageInfoDO>(pageCondition);
		int count = this.sysMessageInfoDao.countByUserId(userId);
		if(count>0){
			List<SysMessageInfoDO> list = this.sysMessageInfoDao.selectByUserId(userId, pageCondition);
			result.setData(list);
			result.setTotalCount(count);
		}
		return result;
	}

	@Override
	public BaseResult removeById(int messageId) {
		this.sysMessageInfoDao.deleteById(messageId);
		return new BaseResult();
	}
	
    @Override
    public int deleteById(int messageId) {
        return this.sysMessageInfoDao.deleteByPrimaryKey(messageId);
    }

	@Override
	public PageResult<SysMessageInfoDO> queryBytoUserId(int userId,
			PageCondition pageCondition) {
		PageResult<SysMessageInfoDO> result = new PageResult<SysMessageInfoDO>(pageCondition);
		int count = this.sysMessageInfoDao.countByToUserId(userId);
		if(count>0){
			List<SysMessageInfoDO> list = this.sysMessageInfoDao.selectByToUserId(userId, pageCondition);
			result.setData(list);
			result.setTotalCount(count);
		}
		return result;
	}

	@Override
	public BaseResult createMessage(SysMessageInfoDO sysMessageInfoDO) {
		sysMessageInfoDO.setSysMessageState("0");
		sysMessageInfoDO.setSysDelSign("0");
		sysMessageInfoDO.setSysMessageDate(new Date());
		sysMessageInfoDO.setSysRead("0");
		UserDO userDO = userDao.findById(sysMessageInfoDO.getSysToUser());
		sysMessageInfoDO.setSysToUserName(userDO.getUserName());
		this.sysMessageInfoDao.insert(sysMessageInfoDO);
		return new BaseResult();
	}
	
	@Override
	public BaseResult createMessageForRibao(SysMessageInfoDO sysMessageInfoDO) {
		sysMessageInfoDO.setSysMessageState("0");
		sysMessageInfoDO.setSysDelSign("0");
		sysMessageInfoDO.setSysMessageDate(new Date());
		sysMessageInfoDO.setSysRead("0");
		this.sysMessageInfoDao.insert(sysMessageInfoDO);
		return new BaseResult();
	}
	
    @Override
    public int updateMessage(SysMessageInfoDO sysMessageInfoDo) {
        return this.sysMessageInfoDao.updateByPrimaryKeySelective(sysMessageInfoDo);
    }

    @Override
    public int queryNotReadMessageCount(int userId) {

        return this.sysMessageInfoDao.countNotReadByUserId(userId);
    }

    @Override
    public PageResult<SysMessageInfoDO> queryMessage(SysMessageInfoDO message, PageCondition pageCondition) {
        PageResult<SysMessageInfoDO> result = new PageResult<SysMessageInfoDO>(pageCondition);
        int count = this.sysMessageInfoDao.countMessageByDo(message, pageCondition);
        if (count > 0) {
            List<SysMessageInfoDO> list = this.sysMessageInfoDao.queryMessageByDo(message, pageCondition);
            result.setData(list);
            result.setTotalCount(count);
        }
        return result;
    }

    @Override
    public SysMessageInfoDO queryMesageById(Integer messageid) {
        SysMessageInfoDO sysMessageInfoDO = new SysMessageInfoDO();
        sysMessageInfoDO = sysMessageInfoDao.selectByPrimaryKey(messageid);
        return sysMessageInfoDO;
    }

}
