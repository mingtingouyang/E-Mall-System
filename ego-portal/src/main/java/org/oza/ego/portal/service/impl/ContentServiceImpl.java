package org.oza.ego.portal.service.impl;

import org.oza.ego.base.pojo.Content;
import org.oza.ego.base.utils.HttpClientUtils;
import org.oza.ego.base.utils.JsonUtils;
import org.oza.ego.base.vo.ADItem;
import org.oza.ego.base.vo.EgoResult;
import org.oza.ego.portal.service.ContentService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ContentServiceImpl implements ContentService {

    @Value("${rest.baseUrl}")
    private String baseUrl;
    @Value("${rest.bigAdUrl}")
    private String adUrl;


    @Override
    public String getAdItemList() {
        String result = HttpClientUtils.doGet(baseUrl + adUrl, null);
        //将json字符串封装成EgoResult，data是参数二类型
        EgoResult egoResult = EgoResult.formatToEgoResult(result, Content.class);
        //创建广告集合
        List<ADItem> adItems = new ArrayList<>();

        //判断是否封装完成
        if (egoResult != null && 200 == egoResult.getStatus()) {
            //取出content集合数据
            if (egoResult.getData() instanceof  List) {
                List<Content> contents = (List<Content>) egoResult.getData();
                //循环封装成广告对象
                for (Content content : contents) {
                    ADItem adItem = new ADItem();
                    adItem.setSrc(content.getPic());
                    adItem.setSrcB(content.getPic2());
                    adItem.setAlt(content.getTitleDesc());
                    adItem.setHref(content.getUrl());
                    adItems.add(adItem);
                }
            }
        }
        return JsonUtils.objectToJson(adItems);
    }
}
