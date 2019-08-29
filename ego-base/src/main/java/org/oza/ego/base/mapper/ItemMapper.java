package org.oza.ego.base.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;
import org.oza.ego.base.pojo.Item;
import org.oza.ego.base.vo.SearchItem;

import java.util.List;

public interface ItemMapper extends BaseMapper<Item> {

    /**
     * 用于solr服务采集数据
     * @return SearchItem 集合
     */
    @Select("select i.id, i.sell_point as sellPoint, i.title, i.price, i.image, c.name as categoryName from tb_item i left join tb_item_cat c on i.cid = c.id where i.status = 1")
    List<SearchItem> getSearchData();
}
