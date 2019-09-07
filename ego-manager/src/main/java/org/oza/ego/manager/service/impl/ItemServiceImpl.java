package org.oza.ego.manager.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.oza.ego.base.mapper.ItemDescMapper;
import org.oza.ego.base.mapper.ItemMapper;
import org.oza.ego.base.mapper.ItemParamValueMapper;
import org.oza.ego.base.pojo.Item;
import org.oza.ego.base.pojo.ItemDesc;
import org.oza.ego.base.pojo.ItemParamValue;
import org.oza.ego.base.utils.IdUtil;
import org.oza.ego.base.vo.EUDataGridResult;
import org.oza.ego.base.vo.EgoResult;
import org.oza.ego.base.vo.SearchItem;
import org.oza.ego.manager.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
public class ItemServiceImpl extends ServiceImpl<ItemMapper, Item> implements ItemService {

    @Autowired
    private ItemDescMapper itemDescMapper;

    @Autowired
    private ItemParamValueMapper itemParamValueMapper;

    @Autowired
    private JmsTemplate jmsTemplate;

    @Value("${activemq.queue}")
    private String QUEUE_NAME;

    @Override
    public EUDataGridResult listAndPage(int curPage, int rows) {
        IPage<Item> page = page(new Page<>(curPage, rows));
        return new EUDataGridResult(page.getTotal(), page.getRecords());
    }

    @Transactional
    @Override
    public EgoResult saveItem(Item item, String desc, String paramData) {
        long itemId = IdUtil.getItemId(); //随机生成 id

        //配置 item 基本初始化信息，并插入进数据库
        item.setStatus((byte)1);
        item.setId(itemId);
        item.setCreated(new Date());
        item.setUpdated(item.getCreated());
        save(item);

        //配置商品描述的初始化信息，并插入进数据库
        ItemDesc itemDesc = new ItemDesc();
        itemDesc.setItemId(itemId);
        itemDesc.setItemDesc(desc);
        itemDesc.setCreated(item.getCreated());
        itemDesc.setUpdated(item.getUpdated());
        itemDescMapper.insert(itemDesc);

        //配置商品参数的初始化信息，并插入进数据库
        ItemParamValue itemParamValue = new ItemParamValue();
        itemParamValue.setItemId(itemId);
        itemParamValue.setParamData(paramData);
        itemParamValue.setCreated(item.getCreated());
        itemParamValue.setUpdated(item.getUpdated());
        itemParamValueMapper.insert(itemParamValue);

        //将商品写入消息队列
        SearchItem temp = new SearchItem();
        temp.setId(itemId);
        temp.setImage(item.getImage());
        temp.setPrice((double)item.getPrice());
        temp.setSellPoint(item.getSellPoint());
        temp.setTitle(item.getTitle());

        jmsTemplate.send(QUEUE_NAME, session ->  session.createObjectMessage(temp));

        return EgoResult.ok(null);
    }
}
