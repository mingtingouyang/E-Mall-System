package org.oza.ego.portal.service.impl;

import org.oza.ego.base.pojo.Item;
import org.oza.ego.base.utils.HttpClientUtils;
import org.oza.ego.base.utils.JsonUtils;
import org.oza.ego.portal.service.ItemService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ItemServiceImpl implements ItemService {

    @Value("${rest.baseUrl}")
    private String baseUrl;

    @Override
    public Item getById(Long itemId) {
        Item item = null;
        String itemJson = HttpClientUtils.doGet(baseUrl + "/item/" + itemId, null);
        if (null != itemJson) {
            item = JsonUtils.jsonToPojo(itemJson, Item.class);
        }
        return item;
    }
}
