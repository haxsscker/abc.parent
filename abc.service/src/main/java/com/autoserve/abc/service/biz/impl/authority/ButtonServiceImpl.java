package com.autoserve.abc.service.biz.impl.authority;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.autoserve.abc.dao.dataobject.ButtonDO;
import com.autoserve.abc.dao.intf.ButtonDao;
import com.autoserve.abc.dao.intf.MenuBtnDao;
import com.autoserve.abc.service.biz.intf.authority.ButtonService;
import com.autoserve.abc.service.biz.result.PlainResult;

@Service
@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
public class ButtonServiceImpl implements ButtonService {

    @Resource
    private MenuBtnDao menuBtnDao;

    @Resource
    private ButtonDao  buttonDao;

    @Override
    public PlainResult<ButtonDO> queryByTag(String tag) {
        PlainResult<ButtonDO> result = new PlainResult<ButtonDO>();
        ButtonDO bdo = buttonDao.findByTag(tag);
        if (bdo == null)
            result.setData(new ButtonDO());
        else
            result.setData(bdo);
        return result;
    }

    @Override
    public PlainResult<List<ButtonDO>> queryAllButton() {
        List<ButtonDO> list = buttonDao.findAll();
        PlainResult<List<ButtonDO>> result = new PlainResult<List<ButtonDO>>();
        if (list == null || list.isEmpty()) {
            result.setData(new ArrayList<ButtonDO>());
        } else {
            result.setData(list);
        }
        return result;
    }

}
