package com.autoserve.abc.service.biz.entity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.autoserve.abc.service.util.MenuNodeComparator;

/**
 * 类MenuTree.java的实现描述：左侧的菜单树结构
 * 
 * @author pp 2014年11月29日 上午11:56:33
 */
public class MenuNode {

    private static Integer ROOT_ID    = 0;
    private Integer        id;
    private String         text;
    private String         iconCls;
    private String         state;
    private AttrOfMenuNode attributes = new AttrOfMenuNode();
    private List<MenuNode> children   = null;

    public static MenuNode buildLeftTree(List<MenuNode> list) {
        MenuNode root = new MenuNode();
        root.setId(ROOT_ID);
        return recursiveBuild(root, list);
    }

    /**
     * 循环构建左侧的树
     * 
     * @param root
     * @param list
     * @return
     */
    // TODO optimize
    public static MenuNode recursiveBuild(MenuNode root, List<MenuNode> list) {
        List<MenuNode> children = new ArrayList<MenuNode>();
        for (int i = 0; i < list.size(); i++) {
            MenuNode item = list.get(i);
            if (item.getAttributes().getParentid().equals(root.getId())) {
                children.add(item);
            }
        }
        Collections.sort(children, new MenuNodeComparator());
        root.setChildren(children);
        for (int i = 0; i < children.size(); i++) {
            recursiveBuild(children.get(i), list);
        }
        return root;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getIconCls() {
        return iconCls;
    }

    public void setIconCls(String iconCls) {
        this.iconCls = iconCls;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public AttrOfMenuNode getAttributes() {
        return attributes;
    }

    public void setAttributes(AttrOfMenuNode attributes) {
        this.attributes = attributes;
    }

    public List<MenuNode> getChildren() {
        return children;
    }

    public void setChildren(List<MenuNode> children) {
        this.children = children;
    }

}
