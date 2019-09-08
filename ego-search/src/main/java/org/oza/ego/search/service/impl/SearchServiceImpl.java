package org.oza.ego.search.service.impl;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
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
import java.util.Map;

@Service
public class SearchServiceImpl implements SearchService {

    @Autowired
    private SolrClient solrClient;

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
            solrClient.add("ego", documents);
            solrClient.commit("ego");
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
            QueryResponse response = solrClient.query("ego", query);
            //解析查询结果
            SolrDocumentList documents = response.getResults();
            //设置商品总数量到搜索结果中
            result.setRecordCount(documents.getNumFound());
            //获取高亮的数据(没设置高亮则为空)
            Map<String, Map<String, List<String>>> highlighting = response.getHighlighting();
            //创建商品集合，准备封装至搜索结果
            List<SearchItem> items = new ArrayList<>();
            //解析文档，封装至集合
            documents.forEach(document -> {
                SearchItem item = new SearchItem();
                //设置id
                item.setId(Long.valueOf(String.valueOf(document.get("id"))));
                //设置标题
                if (null != highlighting) {
                    //如果高亮不为空，获取当前文档的高亮标题
                    Map<String, List<String>> highlightingMap = highlighting.get(document.get("id"));
                    List<String> striList = highlightingMap.get("item_title");
                    item.setTitle(striList.get(0));
                } else {
                    //没有高亮则直接设置标题
                    item.setTitle(String.valueOf(document.get("item_title")));
                }
                //设置图片
                item.setImage(String.valueOf(document.get("item_image")));
                //设置销售亮点
                item.setSellPoint(String.valueOf(document.get("item_sell_point")));
                //设置类别名
                item.setCategoryName(String.valueOf(document.get("item_category_name")));
                //设置价格
                item.setPrice(Double.valueOf(String.valueOf(document.get("item_price"))));

                //添加进集合
                items.add(item);
            });
            //将商品集合封装至结果
            result.setItemList(items);
            //设置当前页
            result.setCurPage(page);
            //设置总页数
            result.setTotalPages((int)Math.ceil(1.0 * result.getRecordCount() / pageSize));
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
