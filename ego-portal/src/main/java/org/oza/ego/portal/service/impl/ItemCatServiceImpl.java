package org.oza.ego.portal.service.impl;

import org.oza.ego.base.utils.HttpClientUtils;
import org.oza.ego.portal.service.ItemCatService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class ItemCatServiceImpl implements ItemCatService {

    @Value("${rest.baseUrl}")
    private String baseUrl;
    @Value("${rest.menuUrl}")
    private String menuUrl;

    /**
     * 从rest接口中获取菜单数据
     * @param callback 回调函数名
     * @return 字符串类型的 jsonp
     */
    @Override
    public String getMenu(String callback) {
        Map<String, String> params = new HashMap<>();
        params.put("callback", callback);
        String result = HttpClientUtils.doPost(baseUrl + menuUrl, params);
        return result;
    }
}
