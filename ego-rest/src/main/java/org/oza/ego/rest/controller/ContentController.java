package org.oza.ego.rest.controller;

import org.oza.ego.base.vo.EgoResult;
import org.oza.ego.rest.service.ContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/content")
public class ContentController {
    @Autowired
    private ContentService contentService;

    /**
     * 根据类目ID查询内容集合
     * @param cid 类目ID
     * @return 操作结果，里面封装了内容集合
     */
    @RequestMapping("/category/{cid}")
    public EgoResult getContents(@PathVariable Long cid) {
        return contentService.getContentsByCatId(cid);
    }
}
