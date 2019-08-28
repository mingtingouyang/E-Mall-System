package org.oza.ego.manager.service;

import org.oza.ego.base.vo.EUTreeNode;
import org.oza.ego.base.vo.EgoResult;

import java.util.List;

public interface ContentCategoryService {
    /**
     * 通过父编号返回子节点
     * @param parentId 父编号
     * @return 子节点集合
     */
    List<EUTreeNode> selectByParentId(Long parentId);

    /**
     * 增加内容分类节点
     * @param parentId 内容的父节点
     * @param name 分类名
     * @return 操作结果
     */
    EgoResult save(Long parentId, String name);

    /**
     * 更新内容类型节点
     * @param name 修改后的类名
     * @param id 该节点的 ID
     * @return 操作结果
     */
    EgoResult updateNode(String name, Long id);

    /**
     * 删除节点
     * @param id 此节点
     * @param parentId 父节点
     * @return 操作结果
     */
    EgoResult deleteNode(Long id, Long parentId);

}
