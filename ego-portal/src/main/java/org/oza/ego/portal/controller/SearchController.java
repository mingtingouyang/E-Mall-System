package org.oza.ego.portal.controller;

import org.oza.ego.base.vo.SearchResult;
import org.oza.ego.portal.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class SearchController {

    @Autowired
    private SearchService searchService;

    /**
     * 根据关键字和当前页数执行搜索，并将结果放入 Model 中
     * @param q 关键字
     * @param page 当前页数，默认值是 1
     * @param m Model 对象
     * @return 对应的视图名
     */
    @RequestMapping("/search")
    public String query(String q, @RequestParam(defaultValue = "1") Integer page, Model m) {
        SearchResult result = searchService.query(q, page);
        m.addAttribute("query", q);
        m.addAttribute("totalPages", result.getTotalPages());
        m.addAttribute("itemList", result.getItemList());
        m.addAttribute("page", page);

        return "search";
    }
}
