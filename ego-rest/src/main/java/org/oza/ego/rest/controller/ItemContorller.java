package org.oza.ego.rest.controller;

import org.oza.ego.base.pojo.Item;
import org.oza.ego.rest.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/item")
public class ItemContorller {

    @Autowired
    private ItemService itemService;

    @RequestMapping("/{itemId}")
    @ResponseBody
    public Item getById(@PathVariable("itemId")Long itemId) {
        return itemService.getById(itemId);
    }
}
