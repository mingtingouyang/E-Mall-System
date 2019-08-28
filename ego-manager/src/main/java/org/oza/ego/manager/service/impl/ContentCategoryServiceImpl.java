package org.oza.ego.manager.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import org.oza.ego.base.mapper.ContentCategoryMapper;
import org.oza.ego.base.pojo.ContentCategory;
import org.oza.ego.base.vo.EUTreeNode;
import org.oza.ego.base.vo.EgoResult;
import org.oza.ego.manager.service.ContentCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ContentCategoryServiceImpl implements ContentCategoryService {

    @Autowired
    private ContentCategoryMapper mapper;

    @Override
    public List<EUTreeNode> selectByParentId(Long parentId) {
        ArrayList<EUTreeNode> nodes = new ArrayList<>();
        //根据父节点条件查询，且状态为1
        QueryWrapper<ContentCategory> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("parent_id", parentId).eq("status", 1);
        List<ContentCategory> categories = mapper.selectList(queryWrapper);

        categories.forEach(category -> {
            EUTreeNode node = new EUTreeNode();
            node.setId(category.getId());
            node.setText(category.getName());
            //如果是父节点则 closed，否则 open
            node.setState(1 == category.getIsParent() ? "closed" : "open");

            nodes.add(node);
        });

        return nodes;
    }

    @Transactional()
    @Override
    public EgoResult save(Long parentId, String name) {
        ContentCategory category = new ContentCategory();

        category.setParentId(parentId);
        category.setName(name);
        category.setIsParent((byte)0);
        category.setSortOder(1);
        category.setCreated(new Date());
        category.setUpdated(category.getCreated());
        category.setStatus(1);

        mapper.insert(category);

        //判断当前节点的父节点是否为目录节点，否则修改其状态为父节点
        ContentCategory parentCat = mapper.selectById(parentId);
        if (0 == parentCat.getIsParent()) {
            parentCat.setIsParent((byte)1);
            mapper.updateById(parentCat);
        }

        return EgoResult.ok(category);
    }

    @Override
    public EgoResult updateNode(String name, Long id) {
        ContentCategory category = new ContentCategory();
        category.setName(name);
        category.setId(id);

        mapper.updateById(category);

        return EgoResult.ok(null);
    }

    @Transactional()
    @Override
    public EgoResult deleteNode(Long id, Long parentId) {
        //查出当前要删除的节点
        ContentCategory curNode = mapper.selectById(id);
        //判断 parent_id 是否有值，没有则从数据库里查出来
        if (null == parentId) {
            parentId = curNode.getParentId();
        }

        //判断该节点是不是此父节点的唯一一个子节点了，如果是则将该父节点改成子节点
        QueryWrapper<ContentCategory> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("parent_id", parentId).eq("status", "1"); //只查询状态为有效的
        List<ContentCategory> childCategories = mapper.selectList(queryWrapper);
        if (1 == childCategories.size()) {
            //查出父节点并修改成子节点
            ContentCategory category = mapper.selectById(parentId);
            category.setIsParent((byte)0);
            category.setUpdated(new Date());
            mapper.updateById(category);
        }

        //判断该节点是不是一个父节点，如果是则将其下所有子节点删除
        if (1 == curNode.getIsParent()) {
            deleteAllChildNode(curNode.getId());
        }

        //修改当前节点
        curNode.setStatus(2);
        curNode.setUpdated(new Date());
        mapper.updateById(curNode);


        return EgoResult.ok(null);
    }

    /**
     * 工具方法，用于递归删除所有子节点
     * @param id 当前节点
     */
    private void deleteAllChildNode(long id) {
        QueryWrapper<ContentCategory> queryWrapper = new QueryWrapper<ContentCategory>().eq("parent_id", id).eq("status", 1);
        List<ContentCategory> categories = mapper.selectList(queryWrapper);
        for (ContentCategory category : categories) {
            category.setUpdated(new Date());
            category.setStatus(2);
            mapper.updateById(category);
            //如果该节点也是父节点，则递归
            if (1 == category.getIsParent()) {
                deleteAllChildNode(category.getId());
            }
        }
    }
}
