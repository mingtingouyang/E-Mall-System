package org.oza.ego.rest.service;

import org.oza.ego.base.pojo.Menu;

import java.util.List;

public interface ItemCatService {

    /**
     * 查询出第一级目录
     * @return 一级目录集合
     */
    Menu initMenu();

    /**
     * 查询出该级目录的子目录
     * @param parentId 父目录 ID
     * @return 所有子节点
     */
    List getNodesByParentId(long parentId);

    /**
     * 指定查询数量，查询该级目录的子目录
     * @param parentId 父目录 ID
     * @param size 子节点数量
     * @return 指定数量的子节点
     */
    List getNodesByParentId(long parentId, Integer size);

}
