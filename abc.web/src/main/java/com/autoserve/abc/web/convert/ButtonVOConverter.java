package com.autoserve.abc.web.convert;

import java.util.ArrayList;
import java.util.List;

import com.autoserve.abc.dao.dataobject.ButtonDO;
import com.autoserve.abc.web.vo.button.AllocatBtnVO;
import com.autoserve.abc.web.vo.button.ButtonListVO;

public class ButtonVOConverter {

    public static AllocatBtnVO convert(ButtonDO bdo) {
        AllocatBtnVO avo = new AllocatBtnVO();
        avo.setBtnIcon(bdo.getBtnIcon());
        avo.setBtnId((bdo.getBtnId()));
        avo.setBtnName((bdo.getBtnName()));
        return avo;
    }

    public static List<AllocatBtnVO> convertList(List<ButtonDO> list) {
        List<AllocatBtnVO> result = new ArrayList<AllocatBtnVO>();
        for (ButtonDO bdo : list) {
            result.add(convert(bdo));
        }
        return result;
    }

    public static ButtonListVO convertToListVO(ButtonDO bdo) {
        ButtonListVO bvo = new ButtonListVO();
        bvo.setField(String.valueOf(bdo.getBtnId()));
        bvo.setTitle(bdo.getBtnName());
        return bvo;
    }

    public static List<ButtonListVO> convertListToListVO(List<ButtonDO> list) {
        List<ButtonListVO> result = new ArrayList<ButtonListVO>();
        for (ButtonDO bdo : list)
            result.add(convertToListVO(bdo));
        return result;
    }
}
