package org.oza.ego.rest.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.oza.ego.base.mapper.ContentMapper;
import org.oza.ego.base.pojo.Content;
import org.oza.ego.base.utils.JsonUtils;
import org.oza.ego.base.vo.EgoResult;
import org.oza.ego.rest.service.ContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import redis.clients.jedis.JedisCluster;

import java.util.List;

@Service
public class ContentServiceImpl implements ContentService {

    @Autowired
    private ContentMapper contentMapper;

    @Autowired
    private JedisCluster jedisCluster;

    @Value("${jedis.content.key}")
    private String key;

    /**
     * 引入了 Redis Cluster 进行缓存，现在缓存中查找，找不到再到数据库中查询
     * 在数据库查完后再将数据存入缓存
     * 缓存的数据结构类型选择 hash，减少寻址的时间
     * @param catId 类别ID
     * @return 封装好的操作结果
     */
    @Override
    public EgoResult getContentsByCatId(Long catId) {
        List<Content> contents;
        try {
            //先尝试从缓存中读取
            String contentsJson = jedisCluster.hget(key, String.valueOf(catId));
            //判断是否有结果
            if (null != contentsJson) {
                //有结果则将数据转换成 content 集合
                contents = JsonUtils.jsonToList(contentsJson, Content.class);
            } else {
                //没有结果则从数据库中取出数据
                contents = searchContentFromDB(catId);
                //再将数据放入数据库
                jedisCluster.hset(key, String.valueOf(catId), JsonUtils.objectToJson(contents));
            }
        } catch (Exception e) {
            e.printStackTrace();
            //出现异常也要从数据库查询
            contents = searchContentFromDB(catId);
            //再将数据放入数据库
            jedisCluster.hset(key, String.valueOf(catId), JsonUtils.objectToJson(contents));
        }

        return EgoResult.ok(contents);
    }

    /**
     * 工具方法，复用
     * @param catId 类目ID
     * @return 内容集合
     */
    private List<Content> searchContentFromDB(Long catId) {
        QueryWrapper<Content> wrapper = new QueryWrapper<Content>().eq("category_id", catId);
        return contentMapper.selectList(wrapper);
    }
}
