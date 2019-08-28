package org.oza.ego.manager;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.oza.ego.base.vo.EUDataGridResult;
import org.oza.ego.manager.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring-mvc.xml", "classpath:spring-mybatis.xml"})
@WebAppConfiguration
public class ItemTest {
    @Autowired
    private ItemService itemService;

    @Test
    public void pageTest() {
        EUDataGridResult result = itemService.listAndPage(1, 30);
        System.out.println(result);
    }
}
