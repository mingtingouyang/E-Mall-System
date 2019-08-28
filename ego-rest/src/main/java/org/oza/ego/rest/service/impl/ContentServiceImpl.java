package org.oza.ego.rest.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.oza.ego.base.mapper.ContentMapper;
import org.oza.ego.base.pojo.Content;
import org.oza.ego.base.vo.EgoResult;
import org.oza.ego.rest.service.ContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContentServiceImpl implements ContentService {

    @Autowired
    private ContentMapper contentMapper;

    @Override
    public EgoResult getContentsByCatId(Long catId) {
        QueryWrapper<Content> wrapper = new QueryWrapper<>();
        List<Content> contents = contentMapper.selectList(wrapper);

        return EgoResult.ok(contents);
    }
}
