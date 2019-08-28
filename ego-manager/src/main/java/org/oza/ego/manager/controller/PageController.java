package org.oza.ego.manager.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PageController {
    @RequestMapping("/")
    public String showIndex() {
        return "index";
    }

    /**
     * 使用路径变量绑定要访问的页面
     * @param page 路径变量
     * @return 返回被视图解析器解析后的页面
     */
    @RequestMapping("/{page}")
    public String showPage(@PathVariable("page") String page) {
        return page;
    }
}
