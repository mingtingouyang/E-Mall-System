package org.oza.ego.search.service.impl;

import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.common.SolrInputDocument;
import org.oza.ego.base.mapper.ItemMapper;
import org.oza.ego.base.vo.EgoResult;
import org.oza.ego.base.vo.SearchItem;
import org.oza.ego.search.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SearchServiceImpl implements SearchService {

    @Autowired
    private HttpSolrClient httpSolrClient;

    @Autowired
    private ItemMapper itemMapper;

    @Override
    public List<SearchItem> getData() {
        return itemMapper.getSearchData();
    }

    @Override
    public List<SolrInputDocument> createDocuments(List<SearchItem> items) {
        List<SolrInputDocument> documents = new ArrayList<>();
        if (null != items) {
            items.forEach(item -> {
                SolrInputDocument document = new SolrInputDocument();
                document.addField("id", item.getId());
                document.addField("item_title", item.getTitle());
                document.addField("item_category_name", item.getCategoryName());
                document.addField("item_price", item.getPrice());
                document.addField("item_sell_point", item.getSellPoint());
                document.addField("item_image", item.getImage());

                documents.add(document);
            });
        }
        return documents;
    }

    @Override
    public EgoResult createIndex(List<SolrInputDocument> documents) {
        try {
            httpSolrClient.add("ego", documents);
            httpSolrClient.commit("ego");
            return EgoResult.ok(null);
        } catch (Exception e) {
            e.printStackTrace();
            return new EgoResult(400, "连接 solr 服务器异常");
        }
    }
}
