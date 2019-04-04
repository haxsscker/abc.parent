package com.autoserve.abc.service.biz.impl.sys;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.SysMessageInfoDO;
import com.autoserve.abc.dao.dataobject.SysMessageReplyDO;
import com.autoserve.abc.dao.dataobject.UserDO;
import com.autoserve.abc.dao.intf.MessageInfoDao;
import com.autoserve.abc.dao.intf.SysMessageReplyDao;
import com.autoserve.abc.dao.intf.UserDao;
import com.autoserve.abc.service.biz.intf.sys.SysMessageReplyService;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.service.biz.result.PageResult;

@Service
public class SysMessageReplyServiceImpl implements SysMessageReplyService {

	@Resource
	private SysMessageReplyDao sysMessageReplyDao;
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private MessageInfoDao messageInfoDao;

	@Override
	public PageResult<SysMessageReplyDO> queryByMessageId(int messageId,
			PageCondition pageCondition) {
		PageResult<SysMessageReplyDO> pageResult = new PageResult<SysMessageReplyDO>(
				pageCondition);

		int count = this.sysMessageReplyDao.countByMessageId(messageId,
				pageCondition);
		if (count > 0) {
			List<SysMessageReplyDO> list = this.sysMessageReplyDao
					.selectByMessageId(messageId, pageCondition);
			pageResult.setData(list);
			pageResult.setTotalCount(count);
		}
		return pageResult;
	}

	@Override
	public PageResult<SysMessageReplyDO> queryByUserId(int userId,
			PageCondition pageCondition) {
		PageResult<SysMessageReplyDO> pageResult = new PageResult<SysMessageReplyDO>(
				pageCondition);

		int count = this.sysMessageReplyDao
				.countByUserId(userId, pageCondition);
		if (count > 0) {
			List<SysMessageReplyDO> list = this.sysMessageReplyDao
					.selectByUserId(userId, pageCondition);
			pageResult.setData(list);
			pageResult.setTotalCount(count);
		}
		return pageResult;
	}

	@Override
	@Transactional
	public BaseResult createMessage(SysMessageReplyDO sysMessageReplyDO) {
		BaseResult result = new BaseResult();
		//只允许回复发给自己的消息
		SysMessageInfoDO messageInfoDO = messageInfoDao.selectByPrimaryKey(sysMessageReplyDO.getSysMessageId());
		if(!messageInfoDO.getSysToUser().equals(sysMessageReplyDO.getSysUserId())){
			result.setSuccess(false);
			result.setMessage("非法操作，回复其他人的消息");
			return result;
		}
		//已经回复的不允许再回复
		if("1".equals(messageInfoDO.getSysMessageState())){
			result.setSuccess(false);
			result.setMessage("只能回复一次");
			return result;
		}
		//更新messageInfo表 是否回复字段
		SysMessageInfoDO msgInfoDOupdate = new SysMessageInfoDO();
		msgInfoDOupdate.setSysMessageId(messageInfoDO.getSysMessageId());
		msgInfoDOupdate.setSysMessageState("1"); //  设置已回复
		messageInfoDao.updateByPrimaryKeySelective(msgInfoDOupdate);
		//回复信息插入数据库
		UserDO userDO = userDao.findById(sysMessageReplyDO.getSysUserId());
		sysMessageReplyDO.setSysUserName(userDO.getUserName());
		sysMessageReplyDO.setSysReplyDate(new Date());
		sysMessageReplyDao.insert(sysMessageReplyDO);
		return result;
	}

	@Override
	public PageResult<SysMessageReplyDO> queryAllReplyByMesageid(
			Integer messageid, PageCondition page) {

		PageResult<SysMessageReplyDO> result = new PageResult<SysMessageReplyDO>(
				page);
		try {
			List<SysMessageReplyDO> list = this.sysMessageReplyDao
					.selectByMessageId(messageid, page);
			result.setData(list);
			result.setMessage("留言信息查询成功.");
		} catch (Exception e) {
			result.setMessage("留言信息查询失败.");
		}
		return result;
	}

}
