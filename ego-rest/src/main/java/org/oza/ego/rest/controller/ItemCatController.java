package org.oza.ego.rest.controller;

import org.oza.ego.base.pojo.Menu;
import org.oza.ego.base.utils.JsonUtils;
import org.oza.ego.rest.service.ItemCatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ItemCatController {

    @Autowired
    private ItemCatService itemCatService;

    /**
     * 根据回调函数名，将数据手动封装成 jsonp 并返回
     * RequestMapping 中设定 produces 参数，指定编码类型解决中文乱码问题
     * @param callback 回调函数名
     * @return jsonp
     */
    @RequestMapping(value="/item/all", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public String getMenu(String callback) {
        Menu menu = itemCatService.initMenu();
        String jsonMenu = JsonUtils.objectToJson(menu);
        //手动封装回调函数
        jsonMenu = callback + "(" + jsonMenu + ")";

        return jsonMenu;
    }

}
