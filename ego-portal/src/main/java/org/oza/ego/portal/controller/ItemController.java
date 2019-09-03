package org.oza.ego.portal.controller;

import org.oza.ego.base.pojo.Item;
import org.oza.ego.portal.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

// TODO: 9/2/19 商品详情页面的商品描述还没有完成
@Controller
@RequestMapping("/item")
public class ItemController {

    @Autowired
    private ItemService itemService;

    /**
     * 调用远程接口获得商品详情，放入 model 中返回到商品详情视图
     * @param itemId 商品id
     * @param model
     * @return item视图
     */
    @RequestMapping("/{itemId}")
    public String getItemById(@PathVariable("itemId") Long itemId, Model model) {
        Item item = itemService.getById(itemId);
        model.addAttribute("item", item);
        return "item";
    }
}
