package com.autoserve.abc.dao.intf;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.autoserve.abc.dao.BaseDao;
import com.autoserve.abc.dao.dataobject.WeChatMenuDO;

public interface WeChatMenuDao extends BaseDao<WeChatMenuDO, Integer> {

    /**
     * 查询所有的菜单信息,包括未启用的
     * 
     * @return
     */
    public List<WeChatMenuDO> findAll();

    /**
     * 查询可用的菜单,只包含已启用的
     * 
     * @return
     */
    public List<WeChatMenuDO> findUseAll();

    /**
     * 根据父节点的Id查询子菜单
     * 
     * @param parent
     * @return
     */
    public List<WeChatMenuDO> findByParent(Integer parent);

    /**
     * 根据menuKey查询菜单信息
     * 
     * @param menuKey
     * @return
     */
    public WeChatMenuDO findWeChatMenuByKey(@Param("menuKey") String menuKey);
}
