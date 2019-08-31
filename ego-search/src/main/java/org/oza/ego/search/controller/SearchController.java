package org.oza.ego.search.controller;

import org.oza.ego.base.vo.EgoResult;
import org.oza.ego.base.vo.SearchResult;
import org.oza.ego.search.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class SearchController {

    @Autowired
    private SearchService service;

    @RequestMapping("/import/all")
    @ResponseBody
    public EgoResult createIndex() {
        EgoResult egoResult = service.createIndex(service.createDocuments(service.getData()));
        return egoResult;
    }

    @GetMapping("/doSearch")
    @ResponseBody
    public SearchResult doSearch(String keyword, String categoryName, String price, @RequestParam(defaultValue = "1") Integer page, Integer sort) {
        return service.doSearch(keyword, categoryName, price, page, sort);
    }
}
