package org.oza.ego.portal.service;

import org.oza.ego.base.pojo.Item;

public interface ItemService {
    /**
     * 调用接口获得数据
     * @param ItemId 商品id
     * @return Item
     */
    Item getById(Long ItemId);
}
