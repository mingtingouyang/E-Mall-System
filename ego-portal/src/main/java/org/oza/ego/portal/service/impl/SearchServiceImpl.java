package org.oza.ego.portal.service.impl;

import org.oza.ego.base.utils.HttpClientUtils;
import org.oza.ego.base.utils.JsonUtils;
import org.oza.ego.base.vo.SearchResult;
import org.oza.ego.portal.service.SearchService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class SearchServiceImpl implements SearchService {

    @Value("${search.baseUrl}")
    private String baseUrl;

    @Value("${search.doSearch}")
    private String searchUrl;

    @Override
    public SearchResult query(String q, Integer page) {
        Map<String, String> params = new HashMap<>();
        params.put("keyword", q);
        params.put("page", String.valueOf(page));
        String resultJson = HttpClientUtils.doGet(baseUrl + searchUrl, params);
        return JsonUtils.jsonToPojo(resultJson, SearchResult.class);
    }
}
