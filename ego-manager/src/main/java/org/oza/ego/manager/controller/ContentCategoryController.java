package org.oza.ego.manager.controller;

import org.oza.ego.base.vo.EUTreeNode;
import org.oza.ego.base.vo.EgoResult;
import org.oza.ego.manager.service.ContentCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/content/category")
public class ContentCategoryController {

    @Autowired
    private ContentCategoryService service;

    /**
     * 根据 id 参数，返回对应的子节点
     * @param parentId 父节点 ID
     * @return 子节点集合
     */
    @GetMapping("/list")
    public List<EUTreeNode> selectByParentId(@RequestParam(name = "id", defaultValue = "0") long parentId) {
        return service.selectByParentId(parentId);
    }

    /**
     * 根据该节点父ID，该节点类别名，添加类别
     * @param parentId 该节点父ID
     * @param name 该节点类别名
     * @return 操作结果
     */
    @PostMapping("/create")
    public EgoResult create(Long parentId, String name) {
        return service.save(parentId, name);
    }

    /**
     * 根据节点ID，修改后的类别名，更新数据库的类别
     * @param id 节点ID
     * @param name 要修改的类别名
     * @return 操作结果
     */
    @PostMapping("/update")
    public EgoResult update(Long id, String name) {
        return service.updateNode(name, id);
    }

    /**
     * 根据选中的节点以及其父节点，删除选中的节点。
     * 能够判断：
     * 1.是否将该节点的父节点改为子节点
     * 2.是否要将该节点的所有子节点删除
     * @param id
     * @param parentId
     * @return
     */
    @PostMapping("/delete")
    public EgoResult delete(Long id, Long parentId) {
        return service.deleteNode(id, parentId);
    }

}
