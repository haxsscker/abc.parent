package com.autoserve.abc.web.module.screen.score.json;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.UserDO;
import com.autoserve.abc.service.biz.convert.UserConverter;
import com.autoserve.abc.service.biz.entity.User;
import com.autoserve.abc.service.biz.enums.EntityState;
import com.autoserve.abc.service.biz.intf.user.UserService;
import com.autoserve.abc.service.biz.result.PageResult;
import com.autoserve.abc.web.convert.UserVOConverter;
import com.autoserve.abc.web.module.screen.BaseController;
import com.autoserve.abc.web.vo.user.UserVO;

public class GetScoreListExcel extends BaseController {
	@Autowired
	private UserService userService;

	public void execute(ParameterParser params) {
		UserDO userDO = new UserDO();
		String[] fieldName = { "客户名称", "真实姓名", "总积分", "最后调整时间" };

		String userName = params.getString("cst_user_name");
		String userRealName = params.getString("cst_real_name");

		userDO.setUserName(userName);
		userDO.setUserRealName(userRealName);
		userDO.setUserState(EntityState.STATE_ENABLE.getState());

		PageResult<UserDO> pageResult = userService.queryList(userDO,
				new PageCondition(1, Integer.MAX_VALUE));
		List<User> list = UserConverter.convertList(pageResult.getData());
		List<UserVO> userVOList = UserVOConverter.convertToList(list);
		List<List<String>> fieldData = new ArrayList<List<String>>();
		for (UserVO user : userVOList) {
			List<String> temp = new ArrayList<String>();
			temp.add(user.getUserName());
			temp.add(user.getUserRealName());
			temp.add(user.getUserScore() + "");
			temp.add(user.getUserScoreLastmodifytime());
			fieldData.add(temp);
		}
		this.ExportExcel(Arrays.asList(fieldName), fieldData, "客户积分表.xls");
	}
}
