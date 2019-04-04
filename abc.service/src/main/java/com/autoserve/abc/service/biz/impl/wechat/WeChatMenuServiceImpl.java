package com.autoserve.abc.service.biz.impl.wechat;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.autoserve.abc.dao.dataobject.WeChatMenuDO;
import com.autoserve.abc.dao.intf.WeChatMenuDao;
import com.autoserve.abc.service.biz.convert.WeChatMenuDOtoItemConverter;
import com.autoserve.abc.service.biz.entity.WeChatMenuItem;
import com.autoserve.abc.service.biz.intf.wechat.WeChatMenuService;
import com.autoserve.abc.service.biz.intf.wxproxy.WxProxyService;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.service.biz.result.PlainResult;
import com.autoserve.abc.service.exception.BusinessException;
import com.autoserve.abc.service.util.MenuToWeChatJSON;

@Service
public class WeChatMenuServiceImpl implements WeChatMenuService {

    private static Logger logger = LoggerFactory.getLogger(WeChatMenuServiceImpl.class);

    @Resource
    private WeChatMenuDao weChatMenuDao;

    @Resource
    WxProxyService        wxProxyService;

    @Override
    public PlainResult<WeChatMenuItem> queryAllMenu() {
        List<WeChatMenuDO> menuList = weChatMenuDao.findAll();
        List<WeChatMenuItem> list = WeChatMenuDOtoItemConverter.convertList(menuList);
        WeChatMenuItem item = WeChatMenuItem.buildCenterTree(list);
        PlainResult<WeChatMenuItem> result = new PlainResult<WeChatMenuItem>();
        result.setData(item);
        return result;
    }

    @Override
    public PlainResult<WeChatMenuItem> queryAllUseMenu() {
        List<WeChatMenuDO> menuList = weChatMenuDao.findUseAll();
        List<WeChatMenuItem> list = WeChatMenuDOtoItemConverter.convertList(menuList);
        WeChatMenuItem item = WeChatMenuItem.buildCenterTree(list);
        PlainResult<WeChatMenuItem> result = new PlainResult<WeChatMenuItem>();
        result.setData(item);
        return result;
    }

    @Override
    public BaseResult createMenu(WeChatMenuDO menu) {
        if (menu == null) {
            logger.info("menu do is null");
            throw new BusinessException("菜单为空");
        }
        BaseResult result = new BaseResult();
        int i = weChatMenuDao.insert(menu);
        //更新成功更新微信自定义菜单
        if (i > 0) {
            PlainResult<WeChatMenuItem> root = queryAllUseMenu();
            List<WeChatMenuItem> list = root.getData().getChildren();
            //更新微信自定义菜单
            PlainResult<Map<String, String>> weChatResult = wxProxyService.updateWeChatMenu(MenuToWeChatJSON
                    .menuItemToJSON(list));
            if (!"ok".equals(weChatResult.getData().get("errmsg"))) {
                result.setSuccess(false);
                result.setMessage("微信自定义菜单同步错误,错误码：" + weChatResult.getData().get("errcode"));
            }
        }
        return result;
    }

    @Override
    public BaseResult modifyMenu(WeChatMenuDO menu) {
        if (menu == null) {
            logger.info("menu do is null");
            throw new BusinessException("菜单为空");
        }
        BaseResult result = new BaseResult();
        int i = weChatMenuDao.update(menu);
        //更新成功更新微信自定义菜单
        if (i > 0) {
            PlainResult<WeChatMenuItem> root = queryAllUseMenu();
            List<WeChatMenuItem> list = root.getData().getChildren();
            //更新微信自定义菜单
            PlainResult<Map<String, String>> weChatResult = wxProxyService.updateWeChatMenu(MenuToWeChatJSON
                    .menuItemToJSON(list));
            if (!"ok".equals(weChatResult.getData().get("errmsg"))) {
                result.setSuccess(false);
                System.out.println(weChatResult.getData().get("errmsg"));
                result.setMessage("微信自定义菜单同步错误,错误码：" + weChatResult.getData().get("errmsg").toString());
            }
        }
        return result;
    }

    @Override
    public BaseResult removeMenu(Integer id) {

        List<WeChatMenuDO> list = weChatMenuDao.findByParent(id);
        BaseResult result = new BaseResult();
        if (list != null && list.size() > 0) { //证明有子菜单
            throw new BusinessException("有子菜单，无法删除");
        }
        int i = weChatMenuDao.delete(id);
        //更新成功更新微信自定义菜单
        if (i > 0) {
            PlainResult<WeChatMenuItem> root = queryAllUseMenu();
            List<WeChatMenuItem> list1 = root.getData().getChildren();
            //更新微信自定义菜单
            PlainResult<Map<String, String>> weChatResult = wxProxyService.updateWeChatMenu(MenuToWeChatJSON
                    .menuItemToJSON(list1));
            if (!"ok".equals(weChatResult.getData().get("errmsg"))) {
                result.setSuccess(false);
                result.setMessage("微信自定义菜单同步错误,错误码：" + weChatResult.getData().get("errcode"));
            }
        }
        return result;
    }

    @Override
    public PlainResult<WeChatMenuDO> queryWeChatMenuByKey(String menuKey) {

        WeChatMenuDO weChatMenuDO = weChatMenuDao.findWeChatMenuByKey(menuKey);
        PlainResult<WeChatMenuDO> result = new PlainResult<WeChatMenuDO>();
        result.setData(weChatMenuDO);
        return result;

    }

}
