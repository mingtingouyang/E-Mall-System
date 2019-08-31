package org.oza.ego.search;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.oza.ego.base.vo.SearchResult;
import org.oza.ego.search.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration({"classpath:spring-solr.xml", "classpath:spring-mvc.xml", "classpath:spring-mybatis.xml"})
public class SearchServiceTest {


    @Autowired
    private SearchService service;

    @Test
    public void getDataTest() {
        System.out.println(service.getData());
    }

    @Test
    public void getDocuments() {
        System.out.println(service.createDocuments(service.getData()));
    }

    @Test
    public void searchTest() {
        SearchResult result = service.doSearch("手机", null, null, 1, null);
        System.out.println(result);
    }
}
