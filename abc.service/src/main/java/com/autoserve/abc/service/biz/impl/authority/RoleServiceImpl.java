package com.autoserve.abc.service.biz.impl.authority;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.autoserve.abc.dao.common.PageCondition;
import com.autoserve.abc.dao.dataobject.RoleDO;
import com.autoserve.abc.dao.intf.EmployeeRoleDao;
import com.autoserve.abc.dao.intf.RoleDao;
import com.autoserve.abc.service.biz.intf.authority.RoleService;
import com.autoserve.abc.service.biz.result.BaseResult;
import com.autoserve.abc.service.biz.result.PageResult;
import com.autoserve.abc.service.exception.BusinessException;

@Service
@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
public class RoleServiceImpl implements RoleService {

    private static final Logger logger = LoggerFactory.getLogger(RoleServiceImpl.class);

    @Resource
    private RoleDao             roleDao;

    @Resource
    private EmployeeRoleDao     employeeRoleDao;

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public BaseResult createRole(RoleDO role) {
        BaseResult result = new BaseResult();
        RoleDO rd = roleDao.findByRoleName(role.getRoleName());
        if (rd != null) {
            logger.info("角色名重复");
            throw new BusinessException("角色名不能重复");
        }
        roleDao.insert(role);
        return result;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public BaseResult removeRole(Integer roleId) {
        BaseResult result = new BaseResult();
        roleDao.delete(roleId);
        employeeRoleDao.deleteByRoleId(roleId);
        return result;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public BaseResult modifyRole(RoleDO role) {
        if (role.getRoleId() == null)
            throw new BusinessException("修改的角色主键为空");
        BaseResult result = new BaseResult();
        roleDao.update(role);
        return result;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public PageResult<RoleDO> queryPageRole(PageCondition condition) {
        PageResult<RoleDO> result = new PageResult<RoleDO>(condition);
        List<RoleDO> list = roleDao.findByPage(condition);
        if (list == null)
            result.setData(new ArrayList<RoleDO>());
        else {
            int count = roleDao.findAllCount();
            result.setTotalCount(count);
            result.setData(list);
        }
        return result;
    }
}
