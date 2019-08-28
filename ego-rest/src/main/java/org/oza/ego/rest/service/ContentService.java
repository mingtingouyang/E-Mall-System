package org.oza.ego.rest.service;

import org.oza.ego.base.vo.EgoResult;

public interface ContentService {

    /**
     * 根据类别ID查询其下的内容集合
     * @param catId 类别ID
     * @return 操作结果，封装内容集合
     */
    EgoResult getContentsByCatId(Long catId);
}
