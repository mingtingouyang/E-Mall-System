package org.oza.ego.manager.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.oza.ego.base.mapper.ContentMapper;
import org.oza.ego.base.pojo.Content;
import org.oza.ego.base.vo.EUDataGridResult;
import org.oza.ego.base.vo.EgoResult;
import org.oza.ego.manager.service.ContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Date;

@Service
public class ContentServiceImpl implements ContentService {

    @Autowired
    private ContentMapper mapper;

    @Override
    public EUDataGridResult listByCatIdAndPage(long categoryId, int page, int rows) {
        EUDataGridResult result = new EUDataGridResult();

        Page<Content> queryPage = new Page<>(page, rows);
        QueryWrapper<Content> wrapper = new QueryWrapper<Content>().eq("category_id", categoryId);

        IPage<Content> resultPage = mapper.selectPage(queryPage, wrapper);

        result.setRows(resultPage.getRecords());
        result.setTotal(resultPage.getTotal());

        return result;
    }

    @Override
    public EgoResult addContent(Content content) {
        content.setCreated(new Date());
        content.setUpdated(content.getCreated());

        mapper.insert(content);

        return EgoResult.ok(null);
    }

    @Override
    public EgoResult update(Content content) {
        content.setUpdated(new Date());
        mapper.updateById(content);
        return EgoResult.ok(null);
    }

    @Override
    public EgoResult delete(Integer[] ids) {
        mapper.deleteBatchIds(Arrays.asList(ids));
        return EgoResult.ok(null);
    }
}
