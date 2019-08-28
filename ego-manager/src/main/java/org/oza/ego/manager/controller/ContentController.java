package org.oza.ego.manager.controller;

import org.oza.ego.base.pojo.Content;
import org.oza.ego.base.vo.EUDataGridResult;
import org.oza.ego.base.vo.EgoResult;
import org.oza.ego.manager.service.ContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/content")
public class ContentController {

    @Autowired
    private ContentService service;

    /**
     * 根据类目的ID，查询出该类下的所有内容，并分页
     * @param categoryId 类目Id
     * @param page 当前页数
     * @param rows 页面容量
     * @return 封装好的表格初始化对象
     */
    @RequestMapping("/query/list")
    public EUDataGridResult selectByCatIdAndPage(long categoryId, int page, int rows) {
        return service.listByCatIdAndPage(categoryId, page, rows);
    }

    /**
     * 保存内容至数据库，图片另外使用 vsftpd 上传
     * @param content 内容
     * @return 操作结果
     */
    @RequestMapping("/save")
    public EgoResult addContent(Content content) {
        return service.addContent(content);
    }

    /**
     * 更新内容
     * @param content 内容
     * @return 操作结果
     */
    @RequestMapping("/edit")
    public EgoResult update(Content content) {
        return service.update(content);
    }

    /**
     * 多项删除内容
     * @param ids id数组
     * @return 操作结果
     */
    @RequestMapping("/delete")
    public EgoResult delete(Integer[] ids) {
        return service.delete(ids);
    }
}
