package com.autoserve.abc.web.vo.button;

import com.alibaba.fastjson.JSON;

public class ButtonListVO {
    private String        title;
    private String        field;
    private final Integer width  = 60;
    private final String  align  = "center";
    private final Object  editor = JSON.parse("{\"type\":\"checkbox\",\"options\":{\"on\":\"√\",\"off\":\"x\"}}");

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public Integer getWidth() {
        return width;
    }

    public String getAlign() {
        return align;
    }

    public Object getEditor() {
        return editor;
    }

}
