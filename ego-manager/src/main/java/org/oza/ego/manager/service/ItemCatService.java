package org.oza.ego.manager.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.oza.ego.base.pojo.ItemCat;
import org.oza.ego.base.vo.EUTreeNode;

import java.util.List;

public interface ItemCatService extends IService<ItemCat> {
    /**
     * 根据父节点，返回子节点集合
     * @param parentId 父节点 id
     * @return 封装后的节点集合
     */
    List<EUTreeNode> getByParentId(Long parentId);
}
