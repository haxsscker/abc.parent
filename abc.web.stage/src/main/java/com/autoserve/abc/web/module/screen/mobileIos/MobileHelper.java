package com.autoserve.abc.web.module.screen.mobileIos;

import java.util.HashMap;
import java.util.Map;

import com.autoserve.abc.dao.dataobject.UserDO;
import com.autoserve.abc.service.biz.intf.user.UserService;
import com.googlecode.ehcache.annotations.Cacheable;
import com.googlecode.ehcache.annotations.TriggersRemove;

public class MobileHelper {

    private final Map<String, String> mobileCodeMap = new HashMap<String, String>();

    /**
     * 将null转换成空字符串
     * 
     * @param input
     * @return
     */
    public static String nullToEmpty(Object input) {
        return input == null ? "" : input.toString();
    }

    /**
     * 手机端用户信息检查验证
     * 
     * @param userId
     * @param map
     * @return
     */
    public static JsonMobileVO check(UserService userService, Integer userId, JsonMobileVO result) {
        UserDO userDO = userService.findById(userId).getData();

//        if (userDO.getUserRealnameIsproven() == null || userDO.getUserRealnameIsproven() == 0) {
//            result.setResultCode("201");
//            result.setResultMessage("您还没有实名认证，请先去认证！");
//            result.setResult("shimingrenzheng");
//            result.setSuccess(false);
//            return result;
//        } else 
        if (userDO.getUserBusinessState() == null || userDO.getUserBusinessState() < 2) {
            result.setResultCode("202");
            result.setResultMessage("您还未开户，请先去开户！");
            result.setResult("kaihu");
            result.setSuccess(false);
            return result;
        } 
        else if (userDO.getUserAuthorizeFlag() == null || userDO.getUserAuthorizeFlag() == 0) {
            result.setResultCode("203");
            result.setResultMessage("您还未开启自动转账授权，请先去授权！");
            result.setResult("shouquan");
            result.setSuccess(false);
            return result;
        }
        return result;
    }

    @Cacheable(cacheName = "validCodeCache")
    public String getValidCode(String id) {
        return mobileCodeMap.get(id);
    }

    @TriggersRemove(cacheName = "validCodeCache")
    public void removeValidCode(String id) {
        mobileCodeMap.remove(id);
    }

    public void putValidCode(String id, String validCOde) {
        mobileCodeMap.put(id, validCOde);
    }

}
