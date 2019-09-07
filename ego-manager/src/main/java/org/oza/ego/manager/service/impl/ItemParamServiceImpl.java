package org.oza.ego.manager.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.oza.ego.base.mapper.ItemParamMapper;
import org.oza.ego.base.pojo.ItemParam;
import org.oza.ego.base.vo.EUDataGridResult;
import org.oza.ego.base.vo.EgoResult;
import org.oza.ego.manager.service.ItemParamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class ItemParamServiceImpl extends ServiceImpl<ItemParamMapper, ItemParam> implements ItemParamService {

    @Autowired
    private ItemParamMapper itemParamMapper;

    @Override
    public EgoResult getByItemCatId(Long itemCatId) {
        //根据类目 id 查询类目参数
        QueryWrapper<ItemParam> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("item_cat_id", itemCatId);
        ItemParam itemParam = getOne(queryWrapper); //获得一个即可，类目 id 唯一
        if (itemParam != null) {
            return EgoResult.ok(itemParam);
        }
        return new EgoResult(400, "没有查到该类的模板");
    }

    @Transactional  //事务处理
    @Override
    public EgoResult saveItemParam(Long itemCatId, String paramData) {
        ItemParam itemParam = new ItemParam();
        itemParam.setItemCatId(itemCatId);
        itemParam.setParamData(paramData);
        itemParam.setCreated(new Date());
        itemParam.setUpdated(itemParam.getCreated());

        save(itemParam);

        return EgoResult.ok(null);
    }

    @Override
    public EUDataGridResult listAndPage(int curPage, int pageSize) {
        List<Map<String, Object>> itemPramList = itemParamMapper.listAndPage((curPage - 1) * pageSize, pageSize);
        int totalRecord = count();
        EUDataGridResult euDataGridResult = new EUDataGridResult(totalRecord, itemPramList);
        return euDataGridResult;
    }
}
