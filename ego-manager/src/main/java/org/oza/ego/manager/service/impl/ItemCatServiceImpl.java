package org.oza.ego.manager.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.oza.ego.base.mapper.ItemCatMapper;
import org.oza.ego.base.pojo.ItemCat;
import org.oza.ego.base.vo.EUTreeNode;
import org.oza.ego.manager.service.ItemCatService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ItemCatServiceImpl extends ServiceImpl<ItemCatMapper, ItemCat> implements ItemCatService {

    @Override
    public List<EUTreeNode> getByParentId(Long parentId) {
        List<EUTreeNode> nodes = new ArrayList<>();
        //条件查询
        QueryWrapper<ItemCat> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("parent_id",parentId);
        List<ItemCat> itemCats = list(queryWrapper);
        //循环封装
        itemCats.forEach(itemCat -> {
            EUTreeNode node = new EUTreeNode();
            node.setId(itemCat.getId());
            node.setText(itemCat.getName());
            //判断是否为父节点，closed 则可以展开
            if (1 == itemCat.getIsParent()) {
                node.setState("closed");
            } else {
                node.setState("open");
            }
            nodes.add(node);
        });
        return nodes;
    }
}
