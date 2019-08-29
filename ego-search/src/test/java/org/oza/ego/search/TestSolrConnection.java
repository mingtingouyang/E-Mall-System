package org.oza.ego.search;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocumentList;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.io.IOException;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration({"classpath:spring-solr.xml", "classpath:spring-mvc.xml", "classpath:spring-mybatis.xml"})
public class TestSolrConnection {

    @Autowired
    private HttpSolrClient httpSolrClient;

    @Test
    public void test() {
        try {
            SolrQuery solrQuery = new SolrQuery();
            solrQuery.add("q", "item_title:手机");
            QueryResponse response = httpSolrClient.query("ego", solrQuery);
            System.out.println(response.getStatus());
            SolrDocumentList documents = response.getResults();
            System.out.println(documents);
        } catch (SolrServerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testString() {
        System.out.println("         ".trim().equals(""));
    }

}
