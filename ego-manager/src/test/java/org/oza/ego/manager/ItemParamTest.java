package org.oza.ego.manager;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.oza.ego.base.mapper.ItemParamMapper;
import org.oza.ego.base.pojo.ItemParam;
import org.oza.ego.base.vo.EUDataGridResult;
import org.oza.ego.manager.service.ItemParamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration({"classpath:spring-mvc.xml", "classpath:spring-mybatis.xml"})
public class ItemParamTest {
    @Autowired
    private ItemParamService itemParamService;

    @Autowired
    private ItemParamMapper itemParamMapper;

    @Test
    public void getOneTest(){
        QueryWrapper<ItemParam> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("item_cat_id", 5);
        ItemParam itemParam = itemParamService.getOne(null, false);
        System.out.println(itemParam);
    }

    @Test
    public void listAndPage() {
        EUDataGridResult euDataGridResult = itemParamService.listAndPage(1, 5);
        System.out.println(euDataGridResult);
    }

}
