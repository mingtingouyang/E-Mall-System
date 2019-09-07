package org.oza.ego.base.pojo;

import java.util.List;

/**
 * 自定义导航菜单
 */
public class Menu {
    private List<?> data; //目录节点

    public List<?> getData() {
        return data;
    }

    public void setData(List<?> data) {
        this.data = data;
    }
}
