package org.oza.ego.rest.service.impl;

import org.oza.ego.base.mapper.ItemMapper;
import org.oza.ego.base.pojo.Item;
import org.oza.ego.rest.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private ItemMapper itemMapper;

    @Override
    public Item getById(Long itemId) {
        return itemMapper.selectById(itemId);
    }
}
