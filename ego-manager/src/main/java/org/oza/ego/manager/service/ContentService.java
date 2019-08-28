package org.oza.ego.manager.service;

import org.oza.ego.base.pojo.Content;
import org.oza.ego.base.vo.EUDataGridResult;
import org.oza.ego.base.vo.EgoResult;

public interface ContentService {

    /**
     * 根据类目 ID 查询该类目下的内容，并分页
     * @param categoryId 类目ID
     * @param page 当前页
     * @param rows 每页容量
     * @return 封装好的表格初始化对象
     */
    EUDataGridResult listByCatIdAndPage(long categoryId, int page, int rows);

    /**
     * 添加内容
     * @param content 内容
     * @return 操作结果
     */
    EgoResult addContent(Content content);

    /**
     * 修改内容
     * @param content 内容
     * @return 操作结果
     */
    EgoResult update(Content content);

    /**
     * 删除多个
     * @param ids id数组
     * @return 操作结果
     */
    EgoResult delete(Integer[] ids);
}
