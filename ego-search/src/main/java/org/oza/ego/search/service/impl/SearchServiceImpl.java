package org.oza.ego.search.service.impl;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.oza.ego.base.mapper.ItemMapper;
import org.oza.ego.base.vo.EgoResult;
import org.oza.ego.base.vo.SearchItem;
import org.oza.ego.base.vo.SearchResult;
import org.oza.ego.search.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SearchServiceImpl implements SearchService {

    @Autowired
    private HttpSolrClient httpSolrClient;

    @Autowired
    private ItemMapper itemMapper;

    @Value("${solr.pageSize}")
    private int pageSize;

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

    @Override
    public SearchResult doSearch(String keyword, String categoryName, String price, int page, Integer sort) {
        SearchResult result = new SearchResult();

        SolrQuery query = getSolrQuery(keyword, categoryName, price, page, sort);
        try {
            //执行搜索
            QueryResponse response = httpSolrClient.query(query);
            //解析查询结果
            SolrDocumentList documents = response.getResults();
            ///////////////////////
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    /**
     * 工具方法，用于创建搜索条件
     * @param keyword 关键字
     * @param categoryName 类目
     * @param price 价格区间
     * @param page 当前页
     * @param sort 排序规则
     * @return 搜索条件对象
     */
    private SolrQuery getSolrQuery(String keyword, String categoryName, String price, int page, Integer sort) {
        SolrQuery query = new SolrQuery();

        //如果关键字存在，设置搜索关键字
        if (null != keyword && !"".equals(keyword.trim())) {
            query.set("q", "item_title:" + keyword);
            //有关键字则开启高亮
            query.setHighlight(true);
            query.setHighlightSimplePre("<font style='color:red'>");
            query.setHighlightSimplePost("</font>");
            query.addHighlightField("item_title");
        } else {
            query.set("q", "item_title:*");
        }
        //判断是否又商品类别过滤
        if (null != categoryName && !"".equals(categoryName.trim())) {
            query.addFilterQuery("item_category_name:" + categoryName);
        }
        //判断是否有价格过滤 “0-9” 格式
        if (null != price && !"".equals(price.trim())) {
            String[] priceNodes = price.split("-");
            if (priceNodes.length > 1) {
                query.addFilterQuery("item_price:[" + priceNodes[0] + "TO" + priceNodes[1] + "]");
            } else {
                query.addFilterQuery("item_price:[" + priceNodes[0] + "TO *]");
            }
        }
        //分页设置
        query.setStart((page - 1) * pageSize); //起始行
        query.setRows(pageSize); //要查几条数据
        //判断是否按价格排序，  0升序，1降序
        if (null != sort) {
            if (0 == sort) {
                query.setSort("item_price", SolrQuery.ORDER.asc);
            } else {
                query.setSort("item_price", SolrQuery.ORDER.desc);
            }
        }
        return query;
    }
}
