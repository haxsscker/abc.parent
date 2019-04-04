package com.autoserve.abc.web.util.webx.pipeline;

import static com.alibaba.citrus.turbine.util.TurbineUtil.getTurbineRunData;

import java.net.URLDecoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.service.pipeline.PipelineContext;
import com.alibaba.citrus.service.pipeline.support.AbstractValve;
import com.alibaba.citrus.turbine.TurbineRunDataInternal;
import com.autoserve.abc.dao.dataobject.UserDO;
import com.autoserve.abc.service.biz.entity.User;
import com.autoserve.abc.service.biz.intf.user.UserService;
import com.autoserve.abc.web.helper.DeployConfigService;

/**
 * 获取登录用户信息
 */
public class UserStateInfoValve extends AbstractValve {

	private static final Logger log = LoggerFactory
			.getLogger(UserStateInfoValve.class);

	@Autowired
	private UserService userService;

	@Autowired
	private DeployConfigService deployConfigService;

	@Autowired
	private HttpServletRequest request;

	@Autowired
	private HttpServletResponse response;

	@Autowired
	private HttpSession session;

	@Override
	public void invoke(PipelineContext pipelineContext) throws Exception {
		try {
			TurbineRunDataInternal rundata = (TurbineRunDataInternal) getTurbineRunData(request);
				// 用户是否关闭
		      if ((URLDecoder.decode(this.request.getRequestURI(), "utf-8").startsWith("/account/")))
		      {
		        String loginUrl = this.deployConfigService.getLoginUrl(this.request);
		        
		        User user = (User)this.session.getAttribute("user");

		        if ((user == null) || (user.getUserId() == null))
		        {
		        	rundata.setRedirectLocation(loginUrl);
		        }
		        else
		        {
		        	// 用户是否关闭
		        	UserDO userDo = userService.findById(user.getUserId()).getData();
		        	if (null == userDo || 1 != userDo.getUserState())
		        	{
		        		rundata.setRedirectLocation(loginUrl);
		        	}
		        }
		      }
		} catch (Exception e) {
			log.warn(e.getMessage(), e);
		} finally {
			pipelineContext.invokeNext();
		}
	}
}
