package org.oza.ego.portal;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.oza.ego.base.utils.HttpClientUtils;
import org.oza.ego.portal.service.ContentService;
import org.oza.ego.portal.service.impl.ContentServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.HashMap;
import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration("classpath:spring-mvc.xml")
public class HttpClientUtilsTest {

    @Autowired
    private ContentService contentService;
    @Test
    public void getTest() {
        String url = "http://localhost:8081/rest/item/all";
        Map<String, String> params = new HashMap<>();
        params.put("callback", "list");

        String result = HttpClientUtils.doGet(url, params);

        System.out.println(result);
    }

    @Test
    public void postTest() {
        String url = "http://localhost:8081/rest/item/all";
        Map<String, String> params = new HashMap<>();
        params.put("callback", "list");

        String result = HttpClientUtils.doPost(url, params);
        System.out.println(result);
    }

    @Test
    public void serviceTest() {
        System.out.println(contentService.getAdItemList());
    }
}
