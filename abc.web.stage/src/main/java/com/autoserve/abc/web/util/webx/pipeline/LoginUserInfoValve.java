package com.autoserve.abc.web.util.webx.pipeline;

import static com.alibaba.citrus.turbine.util.TurbineUtil.getTurbineRunData;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.service.pipeline.PipelineContext;
import com.alibaba.citrus.service.pipeline.support.AbstractValve;
import com.alibaba.citrus.turbine.TurbineRunDataInternal;
import com.autoserve.abc.dao.dataobject.SessionRecordDO;
import com.autoserve.abc.dao.intf.SessionRecordDao;
import com.autoserve.abc.service.biz.entity.SysConfig;
import com.autoserve.abc.service.biz.entity.User;
import com.autoserve.abc.service.biz.enums.SysConfigEntry;
import com.autoserve.abc.service.biz.intf.authority.RoleService;
import com.autoserve.abc.service.biz.intf.sys.SysConfigService;
import com.autoserve.abc.service.biz.intf.user.UserService;
import com.autoserve.abc.service.biz.result.PlainResult;
import com.autoserve.abc.web.helper.DeployConfigService;
import com.autoserve.abc.web.util.QueryStringBuilder;

/**
 * 获取登录用户信息
 */
public class LoginUserInfoValve extends AbstractValve {

	private static final Logger log = LoggerFactory
			.getLogger(LoginUserInfoValve.class);

	@Autowired
	private UserService userService;

	@Autowired
	private RoleService roleService;

	@Autowired
	private DeployConfigService deployConfigService;

	@Autowired
	private HttpServletRequest request;

	@Autowired
	private HttpServletResponse response;

	@Autowired
	private HttpSession session;
	@Autowired
	private SysConfigService sysConfigService;
	@Autowired
	private SessionRecordDao sessionRecordDao;
	
	@Override
	public void invoke(PipelineContext pipelineContext) throws Exception {
		try {
			TurbineRunDataInternal rundata = (TurbineRunDataInternal) getTurbineRunData(request);
			// 查询站点是否关闭
			PlainResult<SysConfig> resultSysConfig = sysConfigService
					.querySysConfig(SysConfigEntry.SHUTDOWN_SITE);
			SysConfig sysConfig = (SysConfig)resultSysConfig.getData();
		      if ((sysConfig != null) && ("1".equals(sysConfig.getConfValue())) && (!(URLDecoder.decode(this.request.getRequestURI(), "utf-8").startsWith("/offSite/"))))
		      {
		        PlainResult sysConfigInfo = this.sysConfigService.querySysConfig(SysConfigEntry.SHUTDOWN_INFO);

		        rundata.getContext().put("shutdownInfo", ((SysConfig)sysConfigInfo.getData()).getConfValue());

		        rundata.forwardTo("/offSite").end();
		      }
		      else if ((URLDecoder.decode(this.request.getRequestURI(), "utf-8").startsWith("/account/")) || (URLDecoder.decode(this.request.getRequestURI(), "utf-8").startsWith("/apply/pledgeApply")) || (URLDecoder.decode(this.request.getRequestURI(), "utf-8").startsWith("/apply/securedApply")))
		      {
		    	//去掉记录session失效前url功能，解决了很多500错误!(现在session失效重新登录后，统一跳转到我的账户页面，不会再跳转到原页面)
		        String loginUrl = this.deployConfigService.getLoginUrl(this.request);//+ "?redirectUrl=" + URLEncoder.encode(getLocaleUrl(rundata), "utf-8");

		        User user = (User)this.session.getAttribute("user");
		        SessionRecordDO sessionRecordDO = null;
		        if(null != user){
//		        	String userName = (String) this.session.getAttribute("userName");
		        	sessionRecordDO = sessionRecordDao.findSessionBySessionId(session.getId());
//		        	sessionRecordDO = sessionRecordDao.findSessionByUserName(userName);
//		        	sessionRecordDO = sessionRecordDao.findSessionByUserName(user.getUserName());
		        }
//		        if ((user == null) || (user.getUserId() == null)||(null != sessionRecordDO && !session.getId().equals(sessionRecordDO.getSessionId())))
	        	if ((user == null) || (user.getUserId() == null)||(null == sessionRecordDO))
		        {
		          this.session.removeAttribute("user");
		          rundata.setRedirectLocation(loginUrl);
		        }

		      }
		      else if ((URLDecoder.decode(this.request.getRequestURI(), "utf-8").startsWith("/login/")) || (URLDecoder.decode(this.request.getRequestURI(), "utf-8").startsWith("/register/")))
		      {
//		        String httpsUrl = this.deployConfigService.getHttpsUrl(this.request);
//		        String _curScheme = this.request.getScheme() + ":";
//
//		        //System.out.println("request url:" + this.request.getRequestURL().toString() + "=" + this.request.getScheme());
//		        //System.out.println("http url:" + httpsUrl + "=" + _curScheme);
//		        if (!(httpsUrl.startsWith(_curScheme)))
//		        {
//		          rundata.setRedirectLocation(httpsUrl);
//		        }
		      }
		} catch (Exception e) {
			log.warn(e.getMessage(), e);
		} finally {
			pipelineContext.invokeNext();
		}
	}

	/**
	 * 获取当前url
	 */
	private String getLocaleUrl(TurbineRunDataInternal rundata) {
		// 获取当前url根路径，如 http://abc.aliyun-inc.net
		String curScheme = rundata.getRequest().getScheme(); // http
		String curServerName = rundata.getRequest().getServerName(); // abc.aliyun-inc.net
		int curServerPort = rundata.getRequest().getServerPort();
		String curUri = rundata.getRequest().getRequestURI();
		String curQueryString = rundata.getRequest().getQueryString();

		String curlocaleUrlRoot = curScheme + "://" + curServerName;
		if (curServerPort != 80) {
			curlocaleUrlRoot += ":" + curServerPort;
		}

		String localeUrl = curlocaleUrlRoot;
		if (StringUtils.isNotBlank(curUri)) {
			localeUrl = localeUrl + curUri;
		}

		if (StringUtils.isNotBlank(curQueryString)) {
			Map<String, String> queryPairs = splitQuery(curQueryString);
			QueryStringBuilder queryStrBuilder = new QueryStringBuilder();
			for (String key : queryPairs.keySet()) {
				queryStrBuilder.addQueryParameter(key, queryPairs.get(key));
			}

			try {
				localeUrl = localeUrl + queryStrBuilder.encode("UTF-8");
			} catch (UnsupportedEncodingException e) {
			}
		}

		return localeUrl;
	}

	private Map<String, String> splitQuery(String queryString) {
		Map<String, String> queryPairs = new LinkedHashMap<String, String>();
		try {
			String[] pairs = queryString.split("&");
			for (String pair : pairs) {
				int idx = pair.indexOf("=");
				queryPairs.put(
						URLDecoder.decode(pair.substring(0, idx), "UTF-8"),
						URLDecoder.decode(pair.substring(idx + 1), "UTF-8"));
			}

			return queryPairs;
		} catch (Exception e) {
			return queryPairs;
		}
	}

}
