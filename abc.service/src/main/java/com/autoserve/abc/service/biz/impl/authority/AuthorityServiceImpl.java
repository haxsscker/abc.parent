package com.autoserve.abc.service.biz.impl.authority;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.autoserve.abc.dao.dataobject.ButtonDO;
import com.autoserve.abc.dao.dataobject.MenuBtnDO;
import com.autoserve.abc.dao.dataobject.MenuDO;
import com.autoserve.abc.dao.dataobject.RoleBtnDO;
import com.autoserve.abc.dao.intf.ButtonDao;
import com.autoserve.abc.dao.intf.EmployeeRoleDao;
import com.autoserve.abc.dao.intf.MenuBtnDao;
import com.autoserve.abc.dao.intf.MenuDao;
import com.autoserve.abc.dao.intf.RoleBtnDao;
import com.autoserve.abc.service.biz.convert.MenuDOtoNodeConverter;
import com.autoserve.abc.service.biz.entity.MenuNode;
import com.autoserve.abc.service.biz.intf.authority.AuthorityService;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.service.biz.result.PlainResult;
import com.autoserve.abc.service.util.GuavaUtil;

/**
 * 类AuthorityServiceImpl.java的实现描述 权限相关操作
 * 
 * @author pp 2014年11月29日 下午5:53:45
 */
@Service
@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
public class AuthorityServiceImpl implements AuthorityService {

    /**
     * 适配前端 逼不得已使用了 men_Id这样的key
     */
    private static final String MENU_ID   = "men_Id";
    private static final String MENU_NAME = "men_Name";
    private static final String MENU_ICON = "iconCls";
    private static final String BUTTON    = "Buttons";
    @Resource
    private MenuDao             menuDao;
    @Resource
    private RoleBtnDao          roleBtnDao;
    @Resource
    private ButtonDao           buttonDao;
    @Resource
    private MenuBtnDao          menuBtnDao;
    @Resource
    private EmployeeRoleDao     employeeRoleDao;

    /**
     * 查询 empId 和 menuId 下分配的按钮
     * 
     * @param empId
     * @param menuId
     * @return
     */
    @Override
    public PlainResult<List<ButtonDO>> queryAllocatedButton(Integer empId, Integer menuId) {
        PlainResult<List<ButtonDO>> result = new PlainResult<List<ButtonDO>>();

        List<ButtonDO> btns = buttonDao.findBtnByEmpAndMenu(empId, menuId);
        btns = GuavaUtil.OrderByParamInteger("btnId", btns);
        if (btns == null || btns.isEmpty()) {
            result.setData(new ArrayList<ButtonDO>());
        } else {
            result.setData(btns);
        }

        return result;
    }

    /**
     * 查询前端角色矩阵
     * 
     * @param roleId
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public PlainResult<JSONObject> queryRoleMatrix(Integer empId) {
        PlainResult<JSONObject> result = new PlainResult<JSONObject>();
        List<MenuDO> list = menuDao.findAll();
        JSONObject root = new JSONObject();
        root.put(MENU_ID, 0);
        result.setData(build(root, list, empId));
        return result;
    }

    /**
     * 修改角色矩阵 实质是先删除 后插入
     * 
     * @param matrixMap
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public BaseResult modifyRoleMatrix(Map<Integer, List<Integer>> matrixMap, Integer roleId) {
        roleBtnDao.deleteByRole(roleId);
        for (Map.Entry<Integer, List<Integer>> entry : matrixMap.entrySet()) {
            Integer menuId = entry.getKey();
            List<Integer> btnIds = entry.getValue();
            for (Integer btnId : btnIds) {
                RoleBtnDO rdo = new RoleBtnDO();
                rdo.setRoleId(roleId);
                rdo.setBtnId(btnId);
                rdo.setMenuId(menuId);
                roleBtnDao.insert(rdo);
            }
        }
        return new BaseResult();
    }

    /**
     * 根据用户ID 寻找用户具有的角色列表 再展示左侧的菜单栏
     * 
     * @param empId
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public PlainResult<MenuNode> queryMenuByEmpId(Integer empId) {
        List<MenuDO> menuList = menuDao.findByEmpId(empId);
        List<MenuNode> nodeList = MenuDOtoNodeConverter.convertList(menuList);
        MenuNode root = MenuNode.buildLeftTree(nodeList);
        PlainResult<MenuNode> pl = new PlainResult<MenuNode>();
        pl.setData(root);
        return pl;
    }

    // TODO 循环改掉
    private JSONObject build(JSONObject root, List<MenuDO> list, Integer empId) {
        List<JSONObject> children = new ArrayList<JSONObject>();
        List<ButtonDO> btnList = buttonDao.findAll();

        for (int i = 0; i < list.size(); i++) {
            MenuDO mdo = list.get(i);
            if (mdo.getMenuParent().equals(root.getInteger(MENU_ID))) {
                JSONObject obj = new JSONObject();
                obj.put(MENU_ID, mdo.getMenuId());
                obj.put(MENU_NAME, mdo.getMenuName());
                obj.put(MENU_ICON, mdo.getMenuSmallicon());
                List<MenuBtnDO> allocatList = menuBtnDao.findByMenuId(mdo.getMenuId());
                List<RoleBtnDO> roleBtnList = roleBtnDao.findByMenuAndRole(empId, mdo.getMenuId());
                Map<Integer, RoleBtnDO> roleBtnMap = new HashMap<Integer, RoleBtnDO>();
                JSONArray btnIds = new JSONArray();
                if (allocatList == null || allocatList.isEmpty()) {
                    obj.put(BUTTON, new JSONArray());
                } else {
                    for (MenuBtnDO mbo : allocatList) {
                        btnIds.add(String.valueOf(mbo.getBtnId()));
                    }
                    obj.put(BUTTON, btnIds);
                }
                if (roleBtnList != null && !roleBtnList.isEmpty()) {
                    for (RoleBtnDO rdo : roleBtnList) {
                        roleBtnMap.put(rdo.getBtnId(), rdo);
                    }
                }
                for (ButtonDO bdo : btnList) {
                    if (roleBtnMap.get(bdo.getBtnId()) != null) {
                        obj.put(String.valueOf(bdo.getBtnId()), "√");
                    } else {
                        obj.put(String.valueOf(bdo.getBtnId()), "x");
                    }
                }
                children.add(obj);
            }
        }
        root.put("children", children);
        for (int i = 0; i < children.size(); i++) {
            build(children.get(i), list, empId);
        }
        return root;
    }

}
