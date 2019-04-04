package com.autoserve.abc.service.biz.impl.authority;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.autoserve.abc.dao.dataobject.ButtonDO;
import com.autoserve.abc.dao.dataobject.MenuBtnDO;
import com.autoserve.abc.dao.dataobject.MenuDO;
import com.autoserve.abc.dao.intf.ButtonDao;
import com.autoserve.abc.dao.intf.MenuBtnDao;
import com.autoserve.abc.dao.intf.MenuDao;
import com.autoserve.abc.dao.intf.RoleBtnDao;
import com.autoserve.abc.service.biz.convert.MenuDOtoItemConverter;
import com.autoserve.abc.service.biz.entity.MenuItem;
import com.autoserve.abc.service.biz.intf.authority.MenuService;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.service.biz.result.PlainResult;
import com.autoserve.abc.service.exception.BusinessException;

/**
 * 类MenuServiceImpl.java的实现描述:
 * 
 * @author pp 2014年11月17日 下午5:36:22
 */
@Service
@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
public class MenuServiceImpl implements MenuService {

    private static Logger logger = LoggerFactory.getLogger(MenuServiceImpl.class);
    @Resource
    private MenuDao       menuDao;

    @Resource
    private MenuBtnDao    menuBtnDao;

    @Resource
    private RoleBtnDao    roleBtnDao;

    @Resource
    private ButtonDao     buttonDao;

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public BaseResult createMenu(MenuDO menu) {
        if (menu == null) {
            logger.info("menu do is null");
            throw new BusinessException("菜单为空");
        }
        BaseResult result = new BaseResult();
        menuDao.insert(menu);
        return result;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public BaseResult modifyMenu(MenuDO menu) {
        if (menu == null) {
            logger.info("menu do is null");
            throw new BusinessException("菜单为空");
        }
        BaseResult result = new BaseResult();
        menuDao.update(menu);
        return result;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public BaseResult removeMenu(Integer id) {
        List<MenuDO> list = menuDao.findByParent(id);
        BaseResult result = new BaseResult();
        if (list != null && list.size() > 0) { //证明有子菜单
            throw new BusinessException("有子菜单，无法删除");
        }
        menuDao.delete(id);
        return result;
    }

    /**
     * 对menu分配按钮 对以前的数据 先删除
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public BaseResult allocBtn(Integer menuId, List<Integer> btns) {
        BaseResult result = new BaseResult();
        menuBtnDao.deleteByMenuId(menuId); //首先删除存在的记录
        for (Integer btn : btns) {
            MenuBtnDO mbo = new MenuBtnDO();
            mbo.setMenuId(menuId);
            mbo.setBtnId(btn);
            menuBtnDao.insert(mbo);
        }
        this.roleBtnDao.deleteMenuByNotInList(menuId, btns);

        return result;
    }

    /**
     * 查询所有菜单 在菜单管理栏做显示
     */
    @Override
    public PlainResult<MenuItem> queryAllMenu() {
        List<MenuDO> menuList = menuDao.findAll();
        List<MenuItem> list = MenuDOtoItemConverter.convertList(menuList);
        MenuItem item = MenuItem.buildCenterTree(list);
        PlainResult<MenuItem> result = new PlainResult<MenuItem>();
        result.setData(item);
        return result;
    }

    /**
     * 获取某个菜单下已经分配的button
     * 
     * @return
     */
    @Override
    public PlainResult<List<ButtonDO>> queryAllocatedButton(Integer menuId) {
        PlainResult<List<ButtonDO>> result = new PlainResult<List<ButtonDO>>();
        List<MenuBtnDO> list = menuBtnDao.findByMenuId(menuId);
        if (list == null || list.isEmpty()) {
            result.setData(new ArrayList<ButtonDO>());
        } else {
            Set<Integer> ids = new HashSet<Integer>();
            for (MenuBtnDO mbo : list) {
                ids.add(mbo.getBtnId());
            }
            List<Integer> idsList = new ArrayList<Integer>();
            for (Integer id : ids) {
                idsList.add(id);
            }
            List<ButtonDO> btns = buttonDao.findByList(idsList);
            if (btns == null || btns.isEmpty()) {
                result.setData(new ArrayList<ButtonDO>());
            } else {
                result.setData(btns);
            }
        }
        return result;
    }

}
