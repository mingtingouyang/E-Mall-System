package org.oza.ego.manager.controller;

import org.oza.ego.base.pojo.Item;
import org.oza.ego.base.vo.EUDataGridResult;
import org.oza.ego.base.vo.EgoResult;
import org.oza.ego.manager.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController  //该注解表面，该控制器下所有方法皆返回 json
@RequestMapping("/item")
public class ItemController {

    @Autowired
    private ItemService itemService;

    /**
     * 根据路径中的 id 参数，获得对应的 item
     * @param id 路径参数
     * @return item 的 JSON
     */
    @RequestMapping("/{id}")
    public Item getById(@PathVariable("id") Long id) {
        Item item = itemService.getById(id);
        return item;
    }

    /**
     * 获得分页
     * @param page 当前页
     * @param rows 需要几行数据
     * @return 项目集合的 JSON
     */
    @RequestMapping("/list")
    public EUDataGridResult listAll(int page, int rows) {
        return itemService.listAndPage(page, rows);
    }

    /**
     * 将前台上传的商品详情、商品描述、商品参数分别保存
     * @param item 商品详情
     * @param desc 商品描述
     * @param itemParams 商品参数
     * @return 封装好的修改结果
     */
    @PostMapping("/save")
    public EgoResult save(Item item, String desc, String itemParams) {
        EgoResult egoResult = itemService.saveItem(item, desc, itemParams);
        return egoResult;
    }
}
