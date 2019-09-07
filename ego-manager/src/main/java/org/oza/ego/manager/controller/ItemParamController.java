package org.oza.ego.manager.controller;

import org.oza.ego.base.vo.EUDataGridResult;
import org.oza.ego.base.vo.EgoResult;
import org.oza.ego.manager.service.ItemParamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/item/param")
public class ItemParamController {

    @Autowired
    private ItemParamService itemParamService;

    /**
     * 根据类目 ID 查询类目参数，并封装成 EgoResult
     * @param itemCatId 类目ID
     * @return EgoResult 对象
     */
    @RequestMapping("/query/itemcatid/{catID}")
    public EgoResult selectByCatId(@PathVariable("catID") Long itemCatId) {
        return itemParamService.getByItemCatId(itemCatId);
    }

    /**
     * 新增类目参数
     * @param catID 对应的类目ID
     * @param paramData 类目参数名
     * @return 封装好的结果对象
     */
    @RequestMapping("/save/{cid}")
    public EgoResult saveItemParam(@PathVariable("cid") Long catID, String paramData) {
        return itemParamService.saveItemParam(catID, paramData);
    }

    /**
     * 获得分页后的类目参数列表
     * @param page 当前页
     * @param rows 页面容量
     * @return 封装好的分页对象
     */
    @RequestMapping("/list")
    public EUDataGridResult listAndPage(Integer page, Integer rows) {
        return itemParamService.listAndPage(page, rows);
    }
}
