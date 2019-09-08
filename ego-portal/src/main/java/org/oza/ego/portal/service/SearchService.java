package org.oza.ego.portal.service;

import org.oza.ego.base.vo.SearchResult;

public interface SearchService {
    /**
     * 根据关键字和当前页数执行搜索
     * @param q 关键字
     * @param page 当前页数
     * @return 搜索结果对象
     */
    SearchResult query(String q, Integer page);
}
