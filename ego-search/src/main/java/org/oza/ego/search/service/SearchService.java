package org.oza.ego.search.service;

import org.apache.solr.common.SolrInputDocument;
import org.oza.ego.base.vo.EgoResult;
import org.oza.ego.base.vo.SearchItem;

import java.util.List;

public interface SearchService {

    /**
     * 收集数据，用于solr服务
     * @return SearchItem 集合
     */
    List<SearchItem> getData();

    /**
     * 将 searchItem 封装成 document， 用于写入solr
     * @param items
     * @return
     */
    List<SolrInputDocument> createDocuments(List<SearchItem> items);

    /**
     * 将文档导入 solr 服务器中
     * @param documents 文档集合
     * @return 操作结果
     */
    EgoResult createIndex(List<SolrInputDocument> documents);
}
