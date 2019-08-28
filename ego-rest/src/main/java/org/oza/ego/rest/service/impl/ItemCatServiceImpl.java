package org.oza.ego.rest.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.oza.ego.base.mapper.ItemCatMapper;
import org.oza.ego.base.pojo.ItemCat;
import org.oza.ego.base.pojo.Menu;
import org.oza.ego.base.pojo.MenuNode;
import org.oza.ego.rest.service.ItemCatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ItemCatServiceImpl implements ItemCatService {

    @Autowired
    private ItemCatMapper itemCatMapper;

    @Override
    public Menu initMenu() {
        Menu menu = new Menu();
        List nodes = getNodesByParentId(0L, 14); //所有一级节点的父ID为0
        menu.setData(nodes);
        return menu;
    }

    @Override
    public List getNodesByParentId(long parentId) {
        return getNodesByParentId(parentId, null);
    }

    @Override
    public List getNodesByParentId(long parentId, Integer size) {
        QueryWrapper<ItemCat> itemCatQueryWrapper = new QueryWrapper<>();
        itemCatQueryWrapper.eq("parent_id", parentId);
        List<ItemCat> itemCats;
        if (size == null) {
            itemCats = itemCatMapper.selectList(itemCatQueryWrapper);
        } else {
            Page<ItemCat> itemCatPage = new Page<>(1, size);
            IPage<ItemCat> page = itemCatMapper.selectPage(itemCatPage, itemCatQueryWrapper);
            itemCats = page.getRecords();
        }

        List nodes = new ArrayList();
        itemCats.forEach(itemCat -> {
            //进行判断，如果是父节点，就添加节点；不是则添加字符串
            if (1 == itemCat.getIsParent()) {
                MenuNode menuNode = new MenuNode();
                menuNode.setU("/products/" + itemCat.getId() + ".html");
                menuNode.setN("<a href='/products/" + itemCat.getId()+".html'>" + itemCat.getName() + "</a>");
                menuNode.setI(getNodesByParentId(itemCat.getId()));
                nodes.add(menuNode);
            } else {
                nodes.add("/products/" + itemCat.getId() + ".html|" + itemCat.getName());
            }
        });

        return nodes;
    }
}
