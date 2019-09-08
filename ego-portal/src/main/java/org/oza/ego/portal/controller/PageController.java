package org.oza.ego.portal.controller;

import org.oza.ego.portal.service.ContentService;
import org.oza.ego.portal.service.ItemCatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class PageController {
    @Autowired
    private ContentService contentService;

    @Autowired
    private ItemCatService itemCatService;

    /**
     * 返回首页
     * @param m Model，存放大广告
     * @return
     */
    @RequestMapping("/index")
    public String showIndex(Model m){
        String ad = contentService.getAdItemList();
        m.addAttribute("ads", ad);
        return "index";
    }

    /**
     * 视图控制
     * @param page 想要看的页面
     * @return
     */
    @RequestMapping("/{page}")
    public String showIndex(@PathVariable("page") String page) {
        return page;
    }


    /**
     * 初始化菜单
     * @param callback 回调函数名
     * @return json字符串
     */
    @RequestMapping(value = "/menu", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String getMenu(String callback) {
        return itemCatService.getMenu(callback);
    }
}
