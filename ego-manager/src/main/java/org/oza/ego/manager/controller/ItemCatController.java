package org.oza.ego.manager.controller;

import org.oza.ego.base.vo.EUTreeNode;
import org.oza.ego.manager.service.ItemCatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/item/cat")
public class ItemCatController {
    @Autowired
    private ItemCatService itemCatService;

    /**
     * 根据 parent id 获取类别树的节点。每次点击一个父节点后，方法返回子节点
     * @param id 父节点。若没有传值（加载根目录），默认父节点为 0。
     * @return 节点集合的 JSON
     */
    @RequestMapping("/list")
    public List<EUTreeNode> getNodesByParentId(@RequestParam(defaultValue = "0") Long id) {
        List<EUTreeNode> nodes = itemCatService.getByParentId(id);
        return nodes;
    }

}
